package com.gp.gpscript.keymgr.asn1.oiw;

import java.math.*;
import java.util.*;

import com.gp.gpscript.keymgr.asn1.*;

public class ElGamalParameter
    implements DEREncodable
{
    DERInteger      p, g;

    public ElGamalParameter(
        BigInteger  p,
        BigInteger  g)
    {
        this.p = new DERInteger(p);
        this.g = new DERInteger(g);
    }

    public ElGamalParameter(
        DERConstructedSequence  seq)
    {
        Enumeration     e = seq.getObjects();

        p = (DERInteger)e.nextElement();
        g = (DERInteger)e.nextElement();
    }

    public BigInteger getP()
    {
        return p.getPositiveValue();
    }

    public BigInteger getG()
    {
        return g.getPositiveValue();
    }

    public DERObject getDERObject()
    {
        DERConstructedSequence  seq = new DERConstructedSequence();

        seq.addObject(p);
        seq.addObject(g);

        return seq;
    }
}
