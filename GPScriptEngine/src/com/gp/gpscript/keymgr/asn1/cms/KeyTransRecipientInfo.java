package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KeyTransRecipientInfo ::= SEQUENCE {
 * 	version CMSVersion,  -- always set to 0 or 2
 * 	rid RecipientIdentifier,
 * 	keyEncryptionAlgorithm KeyEncryptionAlgorithmIdentifier,
 * 	encryptedKey EncryptedKey
 * }
 * </pre>
 *
 *
 */

public class KeyTransRecipientInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                       version;
	private RecipientIdentifier              rid;
	private KeyEncryptionAlgorithmIdentifier keyEncryptionAlgorithm;
	private EncryptedKey                     encryptedKey;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public KeyTransRecipientInfo(RecipientIdentifier              _rid,
								 KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm,
								 EncryptedKey                     _encryptedKey) {

		setRecipientIdentifier(_rid);
		setKeyEncryptionAlgorithm(_keyEncryptionAlgorithm);
		setEncryptedKey(_encryptedKey);
		setVersion();
	}

	public KeyTransRecipientInfo(ASN1Sequence _seq) {

		version                = CMSVersion.getInstance(_seq.getObjectAt(0));
		rid                    = RecipientIdentifier.getInstance(_seq.getObjectAt(1));
		keyEncryptionAlgorithm = KeyEncryptionAlgorithmIdentifier.getInstance(_seq.getObjectAt(2));
		encryptedKey           = EncryptedKey.getInstance(_seq.getObjectAt(3));
	}

	public KeyTransRecipientInfo(KeyTransRecipientInfo _orig) {
		version                = _orig.version;
		rid                    = _orig.rid;
		keyEncryptionAlgorithm = _orig.keyEncryptionAlgorithm;
		encryptedKey           = _orig.encryptedKey;
	}

    /**
     * return a KeyTransRecipientInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KeyTransRecipientInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a KeyTransRecipientInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KeyTransRecipientInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KeyTransRecipientInfo) {
			return (KeyTransRecipientInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new KeyTransRecipientInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid KeyTransRecipientInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public RecipientIdentifier getRecipientIdentifier() {
		return rid;
	}

	public KeyEncryptionAlgorithmIdentifier getKeyEncryptionAlgorithm() {
		return keyEncryptionAlgorithm;
	}

	public EncryptedKey getEncryptedKey() {
		return encryptedKey;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(rid);
		_seq.addObject(keyEncryptionAlgorithm);
		_seq.addObject(encryptedKey);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setVersion(CMSVersion _version) {
		version = _version;
	}

	private void setVersion() {
		DEREncodable _obj = rid.getId();
		if(_obj instanceof IssuerAndSerialNumber) {
			setVersion(new CMSVersion(new BigInteger("0")));
		}
		else if(_obj instanceof SubjectKeyIdentifier) {
			setVersion(new CMSVersion(new BigInteger("2")));
		}
	}

	private void setRecipientIdentifier(RecipientIdentifier _rid) {
		rid = _rid;
	}

	private void setKeyEncryptionAlgorithm(KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm) {
		keyEncryptionAlgorithm = _keyEncryptionAlgorithm;
	}

	private void setEncryptedKey(EncryptedKey _encryptedKey) {
		encryptedKey = _encryptedKey;
	}


}
