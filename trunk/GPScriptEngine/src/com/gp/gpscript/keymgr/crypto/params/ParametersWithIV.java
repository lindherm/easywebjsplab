package com.gp.gpscript.keymgr.crypto.params;

import com.gp.gpscript.keymgr.crypto.CipherParameters;

public class ParametersWithIV
    implements CipherParameters
{
    private byte[]              iv;
    private CipherParameters    parameters;

    public ParametersWithIV(
        CipherParameters    parameters,
        byte[]              iv)
    {
        this(parameters, iv, 0, iv.length);
    }

    public ParametersWithIV(
        CipherParameters    parameters,
        byte[]              iv,
        int                 ivOff,
        int                 ivLen)
    {
        this.iv = new byte[ivLen];
        this.parameters = parameters;

        System.arraycopy(iv, ivOff, this.iv, 0, ivLen);
    }

    public byte[] getIV()
    {
        byte[]  tmp = new byte[iv.length];

        System.arraycopy(iv, 0, tmp, 0, tmp.length);

        return iv;
    }

    public CipherParameters getParameters()
    {
        return parameters;
    }
}
