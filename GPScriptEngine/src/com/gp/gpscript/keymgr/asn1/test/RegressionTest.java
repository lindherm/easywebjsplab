package com.gp.gpscript.keymgr.asn1.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.util.test.*;

public class RegressionTest
{private static Logger log = Logger.getLogger(RegressionTest.class);
    public static Test[]    tests = {
		new CertificateTest(),
		new OIDTest(),
		new PKCS10Test(),
        new X509NameTest(),
		new EncryptedPrivateKeyInfoTest()
    };

    public static void main(
        String[]    args)
    {
        for (int i = 0; i != tests.length; i++)
        {
            TestResult  result = tests[i].perform();
            log.debug(result);
        }
    }
}

