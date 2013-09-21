package com.gp.gpscript.utils;

import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.DESKeyGenerator;
import org.bouncycastle.crypto.generators.DESedeKeyGenerator;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.encoders.Hex;

//import com.watchdata.wdcams.loader.Loader;

public class CryptoUtil {
	private static Logger log = Logger.getLogger(CryptoUtil.class);
	public static final int DES_ECB = 1;

	public static final int DES_CBC = 2;

	static final int RSA = 3;

	static final int SHA_1 = 1;

	static final int MD5 = 2;

	static final int DES_KEY_GEN = 1;

	static final int DES2_KEY_GEN = 2;

	static final int RSA_KEY_PAIR_GEN = 3;

	static final int IS09797_METHOD_1 = 1;

	static final int IS09797_METHOD_2 = 2;

	static final int EMV_PAD = 3;

	static final int NONE = 4;

	static final int DES_MAC = 1;

	static final int DES_MAC_EMV = 2;

	// GP method
	int i;

	String Kmc;

	String Psk;

	String Rcard;

	String Rter;

	byte[] Kdckek = new byte[16];

	String initRes;

	public String getValue(String data) {
		String mystring = "45";
		RandomAccessFile rfile = null;
		data = data.toUpperCase();
		try {
			int aIndex = -1;
			rfile = new RandomAccessFile("Datafile.txt", "r");
			while (aIndex < 0) {
				mystring = rfile.readLine();
				aIndex = mystring.indexOf(";" + data);
			}
			mystring = rfile.readLine();

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return (mystring);
	}

	public String initializeUpdate(byte keyVersion, byte keyIndex) {
		byte random1[] = new byte[8];
		try {
			random1 = generaterandom(8);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		String Apdu = "";
		byte[] a = new byte[10];
		String mystring;

		mystring = Byte.toString(keyVersion);
		if (mystring.length() == 1) {
			mystring = "0" + mystring;
		}
		Apdu = "8050" + mystring;

		mystring = Byte.toString(keyIndex);
		if (mystring.length() == 1) {
			mystring = "0" + mystring;
		}
		Apdu = Apdu + mystring;
		Rter = new String(Hex.encode(random1));
		Apdu = Apdu + "08" + Rter;
		Apdu = Apdu.toUpperCase();
		// return initializeUpdate Apdu
		return Apdu;
	}

	public String GPexternalAuthenticate() {
		byte[] Kdcenc = new byte[16];
		byte[] Kdcmac = new byte[16];
		byte[] Kscenc = new byte[16];
		byte[] Kscmac = new byte[16];
		byte[] Host = new byte[8];
		byte[] Smac = new byte[8];
		String data;
		String Apdu;
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		// Kdcenc
		data = initRes.substring(0, 4) + initRes.substring(8, 16) + "f001" + initRes.substring(0, 4) + initRes.substring(8, 16) + "0f01";
		try {
			Kdcenc = encrypt(Hex.decode(Kmc), DES_ECB, Hex.decode(data), iv);
			log.debug("Kdcenc=" + new String(Hex.encode(Kdcenc)));
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		// Kdcmac
		data = initRes.substring(0, 4) + initRes.substring(8, 16) + "f002" + initRes.substring(0, 4) + initRes.substring(8, 16) + "0f02";

		try {
			Kdcmac = encrypt(Hex.decode(Kmc), DES_ECB, Hex.decode(data), iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());

		}
		// Kdckek
		data = initRes.substring(0, 4) + initRes.substring(8, 16) + "f003" + initRes.substring(0, 4) + initRes.substring(8, 16) + "0f03";
		try {
			Kdckek = encrypt(Hex.decode(Kmc), DES_ECB, Hex.decode(data), iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		// Rter= "2021222324252627";
		Rcard = initRes.substring(24, 40);
		data = Rcard.substring(8, 16) + Rter.substring(0, 8) + Rcard.substring(0, 8) + Rter.substring(8, 16);
		log.debug("data=" + data);
		// Kscenc

		try {
			Kscenc = encrypt(Kdcenc, DES_ECB, Hex.decode(data), iv);
			log.debug("Kscenc=" + new String(Hex.encode(Kscenc)));
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		// Kscmac
		try {
			Kscmac = encrypt(Kdcmac, DES_ECB, Hex.decode(data), iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		// host

		// Rcard="99BE23A67F24863A";
		try {
			Host = DESsign(Kscenc, DES_MAC, Hex.decode(Rcard + Rter));
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		// Smac

		try {
			data = "8482000010" + (new String(Hex.encode(Host)));
			Smac = DESsign(Kscmac, DES_MAC, Hex.decode(data));
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}

		// Apdu
		return "8482000010" + new String(Hex.encode(Host)) + new String(Hex.encode(Smac));
	}

	public String VSexternalAuthenticate() {
		byte[] Host = new byte[8];
		try {
			// Rter="2021222324252627";
			Rcard = initRes.substring(24, 40);
			log.debug(Rcard + Rter);
			Host = DESsign(Hex.decode(Psk), DES_MAC, Hex.decode(Rcard + Rter));
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}

		// Apdu
		return "8082000008" + new String(Hex.encode(Host));

	}

	// putkey
	public String putkey(byte ID, byte[] key, byte[] enckey) {
		byte[] data = new byte[8];
		String mystring;
		String Apdu;
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		try {
			data = encrypt(enckey, DES_ECB, key, iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		mystring = Byte.toString(ID);
		if (mystring.length() == 1) {
			mystring = "0" + mystring;
		}
		Apdu = "80D800" + mystring + "17018110" + new String(Hex.encode(data));
		try {
			data = encrypt(key, DES_ECB, iv, iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		mystring = new String(Hex.encode(data));
		Apdu = Apdu + "03" + mystring.substring(0, 6);
		return Apdu;
	}

	// reloadpin
	public String reloadpin(byte ID, byte[] pin, byte[] enckey) {
		byte[] data = new byte[16];
		String mystring;
		String Apdu;
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		try {
			data = encrypt(enckey, DES_ECB, pin, iv);
		} catch (CryptoException e) {
			log.error(e.getMessage());
		}
		mystring = Byte.toString(ID);

		if (mystring.length() == 1) {
			mystring = "0" + mystring;
		}
		Apdu = "802400" + mystring + "08" + new String(Hex.encode(data));

		return Apdu;
	}

	public static boolean isEqualTo(byte[] a, byte[] b) {
		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i != a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates a digest of data specified,using the algorithm specified by the mech parameter
	 * 
	 * @param digestMech
	 *            SHA_1 or MD5
	 * @param data
	 *            Data to operate on
	 * @return A byte array containing the digest of data
	 * @throws CryptoException
	 */
	public static byte[] digest(int digestMech, byte[] data) throws CryptoException {
		Digest digest = null;
		byte[] resBuf = null;

		if (data.length == 0) {
			throw new CryptoException("invalid data");
		}

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

		if (rdmLength == 0) {
			throw new CryptoException("invalid random length");
		}

		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			/*
			 * This following call to setSeed() makes the initialisation of the SecureRandom object _very_ fast, but not secure AT ALL.
			 */
			// sr.setSeed(50);
		} catch (Exception nsa) {
			log.error(nsa.getMessage());
			throw new CryptoException("generating random number error");
		}

		resBuf = new byte[rdmLength];
		sr.nextBytes(resBuf);

		return resBuf;
	}

	public static String generateRandom(int randomLength) {

		try {
			byte[] random = generaterandom(randomLength);
			return RsaUtil.byte2hex(random);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
		return "";
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

		if (mech != DES_KEY_GEN && mech != DES2_KEY_GEN) {
			throw new CryptoException("invalid mech parameter");
		}

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
		 * initialise the key generator with the parameters and finally, generate the key
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
	 * Performs an encryption operarion,using the key specified by the value parameter, the algorithm specified by the mech parameter.
	 * 
	 * @param key
	 *            The key to use in encrypt operation
	 * @param mech
	 *            DEC_ECB or DES_CBC
	 * @param dataToEncrypt
	 *            Data to encrypt
	 * @param iv
	 *            Intial vector to use if provided and depending if required by the algorithm specified by the mech parameter
	 * @return A byte array containing the encryptd data
	 * @throws CryptoException
	 */
	public static byte[] encrypt(byte[] key, int mech, byte[] dataToEncrypt, byte[] iv) throws CryptoException {

		BlockCipher engine = null;
		CipherParameters param = null;
		BufferedBlockCipher cipher = null;

		if (key.length != 8 && key.length != 16 && key.length != 24) {
			throw new CryptoException("invalid encrypt key");
		}

		if (dataToEncrypt.length == 0 || dataToEncrypt.length % 8 != 0) {
			throw new CryptoException("invalid data length");
		}

		if (mech != DES_ECB && mech != DES_CBC) {
			throw new CryptoException("invalid encrypt mech parameter");
		}

		if (mech == DES_CBC && iv.length != 8) {
			throw new CryptoException("invalid encrypt intial vector");
		}

		switch (mech) {
		case DES_ECB:
			if (key.length == 8) {
				engine = new DESEngine();
			} else {
				engine = new DESedeEngine();

			}
			param = new KeyParameter(key);
			break;
		case DES_CBC:
			if (key.length == 8) {
				engine = new CBCBlockCipher(new DESEngine());
			} else {
				engine = new CBCBlockCipher(new DESedeEngine());

			}
			param = new ParametersWithIV(new KeyParameter(key), iv);
			break;
		}

		cipher = new BufferedBlockCipher(engine);
		cipher.init(true, param);

		byte[] out = new byte[dataToEncrypt.length];

		int len1 = cipher.processBytes(dataToEncrypt, 0, dataToEncrypt.length, out, 0);

		try {
			cipher.doFinal(out, len1);
		} catch (CryptoException e) {
			log.error(e.getMessage());
			throw new CryptoException("encrypt exception");
		}

		return out;
	}

	/*
	 * Performs an decryption operarion,using the key specified by the value parameter, the algorithm specified by the mech parameter.
	 * 
	 * @param key The key to use in decrypt operation.
	 * 
	 * @param mech DES_ECB or DES_CBC
	 * 
	 * @param dataToDecrypt Data to decrypt
	 * 
	 * @param iv Intial vector to use if provided and depending if required by the algorithm specified by the mech parameter
	 * 
	 * @return A byte array containing the decryptd data
	 * 
	 * @throws CryptoException
	 */
	public static byte[] decrypt(byte[] key, int mech, byte[] dataToDecrypt, byte[] iv) throws CryptoException {

		BlockCipher engine = null;
		CipherParameters param = null;
		BufferedBlockCipher cipher = null;
		byte[] out = null;

		if (key.length != 8 && key.length != 16 && key.length != 24) {
			throw new CryptoException("invalid decrypt key");
		}

		if (dataToDecrypt.length == 0 || dataToDecrypt.length % 8 != 0) {
			throw new CryptoException("invalid data length");
		}

		if (mech != DES_ECB && mech != DES_CBC) {
			throw new CryptoException("invalid decrypt mech parameter");
		}

		if (mech == DES_CBC && iv.length != 8) {
			throw new CryptoException("invalid decrypt intial vector");
		}

		switch (mech) {
		case DES_ECB:
			if (key.length == 8) {
				engine = new DESEngine();
			} else {
				engine = new DESedeEngine();

			}
			param = new KeyParameter(key);
			break;
		case DES_CBC:
			if (key.length == 8) {
				engine = new CBCBlockCipher(new DESEngine());
			} else {
				engine = new CBCBlockCipher(new DESedeEngine());

			}
			param = new ParametersWithIV(new KeyParameter(key), iv);
			break;
		}

		cipher = new BufferedBlockCipher(engine);
		cipher.init(false, param);

		out = new byte[dataToDecrypt.length];

		int len1 = cipher.processBytes(dataToDecrypt, 0, dataToDecrypt.length, out, 0);

		try {
			cipher.doFinal(out, len1);
		} catch (CryptoException e) {
			log.error(e.getMessage());
			throw new CryptoException("decrypt exception");
		}

		return out;
	}

	/**
	 * Performs a decryption operarion using the key specified by the decryptkey parameter, the algorithm specified by the decryptMech parameter, followed immediately by an encrypt operation using the key passed as encryptKey,the mechanism passed as encryptMech .
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
	 *            Intial vector to use for the decrypt operation if provided and depending if required by the algorthm specified by the mech parameter.
	 * @param encryptIV
	 * @return A new byte array containing the result of the decrypt operations on data.
	 * @throws CryptoException
	 */
	public static byte[] decryptEncrypt(byte[] decryptkey, int decryptMech, byte[] encryptkey, int encryptMech, byte[] data, byte[] decryptIV, byte[] encryptIV) throws CryptoException {
		byte[] decData = null;

		if (decryptkey.length != 8 && decryptkey.length != 16 && decryptkey.length != 24) {
			throw new CryptoException("invalid decrypt key");
		}

		if (encryptkey.length != 8 && encryptkey.length != 16 && encryptkey.length != 24) {
			throw new CryptoException("invalid encrypt key");
		}

		if (data.length == 0 || data.length % 8 != 0) {
			throw new CryptoException("invalid data length");
		}

		if (decryptMech != DES_ECB && decryptMech != DES_CBC) {
			throw new CryptoException("invalid decrypto mech parameter");
		}

		if (encryptMech != DES_ECB && encryptMech != DES_CBC) {
			throw new CryptoException("invalid encrypto mech parameter");
		}

		if (encryptMech == DES_CBC && encryptIV.length != 8) {
			throw new CryptoException("invalid encrypto intial vector");
		}

		if (decryptMech == DES_CBC && decryptIV.length != 8) {
			throw new CryptoException("invalid decrypto intial vector");
		}

		decData = decrypt(decryptkey, decryptMech, data, decryptIV);
		if (decData.length == 0) {
			throw new CryptoException("decrypto error");
		}

		decData = encrypt(encryptkey, encryptMech, decData, encryptIV);
		if (decData.length == 0) {
			throw new CryptoException("encrypto error");
		}

		return decData;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the singingKey parameter, and the algorithm specified by the signingMech parameter.
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
	public static byte[] DESsign(byte[] signingkey, int signingMech, byte[] data) throws CryptoException {
		byte[] out = new byte[8];
		KeyParameter key = null;
		BlockCipher cipher = null;
		Mac mac = null;

		// Full Triple DES MAC in GP Card Spec.
		if (signingkey.length != 16) {
			throw new CryptoException("invalid key");
		}

		if (data.length == 0) {
			throw new CryptoException("invalid data");
		}

		// padding the message according to ISO 7816-4
		// which is equivalent to method 2 of ISO/IEC 9797-1
		int paddedDataLen = data.length + 1;
		if ((data.length + 1) % 8 != 0) {
			paddedDataLen += 8 - (data.length + 1) % 8;
		}

		byte[] paddedData = new byte[paddedDataLen];
		System.arraycopy(data, 0, paddedData, 0, data.length);

		paddedData[data.length] = (byte) 0x80;

		// System.out.println(new String(Hex.encode(paddedData)));

		switch (signingMech) {
		case DES_MAC:
			key = new KeyParameter(signingkey);
			cipher = new DESedeEngine();
			mac = new CBCBlockCipherMac(cipher, 64);

			mac.init(key);
			mac.update(paddedData, 0, paddedData.length);
			mac.doFinal(out, 0);
			break;
		case DES_MAC_EMV:

			break;
		default:
			throw new CryptoException("unsupported mech");

		}

		return out;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the singingKey parameter, and the algorithm specified by the signingMech parameter.
	 * 
	 * @param privParameters
	 *            The RSA private key to use for signing operation
	 * @param data
	 *            Data to sign.
	 * @return A byte array containing the signature of data
	 * @throws CryptoException
	 */
	public static byte[] RSAsign(RSAPrivateCrtKeyParameters privParameters, byte[] data) throws CryptoException {

		int bitLen = 0;
		if (!(privParameters instanceof RSAPrivateCrtKeyParameters)) {
			throw new CryptoException("invalid paramter");
		}
		bitLen = privParameters.getModulus().bitLength();
		if (bitLen % 8 != 0 || bitLen < 512) {
			throw new CryptoException("invalid key");
		}

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
	 * Creates a signature of the data specified,using the key specified by the singingKey parameter, and the algorithm specified by the signingMech parameter.
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
	public static boolean DESverify(byte[] verifykey, int verifyMech, byte[] data, byte[] signature) throws CryptoException {
		byte[] out = new byte[8];

		if (verifykey.length != 8 && verifykey.length != 16 && verifykey.length != 24) {
			throw new CryptoException("invalid key");
		}

		if (data.length % 8 != 0 || data.length == 0) {
			throw new CryptoException("invalid data");
		}

		if (signature.length != 8) {
			throw new CryptoException("invalid signature");
		}

		switch (verifyMech) {
		case DES_MAC:
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

		if (!isEqualTo(out, signature)) {
			return false;
		}

		return true;
	}

	/**
	 * Creates a signature of the data specified,using the key specified by the singingKey parameter, and the algorithm specified by the signingMech parameter.
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
	public static boolean RSAverify(RSAKeyParameters pubParameters, byte[] data, byte[] signature) throws CryptoException {

		if (data.length == 0) {
			throw new CryptoException("invalid data");
		}

		if ((pubParameters.getModulus()).bitLength() % 8 != 0 || (pubParameters.getModulus()).bitLength() == 0) {
			throw new CryptoException("invalid public key");
		}

		if (signature.length == 0 || (signature.length != pubParameters.getModulus().bitLength() / 8)) {
			throw new CryptoException("invalid signature");
		}

		byte[] out = new byte[(pubParameters.getModulus()).bitLength() / 8];

		AsymmetricBlockCipher eng = new RSAEngine();

		eng.init(false, pubParameters);

		try {
			out = eng.processBlock(signature, 0, signature.length);
		} catch (Exception e) {
			throw new CryptoException("RSA verify exception");
		}
		// System.out.println("out=" + out);

		if (!isEqualTo(data, out)) {
			return false;
		}

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
	public static AsymmetricCipherKeyPair generateRSAKeyPair(int publicExponent, int strength) throws CryptoException {

		AsymmetricCipherKeyPair pair = null;
		AsymmetricBlockCipher eng = null;
		RSAKeyPairGenerator pGen = null;
		RSAKeyGenerationParameters genParam = null;

		if (publicExponent == 0) {
			throw new CryptoException("invalid publicExponent parameter");
		}

		if (strength < 512 || strength % 8 != 0) {
			throw new CryptoException("invalid strength parameter");
		}

		pGen = new RSAKeyPairGenerator();
		genParam = new RSAKeyGenerationParameters(BigInteger.valueOf(publicExponent), new SecureRandom(), strength, 25);

		pGen.init(genParam);
		pair = pGen.generateKeyPair();

		return pair;
	}

	/**
	 * Derives the key derivedKey using the masterKey specified,and the algorithm specified by the mech parameter. The data parameter is used to specify the deversification data to be used in the operation.
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
	public static byte[] deriveKey(byte[] masterKey, int mech, byte[] data) throws CryptoException {

		byte[] iv = new byte[8];
		byte[] drivedkey = null;

		if (mech != DES_CBC && mech != DES_ECB) {
			throw new CryptoException("invalid mech");
		}

		if (data.length != 16) {
			throw new CryptoException("invalid data");
		}

		if (masterKey.length != 8 && masterKey.length != 16) {
			throw new CryptoException("invalid key");
		}

		for (int i = 0; i < iv.length; i++) {
			iv[i] = 0;

		}
		drivedkey = encrypt(masterKey, mech, data, iv);

		return drivedkey;
	}

	// �Ѽ����ַ����80,Ȼ�����00��8�����ת��Ϊ byte[]
	public static byte[] getEncryptBlock(String encryptData) throws Exception {
		// ����8����Ĳ���0x00
		byte[] macBlock = Hex.decode(encryptData);
		int blockLength = macBlock.length + 1;
		while (blockLength % 8 != 0) {
			blockLength++;
		}
		byte[] bMacBlock = new byte[blockLength];
		System.arraycopy(macBlock, 0, bMacBlock, 0, macBlock.length);
		blockLength = macBlock.length;
		bMacBlock[blockLength++] = (byte) 0x80;
		while (blockLength < bMacBlock.length)
			bMacBlock[blockLength++] = 0x00;

		return bMacBlock;
	}

	/*
	 * ����16�����ַ���� 80 ��Ȼ����䵽16�ı���
	 */
	public static byte[] get16PaddedBlock(String encryptData) throws Exception {
		// ����8����Ĳ���0x00
		byte[] original = Hex.decode(encryptData);
		int blockLength = original.length;
		if (blockLength < 16) {
			blockLength++;
			while (blockLength % 16 != 0) {
				blockLength++;
			}
			byte[] padded = new byte[blockLength];
			System.arraycopy(original, 0, padded, 0, original.length);
			blockLength = original.length;

			padded[blockLength++] = (byte) 0x80;

			while (blockLength < padded.length)
				padded[blockLength++] = 0x00;
			return padded;
		}
		return original;
	}

	public static String singleDesFinalTripeDesMac(String key, String data, byte[] icv) throws Exception {

		byte[] signKey = RsaUtil.hex2byte(key);
		byte[] leftKey = RsaUtil.hex2byte(key.substring(0, 16));

		if (key.length() != 32) {
			throw new Exception("invalid key");
		}

		if (data.length() == 0) {
			throw new CryptoException("invalid data");
		}

		byte[] paddedData = getEncryptBlock(data);

		int blockNum = paddedData.length / 8;
		int currentNum = 0;

		byte[] blockData = new byte[8];
		byte[] initData = new byte[8];

		int j = 0;
		while (currentNum < blockNum - 1) {
			// ȡ�õ�ǰblock
			for (j = 0; j < 8; j++) {
				blockData[j] = paddedData[currentNum * 8 + j];
			}
			icv = encrypt(leftKey, DES_CBC, blockData, icv);
			currentNum++;
		}
		for (j = 0; j < 8; j++) {
			blockData[j] = paddedData[currentNum * 8 + j];
		}
		initData = encrypt(signKey, DES_CBC, blockData, icv);

		return RsaUtil.byte2hex(initData);
	}

	public static String fullTripeDesMac(String key, String data, byte[] icv) throws Exception {

		byte[] signKey = RsaUtil.hex2byte(key);

		if (key.length() != 32) {
			throw new Exception("invalid key");
		}

		if (data.length() == 0) {
			throw new CryptoException("invalid data");
		}

		byte[] paddedData = getEncryptBlock(data);

		int blockNum = paddedData.length / 8;
		int currentNum = 0;

		byte[] blockData = new byte[8];
		byte[] initData = new byte[8];

		int j = 0;
		while (currentNum < blockNum) {
			// ȡ�õ�ǰblock
			for (j = 0; j < 8; j++) {
				blockData[j] = paddedData[currentNum * 8 + j];
			}
			icv = encrypt(signKey, DES_CBC, blockData, icv);
			currentNum++;
		}

		return RsaUtil.byte2hex(icv);

	}

	public static String getHexLen(String str, int lenSize) {

		String len = "" + Integer.toString((str.length() / 2), 16);
		while (len.length() < lenSize)
			len = "0" + len;
		return len;

	}

	public static String intToHex(int num, int len) {
		String str = Integer.toString(num, 16);
		while (str.length() < len)
			str = "0" + str;
		return str;
	}

	public static String getKcv(String key) {

		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		String kcv = "";
		try {
			kcv = RsaUtil.byte2hex(encrypt(RsaUtil.hex2byte(key), DES_ECB, iv, iv));
			kcv = kcv.substring(0, 6);
			// kcv = kcv.substring(10);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
		return kcv;
	}

	public static String singleDESEncrypt(String key, String data) throws Exception {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		byte[] signKey = RsaUtil.hex2byte(key.substring(0, 16));
		if (signKey.length != 8) {
			throw new Exception("invalid key");
		}

		if (data.length() == 0) {
			throw new CryptoException("invalid data");
		}
		byte[] paddedData = CryptoUtil.getEncryptBlock(data);
		byte[] icv = CryptoUtil.encrypt(signKey, CryptoUtil.DES_CBC, paddedData, iv);
		return RsaUtil.byte2hex(icv).substring(0, 16);
	}

	public static void main(String[] args) throws Exception {
		// byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};

		String Key = "35d9c9ed20306e168c420fc2d0757440";
		String macData = "84e200001991040e82021c0094080801010018010200";
		String IV = "668DE4738B0B4B69";
		log.debug(CryptoUtil.singleDesFinalTripeDesMac(Key, macData, HexStr.hexToBuffer(IV)));
		log.debug(singleDESEncrypt(Key, IV));

	}
}
