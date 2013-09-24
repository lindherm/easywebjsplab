package com.gp.gpscript.keymgr.crypto.params;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class AsymmetricKeyParameter
	implements CipherParameters
{
    boolean privateKey;

    public AsymmetricKeyParameter(
        boolean privateKey)
    {
        this.privateKey = privateKey;
    }

    public boolean isPrivate()
    {
        return privateKey;
    }
}
