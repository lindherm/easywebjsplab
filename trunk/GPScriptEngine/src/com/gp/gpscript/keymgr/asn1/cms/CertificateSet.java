package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * CertificateSet ::= SET OF CertificateChoices
 * </pre>
 *
 *
 */

public class CertificateSet extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set certificateChoices;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public CertificateSet(Vector _certificateChoices) {
		setCertificateChoices(_certificateChoices);
	}

	public CertificateSet(ASN1Set _certificateChoices) {
		certificateChoices = _certificateChoices;
	}

	public CertificateSet(CertificateSet _orig) {
		certificateChoices = _orig.certificateChoices;
	}

    /**
     * return a CertificateSet object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static CertificateSet getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a CertificateSet object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static CertificateSet getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CertificateSet) {
			return (CertificateSet)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new CertificateSet((ASN1Set)_obj);
		}

		if(_obj instanceof Vector) {
			return new CertificateSet((Vector)_obj);
		}

		CertificateChoices _cc = CertificateChoices.getInstance(_obj);
		Vector _vec = new Vector();
		_vec.addElement(_cc);
		return new CertificateSet(_vec);
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getCertificateChoices() {
		int    _len = certificateChoices.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(CertificateChoices.getInstance(certificateChoices.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return certificateChoices;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setCertificateChoices(Vector _certificateChoices) {

		int _len   = _certificateChoices.size();
		certificateChoices = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			certificateChoices.addObject(CertificateChoices.getInstance(_certificateChoices.elementAt(i)));
		}
	}


}
