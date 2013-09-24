package com.gp.gpscript.keymgr.crypto.io;

import java.io.*;

import com.gp.gpscript.keymgr.crypto.Mac;

public class MacOutputStream
    extends FilterOutputStream
{
    protected Mac mac;

    private boolean on;

    public MacOutputStream(
        OutputStream stream,
        Mac          mac)
    {
        super(stream);
        this.mac = mac;
    }

    public void write(int b)
        throws IOException
    {
        mac.update((byte)b);
        out.write(b);
    }

    public void write(
        byte[] b,
        int off,
        int len)
        throws IOException
    {
        mac.update(b, off, len);
        out.write(b, off, len);
    }

    public Mac getMac()
    {
        return mac;
    }
}

