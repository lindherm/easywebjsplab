package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KeyAgreeRecipientIdentifier ::= CHOICE {
 * 	issuerAndSerialNumber IssuerAndSerialNumber,
 * 	rKeyId [0] IMPLICIT RecipientKeyIdentifier
 * }
 * </pre>
 *
 *
 */

public class KeyAgreeRecipientIdentifier extends CMSObject {

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

	public KeyAgreeRecipientIdentifier(IssuerAndSerialNumber _issuerAndSerialNumber) {
		setId(_issuerAndSerialNumber);
	}

	public KeyAgreeRecipientIdentifier(RecipientKeyIdentifier _rKeyId) {
		setId(_rKeyId);
	}

	public KeyAgreeRecipientIdentifier(DERObject _obj) {

		if(_obj instanceof ASN1TaggedObject) {
			id = RecipientKeyIdentifier.getInstance((ASN1TaggedObject)_obj, false);
		}
		else {
			id = IssuerAndSerialNumber.getInstance(_obj);
		}
	}

	public KeyAgreeRecipientIdentifier(KeyAgreeRecipientIdentifier _orig) {
		id = _orig.id;
	}

    /**
     * return a KeyAgreeRecipientIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KeyAgreeRecipientIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato);
	}

    /**
     * return a KeyAgreeRecipientIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KeyAgreeRecipientIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KeyAgreeRecipientIdentifier) {
			return (KeyAgreeRecipientIdentifier)_obj;
		}

		if(_obj instanceof RecipientKeyIdentifier) {
			return new KeyAgreeRecipientIdentifier((RecipientKeyIdentifier)_obj);
		}

		if(_obj instanceof IssuerAndSerialNumber) {
			return new KeyAgreeRecipientIdentifier((IssuerAndSerialNumber)_obj);
		}

		if(_obj instanceof DERObject) {
			return new KeyAgreeRecipientIdentifier((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid KeyAgreeRecipientIdentifier: " + _obj.getClass().getName());
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
		if(id instanceof IssuerAndSerialNumber) {
			return id.getDERObject();
		}
		else {
			return new BERTaggedObject(false, 0, id.getDERObject());
		}
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setId(IssuerAndSerialNumber _issuerAndSerialNumber) {
		id = _issuerAndSerialNumber;
	}

	private void setId(RecipientKeyIdentifier _rKeyId) {
		id = _rKeyId;
	}

}
