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
 * EncryptedKey ::= OCTET STRING
 * </pre>
 *
 *
 */

public class EncryptedKey extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString key;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EncryptedKey(byte[] _key) {
		setKey(_key);
	}

	public EncryptedKey(ASN1OctetString _key) {
		key = _key;
	}

	public EncryptedKey(EncryptedKey _orig) {
		key = _orig.key;
	}

    /**
     * return an EncryptedKey object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EncryptedKey getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return an EncryptedKey object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EncryptedKey getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EncryptedKey) {
			return (EncryptedKey)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new EncryptedKey((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid EncryptedKey: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getKey() {
		return key.getOctets();
	}

	public DERObject getDERObject() {
		return key;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setKey(byte[] _key) {
		key = new BERConstructedOctetString(_key);
	}


}
