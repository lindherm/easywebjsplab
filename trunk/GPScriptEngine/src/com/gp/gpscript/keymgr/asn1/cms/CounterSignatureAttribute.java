package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * Countersignature ::= SignerInfo
 * </pre>
 *
 *
 */

public class CounterSignatureAttribute extends Attribute {

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

	protected CounterSignatureAttribute() {
		super();
		attrType = CMSObjectIdentifiers.id_countersignature;
	}

	public CounterSignatureAttribute(ASN1Sequence _seq) {
		super(_seq);
		attrType = CMSObjectIdentifiers.id_countersignature;
	}

	public CounterSignatureAttribute(Attribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public CounterSignatureAttribute(CounterSignatureAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public CounterSignatureAttribute(SignerInfo _signerInfo) {
		this();
		addSigner(_signerInfo);
	}

    /**
     * return a CounterSignatureAttribute object from a tagged object.
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
     * return a CounterSignatureAttribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof CounterSignatureAttribute) {
			return (CounterSignatureAttribute)_obj;
		}

		if(_obj instanceof Attribute) {
			return new CounterSignatureAttribute((Attribute)_obj);
		}

		if(_obj instanceof ASN1Sequence) {
			return new CounterSignatureAttribute((ASN1Sequence)_obj);
		}

		return new CounterSignatureAttribute(SignerInfo.getInstance(_obj));
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public void addSigner(SignerInfo _si) {
		if(attrValues == null) {
			attrValues = new DERConstructedSet();
		}

		attrValues.addObject(_si);
	}

	public Vector getSigners(){

		Vector _res = new Vector();
		int    _len = attrValues.getSize();
		for(int i = 0; i < _len; i++) {
			_res.addElement(SignerInfo.getInstance(attrValues.getObjectAt(i)));
		}

		return _res;
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


}
