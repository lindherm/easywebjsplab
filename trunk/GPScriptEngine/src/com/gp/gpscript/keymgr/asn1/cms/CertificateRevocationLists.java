package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.x509.CertificateList;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * CertificateRevocationLists ::= SET OF CertificateList
 * </pre>
 *
 *
 */

public class CertificateRevocationLists extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set crls;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public CertificateRevocationLists(Vector _crls) {
		setCrls(_crls);
	}

	public CertificateRevocationLists(ASN1Set _crls) {
		crls = _crls;
	}

	public CertificateRevocationLists(CertificateRevocationLists _orig) {
		crls = _orig.crls;
	}

    /**
     * return an CertificateRevocationLists object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static CertificateRevocationLists getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a CertificateRevocationLists object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static CertificateRevocationLists getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CertificateRevocationLists) {
			return (CertificateRevocationLists)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new CertificateRevocationLists((ASN1Set)_obj);
		}

		if(_obj instanceof Vector) {
			return new CertificateRevocationLists((Vector)_obj);
		}

		throw new IllegalArgumentException("Invalid CertificateRevocationLists: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getCrls() {
		int    _len = crls.getSize();
		Vector _res = new Vector();
		Object _obj;
		for(int i = 0; i < _len; i++) {
			_res.addElement(CertificateList.getInstance(crls.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return crls;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setCrls(Vector _crls) {
		int _len = _crls.size();
		crls     = new DERConstructedSet();
		Object _obj;
		for(int i = 0; i < _len; i++) {
			crls.addObject(CertificateList.getInstance(_crls.elementAt(i)));
		}
	}


}
