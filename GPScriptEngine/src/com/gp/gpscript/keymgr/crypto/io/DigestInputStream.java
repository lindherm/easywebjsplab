package com.gp.gpscript.keymgr.crypto.io;

import java.io.*;

import com.gp.gpscript.keymgr.crypto.Digest;

public class DigestInputStream
    extends FilterInputStream
{
    protected Digest digest;

    public DigestInputStream(
        InputStream stream,
        Digest      digest)
    {
        super(stream);
        this.digest = digest;
    }

    public int read()
        throws IOException
    {
        int b = in.read();

        if (b >= 0)
        {
            digest.update((byte)b);
        }
        return b;
    }

    public int read(
        byte[] b,
        int off,
        int len)
        throws IOException
    {
        int n = in.read(b, off, len);
        if (n > 0)
        {
            digest.update(b, off, n);
        }
        return n;
    }

    public Digest getDigest()
    {
        return digest;
    }
}
