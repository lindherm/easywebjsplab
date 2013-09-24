package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RC2wrapParameter ::= RC2ParameterVersion
 * </pre>
 *
 *
 */

public class RC2wrapParameter extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private RC2ParameterVersion param;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RC2wrapParameter(RC2ParameterVersion _param) {
		param = _param;
	}

	public RC2wrapParameter(RC2wrapParameter _orig) {
		param = _orig.param;
	}

    /**
     * return a RC2wrapParameter object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RC2wrapParameter getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(RC2ParameterVersion.getInstance(_ato, _explicit));
	}

    /**
     * return a RC2wrapParameter object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RC2wrapParameter getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RC2wrapParameter) {
			return (RC2wrapParameter)_obj;
		}

		return new RC2wrapParameter(RC2ParameterVersion.getInstance(_obj));
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public RC2ParameterVersion getParam() {
		return param;
	}



	public DERObject getDERObject() {
		return param.getDERObject();
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setParam(RC2ParameterVersion _param) {
		param = _param;
	}


}
