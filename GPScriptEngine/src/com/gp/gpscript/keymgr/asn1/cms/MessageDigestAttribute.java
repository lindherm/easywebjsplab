package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DEROctetString;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * MessageDigest ::= OCTET STRING
 * </pre>
 *
 *
 */

public class MessageDigestAttribute extends Attribute {

	/*
	 *
	 *  VARIABLES
	 *
	 */


	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	protected MessageDigestAttribute() {
		super();
		attrType = CMSObjectIdentifiers.id_messageDigest;
	}

	public MessageDigestAttribute(ASN1Sequence _seq) {
		this();
		attrValues = (ASN1Set)_seq.getObjectAt(1);
	}

	public MessageDigestAttribute(Attribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public MessageDigestAttribute(byte[] _digest) {
		this();
		setDigest(_digest);
	}

	public MessageDigestAttribute(ASN1OctetString _digest) {
		this();
		setDigest(_digest);
	}

	public MessageDigestAttribute(MessageDigestAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

    /**
     * return a MessageDigestAttribute object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static Attribute getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a MessageDigestAttribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof MessageDigestAttribute) {
			return (MessageDigestAttribute)_obj;
		}

		if(_obj instanceof Attribute) {
			return new MessageDigestAttribute((Attribute)_obj);
		}

		if(_obj instanceof byte[]) {
			return new MessageDigestAttribute((byte[])_obj);
		}

		if(_obj instanceof ASN1OctetString) {
			return new MessageDigestAttribute((ASN1OctetString)_obj);
		}

		if(_obj instanceof ASN1Sequence) {
			return new MessageDigestAttribute((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid MessageDigestAttribute: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getDigest() {
		return ((DEROctetString)attrValues.getObjectAt(0)).getOctets();
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setDigest(ASN1OctetString _digest) {
		attrValues = new DERConstructedSet();
		attrValues.addObject(_digest);
	}

	private void setDigest(byte[] _digest) {
		attrValues = new DERConstructedSet();
		attrValues.addObject(new DEROctetString(_digest));
	}


}
