package com.gp.gpscript.keymgr.asn1.x509;

import com.gp.gpscript.keymgr.asn1.DERObjectIdentifier;

public interface X509ObjectIdentifiers
{
    //
    // base id
    //
    static final String                 id                      = "2.5.4";

    static final DERObjectIdentifier    commonName              = new DERObjectIdentifier(id + ".3");
    static final DERObjectIdentifier    countryName             = new DERObjectIdentifier(id + ".6");
    static final DERObjectIdentifier    localityName            = new DERObjectIdentifier(id + ".7");
    static final DERObjectIdentifier    stateOrProvinceName     = new DERObjectIdentifier(id + ".8");
    static final DERObjectIdentifier    organization            = new DERObjectIdentifier(id + ".10");
    static final DERObjectIdentifier    organizationalUnitName  = new DERObjectIdentifier(id + ".11");

    // id-SHA1 OBJECT IDENTIFIER ::=
    //   {iso(1) identified-organization(3) oiw(14) secsig(3) algorithms(2) 26 }    //
    static final DERObjectIdentifier    id_SHA1                 = new DERObjectIdentifier("1.3.14.3.2.26");

    //
    // ripemd160 OBJECT IDENTIFIER ::=
    //      {iso(1) identified-organization(3) TeleTrust(36) algorithm(3) hashAlgorithm(2) RIPEMD-160(1)}
    //
    static final DERObjectIdentifier    ripemd160               = new DERObjectIdentifier("1.3.36.3.2.1");

    //
    // ripemd160WithRSAEncryption OBJECT IDENTIFIER ::=
    //      {iso(1) identified-organization(3) TeleTrust(36) algorithm(3) signatureAlgorithm(3) rsaSignature(1) rsaSignatureWithripemd160(2) }
    //
    static final DERObjectIdentifier    ripemd160WithRSAEncryption = new DERObjectIdentifier("1.3.36.3.3.1.2");


	static final DERObjectIdentifier	id_ea_rsa = new DERObjectIdentifier("2.5.8.1.1");
}

