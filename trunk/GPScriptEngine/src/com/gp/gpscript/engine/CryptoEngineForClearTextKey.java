package com.gp.gpscript.engine;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;

import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.script.GPConstant;
import com.gp.gpscript.script.GPKeyCryptoEngine;
import com.gp.gpscript.script.NativeByteString;
import com.gp.gpscript.script.NativeKey;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDByteUtil;

public class CryptoEngineForClearTextKey implements GPKeyCryptoEngine {
	private Logger log = Logger.getLogger(getClass().getName());

	public int init(String string, String string1) {
		return 0;
	}

	public int init(String string, String string1, String string2) {
		return 0;
	}

	public void close() {
	}
	
	public NativeByteString macConnByPan(NativeKey p1, Number p2,
			NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	public NativeByteString decryptConnByPan(NativeKey p1, Number p2,
			NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	public NativeByteString decryptEncryptConnByPan(NativeKey p1, Number p2,
			NativeByteString p3, Number p4, NativeByteString p5,
			NativeByteString p6, NativeByteString p7, String p8) {
		// TODO Auto-generated method stub
		return null;
	}

	public NativeByteString wrapKeyConnByPan(NativeKey p1, Number p2,
			NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}


	public NativeByteString deriveKeyConnByPan(NativeKey p1, Number p2,
			NativeByteString p3, NativeByteString p4, String p5,NativeKey p6) {
		// TODO Auto-generated method stub
		return null;
	}
	public NativeByteString connectByPan(String p1) {
		// TODO Auto-generated method stub
		return null;
	}


	public NativeByteString encryptConnByPan(NativeByteString p1, Number p2,
			NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public NativeByteString disconnByPan(String p1) {
		// TODO Auto-generated method stub
		return null;
	}
	public NativeByteString deriveKeyLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, NativeByteString p6) {
		return new NativeByteString("deriveKeyLmk", new Integer(GPConstant.HEX));
	}

	public NativeByteString decryptEncryptLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, Number p6, Number p7, Number p8, NativeByteString p9, NativeByteString p10, NativeByteString p11) {
		return new NativeByteString("decryptEncryptLmk", new Integer(GPConstant.HEX));
	}

	public NativeByteString wrapToLmk(Number p1, Number p2, Number p3, NativeByteString p4, Number p5, Number p6, NativeByteString p7, NativeByteString p8) {
		return new NativeByteString("wrapToLmk", new Integer(GPConstant.HEX));
	}

	public NativeByteString edk(Number p1, NativeByteString p2, NativeByteString p3) {
		return new NativeByteString("edk", new Integer(GPConstant.HEX));
	}

	public NativeByteString inputKey(NativeByteString p1, NativeByteString p2, NativeByteString p3) {
		return new NativeByteString("inputKey", new Integer(GPConstant.HEX));
	}

	public NativeByteString getKey(NativeKey p1) {
		return new NativeByteString("getKey", new Integer(GPConstant.HEX));
	}

	/**
	 * Decrypt Perform a decryption operation
	 * 
	 * @param p1
	 *            the key used for decrypt
	 * @param p2
	 *            mech-DES_ECB/DES_CBC/RSA (RSA is not supplied now)
	 * @param p3
	 *            data to encrypt
	 * @param p4
	 *            Initial vector
	 * @return a new ByteString of the decrypted data
	 */
	public NativeByteString decrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		NativeByteString bstrKey = p1.getBlob();
		byte[] key = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			key[i] = bstrKey.ByteAt(i);
		int mech = (int) p2.intValue();
		byte[] dataToDecrypt = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			dataToDecrypt[i] = p3.ByteAt(i);
		byte[] iv = new byte[p4.GetLength()];
		for (int i = 0; i < p4.GetLength(); i++)
			iv[i] = p4.ByteAt(i);

		String out = null;
			out =WD3DesCryptoUtil.ecb_decrypt(WDByteUtil.bytes2HEX(key), WDByteUtil.bytes2HEX(dataToDecrypt), Padding.NoPadding);

		// return
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(out, ee);
		return sNew;
	}

	/**
	 * decryptEncrypt Perform a decryption and encryption operation
	 * 
	 * @param p1
	 *            the key used for decrypt
	 * @param p2
	 *            decrypt mech--DES_ECB/DES_CBC/RSA (RSA is not supplied now)
	 * @param p3
	 *            the key used for encrypt
	 * @param p4
	 *            decrypt mech--DES_ECB/DES_CBC/RSA (RSA is not supplied now)
	 * @param p5
	 *            data to de/encrypt
	 * @param p6
	 *            Initial vector for decrypt
	 * @param p7
	 *            Initial vector for encrypt
	 * @return a new ByteString of the result of decrypted and encrypted data
	 */
	public NativeByteString decryptEncrypt(NativeKey p1, Number p2, NativeKey p3, Number p4, NativeByteString p5, NativeByteString p6, NativeByteString p7) {
		NativeByteString bstrKey = p1.getBlob();
		byte[] decrytKey = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			decrytKey[i] = bstrKey.ByteAt(i);
		int decryptMech = (int) p2.intValue();
		bstrKey = p3.getBlob();
		byte[] encrytKey = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			encrytKey[i] = bstrKey.ByteAt(i);
		int encryptMech = (int) p4.intValue();
		byte[] data = new byte[p5.GetLength()];
		for (int i = 0; i < p5.GetLength(); i++)
			data[i] = p5.ByteAt(i);
		byte[] decryptIv = new byte[p6.GetLength()];
		for (int i = 0; i < p6.GetLength(); i++)
			decryptIv[i] = p6.ByteAt(i);
		byte[] encryptIv = new byte[p7.GetLength()];
		for (int i = 0; i < p7.GetLength(); i++)
			encryptIv[i] = p7.ByteAt(i);

		byte[] out = null;
		/*try {
			//out = Crypto.decryptEncrypt(decrytKey, decryptMech, encrytKey, encryptMech, data, decryptIv, encryptIv);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/

		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * derive the key using the data and master key
	 * 
	 * @param p1
	 *            masterkey
	 * @param p2
	 *            mech=DES_ECB/DES_CBC
	 * @param p3
	 *            data for derive
	 * @param p4
	 *            derivedKey
	 */
	public void deriveKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		NativeByteString bstrMasterKey = p1.getBlob();
		byte[] masterKey = new byte[bstrMasterKey.GetLength()];
		for (int i = 0; i < bstrMasterKey.GetLength(); i++)
			masterKey[i] = bstrMasterKey.ByteAt(i);
		int mech = (int) p2.intValue();
		byte[] data = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			data[i] = p3.ByteAt(i);

		byte[] out = null;
		/*try {
			//out = Crypto.deriveKey(masterKey, mech, data);
			String keyToDerive = new String(Hex.encode(out));
			p4.strBlob = keyToDerive;
			// not implemented GP_Global.setKey(p4.strIndex,keyToDerive);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/

	}

	public void deriveOddKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		NativeByteString bstrMasterKey = p1.getBlob();
		byte[] masterKey = new byte[bstrMasterKey.GetLength()];
		for (int i = 0; i < bstrMasterKey.GetLength(); i++)
			masterKey[i] = bstrMasterKey.ByteAt(i);
		int mech = (int) p2.intValue();
		byte[] data = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			data[i] = p3.ByteAt(i);

		byte[] out = null;
		/*try {
			out = Crypto.deriveKey(masterKey, mech, data);
			String keyToDerive = new String(Hex.encode(out));
			log.debug("before odd adjust :" + keyToDerive);
			keyToDerive = Hex.getOddString(keyToDerive);
			p4.strBlob = keyToDerive;
			// not implemented GP_Global.setKey(p4.strIndex,keyToDerive);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/
	}

	/**
	 * create a digest of the data,using the algorithm specified by the mech parameter
	 * 
	 * @param p1
	 *            mech=SHA-1/MD5
	 * @param p2
	 *            data
	 * @return a ByteString containing the digest of data
	 */
	public NativeByteString digest(Number p1, NativeByteString p2) {
		int digestMech = (int) p1.intValue();
		byte[] data = new byte[p2.GetLength()];
		for (int i = 0; i < p2.GetLength(); i++)
			data[i] = p2.ByteAt(i);

		byte[] out = null;
		/*try {
			out = Crypto.digest(digestMech, data);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}
*/
		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * encrypt
	 * 
	 * @param p1
	 *            key to use in the encrypt option
	 * @param p2
	 *            mech=DES_ECB/DES_CBC/RSA
	 * @param p3
	 *            data to encrypt
	 * @param p4
	 *            initial vector
	 * @return a ByteString object containing the encrypted object
	 */
	public NativeByteString encrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		NativeByteString bstrKey = p1.getBlob();
		byte[] key = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			key[i] = bstrKey.ByteAt(i);

		int mech = (int) p2.intValue();

		byte[] dataToEncrypt = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			dataToEncrypt[i] = p3.ByteAt(i);

		byte[] iv = new byte[p4.GetLength()];
		for (int i = 0; i < p4.GetLength(); i++)
			iv[i] = p4.ByteAt(i);

		byte[] out = null;
		/*try {
			out = Crypto.encrypt(key, mech, dataToEncrypt, iv);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/

		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * generateKey create a 8/16 bytes key
	 * 
	 * @param p1
	 *            mech=DES_KEY_GEN(8 bytes)/DES2_KEY_GEN(16 bytes)
	 * @param p2
	 *            the key object containing the created key
	 */
	public void generateKey(Number p1, NativeKey p2) {
		int mech = (int) p1.intValue();

		KeyParameter out;
		byte[] keyToGen = null;
		/*try {
			out = Crypto.generatorKey(mech);
			keyToGen = out.getKey();
			String strKeyToDerive = new String(Hex.encode(keyToGen));
			p2.strBlob = strKeyToDerive;
			// not implemented GP_Global.setKey(p2.strIndex,strKeyToDerive);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/
	}

	/**
	 * generateKeyPair create a public/private key pair need update the database
	 * 
	 * @param p1
	 *            mech=RSA_KEY_PAIR_GEN
	 * @param p2
	 *            public key
	 * @param p3
	 *            private key
	 */
	public void generateKeyPair(Number p1, NativeKey p2, NativeKey p3) {
		int mech = (int) p1.intValue();
		// call syp
		AsymmetricCipherKeyPair pair;
		CipherParameters publicParam;
		CipherParameters privateParam;
		byte[] publicKey = null;
		byte[] privateKey = null;
		/*try {
			pair = Crypto.generateRSAKeyPair(3, 1024);
			String strModulus = new String(Hex.encode((((RSAKeyParameters) pair.getPublic()).getModulus()).toByteArray()));
			String strExponent = new String(Hex.encode((((RSAKeyParameters) pair.getPublic()).getExponent()).toByteArray()));
			String strP = new String(Hex.encode((((RSAPrivateCrtKeyParameters) pair.getPrivate()).getP()).toByteArray()));
			String strQ = new String(Hex.encode((((RSAPrivateCrtKeyParameters) pair.getPrivate()).getQ()).toByteArray()));
			String strDP1 = new String(Hex.encode((((RSAPrivateCrtKeyParameters) pair.getPrivate()).getDP()).toByteArray()));
			String strDQ1 = new String(Hex.encode((((RSAPrivateCrtKeyParameters) pair.getPrivate()).getDQ()).toByteArray()));
			String strPQ = new String(Hex.encode((((RSAPrivateCrtKeyParameters) pair.getPrivate()).getQInv()).toByteArray()));
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/
	}

	/**
	 * generateRandom
	 * 
	 * @param p1
	 *            the length of the random data
	 * @return a bytestring with length bytes of random data
	 */
	public NativeByteString generateRandom(Number p1) {
		int rdmLength = (int) p1.intValue();

		byte[] out = null;
		/*try {
			out = Crypto.generaterandom(rdmLength);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}
*/
		// return
		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * sign
	 * 
	 * @param p1
	 *            signing key
	 * @param p2
	 *            signing mech=DES_MAC(3DES)/RSA
	 * @param p3
	 *            data
	 * @return the signatrue of data
	 */
	public NativeByteString sign(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		NativeByteString bstrKey = p1.getBlob();
		byte[] signingkey = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			signingkey[i] = bstrKey.ByteAt(i);
		int signingMech = (int) p2.intValue();
		byte[] data = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			data[i] = p3.ByteAt(i);
		byte[] iv = new byte[p4.GetLength()];
		for (int i = 0; i < p4.GetLength(); i++)
			iv[i] = p4.ByteAt(i);

		byte[] out = null;
		/*if ((signingMech == Crypto.DES_MAC) || (signingMech == Crypto.DES_MAC_EMV)) {
			try {
				//out = Crypto.DESsign(signingkey, signingMech, data, iv);
			} catch (CryptoException e) {
				e.printStackTrace();
				throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
			}
		} else // RSA
		{
			try {
				// get component of the RSA
				BigInteger mod = new BigInteger(p1.getComponent(p1.MODULUS).toString(), 16);
				BigInteger pubExp = new BigInteger("11", 16);
				BigInteger privExp = new BigInteger(p1.getComponent(p1.EXPONENT).toString(), 16);
				BigInteger p = new BigInteger(p1.getComponent(p1.CRT_P).toString(), 16);
				BigInteger q = new BigInteger(p1.getComponent(p1.CRT_Q).toString(), 16);
				BigInteger pExp = new BigInteger(p1.getComponent(p1.CRT_DP1).toString(), 16);
				BigInteger qExp = new BigInteger(p1.getComponent(p1.CRT_DQ1).toString(), 16);
				BigInteger crtCoef = new BigInteger(p1.getComponent(p1.CRT_PQ).toString(), 16);

				RSAPrivateCrtKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);
				//out = Crypto.RSAsign(privParameters, data);
			} catch (CryptoException e) {
				e.printStackTrace();
				throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
			}
		}
		// return
*/		String str = new String(Hex.encode(out));
		Integer ee = new Integer(GPConstant.HEX);
		NativeByteString sNew = new NativeByteString(str, ee);
		return sNew;
	}

	/**
	 * unwrap
	 * 
	 * @param p1
	 *            unWrap mech=DES_ECB/DES_CBC/RSA
	 * @param p2
	 *            keyToUnwrap
	 * @param p3
	 *            key result
	 * @param p4
	 *            initial vector
	 * @return
	 */
	public boolean verify(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		NativeByteString bstrKey = p1.getBlob();
		byte[] verifykey = new byte[bstrKey.GetLength()];
		for (int i = 0; i < bstrKey.GetLength(); i++)
			verifykey[i] = bstrKey.ByteAt(i);
		int verifyMech = (int) p2.intValue();
		byte[] data = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			data[i] = p3.ByteAt(i);
		byte[] signature = new byte[p3.GetLength()];
		for (int i = 0; i < p3.GetLength(); i++)
			signature[i] = p3.ByteAt(i);

		boolean out = false;
		/*if ((verifyMech == Crypto.DES_MAC) || (verifyMech == Crypto.DES3_MAC) || (verifyMech == Crypto.DES3_MAC_EMV)) {
			try {
				out = Crypto.DESverify(verifykey, verifyMech, data, signature);
			} catch (CryptoException e) {
				e.printStackTrace();
				throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
			}
		} else // RSA
		{
			try {
				// get component of the RSA
				BigInteger mod = new BigInteger(p1.getComponent(p1.MODULUS).toString(), 16);
				BigInteger pubExp = new BigInteger("3", 16);
				RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);
				out = Crypto.RSAverify(pubParameters, data, signature);
			} catch (CryptoException e) {
				e.printStackTrace();
				throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
			}
		}*/
		// return
		return out;
	}

	/**
	 * wrap to encrypt a key
	 * 
	 * @param p1
	 *            wrapKey
	 * @param p2
	 *            wrapMech
	 * @param p3
	 *            keyToWrap
	 * @param p4
	 *            keyResult
	 * @param p5
	 *            initial vector
	 */
	public void wrap(NativeKey p1, Number p2, NativeKey p3, NativeKey p4, NativeByteString p5) {
		NativeByteString bwrapKey = p1.getBlob();
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);
		int wrapMech = (int) p2.intValue();
		NativeByteString bkeyToWrap = p3.getBlob();
		byte[] keyToWrap = new byte[bkeyToWrap.GetLength()];
		for (int i = 0; i < bkeyToWrap.GetLength(); i++)
			keyToWrap[i] = bkeyToWrap.ByteAt(i);
		byte[] iv = new byte[p5.GetLength()];
		for (int i = 0; i < p5.GetLength(); i++)
			iv[i] = p5.ByteAt(i);

		byte[] out = null;
	/*	try {
			out = Crypto.encrypt(wrapKey, wrapMech, keyToWrap, iv);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}
*/
		// return
		String strKeyResult = new String(Hex.encode(out));
		p4.strBlob = strKeyResult;
		// not implemented GP_Global.setKey(p4.strIndex,strKeyResult);
	}

	/**
	 * unwrap to decrypt a key using the key returned by getWrapKey() method
	 * 
	 * @param p1
	 *            unwrapMech
	 * @param p2
	 *            keyToUnwrap
	 * @param p3
	 *            keyResult
	 * @param p4
	 *            initial vector
	 */
	public void unwrap(Number p1, NativeKey p2, NativeKey p3, NativeByteString p4) {
		int unwrapMech = (int) p1.intValue();

		NativeByteString bKeyToUnwrap = p2.getBlob();
		byte[] keyToUnwrap = new byte[bKeyToUnwrap.GetLength()];
		for (int i = 0; i < bKeyToUnwrap.GetLength(); i++)
			keyToUnwrap[i] = bKeyToUnwrap.ByteAt(i);

		byte[] iv = new byte[p4.GetLength()];
		for (int i = 0; i < p4.GetLength(); i++)
			iv[i] = p4.ByteAt(i);

		NativeKey unwrapKey1 = new NativeKey("");
		p2.getWrapKey(unwrapKey1); // get the unwrapKey
		NativeByteString bunwrapKey = unwrapKey1.getBlob();
		byte[] unwrapKey = new byte[bunwrapKey.GetLength()];
		for (int i = 0; i < bunwrapKey.GetLength(); i++)
			unwrapKey[i] = bunwrapKey.ByteAt(i);

		// call syp
		byte[] out = null;
		/*try {
			out = Crypto.decrypt(unwrapKey, unwrapMech, keyToUnwrap, iv);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}
*/
		// return
		String strKeyResult = new String(Hex.encode(out));
		p3.strBlob = strKeyResult;
		// not implemented GP_Global.setKey(p3.strIndex,strKeyResult);
	}

	/**
	 * unwrapWrap decrypt keyToUnwrap using the key returned by getWrapKey() method and wrap the result by wrapKey
	 * 
	 * @param p1
	 *            unWrapMech
	 * @param p2
	 *            wrapKey
	 * @param p3
	 *            wrapMech
	 * @param p4
	 *            keyToUnwrap
	 * @param p5
	 *            keyResult
	 * @param p6
	 *            unwrapIV
	 * @param p7
	 *            wrapIV
	 */
	public void unwrapWrap(Number p1, NativeKey p2, Number p3, NativeKey p4, NativeKey p5, NativeByteString p6, NativeByteString p7) {
		int unwrapMech = (int) p1.intValue();
		int wrapMech = (int) p3.intValue();

		NativeByteString bKeyToUnwrap = p4.getBlob();
		byte[] keyToUnwrap = new byte[bKeyToUnwrap.GetLength()];
		for (int i = 0; i < bKeyToUnwrap.GetLength(); i++)
			keyToUnwrap[i] = bKeyToUnwrap.ByteAt(i);

		NativeByteString bwrapKey = p4.getBlob();
		byte[] wrapKey = new byte[bwrapKey.GetLength()];
		for (int i = 0; i < bwrapKey.GetLength(); i++)
			wrapKey[i] = bwrapKey.ByteAt(i);

		byte[] unwrapIV = new byte[p6.GetLength()];
		for (int i = 0; i < p6.GetLength(); i++)
			unwrapIV[i] = p6.ByteAt(i);
		byte[] wrapIV = new byte[p7.GetLength()];
		for (int i = 0; i < p7.GetLength(); i++)
			wrapIV[i] = p7.ByteAt(i);

		// get the unwrapKey
		NativeKey unwrapKey1 = new NativeKey("");
		p4.getWrapKey(unwrapKey1);
		NativeByteString bunwrapKey = unwrapKey1.getBlob();
		byte[] unwrapKey = new byte[bunwrapKey.GetLength()];
		for (int i = 0; i < bunwrapKey.GetLength(); i++)
			unwrapKey[i] = bunwrapKey.ByteAt(i);

		// decrypt
		byte[] out = null;
		/*try {
			out = Crypto.decrypt(unwrapKey, unwrapMech, keyToUnwrap, unwrapIV);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/
		// encrypt
		byte[] out1 = null;
		/*try {
			out1 = Crypto.encrypt(wrapKey, wrapMech, out, wrapIV);
		} catch (CryptoException e) {
			e.printStackTrace();
			throw new EvaluatorException((new GPError("Crypto", 0, 0, e.getMessage())).toString());
		}*/

		// return
		String strKeyResult = new String(Hex.encode(out1));
		p5.strBlob = strKeyResult;
		// not implemented GP_Global.setKey(p5.strIndex,strKeyResult);
	}

	
	

	

	
	

}