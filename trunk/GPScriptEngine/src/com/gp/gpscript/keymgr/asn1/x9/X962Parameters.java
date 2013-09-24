package com.gp.gpscript.keymgr.asn1.x9;

import com.gp.gpscript.keymgr.asn1.*;

public class X962Parameters
    implements DEREncodable
{
    private DERObject           params = null;

    public X962Parameters(
        X9ECParameters      ecParameters)
    {
        this.params = ecParameters.getDERObject();
    }

    public X962Parameters(
        DERObjectIdentifier  namedCurve)
    {
        this.params = namedCurve;
    }

    public X962Parameters(
        DERObject           obj)
    {
        this.params = obj;
    }

    public boolean isNamedCurve()
    {
        return (params instanceof DERObjectIdentifier);
    }

    public DERObject getParameters()
    {
        return params;
    }

    /**
     * <pre>
     * Parameters ::= CHOICE {
     *    ecParameters ECParameters,
     *    namedCurve   CURVES.&id({CurveNames}),
     *    implicitlyCA NULL
     * }
     * </pre>
     */
    public DERObject getDERObject()
    {
        return params;
    }
}
