package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientEncryptedKey ::= SEQUENCE {
 * 	rid KeyAgreeRecipientIdentifier,
 * 	encryptedKey EncryptedKey
 * }
 * </pre>
 *
 *
 */

public class RecipientEncryptedKey extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private KeyAgreeRecipientIdentifier rid;
	private EncryptedKey                encryptedKey;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientEncryptedKey(KeyAgreeRecipientIdentifier _rid,
	                             EncryptedKey                _encryptedKey) {

		setRid(_rid);
		setEncryptedKey(_encryptedKey);
	}

	public RecipientEncryptedKey(ASN1Sequence _seq){

		rid          = KeyAgreeRecipientIdentifier.getInstance(_seq.getObjectAt(0));
		encryptedKey = EncryptedKey.getInstance(_seq.getObjectAt(1));
	}

	public RecipientEncryptedKey(RecipientEncryptedKey _orig) {
		rid          = _orig.rid;
		encryptedKey = _orig.encryptedKey;
	}

    /**
     * return a RecipientEncryptedKey object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientEncryptedKey getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a RecipientEncryptedKey object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientEncryptedKey getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientEncryptedKey) {
			return (RecipientEncryptedKey)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new RecipientEncryptedKey((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientEncryptedKey: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public KeyAgreeRecipientIdentifier getRid() {
		return rid;
	}


	public EncryptedKey getEncryptedKey() {
		return encryptedKey;
	}

	private void setEncryptedKey(EncryptedKey _encryptedKey) {
		encryptedKey = _encryptedKey;
	}



	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(rid);
		_seq.addObject(encryptedKey);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setRid(KeyAgreeRecipientIdentifier _rid) {
		rid = _rid;
	}


}
