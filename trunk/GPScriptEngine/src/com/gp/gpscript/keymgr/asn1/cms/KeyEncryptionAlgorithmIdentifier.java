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
 * KeyEncryptionAlgorithmIdentifier ::= AlgorithmIdentifier
 * </pre>
 *
 *
 */

public class KeyEncryptionAlgorithmIdentifier extends CMSObject {

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

	public KeyEncryptionAlgorithmIdentifier(AlgorithmIdentifier _alg) {
		setAlgorithmIdentifier(_alg);
	}

	public KeyEncryptionAlgorithmIdentifier(ASN1Sequence _obj) {
		this(new AlgorithmIdentifier(_obj));
	}

	public KeyEncryptionAlgorithmIdentifier(KeyEncryptionAlgorithmIdentifier _orig) {
		alg = _orig.alg;
	}

	public KeyEncryptionAlgorithmIdentifier(DERObjectIdentifier _objectId) {
		this(new AlgorithmIdentifier(_objectId));
	}

	public KeyEncryptionAlgorithmIdentifier(DERObjectIdentifier _objectId, DERObject _parameters) {
		this(new AlgorithmIdentifier(_objectId, _parameters));
	}

	public KeyEncryptionAlgorithmIdentifier(String _objectId) {
		this(new AlgorithmIdentifier(new DERObjectIdentifier(_objectId)));
	}

    /**
     * return a KeyEncryptionAlgorithmIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KeyEncryptionAlgorithmIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return new KeyEncryptionAlgorithmIdentifier(AlgorithmIdentifier.getInstance(_ato, _explicit));
	}

    /**
     * return a KeyEncryptionAlgorithmIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KeyEncryptionAlgorithmIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KeyEncryptionAlgorithmIdentifier) {
			return (KeyEncryptionAlgorithmIdentifier)_obj;
		}

		return new KeyEncryptionAlgorithmIdentifier(AlgorithmIdentifier.getInstance(_obj));
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
