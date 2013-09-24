package com.gp.gpscript.keymgr.asn1;

import java.io.IOException;

import com.gp.gpscript.keymgr.asn1.DERObject;

public interface ASN1InputStream
{
	public DERObject readObject()
		throws IOException;

	public void close()
		throws IOException;
}