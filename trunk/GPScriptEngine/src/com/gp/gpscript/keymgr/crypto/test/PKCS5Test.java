package com.gp.gpscript.keymgr.crypto.test;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.pkcs.*;
import com.gp.gpscript.keymgr.asn1.util.*;
import com.gp.gpscript.keymgr.asn1.x509.*;
import com.gp.gpscript.keymgr.crypto.*;
import com.gp.gpscript.keymgr.crypto.engines.*;
import com.gp.gpscript.keymgr.crypto.generators.*;
import com.gp.gpscript.keymgr.crypto.modes.*;
import com.gp.gpscript.keymgr.crypto.paddings.*;
import com.gp.gpscript.keymgr.crypto.params.*;
import com.gp.gpscript.keymgr.util.encoders.*;
import com.gp.gpscript.keymgr.util.test.*;



/**
 * a test class for PKCS5 PBES2 with PBKDF2 (PKCS5 v2.0) using
 * test vectors provider at
 * <a href=http://www.rsasecurity.com/rsalabs/pkcs/pkcs-5/index.html>
 * RSA's PKCS5 Page</a>
 * <p>
 * The vectors are Base 64 encoded and encrypted using the password "password"
 * (without quotes). They should all yield the same PrivateKeyInfo object.
 */
public class PKCS5Test
    implements PKCSObjectIdentifiers, Test
{private static Logger log = Logger.getLogger(PKCS5Test.class);
    /**
     * encrypted using des-cbc.
     */
    static byte[] sample1 = Base64.decode(
        "MIIBozA9BgkqhkiG9w0BBQ0wMDAbBgkqhkiG9w0BBQwwDgQIfWBDXwLp4K4CAggA"
      + "MBEGBSsOAwIHBAiaCF/AvOgQ6QSCAWDWX4BdAzCRNSQSANSuNsT5X8mWYO27mr3Y"
      + "9c9LoBVXGNmYWKA77MI4967f7SmjNcgXj3xNE/jmnVz6hhsjS8E5VPT3kfyVkpdZ"
      + "0lr5e9Yk2m3JWpPU7++v5zBkZmC4V/MwV/XuIs6U+vykgzMgpxQg0oZKS9zgmiZo"
      + "f/4dOCL0UtCDnyOSvqT7mCVIcMDIEKu8QbVlgZYBop08l60EuEU3gARUo8WsYQmO"
      + "Dz/ldx0Z+znIT0SXVuOwc+RVItC5T/Qx+aijmmpt+9l14nmaGBrEkmuhmtdvU/4v"
      + "aptewGRgmjOfD6cqK+zs0O5NrrJ3P/6ZSxXj91CQgrThGfOv72bUncXEMNtc8pks"
      + "2jpHFjGMdKufnadAD7XuMgzkkaklEXZ4f5tU6heIIwr51g0GBEGF96gYPFnjnSQM"
      + "75JE02Clo+DfcfXpcybPTwwFg2jd6JTTOfkdf6OdSlA/1XNK43FA");

    /**
     * encrypted using des-ede3-cbc.
     */
    static byte[] sample2 = Base64.decode(
        "MIIBpjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIeFeOWl1jywYCAggA"
      + "MBQGCCqGSIb3DQMHBAjUJ5eGBhQGtQSCAWBrHrRgqO8UUMLcWzZEtpk1l3mjxiF/"
      + "koCMkHsFwowgyWhEbgIkTgbSViK54LVK8PskekcGNLph+rB6bGZ7pPbL5pbXASJ8"
      + "+MkQcG3FZdlS4Ek9tTJDApj3O1UubZGFG4uvTlJJFbF1BOJ3MkY3XQ9Gl1qwv7j5"
      + "6e103Da7Cq9+oIDKmznza78XXQYrUsPo8mJGjUxPskEYlzwvHjKubRnYm/K6RKhi"
      + "5f4zX4BQ/Dt3H812ZjRXrsjAJP0KrD/jyD/jCT7zNBVPH1izBds+RwizyQAHwfNJ"
      + "BFR78TH4cgzB619X47FDVOnT0LqQNVd0O3cSwnPrXE9XR3tPayE+iOB15llFSmi8"
      + "z0ByOXldEpkezCn92Umk++suzIVj1qfsK+bv2phZWJPbLEIWPDRHUbYf76q5ArAr"
      + "u4xtxT/hoK3krEs/IN3d70qjlUJ36SEw1UaZ82PWhakQbdtu39ZraMJB");

    /**
     * encrypted using rc2-cbc.
     */
    static byte[] sample3 = Base64.decode(
        "MIIBrjBIBgkqhkiG9w0BBQ0wOzAeBgkqhkiG9w0BBQwwEQQIrHyQPBZqWLUCAggA"
      + "AgEQMBkGCCqGSIb3DQMCMA0CAToECEhbh7YZKiPSBIIBYCT1zp6o5jpFlIkgwPop"
      + "7bW1+8ACr4exqzkeb3WflQ8cWJ4cURxzVdvxUnXeW1VJdaQZtjS/QHs5GhPTG/0f"
      + "wtvnaPfwrIJ3FeGaZfcg2CrYhalOFmEb4xrE4KyoEQmUN8tb/Cg94uzd16BOPw21"
      + "RDnE8bnPdIGY7TyL95kbkqH23mK53pi7h+xWIgduW+atIqDyyt55f7WMZcvDvlj6"
      + "VpN/V0h+qxBHL274WA4dj6GYgeyUFpi60HdGCK7By2TBy8h1ZvKGjmB9h8jZvkx1"
      + "MkbRumXxyFsowTZawyYvO8Um6lbfEDP9zIEUq0IV8RqH2MRyblsPNSikyYhxX/cz"
      + "tdDxRKhilySbSBg5Kr8OfcwKp9bpinN96nmG4xr3Tch1bnVvqJzOQ5+Vva2WwVvH"
      + "2JkWvYm5WaANg4Q6bRxu9vz7DuhbJjQdZbxFezIAgrJdSe92B00jO/0Kny1WjiVO"
      + "6DA=");

    static byte[] result = Hex.decode(
        "30820155020100300d06092a864886f70d01010105000482013f3082013b020100024100"
      + "debbfc2c09d61bada2a9462f24224e54cc6b3cc0755f15ce318ef57e79df17026b6a85cc"
      + "a12428027245045df2052a329a2f9ad3d17b78a10572ad9b22bf343b020301000102402d"
      + "90a96adcec472743527bc023153d8f0d6e96b40c8ed228276d467d843306429f8670559b"
      + "f376dd41857f6397c2fc8d95e0e53ed62de420b855430ee4a1b8a1022100ffcaf0838239"
      + "31e073ff534f06a5d415b3d414bc614a4544a3dff7ed271817eb022100deea30242117db"
      + "2d3b8837f58f1da530ff83cf9283680da33683ec4e583610f1022100e6026381adb0a683"
      + "f16a8f4c096b462979b9e4277cc89f3ed8a905b46fa9ff9f02210097c146d4d1d2b3dbaf"
      + "53a504ff51674c5c271800de84d003f4f10ac6ab36e38102202bfa141f10bda874e1017d"
      + "845e82767c1c38e82745daf421f0c8cd09d765238700000000000000");

    private class PBETest
        implements Test
    {
        int                 id;
        BufferedBlockCipher cipher;
        byte[]              sample;
        int                 keySize;

        PBETest(
            int                 id,
            BufferedBlockCipher cipher,
            byte[]              sample,
            int                 keySize)
        {
            this.id = id;
            this.cipher = cipher;
            this.sample = sample;
            this.keySize = keySize;
        }

        public String getName()
        {
            return cipher.getUnderlyingCipher().getAlgorithmName() + " PKCS5S2 Test " + id;
        }

        public TestResult perform()
        {
            char[]                  password = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };
            PBEParametersGenerator  generator = new PKCS5S2ParametersGenerator();
            ByteArrayInputStream    bIn = new ByteArrayInputStream(sample);
            DERInputStream          dIn = new DERInputStream(bIn);
            EncryptedPrivateKeyInfo info;

            try
            {
                info = new EncryptedPrivateKeyInfo((DERConstructedSequence)dIn.readObject());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new SimpleTestResult(false, getName() + ": failed construction - exception " + e.toString());
            }

            PBES2Parameters         alg = new PBES2Parameters((DERConstructedSequence)info.getEncryptionAlgorithm().getParameters());
            PBKDF2Params            func = (PBKDF2Params)alg.getKeyDerivationFunc();
            EncryptionScheme        scheme = alg.getEncryptionScheme();

            if (func.getKeyLength() != null)
            {
                keySize = func.getKeyLength().intValue() * 8;
            }

            int     iterationCount = func.getIterationCount().intValue();
            byte[]  salt = func.getSalt();

            generator.init(
                PBEParametersGenerator.PKCS5PasswordToBytes(password),
                salt,
                iterationCount);

            CipherParameters    param;

            if (scheme.getObjectId().equals(RC2_CBC))
            {
                RC2CBCParameter rc2Params = new RC2CBCParameter((DERConstructedSequence)scheme.getObject());
                byte[]  iv = rc2Params.getIV();

                param = new ParametersWithIV(generator.generateDerivedParameters(keySize), iv);
            }
            else
            {
                byte[]  iv = ((DEROctetString)scheme.getObject()).getOctets();

                param = new ParametersWithIV(generator.generateDerivedParameters(keySize), iv);
            }

            cipher.init(false, param);

            byte[]  data = info.getEncryptedData();
            byte[]  out = new byte[cipher.getOutputSize(data.length)];
            int     len = cipher.processBytes(data, 0, data.length, out, 0);

            try
            {
                cipher.doFinal(out, len);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new SimpleTestResult(false, getName() + ": failed doFinal - exception " + e.toString());
            }

            if (result.length != out.length)
            {
                return new SimpleTestResult(false, getName() + ": failed");
            }

            for (int i = 0; i != out.length; i++)
            {
                if (out[i] != result[i])
                {
                    return new SimpleTestResult(false, getName() + ": failed");
                }
            }

            return new SimpleTestResult(true, getName() + ": Okay");
        }
    }

    public String getName()
    {
        return "PKCS5S2";
    }

    public TestResult perform()
    {
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESEngine()));
        Test                test = new PBETest(0, cipher, sample1, 64);
        TestResult          result = test.perform();

        if (!result.isSuccessful())
        {
            return new SimpleTestResult(false, getName() + ": " + result.toString());
        }

        cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
        test = new PBETest(1, cipher, sample2, 192);
        result = test.perform();

        if (!result.isSuccessful())
        {
            return new SimpleTestResult(false, getName() + ": " + result.toString());
        }

        cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RC2Engine()));
        test = new PBETest(2, cipher, sample3, 0);
        result = test.perform();

        if (!result.isSuccessful())
        {
            return new SimpleTestResult(false, getName() + ": " + result.toString());
        }

        return new SimpleTestResult(true, getName() + ": Okay");
    }

    public static void main(
        String[]    args)
    {
        Test    test = new PKCS5Test();

        TestResult  result = test.perform();

        log.debug(result.toString());
    }
}