package com.gp.gpscript.keymgr.asn1.cms;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedOctetString;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERInteger;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RC2CBCParameter ::= SEQUENCE {
 * 	rc2ParameterVersion INTEGER,
 * 	iv OCTET STRING  -- exactly 8 octets
 * }
 * </pre>
 *
 *
 */

public class RC2CBCParameter extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERInteger      rc2ParameterVersion;
	private ASN1OctetString iv;


	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RC2CBCParameter(BigInteger _rc2ParameterVersion, byte[] _iv) {
		setRc2ParameterVersion(_rc2ParameterVersion);
		setIv(_iv);
	}

	public RC2CBCParameter(ASN1Sequence _seq) {
		rc2ParameterVersion = (DERInteger)_seq.getObjectAt(0);
		iv                  = (ASN1OctetString)_seq.getObjectAt(1);
	}

	public RC2CBCParameter(RC2CBCParameter _orig) {
		rc2ParameterVersion = _orig.rc2ParameterVersion;
		iv                  = _orig.iv;
	}

    /**
     * return a RC2CBCParameter object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RC2CBCParameter getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a RC2CBCParameter object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RC2CBCParameter getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RC2CBCParameter) {
			return (RC2CBCParameter)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new RC2CBCParameter((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid RC2CBCParameter: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public BigInteger getRc2ParameterVersion() {
		return rc2ParameterVersion.getValue();
	}


	public byte[] getIv() {
		return iv.getOctets();
	}



	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(rc2ParameterVersion);
		_seq.addObject(iv);
		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setRc2ParameterVersion(BigInteger _rc2ParameterVersion) {
		rc2ParameterVersion = new DERInteger(_rc2ParameterVersion);
	}

	private void setIv(byte[] _iv) {
		iv = new BERConstructedOctetString(_iv);
	}


}
