package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * Attribute ::= SEQUENCE {
 * 	attrType OBJECT IDENTIFIER,
 * 	attrValues SET OF AttributeValue
 * }
 * </pre>
 *
 */

public class Attribute extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	protected DERObjectIdentifier attrType;
	protected ASN1Set             attrValues;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	protected Attribute() {

	}

	public Attribute(String _attrType, Vector _attrValues) {

		setAttrType(_attrType);
		setAttrValues(_attrValues);
	}

	public Attribute(ASN1Sequence _seq) {
		attrType   = (DERObjectIdentifier)_seq.getObjectAt(0);
		attrValues = (DERConstructedSet)_seq.getObjectAt(1);
	}

	public Attribute(Attribute _orig) {
		attrType   = _orig.attrType;
		attrValues = _orig.attrValues;
	}

	protected Attribute(DERObjectIdentifier _attrType, ASN1Set _attrValues) {
		attrType   = _attrType;
		attrValues = _attrValues;
	}

    /**
     * return an Attribute from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *              tagged object cannot be converted.
     */
	public static Attribute getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an Attribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof Attribute) {
			return (Attribute)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new Attribute((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid Attribute: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */


	public String getAttrType() {
		return attrType.getId();
	}

	public ASN1Set getAttrValuesDERSet() {
		return attrValues;
	}

	public Vector getAttrValues() {

		int    _len = attrValues.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(AttributeValue.getInstance(attrValues.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(attrType);
		_seq.addObject(attrValues);
		return _seq;
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setAttrType(String _attrType) {
		attrType = new DERObjectIdentifier(_attrType);
	}

	private void setAttrValues(Vector _attrValues) {

		int _len   = _attrValues.size();
		attrValues = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			attrValues.addObject(AttributeValue.getInstance(_attrValues.elementAt(i)));
		}
	}

}
