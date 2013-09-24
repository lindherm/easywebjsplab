package com.gp.gpscript.keymgr.asn1.cms;

import java.util.Vector;

import com.gp.gpscript.keymgr.asn1.ASN1Set;
import com.gp.gpscript.keymgr.asn1.ASN1TaggedObject;
import com.gp.gpscript.keymgr.asn1.DERConstructedSet;
import com.gp.gpscript.keymgr.asn1.DERObject;

/**
 * <br>
 * <pre>
 * RFC 2630
 *
 * RecipientInfos ::= SET OF RecipientInfo
 * </pre>
 *
 *
 */

public class RecipientInfos extends CMSObject {

	/*
	 *
	 *  VARIABLES
	 *
	 */

	private ASN1Set infos;

	/*
	 *
	 *  CONSTRUCTORS
	 *
	 */

	public RecipientInfos(Vector _infos) {

		setInfos(_infos);
	}

	public RecipientInfos(ASN1Set _infos) {
		infos = _infos;
	}

	public RecipientInfos(RecipientInfos _orig) {
		infos = _orig.infos;
	}

    /**
     * return a RecipientInfos object from a tagged object.
     *
     * @param _ato the tagged object holding the object we want.
     * @param _explicit true if the object is meant to be explicitly
     *              tagged false otherwise.
     * @exception IllegalArgumentException if the object held by the
     *          tagged object cannot be converted.
     */
	public static RecipientInfos getInstance(ASN1TaggedObject _ato, boolean _explicit) {
		return getInstance(ASN1Set.getInstance(_ato, _explicit));
	}

    /**
     * return a RecipientInfos object from the given object.
     *
     * @param _obj the object we want converted.
     * @exception IllegalArgumentException if the object cannot be converted.
     */
	public static RecipientInfos getInstance(Object _obj) {
		if(_obj == null || _obj instanceof RecipientInfos) {
			return (RecipientInfos)_obj;
		}

		if(_obj instanceof ASN1Set) {
			return new RecipientInfos((ASN1Set)_obj);
		}

		throw new IllegalArgumentException("Invalid RecipientInfos: " + _obj.getClass().getName());
	}

	/*
	 *
	 *  BUSINESS METHODS
	 *
	 */

	public Vector getInfos() {

		int    _len = infos.getSize();
		Vector _res = new Vector();
		for(int i = 0; i < _len; i++) {
			_res.addElement(RecipientInfo.getInstance(infos.getObjectAt(i)));
		}
		return _res;
	}

	public DERObject getDERObject() {
		return infos;
	}

	/*
	 *
	 *  INTERNAL METHODS
	 *
	 */


	private void setInfos(Vector _infos) {

		int _len   = _infos.size();
		infos = new DERConstructedSet();
		for(int i = 0; i < _len; i++) {
			infos.addObject(RecipientInfo.getInstance(_infos.elementAt(i)));
		}
	}


}
