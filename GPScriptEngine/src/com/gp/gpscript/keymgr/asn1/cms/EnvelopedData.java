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
 * EnvelopedData ::= SEQUENCE {
 * 	version CMSVersion,
 * 	originatorInfo [0] IMPLICIT OriginatorInfo OPTIONAL,
 * 	recipientInfos RecipientInfos,
 * 	encryptedContentInfo EncryptedContentInfo,
 * 	unprotectedAttrs [1] IMPLICIT UnprotectedAttributes OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class EnvelopedData extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion            version;
	private OriginatorInfo        originatorInfo;
	private RecipientInfos        recipientInfos;
	private EncryptedContentInfo  encryptedContentInfo;
	private UnprotectedAttributes unprotectedAttrs;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EnvelopedData(OriginatorInfo        _originatorInfo,
	                     RecipientInfos        _recipientInfos,
	                     EncryptedContentInfo  _encryptedContentInfo,
	                     UnprotectedAttributes _unprotectedAttrs) {

		setOriginatorInfo(_originatorInfo);
		setRecipientInfos(_recipientInfos);
		setEncryptedContentInfo(_encryptedContentInfo);
		setUnprotectedAttrs(_unprotectedAttrs);
		setVersion();
	}

	public EnvelopedData(ASN1Sequence _seq) {

		Object _tmp;
		int i = 0;

		version = CMSVersion.getInstance(_seq.getObjectAt(i++));

		_tmp = _seq.getObjectAt(i++);
		if(_tmp instanceof ASN1TaggedObject) {
			originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)_tmp, false);
			_tmp = _seq.getObjectAt(i++);
		}
		recipientInfos = RecipientInfos.getInstance(_tmp);

		encryptedContentInfo = EncryptedContentInfo.getInstance(_seq.getObjectAt(i++));

		if(_seq.getSize() > i) {
			unprotectedAttrs = UnprotectedAttributes.getInstance((ASN1TaggedObject)_seq.getObjectAt(i), false);
		}

	}

	public EnvelopedData(EnvelopedData _orig) {
		originatorInfo       = _orig.originatorInfo;
		recipientInfos       = _orig.recipientInfos;
		encryptedContentInfo = _orig.encryptedContentInfo;
		unprotectedAttrs     = _orig.unprotectedAttrs;
	}

    /**
     * return an EnvelopedData object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EnvelopedData getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an EnvelopedData object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EnvelopedData getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EnvelopedData) {
			return (EnvelopedData)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new EnvelopedData((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid EnvelopedData: " + _obj.getClass().getName());
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

	public EncryptedContentInfo getEncryptedContentInfo() {
		return encryptedContentInfo;
	}

	public UnprotectedAttributes getUnprotectedAttrs() {
		return unprotectedAttrs;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();

		_seq.addObject(version);
		if(originatorInfo != null) {
			_seq.addObject(new BERTaggedObject(false, 0, originatorInfo.getDERObject()));
		}
		_seq.addObject(recipientInfos);
		_seq.addObject(encryptedContentInfo);
		if(unprotectedAttrs != null) {
			_seq.addObject(new BERTaggedObject(false, 1, unprotectedAttrs.getDERObject()));
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
		if(originatorInfo != null || unprotectedAttrs != null) {
			setVersion(new CMSVersion(new BigInteger("2")));
		}
		else {
			setVersion(new CMSVersion(new BigInteger("0")));
		}
	}

	private void setOriginatorInfo(OriginatorInfo _originatorInfo) {
		originatorInfo = _originatorInfo;
	}

	private void setRecipientInfos(RecipientInfos _recipientInfos) {
		recipientInfos = _recipientInfos;
	}

	private void setEncryptedContentInfo(EncryptedContentInfo _encryptedContentInfo) {
		encryptedContentInfo = _encryptedContentInfo;
	}

	private void setUnprotectedAttrs(UnprotectedAttributes _unprotectedAttrs) {
		unprotectedAttrs = _unprotectedAttrs;
	}


}
