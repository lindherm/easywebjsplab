package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KEKRecipientInfo ::= SEQUENCE {
 * 	version CMSVersion,  -- always set to 4
 * 	kekid KEKIdentifier,
 * 	keyEncryptionAlgorithm KeyEncryptionAlgorithmIdentifier,
 * 	encryptedKey EncryptedKey
 * }
 * </pre>
 *
 *
 */

public class KEKRecipientInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                       version;
	private KEKIdentifier                    kekid;
	private KeyEncryptionAlgorithmIdentifier keyEncryptionAlgorithm;
	private EncryptedKey                     encryptedKey;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public KEKRecipientInfo(KEKIdentifier                    _kekid,
							KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm,
							EncryptedKey                     _encryptedKey) {

		setKekid(_kekid);
		setKeyEncryptionAlgorithm(_keyEncryptionAlgorithm);
		setEncryptedKey(_encryptedKey);
		setVersion();
	}


	public KEKRecipientInfo(ASN1Sequence _seq) {

		version                = CMSVersion.getInstance(_seq.getObjectAt(0));
		kekid                  = KEKIdentifier.getInstance(_seq.getObjectAt(1));
		keyEncryptionAlgorithm = KeyEncryptionAlgorithmIdentifier.getInstance(_seq.getObjectAt(2));
		encryptedKey           = EncryptedKey.getInstance(_seq.getObjectAt(3));
	}

	public KEKRecipientInfo(KEKRecipientInfo _orig) {
		version                = _orig.version;
		kekid                  = _orig.kekid;
		keyEncryptionAlgorithm = _orig.keyEncryptionAlgorithm;
		encryptedKey           = _orig.encryptedKey;
	}

    /**
     * return a KEKRecipientInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KEKRecipientInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a KEKRecipientInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KEKRecipientInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KEKRecipientInfo) {
			return (KEKRecipientInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new KEKRecipientInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid KEKRecipientInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public KEKIdentifier getKekid() {
		return kekid;
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
		_seq.addObject(kekid);
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
		setVersion(new CMSVersion(new BigInteger("4")));
	}

	private void setKekid(KEKIdentifier _kekid) {
		kekid = _kekid;
	}

	private void setKeyEncryptionAlgorithm(KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm) {
		keyEncryptionAlgorithm = _keyEncryptionAlgorithm;
	}

	private void setEncryptedKey(EncryptedKey _encryptedKey) {
		encryptedKey = _encryptedKey;
	}

}
