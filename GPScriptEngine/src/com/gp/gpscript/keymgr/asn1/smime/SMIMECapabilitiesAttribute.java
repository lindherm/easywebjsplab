package com.gp.gpscript.keymgr.asn1.smime;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DEROctetString;
import com.gp.gpscript.keymgr.asn1.cms.Attribute;

/**
* RFC 2633
*
* SMIMECapabilities ::= SEQUENCE OF SMIMECapability
*
*/

public class SMIMECapabilitiesAttribute extends Attribute {

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

	public SMIMECapabilitiesAttribute() {
		super();
		attrType = SMIMEObjectIdentifiers.smimeCapabilities;
	}

	public SMIMECapabilitiesAttribute(ASN1Sequence _seq) {
		this();
		attrValues = (DERConstructedSet)_seq.getObjectAt(1);
	}

	public SMIMECapabilitiesAttribute(SMIMECapabilitiesAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}



	public static Attribute getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SMIMECapabilitiesAttribute) {
			return (SMIMECapabilitiesAttribute)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new SMIMECapabilitiesAttribute((ASN1Sequence)_obj);
		}

		if(_obj instanceof ASN1TaggedObject) {
			return getInstance(((ASN1TaggedObject)_obj).getObject());
		}

		throw new IllegalArgumentException("Invalid SMIMECapabilitiesAttribute: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public void addCapability(SMIMECapability _cap) {
		ASN1Sequence _caps;

		if(attrValues == null) {
			attrValues = new DERConstructedSet();
		}

		if(attrValues.getSize() == 0) {
			_caps = new BERConstructedSequence();
			attrValues.addObject(_caps);
		}
		else {
			_caps = (ASN1Sequence)attrValues.getObjectAt(0);
		}

		_caps.addObject(_cap);
	}

	public Vector getCapabilities() {
		Vector       _res  = new Vector();
		ASN1Sequence _caps = (ASN1Sequence)attrValues.getObjectAt(0);

		for(int i = 0; i < _caps.getSize(); i++) {
			_res.addElement(SMIMECapability.getInstance(_caps.getObjectAt(i)));
		}

		return _res;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


}
