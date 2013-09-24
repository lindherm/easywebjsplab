package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KeyAgreeRecipientInfo ::= SEQUENCE {
 * 	version CMSVersion,  -- always set to 3
 * 	originator [0] EXPLICIT OriginatorIdentifierOrKey,
 * 	ukm [1] EXPLICIT UserKeyingMaterial OPTIONAL,
 * 	keyEncryptionAlgorithm KeyEncryptionAlgorithmIdentifier,
 * 	recipientEncryptedKeys RecipientEncryptedKeys
 * }
 * </pre>
 *
 *
 */

public class KeyAgreeRecipientInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                       version;
	private OriginatorIdentifierOrKey        originator;
	private UserKeyingMaterial               ukm;
	private KeyEncryptionAlgorithmIdentifier keyEncryptionAlgorithm;
	private RecipientEncryptedKeys           recipientEncryptedKeys;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public KeyAgreeRecipientInfo(OriginatorIdentifierOrKey        _originator,
								 UserKeyingMaterial               _ukm,
								 KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm,
								 RecipientEncryptedKeys           _recipientEncryptedKeys) {

		setOriginator(_originator);
		setUserKeyingMaterial(_ukm);
		setKeyEncryptionAlgorithm(_keyEncryptionAlgorithm);
		setRecipientEncryptedKeys(_recipientEncryptedKeys);
		setVersion();
	}

	public KeyAgreeRecipientInfo(ASN1Sequence _seq) {

		int i = 0;

		version = CMSVersion.getInstance(_seq.getObjectAt(i++));

		switch(_seq.getSize()) {
			case 3 :
				break;
			case 4 :
				ASN1TaggedObject _ato = (ASN1TaggedObject)_seq.getObjectAt(i++);
				switch(_ato.getTagNo()) {
					case 0 :
						originator = OriginatorIdentifierOrKey.getInstance(_ato, true);
						break;
					case 1 :
						ukm = UserKeyingMaterial.getInstance(_ato, true);
						break;
					default:
						throw new IllegalArgumentException("Invalid KeyAgreeRecipientInfo. Tag number out of range: " + _ato.getTagNo());
				}
				break;
			case 5 :
				originator = OriginatorIdentifierOrKey.getInstance((ASN1TaggedObject)_seq.getObjectAt(i++), true);
				ukm        = UserKeyingMaterial.getInstance((ASN1TaggedObject)_seq.getObjectAt(i++), true);
				break;
			default:
				throw new IllegalArgumentException("Invalid KeyAgreeRecipientInfo. Bad sequence.");
		}

		keyEncryptionAlgorithm = KeyEncryptionAlgorithmIdentifier.getInstance(_seq.getObjectAt(i++));
		recipientEncryptedKeys = RecipientEncryptedKeys.getInstance(_seq.getObjectAt(i++));
	}

	public KeyAgreeRecipientInfo(KeyAgreeRecipientInfo _orig) {
		version                = _orig.version;
		originator             = _orig.originator;
		ukm                    = _orig.ukm;
		keyEncryptionAlgorithm = _orig.keyEncryptionAlgorithm;
		recipientEncryptedKeys = _orig.recipientEncryptedKeys;
	}

    /**
     * return a KeyAgreeRecipientInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KeyAgreeRecipientInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a KeyAgreeRecipientInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KeyAgreeRecipientInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KeyAgreeRecipientInfo) {
			return (KeyAgreeRecipientInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new KeyAgreeRecipientInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid KeyAgreeRecipientInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public OriginatorIdentifierOrKey getOriginator() {
		return originator;
	}

	public UserKeyingMaterial getUserKeyingMaterial() {
		return ukm;
	}

	public KeyEncryptionAlgorithmIdentifier getKeyEncryptionAlgorithm() {
		return keyEncryptionAlgorithm;
	}

	public RecipientEncryptedKeys getRecipientEncryptedKeys() {
		return recipientEncryptedKeys;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(new BERTaggedObject(true, 0, originator.getDERObject()));

		if(ukm != null) {
			_seq.addObject(new BERTaggedObject(true, 1, ukm.getDERObject()));
		}

		_seq.addObject(keyEncryptionAlgorithm);
		_seq.addObject(recipientEncryptedKeys);
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
		setVersion(new CMSVersion(new BigInteger("3")));
	}

	private void setOriginator(OriginatorIdentifierOrKey _originator) {
		originator = _originator;
	}

	private void setUserKeyingMaterial(UserKeyingMaterial _ukm) {
		ukm = _ukm;
	}

	private void setKeyEncryptionAlgorithm(KeyEncryptionAlgorithmIdentifier _keyEncryptionAlgorithm) {
		keyEncryptionAlgorithm = _keyEncryptionAlgorithm;
	}

	private void setRecipientEncryptedKeys(RecipientEncryptedKeys _recipientEncryptedKeys) {
		recipientEncryptedKeys = _recipientEncryptedKeys;
	}

}
