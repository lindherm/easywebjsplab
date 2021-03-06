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
 * SubjectKeyIdentifier ::= OCTET STRING
 * </pre>
 *
 *
 */

public class SubjectKeyIdentifier extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString id;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SubjectKeyIdentifier(byte[] _id) {
		setId(_id);
	}

	public SubjectKeyIdentifier(ASN1OctetString _id) {
		id = _id;
	}

	public SubjectKeyIdentifier(SubjectKeyIdentifier _orig) {
		id = _orig.id;
	}

    /**
     * return a SubjectKeyIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SubjectKeyIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return a SubjectKeyIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SubjectKeyIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SubjectKeyIdentifier) {
			return (SubjectKeyIdentifier)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new SubjectKeyIdentifier((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid SubjectKeyIdentifier: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getId() {
		return id.getOctets();
	}

	public DERObject getDERObject() {
		return id;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setId(byte[] _id) {
		id = new BERConstructedOctetString(_id);
	}

}
