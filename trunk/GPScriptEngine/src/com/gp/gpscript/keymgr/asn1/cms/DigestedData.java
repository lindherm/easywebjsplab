package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * DigestedData ::= SEQUENCE {
 * 	version CMSVersion,
 * 	digestAlgorithm DigestAlgorithmIdentifier,
 * 	encapContentInfo EncapsulatedContentInfo,
 * 	digest Digest
 * }
 * </pre>
 *
 *
 */

public class DigestedData extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                version;
	private DigestAlgorithmIdentifier digestAlgorithm;
	private EncapsulatedContentInfo   encapContentInfo;
	private Digest                    digest;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public DigestedData(DigestAlgorithmIdentifier _digestAlgorithm,
						EncapsulatedContentInfo   _encapContentInfo,
						Digest                    _digest) {

		setDigestAlgorithm(_digestAlgorithm);
		setEncapContentInfo(_encapContentInfo);
		setDigest(_digest);
		setVersion();
	}

	public DigestedData(ASN1Sequence _seq) {

		version          = CMSVersion.getInstance(_seq.getObjectAt(0));
		digestAlgorithm  = DigestAlgorithmIdentifier.getInstance(_seq.getObjectAt(1));
		encapContentInfo = EncapsulatedContentInfo.getInstance(_seq.getObjectAt(2));
		digest           = Digest.getInstance(_seq.getObjectAt(2));
	}

	public DigestedData(DigestedData _orig) {
		version          = _orig.version;
		digestAlgorithm  = _orig.digestAlgorithm;
		encapContentInfo = _orig.encapContentInfo;
		digest           = _orig.digest;
	}

    /**
     * return a DigestData object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static DigestedData getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a DigestedData object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static DigestedData getInstance(Object _obj) {
		if(_obj == null || _obj instanceof DigestedData) {
			return (DigestedData)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new DigestedData((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid DigestedData: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public DigestAlgorithmIdentifier getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public EncapsulatedContentInfo getEncapContentInfo() {
		return encapContentInfo;
	}

	public Digest getDigest() {
		return digest;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);
		_seq.addObject(digestAlgorithm);
		_seq.addObject(encapContentInfo);
		_seq.addObject(digest);
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
		if(encapContentInfo.getContentType().getContentType().equals(CMSObjectIdentifiers.id_data.getId())) {
			setVersion(new CMSVersion(new BigInteger("0")));
		}
		else {
			setVersion(new CMSVersion(new BigInteger("2")));
		}
	}

	private void setDigestAlgorithm(DigestAlgorithmIdentifier _digestAlgorithm) {
		digestAlgorithm = _digestAlgorithm;
	}

	private void setEncapContentInfo(EncapsulatedContentInfo _encapContentInfo) {
		encapContentInfo = _encapContentInfo;
	}

	private void setDigest(Digest _digest) {
		digest = _digest;
	}


}
