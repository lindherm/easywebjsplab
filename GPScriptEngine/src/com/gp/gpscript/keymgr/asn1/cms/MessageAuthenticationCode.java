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
 * MessageAuthenticationCode ::= OCTET STRING
 * </pre>
 *
 *
 */

public class MessageAuthenticationCode extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString mac;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public MessageAuthenticationCode(byte[] _mac) {
		setMac(_mac);
	}

	public MessageAuthenticationCode(ASN1OctetString _mac) {
		mac = _mac;
	}

	public MessageAuthenticationCode(MessageAuthenticationCode _orig) {
		mac = _orig.mac;
	}

    /**
     * return a MessageAuthenticationCode object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static MessageAuthenticationCode getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1OctetString.getInstance(_ato, _explicit));
	}

    /**
     * return a MessageAuthenticationCode object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static MessageAuthenticationCode getInstance(Object _obj) {
		if(_obj == null || _obj instanceof MessageAuthenticationCode) {
			return (MessageAuthenticationCode)_obj;
		}

		if(_obj instanceof ASN1OctetString) {
			return new MessageAuthenticationCode((ASN1OctetString)_obj);
		}

		throw new IllegalArgumentException("Invalid MessageAuthenticationCode: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getMac() {
		return mac.getOctets();
	}

	public DERObject getDERObject() {
		return mac;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setMac(byte[] _mac) {
		mac = new BERConstructedOctetString(_mac);
	}


}
