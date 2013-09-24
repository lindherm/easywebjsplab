package com.gp.gpscript.keymgr.asn1.smime;

import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

/*
 * RFC 2630
 *
 */
public interface SMIMEObjectIdentifiers {


	/*
	 * id-aa OBJECT IDENTIFIER ::= {iso(1) member-body(2) usa(840)
	 * rsadsi(113549) pkcs(1) pkcs-9(9) smime(16) attributes(2)}
	 */
	public static DERObjectIdentifier id_aa = new DERObjectIdentifier("1.2.840.113549.1.9.16.2");

	/*
	 * smimeCapabilities OBJECT IDENTIFIER ::=
	 * {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-9(9) 15}
	 */
	public static DERObjectIdentifier smimeCapabilities = new DERObjectIdentifier("1.2.840.113549.1.9.15");

	/*
	 * id-aa-encrypKeyPref OBJECT IDENTIFIER ::= {id-aa 11}
	 *
	 */
	public static DERObjectIdentifier id_aa_encrypKeyPref = new DERObjectIdentifier("1.2.840.113549.1.9.16.2.11");

	/*
	 * dES-EDE3-CBC OBJECT IDENTIFIER ::=
	 * {iso(1) member-body(2) us(840) rsadsi(113549) encryptionAlgorithm(3) 7}
	 */
	public static DERObjectIdentifier dES_EDE3_CBC = new DERObjectIdentifier("1.2.840.113549.3.7");

	/*
	 * rC2-CBC OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) encryptionAlgorithm(3) 2}
	 */
	public static DERObjectIdentifier rC2_CBC = new DERObjectIdentifier("1.2.840.113549.3.2");













	/*
	 * THE FOLLOWING ARE COMMENTED OUT IN RFC 2633
	 */






	/*
	 * md5 OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) digestAlgorithm(2) 5}
	 */
	public static DERObjectIdentifier md5 = new DERObjectIdentifier("1.2.840.113549.2.5");

	/*
	 * sha-1 OBJECT IDENTIFIER ::=
	 *    {iso(1) identified-organization(3) oiw(14) secsig(3) algorithm(2) 26}
	 */
	public static DERObjectIdentifier sha_1 = new DERObjectIdentifier("1.3.14.3.2.26");

	/*
	 * rsaEncryption OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-1(1) 1}
	 */
	public static DERObjectIdentifier rsaEncryption = new DERObjectIdentifier("1.2.840.113549.1.1.1");

	/*
	 * rsa OBJECT IDENTIFIER ::=
	 *    {joint-iso-ccitt(2) ds(5) algorithm(8) encryptionAlgorithm(1) 1}
	 */
	public static DERObjectIdentifier rsa = new DERObjectIdentifier("2.5.8.1.1");

	/*
	 * id-dsa OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) x9-57(10040) x9cm(4) 1 }
	 */
	public static DERObjectIdentifier id_dsa = new DERObjectIdentifier("1.2.840.10040.4.1");

	/*
	 * md2WithRSAEncryption OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-1(1) 2}
	 */
	public static DERObjectIdentifier md2WithRSAEncryption = new DERObjectIdentifier("1.2.840.113549.1.1.2");

	/*
	 * md5WithRSAEncryption OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-1(1) 4}
	 */
	public static DERObjectIdentifier md5WithRSAEncryption = new DERObjectIdentifier("1.2.840.113549.1.1.4");

	/*
	 * sha-1WithRSAEncryption OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-1(1) 5}
	 */
	public static DERObjectIdentifier sha_1WithRSAEncryption = new DERObjectIdentifier("1.2.840.113549.1.1.5");

	/*
	 * id-dsa-with-sha1 OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) x9-57(10040) x9cm(4) 3}
	 */
	public static DERObjectIdentifier id_dsa_with_sha1 = new DERObjectIdentifier("1.2.840.10040.4.3");

	/*
	 * signingTime OBJECT IDENTIFIER ::=
	 *    {iso(1) member-body(2) us(840) rsadsi(113549) pkcs(1) pkcs-9(9) 5}
	 */
	public static DERObjectIdentifier signingTime = new DERObjectIdentifier("1.2.840.113549.1.9.5");
}