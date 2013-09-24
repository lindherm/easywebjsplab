package com.gp.gpscript.keymgr.asn1;

import java.io.*;
import java.util.*;

public class BERConstructedSequence
    extends DERConstructedSequence
{
    /*
     */
    void encode(
        DEROutputStream out)
        throws IOException
    {
        if (out instanceof BEROutputStream)
        {
            out.write(SEQUENCE | CONSTRUCTED);
            out.write(0x80);

            Enumeration e = getObjects();
            while (e.hasMoreElements())
            {
                out.writeObject(e.nextElement());
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
