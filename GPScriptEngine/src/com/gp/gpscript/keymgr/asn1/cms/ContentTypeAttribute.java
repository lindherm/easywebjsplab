package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
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

public class ContentTypeAttribute extends Attribute {

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

	protected ContentTypeAttribute() {
		super();
		attrType = CMSObjectIdentifiers.id_contentType;
	}

	protected ContentTypeAttribute(ASN1Sequence _seq) {
		this();
		attrValues = (DERConstructedSet)_seq.getObjectAt(1);
	}

	public ContentTypeAttribute(Attribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public ContentTypeAttribute(ContentTypeAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public ContentTypeAttribute(DERObjectIdentifier _contentType) {
		this();
		attrValues = new DERConstructedSet();
		attrValues.addObject(_contentType);
	}

	public ContentTypeAttribute(String _type) {
		this();
		setContentType(_type);
	}

    /**
     * return a ContentTypeAttribute object from a tagged object.
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
     * return a ContentTypeAttribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof ContentTypeAttribute) {
			return (ContentTypeAttribute)_obj;
		}

		if(_obj instanceof Attribute) {
			return new ContentTypeAttribute((Attribute)_obj);
		}

		if(_obj instanceof DERObjectIdentifier) {
			return new ContentTypeAttribute((DERObjectIdentifier)_obj);
		}

		if(_obj instanceof ASN1Sequence) {
			return new ContentTypeAttribute((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid ContentType: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public String getContentType() {
		return (DERObjectIdentifier.getInstance(attrValues.getObjectAt(0))).getId();
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setContentType(String _type) {
		attrValues = new DERConstructedSet();
		attrValues.addObject(new DERObjectIdentifier(_type));
	}


}
