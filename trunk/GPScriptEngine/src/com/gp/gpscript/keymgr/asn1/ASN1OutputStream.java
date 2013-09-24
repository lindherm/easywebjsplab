package com.gp.gpscript.keymgr.asn1;

import java.io.IOException;

public interface ASN1OutputStream
{
	public void writeObject(Object obj)
		throws IOException;

	public void close()
		throws IOException;
}