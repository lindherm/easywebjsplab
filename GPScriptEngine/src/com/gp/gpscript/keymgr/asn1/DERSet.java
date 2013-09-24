package com.gp.gpscript.keymgr.asn1;

import java.io.*;

/**
 * DER Set with a single object.
 */
public class DERSet
    extends DERConstructedSet
{
    DERSet()
    {
    }

    /**
     * @param sequence the sequence making up the set
     */
    public DERSet(
        DEREncodable   sequence)
    {
        this.addObject(sequence);
    }

    public DERObject getSequence()
    {
        return (DERObject)this.getObjectAt(0);
    }
}
