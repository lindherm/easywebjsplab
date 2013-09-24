package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * EncryptedContentInfo ::= SEQUENCE {
 * 	contentType ContentType,
 * 	contentEncryptionAlgorithm ContentEncryptionAlgorithmIdentifier,
 * 	encryptedContent [0] IMPLICIT EncryptedContent OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class EncryptedContentInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ContentType                          contentType;
	private ContentEncryptionAlgorithmIdentifier contentEncryptionAlgorithm;
	private EncryptedContent                     encryptedContent;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EncryptedContentInfo(ContentType                          _contentType,
	                            ContentEncryptionAlgorithmIdentifier _contentEncryptionAlgorithm,
	                            EncryptedContent                     _encryptedContent) {

		setContentType(_contentType);
		setContentEncryptionAlgorithm(_contentEncryptionAlgorithm);
		setEncryptedContent(_encryptedContent);
	}

	public EncryptedContentInfo(ASN1Sequence _seq) {

		contentType = (ContentType)ContentType.getInstance(_seq.getObjectAt(0));
		contentEncryptionAlgorithm = ContentEncryptionAlgorithmIdentifier.getInstance(_seq.getObjectAt(1));
		if(_seq.getSize() > 2) {
			encryptedContent = EncryptedContent.getInstance((ASN1TaggedObject)_seq.getObjectAt(2), false);
		}
	}

	public EncryptedContentInfo(EncryptedContentInfo _orig) {
		contentType                = _orig.contentType;
		contentEncryptionAlgorithm = _orig.contentEncryptionAlgorithm;
		encryptedContent           = _orig.encryptedContent;
	}

    /**
     * return an EncryptedContentInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EncryptedContentInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an EncryptedContentInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EncryptedContentInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EncryptedContentInfo) {
			return (EncryptedContentInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new EncryptedContentInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid EncryptedContentInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public ContentType getContentType() {
		return contentType;
	}

	public ContentEncryptionAlgorithmIdentifier getContentEncryptionAlgorithm() {
		return contentEncryptionAlgorithm;
	}

	public EncryptedContent getEncryptedContent() {
		return encryptedContent;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();

		_seq.addObject(contentType);
		_seq.addObject(contentEncryptionAlgorithm);
		if(encryptedContent != null) {
			_seq.addObject(new BERTaggedObject(false, 0, encryptedContent.getDERObject()));
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setContentType(ContentType _contentType) {
		contentType = _contentType;
	}

	private void setContentEncryptionAlgorithm(ContentEncryptionAlgorithmIdentifier _contentEncryptionAlgorithm) {
		contentEncryptionAlgorithm = _contentEncryptionAlgorithm;
	}

	private void setEncryptedContent(EncryptedContent _encryptedContent) {
		encryptedContent = _encryptedContent;
	}


}
