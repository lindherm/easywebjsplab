package com.gp.gpscript.keymgr.asn1.misc;

import com.gp.gpscript.keymgr.asn1.*;

public class VerisignCzagExtension
    extends DERIA5String
{
    public VerisignCzagExtension(
        DERIA5String str)
    {
        super(str.getString());
    }

    public String toString()
    {
        return "VerisignCzagExtension: " + this.getString();
    }
}
