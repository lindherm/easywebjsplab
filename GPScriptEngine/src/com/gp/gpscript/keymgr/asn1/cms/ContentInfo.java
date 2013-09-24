package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * ContentInfo ::= SEQUENCE {
 * 	contentType ContentType,
 * 	content [0] EXPLICIT ANY DEFINED BY contentType
 * }
 * </pre>
 *
 *
 */

public class ContentInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ContentType  contentType;
	private DEREncodable content;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public ContentInfo(ContentType _contentType, DEREncodable _content) {
		setContentType(_contentType);
		setContent(_content);
	}

	public ContentInfo(ASN1Sequence _seq) {

		contentType = (ContentType)ContentType.getInstance(_seq.getObjectAt(0));
		if(_seq.getSize() > 1) {
			content = (DEREncodable)(((ASN1TaggedObject)_seq.getObjectAt(1)).getObject());
		}
	}

	public ContentInfo(ContentInfo _orig) {
		contentType = _orig.contentType;
		content     = _orig.content;
	}

    /**
     * return an ContentInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static ContentInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a ContentInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static ContentInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof ContentInfo) {
			return (ContentInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new ContentInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid ContentInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public ContentType getContentType() {
		return contentType;
	}

	public DEREncodable getContent() {
		return content;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(contentType);
		if(content != null) {
			_seq.addObject(new BERTaggedObject(true, 0, content.getDERObject()));
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

	private void setContent(DEREncodable _content) {
		content = _content;
	}

}
