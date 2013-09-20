package com.gp.gpscript.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * <p>
 * Title: RSA ������
 * </p>
 */

public class RsaUtil {
	// Ĭ�ϵ���ָ��
	private static final String sPublicExponent = "010001";
	// ��Կ����
	private static final int KEY_SIZE = 1024;

	/**
	 * �����Կ��
	 * 
	 * @return KeyPair
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.genKeyPair();
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * ��ɹ�Կ
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @return RSAPublicKey
	 * @throws EncryptException
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * ��ɹ�Կ
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @return RSAPublicKey
	 * @throws EncryptException
	 */

	public static RSAPublicKey generateRSAPublicKey(String modulus, String publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus, 16), new BigInteger(publicExponent, 16));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * ���˽Կ
	 * 
	 * @param modulus
	 * @param privateExponent
	 * @return RSAPrivateKey
	 * @throws EncryptException
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * ���˽Կ
	 * 
	 * @param modulus
	 * @param privateExponent
	 * @return RSAPrivateKey
	 * @throws EncryptException
	 */
	public static RSAPrivateKey generateRSAPrivateKey(String modulus, String privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus, 16), new BigInteger(privateExponent, 16));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * ����
	 * 
	 * @param key
	 *            ���ܵ���Կ
	 * @param data
	 *            ����ܵ��������
	 * @return ���ܺ�����
	 * @throws EncryptException
	 */
	public static byte[] encrypt(Key key, byte[] data) throws Exception {
		try {
			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int blockSize = cipher.getBlockSize(); // ��ü��ܿ��С���磺����ǰ���Ϊ128��byte����key_size=1024 ���ܿ��СΪ127 byte,���ܺ�Ϊ128��byte;��˹���2����ܿ飬��һ��127 byte�ڶ���Ϊ1��byte
			int outputSize = cipher.getOutputSize(data.length); // ��ü��ܿ���ܺ���С
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				}
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * ����
	 * 
	 * @param key
	 *            ���ܵ���Կ
	 * @param raw
	 *            �Ѿ����ܵ����
	 * @return ���ܺ������
	 * @throws EncryptException
	 */
	public static byte[] decrypt(Key key, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * ����ǩ��
	 * 
	 * @param key
	 *            ˽Կ
	 * @param srcInfo
	 *            ��ǩ����Ϣ
	 */
	public static String Sign(RSAPrivateKey key, String srcInfo) throws Exception {
		Signature sign = Signature.getInstance("SHA1withRSA");
		sign.initSign(key);
		sign.update(hex2byte(srcInfo));
		return byte2hex(sign.sign());
	}

	public static String Sign(RSAPrivateKey key, byte[] srcInfo) throws Exception {
		Signature sign = Signature.getInstance("SHA1withRSA");
		sign.initSign(key);
		sign.update(srcInfo);
		return byte2hex(sign.sign());
	}

	/**
	 * ��֤ǩ��
	 * 
	 * @param publicKey
	 *            ��Կ�ַ�
	 * @param srcInfo
	 *            ��ǩ����Ϣ
	 * @param signInfo
	 *            ǩ��
	 */
	public static boolean VSign(RSAPublicKey key, String srcInfo, String signInfo) throws Exception {

		Signature sign = Signature.getInstance("SHA1withRSA");
		sign.initVerify(key);
		sign.update(hex2byte(srcInfo));
		return sign.verify(hex2byte(signInfo));
	}

	/**
	 * ������ת�ַ�
	 */

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * �ַ�ת������
	 */
	public static byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	/*******************************************************************************
	 * дϵ�л�����
	 */
	public static void RWobject(String filename, Object obj) throws Exception {
		java.io.FileOutputStream outfile = new java.io.FileOutputStream(filename);
		java.io.ObjectOutputStream outobj = new java.io.ObjectOutputStream(outfile);
		outobj.writeObject(obj);
		outobj.flush();
	}

	/*******************************************************************************
	 * ��ϵ�л�����
	 */
	public static Object RWobject(String filename) throws Exception {
		java.io.FileInputStream infile = new java.io.FileInputStream(filename);
		java.io.ObjectInputStream inobj = new java.io.ObjectInputStream(infile);
		Object obj = inobj.readObject();
		return obj;
	}

	/**
	 * ���Գ���
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

	}
}
