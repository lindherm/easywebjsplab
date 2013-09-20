package com.gp.gpscript.profile.key;
import java.lang.*;
import org.w3c.dom.*;

import com.gp.gpscript.profile.*;
/**
Usage element determines for what cryptographic operations the key defined by the key profile can be used for.
Each of the usages reflect the usage of the particular key as the source key in the cryptographic operation. In
relation to the GP Scripting language [GP_SYS_SCR] , the usage is important in specifying whether certain
cryptographic methods in the Crypto function can be executed or not.
*/
public class kpUsage  extends ProfileNode
{
/**
Whether the cryptographic operation of
encrypt is permissible using a key defined by
this key profile. For example, if Encrypt is
specified, then the key defined by this key
profile can be used to encrypt arbitrary data.
In the absence of this attribute, the default
value is no support for the encrypt operation
using this key.
*/
public		String Encrypt;
/**
Whether the cryptographic operation of
decrypt is permissible on a key defined by this
key profile. For example, if Decrypt is
specified, then the key defined by this key
profile can be used to decrypt arbitrary data.
In the absence of this attribute, the default
value is no support for the decrypt operation
using this key.
*/
public		String Decrypt;
/**
Whether the cryptographic operation of
decrypt followed by encrypt is permissible
using a key defined by this key profile. For
example, if DecryptEncrypt is specified, then
the key defined by this key profile can be
used to as either the decrypt or encrypt key
when decrypting arbitrary data with one key
and encrypting the result with another key.
In the absence of this attribute, the default
value is no support for the decrypt and
encrypt operation using this key.
*/
public		String DecryptEncrypt;
/**
Whether the cryptographic operation of sign is
permissible using a key defined by this key
profile. For example, if Sign is specified, then
the key defined by this key profile can be
used to sign arbitrary data.
In the absence of this attribute, the default
value is no support for the sign operation
using this key.
*/
public		String Sign;
/**
Whether the cryptographic operation of verify
is permissible using a key defined by this key
profile. For example, if Verify is specified,
then the key defined by this key profile can be
used to verify whether a signature for data
provided is valid.
In the absence of this attribute, the default
value is no support for the verify operation
using this key.
*/
public		String Verify;
/**
Whether the cryptographic operation of verify
recover is permissible using a key defined by
this key profile. For example, if VerifyRecover
is specified, then the key defined by this key
profile can be used to verify whether a
signature is valid.
In the absence of this attribute, the default
value is no support for the verify recover
operation using this key.
*/
public		String VerifyRecover;
/**
Whether the cryptographic operation of wrap
is permissible using a key defined by this key
profile. For example, if Wrap is specified,
then the key defined by this key profile can be
used to wrap another key.
The usage of the key to be wrapped by this
key does not affect whether it can be wrapped
or not. Thus, any key can be wrapped.
In the absence of this attribute, the default
value is no support for the wrap operation
using this key.
*/
public		String Wrap;
/**
Whether the cryptographic operation of
unwrap is permissible using a key defined by
this key profile. For example, if Unwrap is
specified, then the key defined by this key
profile can be used to unwrap another key.
The usage of the key to be unwrapped does
not affect whether it can be unwrapped or not.
Thus, any key can be unwrapped.
In the absence of this attribute, the default
value is no support for the unwrap operation
using this key.
*/
public		String Unwrap;
/**
Whether a key defined by this key profile can
participate in either the Unwrap or the Wrap
of an UnwrapWrap operation of another key.
This usage does not allow a key to participate
in a solely Unwrap operation of another key or
a solely Wrap operation of another key.
In the absence of this attribute, the default
value is no support for participation in an
UnwrapWrap operation.;
*/
public		String UnwrapWrap;
/**
Whether the cryptographic operation of key
derivation is permissible using a key defined
by this key profile. For example, if Derive is
specified, then the key defined by this key
profile can be used to derive a key value for
another key.
In the absence of this attribute, the default
value is no support for the key derivation
operation using this key.
*/
public		String Derive;

	public kpUsage(Node node)
	{
			super(node);
	if(node.hasAttributes())
	{	Node attr;
		NamedNodeMap map=node.getAttributes();
		attr=map.getNamedItem("Encrypt");
	 	if(attr!=null)Encrypt=attr.getNodeValue();

	 	attr=map.getNamedItem("Decrypt");
	 	if(attr!=null)Decrypt=attr.getNodeValue();

		attr=map.getNamedItem("DecryptEncrypt");
	 	if(attr!=null)DecryptEncrypt=attr.getNodeValue();

	 	attr=map.getNamedItem("Sign");
	 	if(attr!=null)Sign=attr.getNodeValue();

	 	attr=map.getNamedItem("Verify");
	 	if(attr!=null)Verify=attr.getNodeValue();

	 	attr=map.getNamedItem("VerifyRecover");
	 	if(attr!=null)VerifyRecover=attr.getNodeValue();

	 	attr=map.getNamedItem("Wrap");
	 	if(attr!=null)Wrap=attr.getNodeValue();

	 	attr=map.getNamedItem("Unwrap");
	 	if(attr!=null)Unwrap=attr.getNodeValue();

	 	attr=map.getNamedItem("UnwrapWrap");
	 	if(attr!=null)UnwrapWrap=attr.getNodeValue();

	 	attr=map.getNamedItem("Derive");
	 	if(attr!=null)Derive=attr.getNodeValue();
	 }

	}

}