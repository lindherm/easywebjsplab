package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERInteger;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RC2ParameterVersion ::= INTEGER
 * </pre>
 *
 *
 */

public class RC2ParameterVersion extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERInteger version;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RC2ParameterVersion(BigInteger _version) {
		setVersion(_version);
	}

	public RC2ParameterVersion(DERInteger _version) {
		version = _version;
	}

	public RC2ParameterVersion(RC2ParameterVersion _orig) {
		version = _orig.version;
	}

    /**
     * return a RC2ParameterVersion object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RC2ParameterVersion getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(DERInteger.getInstance(_ato, _explicit));
	}

    /**
     * return a RC2ParameterVersion object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RC2ParameterVersion getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RC2ParameterVersion) {
			return (RC2ParameterVersion)_obj;
		}

		if(_obj instanceof DERInteger) {
			return new RC2ParameterVersion((DERInteger)_obj);
		}

		throw new IllegalArgumentException("Invalid RC2ParameterVersion: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public BigInteger getVersion() {
		return version.getValue();
	}



	public DERObject getDERObject() {
		return version;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setVersion(BigInteger _version) {
		version = new DERInteger(_version);
	}


}
