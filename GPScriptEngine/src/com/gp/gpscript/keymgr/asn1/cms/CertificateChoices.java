package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.x509.X509CertificateStructure;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * CertificateChoices ::= CHOICE {
 * 	certificate Certificate,  -- See X.509
 * 	extendedCertificate [0] IMPLICIT ExtendedCertificate,  -- Obsolete
 * 	attrCert [1] IMPLICIT AttributeCertificate -- See X.509 & X9.57
 * }
 * </pre>
 *
 *
 */

public class CertificateChoices extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DEREncodable cert;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public CertificateChoices(X509CertificateStructure _cert) {
		setCertificate(_cert);
	}

	public CertificateChoices(ExtendedCertificate _cert) {
		setCertificate(_cert);
	}

	public CertificateChoices(DERObject _cert) {

		if(_cert instanceof ASN1TaggedObject) {
			ASN1TaggedObject _dto = (ASN1TaggedObject)_cert;
			switch(_dto.getTagNo()) {
				case 0 :
					cert = ExtendedCertificate.getInstance(_dto, false);
					break;
				case 1 :
					throw new IllegalArgumentException("Invalid Certificate Choices -- AttributeCertificate not supported");
				default:
					throw new IllegalArgumentException("Invalid CertificateChoices -- Bad tag: " + _dto.getTagNo());
			}
		}
		else {
			cert = X509CertificateStructure.getInstance(_cert);
		}
	}

	public CertificateChoices(CertificateChoices _orig) {
		cert = _orig.cert;
	}

    /**
     * return a CertificateChoices object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static CertificateChoices getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato.getObject());
	}

    /**
     * return a CertificateChoices object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static CertificateChoices getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CertificateChoices) {
			return (CertificateChoices)_obj;
		}

		if(_obj instanceof X509CertificateStructure) {
			return new CertificateChoices((X509CertificateStructure)_obj);
		}

		if(_obj instanceof ExtendedCertificate) {
			return new CertificateChoices((ExtendedCertificate)_obj);
		}

		if(_obj instanceof DERObject) {
			return new CertificateChoices((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid CertificateChoices: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getCertificate() {
		return cert;
	}

	public DERObject getDERObject() {
		if(cert instanceof ExtendedCertificate) {
			return new BERTaggedObject(false, 0, cert.getDERObject());
		}

		return cert.getDERObject();
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setCertificate(X509CertificateStructure _cert) {
		cert = _cert;
	}

	private void setCertificate(ExtendedCertificate _cert) {
		cert = _cert;
	}


}
