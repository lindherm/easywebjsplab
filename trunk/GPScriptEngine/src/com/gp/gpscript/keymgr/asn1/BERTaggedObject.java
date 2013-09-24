package com.gp.gpscript.keymgr.asn1;

import java.io.*;
import java.util.*;

/**
 * BER TaggedObject - in ASN.1 nottation this is any object proceeded by
 * a [n] where n is some number - these are assume to follow the construction
 * rules (as with sequences).
 */
public class BERTaggedObject
    extends DERTaggedObject
{
    /**
     * This creates an empty tagged object of tagNo (ie. zero length).
     *
     * @param tagNo the tag number for this object.
     */
    public BERTaggedObject(
        int     tagNo)
    {
		super(tagNo);
    }

    /**
     * @param tagNo the tag number for this object.
     * @param obj the tagged object.
     */
    public BERTaggedObject(
        int             tagNo,
        DEREncodable    obj)
    {
		super(tagNo, obj);
    }

    /**
     * @param explicit true if an explicitly tagged object.
     * @param tagNo the tag number for this object.
     */
    public BERTaggedObject(
        boolean         explicit,
        int             tagNo)
    {
		super(explicit, tagNo);
    }

    /**
     * @param explicit true if an explicitly tagged object.
     * @param tagNo the tag number for this object.
     * @param obj the tagged object.
     */
    public BERTaggedObject(
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
		if (out instanceof BEROutputStream)
		{
            out.write(CONSTRUCTED | TAGGED | tagNo);
            out.write(0x80);

			if (!empty)
			{
                if (!explicit)
                {
                    if (obj instanceof BERConstructedOctetString)
                    {
                        Enumeration  e = ((BERConstructedOctetString)obj).getObjects();

                        while (e.hasMoreElements())
                        {
                            out.writeObject(e.nextElement());
                        }
                    }
                    else if (obj instanceof ASN1Sequence)
                    {
                        Enumeration  e = ((ASN1Sequence)obj).getObjects();

                        while (e.hasMoreElements())
                        {
                            out.writeObject(e.nextElement());
                        }
                    }
                    else if (obj instanceof ASN1Set)
                    {
                        Enumeration  e = ((ASN1Set)obj).getObjects();

                        while (e.hasMoreElements())
                        {
                            out.writeObject(e.nextElement());
                        }
                    }
                    else
                    {
                        throw new RuntimeException("not implemented: " + obj.getClass().getName());
                    }
                }
                else
                {
				    out.writeObject(obj);
                }
			}

            out.write(0x00);
            out.write(0x00);
		}
		else
		{
			super.encode(out);
		}
    }
}
