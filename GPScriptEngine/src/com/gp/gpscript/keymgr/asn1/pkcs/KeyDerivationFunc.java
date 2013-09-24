package com.gp.gpscript.keymgr.asn1.pkcs;

import java.util.*;

import com.gp.gpscript.keymgr.asn1.*;
import com.gp.gpscript.keymgr.asn1.x509.*;

public class KeyDerivationFunc
    extends AlgorithmIdentifier
{
    KeyDerivationFunc(
        DERConstructedSequence  seq)
    {
        super(seq);
    }
}
