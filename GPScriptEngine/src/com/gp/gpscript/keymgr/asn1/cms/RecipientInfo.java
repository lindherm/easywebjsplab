package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.ASN1Sequence;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.BERTaggedObject;
import com.gp.gpscript.keymgr.asn1.DEREncodable;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientInfo ::= CHOICE {
 * 	ktri KeyTransRecipientInfo,
 * 	kari [1] KeyAgreeRecipientInfo,
 * 	kekri [2] KEKRecipientInfo
 * }
 * </pre>
 *
 *
 */

public class RecipientInfo extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private DEREncodable info;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientInfo(KeyTransRecipientInfo _info) {
		setInfo(_info);
	}

	public RecipientInfo(KeyAgreeRecipientInfo _info) {
		setInfo(_info);
	}

	public RecipientInfo(KEKRecipientInfo _info) {
		setInfo(_info);
	}

	public RecipientInfo(DERObject  _info) {

		if(_info instanceof ASN1TaggedObject) {
			ASN1TaggedObject _dto = (ASN1TaggedObject)_info;
			switch(_dto.getTagNo()) {
				case 1 :
					info = KeyAgreeRecipientInfo.getInstance(_dto, false);
					break;
				case 2 :
					info = KEKRecipientInfo.getInstance(_dto, false);
					break;
				default:
					throw new IllegalArgumentException("Invalid RecipientInfo");
			}
		}
		else {
			info = KeyTransRecipientInfo.getInstance(_info);
		}
	}

	public RecipientInfo(RecipientInfo _orig) {
		info = _orig.info;
	}

    /**
     * return a RecipientInfo object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientInfo getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(_ato);
	}

    /**
     * return a RecipientInfo object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientInfo getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientInfo) {
			return (RecipientInfo)_obj;
		}

		if(_obj instanceof KeyTransRecipientInfo) {
			return new RecipientInfo((KeyTransRecipientInfo)_obj);
		}

		if(_obj instanceof KeyAgreeRecipientInfo) {
			return new RecipientInfo((KeyAgreeRecipientInfo)_obj);
		}

		if(_obj instanceof KEKRecipientInfo) {
			return new RecipientInfo((KEKRecipientInfo)_obj);
		}

		if(_obj instanceof DERObject) {
			return new RecipientInfo((DERObject)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientInfo: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public DEREncodable getInfo() {
		return info;
	}

	public DERObject getDERObject() {
		if(info instanceof KeyAgreeRecipientInfo) {
			return new BERTaggedObject(false, 1, info.getDERObject());
		}
		else if(info instanceof KEKRecipientInfo) {
			return new BERTaggedObject(false, 2, info.getDERObject());
		}
		else {
			return info.getDERObject();
		}
	}

	public CMSVersion getVersion() {
		if(info instanceof KeyAgreeRecipientInfo) {
			return ((KeyAgreeRecipientInfo)info).getVersion();
		}
		else if(info instanceof KEKRecipientInfo) {
			return ((KEKRecipientInfo)info).getVersion();
		}
		else {
			return ((KeyTransRecipientInfo)info).getVersion();
		}
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setInfo(KeyTransRecipientInfo _info) {
		info = _info;
	}

	private void setInfo(KeyAgreeRecipientInfo _info) {
		info = _info;
	}

	private void setInfo(KEKRecipientInfo _info) {
		info = _info;
	}


}
