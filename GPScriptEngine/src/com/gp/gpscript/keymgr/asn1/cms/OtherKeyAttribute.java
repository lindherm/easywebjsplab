package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * OtherKeyAttribute ::= SEQUENCE {
 * 	keyAttrId OBJECT IDENTIFIER,
 * 	keyAttr ANY DEFINED BY keyAttrId OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class OtherKeyAttribute extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERObjectIdentifier keyAttrId;
	private DERObject           keyAttr;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public OtherKeyAttribute(String _keyAttrId, DERObject _keyAttr) {
		setKeyAttributeId(_keyAttrId);
		setKeyAttribute(_keyAttr);
	}

	public OtherKeyAttribute(ASN1Sequence _seq) {
		keyAttrId = (DERObjectIdentifier)_seq.getObjectAt(0);
		if(_seq.getSize() > 1) {
			keyAttr = (DERObject)_seq.getObjectAt(1);
		}
	}

	public OtherKeyAttribute(OtherKeyAttribute _orig) {
		keyAttrId = _orig.keyAttrId;
		keyAttr   = _orig.keyAttr;
	}

    /**
     * return an OtherKeyAttribute object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static OtherKeyAttribute getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an OtherKeyAttribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static OtherKeyAttribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof OtherKeyAttribute) {
			return (OtherKeyAttribute)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new OtherKeyAttribute((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid OtherKeyAttribute: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public String getKeyAttributeId() {
		return keyAttrId.getId();
	}

	public DERObject getKeyAttribute() {
		return keyAttr;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(keyAttrId);
		if(keyAttr != null) {
			_seq.addObject(keyAttr);
		}
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setKeyAttributeId(String _keyAttributeId) {
		keyAttrId = new DERObjectIdentifier(_keyAttributeId);
	}

	private void setKeyAttribute(DERObject _keyAttr) {
		keyAttr = _keyAttr;
	}


}
