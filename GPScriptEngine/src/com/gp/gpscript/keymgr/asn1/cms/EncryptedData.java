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
 * EncryptedData ::= SEQUENCE {
 * 	version CMSVersion,
 * 	encryptedContentInfo EncryptedContentInfo,
 * 	unprotectedAttrs [1] IMPLICIT UnprotectedAttributes OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class EncryptedData extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private CMSVersion            version;
	private EncryptedContentInfo  encryptedContentInfo;
	private UnprotectedAttributes unprotectedAttrs;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EncryptedData(EncryptedContentInfo _encryptedContentInfo,
	                     UnprotectedAttributes _unprotectedAttrs) {

		setEncryptedContentInfo(_encryptedContentInfo);
		setUnprotectedAttrs(_unprotectedAttrs);
		setVersion();
	}

	public EncryptedData(ASN1Sequence _seq) {

		version              = CMSVersion.getInstance(_seq.getObjectAt(0));
		encryptedContentInfo = EncryptedContentInfo.getInstance(_seq.getObjectAt(1));
		if(_seq.getSize() > 2) {
			unprotectedAttrs = UnprotectedAttributes.getInstance((ASN1TaggedObject)_seq.getObjectAt(2), false);
		}
	}

	public EncryptedData(EncryptedData _orig) {
		version              = _orig.version;
		encryptedContentInfo = _orig.encryptedContentInfo;
		unprotectedAttrs     = _orig.unprotectedAttrs;
	}

    /**
     * return an EncryptedData object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EncryptedData getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an EncryptedData object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EncryptedData getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EncryptedData) {
			return (EncryptedData)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new EncryptedData((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid EncryptedData: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public CMSVersion getVersion() {
		return version;
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
		setVersion(new CMSVersion(new BigInteger(unprotectedAttrs == null ? "0" : "2")));
	}

	private void setEncryptedContentInfo(EncryptedContentInfo _encryptedContentInfo) {
		encryptedContentInfo = _encryptedContentInfo;
	}

	private void setUnprotectedAttrs(UnprotectedAttributes _unprotectedAttrs) {
		unprotectedAttrs = _unprotectedAttrs;
	}


}
