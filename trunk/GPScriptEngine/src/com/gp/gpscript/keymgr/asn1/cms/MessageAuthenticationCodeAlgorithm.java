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
 * MessageAuthenticationCodeAlgorithm ::= AlgorithmIdentifier
 * </pre>
 *
 *
 */

public class MessageAuthenticationCodeAlgorithm extends CMSObject{

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

	public MessageAuthenticationCodeAlgorithm(AlgorithmIdentifier _alg) {
		setAlgorithmIdentifier(_alg);
	}

	public MessageAuthenticationCodeAlgorithm(ASN1Sequence _obj) {
		this(new AlgorithmIdentifier(_obj));
	}

	public MessageAuthenticationCodeAlgorithm(MessageAuthenticationCodeAlgorithm _orig) {
		alg = _orig.alg;
	}

	public MessageAuthenticationCodeAlgorithm(DERObjectIdentifier _objectId) {
		this(new AlgorithmIdentifier(_objectId));
	}

	public MessageAuthenticationCodeAlgorithm(String _objectId) {
		this(new AlgorithmIdentifier(new DERObjectIdentifier(_objectId)));
	}

	public MessageAuthenticationCodeAlgorithm(DERObjectIdentifier _objectId, DERObject _parameters) {
		this(new AlgorithmIdentifier(_objectId, _parameters));
	}

    /**
     * return a MessageAuthenticationCodeAlgorithm object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static MessageAuthenticationCodeAlgorithm getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return new MessageAuthenticationCodeAlgorithm(AlgorithmIdentifier.getInstance(_ato, _explicit));
	}

    /**
     * return a MessageAuthenticationCodeAlgorithm object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static MessageAuthenticationCodeAlgorithm getInstance(Object _obj) {
		if(_obj == null || _obj instanceof MessageAuthenticationCodeAlgorithm) {
			return (MessageAuthenticationCodeAlgorithm)_obj;
		}

		return new MessageAuthenticationCodeAlgorithm(AlgorithmIdentifier.getInstance(_obj));
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
