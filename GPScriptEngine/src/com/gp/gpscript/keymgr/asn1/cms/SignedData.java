package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;
import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.util.ASN1Dump;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * SignedData ::= SEQUENCE {
 * 	version CMSVersion,
 * 	digestAlgorithms DigestAlgorithmIdentifiers,
 * 	encapContentInfo EncapsulatedContentInfo,
 * 	certificates [0] IMPLICIT CertificateSet OPTIONAL,
 * 	crls [1] IMPLICIT CertificateRevocationLists OPTIONAL,
 * 	signerInfos SignerInfos
 * }
 * </pre>
 *
 *
 */

public class SignedData extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                 version;
	private DigestAlgorithmIdentifiers digestAlgorithms;
	private EncapsulatedContentInfo    encapContentInfo;
	private CertificateSet             certificates;
	private CertificateRevocationLists crls;
	private SignerInfos                signerInfos;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SignedData(String _contentType, byte[] _content) {

		setDigestAlgorithms(new DigestAlgorithmIdentifiers(new Vector()));
		setEncapContentInfo(new EncapsulatedContentInfo(new ContentType(_contentType), _content));
		setCertificates(null);
		setCrls(null);
		setSignerInfos(new SignerInfos(new Vector()));
		setVersion();
	}

	public SignedData(DigestAlgorithmIdentifiers _digestAlgorithms,
					  EncapsulatedContentInfo    _encapContentInfo,
					  CertificateSet             _certificates,
					  CertificateRevocationLists _crls,
					  SignerInfos                _signerInfos) {

		setDigestAlgorithms(_digestAlgorithms);
		setEncapContentInfo(_encapContentInfo);
		setCertificates(_certificates);
		setCrls(_crls);
		setSignerInfos(_signerInfos);
		setVersion();
	}

	public SignedData(ASN1Sequence _seq) {

		int i = 0;
		version          = CMSVersion.getInstance(_seq.getObjectAt(i++));
		digestAlgorithms = DigestAlgorithmIdentifiers.getInstance(_seq.getObjectAt(i++));
		encapContentInfo = EncapsulatedContentInfo.getInstance(_seq.getObjectAt(i++));

		switch(_seq.getSize()) {
			case 4 :
				break;
			case 5 :
				ASN1TaggedObject _dto = (ASN1TaggedObject)_seq.getObjectAt(i++);
				switch(_dto.getTagNo()) {
					case 0 :
						certificates = CertificateSet.getInstance(_dto, false);
						break;
					case 1 :
						crls = CertificateRevocationLists.getInstance(_dto, false);
						break;
					default:
						throw new IllegalArgumentException("Invalid SignedData -- Bad tag: " + _dto.getTagNo());
				}
				break;
			case 6 :
				certificates = CertificateSet.getInstance((ASN1TaggedObject)_seq.getObjectAt(i++), false);
				crls         = CertificateRevocationLists.getInstance((ASN1TaggedObject)_seq.getObjectAt(i++), false);
				break;
			default:
				throw new IllegalArgumentException("Invalid SignedData");
		}

		signerInfos = SignerInfos.getInstance(_seq.getObjectAt(i));
	}

	public SignedData(SignedData _orig) {
		version          = _orig.version;
		digestAlgorithms = _orig.digestAlgorithms;
		encapContentInfo = _orig.encapContentInfo;
		certificates     = _orig.certificates;
		crls             = _orig.crls;
		signerInfos      = _orig.signerInfos;
	}

    /**
     * return a SignedData object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static SignedData getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a SignedData object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static SignedData getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SignedData) {
			return (SignedData)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new SignedData((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid SignedData: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public DigestAlgorithmIdentifiers getDigestAlgorithms() {
		return digestAlgorithms;
	}

	public EncapsulatedContentInfo getEncapContentInfo() {
		return encapContentInfo;
	}

	public CertificateSet getCertificates() {
		return certificates;
	}

	public CertificateRevocationLists getCrls() {
		return crls;
	}

	public SignerInfos getSignerInfos() {
		return signerInfos;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(digestAlgorithms);
		_seq.addObject(encapContentInfo);

		if(certificates != null) {
			_seq.addObject(new BERTaggedObject(false, 0, certificates.getDERObject()));
		}

		if(crls != null) {
			_seq.addObject(new BERTaggedObject(false, 1, crls.getDERObject()));
		}

		_seq.addObject(signerInfos);
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
		setVersion(new CMSVersion(new BigInteger("1")));
	}

	private void setDigestAlgorithms(DigestAlgorithmIdentifiers _digestAlgorithms) {
		digestAlgorithms = _digestAlgorithms;
	}

	private void setEncapContentInfo(EncapsulatedContentInfo _encapContentInfo) {
		encapContentInfo = _encapContentInfo;
	}

	private void setCertificates(CertificateSet _certificates) {
		certificates = _certificates;
	}

	private void setCrls(CertificateRevocationLists _crls) {
		crls = _crls;
	}

	private void setSignerInfos(SignerInfos _signerInfos) {
		signerInfos = _signerInfos;
	}





}
