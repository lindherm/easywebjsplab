package com.gp.gpscript.keymgr.asn1.smime;

import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERInteger;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;
import com.gp.gpscript.keymgr.asn1.cms.CBCParameter;
import com.gp.gpscript.keymgr.asn1.cms.IV;
import com.gp.gpscript.keymgr.asn1.cms.RC2CBCParameter;

/**
*
* RFC 2633
*
* SMIMECapability ::= SEQUENCE {
* 	capabilityID OBJECT IDENTIFIER,
* 	parameters ANY DEFINED BY capabilityID OPTIONAL
* }
*/
public class SMIMECapability implements DEREncodable {

	/*
	 *
	 *  STATIC
	 *
	 */


	public static SMIMECapability RC2_CBC_40_MS() {
		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new DERInteger(40));
	}

	public static SMIMECapability RC2_CBC_64_MS() {
		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new DERInteger(64));
	}

	public static SMIMECapability RC2_CBC_128_MS() {
		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new DERInteger(128));
	}

	public static SMIMECapability RC2_CBC_40(byte[] _iv) {
		if(_iv == null) {
			return RC2_CBC_40_MS();
		}

		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new RC2CBCParameter(new BigInteger("160"), _iv));
	}

	public static SMIMECapability RC2_CBC_64(byte[] _iv) {
		if(_iv == null) {
			return RC2_CBC_64_MS();
		}

		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new RC2CBCParameter(new BigInteger("120"), _iv));
	}

	public static SMIMECapability RC2_CBC_128(byte[] _iv) {
		if(_iv == null) {
			return RC2_CBC_128_MS();
		}

		return new SMIMECapability(SMIMEObjectIdentifiers.rC2_CBC, new RC2CBCParameter(new BigInteger("58"), _iv));
	}

	public static SMIMECapability DES_EDE_CBC_MS() {
		return new SMIMECapability(SMIMEObjectIdentifiers.dES_EDE3_CBC, null);
	}

	public static SMIMECapability DES_EDE_CBC(byte[] _iv) {
		if(_iv == null) {
			return DES_EDE_CBC_MS();
		}

		return new SMIMECapability(SMIMEObjectIdentifiers.dES_EDE3_CBC, new CBCParameter(new IV(_iv)));
	}

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERObjectIdentifier capabilityID;
	private DEREncodable        parameters;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public SMIMECapability(ASN1Sequence _seq) {
		capabilityID = (DERObjectIdentifier)_seq.getObjectAt(0);
		if(_seq.getSize() > 1) {
			parameters = (DERObject)_seq.getObjectAt(1);
		}
	}

	public SMIMECapability(SMIMECapability _orig) {
		capabilityID = _orig.capabilityID;
		parameters   = _orig.parameters;
	}

	public SMIMECapability(DERObjectIdentifier _capabilityID, DEREncodable _parameters) {
		capabilityID = _capabilityID;
		parameters   = _parameters;
	}

	public static SMIMECapability getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato.getObject());
	}

	public static SMIMECapability getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SMIMECapability) {
			return (SMIMECapability)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new SMIMECapability((ASN1Sequence)_obj);
		}

		if(_obj instanceof ASN1TaggedObject) {
			return getInstance(((ASN1TaggedObject)_obj).getObject());
		}

		throw new IllegalArgumentException("Invalid SMIMECapability");
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DERObject getDERObject() {
		DERConstructedSequence _seq = new DERConstructedSequence();
		_seq.addObject(capabilityID);

		if(parameters != null) {
			_seq.addObject(parameters);
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

}
