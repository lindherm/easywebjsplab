package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * SignerIdentifier ::= CHOICE {
 * 	issuerAndSerialNumber IssuerAndSerialNumber,
 * 	subjectKeyIdentifier [0] SubjectKeyIdentifier
 * }
 * </pre>
 *
 *
 */

public class SignerIdentifier extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DEREncodable id;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SignerIdentifier(IssuerAndSerialNumber _id) {
		setId(_id);
	}

	public SignerIdentifier(SubjectKeyIdentifier _id) {
		setId(_id);
	}


	public SignerIdentifier(DERObject _id){

		if(_id instanceof ASN1TaggedObject) {
			id = SubjectKeyIdentifier.getInstance((ASN1TaggedObject)_id, false);
		}
		else {
			id = IssuerAndSerialNumber.getInstance(_id);
		}
	}

	public SignerIdentifier(SignerIdentifier _orig) {
		id = _orig.id;
	}

    /**
     * return a SignerIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SignerIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato);
	}

    /**
     * return a SignerIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SignerIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SignerIdentifier) {
			return (SignerIdentifier)_obj;
		}

		if(_obj instanceof IssuerAndSerialNumber) {
			return new SignerIdentifier((IssuerAndSerialNumber)_obj);
		}

		if(_obj instanceof SubjectKeyIdentifier) {
			return new SignerIdentifier((SubjectKeyIdentifier)_obj);
		}

		if(_obj instanceof DERObject) {
			return new SignerIdentifier((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid SignerIdentifier: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getId() {
		return id;
	}

	public DERObject getDERObject() {
		if(id instanceof SubjectKeyIdentifier) {
			return new BERTaggedObject(false, 0, id.getDERObject());
		}
		else {
			return id.getDERObject();
		}
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setId(IssuerAndSerialNumber _id) {
		id = _id;
	}

	private void setId(SubjectKeyIdentifier _id) {
		id = _id;
	}

}
