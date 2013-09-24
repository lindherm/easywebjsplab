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
 * SignatureValue ::= OCTET STRING
 * </pre>
 *
 *
 */

public class SignatureValue extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString value;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SignatureValue(byte[] _value) {
		setValue(_value);
	}

	public SignatureValue(ASN1OctetString _value) {
		value = _value;
	}

	public SignatureValue(SignatureValue _orig) {
		value = _orig.value;
	}

    /**
     * return an SignatureValue object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SignatureValue getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return a SignatureValue object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SignatureValue getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SignatureValue) {
			return (SignatureValue)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new SignatureValue((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid SignatureValue: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getValue() {
		return value.getOctets();
	}


	public DERObject getDERObject() {
		return value;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setValue(byte[] _value) {
		value = new BERConstructedOctetString(_value);
	}


}
