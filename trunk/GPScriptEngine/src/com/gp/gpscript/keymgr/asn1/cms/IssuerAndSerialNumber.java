package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERInteger;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.x509.X509Name;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * IssuerAndSerialNumber ::= SEQUENCE {
 * 	issuer Name,
 * 	serialNumber CertificateSerialNumber
 * }
 * </pre>
 *
 *
 */

public class IssuerAndSerialNumber extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private X509Name   issuer;
	private DERInteger serialNumber;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public IssuerAndSerialNumber(X509Name _issuer, BigInteger _serialNumber) {
		setIssuer(_issuer);
		setSerialNumber(_serialNumber);
	}

	public IssuerAndSerialNumber(ASN1Sequence _seq) {
		issuer = X509Name.getInstance(_seq.getObjectAt(0));
		serialNumber = (DERInteger)_seq.getObjectAt(1);
	}

	public IssuerAndSerialNumber(IssuerAndSerialNumber _orig) {
		issuer       = _orig.issuer;
		serialNumber = _orig.serialNumber;
	}

    /**
     * return an IssuerAndSerialNumber object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static IssuerAndSerialNumber getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an IssuerAndSerialNumber object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static IssuerAndSerialNumber getInstance(Object _obj) {
		if(_obj == null || _obj instanceof IssuerAndSerialNumber) {
			return (IssuerAndSerialNumber)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new IssuerAndSerialNumber((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid IssuerAndSerialNumber: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public X509Name getIssuer() {
		return issuer;
	}

	public BigInteger getSerialNumber() {
		return serialNumber.getValue();
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(issuer);
		_seq.addObject(serialNumber);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setIssuer(X509Name _issuer) {
		issuer = _issuer;
	}

	private void setSerialNumber(BigInteger _serialNumber) {
		serialNumber = new DERInteger(_serialNumber);
	}


}
