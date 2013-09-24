package com.gp.gpscript.keymgr.asn1;

import java.io.*;
import java.util.*;

public class BERConstructedOctetString
    extends DEROctetString
{
    /**
     * convert a vector of octet strings into a single byte string
     */
    static private byte[] toBytes(
        Vector  octs)
    {
        ByteArrayOutputStream   bOut = new ByteArrayOutputStream();

        for (int i = 0; i != octs.size(); i++)
        {
            DEROctetString  o = (DEROctetString)octs.elementAt(i);

            try
            {
                bOut.write(o.getOctets());
            }
            catch (IOException e)
            {
                throw new RuntimeException("exception converting octets " + e.toString());
            }
        }

        return bOut.toByteArray();
    }

    private Vector  octs;

    /**
     * @param string the octets making up the octet string.
     */
    public BERConstructedOctetString(
        byte[]  string)
    {
		super(string);
    }

    public BERConstructedOctetString(
        Vector  octs)
    {
		super(toBytes(octs));

        this.octs = octs;
    }

    public BERConstructedOctetString(
        DERObject  obj)
    {
		super(obj);
    }

    public BERConstructedOctetString(
        DEREncodable  obj)
    {
        super(obj.getDERObject());
    }

    public byte[] getOctets()
    {
        return string;
    }

    /**
     * return the DER octets that make up this string.
     */
    public Enumeration getObjects()
    {
        if (octs == null)
        {
            octs = generateOcts();
        }

        return octs.elements();
    }

    private Vector generateOcts()
    {
        int     start = 0;
        int     end = 0;
        Vector  vec = new Vector();

        while ((end + 1) < string.length)
        {
            if (string[end] == 0 && string[end + 1] == 0)
            {
                byte[]  nStr = new byte[end - start + 1];

                for (int i = 0; i != nStr.length; i++)
                {
                    nStr[i] = string[start + i];
                }

                vec.addElement(new DEROctetString(nStr));
                start = end + 1;
            }
            end++;
        }

        byte[]  nStr = new byte[string.length - start];
        for (int i = 0; i != nStr.length; i++)
        {
            nStr[i] = string[start + i];
        }

        vec.addElement(new DEROctetString(nStr));

        return vec;
    }

    public void encode(
        DEROutputStream out)
        throws IOException
    {
		if (out instanceof BEROutputStream)
		{
            out.write(CONSTRUCTED | OCTET_STRING);

            out.write(0x80);

            if (octs == null)
            {
                octs = generateOcts();
            }

            for (int i = 0; i != octs.size(); i++)
            {
                out.writeObject(octs.elementAt(i));
            }

            out.write(0x00);
            out.write(0x00);
		}
		else
		{
			super.encode(out);
		}
    }

    public boolean equals(
        Object  o)
    {
        if ((o == null) || !(o instanceof BERConstructedOctetString))
        {
            return false;
        }

		if(!super.equals(o))
		{
			return false;
		}

		BERConstructedOctetString other = (BERConstructedOctetString)o;

		if(octs == null)
		{
			if(other.octs != null)
			{
				return false;
			}
		}
		else
		{
			if(!(octs.equals(other.octs)))
			{
				return false;
			}
		}

		return true;
    }

}
