package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * ContentType ::= OBJECT IDENTIFIER
 * </pre>
 *
 *
 */

public class ContentType implements DEREncodable {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	DERObjectIdentifier type;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public ContentType(DERObjectIdentifier _type) {
		type = _type;
	}

	public ContentType(String _type) {
		setContentType(_type);
	}

	public ContentType(ContentType _orig) {
		type = _orig.type;
	}

    /**
     * return a ContentType object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static ContentType getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(DERObjectIdentifier.getInstance(_ato, _explicit));
	}

    /**
     * return a ContentType object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static ContentType getInstance(Object _obj) {
		if(_obj == null || _obj instanceof ContentType) {
			return (ContentType)_obj;
		}

		if(_obj instanceof DERObjectIdentifier) {
			return new ContentType((DERObjectIdentifier)_obj);
		}

		if(_obj instanceof String) {
			return new ContentType((String)_obj);
		}

		throw new IllegalArgumentException("Invalid ContentType: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public String getContentType() {
		return type.getId();
	}

	public DERObject getDERObject() {
		return type;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setContentType(String _type) {
		type = new DERObjectIdentifier(_type);
	}


}
