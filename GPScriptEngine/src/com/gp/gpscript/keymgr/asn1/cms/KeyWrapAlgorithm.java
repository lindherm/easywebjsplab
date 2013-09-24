package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;
import com.gp.gpscript.keymgr.asn1.x509.AlgorithmIdentifier;


/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KeyWrapAlgorithm ::= AlgorithmIdentifier
 * </pre>
 *
 *
 */

public class KeyWrapAlgorithm extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private AlgorithmIdentifier alg;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */


	public KeyWrapAlgorithm(AlgorithmIdentifier _alg) {
		setAlgorithmIdentifier(_alg);
	}

	public KeyWrapAlgorithm(ASN1Sequence _obj) {
		alg = new AlgorithmIdentifier(_obj);
	}

	public KeyWrapAlgorithm(KeyWrapAlgorithm _orig) {
		alg = _orig.alg;
	}

	public KeyWrapAlgorithm(DERObjectIdentifier _objectId) {
		alg = new AlgorithmIdentifier(_objectId);
	}

	public KeyWrapAlgorithm(DERObjectIdentifier _objectId, DERObject _parameters) {
		alg = new AlgorithmIdentifier(_objectId, _parameters);
	}

    /**
     * return a KeyWrapAlgorithm object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KeyWrapAlgorithm getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return new KeyWrapAlgorithm(AlgorithmIdentifier.getInstance(_ato, _explicit));
	}

    /**
     * return a KeyWrapAlgorithm object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KeyWrapAlgorithm getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KeyWrapAlgorithm) {
			return (KeyWrapAlgorithm)_obj;
		}

		return new KeyWrapAlgorithm(AlgorithmIdentifier.getInstance(_obj));
	}


	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public AlgorithmIdentifier getAlgorithmIdentifier() {
		return alg;
	}

	public DERObject getDERObject() {
		return alg.getDERObject();
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setAlgorithmIdentifier(AlgorithmIdentifier _alg) {
		alg = _alg;
	}


}
