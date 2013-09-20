package com.gp.gpscript.engine;

import com.gp.gpscript.script.GPKeyCryptoEngine;
import com.gp.gpscript.script.NativeByteString;
import com.gp.gpscript.script.NativeKey;

public class CryptoEngineForKmsKey implements GPKeyCryptoEngine {

	@Override
	public int init(String p1, String p2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int init(String p1, String p2, String p3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public NativeByteString macConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString decryptConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString encryptConnByPan(NativeByteString p1, Number p2, NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString deriveKeyConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5, NativeKey p6) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString wrapKeyConnByPan(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4, String p5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString decryptEncryptConnByPan(NativeKey p1, Number p2, NativeByteString p3, Number p4, NativeByteString p5, NativeByteString p6, NativeByteString p7, String p8) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString connectByPan(String p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString disconnByPan(String p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString deriveKeyLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, NativeByteString p6) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString decryptEncryptLmk(Number p1, Number p2, Number p3, NativeByteString p4, NativeByteString p5, Number p6, Number p7, Number p8, NativeByteString p9, NativeByteString p10, NativeByteString p11) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString wrapToLmk(Number p1, Number p2, Number p3, NativeByteString p4, Number p5, Number p6, NativeByteString p7, NativeByteString p8) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString edk(Number p1, NativeByteString p2, NativeByteString p3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString inputKey(NativeByteString p1, NativeByteString p2, NativeByteString p3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString getKey(NativeKey p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString decrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString decryptEncrypt(NativeKey p1, Number p2, NativeKey p3, Number p4, NativeByteString p5, NativeByteString p6, NativeByteString p7) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deriveKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deriveOddKey(NativeKey p1, Number p2, NativeByteString p3, NativeKey p4) {
		// TODO Auto-generated method stub

	}

	@Override
	public NativeByteString digest(Number p1, NativeByteString p2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString encrypt(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateKey(Number p1, NativeKey p2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateKeyPair(Number p1, NativeKey p2, NativeKey p3) {
		// TODO Auto-generated method stub

	}

	@Override
	public NativeByteString generateRandom(Number p1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeByteString sign(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verify(NativeKey p1, Number p2, NativeByteString p3, NativeByteString p4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void wrap(NativeKey p1, Number p2, NativeKey p3, NativeKey p4, NativeByteString p5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unwrap(Number p1, NativeKey p2, NativeKey p3, NativeByteString p4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unwrapWrap(Number p1, NativeKey p2, Number p3, NativeKey p4, NativeKey p5, NativeByteString p6, NativeByteString p7) {
		// TODO Auto-generated method stub

	}

}
