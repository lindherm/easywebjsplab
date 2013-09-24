package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERGeneralizedTime;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERUTCTime;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * Time ::= CHOICE {
 * 	utcTime UTCTime,
 * 	generalTime GeneralizedTime
 * }
 * </pre>
 *
 *
 */

public class Time extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DERObject time;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public Time(DERUTCTime _time) {
		time = _time;
	}

	public Time(DERGeneralizedTime _time) {
		time = _time;
	}

	public Time(Time _orig) {
		time = _orig.time;
	}

    /**
     * return a Time object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static Time getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato.getObject());
	}

    /**
     * return a Time object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static Time getInstance(Object _obj) {
		if(_obj == null || _obj instanceof Time) {
			return (Time)_obj;
		}

		if(_obj instanceof DERUTCTime) {
			return new Time((DERUTCTime)_obj);
		}

		if(_obj instanceof DERGeneralizedTime) {
			return new Time((DERGeneralizedTime)_obj);
		}

		throw new IllegalArgumentException("Invalid Time: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DERObject getTime() {
		return time;
	}

	public DERObject getDERObject() {
		return time;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setTime(DERUTCTime _time) {
		time = _time;
	}

	private void setTime(DERGeneralizedTime _time) {
		time = _time;
	}

}
