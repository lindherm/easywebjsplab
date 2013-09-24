package com.gp.gpscript.keymgr.asn1;

import java.io.*;

/**
 * DER TaggedObject - in ASN.1 nottation this is any object proceeded by
 * a [n] where n is some number - these are assume to follow the construction
 * rules (as with sequences).
 */
public class DERTaggedObject
    extends ASN1TaggedObject
{
    /**
     * This creates an empty tagged object of tagNo (ie. zero length).
     *
     * @param tagNo the tag number for this object.
     */
    public DERTaggedObject(
        int     tagNo)
    {
		super(tagNo);
    }

    /**
     * This creates an empty tagged object of tagNo (ie. zero length).
     *
     * @param explict whether we are explicitly tagged.
     * @param tagNo the tag number for this object.
     */
    public DERTaggedObject(
        boolean explicit,
        int     tagNo)
    {
		super(explicit, tagNo);
    }

    /**
     * @param tagNo the tag number for this object.
     * @param obj the tagged object.
     */
    public DERTaggedObject(
        int             tagNo,
        DEREncodable    obj)
    {
		super(tagNo, obj);
    }

    /**
     * @param explicit true if an explicitly tagged object.
     * @param tagNo the tag number for this object.
     * @param obj the tagged object.
     */
    public DERTaggedObject(
        boolean         explicit,
        int             tagNo,
        DEREncodable    obj)
    {
		super(explicit, tagNo, obj);
    }

    void encode(
        DEROutputStream  out)
        throws IOException
    {
        if (!empty)
        {
            ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
            DEROutputStream         dOut = new DEROutputStream(bOut);

            dOut.writeObject(obj);
            dOut.close();

            byte[]  bytes = bOut.toByteArray();

            if (explicit)
            {
                out.writeEncoded(CONSTRUCTED | TAGGED | tagNo, bytes);
            }
            else
            {
                //
                // need to mark constructed types...
                //
                if ((bytes[0] & CONSTRUCTED) != 0)
                {
                    bytes[0] = (byte)(CONSTRUCTED | TAGGED | tagNo);
                }
                else
                {
                    bytes[0] = (byte)(TAGGED | tagNo);
                }

                out.write(bytes);
            }
        }
        else
        {
            out.writeEncoded(CONSTRUCTED | TAGGED | tagNo, new byte[0]);
        }
    }
}
