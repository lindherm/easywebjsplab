package com.gp.gpscript.keymgr.crypto.test;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.crypto.AsymmetricBlockCipher;
import com.gp.gpscript.keymgr.crypto.AsymmetricCipherKeyPair;
import com.gp.gpscript.keymgr.crypto.digests.RIPEMD128Digest;
import com.gp.gpscript.keymgr.crypto.digests.RIPEMD160Digest;
import com.gp.gpscript.keymgr.crypto.digests.SHA1Digest;
import com.gp.gpscript.keymgr.crypto.encodings.ISO9796d1Encoding;
import com.gp.gpscript.keymgr.crypto.engines.RSAEngine;
import com.gp.gpscript.keymgr.crypto.generators.RSAKeyPairGenerator;
import com.gp.gpscript.keymgr.crypto.params.RSAKeyGenerationParameters;
import com.gp.gpscript.keymgr.crypto.params.RSAKeyParameters;
import com.gp.gpscript.keymgr.crypto.signers.ISO9796d2Signer;
import com.gp.gpscript.keymgr.util.encoders.Hex;
import com.gp.gpscript.keymgr.util.test.*;



/**
 * test vectors from ISO 9796-1 and ISO 9796-2 edition 1.
 */
public class ISO9796Test
    implements Test
{private static Logger log = Logger.getLogger(ISO9796Test.class);
    static BigInteger mod1 = new BigInteger("0100000000000000000000000000000000bba2d15dbb303c8a21c5ebbcbae52b7125087920dd7cdf358ea119fd66fb064012ec8ce692f0a0b8e8321b041acd40b7", 16);

    static BigInteger pub1 = new BigInteger("03", 16);

    static BigInteger pri1 = new BigInteger("2aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaac9f0783a49dd5f6c5af651f4c9d0dc9281c96a3f16a85f9572d7cc3f2d0f25a9dbf1149e4cdc32273faadd3fda5dcda7", 16);

    static BigInteger mod2 = new BigInteger("ffffff7fa27087c35ebead78412d2bdffe0301edd494df13458974ea89b364708f7d0f5a00a50779ddf9f7d4cb80b8891324da251a860c4ec9ef288104b3858d", 16);

    static BigInteger pub2 = new BigInteger("03", 16);

    static BigInteger pri2 = new BigInteger("2aaaaa9545bd6bf5e51fc7940adcdca5550080524e18cfd88b96e8d1c19de6121b13fac0eb0495d47928e047724d91d1740f6968457ce53ec8e24c9362ce84b5", 16);

    static byte msg1[] = Hex.decode("0cbbaa99887766554433221100");

    //
    // you'll need to see the ISO 9796 to make sense of this
    //
    static byte sig1[] = mod1.subtract(new BigInteger("309f873d8ded8379490f6097eaafdabc137d3ebfd8f25ab5f138d56a719cdc526bdd022ea65dabab920a81013a85d092e04d3e421caab717c90d89ea45a8d23a", 16)).toByteArray();

    static byte msg2[] = Hex.decode("fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210");

    static byte sig2[] = new BigInteger("319bb9becb49f3ed1bca26d0fcf09b0b0a508e4d0bd43b350f959b72cd25b3af47d608fdcd248eada74fbe19990dbeb9bf0da4b4e1200243a14e5cab3f7e610c", 16).toByteArray();

    static byte msg3[] = Hex.decode("0112233445566778899aabbccd");

    static byte sig3[] = mod2.subtract(new BigInteger("58e59ffb4b1fb1bcdbf8d1fe9afa3730c78a318a1134f5791b7313d480ff07ac319b068edf8f212945cb09cf33df30ace54f4a063fcca0b732f4b662dc4e2454", 16)).toByteArray();

    //
    // ISO 9796-2
    //
    static BigInteger mod3 = new BigInteger("ffffffff78f6c55506c59785e871211ee120b0b5dd644aa796d82413a47b24573f1be5745b5cd9950f6b389b52350d4e01e90009669a8720bf265a2865994190a661dea3c7828e2e7ca1b19651adc2d5", 16);

    static BigInteger pub3 = new BigInteger("03", 16);

    static BigInteger pri3 = new BigInteger("2aaaaaaa942920e38120ee965168302fd0301d73a4e60c7143ceb0adf0bf30b9352f50e8b9e4ceedd65343b2179005b2f099915e4b0c37e41314bb0821ad8330d23cba7f589e0f129b04c46b67dfce9d", 16);

    static BigInteger mod4 = new BigInteger("FFFFFFFF45f1903ebb83d4d363f70dc647b839f2a84e119b8830b2dec424a1ce0c9fd667966b81407e89278283f27ca8857d40979407fc6da4cc8a20ecb4b8913b5813332409bc1f391a94c9c328dfe46695daf922259174544e2bfbe45cc5cd", 16);
    static BigInteger pub4 = new BigInteger("02", 16);
    static BigInteger pri4 = new BigInteger("1fffffffe8be3207d7707a9a6c7ee1b8c8f7073e5509c2337106165bd8849439c193faccf2cd70280fd124f0507e4f94cb66447680c6b87b6599d1b61c8f3600854a618262e9c1cb1438e485e47437be036d94b906087a61ee74ab0d9a1accd8", 16);

    static byte msg4[] = Hex.decode("6162636462636465636465666465666765666768666768696768696a68696a6b696a6b6c6a6b6c6d6b6c6d6e6c6d6e6f6d6e6f706e6f7071");
    static byte sig4[] = Hex.decode("374695b7ee8b273925b4656cc2e008d41463996534aa5aa5afe72a52ffd84e118085f8558f36631471d043ad342de268b94b080bee18a068c10965f581e7f32899ad378835477064abed8ef3bd530fce");

    static byte msg5[] = Hex.decode("fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210fedcba9876543210");
    static byte sig5[] = Hex.decode("5cf9a01854dbacaec83aae8efc563d74538192e95466babacd361d7c86000fe42dcb4581e48e4feb862d04698da9203b1803b262105104d510b365ee9c660857ba1c001aa57abfd1c8de92e47c275cae");

    static byte sig6[] = mod4.subtract(new BigInteger("680b616d41c7f5df782b554c490443aa5cc0f78adef24d18930f1b97d3eb9dc83ebee5e29f8fdf07fa8c0b47918c4c2efde260e73ae313c05881360e278779a8b7222cc2a8c2e7778431c1e946619cf3af99201996cffc5e5c6c1b1c1a18cd1f", 16)).toByteArray();

    public String getName()
    {
        return "ISO9796";
    }

    private boolean isSameAs(
        byte[]  a,
        int     off,
        byte[]  b)
    {
        if ((a.length - off) != b.length)
        {
            return false;
        }

        for (int i = 0; i != b.length; i++)
        {
            if (a[i + off] != b[i])
            {
                return false;
            }
        }

        return true;
    }

    private TestResult doTest1()
    {
        RSAKeyParameters    pubParameters = new RSAKeyParameters(false, mod1, pub1);
        RSAKeyParameters    privParameters = new RSAKeyParameters(true, mod1, pri1);
        RSAEngine           rsa = new RSAEngine();
        byte[]              data;

        //
        // ISO 9796-1 - public encrypt, private decrypt
        //
        ISO9796d1Encoding eng = new ISO9796d1Encoding(rsa);

        eng.init(true, privParameters);

        eng.setPadBits(4);

        try
        {
            data = eng.processBlock(msg1, 0, msg1.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        eng.init(false, pubParameters);

        if (!isSameAs(sig1, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 generation Test 1");
        }

        try
        {
            data = eng.processBlock(data, 0, data.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        if (!isSameAs(msg1, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 retrieve Test 1");
        }

        return new SimpleTestResult(true, "ISO9796: Okay");
    }

    private TestResult doTest2()
    {
        RSAKeyParameters    pubParameters = new RSAKeyParameters(false, mod1, pub1);
        RSAKeyParameters    privParameters = new RSAKeyParameters(true, mod1, pri1);
        RSAEngine           rsa = new RSAEngine();
        byte[]              data;

        //
        // ISO 9796-1 - public encrypt, private decrypt
        //
        ISO9796d1Encoding eng = new ISO9796d1Encoding(rsa);

        eng.init(true, privParameters);

        try
        {
            data = eng.processBlock(msg2, 0, msg2.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        eng.init(false, pubParameters);

        if (!isSameAs(data, 1, sig2))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 generation Test 2");
        }

        try
        {
            data = eng.processBlock(data, 0, data.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        if (!isSameAs(msg2, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 retrieve Test 2");
        }

        return new SimpleTestResult(true, "ISO9796: Okay");
    }

    public TestResult doTest3()
    {
        RSAKeyParameters    pubParameters = new RSAKeyParameters(false, mod2, pub2);
        RSAKeyParameters    privParameters = new RSAKeyParameters(true, mod2, pri2);
        RSAEngine           rsa = new RSAEngine();
        byte[]              data;

        //
        // ISO 9796-1 - public encrypt, private decrypt
        //
        ISO9796d1Encoding eng = new ISO9796d1Encoding(rsa);

        eng.init(true, privParameters);

        eng.setPadBits(4);

        try
        {
            data = eng.processBlock(msg3, 0, msg3.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        eng.init(false, pubParameters);

        if (!isSameAs(sig3, 1, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 generation Test 3");
        }

        try
        {
            data = eng.processBlock(data, 0, data.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        if (!isSameAs(msg3, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-1 retrieve Test 3");
        }

        return new SimpleTestResult(true, "ISO9796: Okay");
    }

    public TestResult doTest4()
    {
        RSAKeyParameters    pubParameters = new RSAKeyParameters(false, mod3, pub3);
        RSAKeyParameters    privParameters = new RSAKeyParameters(true, mod3, pri3);
        RSAEngine           rsa = new RSAEngine();
        byte[]              data;

        //
        // ISO 9796-2 - Signing
        //
        ISO9796d2Signer eng = new ISO9796d2Signer(rsa, new RIPEMD128Digest());

        eng.init(true, privParameters);

        try
        {
            eng.update(msg4[0]);
            eng.update(msg4, 1, msg4.length - 1);

            data = eng.generateSignature();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        eng.init(false, pubParameters);

        if (!isSameAs(sig4, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-2 generation Test 4");
        }

        eng.update(msg4[0]);
        eng.update(msg4, 1, msg4.length - 1);

        if (!eng.verifySignature(sig4))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-2 verify Test 4");
        }

        return new SimpleTestResult(true, "ISO9796: Okay");
    }

    public TestResult doTest5()
    {
        RSAKeyParameters    pubParameters = new RSAKeyParameters(false, mod3, pub3);
        RSAKeyParameters    privParameters = new RSAKeyParameters(true, mod3, pri3);
        RSAEngine           rsa = new RSAEngine();
        byte[]              data;

        //
        // ISO 9796-2 - Signing
        //
        ISO9796d2Signer eng = new ISO9796d2Signer(rsa, new RIPEMD160Digest(), true);

        eng.init(true, privParameters);

        try
        {
            eng.update(msg5[0]);
            eng.update(msg5, 1, msg5.length - 1);

            data = eng.generateSignature();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new SimpleTestResult(false, "ISO9796: failed - exception " + e.toString());
        }

        eng.init(false, pubParameters);

        if (!isSameAs(sig5, 0, data))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-2 generation Test 5");
        }

        eng.update(msg5[0]);
        eng.update(msg5, 1, msg5.length - 1);

        if (!eng.verifySignature(sig5))
        {
            return new SimpleTestResult(false, "ISO9796: failed ISO9796-2 verify Test 5");
        }

        return new SimpleTestResult(true, "ISO9796: Okay");
    }

    public TestResult perform()
    {
        TestResult  res = doTest1();
        if (!res.isSuccessful())
        {
            return res;
        }

        res = doTest2();
        if (!res.isSuccessful())
        {
            return res;
        }

        res = doTest3();
        if (!res.isSuccessful())
        {
            return res;
        }

        res = doTest4();
        if (!res.isSuccessful())
        {
            return res;
        }

        return doTest5();
    }

    public static void main(
        String[]    args)
    {
        ISO9796Test         test = new ISO9796Test();
        TestResult      result = test.perform();

        log.debug(result);
    }
}
