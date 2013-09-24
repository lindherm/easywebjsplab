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
 * Digest ::= OCTET STRING
 * </pre>
 *
 *
 */

public class Digest extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString digest;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public Digest(byte[] _digest) {
		setDigest(_digest);
	}

	public Digest(ASN1OctetString _digest) {
		digest = _digest;
	}

	public Digest(Digest _orig) {
		digest = _orig.digest;
	}

    /**
     * return a Digest object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static Digest getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return a Digest object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Digest getInstance(Object _obj) {
		if(_obj == null || _obj instanceof Digest) {
			return (Digest)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new Digest((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid Digest: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getDigest() {
		return digest.getOctets();
	}

	public DERObject getDERObject() {
		return digest;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setDigest(byte[] _digest) {
		digest = new BERConstructedOctetString(_digest);
	}


}
