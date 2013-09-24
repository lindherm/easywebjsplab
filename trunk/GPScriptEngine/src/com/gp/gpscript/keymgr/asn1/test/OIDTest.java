package com.gp.gpscript.keymgr.asn1.test;

import java.io.*;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.pkcs.*;
import com.gp.gpscript.keymgr.util.encoders.*;
import com.gp.gpscript.keymgr.util.test.*;


/**
 * X.690 test example
 */
public class OIDTest
	implements Test
{private static Logger log = Logger.getLogger(OIDTest.class);
	byte[]	req = Hex.decode("0603813403");

	public String getName()
	{
		return "OID";
	}

	public TestResult perform()
	{
		try
		{
			ByteArrayInputStream	bIn = new ByteArrayInputStream(req);
			DERInputStream			dIn = new DERInputStream(bIn);

			DERObjectIdentifier 	o = new DERObjectIdentifier("2.100.3");

			ByteArrayOutputStream	bOut = new ByteArrayOutputStream();
			DEROutputStream			dOut = new DEROutputStream(bOut);

			dOut.writeObject(o);

			byte[]					bytes = bOut.toByteArray();

			if (bytes.length != req.length)
			{
        		return new SimpleTestResult(false, getName() + ": failed length test");
			}

			for (int i = 0; i != req.length; i++)
			{
				if (bytes[i] != req[i])
				{
					return new SimpleTestResult(false, getName() + ": failed comparison test");
				}
			}
		}
		catch (Exception e)
		{
            e.printStackTrace();
        	return new SimpleTestResult(false, getName() + ": Exception - " + e.toString());
		}

        return new SimpleTestResult(true, getName() + ": Okay");
    }

    public static void main(
        String[]    args)
    {
        Test    test = new OIDTest();

        TestResult  result = test.perform();

        log.debug(result);
    }
}
