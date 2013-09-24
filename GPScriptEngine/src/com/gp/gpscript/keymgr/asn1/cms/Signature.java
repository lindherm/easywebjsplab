package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERBitString;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 *
 * Signature ::= BIT STRING
 * </pre>
 *
 *
 */

public class Signature extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERBitString bitString;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public Signature(byte[] _bitString) {
		setBitString(_bitString);
	}

	public Signature(DERBitString _bitString) {
		bitString =_bitString;
	}

	public Signature(Signature _orig) {
		bitString =_orig.bitString;
	}

    /**
     * return a Signature object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static Signature getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(DERBitString.getInstance(_ato, _explicit));
	}

    /**
     * return a Signature object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Signature getInstance(Object _obj) {
		if(_obj == null || _obj instanceof Signature) {
			return (Signature)_obj;
		}

		if(_obj instanceof DERBitString) {
			return new Signature((DERBitString)_obj);
		}

		throw new IllegalArgumentException("Invalid Signature: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getBitString() {
		return bitString.getBytes();
	}

	public DERObject getDERObject() {
		return bitString;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setBitString(byte[] _bitString) {
		bitString = new DERBitString(_bitString);
	}



}
