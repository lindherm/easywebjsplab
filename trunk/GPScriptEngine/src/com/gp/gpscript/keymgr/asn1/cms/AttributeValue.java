package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * AttributeValue ::= ANY
 * </pre>
 *
 *
 */

public class AttributeValue extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DEREncodable value;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public AttributeValue(DEREncodable _value) {
		setValue(_value);
	}

	public AttributeValue(AttributeValue _orig) {
		value = _orig.value;
	}

    /**
     * return an AttributeValue from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static AttributeValue getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato.getObject());
	}

    /**
     * return an AttributeValue object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static AttributeValue getInstance(Object _obj) {
		if(_obj == null || _obj instanceof AttributeValue) {
			return (AttributeValue)_obj;
		}

		if(_obj instanceof DEREncodable) {
			return new AttributeValue((DEREncodable)_obj);
		}

		throw new IllegalArgumentException("Invalid AttributeValue: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getValue() {
		return value;
	}

	public DERObject getDERObject() {
		return value.getDERObject();
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setValue(DEREncodable _value) {
		value = _value;
	}


}
