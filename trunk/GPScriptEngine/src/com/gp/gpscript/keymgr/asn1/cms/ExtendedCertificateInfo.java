package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.x509.X509CertificateStructure;

/**
*	RFC 2630
*
*
*	ExtendedCertificateInfo ::= SEQUENCE {
*		version CMSVersion,
*		certificate Certificate,
*		attributes UnauthAttributes
*	}
*/
public class ExtendedCertificateInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion               version;
	private X509CertificateStructure certificate;
	private UnauthAttributes         attributes;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public ExtendedCertificateInfo(ASN1Sequence _seq) {

		version     = CMSVersion.getInstance(_seq.getObjectAt(0));
		certificate = X509CertificateStructure.getInstance(_seq.getObjectAt(1));
		attributes  = UnauthAttributes.getInstance(_seq.getObjectAt(2));
	}

	public ExtendedCertificateInfo(ExtendedCertificateInfo _cert) {
		version     = _cert.version;
		certificate = _cert.certificate;
		attributes  = _cert.attributes;
	}

    /**
     * return an ExtendedCertificateInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static ExtendedCertificateInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an ExtendedCertificateInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static ExtendedCertificateInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof ExtendedCertificateInfo) {
			return (ExtendedCertificateInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new ExtendedCertificateInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid ExtendedCertificateInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DERObject getDERObject() {
		DERConstructedSequence _seq = new DERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(certificate);
		_seq.addObject(attributes);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	protected static int getASN1Type() {
		return DER;
	}

}
