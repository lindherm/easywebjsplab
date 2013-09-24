package com.gp.gpscript.keymgr.crypto.generators;

import com.gp.gpscript.keymgr.crypto.CipherKeyGenerator;
import com.gp.gpscript.keymgr.crypto.CipherParameters;
import com.gp.gpscript.keymgr.crypto.params.DESParameters;

public class DESKeyGenerator
    extends CipherKeyGenerator
{
    public byte[] generateKey()
    {
        byte[]  newKey = new byte[DESParameters.DES_KEY_LENGTH];

        do
        {
            random.nextBytes(newKey);

            DESParameters.setOddParity(newKey);
        }
        while (DESParameters.isWeakKey(newKey, 0));

        return newKey;
    }
}
