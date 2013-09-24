package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

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
 * SignerInfo ::= SEQUENCE {
 * 	version CMSVersion,
 * 	sid SignerIdentifier,
 * 	digestAlgorithm DigestAlgorithmIdentifier,
 * 	signedAttrs [0] IMPLICIT SignedAttributes OPTIONAL,
 * 	signatureAlgorithm SignatureAlgorithmIdentifier,
 * 	signature SignatureValue,
 * 	unsignedAttrs [1] IMPLICIT UnsignedAttributes OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class SignerInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                   version;
	private SignerIdentifier             sid;
	private DigestAlgorithmIdentifier    digestAlgorithm;
	private SignedAttributes             signedAttrs;
	private SignatureAlgorithmIdentifier signatureAlgorithm;
	private SignatureValue               signature;
	private UnsignedAttributes           unsignedAttrs;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SignerInfo(SignerIdentifier             _sid,
					  DigestAlgorithmIdentifier    _digestAlgorithm,
					  SignedAttributes             _signedAttrs,
					  SignatureAlgorithmIdentifier _signatureAlgorithm,
					  SignatureValue               _signature,
					  UnsignedAttributes           _unsignedAttrs) {

		setSid(_sid);
		setDigestAlgorithm(_digestAlgorithm);
		setSignedAttrs(_signedAttrs);
		setSignatureAlgorithm(_signatureAlgorithm);
		setSignature(_signature);
		setUnsignedAttrs(_unsignedAttrs);
		setVersion();
	}

	public SignerInfo(ASN1Sequence _seq) {

		int i = 0;
		Object _tmp;

		version         = CMSVersion.getInstance(_seq.getObjectAt(i++));
		sid             = SignerIdentifier.getInstance(_seq.getObjectAt(i++));
		digestAlgorithm = DigestAlgorithmIdentifier.getInstance(_seq.getObjectAt(i++));

		_tmp = _seq.getObjectAt(i++);
		if(_tmp instanceof ASN1TaggedObject) {
			signedAttrs = SignedAttributes.getInstance((ASN1TaggedObject)_tmp, false);
			_tmp = _seq.getObjectAt(i++);
		}

		signatureAlgorithm = SignatureAlgorithmIdentifier.getInstance(_tmp);
		signature          = SignatureValue.getInstance(_seq.getObjectAt(i++));

		if(_seq.getSize() > i) {
			unsignedAttrs = UnsignedAttributes.getInstance((ASN1TaggedObject)_seq.getObjectAt(i), false);
		}
	}

	public SignerInfo(SignerInfo _orig) {
		version            = _orig.version;
		sid                = _orig.sid;
		digestAlgorithm    = _orig.digestAlgorithm;
		signedAttrs        = _orig.signedAttrs;
		signatureAlgorithm = _orig.signatureAlgorithm;
		signature          = _orig.signature;
		unsignedAttrs      = _orig.unsignedAttrs;
	}

    /**
     * return a SignerInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SignerInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a SignerInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SignerInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SignerInfo) {
			return (SignerInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new SignerInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid SignerInfo: " + _obj.getClass().getName());
	}


	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public SignerIdentifier getSid() {
		return sid;
	}

	public DigestAlgorithmIdentifier getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public SignedAttributes getSignedAttrs() {
		return signedAttrs;
	}

	public SignatureAlgorithmIdentifier getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public SignatureValue getSignature() {
		return signature;
	}

	public UnsignedAttributes getUnsignedAttrs() {
		return unsignedAttrs;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(sid);
		_seq.addObject(digestAlgorithm);

		if(signedAttrs != null) {
			_seq.addObject(new BERTaggedObject(false, 0, signedAttrs.getDERObject()));
		}

		_seq.addObject(signatureAlgorithm);
		_seq.addObject(signature);

		if(unsignedAttrs != null) {
			_seq.addObject(new BERTaggedObject(false, 1, unsignedAttrs.getDERObject()));
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setVersion(CMSVersion _version) {
		version = _version;
	}

	private void setVersion() {
		Object _obj = sid.getId();
		if(_obj instanceof IssuerAndSerialNumber) {
			setVersion(new CMSVersion(new BigInteger("1")));
		}
		else if(_obj instanceof SubjectKeyIdentifier) {
			setVersion(new CMSVersion(new BigInteger("3")));
		}
	}

	private void setSid(SignerIdentifier _sid) {
		sid = _sid;
	}

	private void setDigestAlgorithm(DigestAlgorithmIdentifier _digestAlgorithm) {
		digestAlgorithm = _digestAlgorithm;
	}

	private void setSignedAttrs(SignedAttributes _signedAttrs) {
		signedAttrs = _signedAttrs;
	}

	private void setSignatureAlgorithm(SignatureAlgorithmIdentifier _signatureAlgorithm) {
		signatureAlgorithm = _signatureAlgorithm;
	}

	private void setSignature(SignatureValue _signature) {
		signature = _signature;
	}

	private void setUnsignedAttrs(UnsignedAttributes _unsignedAttrs) {
		unsignedAttrs = _unsignedAttrs;
	}
}
