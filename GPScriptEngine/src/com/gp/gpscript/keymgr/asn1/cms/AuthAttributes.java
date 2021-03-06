package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * AuthAttributes ::= SET SIZE (1..MAX) OF Attribute
 * </pre>
 *
 *
 */

public class AuthAttributes extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set attributes;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public AuthAttributes(Vector _attributes) {

		setAttributes(_attributes);
	}

	public AuthAttributes(ASN1Set _attributes) {
		attributes = _attributes;
	}

	public AuthAttributes(AuthAttributes _orig) {
		attributes = _orig.attributes;
	}

    /**
     * return an AuthAttributes object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static AuthAttributes getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return an AuthAttributes object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static AuthAttributes getInstance(Object _obj) {
		if(_obj == null || _obj instanceof AuthAttributes) {
			return (AuthAttributes)_obj;
		}

		if(_obj instanceof Vector) {
			return new AuthAttributes((Vector)_obj);
		}

		if(_obj instanceof ASN1Set) {
			return new AuthAttributes((ASN1Set)_obj);
		}

		throw new IllegalArgumentException("Invalid AuthAttributes: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */


	public Vector getAttributes() {

		int    _len = attributes.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(Attribute.getInstance(attributes.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return attributes;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	protected static int getASN1Type() {
		return DER;
	}

	private void setAttributes(Vector _attributes) {

		int _len   = _attributes.size();
		attributes = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			attributes.addObject(Attribute.getInstance(_attributes.elementAt(i)));
		}
	}


}
