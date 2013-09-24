package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * DigestAlgorithmIdentifiers ::= SET OF DigestAlgorithmIdentifier
 * </pre>
 *
 *
 */

public class DigestAlgorithmIdentifiers extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set identifiers;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public DigestAlgorithmIdentifiers(Vector _identifiers) {

		setIdentifiers(_identifiers);
	}

	public DigestAlgorithmIdentifiers(ASN1Set _identifiers) {
		identifiers = _identifiers;
	}

	public DigestAlgorithmIdentifiers(DigestAlgorithmIdentifiers _orig) {
		identifiers = _orig.identifiers;
	}

    /**
     * return a DigestAlgorithmIdentifiers object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static DigestAlgorithmIdentifiers getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a DigestAlgorithmIdentifiers object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static DigestAlgorithmIdentifiers getInstance(Object _obj) {
		if(_obj == null || _obj instanceof DigestAlgorithmIdentifiers) {
			return (DigestAlgorithmIdentifiers)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new DigestAlgorithmIdentifiers((ASN1Set)_obj);
		}

		throw new IllegalArgumentException("Invalid DigestAlgorithmIdentifiers: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getIdentifiers() {

		int    _len = identifiers.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(DigestAlgorithmIdentifier.getInstance(identifiers.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return identifiers;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setIdentifiers(Vector _identifiers) {

		int _len   = _identifiers.size();
		identifiers = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			identifiers.addObject(DigestAlgorithmIdentifier.getInstance(_identifiers.elementAt(i)));
		}
	}


}
