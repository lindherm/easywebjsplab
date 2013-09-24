package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1OctetString;
import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedOctetString;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERGeneralizedTime;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * KEKIdentifier ::= SEQUENCE {
 * 	keyIdentifier OCTET STRING,
 * 	date GeneralizedTime OPTIONAL,
 * 	other OtherKeyAttribute OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class KEKIdentifier extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1OctetString     keyIdentifier;
	private DERGeneralizedTime date;
	private OtherKeyAttribute  other;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public KEKIdentifier(byte[] _keyIdentifier, String _date, OtherKeyAttribute _other) {
		setKeyIdentifier(_keyIdentifier);
		setDate(_date);
		setOther(_other);
	}

	public KEKIdentifier(ASN1Sequence _seq){

		keyIdentifier = (ASN1OctetString)_seq.getObjectAt(0);

		switch(_seq.getSize()) {
			case 1 :
				break;
			case 2 :
				try {
					date = (DERGeneralizedTime)_seq.getObjectAt(1);
				}
				catch(Exception ex) {
                    ex.printStackTrace();
					other = OtherKeyAttribute.getInstance(_seq.getObjectAt(2));
				}
				break;
			case 3 :
				date  = (DERGeneralizedTime)_seq.getObjectAt(1);
				other = OtherKeyAttribute.getInstance(_seq.getObjectAt(2));
				break;
			default:
				throw new IllegalArgumentException("Invalid KEKIdentifier");
		}
	}

	public KEKIdentifier(KEKIdentifier _orig) {
		keyIdentifier = _orig.keyIdentifier;
		date          = _orig.date;
		other         = _orig.other;
	}

    /**
     * return a KEKIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static KEKIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a KEKIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static KEKIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof KEKIdentifier) {
			return (KEKIdentifier)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new KEKIdentifier((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid KEKIdentifier: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public byte[] getKeyIdentifier() {
		return keyIdentifier.getOctets();
	}

	public String getDate() {
		return date.getTime();
	}

	public OtherKeyAttribute getOther() {
		return other;
	}

	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(keyIdentifier);

		if(date != null) {
			_seq.addObject(date);
		}

		if(other != null) {
			_seq.addObject(other);
		}

		return _seq;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */

	private void setKeyIdentifier(byte[] _keyIdentifier) {
		keyIdentifier = new BERConstructedOctetString(_keyIdentifier);
	}

	private void setDate(String _date) {
		if(_date == null) {
			date = null;
		}
		else {
			date = new DERGeneralizedTime(_date);
		}
	}

	private void setOther(OtherKeyAttribute _other) {
		other = _other;
	}


}
