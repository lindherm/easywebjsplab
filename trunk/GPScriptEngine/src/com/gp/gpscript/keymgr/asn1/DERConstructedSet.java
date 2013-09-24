package com.gp.gpscript.keymgr.asn1;

import java.io.*;
import java.util.*;

public class DERConstructedSet
    extends ASN1Set
{
    /*
     * A note on the implementation:
     * <p>
     * As DER requires the constructed, definite-length model to
     * be used for structured types, this varies slightly from the
     * ASN.1 descriptions given. Rather than just outputing SET,
     * we also have to specify CONSTRUCTED, and the objects length.
     */
    void encode(
        DEROutputStream out)
        throws IOException
    {
        ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
        DEROutputStream         dOut = new DEROutputStream(bOut);
        Enumeration             e = this.getObjects();

        while (e.hasMoreElements())
        {
            Object    obj = e.nextElement();

            dOut.writeObject(obj);
        }

        dOut.close();

        byte[]  bytes = bOut.toByteArray();

        out.writeEncoded(SET | CONSTRUCTED, bytes);
    }

    public int hashCode()
    {
        Enumeration             e = this.getObjects();
        int                     hashCode = 0;

        while (e.hasMoreElements())
        {
            hashCode ^= e.nextElement().hashCode();
        }

        return hashCode;
    }

    public boolean equals(
        Object  o)
    {
        if (o == null || !(o instanceof DERConstructedSet))
        {
            return false;
        }

        DERConstructedSet   other = (DERConstructedSet)o;

        if (this.getSize() != other.getSize())
        {
            return false;
        }

        Enumeration s1 = this.getObjects();
        Enumeration s2 = other.getObjects();

        while (s1.hasMoreElements())
        {
            if (!s1.nextElement().equals(s2.nextElement()))
            {
                return false;
            }
        }

        return true;
    }
}
