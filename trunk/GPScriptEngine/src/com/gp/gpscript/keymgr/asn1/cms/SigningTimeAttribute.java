package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Calendar;
import java.util.Date;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERGeneralizedTime;
import com.gp.gpscript.keymgr.asn1.DERUTCTime;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * SigningTimeAttribute  ::= Time
 * </pre>
 *
 *
 */

public class SigningTimeAttribute extends Attribute {

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

	protected SigningTimeAttribute() {
		super();
		attrType = CMSObjectIdentifiers.id_signingTime;
	}

	public SigningTimeAttribute(ASN1Sequence _seq) {
		this();
		attrValues = (DERConstructedSet)_seq.getObjectAt(1);
	}

	public SigningTimeAttribute(Attribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public SigningTimeAttribute(Date _date) {

		this();
		Calendar _cal = Calendar.getInstance();
		_cal.setTime(_date);
		int _year = _cal.get(Calendar.YEAR);
		if(_year < 1950 || _year > 2059) {
			setTime(Time.getInstance(new DERGeneralizedTime(_date)));
		}
		else {
			setTime(Time.getInstance(new DERUTCTime(_date)));
		}
	}

	public SigningTimeAttribute(SigningTimeAttribute _orig) {
		this();
		attrValues = _orig.attrValues;
	}

	public SigningTimeAttribute(Time _time) {
		this();
		setTime(_time);
	}

    /**
     * return a SigningTimeAttribute object from a tagged object.
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
     * return a SigningTimeAttribute object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Attribute getInstance(Object _obj) {
		if(_obj == null || _obj instanceof SigningTimeAttribute) {
			return (SigningTimeAttribute)_obj;
		}

		if(_obj instanceof Attribute) {
			return new SigningTimeAttribute((Attribute)_obj);
		}

		if(_obj instanceof ASN1Sequence) {
			return new SigningTimeAttribute((ASN1Sequence)_obj);
		}

		return new SigningTimeAttribute(Time.getInstance(_obj));
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Time getTime() {

		return Time.getInstance(attrValues.getObjectAt(0));
	}


	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setTime(Time _time) {
		attrValues = new DERConstructedSet();
		attrValues.addObject(_time);
	}


}
