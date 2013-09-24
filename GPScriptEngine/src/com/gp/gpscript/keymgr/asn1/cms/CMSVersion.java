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
 * CMSVersion ::= INTEGER  { v0(0), v1(1), v2(2), v3(3), v4(4) }
 * </pre>
 *
 *
 */

public class CMSVersion extends CMSObject {

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

	public CMSVersion(BigInteger _version) {
		setVersion(_version);
	}

	public CMSVersion(DERInteger _version) {
		version = _version;
	}

	public CMSVersion(CMSVersion _orig) {
		version = _orig.version;
	}

    /**
     * return an CMSVersion object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static CMSVersion getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(DERInteger.getInstance(_ato, _explicit));
	}

    /**
     * return a CMSVersion object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static CMSVersion getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CMSVersion) {
			return (CMSVersion)_obj;
		}

		return new CMSVersion(DERInteger.getInstance(_obj));
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
