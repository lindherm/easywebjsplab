package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERBitString;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;
import com.gp.gpscript.keymgr.asn1.x509.AlgorithmIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * OriginatorPublicKey ::= SEQUENCE {
 * 	algorithm AlgorithmIdentifier,
 * 	publicKey BIT STRING
 * }
 * </pre>
 *
 *
 */

public class OriginatorPublicKey extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private AlgorithmIdentifier algorithm;
	private DERBitString        publicKey;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public OriginatorPublicKey(AlgorithmIdentifier _algorithm, byte[] _publicKey) {
		setAlgorithm(_algorithm);
		setPublicKey(_publicKey);
	}

	public OriginatorPublicKey(ASN1Sequence _seq) {

		Object _tmp = _seq.getObjectAt(0);
		if(_tmp instanceof AlgorithmIdentifier) {
			algorithm = (AlgorithmIdentifier)_tmp;
		}
		else if (_tmp instanceof DERObjectIdentifier) {
			algorithm = new AlgorithmIdentifier((DERObjectIdentifier)_tmp);
		}
		else if (_tmp instanceof ASN1Sequence) {
			algorithm = new AlgorithmIdentifier((ASN1Sequence)_tmp);
		}
		else {
			throw new IllegalArgumentException("Invalid AlgorithmIdentifier");
		}

		publicKey = (DERBitString)_seq.getObjectAt(1);
	}

	public OriginatorPublicKey(OriginatorPublicKey _orig) {
		algorithm = _orig.algorithm;
		publicKey = _orig.publicKey;
	}

    /**
     * return an OriginatorPublicKey object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static OriginatorPublicKey getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return an OriginatorPublicKey object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static OriginatorPublicKey getInstance(Object _obj) {
		if(_obj == null || _obj instanceof OriginatorPublicKey) {
			return (OriginatorPublicKey)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new OriginatorPublicKey((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid OriginatorPublicKey: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public AlgorithmIdentifier getAlgorithm() {
		return algorithm;
	}

	public byte[] getPublicKey() {
		return publicKey.getBytes();
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(algorithm);
		_seq.addObject(publicKey);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setAlgorithm(AlgorithmIdentifier _algorithm) {
		algorithm = _algorithm;
	}

	private void setPublicKey(byte[] _key) {
		publicKey = new DERBitString(_key);
	}


}
