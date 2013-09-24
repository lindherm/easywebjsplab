package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
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
 * OriginatorIdentifierOrKey ::= CHOICE {
 * 	issuerAndSerialNumber IssuerAndSerialNumber,
 * 	subjectKeyIdentifier [0] SubjectKeyIdentifier,
 * 	originatorKey [1] OriginatorPublicKey
 * }
 * </pre>
 *
 *
 */

public class OriginatorIdentifierOrKey extends CMSObject {

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

	public OriginatorIdentifierOrKey(IssuerAndSerialNumber _id) {
		setId(_id);
	}

	public OriginatorIdentifierOrKey(SubjectKeyIdentifier _id) {
		setId(_id);
	}

	public OriginatorIdentifierOrKey(OriginatorPublicKey _id) {
		setId(_id);
	}


	public OriginatorIdentifierOrKey(DERObject _obj) {

		if(_obj instanceof ASN1TaggedObject) {
			ASN1TaggedObject _dto = (ASN1TaggedObject)_obj;
			switch(_dto.getTagNo()) {
				case 0 :
					id = SubjectKeyIdentifier.getInstance(_dto, false);
					break;
				case 1 :
					id = OriginatorPublicKey.getInstance(_dto, false);
					break;
				default:
					throw new IllegalArgumentException("Invalid OriginatorIdentifierOrKey: " + _obj.getClass().getName());
			}
		}
		else {
			id = IssuerAndSerialNumber.getInstance(_obj);
		}
	}

	public OriginatorIdentifierOrKey(OriginatorIdentifierOrKey _orig) {
		id = _orig.id;
	}

    /**
     * return an OriginatorIdentifierOrKey object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static OriginatorIdentifierOrKey getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato);
	}

    /**
     * return an OriginatorIdentifierOrKey object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static OriginatorIdentifierOrKey getInstance(Object _obj) {
		if(_obj == null || _obj instanceof OriginatorIdentifierOrKey) {
			return (OriginatorIdentifierOrKey)_obj;
		}

		if(_obj instanceof IssuerAndSerialNumber) {
			return new OriginatorIdentifierOrKey((IssuerAndSerialNumber)_obj);
		}

		if(_obj instanceof SubjectKeyIdentifier) {
			return new OriginatorIdentifierOrKey((SubjectKeyIdentifier)_obj);
		}

		if(_obj instanceof OriginatorPublicKey) {
			return new OriginatorIdentifierOrKey((OriginatorPublicKey)_obj);
		}

		if(_obj instanceof DERObject) {
			return new OriginatorIdentifierOrKey((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid OriginatorIdentifierOrKey: " + _obj.getClass().getName());
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
		else if(id instanceof OriginatorPublicKey) {
			return new BERTaggedObject(false, 1, id.getDERObject());
		}

		return id.getDERObject();
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

	private void setId(OriginatorPublicKey _id) {
		id = _id;
	}


}
