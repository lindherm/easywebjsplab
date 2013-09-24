package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * CBCParameter ::= IV
 * </pre>
 *
 *
 */

public class CBCParameter extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private IV iv;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public CBCParameter(IV _iv) {
		iv = _iv;
	}

	public CBCParameter(CBCParameter _orig) {
		iv = _orig.iv;
	}

    /**
     * return a CBCParameter object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static CBCParameter getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(IV.getInstance(_ato, _explicit));
	}

    /**
     * return a CBCParameter object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static CBCParameter getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CBCParameter) {
			return (CBCParameter)_obj;
		}

		return new CBCParameter(IV.getInstance(_obj));
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public IV getIv() {
		return iv;
	}

	public DERObject getDERObject() {
		return iv.getDERObject();
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setIv(IV _iv) {
		iv = _iv;
	}


}
