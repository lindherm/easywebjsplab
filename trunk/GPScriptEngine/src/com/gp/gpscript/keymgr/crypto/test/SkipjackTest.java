package com.gp.gpscript.keymgr.crypto.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.engines.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;

/**
 */
public class SkipjackTest
    extends CipherTest
{private static Logger log = Logger.getLogger(SkipjackTest.class);
    static Test[]  tests =
            {
                new BlockCipherVectorTest(0, new SkipjackEngine(),
                        new KeyParameter(Hex.decode("00998877665544332211")),
                        "33221100ddccbbaa", "2587cae27a12d300")
            };

    SkipjackTest()
    {
        super(tests);
    }

    public String getName()
    {
        return "SKIPJACK";
    }

    public static void main(
        String[]    args)
    {
        SkipjackTest    test = new SkipjackTest();
        TestResult      result = test.perform();

        log.debug(result);
    }
}
