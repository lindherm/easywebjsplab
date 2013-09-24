package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * OriginatorInfo ::= SEQUENCE {
 * 	certs [0] IMPLICIT CertificateSet OPTIONAL,
 * 	crls [1] IMPLICIT CertificateRevocationLists OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class OriginatorInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CertificateSet             certs;
	private CertificateRevocationLists crls;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public OriginatorInfo(CertificateSet _certs, CertificateRevocationLists _crls) {
		setCertificates(_certs);
		setCrls(_crls);
	}

	public OriginatorInfo(ASN1Sequence _seq) {

		switch(_seq.getSize()) {
			case 0 :
				break;
			case 1 :
				ASN1TaggedObject _dto = (ASN1TaggedObject)_seq.getObjectAt(0);
				switch(_dto.getTagNo()) {
					case 0 :
						certs = CertificateSet.getInstance(_dto, false);
						break;
					case 1 :
						crls = CertificateRevocationLists.getInstance(_dto, false);
						break;
					default:
						throw new IllegalArgumentException("Invalid OriginatorInfo -- Bad tag: " + _dto.getTagNo());
				}
				break;
			case 2 :
				certs = CertificateSet.getInstance((ASN1TaggedObject)_seq.getObjectAt(0), false);
				crls  = CertificateRevocationLists.getInstance((ASN1TaggedObject)_seq.getObjectAt(1), false);
				break;
			default:
				throw new IllegalArgumentException("Invalid OriginatorInfo");
		}
	}

	public OriginatorInfo(OriginatorInfo _orig) {
		certs = _orig.certs;
		crls  = _orig.crls;
	}

    /**
     * return an OriginatorInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static OriginatorInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an OriginatorInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static OriginatorInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof OriginatorInfo) {
			return (OriginatorInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new OriginatorInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid OriginatorInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CertificateSet getCertificates() {
		return certs;
	}


	public CertificateRevocationLists getCrls() {
		return crls;
	}



	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		if(certs != null) {
			_seq.addObject(new BERTaggedObject(false, 0, certs.getDERObject()));
		}

		if(crls != null) {
			_seq.addObject(new BERTaggedObject(false, 1, crls.getDERObject()));
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setCertificates(CertificateSet _certs) {
		certs = _certs;
	}

	private void setCrls(CertificateRevocationLists _crls) {
		crls = _crls;
	}


}
