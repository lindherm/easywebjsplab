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
 * AuthenticatedData ::= SEQUENCE {
 * 	version CMSVersion,
 * 	originatorInfo [0] IMPLICIT OriginatorInfo OPTIONAL,
 * 	recipientInfos RecipientInfos,
 * 	macAlgorithm MessageAuthenticationCodeAlgorithm,
 * 	digestAlgorithm [1] DigestAlgorithmIdentifier OPTIONAL,
 * 	encapContentInfo EncapsulatedContentInfo,
 * 	authenticatedAttributes [2] IMPLICIT AuthAttributes OPTIONAL,
 * 	mac MessageAuthenticationCode,
 * 	unauthenticatedAttributes [3] IMPLICIT UnauthAttributes OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class AuthenticatedData extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion                         version;
	private OriginatorInfo                     originatorInfo;
	private RecipientInfos                     recipientInfos;
	private MessageAuthenticationCodeAlgorithm macAlgorithm;
	private DigestAlgorithmIdentifier          digestAlgorithm;
	private EncapsulatedContentInfo            encapContentInfo;
	private AuthAttributes                     authenticatedAttributes;
	private MessageAuthenticationCode          mac;
	private UnauthAttributes                   unauthenticatedAttributes;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public AuthenticatedData(OriginatorInfo                     _originatorInfo,
	                         RecipientInfos                     _recipientInfos,
	                         MessageAuthenticationCodeAlgorithm _macAlgorithm,
	                         DigestAlgorithmIdentifier          _digestAlgorithm,
	                         EncapsulatedContentInfo            _encapContentInfo,
	                         AuthAttributes                     _authenticatedAttributes,
	                         MessageAuthenticationCode          _mac,
	                         UnauthAttributes                   _unauthenticatedAttributes) {


		setOriginatorInfo(_originatorInfo);
		setRecipientInfos(_recipientInfos);
		setMacAlgorithm(_macAlgorithm);
		setDigestAlgorithm(_digestAlgorithm);
		setEncapContentInfo(_encapContentInfo);
		setAuthenticatedAttributes(_authenticatedAttributes);
		setMac(_mac);
		setUnauthenticatedAttributes(_unauthenticatedAttributes);
		setVersion();
	}




	public AuthenticatedData(ASN1Sequence _seq) {

		int i = 0;
		Object _tmp;

		_tmp = _seq.getObjectAt(i++);
		version = CMSVersion.getInstance(_tmp);

		_tmp = _seq.getObjectAt(i++);
		if(_tmp instanceof ASN1TaggedObject) {
			originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)_tmp, false);
			_tmp = _seq.getObjectAt(i++);
		}
		recipientInfos = RecipientInfos.getInstance(_tmp);

		macAlgorithm = MessageAuthenticationCodeAlgorithm.getInstance(_seq.getObjectAt(i++));

		_tmp = _seq.getObjectAt(i++);
		if(_tmp instanceof ASN1TaggedObject) {
			digestAlgorithm = DigestAlgorithmIdentifier.getInstance((ASN1TaggedObject)_tmp, false);
			_tmp = _seq.getObjectAt(i++);
		}
		encapContentInfo = EncapsulatedContentInfo.getInstance(_tmp);

		_tmp = _seq.getObjectAt(i++);
		if(_tmp instanceof ASN1TaggedObject) {
			authenticatedAttributes = AuthAttributes.getInstance((ASN1TaggedObject)_tmp, false);
			_tmp = _seq.getObjectAt(i++);
		}
		mac = MessageAuthenticationCode.getInstance(_tmp);

		if(_seq.getSize() > i) {
			unauthenticatedAttributes = UnauthAttributes.getInstance((ASN1TaggedObject)_seq.getObjectAt(i++), false);
		}
	}

	public AuthenticatedData(AuthenticatedData _orig) {
		version                   = _orig.version;
		originatorInfo            = _orig.originatorInfo;
		recipientInfos            = _orig.recipientInfos;
		macAlgorithm              = _orig.macAlgorithm;
		digestAlgorithm           = _orig.digestAlgorithm;
		encapContentInfo          = _orig.encapContentInfo;
		authenticatedAttributes   = _orig.authenticatedAttributes;
		mac                       = _orig.mac;
		unauthenticatedAttributes = _orig.unauthenticatedAttributes;
	}

    /**
     * return an AuthenticatedData object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static AuthenticatedData getInstance(
        ASN1TaggedObject    _ato,
        boolean             _explicit)
    {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an AuthenticatedData object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static AuthenticatedData getInstance(Object _obj) {
		if(_obj == null || _obj instanceof AuthenticatedData) {
			return (AuthenticatedData)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new AuthenticatedData((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid AuthenticatedData: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
	}

	public OriginatorInfo getOriginatorInfo() {
		return originatorInfo;
	}

	public RecipientInfos getRecipientInfos() {
		return recipientInfos;
	}

	public MessageAuthenticationCodeAlgorithm getMacAlgorithm() {
		return macAlgorithm;
	}

	public DigestAlgorithmIdentifier getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public EncapsulatedContentInfo getEncapContentInfo() {
		return encapContentInfo;
	}

	public AuthAttributes getAuthenticatedAttributes() {
		return authenticatedAttributes;
	}

	public MessageAuthenticationCode getMac() {
		return mac;
	}

	public UnauthAttributes getUnauthenticatedAttributes() {
		return unauthenticatedAttributes;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(version);

		if(originatorInfo != null) {
			_seq.addObject(new BERTaggedObject(false, 0, originatorInfo.getDERObject()));
		}

		_seq.addObject(recipientInfos);
		_seq.addObject(macAlgorithm);

		if(digestAlgorithm != null) {
			_seq.addObject(new BERTaggedObject(false, 1, digestAlgorithm.getDERObject()));
		}

		_seq.addObject(encapContentInfo);

		if(authenticatedAttributes != null) {
			_seq.addObject(new BERTaggedObject(false, 2, authenticatedAttributes.getDERObject()));
		}

		_seq.addObject(mac);

		if(unauthenticatedAttributes != null) {
			_seq.addObject(new BERTaggedObject(false, 3, unauthenticatedAttributes.getDERObject()));
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
		setVersion(new CMSVersion(new BigInteger("0")));
	}

	private void setOriginatorInfo(OriginatorInfo _originatorInfo) {
		originatorInfo = _originatorInfo;
	}

	private void setRecipientInfos(RecipientInfos _recipientInfos) {
		recipientInfos = _recipientInfos;
	}

	private void setMacAlgorithm(MessageAuthenticationCodeAlgorithm _macAlgorithm) {
		macAlgorithm = _macAlgorithm;
	}

	private void setDigestAlgorithm(DigestAlgorithmIdentifier _digestAlgorithm) {
		digestAlgorithm = _digestAlgorithm;
	}

	private void setEncapContentInfo(EncapsulatedContentInfo _encapContentInfo) {
		encapContentInfo = _encapContentInfo;
	}

	private void setAuthenticatedAttributes(AuthAttributes _authenticatedAttributes) {
		authenticatedAttributes = _authenticatedAttributes;
	}

	private void setMac(MessageAuthenticationCode _mac) {
		mac = _mac;
	}

	private void setUnauthenticatedAttributes(UnauthAttributes _unauthenticatedAttributes) {
		unauthenticatedAttributes = _unauthenticatedAttributes;
	}


}
