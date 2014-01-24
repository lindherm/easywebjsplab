package com.gp.gpscript.keymgr.asn1;

import java.math.BigInteger;
import java.io.FilterInputStream;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.EOFException;


public class DERInputStream
    extends FilterInputStream implements DERTags, ASN1InputStream
{
    public DERInputStream(
        InputStream is)
    {
        super(is);
    }

    protected int readLength()
        throws IOException
    {
        int length = read();
        if (length < 0)
        {
            throw new IOException("EOF found when length expected");
        }

        if (length == 0x80)
        {
            return -1;      // indefinite-length encoding
        }

        if (length > 127)
        {
            int size = length & 0x7f;

            length = 0;
            for (int i = 0; i < size; i++)
            {
                int next = read();

                if (next < 0)
                {
                    throw new IOException("EOF found reading length");
                }

                length = (length << 8) + next;
            }
        }

        return length;
    }

    protected void readFully(
        byte[]  bytes)
        throws IOException
    {
        int     left = bytes.length;

        if (left == 0)
        {
            return;
        }

        while ((left -= read(bytes, bytes.length - left, left)) != 0)
        {
            ;
        }
    }

	/**
	 * build an object given its tag and a byte stream to construct it
	 * from.
	 */
    protected DERObject buildObject(
		int	    tag,
		byte[]	bytes)
		throws IOException
	{
		switch (tag)
        {
        case NULL:
            return null;
        case SEQUENCE | CONSTRUCTED:
            ByteArrayInputStream    bIn = new ByteArrayInputStream(bytes);
            BERInputStream          dIn = new BERInputStream(bIn);
            DERConstructedSequence  seq = new DERConstructedSequence();

            try
            {
                for (;;)
                {
                    DERObject   obj = dIn.readObject();

                    seq.addObject(obj);
                }
            }
            catch (EOFException ex)
            {
                ex.printStackTrace();
                return seq;
            }
        case SET | CONSTRUCTED:
            bIn = new ByteArrayInputStream(bytes);
            dIn = new BERInputStream(bIn);

            DERSet       set = new DERSet();

            try
            {
                for (;;)
                {
                    DERObject   obj = dIn.readObject();

                    set.addObject(obj);
                }
            }
            catch (EOFException ex)
            {
                ex.printStackTrace();
                return set;
            }
        case BOOLEAN:
            return new DERBoolean(bytes);
        case INTEGER:
            return new DERInteger(bytes);
        case ENUMERATED:
            return new DEREnumerated(bytes);
        case OBJECT_IDENTIFIER:
            return new DERObjectIdentifier(bytes);
        case BIT_STRING:
            int     padBits = bytes[0];
            byte[]  data = new byte[bytes.length - 1];

            System.arraycopy(bytes, 1, data, 0, bytes.length - 1);

            return new DERBitString(data, padBits);
        case UTF8_STRING:
            return new DERUTF8String(bytes);
        case PRINTABLE_STRING:
            return new DERPrintableString(bytes);
        case IA5_STRING:
            return new DERIA5String(bytes);
        case T61_STRING:
            return new DERT61String(bytes);
        case VISIBLE_STRING:
            return new DERVisibleString(bytes);
        case UNIVERSAL_STRING:
            return new DERUniversalString(bytes);
        case BMP_STRING:
            return new DERBMPString(bytes);
        case OCTET_STRING:
            return new DEROctetString(bytes);
        case UTC_TIME:
            return new DERUTCTime(bytes);
        case GENERALIZED_TIME:
            return new DERGeneralizedTime(bytes);
        default:
            //
            // with tagged object tag number is bottom 5 bits
            //
            if ((tag & (TAGGED | CONSTRUCTED)) != 0)
            {
                if ((tag & 0x1f) == 0x1f)
                {
                    throw new IOException("unsupported high tag encountered");
                }

                if (bytes.length == 0)        // empty tag!
                {
                    return new DERTaggedObject(tag & 0x1f);
                }

                //
                // simple type - implicit... return an octet string
                //
                if ((tag & CONSTRUCTED) == 0)
                {
                    return new DERTaggedObject(false, tag & 0x1f, new DEROctetString(bytes));
                }

                bIn = new ByteArrayInputStream(bytes);
                dIn = new BERInputStream(bIn);

                DEREncodable dObj = dIn.readObject();

                //
                // explicitly tagged (probably!) - if it isn't we'd have to
                // tell from the context
                //
                if (dIn.available() == 0)
                {
                    return new DERTaggedObject(tag & 0x1f, dObj);
                }

                //
                // another implicit object, we'll create a sequence...
                //
                seq = new DERConstructedSequence();

                seq.addObject(dObj);

                try
                {
                    for (;;)
                    {
                        dObj = dIn.readObject();

                        seq.addObject(dObj);
                    }
                }
                catch (EOFException ex)
                {
                    ex.printStackTrace();
                    // ignore --
                }

                return new DERTaggedObject(false, tag & 0x1f, seq);
            }

            return new DERUnknownTag(tag, bytes);
        }
	}

    public DERObject readObject()
        throws IOException
    {
        int tag = read();
        if (tag == -1)
        {
            throw new EOFException();
        }

        int     length = readLength();
        byte[]  bytes = new byte[length];

        readFully(bytes);

		return buildObject(tag, bytes);
	}
}