package com.gp.gpscript.keymgr.crypto.test;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.engines.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;

/**
 * cast6 tester - vectors from http://www.ietf.org/rfc/rfc2612.txt
 */
public class CAST6Test
    extends CipherTest
{private static Logger log = Logger.getLogger(CAST6Test.class);
    static Test[]  tests = {
		new BlockCipherVectorTest(0, new CAST6Engine(),
			new KeyParameter(Hex.decode("2342bb9efa38542c0af75647f29f615d")),
			"00000000000000000000000000000000",
			"c842a08972b43d20836c91d1b7530f6b"),
		new BlockCipherVectorTest(0, new CAST6Engine(),
			new KeyParameter(Hex.decode("2342bb9efa38542cbed0ac83940ac298bac77a7717942863")),
			"00000000000000000000000000000000",
			"1b386c0210dcadcbdd0e41aa08a7a7e8"),
		new BlockCipherVectorTest(0, new CAST6Engine(),
			new KeyParameter(Hex.decode("2342bb9efa38542cbed0ac83940ac2988d7c47ce264908461cc1b5137ae6b604")),
			"00000000000000000000000000000000",
			"4f6a2038286897b9c9870136553317fa")
            };

    CAST6Test()
    {
        super(tests);
    }

    public String getName()
    {
        return "CAST6";
    }

    public static void main(
        String[]    args)
    {
        CAST6Test    	test = new CAST6Test();
        TestResult      result = test.perform();

        log.debug(result);
    }
}
