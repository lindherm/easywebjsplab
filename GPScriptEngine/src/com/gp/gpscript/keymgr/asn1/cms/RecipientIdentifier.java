package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientIdentifier ::= CHOICE {
 * 	issuerAndSerialNumber IssuerAndSerialNumber,
 * 	subjectKeyIdentifier [0] SubjectKeyIdentifier
 * }
 * </pre>
 *
 *
 */

public class RecipientIdentifier extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	public DEREncodable id;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientIdentifier(IssuerAndSerialNumber _id) {
		setId(_id);
	}

	public RecipientIdentifier(com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier _id) {
		setId(_id);
	}

	public RecipientIdentifier(DERObject _obj) {

		if(_obj instanceof ASN1TaggedObject) {
			id = com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier.getInstance((ASN1TaggedObject)_obj, false);
		}
		else {
			id = IssuerAndSerialNumber.getInstance(_obj);
		}
	}

	public RecipientIdentifier(RecipientIdentifier _orig) {
		id = _orig.id;
	}

    /**
     * return a RecipientIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato);
	}

    /**
     * return a RecipientIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientIdentifier) {
			return (RecipientIdentifier)_obj;
		}

		if(_obj instanceof IssuerAndSerialNumber) {
			return new RecipientIdentifier((IssuerAndSerialNumber)_obj);
		}

		if(_obj instanceof com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier) {
			return new RecipientIdentifier((com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier)_obj);
		}

		if(_obj instanceof DERObject) {
			return new RecipientIdentifier((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientIdentifier: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getId() {
		return id;
	}

	private void setId(com.gp.gpscript.keymgr.asn1.x509.SubjectKeyIdentifier _id) {
		id = _id;
	}


	public DERObject getDERObject() {
		if(id instanceof IssuerAndSerialNumber) {
			return id.getDERObject();
		}
		else {
			return new BERTaggedObject(0, id.getDERObject());
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


}
