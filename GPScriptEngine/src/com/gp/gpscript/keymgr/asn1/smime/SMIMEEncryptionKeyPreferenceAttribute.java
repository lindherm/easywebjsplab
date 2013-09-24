package com.gp.gpscript.keymgr.asn1.smime;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.cms.Attribute;
import com.gp.gpscript.keymgr.asn1.cms.IssuerAndSerialNumber;
import com.gp.gpscript.keymgr.asn1.cms.RecipientKeyIdentifier;
import com.gp.gpscript.keymgr.asn1.cms.SubjectKeyIdentifier;
import com.gp.gpscript.keymgr.asn1.smime.SMIMEObjectIdentifiers;

/**
*
* RFC 2633
*
*
* SMIMEEncryptionKeyPreference ::= CHOICE {
* 	issuerAndSerialNumber   [0] IssuerAndSerialNumber,
* 	receipentKeyId          [1] RecipientKeyIdentifier,
* 	subjectAltKeyIdentifier [2] SubjectKeyIdentifier
* }
*/

public class SMIMEEncryptionKeyPreferenceAttribute extends Attribute {

	/*
	 *
	 *  VARIABLES
	 *
	 */


	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	protected SMIMEEncryptionKeyPreferenceAttribute() {
		super();
		attrType = SMIMEObjectIdentifiers.id_aa_encrypKeyPref;
	}

	public SMIMEEncryptionKeyPreferenceAttribute(IssuerAndSerialNumber _iasn) {
		this();
		attrValues = new DERConstructedSet();
		attrValues.addObject(_iasn);
	}

	public SMIMEEncryptionKeyPreferenceAttribute(RecipientKeyIdentifier _rki) {
		this();
		attrValues = new DERConstructedSet();
		attrValues.addObject(_rki);
	}

	public SMIMEEncryptionKeyPreferenceAttribute(SubjectKeyIdentifier _ski) {
		this();
		attrValues = new DERConstructedSet();
		attrValues.addObject(_ski);
	}

	public SMIMEEncryptionKeyPreferenceAttribute(ASN1Sequence _seq) {
		this();
		attrValues = (DERConstructedSet)_seq.getObjectAt(1);
	}

	public SMIMEEncryptionKeyPreferenceAttribute(SMIMEEncryptionKeyPreferenceAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}



	public static Attribute getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SMIMEEncryptionKeyPreferenceAttribute) {
			return (SMIMEEncryptionKeyPreferenceAttribute)_obj;
		}

		if(_obj instanceof IssuerAndSerialNumber) {
			return new SMIMEEncryptionKeyPreferenceAttribute((IssuerAndSerialNumber)_obj);
		}

		if(_obj instanceof RecipientKeyIdentifier) {
			return new SMIMEEncryptionKeyPreferenceAttribute((RecipientKeyIdentifier)_obj);
		}

		if(_obj instanceof SubjectKeyIdentifier) {
			return new SMIMEEncryptionKeyPreferenceAttribute((SubjectKeyIdentifier)_obj);
		}

		if(_obj instanceof ASN1Sequence) {
			return new SMIMEEncryptionKeyPreferenceAttribute((ASN1Sequence)_obj);
		}

		if(_obj instanceof ASN1TaggedObject) {
			return getInstance(((ASN1TaggedObject)_obj).getObject());
		}

		throw new IllegalArgumentException("Invalid SMIMEEncryptionKeyPreferenceAttribute: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getEncryptionKeyPreference() {
		return (DEREncodable)attrValues.getObjectAt(0);
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

}
