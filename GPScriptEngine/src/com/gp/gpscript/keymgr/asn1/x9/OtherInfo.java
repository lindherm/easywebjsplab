package com.gp.gpscript.keymgr.asn1.x9;

import java.io.*;
import java.util.Enumeration;
import java.math.BigInteger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.pkcs.PKCSObjectIdentifiers;

/**
 * ANS.1 def for Diffie-Hellman key exchange OtherInfo structure. See
 * RFC 2631, or X9.42, for further details.
 */
public class OtherInfo
    implements DEREncodable
{
    private KeySpecificInfo     keyInfo;
    private DEROctetString      partyAInfo;
    private DEROctetString      suppPubInfo;

    public OtherInfo(
        KeySpecificInfo     keyInfo,
        DEROctetString      partyAInfo,
        DEROctetString      suppPubInfo)
    {
        this.keyInfo = keyInfo;
        this.partyAInfo = partyAInfo;
        this.suppPubInfo = suppPubInfo;
    }

    public OtherInfo(
        DERConstructedSequence  seq)
    {
        Enumeration e = seq.getObjects();

        keyInfo = new KeySpecificInfo((DERConstructedSequence)e.nextElement());

        while (e.hasMoreElements())
        {
            DERTaggedObject o = (DERTaggedObject)e.nextElement();

            if (o.getTagNo() == 0)
            {
                partyAInfo = (DEROctetString)o.getObject();
            }
            else if (o.getTagNo() == 2)
            {
                suppPubInfo = (DEROctetString)o.getObject();
            }
        }
    }

    public KeySpecificInfo getKeyInfo()
    {
        return keyInfo;
    }

    public DEROctetString getPartyAInfo()
    {
        return partyAInfo;
    }

    public DEROctetString getSuppPubInfo()
    {
        return suppPubInfo;
    }

    /**
     * <pre>
     *  OtherInfo ::= SEQUENCE {
     *      keyInfo KeySpecificInfo,
     *      partyAInfo [0] OCTET STRING OPTIONAL,
     *      suppPubInfo [2] OCTET STRING
     *  }
     * </pre>
     */
    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(keyInfo);

        if (partyAInfo != null)
        {
            seq.addObject(new DERTaggedObject(0, partyAInfo));
        }

        seq.addObject(new DERTaggedObject(2, suppPubInfo));

        return seq;
    }
}
