package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientEncryptedKeys ::= SEQUENCE OF RecipientEncryptedKey
 * </pre>
 *
 *
 */

public class RecipientEncryptedKeys extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private Vector keys;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientEncryptedKeys(Vector _keys) {
		setKeys(_keys);
	}

	public RecipientEncryptedKeys(ASN1Sequence _seq) {

		keys = new Vector();
		int _len = _seq.getSize();
		for(int i = 0; i < _len; i++) {
			keys.addElement(RecipientEncryptedKey.getInstance(_seq.getObjectAt(i)));
		}
	}

	public RecipientEncryptedKeys(RecipientEncryptedKeys _orig) {
		keys = _orig.keys;
	}

    /**
     * return a RecipientEncryptedKeys object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientEncryptedKeys getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a RecipientEncryptedKeys object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientEncryptedKeys getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientEncryptedKeys) {
			return (RecipientEncryptedKeys)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new RecipientEncryptedKeys((ASN1Sequence)_obj);
		}

		if(_obj instanceof Vector) {
			return new RecipientEncryptedKeys((Vector)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientEncryptedKeys: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getKeys() {
		return keys;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		int _len = keys.size();
		for(int i = 0; i < _len; i++) {
			_seq.addObject((RecipientEncryptedKey)keys.elementAt(i));
		}
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setKeys(Vector _keys) {
		keys = _keys;
	}


}
