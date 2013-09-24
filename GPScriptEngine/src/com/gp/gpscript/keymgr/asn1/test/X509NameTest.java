package com.gp.gpscript.keymgr.asn1.test;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;
import com.gp.gpscript.keymgr.util.encoders.*;
import com.gp.gpscript.keymgr.util.test.*;


public class X509NameTest
	implements Test
{private static Logger log = Logger.getLogger(X509NameTest.class);
	public String getName()
	{
		return "X509Name";
	}

	public TestResult perform()
	{
        Hashtable                   attrs = new Hashtable();

        attrs.put(X509Name.C, "AU");
        attrs.put(X509Name.O, "The Legion of the Bouncy Castle");
        attrs.put(X509Name.L, "Melbourne");
        attrs.put(X509Name.ST, "Victoria");
        attrs.put(X509Name.E, "feedback-crypto@bouncycastle.org");

        X509Name    name1 = new X509Name(attrs);

        if (!name1.equals(name1))
        {
            return new SimpleTestResult(false, getName() + ": Failed same object test");
        }

        X509Name    name2 = new X509Name(attrs);

        if (!name1.equals(name2))
        {
            return new SimpleTestResult(false, getName() + ": Failed same name test");
        }

        Vector  ord1 = new Vector();

        ord1.addElement(X509Name.C);
        ord1.addElement(X509Name.O);
        ord1.addElement(X509Name.L);
        ord1.addElement(X509Name.ST);
        ord1.addElement(X509Name.E);

        Vector  ord2 = new Vector();

        ord2.addElement(X509Name.E);
        ord2.addElement(X509Name.ST);
        ord2.addElement(X509Name.L);
        ord2.addElement(X509Name.O);
        ord2.addElement(X509Name.C);

        name1 = new X509Name(ord1, attrs);
        name2 = new X509Name(ord2, attrs);

        if (!name1.equals(name2))
        {
            return new SimpleTestResult(false, getName() + ": Failed reverse name test");
        }

        ord2 = new Vector();

        ord2.addElement(X509Name.ST);
        ord2.addElement(X509Name.ST);
        ord2.addElement(X509Name.L);
        ord2.addElement(X509Name.O);
        ord2.addElement(X509Name.C);

        name1 = new X509Name(ord1, attrs);
        name2 = new X509Name(ord2, attrs);

        if (name1.equals(name2))
        {
            return new SimpleTestResult(false, getName() + ": Failed different name test");
        }

        ord2 = new Vector();

        ord2.addElement(X509Name.ST);
        ord2.addElement(X509Name.L);
        ord2.addElement(X509Name.O);
        ord2.addElement(X509Name.C);

        name1 = new X509Name(ord1, attrs);
        name2 = new X509Name(ord2, attrs);

        if (name1.equals(name2))
        {
            return new SimpleTestResult(false, getName() + ": Failed subset name test");
        }

        return new SimpleTestResult(true, getName() + ": Okay");
    }

    public static void main(
        String[]    args)
    {
        Test    test = new X509NameTest();

        TestResult  result = test.perform();

        log.debug(result);
    }
}
