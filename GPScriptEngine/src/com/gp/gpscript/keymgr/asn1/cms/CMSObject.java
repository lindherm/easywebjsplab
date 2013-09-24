package com.gp.gpscript.keymgr.asn1.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gp.gpscript.keymgr.asn1.ASN1InputStream;
import com.gp.gpscript.keymgr.asn1.ASN1OutputStream;
import com.gp.gpscript.keymgr.asn1.BERInputStream;
import com.gp.gpscript.keymgr.asn1.BEROutputStream;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERInputStream;
import com.gp.gpscript.keymgr.asn1.DEROutputStream;

public abstract class CMSObject implements DEREncodable {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	protected static final int BER = 0;
	protected static final int DER = 1;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */


	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public boolean validate() {
		return true;
	}

	public byte[] getEncoded()
		throws IOException {

		ByteArrayOutputStream _baos = new ByteArrayOutputStream();
		ASN1OutputStream      _aos  = getOutputStream(_baos);

		_aos.writeObject(this);
		_aos.close();
		_baos.close();
		return _baos.toByteArray();
	}

	public ASN1OutputStream getOutputStream(OutputStream _out)
		throws IOException {

		switch(getASN1Type()) {
			case BER:
				return new BEROutputStream(_out);
			case DER:
				return new DEROutputStream(_out);
			default :
				throw new IOException("Invalid encoding");
		}
	}

	public static ASN1InputStream getInputStream(InputStream _in)
		throws IOException {

		switch(getASN1Type()) {
			case BER:
				return (ASN1InputStream)(new BERInputStream(_in));
			case DER:
				return (ASN1InputStream)(new DERInputStream(_in));
			default :
				throw new IOException("Invalid encoding");
		}
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	protected static int getASN1Type() {
		return BER;
	}

}
