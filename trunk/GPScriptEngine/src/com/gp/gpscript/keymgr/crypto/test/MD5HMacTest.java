
package com.gp.gpscript.keymgr.crypto.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.digests.*;
import com.gp.gpscript.keymgr.crypto.macs.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;


/**
 * MD5 HMac Test, test vectors from RFC 2202
 */
public class MD5HMacTest
    implements Test
{private static Logger log = Logger.getLogger(MD5HMacTest.class);
	final static String[] keys = {
		"0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b",
		"4a656665",
		"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		"0102030405060708090a0b0c0d0e0f10111213141516171819",
		"0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c",
		"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
		"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
	};

	final static String[] digests = {
		"9294727a3638bb1c13f48ef8158bfc9d",
		"750c783e6ab0b503eaa86e310a5db738",
		"56be34521d144c88dbb8c733f0e8b3f6",
		"697eaf0aca3a3aea3a75164746ffaa79",
		"56461ef2342edc00f9bab995690efd4c",
		"6b1ab7fe4bd7bf8f0b62e6ce61b9d0cd",
		"6f630fad67cda0ee1fb1f562db3aa53e"
	};

	final static String[] messages = {
		"Hi There",
		"what do ya want for nothing?",
		"0xdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
		"0xcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcdcd",
		"Test With Truncation",
		"Test Using Larger Than Block-Size Key - Hash Key First",
		"Test Using Larger Than Block-Size Key and Larger Than One Block-Size Data"
	};

	public static boolean arraysEqual(byte[] a, byte[] b)
	{
		if (a == b) return true;
		if (a.length != b.length) return false;

		for (int i = 0; i < a.length; i++)
		{
			if (a[i] != b[i]) return false;
		}

		return true;
	}

    public String getName()
    {
        return "MD5HMac";
    }

    public TestResult perform()
    {
		HMac hmac = new HMac(new MD5Digest());
		byte[] resBuf = new byte[hmac.getMacSize()];

		for (int i = 0; i < messages.length; i++)
		{
			byte[] m = messages[i].getBytes();
			if (messages[i].startsWith("0x"))
			{
				m = Hex.decode(messages[i].substring(2));
			}
			hmac.init(new KeyParameter(Hex.decode(keys[i])));
			hmac.update(m, 0, m.length);
			hmac.doFinal(resBuf, 0);

			if (!arraysEqual(resBuf, Hex.decode(digests[i])))
			{
				return new SimpleTestResult(false, getName() + "Vector " + i + " failed");
			}
		}

        // test reset
        int vector = 0; // vector used for test
        byte[] m = messages[vector].getBytes();
        if (messages[vector].startsWith("0x"))
        {
            m = Hex.decode(messages[vector].substring(2));
        }
        hmac.init(new KeyParameter(Hex.decode(keys[vector])));
        hmac.update(m, 0, m.length);
        hmac.doFinal(resBuf, 0);
        hmac.reset();
        hmac.update(m, 0, m.length);
        hmac.doFinal(resBuf, 0);

        if (!arraysEqual(resBuf, Hex.decode(digests[vector])))
        {
            return new SimpleTestResult(false, getName() +
                    "Reset with vector " + vector + " failed");
        }

        return new SimpleTestResult(true, getName() + ": Okay");
    }

    public static void main(
        String[]    args)
    {
        MD5HMacTest   test = new MD5HMacTest();
        TestResult    result = test.perform();

        log.debug(result);
    }
}
