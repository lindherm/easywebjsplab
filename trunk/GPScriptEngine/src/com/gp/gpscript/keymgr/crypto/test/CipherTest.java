package com.gp.gpscript.keymgr.crypto.test;

import com.gp.gpscript.keymgr.util.test.*;

/**
 */
public abstract class CipherTest
    implements Test
{
    Test[]      tests;

    protected CipherTest(
        Test[]  tests)
    {
        this.tests = tests;
    }

    public abstract String getName();

    public TestResult perform()
    {
        for (int i = 0; i != tests.length; i++)
        {
            TestResult  res = tests[i].perform();

            if (!res.isSuccessful())
            {
                return res;
            }
        }

        return new SimpleTestResult(true, getName() + ": Okay");
    }
}
