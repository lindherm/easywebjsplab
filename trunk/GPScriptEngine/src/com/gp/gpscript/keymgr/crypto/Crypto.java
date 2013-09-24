/**
 *This object is reponsible for performing cryptographic operations for script interpreter
 **@version 1.00
 *@author Shi Yuping
 *        Beijing WatchData System Co., Ltd.  R&D Center
 *        2002/11/07
 */
package com.gp.gpscript.keymgr.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERInputStream;
import com.gp.gpscript.keymgr.asn1.DEROutputStream;
import com.gp.gpscript.keymgr.asn1.pkcs.RSAPrivateKeyStructure;
import com.gp.gpscript.keymgr.crypto.digests.MD5Digest;
import com.gp.gpscript.keymgr.crypto.digests.SHA1Digest;
import com.gp.gpscript.keymgr.crypto.engines.DESEngine;
import com.gp.gpscript.keymgr.crypto.engines.DESedeEngine;
import com.gp.gpscript.keymgr.crypto.engines.RSAEngine;
import com.gp.gpscript.keymgr.crypto.generators.DESKeyGenerator;
import com.gp.gpscript.keymgr.crypto.generators.DESedeKeyGenerator;
import com.gp.gpscript.keymgr.crypto.generators.RSAKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.macs.CBCBlockCipherMac;
import com.gp.gpscript.keymgr.crypto.modes.CBCBlockCipher;
import com.gp.gpscript.keymgr.crypto.params.DESParameters;
import com.gp.gpscript.keymgr.crypto.params.KeyParameter;
import com.gp.gpscript.keymgr.crypto.params.ParametersWithIV;
import com.gp.gpscript.keymgr.crypto.params.RSAKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.RSAKeyParameters;
import com.gp.gpscript.keymgr.crypto.params.RSAPrivateCrtKeyParameters;
import com.gp.gpscript.keymgr.util.encoders.Hex;


/**
 * standard vector test for MD5 from "Handbook of Applied Cryptography", page
 * 345.
 */

// *
public class Crypto {
	private static Logger log = Logger.getLogger(Crypto.class);
	public static final int DES_ECB = 1;
	public static final int DES_CBC = 2;
	public static final int RSA = 3;

	public static final int SHA_1 = 4;
	public static final int MD5 = 5;

	public static final int DES_KEY_GEN = 6;
	public static final int DES2_KEY_GEN = 7;
	public static final int RSA_KEY_PAIR_GEN = 8;

	public static final int IS09797_METHOD_1 = 9;
	public static final int IS09797_METHOD_2 = 10;
	public static final int EMV_PAD = 11;
	public static final int NONE = 12;

	public static final int DES_MAC = 13;
	public static final int DES_MAC_EMV = 14;
	public static final int DES3_MAC = 15;
	public static final int DES3_MAC_EMV = 16;
	public static final int DES_CBC_PAD = 17;

	public static boolean isEqualTo(byte[] a, byte[] b) {
		if (a.length != b.length)
			return false;

		for (int i = 0; i != a.length; i++)
			if (a[i] != b[i])
				return false;

		return true;
	}

	/**
	 * Creates a digest of data specified,using the algorithm specified by the
	 * mech parameter
	 * 
	 * @param digestMech
	 *            SHA_1 or MD5
	 * @param data
	 *            Data to operate on
	 * @return A byte array containing the digest of data
	 * @throws CryptoException
	 */
	public static byte[] digest(int digestMech, byte[] data)
			throws CryptoException {

		Digest digest = null;
		byte[] resBuf = null;

		if (data.length == 0)
			throw new CryptoException("invalid data");

		switch (digestMech) {
		case SHA_1:
			digest = new SHA1Digest();
			break;
		case MD5:
			digest = new MD5Digest();
			break;
		default:
			throw new CryptoException("invalid mech parameter");
		}

		resBuf = new byte[digest.getDigestSize()];
		digest.update(data, 0, data.length);
		digest.doFinal(resBuf, 0);

		return resBuf;
	}

	/**
	 * Generates a string of random bytes of length specified
	 * 
	 * @param rdmLength
	 *            Length of random data to generate in bytes.
	 * @return a byte array with length bytes of random data
	 * @throws CryptoException
	 */
	public static byte[] generaterandom(int rdmLength) throws CryptoException {
		byte[] resBuf = null;
		SecureRandom sr = null;

		if (rdmLength == 0)
			throw new CryptoException("invalid random length");

		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			/*
			 * This following call to setSeed() makes the initialisation of the
			 * SecureRandom object _very_ fast, but not secure AT ALL.
			 */
			// sr.setSeed(50); //2003/07/16
		} catch (Exception nsa) {
			// nsa.printStackTrace();
			log.error(nsa.getMessage());
			throw new CryptoException("generating random number error");
		}

		resBuf = new byte[rdmLength];
		sr.nextBytes(resBuf);

		return resBuf;
	}

	/**
	 * Generates a secret key,using the algorithm specfied by the mech parameter
	 * 
	 * @param mech
	 *            DES_KEY_GEN DES2_KEY_GEN
	 * @return The key data to generate
	 * @throws CryptoException
	 */
	public static KeyParameter generatorKey(int mech) throws CryptoException {

		SecureRandom sr = null;
		int keyLen = 0;
		DESKeyGenerator kg = null;
		DESedeKeyGenerator trikg = null;
		byte[] keydata = null;
		KeyParameter key = null;

		if (mech != DES_KEY_GEN && mech != DES2_KEY_GEN)
			throw new CryptoException("invalid mech parameter");

		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			// sr.setSeed("www.watchdata.com.cn".getBytes());
		} catch (Exception nsa) {
			log.error(nsa.getMessage());
			throw new CryptoException("get SecureRandom instance error");
		}

		switch (mech) {
		case DES_KEY_GEN:
			keyLen = DESParameters.DES_KEY_LENGTH * 8;
			kg = new DESKeyGenerator();
			break;
		case DES2_KEY_GEN:
			keyLen = DESParameters.DES_KEY_LENGTH * 2 * 8;
			trikg = new DESedeKeyGenerator();
			break;
		default:
			throw new CryptoException("invalid mech");
		}

		KeyGenerationParameters kgp = new KeyGenerationParameters(sr, keyLen);

		/*
		 * initialise the key generator with the parameters and finally,
		 * generate the key
		 */
		switch (mech) {
		case DES_KEY_GEN:
			kg.init(kgp);
			keydata = kg.generateKey();
			key = new KeyParameter(keydata);
			break;
		case DES2_KEY_GEN:
			trikg.init(kgp);
			keydata = trikg.generateKey();
			key = new KeyParameter(keydata);
			break;
		}

		return key;
	}

	/**
	 * Performs an encryption operarion,using the key specified by the value
	 * parameter, the algorithm specified by the mech parameter.
	 * 
	 * @param key
	 *            The key to use in encrypt operation
	 * @param mech
	 *            DEC_ECB or DES_CBC
	 * @param dataToEncrypt
	 *            Data to encrypt
	 * @param iv
	 *            Intial vector to use if provided and depending if required by
	 *            the algorithm specified by the mech parameter
	 * @return A byte array containing the encryptd data
	 * @throws CryptoException
	 */
	public static byte[] encrypt(byte[] key, int mech, byte[] dataToEncrypt,
			byte[] iv) throws CryptoException {

		BlockCipher engine = null;
		CipherParameters param = null;
		BufferedBlockCipher cipher = null;

		if (key.length != 8 && key.length != 16 && key.length != 24)
			throw new CryptoException("invalid encrypt key");

		if (dataToEncrypt.length == 0 || dataToEncrypt.length % 8 != 0)
			throw new CryptoException("invalid data length");

		if (mech != DES_ECB && mech != DES_CBC)
			throw new CryptoException("invalid encrypt mech parameter");

		if (mech == DES_CBC && iv.length != 8)
			throw new CryptoException("invalid encrypt intial vector");

		switch (mech) {
		case DES_ECB:
			if (key.length == 8)
				engine = new DESEngine();
			else
				engine = new DESedeEngine();

			param = new KeyParameter(key);
			break;
		case DES_CBC:
			if (key.length == 8)
				engine = new CBCBlockCipher(new DESEngine());
			else
				engine = new CBCBlockCipher(new DESedeEngine());

			param = new ParametersWithIV(new KeyParameter(key), iv);
			break;
		}

		cipher = new BufferedBlockCipher(engine);
		cipher.init(true, param);

		byte[] out = new byte[dataToEncrypt.length];

		int len1 = cipher.processBytes(dataToEncrypt, 0, dataToEncrypt.length,
				out, 0);

		try {
			cipher.doFinal(out, len1);
		} catch (CryptoException e) {
			log.error(e.getMessage());
			throw new CryptoException("encrypt exception");
		}

		return out;
	}

	/*
	 * Performs an decryption operarion,using the key specified by the value
	 * parameter, the algorithm specified by the mech parameter.
	 * 
	 * @param key The key to use in decrypt operation.
	 * 
	 * @param mech DES_ECB or DES_CBC
	 * 
	 * @param dataToDecrypt Data to decrypt
	 * 
	 * @param iv Intial vector to use if provided and depending if required by
	 * the algorithm specified by the mech parameter
	 * 
	 * @return A byte array containing the decryptd data
	 * 
	 * @throws CryptoException
	 */
	public static byte[] decrypt(byte[] key, int mech, byte[] dataToDecrypt,
			byte[] iv) throws CryptoException {

		BlockCipher engine = null;
		CipherParameters param = null;
		BufferedBlockCipher cipher = null;
		byte[] out = null;

		if (key.length != 8 && key.length != 16 && key.length != 24)
			throw new CryptoException("invalid decrypt key");

		if (dataToDecrypt.length == 0 || dataToDecrypt.length % 8 != 0)
			throw new CryptoException("invalid data length");

		if (mech != DES_ECB && mech != DES_CBC)
			throw new CryptoException("invalid decrypt mech parameter");

		if (mech == DES_CBC && iv.length != 8)
			throw new CryptoException("invalid decrypt intial vector");

		switch (mech) {
		case DES_ECB:
			if (key.length == 8)
				engine = new DESEngine();
			else
				engine = new DESedeEngine();

			param = new KeyParameter(key);
			break;
		case DES_CBC:
			if (key.length == 8)
				engine = new CBCBlockCipher(new DESEngine());
			else
				engine = new CBCBlockCipher(new DESedeEngine());

			param = new ParametersWithIV(new KeyParameter(key), iv);
			break;
		}

		cipher = new BufferedBlockCipher(engine);
		cipher.init(false, param);

		out = new byte[dataToDecrypt.length];

		int len1 = cipher.processBytes(dataToDecrypt, 0, dataToDecrypt.length,
				out, 0);

		try {
			cipher.doFinal(out, len1);
		} catch (CryptoException e) {
			log.error(e.getMessage());
			throw new CryptoException("decrypt exception");
		}

		return out;
	}

	/**
	 * Performs a decryption operarion using the key specified by the decryptkey
	 * parameter, the algorithm specified by the decryptMech parameter, followed
	 * immediately by an encrypt operation using the key passed as
	 * encryptKey,the mechanism passed as encryptMech .
	 * 
	 * @param decryptkey
	 *            The key to use in decrypt operation.
	 * @param decryptMech
	 *            DES_ECB or DES_CBC
	 * @param encryptkey
	 *            The key to use in encrypt operation
	 * @param encryptMech
	 *            DES_ECB or DES_CBC
	 * @param data
	 *            Data to encrypt
	 * @param decryptIV
	 *            Intial vector to use for the decrypt operation if provided and
	 *            depending if required by the algorthm specified by the mech
	 *            parameter.
	 * @param encryptIV
	 * @return A new byte array containing the result of the decrypt operations
	 *         on data.
	 * @throws CryptoException
	 */
	public static byte[] decryptEncrypt(byte[] decryptkey, int decryptMech,
			byte[] encryptkey, int encryptMech, byte[] data, byte[] decryptIV,
			byte[] encryptIV) throws CryptoException {
		byte[] decData = null;

		if (decryptkey.length != 8 && decryptkey.length != 16
				&& decryptkey.length != 24)
			throw new CryptoException("invalid decrypt key");

		if (encryptkey.length != 8 && encryptkey.length != 16
				&& encryptkey.length != 24)
			throw new CryptoException("invalid encrypt key");

		if (data.length == 0 || data.length % 8 != 0)
			throw new CryptoException("invalid data length");

		if (decryptMech != DES_ECB && decryptMech != DES_CBC)
			throw new CryptoException("invalid decrypto mech parameter");

		if (encryptMech != DES_ECB && encryptMech != DES_CBC)
			throw new CryptoException("invalid encrypto mech parameter");

		if (encryptMech == DES_CBC && encryptIV.length != 8)
			throw new CryptoException("invalid encrypto intial vector");

		if (decryptMech == DES_CBC && decryptIV.length != 8)
			throw new CryptoException("invalid decrypto intial vector");

		decData = decrypt(decryptkey, decryptMech, data, decryptIV);
		if (decData.length == 0)
			throw new CryptoException("decrypto error");

		decData = encrypt(encryptkey, encryptMech, decData, encryptIV);
		if (decData.length == 0)
			throw new CryptoException("encrypto error");

		return decData;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the
	 * singingKey parameter, and the algorithm specified by the signingMech
	 * parameter.
	 * 
	 * @param signingkey
	 *            Key to use for signing operation
	 * @param signingMech
	 *            DES_MAC
	 * @param data
	 *            Data to sign.
	 * @return A byte array containing the signature of data
	 * @throws CryptoException
	 */
	public static byte[] DESsign(byte[] signingkey, int signingMech,
			byte[] data, byte[] iv) throws CryptoException {
		byte[] out = new byte[8];
		KeyParameter key = null;
		BlockCipher cipher = null;
		Mac mac = null;

		/*if (signingkey.length != 16)
			throw new CryptoException("invalid key");*/

		if (data.length == 0)
			throw new CryptoException("invalid data");

		// padding the message according to ISO 7816-4
		// which is equivalent to method 2 of ISO/IEC 9797-1
		int paddedDataLen = data.length + 1;
		if ((data.length + 1) % 8 != 0) {
			paddedDataLen += 8 - (data.length + 1) % 8;
		}
		byte[] paddedData = new byte[paddedDataLen];
		System.arraycopy(data, 0, paddedData, 0, data.length);

		paddedData[data.length] = (byte) 0x80;

		// left8 xor iv
		if (iv != null)
			for (int i = 0; i < 8; i++)
				paddedData[i] = (byte) (paddedData[i] ^ iv[i]);

		// String str=new String(Hex.encode(paddedData));
		switch (signingMech) {
		case DES_MAC:
			key = new KeyParameter(signingkey);

			cipher = new DESedeEngine();

			// mac = new CBCBlockCipherMac(cipher,64);
			mac = new CBCBlockCipherMac(cipher, 64);

			mac.init(key);
			mac.update(paddedData, 0, paddedData.length);

			mac.doFinal(out, 0);
			break;
		case DES_MAC_EMV:

			byte[] leftKey = new byte[8];
			System.arraycopy(signingkey, 0, leftKey, 0, 8);

			key = new KeyParameter(leftKey);

			cipher = new DESEngine();

			// mac = new CBCBlockCipherMac(cipher,64);
			mac = new CBCBlockCipherMac(cipher, 64);

			mac.init(key);
			mac.update(paddedData, 0, paddedData.length);
			mac.doSingleFinal(signingkey, out, 0);

			break;
		default:
			throw new CryptoException("unsupported mech");
		}

		return out;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the
	 * singingKey parameter, and the algorithm specified by the signingMech
	 * parameter.
	 * 
	 * @param privParameters
	 *            The RSA private key to use for signing operation
	 * @param data
	 *            Data to sign.
	 * @return A byte array containing the signature of data
	 * @throws CryptoException
	 */
	public static byte[] RSAsign(RSAPrivateCrtKeyParameters privParameters,
			byte[] data) throws CryptoException {

		int bitLen = 0;

		if (!(privParameters instanceof RSAPrivateCrtKeyParameters))
			throw new CryptoException("invalid paramter");

		bitLen = privParameters.getModulus().bitLength();

		if (bitLen % 8 != 0 || bitLen < 512)
			throw new CryptoException("invalid key");

		byte[] out = new byte[bitLen / 8];

		// RAW RSA
		AsymmetricBlockCipher eng = new RSAEngine();

		eng.init(true, privParameters);

		try {
			out = eng.processBlock(data, 0, data.length);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new CryptoException("RSA raw encrypt exception");
		}

		return out;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the
	 * singingKey parameter, and the algorithm specified by the signingMech
	 * parameter.
	 * 
	 * @param verifykey
	 *            The key used for signature verification
	 * @param verifyMech
	 *            DES_MAC or DES_MAC_EMV
	 * @param data
	 *            Data on which the signature is based (or genreated).
	 * @param signature
	 *            Signature to verify
	 * @return True for false depending if verify was successful or not
	 * @throws CryptoException
	 */
	public static boolean DESverify(byte[] verifykey, int verifyMech,
			byte[] data, byte[] signature) throws CryptoException {
		byte[] out = new byte[8];

		if (verifykey.length != 8 && verifykey.length != 16
				&& verifykey.length != 24)
			throw new CryptoException("invalid key");

		if (data.length % 8 != 0 || data.length == 0)
			throw new CryptoException("invalid data");

		if (signature.length != 8)
			throw new CryptoException("invalid signature");

		switch (verifyMech) {
		case DES_MAC:
		case DES3_MAC: // add by sjg
			KeyParameter key = new KeyParameter(verifykey);
			BlockCipher cipher = new DESedeEngine();
			Mac mac = new CBCBlockCipherMac(cipher, 64);

			mac.init(key);
			mac.update(data, 0, data.length);
			mac.doFinal(out, 0);
			break;
		case DES_MAC_EMV:
			break;
		}

		if (!isEqualTo(out, signature))
			return false;

		return true;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the
	 * singingKey parameter, and the algorithm specified by the signingMech
	 * parameter.
	 * 
	 * @param verifykey
	 *            The RSA public key used for signature verification
	 * @param data
	 *            Data on which the signature is based (or genreated).
	 * @param signature
	 *            Signature to verify
	 * @return True for false depending if verify was successful or not
	 * @throws CryptoException
	 */
	public static boolean RSAverify(RSAKeyParameters pubParameters,
			byte[] data, byte[] signature) throws CryptoException {

		if (data.length == 0)
			throw new CryptoException("invalid data");

		if ((pubParameters.getModulus()).bitLength() % 8 != 0
				|| (pubParameters.getModulus()).bitLength() == 0)
			throw new CryptoException("invalid public key");

		if (signature.length == 0
				|| (signature.length != pubParameters.getModulus().bitLength() / 8))
			throw new CryptoException("invalid signature");

		byte[] out = new byte[(pubParameters.getModulus()).bitLength() / 8];

		AsymmetricBlockCipher eng = new RSAEngine();

		eng.init(false, pubParameters);

		try {
			out = eng.processBlock(signature, 0, signature.length);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new CryptoException("RSA verify exception");
		}

		if (!isEqualTo(data, out))
			return false;

		return true;
	}

	/**
	 * Creates a RSA public/private key pair
	 * 
	 * @param publicExponent
	 *            public key Exponent
	 * @param strength
	 *            RSA modulus bit number
	 * @return RSA private/public key pair
	 * @throws CryptoException
	 */
	public static AsymmetricCipherKeyPair generateRSAKeyPair(
			int publicExponent, int strength) throws CryptoException {

		AsymmetricCipherKeyPair pair = null;
		AsymmetricBlockCipher eng = null;
		RSAKeyPairGenerator pGen = null;
		RSAKeyGenerationParameters genParam = null;

		if (publicExponent == 0)
			throw new CryptoException("invalid publicExponent parameter");

		if (strength < 512 || strength % 8 != 0)
			throw new CryptoException("invalid strength parameter");

		pGen = new RSAKeyPairGenerator();
		genParam = new RSAKeyGenerationParameters(BigInteger
				.valueOf(publicExponent), new SecureRandom(), strength, 25);

		pGen.init(genParam);
		pair = pGen.generateKeyPair();

		return pair;
	}

	/**
	 * Derives the key derivedKey using the masterKey specified,and the
	 * algorithm specified by the mech parameter. The data parameter is used to
	 * specify the deversification data to be used in the operation.
	 * 
	 * @param masterKey
	 *            The key to derive from
	 * @param mech
	 *            DES_ECB or DES_CBC
	 * @param data
	 *            Deriviation data to use
	 * @return Key to derive.
	 * @throws CryptoException
	 */
	public static byte[] deriveKey(byte[] masterKey, int mech, byte[] data)
			throws CryptoException {

		byte[] iv = new byte[8];
		byte[] drivedkey = null;

		if (mech != DES_CBC && mech != DES_ECB)
			throw new CryptoException("invalid mech");

		if (data.length != 16)
			throw new CryptoException("invalid data");

		if (masterKey.length != 8 && masterKey.length != 16)
			throw new CryptoException("invalid key");

		for (int i = 0; i < iv.length; i++)
			iv[i] = 0;

		drivedkey = encrypt(masterKey, mech, data, iv);
		log.debug("drivedkey:====" + drivedkey);
		return drivedkey;
	}

	/*
	 * public static void main( String[] args) {
	 * 
	 * String testVec1 =
	 * "6A03010001BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
	 * ; String testVec2 =
	 * "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBC2BD9EAB49584C60E4C00BED9AE33BCEFFCE84C8BC"
	 * ; byte[] data1 = Hex.decode(testVec1); byte[] data2 =
	 * Hex.decode(testVec2); AsymmetricCipherKeyPair pair=null; byte[] data =
	 * null,resdata = null,out = null,iv={0,0,0,0,0,0,0,0};
	 * 
	 * int i; for(i=0;i<100;i++) { KeyParameter deskey = null;
	 * 
	 * System.out.println(i);
	 * 
	 * try { deskey = generatorKey(DES2_KEY_GEN); } catch (CryptoException e) {
	 * System.out.println(e.getMessage()); }
	 * 
	 * System.out.println("the generating des key:"); System.out.println(new
	 * String(Hex.encode(deskey.getKey())));
	 * 
	 * try{ data = generaterandom(256); } catch (CryptoException e) {
	 * System.out.println(e.getMessage()); }
	 * 
	 * System.out.println("the data to encrypt"); System.out.println(new
	 * String(Hex.encode(data)));
	 * 
	 * try { resdata = encrypt(deskey.getKey(),DES_CBC, data, iv); } catch
	 * (CryptoException e) { System.out.println(e.getMessage()); }
	 * 
	 * System.out.println("the des ECB encrypt result is");
	 * System.out.println(new String(Hex.encode(resdata)));
	 * 
	 * try { out = decrypt(deskey.getKey(),DES_CBC, resdata, iv); } catch
	 * (CryptoException e) { System.out.println(e.getMessage()); }
	 * 
	 * System.out.println("the des ECB decrypt result is");
	 * System.out.println(new String(Hex.encode(out)));
	 * 
	 * if(isEqualTo(out,data))
	 * System.out.println("the des ECB encrypt/decrypt OK"); else
	 * System.out.println("the des ECB encrypt/decrypt error");
	 * 
	 * } }
	 */

	public static byte[] encodePrivateKey(String modulus,
			String publicExponent, String privateExponent, String prime1,
			String prime2, String exponent1, String exponent2,
			String coefficient) {

		if (modulus == null || modulus.length() == 0)
			return null;
		if (publicExponent == null || publicExponent.length() == 0)
			return null;
		if (privateExponent == null || privateExponent.length() == 0)
			return null;
		if (prime1 == null || prime1.length() == 0)
			return null;
		if (prime2 == null || prime2.length() == 0)
			return null;
		if (exponent1 == null || exponent1.length() == 0)
			return null;
		if (exponent2 == null || exponent2.length() == 0)
			return null;
		if (coefficient == null || coefficient.length() == 0)
			return null;

		BigInteger biModulus = new BigInteger(modulus, 16);
		BigInteger biPublicExponent = new BigInteger(publicExponent, 16);
		BigInteger biPrivateExponent = new BigInteger(privateExponent, 16);
		BigInteger biPrime1 = new BigInteger(prime1, 16);
		BigInteger biPrime2 = new BigInteger(prime2, 16);
		BigInteger biExponent1 = new BigInteger(exponent1, 16);
		BigInteger biExponent2 = new BigInteger(exponent2, 16);
		BigInteger biCoefficient = new BigInteger(coefficient, 16);

		RSAPrivateKeyStructure rsaPrivateKey = new RSAPrivateKeyStructure(
				biModulus, biPublicExponent, biPrivateExponent, biPrime1,
				biPrime2, biExponent1, biExponent2, biCoefficient);

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		DEROutputStream dOut = new DEROutputStream(bOut);

		try {
			dOut.writeObject(rsaPrivateKey);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}

		return bOut.toByteArray();
	}

	public static RSAKeyParameters decodePrivateKey(byte[] encodedPrivateKey) {
		if (encodedPrivateKey == null || encodedPrivateKey.length == 0)
			return null;

		ByteArrayInputStream bIn = new ByteArrayInputStream(encodedPrivateKey);
		DERInputStream dIn = new DERInputStream(bIn);
		RSAPrivateKeyStructure rsaPKS = null;
		DERConstructedSequence seq;

		try {
			rsaPKS = new RSAPrivateKeyStructure((DERConstructedSequence) dIn
					.readObject());
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}

		RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(rsaPKS
				.getModulus(), rsaPKS.getPublicExponent(), rsaPKS
				.getPrivateExponent(), rsaPKS.getPrime1(), rsaPKS.getPrime2(),
				rsaPKS.getExponent1(), rsaPKS.getExponent2(), rsaPKS
						.getCoefficient());

		return privParameters;
	}

	public static void main(String[] args) {

		AsymmetricCipherKeyPair RSAKeyPair = null;
		RSAPrivateCrtKeyParameters rsaPKS = null;

		try {
			RSAKeyPair = generateRSAKeyPair(0x03, 1024);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}

		rsaPKS = (RSAPrivateCrtKeyParameters) RSAKeyPair.getPrivate();

		String modulus = new String(Hex.encode((rsaPKS.getModulus())
				.toByteArray()));
		String publicExponent = new String(Hex.encode((rsaPKS
				.getPublicExponent()).toByteArray()));
		String privateExponent = new String(Hex.encode((rsaPKS.getExponent())
				.toByteArray()));
		String prime1 = new String(Hex.encode((rsaPKS.getP()).toByteArray()));
		String prime2 = new String(Hex.encode((rsaPKS.getQ()).toByteArray()));
		String exponent1 = new String(Hex
				.encode((rsaPKS.getDP()).toByteArray()));
		String exponent2 = new String(Hex
				.encode((rsaPKS.getDQ()).toByteArray()));
		String coefficient = new String(Hex.encode((rsaPKS.getQInv())
				.toByteArray()));

		log.debug("modulus");
		log.debug(modulus);
		log.debug("publicExponent");
		log.debug(publicExponent);
		log.debug("privateExponent");
		log.debug(privateExponent);
		log.debug("prime1");
		log.debug(prime1);
		log.debug("prime2");
		log.debug(prime2);
		log.debug("exponent1");
		log.debug(exponent1);
		log.debug("exponent2");
		log.debug(exponent2);
		log.debug("coefficient");
		log.debug(coefficient);

		byte[] rsakey = encodePrivateKey(modulus, publicExponent,
				privateExponent, prime1, prime2, exponent1, exponent2,
				coefficient);

		RSAPrivateCrtKeyParameters rsaKey = (RSAPrivateCrtKeyParameters) decodePrivateKey(rsakey);

		String amodulus = new String(Hex.encode((rsaKey.getModulus())
				.toByteArray()));
		String apublicExponent = new String(Hex.encode((rsaKey
				.getPublicExponent()).toByteArray()));
		String aprivateExponent = new String(Hex.encode((rsaKey.getExponent())
				.toByteArray()));
		String aprime1 = new String(Hex.encode((rsaKey.getP()).toByteArray()));
		String aprime2 = new String(Hex.encode((rsaKey.getQ()).toByteArray()));
		String aexponent1 = new String(Hex.encode((rsaKey.getDP())
				.toByteArray()));
		String aexponent2 = new String(Hex.encode((rsaKey.getDQ())
				.toByteArray()));
		String acoefficient = new String(Hex.encode((rsaKey.getQInv())
				.toByteArray()));

		log.debug("modulus");
		log.debug(amodulus);
		log.debug("publicExponent");
		log.debug(apublicExponent);
		log.debug("privateExponent");
		log.debug(aprivateExponent);
		log.debug("prime1");
		log.debug(aprime1);
		log.debug("prime2");
		log.debug(aprime2);
		log.debug("exponent1");
		log.debug(aexponent1);
		log.debug("exponent2");
		log.debug(aexponent2);
		log.debug("coefficient");
		log.debug(acoefficient);

	}

}
