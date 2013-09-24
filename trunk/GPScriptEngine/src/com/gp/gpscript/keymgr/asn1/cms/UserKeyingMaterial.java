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
 * UserKeyingMaterial ::= OCTET STRING
 * </pre>
 *
 *
 */

public class UserKeyingMaterial extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString material;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public UserKeyingMaterial(byte[] _material) {
		setMaterial(_material);
	}

	public UserKeyingMaterial(ASN1OctetString _material) {
		material = _material;
	}

	public UserKeyingMaterial(UserKeyingMaterial _orig) {
		material = _orig.material;
	}

    /**
     * return a UserKeyingMaterial object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static UserKeyingMaterial getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return an UserKeyingMaterial object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static UserKeyingMaterial getInstance(Object _obj) {
		if(_obj == null || _obj instanceof UserKeyingMaterial) {
			return (UserKeyingMaterial)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new UserKeyingMaterial((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid UserKeyingMaterial: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getMaterial() {
		return material.getOctets();
	}

	public DERObject getDERObject() {
		return material;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setMaterial(byte[] _material) {
		material = new BERConstructedOctetString(_material);
	}
}
