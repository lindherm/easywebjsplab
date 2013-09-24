package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedOctetString;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * EncapsulatedContentInfo ::= SEQUENCE {
 * 	eContentType ContentType,
 * 	eContent [0] EXPLICIT OCTET STRING OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class EncapsulatedContentInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ContentType     eContentType;
	private ASN1OctetString eContent;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EncapsulatedContentInfo(ContentType _eContentType, byte[] _eContent) {
		setContentType(_eContentType);
		setContent(_eContent);
	}

	public EncapsulatedContentInfo(ASN1Sequence _seq) {

		eContentType = (ContentType)ContentType.getInstance(_seq.getObjectAt(0));
		if(_seq.getSize() > 1) {
			eContent = ASN1OctetString.getInstance((ASN1TaggedObject)_seq.getObjectAt(1), true);
		}
	}

	public EncapsulatedContentInfo(EncapsulatedContentInfo _orig) {
		eContentType = _orig.eContentType;
		eContent     = _orig.eContent;
	}

    /**
     * return an EncapsulatedContentInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EncapsulatedContentInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an EncapsulatedContentInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EncapsulatedContentInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EncapsulatedContentInfo) {
			return (EncapsulatedContentInfo)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new EncapsulatedContentInfo((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid EncapsulatedContentInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getContentBytes() {
		return eContent.getOctets();
	}

	public ASN1OctetString getContent() {
		return eContent;
	}

	public ContentType getContentType() {
		return eContentType;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(eContentType);
		if(eContent != null) {
			_seq.addObject(new BERTaggedObject(true, 0, eContent));
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setContent(byte[] _eContent) {
		if(_eContent == null) {
			eContent = null;
		}
		else {
			eContent = new BERConstructedOctetString(_eContent);
		}
	}

	private void setContentType(ContentType _eContentType) {
		eContentType = _eContentType;
	}


}
