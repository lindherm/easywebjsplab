package com.gp.gpscript.keymgr.asn1.cms;

import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

/**
 * <br>
 * <pre>
 * RFC 2630
 * </pre>
 *
 *
 */

public interface CMSObjectIdentifiers {

	/**
	* -- Content Type Object Identifiers
	*
	*/


	/**
	* id-ct-contentInfo OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs-9(9) smime(16)
	* 	  ct(1) 6 }
	*/
	public static final DERObjectIdentifier id_ct_contentInfo = new DERObjectIdentifier("1.2.840.113549.1.9.16.1.6");


	/**
	* id-data OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs7(7) 1 }
	*/
	public static final DERObjectIdentifier id_data = new DERObjectIdentifier("1.2.840.113549.1.7.1");


	/**
	* id-signedData OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs7(7) 2 }
	*/
	public static final DERObjectIdentifier id_signedData = new DERObjectIdentifier("1.2.840.113549.1.7.2");


	/**
	* id-envelopedData OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs7(7) 3 }
	*/
	public static final DERObjectIdentifier id_envelopedData = new DERObjectIdentifier("1.2.840.113549.1.7.3");


	/**
	* id-digestedData OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs7(7) 5 }
	*/
	public static final DERObjectIdentifier id_digestedData = new DERObjectIdentifier("1.2.840.113549.1.7.5");


	/**
	* id-encryptedData OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs7(7) 6 }
	*/
	public static final DERObjectIdentifier id_encryptedData = new DERObjectIdentifier("1.2.840.113549.1.7.6");


	/**
	* id-ct-authData OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs-9(9) smime(16)
	* 	  ct(1) 2 }
	*/
	public static final DERObjectIdentifier id_ct_authData = new DERObjectIdentifier("1.2.840.113549.1.9.16.1.2");


	/**
	* -- Attribute Object Identifiers
	*/


	/**
	* id-contentType OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs9(9) 3 }
	*/
	public static final DERObjectIdentifier id_contentType = new DERObjectIdentifier("1.2.840.113549.1.9.3");


	/**
	* id-messageDigest OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs9(9) 4 }
	*/
	public static final DERObjectIdentifier id_messageDigest = new DERObjectIdentifier("1.2.840.113549.1.9.4");


	/**
	* id-signingTime OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs9(9) 5 }
	*/
	public static final DERObjectIdentifier id_signingTime = new DERObjectIdentifier("1.2.840.113549.1.9.5");


	/**
	* id-countersignature OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs9(9) 6 }
	*/
	public static final DERObjectIdentifier id_countersignature = new DERObjectIdentifier("1.2.840.113549.1.9.6");







	/**
	* -- Algorithm Object Identifiers
	*
	*/

	/**
	* sha-1 OBJECT IDENTIFIER ::= { iso(1) identified-organization(3)
	* 	  oiw(14) secsig(3) algorithm(2) 26 }
	*/
	public static final DERObjectIdentifier sha_1 = new DERObjectIdentifier("1.3.14.3.2.26");


	/**
	* md5 OBJECT IDENTIFIER ::= { iso(1) member-body(2) us(840)
	* 	  rsadsi(113549) digestAlgorithm(2) 5 }
	*/
	public static final DERObjectIdentifier md5 = new DERObjectIdentifier("1.2.840.113549.2.5");


	/**
	* id-dsa-with-sha1 OBJECT IDENTIFIER ::=  { iso(1) member-body(2)
	* 	  us(840) x9-57 (10040) x9cm(4) 3 }
	*/
	public static final DERObjectIdentifier id_dsa_with_sha1 = new DERObjectIdentifier("1.2.840.10040.4.3");


	/**
	* rsaEncryption OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs-1(1) 1 }
	*/
	public static final DERObjectIdentifier rsaEncryption = new DERObjectIdentifier("1.2.840.113549.1.1.1");


	/**
	* dh-public-number OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) ansi-x942(10046) number-type(2) 1 }
	*/
	public static final DERObjectIdentifier dh_public_number = new DERObjectIdentifier("1.2.840.10046.2.1");


	/**
	* id-alg-ESDH OBJECT IDENTIFIER ::= { iso(1) member-body(2) us(840)
	* 	  rsadsi(113549) pkcs(1) pkcs-9(9) smime(16) alg(3) 5 }
	*/
	public static final DERObjectIdentifier id_alg_ESDH = new DERObjectIdentifier("1.2.840.113549.1.9.16.3.5");


	/**
	* id-alg-CMS3DESwrap OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs-9(9) smime(16) alg(3) 6 }
	*/
	public static final DERObjectIdentifier id_alg_CMS3DESwrap = new DERObjectIdentifier("1.2.840.113549.1.9.16.3.6");


	/**
	* id-alg-CMSRC2wrap OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) pkcs(1) pkcs-9(9) smime(16) alg(3) 7 }
	*/
	public static final DERObjectIdentifier id_alg_CMSRC2wrap = new DERObjectIdentifier("1.2.840.113549.1.9.16.7");


	/**
	* des-ede3-cbc OBJECT IDENTIFIER ::= { iso(1) member-body(2)
	* 	  us(840) rsadsi(113549) encryptionAlgorithm(3) 7 }
	*/
	public static final DERObjectIdentifier des_ede3_cbc = new DERObjectIdentifier("1.2.840.113549.3.7");


	/**
	* rc2-cbc OBJECT IDENTIFIER ::= { iso(1) member-body(2) us(840)
	* 	  rsadsi(113549) encryptionAlgorithm(3) 2 }
	*
	*/
	public static final DERObjectIdentifier rc2_cbc = new DERObjectIdentifier("1.2.840.113549.3.2");


	/**
	* hMAC-SHA1 OBJECT IDENTIFIER ::= { iso(1) identified-organization(3)
	* 	  dod(6) internet(1) security(5) mechanisms(5) 8 1 2 }
	*/
	public static final DERObjectIdentifier hMAC_SHA1 = new DERObjectIdentifier("1.3.6.1.5.5.8.1.2");

}
