package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERConstructedSequence;
import com.gp.gpscript.keymgr.asn1.DERGeneralizedTime;
import com.gp.gpscript.keymgr.asn1.DERObject;
import com.gp.gpscript.keymgr.asn1.DERUTCTime;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientKeyIdentifier ::= SEQUENCE {
 * 	subjectKeyIdentifier SubjectKeyIdentifier,
 * 	date GeneralizedTime OPTIONAL,
 * 	other OtherKeyAttribute OPTIONAL
 * }
 * </pre>
 *
 *
 */

public class RecipientKeyIdentifier extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private SubjectKeyIdentifier subjectKeyIdentifier;
	private DERGeneralizedTime   date;
	private OtherKeyAttribute    other;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientKeyIdentifier(SubjectKeyIdentifier _subjectKeyIdentifier,
	                              String               _date,
	                              OtherKeyAttribute    _other) {
		setSubjectKeyIdentifier(_subjectKeyIdentifier);
		setDate(_date);
		setOtherKeyAttribute(_other);
	}

	public RecipientKeyIdentifier(ASN1Sequence _seq) {

		subjectKeyIdentifier = SubjectKeyIdentifier.getInstance(_seq.getObjectAt(0));

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

	public RecipientKeyIdentifier(RecipientKeyIdentifier _orig) {
		subjectKeyIdentifier = _orig.subjectKeyIdentifier;
		date                 = _orig.date;
		other                = _orig.other;
	}

    /**
     * return a RecipientKeyIdentifier object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientKeyIdentifier getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Sequence.getInstance(_ato, _explicit));
	}

    /**
     * return a RecipientKeyIdentifier object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientKeyIdentifier getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientKeyIdentifier) {
			return (RecipientKeyIdentifier)_obj;
		}

		if(_obj instanceof ASN1Sequence) {
			return new RecipientKeyIdentifier((ASN1Sequence)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientKeyIdentifier: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public SubjectKeyIdentifier getSubjectKeyIdentifier() {
		return subjectKeyIdentifier;
	}

	public String getDate() {
		return date.getTime();
	}

	private void setDate(String _date) {
		date = new DERGeneralizedTime(_date);
	}


	public OtherKeyAttribute getOtherKeyAttribute() {
		return other;
	}


	public DERObject getDERObject() {
		BERConstructedSequence _seq = new BERConstructedSequence();
		_seq.addObject(subjectKeyIdentifier);

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


	private void setSubjectKeyIdentifier(SubjectKeyIdentifier _subjectKeyIdentifier) {
		subjectKeyIdentifier = _subjectKeyIdentifier;
	}


	private void setOtherKeyAttribute(OtherKeyAttribute _other) {
		other = _other;
	}


}
