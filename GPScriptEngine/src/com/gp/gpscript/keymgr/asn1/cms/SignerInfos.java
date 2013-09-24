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
 * SignerInfos ::= SET OF SignerInfo
 * </pre>
 *
 *
 */

public class SignerInfos extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set infos;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SignerInfos(Vector _infos) {

		setInfos(_infos);
	}

	public SignerInfos(ASN1Set _infos) {
		infos = _infos;
	}

	public SignerInfos(SignerInfos _orig) {
		infos = _orig.infos;
	}

    /**
     * return a SignerInfos object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SignerInfos getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a SignerInfos object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SignerInfos getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SignerInfos) {
			return (SignerInfos)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new SignerInfos((ASN1Set)_obj);
		}

		throw new IllegalArgumentException("Invalid SignerInfos: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getInfos() {

		int    _len = infos.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(SignerInfo.getInstance(infos.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return infos;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setInfos(Vector _infos) {

		int _len   = _infos.size();
		infos = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			infos.addObject(SignerInfo.getInstance(_infos.elementAt(i)));
		}
	}

}
