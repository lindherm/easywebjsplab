package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedOctetString;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * IV ::= OCTET STRING  -- exactly 8 octets
 * </pre>
 *
 *
 */

public class IV extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString iv;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public IV(byte[] _iv) {
		setIV(_iv);
	}

	public IV(ASN1OctetString _iv) {
		iv = _iv;
	}

	public IV(IV _orig) {
		iv = _orig.iv;
	}


    /**
     * return an IV object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static IV getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return an IV object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static IV getInstance(Object _obj) {
		if(_obj == null || _obj instanceof IV) {
			return (IV)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new IV((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid IV: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getIV() {
		return iv.getOctets();
	}

	public DERObject getDERObject() {
		return iv;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setIV(byte[] _iv) {
		iv = new BERConstructedOctetString(_iv);
	}


}
