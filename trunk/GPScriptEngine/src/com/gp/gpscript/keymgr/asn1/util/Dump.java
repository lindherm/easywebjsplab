package com.gp.gpscript.keymgr.asn1.util;

import java.io.*;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.asn1.*;

public class Dump
{private static Logger log = Logger.getLogger(Dump.class);
    public static void main(
        String args[])
        throws Exception
    {
        FileInputStream fIn = new FileInputStream(args[0]);
        BERInputStream  bIn = new BERInputStream(fIn);
        Object          obj = null;

		try
		{
			while ((obj = bIn.readObject()) != null)
			{
				log.debug(ASN1Dump.dumpAsString(obj));
			}
		}
		catch (EOFException e)
		{
            e.printStackTrace();
//			 ignore
		}
    }
}
