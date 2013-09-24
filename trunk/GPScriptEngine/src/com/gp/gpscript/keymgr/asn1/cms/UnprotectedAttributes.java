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
 * UnprotectedAttributes ::= SET SIZE (1..MAX) OF Attribute
 * </pre>
 *
 *
 */

public class UnprotectedAttributes extends CMSObject {

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

	public UnprotectedAttributes(Vector _attributes) {
		setAttributes(_attributes);
	}

	public UnprotectedAttributes(ASN1Set _attributes) {
		attributes = _attributes;
	}

	public UnprotectedAttributes(UnprotectedAttributes _orig) {
		attributes = _orig.attributes;
	}

    /**
     * return a UnprotectedAttributes object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static UnprotectedAttributes getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a UnprotectedAttributes object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static UnprotectedAttributes getInstance(Object _obj) {
		if(_obj == null || _obj instanceof UnprotectedAttributes) {
			return (UnprotectedAttributes)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new UnprotectedAttributes((ASN1Set)_obj);
		}

		if(_obj instanceof Vector) {
			return new UnprotectedAttributes((Vector)_obj);
		}

		throw new IllegalArgumentException("Invalid UnprotectedAttributes: " + _obj.getClass().getName());
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


	private void setAttributes(Vector _attributes) {

		int _len   = _attributes.size();
		attributes = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			attributes.addObject(Attribute.getInstance(_attributes.elementAt(i)));
		}
	}

}
