package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
*	RFC 2630
*
*
*	ExtendedCertificate ::= SEQUENCE {
*		extendedCertificateInfo ExtendedCertificateInfo,
*		signatureAlgorithm SignatureAlgorithmIdentifier,
*		signature Signature
*	}
*/
public class ExtendedCertificate extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ExtendedCertificateInfo      extendedCertificateInfo;
	private SignatureAlgorithmIdentifier signatureAlgorithm;
	private Signature                    signature;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public ExtendedCertificate(ASN1Sequence _seq) {

		extendedCertificateInfo = ExtendedCertificateInfo.getInstance(_seq.getObjectAt(0));
		signatureAlgorithm      = SignatureAlgorithmIdentifier.getInstance(_seq.getObjectAt(1));
		signature               = Signature.getInstance(_seq.getObjectAt(2));
	}

	public ExtendedCertificate(ExtendedCertificate _cert) {
		extendedCertificateInfo = _cert.extendedCertificateInfo;
		signatureAlgorithm      = _cert.signatureAlgorithm;
		signature               = _cert.signature;
	}

    /**
     * return an ExtendedCertificate object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static ExtendedCertificate getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an ExtendedCertificate object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static ExtendedCertificate getInstance(Object _obj) {
		if(_obj == null || _obj instanceof ExtendedCertificate) {
			return (ExtendedCertificate)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new ExtendedCertificate((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid ExtendedCertificate: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DERObject getDERObject() {
		DERConstructedSequence _seq = new DERConstructedSequence();
		_seq.addObject(extendedCertificateInfo);
		_seq.addObject(signatureAlgorithm);
		_seq.addObject(signature);
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
