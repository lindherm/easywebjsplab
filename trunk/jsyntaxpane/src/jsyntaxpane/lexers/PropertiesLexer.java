// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertiesLexer.java

package jsyntaxpane.lexers;

import java.io.*;
import jsyntaxpane.Token;
import jsyntaxpane.TokenType;

// Referenced classes of package jsyntaxpane.lexers:
//            DefaultJFlexLexer

public final class PropertiesLexer extends DefaultJFlexLexer
{

    private static int[] zzUnpackAction()
    {
        int result[] = new int[10];
        int offset = 0;
        offset = zzUnpackAction("\001\000\001\001\001\002\002\001\002\002\002\000\001\003", offset, result);
        return result;
    }

    private static int zzUnpackAction(String packed, int offset, int result[])
    {
        int i = 0;
        int j = offset;
        for(int l = packed.length(); i < l;)
        {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do
                result[j++] = value;
            while(--count > 0);
        }

        return j;
    }

    private static int[] zzUnpackRowMap()
    {
        int result[] = new int[10];
        int offset = 0;
        offset = zzUnpackRowMap("\000\000\000\b\000\020\000\030\000 \000(\000\b\0000\000 \000\b", offset, result);
        return result;
    }

    private static int zzUnpackRowMap(String packed, int offset, int result[])
    {
        int i = 0;
        int j = offset;
        for(int l = packed.length(); i < l;)
        {
            int high = packed.charAt(i++) << 16;
            result[j++] = high | packed.charAt(i++);
        }

        return j;
    }

    private static int[] zzUnpackTrans()
    {
        int result[] = new int[56];
        int offset = 0;
        offset = zzUnpackTrans("\001\002\001\003\001\002\001\004\001\002\002\005\001\002\b\000\003\003\001\006\001\007\003\003\004\000\001\002\005\000\001\b\002\000\002\t\001\n\004\000\001\007\005\000\001\b\003\000\001\b\001\n", offset, result);
        return result;
    }

    private static int zzUnpackTrans(String packed, int offset, int result[])
    {
        int i = 0;
        int j = offset;
        for(int l = packed.length(); i < l;)
        {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            value--;
            do
                result[j++] = value;
            while(--count > 0);
        }

        return j;
    }

    private static int[] zzUnpackAttribute()
    {
        int result[] = new int[10];
        int offset = 0;
        offset = zzUnpackAttribute("\001\000\001\t\004\001\001\t\002\000\001\t", offset, result);
        return result;
    }

    private static int zzUnpackAttribute(String packed, int offset, int result[])
    {
        int i = 0;
        int j = offset;
        for(int l = packed.length(); i < l;)
        {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do
                result[j++] = value;
            while(--count > 0);
        }

        return j;
    }

    public PropertiesLexer()
    {
        zzLexicalState = 0;
        zzBuffer = new char[16384];
        zzAtBOL = true;
    }

    public int yychar()
    {
        return yychar;
    }

    public PropertiesLexer(Reader in)
    {
        zzLexicalState = 0;
        zzBuffer = new char[16384];
        zzAtBOL = true;
        zzReader = in;
    }

    public PropertiesLexer(InputStream in)
    {
        this(((Reader) (new InputStreamReader(in))));
    }

    private static char[] zzUnpackCMap(String packed)
    {
        char map[] = new char[0x10000];
        int i = 0;
        int j = 0;
        while(i < 44) 
        {
            int count = packed.charAt(i++);
            char value = packed.charAt(i++);
            do
                map[j++] = value;
            while(--count > 0);
        }
        return map;
    }

    private boolean zzRefill()
        throws IOException
    {
        if(zzStartRead > 0)
        {
            System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);
            zzEndRead -= zzStartRead;
            zzCurrentPos -= zzStartRead;
            zzMarkedPos -= zzStartRead;
            zzStartRead = 0;
        }
        if(zzCurrentPos >= zzBuffer.length)
        {
            char newBuffer[] = new char[zzCurrentPos * 2];
            System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
            zzBuffer = newBuffer;
        }
        int numRead = zzReader.read(zzBuffer, zzEndRead, zzBuffer.length - zzEndRead);
        if(numRead > 0)
        {
            zzEndRead += numRead;
            return false;
        }
        if(numRead == 0)
        {
            int c = zzReader.read();
            if(c == -1)
            {
                return true;
            } else
            {
                zzBuffer[zzEndRead++] = (char)c;
                return false;
            }
        } else
        {
            return true;
        }
    }

    public final void yyclose()
        throws IOException
    {
        zzAtEOF = true;
        zzEndRead = zzStartRead;
        if(zzReader != null)
            zzReader.close();
    }

    public final void yyreset(Reader reader)
    {
        zzReader = reader;
        zzAtBOL = true;
        zzAtEOF = false;
        zzEOFDone = false;
        zzEndRead = zzStartRead = 0;
        zzCurrentPos = zzMarkedPos = 0;
        yyline = yychar = yycolumn = 0;
        zzLexicalState = 0;
    }

    public final int yystate()
    {
        return zzLexicalState;
    }

    public final void yybegin(int newState)
    {
        zzLexicalState = newState;
    }

    public final String yytext()
    {
        return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
    }

    public final char yycharat(int pos)
    {
        return zzBuffer[zzStartRead + pos];
    }

    public final int yylength()
    {
        return zzMarkedPos - zzStartRead;
    }

    private void zzScanError(int errorCode)
    {
        String message;
        try
        {
            message = ZZ_ERROR_MSG[errorCode];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            message = ZZ_ERROR_MSG[0];
        }
        throw new Error(message);
    }

    public void yypushback(int number)
    {
        if(number > yylength())
            zzScanError(2);
        zzMarkedPos -= number;
    }

    public Token yylex()
        throws IOException
    {
        int zzEndReadL = zzEndRead;
        char zzBufferL[] = zzBuffer;
        char zzCMapL[] = ZZ_CMAP;
        int zzTransL[] = ZZ_TRANS;
        int zzRowMapL[] = ZZ_ROWMAP;
        int zzAttrL[] = ZZ_ATTRIBUTE;
        do
        {
            int zzMarkedPosL = zzMarkedPos;
            yychar += zzMarkedPosL - zzStartRead;
            int zzAction = -1;
            int zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
            zzState = ZZ_LEXSTATE[zzLexicalState];
            int zzInput;
            int zzAttributes;
label0:
            do
            {
                do
                {
                    if(zzCurrentPosL < zzEndReadL)
                    {
                        zzInput = zzBufferL[zzCurrentPosL++];
                    } else
                    {
                        if(zzAtEOF)
                        {
                            zzInput = -1;
                            break label0;
                        }
                        zzCurrentPos = zzCurrentPosL;
                        zzMarkedPos = zzMarkedPosL;
                        boolean eof = zzRefill();
                        zzCurrentPosL = zzCurrentPos;
                        zzMarkedPosL = zzMarkedPos;
                        zzBufferL = zzBuffer;
                        zzEndReadL = zzEndRead;
                        if(eof)
                        {
                            zzInput = -1;
                            break label0;
                        }
                        zzInput = zzBufferL[zzCurrentPosL++];
                    }
                    int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
                    if(zzNext == -1)
                        break label0;
                    zzState = zzNext;
                    zzAttributes = zzAttrL[zzState];
                } while((zzAttributes & 1) != 1);
                zzAction = zzState;
                zzMarkedPosL = zzCurrentPosL;
            } while((zzAttributes & 8) != 8);
            zzMarkedPos = zzMarkedPosL;
            switch(zzAction >= 0 ? ZZ_ACTION[zzAction] : zzAction)
            {
            case 3: // '\003'
                return token(TokenType.KEYWORD);

            case 2: // '\002'
                return token(TokenType.COMMENT);

            default:
                if(zzInput == -1 && zzStartRead == zzCurrentPos)
                {
                    zzAtEOF = true;
                    return null;
                }
                zzScanError(1);
                break;

            case 1: // '\001'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
                break;
            }
        } while(true);
    }

    public static final int YYEOF = -1;
    private static final int ZZ_BUFFERSIZE = 16384;
    public static final int YYINITIAL = 0;
    private static final int ZZ_LEXSTATE[] = {
        0, 0
    };
    private static final String ZZ_CMAP_PACKED = "\t\000\001\002\001\004\002\000\001\003\022\000\001\006\002\000\001\001\n\000\001\005\001\000\n\005\003\000\001\007\003\000\032\005\004\000\001\005\001\000\032\005\uFF85\0";
    private static final char ZZ_CMAP[] = zzUnpackCMap("\t\000\001\002\001\004\002\000\001\003\022\000\001\006\002\000\001\001\n\000\001\005\001\000\n\005\003\000\001\007\003\000\032\005\004\000\001\005\001\000\032\005\uFF85\0");
    private static final int ZZ_ACTION[] = zzUnpackAction();
    private static final String ZZ_ACTION_PACKED_0 = "\001\000\001\001\001\002\002\001\002\002\002\000\001\003";
    private static final int ZZ_ROWMAP[] = zzUnpackRowMap();
    private static final String ZZ_ROWMAP_PACKED_0 = "\000\000\000\b\000\020\000\030\000 \000(\000\b\0000\000 \000\b";
    private static final int ZZ_TRANS[] = zzUnpackTrans();
    private static final String ZZ_TRANS_PACKED_0 = "\001\002\001\003\001\002\001\004\001\002\002\005\001\002\b\000\003\003\001\006\001\007\003\003\004\000\001\002\005\000\001\b\002\000\002\t\001\n\004\000\001\007\005\000\001\b\003\000\001\b\001\n";
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;
    private static final String ZZ_ERROR_MSG[] = {
        "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"
    };
    private static final int ZZ_ATTRIBUTE[] = zzUnpackAttribute();
    private static final String ZZ_ATTRIBUTE_PACKED_0 = "\001\000\001\t\004\001\001\t\002\000\001\t";
    private Reader zzReader;
    private int zzState;
    private int zzLexicalState;
    private char zzBuffer[];
    private int zzMarkedPos;
    private int zzCurrentPos;
    private int zzStartRead;
    private int zzEndRead;
    private int yyline;
    private int yychar;
    private int yycolumn;
    private boolean zzAtBOL;
    private boolean zzAtEOF;
    private boolean zzEOFDone;

}
