package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedOctetString;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * EncryptedContent ::= OCTET STRING
 * </pre>
 *
 *
 */

public class EncryptedContent extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString content;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public EncryptedContent(byte[] _content) {
		setContent(_content);
	}

	public EncryptedContent(ASN1OctetString _content) {
		content = _content;
	}

	public EncryptedContent(EncryptedContent _orig) {
		content = _orig.content;
	}

    /**
     * return an EncryptedContent object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static EncryptedContent getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return an EncryptedContent object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static EncryptedContent getInstance(Object _obj) {
		if(_obj == null || _obj instanceof EncryptedContent) {
			return (EncryptedContent)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new EncryptedContent((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid EncryptedContent: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getContent() {
		return content.getOctets();
	}

	public DERObject getDERObject() {
		return content;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setContent(byte[] _content) {
		content = new BERConstructedOctetString(_content);
	}


}
