// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XmlLexer.java

package jsyntaxpane.lexers;

import java.io.*;
import jsyntaxpane.Token;
import jsyntaxpane.TokenType;

// Referenced classes of package jsyntaxpane.lexers:
//            DefaultJFlexLexer

public final class XmlLexer extends DefaultJFlexLexer
{

    private static int[] zzUnpackAction()
    {
        int result[] = new int[57];
        int offset = 0;
        offset = zzUnpackAction("\005\000\007\001\001\002\005\001\001\000\001\003\013\000\001\004\001\005\001\000\001\006\001\000\001\007\002\000\001\b\001\000\001\t\002\000\001\n\001\000\001\013\001\f\002\000\001\r\001\016\004\000\001\017", offset, result);
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
        int result[] = new int[57];
        int offset = 0;
        offset = zzUnpackRowMap("\000\000\000\032\0004\000N\000h\000\202\000\234\000\266\000\320\000\352\000\u0104\000\u011E\000\202\000\u0138\000\u0152\000\u016C\000\u0186\000\u01A0\000\u01BA\000\u01D4\000\u01EE\000\u0208\000\u0222\000\u023C\000\320\000\u0256\000\u0270\000\u0104\000\u028A\000\u02A4\000\u0138\000\202\000\202\000\u016C\000\202\000\u0186\000\202\000\u02BE\000\u02D8\000\u02F2\000\u030C\000\202\000\u0326\000\u0340\000\202\000\u035A\000\202\000\202\000\u0374\000\u038E\000\202\000\202\000\u03A8\000\u03C2\000\u03DC\000\u03F6\000\202", offset, result);
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
        int result[] = new int[1040];
        int offset = 0;
        offset = zzUnpackTrans("\003\006\001\007\022\006\001\b\003\006\005\t\001\n\024\t\021\013\001\f\b\013\006\006\001\r\002\016\004\006\004\016\001\006\001\017\001\006\001\020\001\021\013\006\002\016\002\006\001\022\001\006\004\016\003\006\001\020\001\021\004\006\036\000\001\023\002\000\002\024\002\000\001\025\001\000\004\024\001\000\001\026\017\000\001\027\017\000\001\030\001\000\005\031\001\032\031\031\001\033\024\031\021\034\001\035\031\034\001\036\b\034\005\000\001\037\001\000\004\037\002\000\004\037\002\000\001 \f\000\001!\023\000\002\"\001\000\021\"\001#\005\"\002$\001\000\022$\001#\004$\006\000\001%\030\000\001&\006\000\001'\022\000\001\024\001\000\004\024\002\000\004\024\020\000\002(\004\000\004(\020\000\002)\004\000\004)\021\000\001\027\016\000\001*\f\000\001+\016\000\001+\005\031\001,\031\031\001,\001-\023\031\021\034\001.\016\034\001/\n\034\001.\b\034\005\000\0010!\000\0011\021\000\001(\001\000\004(\002\000\004(\n\000\0022\002\000\001)\0013\004)\002\000\004)\023\000\001+\f\000\001*\001\000\001+\005\031\001,\0014\023\031\006\034\0014\n\034\001.\b\034\016\000\0015\f\000\0022\003\000\0013\"\000\0016\032\000\0017\030\000\0018\026\000\0019\r\0", offset, result);
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
        int result[] = new int[57];
        int offset = 0;
        offset = zzUnpackAttribute("\005\000\001\t\006\001\001\t\005\001\001\000\001\001\013\000\002\t\001\000\001\t\001\000\001\t\002\000\001\001\001\000\001\t\002\000\001\t\001\000\002\t\002\000\002\t\004\000\001\t", offset, result);
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

    public XmlLexer()
    {
        zzLexicalState = 0;
        zzBuffer = new char[16384];
        zzAtBOL = true;
    }

    public int yychar()
    {
        return yychar;
    }

    public XmlLexer(Reader in)
    {
        zzLexicalState = 0;
        zzBuffer = new char[16384];
        zzAtBOL = true;
        zzReader = in;
    }

    public XmlLexer(InputStream in)
    {
        this(((Reader) (new InputStreamReader(in))));
    }

    private static char[] zzUnpackCMap(String packed)
    {
        char map[] = new char[0x10000];
        int i = 0;
        int j = 0;
        while(i < 218) 
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
            case 12: // '\f'
                yybegin(2);
                return token(TokenType.COMMENT2, 4);

            case 10: // '\n'
                yybegin(0);
                return token(TokenType.COMMENT2, -4);

            case 5: // '\005'
                yybegin(0);
                return token(TokenType.TYPE, -1);

            case 15: // '\017'
                yybegin(4);
                return token(TokenType.COMMENT2, 3);

            case 11: // '\013'
                yybegin(0);
                return token(TokenType.COMMENT2, -3);

            case 2: // '\002'
                yybegin(0);
                return token(TokenType.TYPE);

            case 9: // '\t'
                return token(TokenType.KEYWORD2);

            case 13: // '\r'
                return token(TokenType.TYPE, -1);

            case 4: // '\004'
                return token(TokenType.IDENTIFIER);

            case 3: // '\003'
                yybegin(6);
                return token(TokenType.TYPE, 1);

            case 6: // '\006'
                return token(TokenType.STRING);

            case 14: // '\016'
                yypushback(3);
                return token(TokenType.COMMENT);

            case 7: // '\007'
                yybegin(0);
                return token(TokenType.TYPE2, -2);

            case 8: // '\b'
                yybegin(8);
                return token(TokenType.TYPE2, 2);

            default:
                if(zzInput == -1 && zzStartRead == zzCurrentPos)
                {
                    zzAtEOF = true;
                    switch(zzLexicalState)
                    {
                    case 8: // '\b'
                        return null;

                    case 0: // '\0'
                        return null;

                    case 2: // '\002'
                        return null;

                    case 4: // '\004'
                        return null;

                    case 6: // '\006'
                        return null;

                    default:
                        return null;

                    case 58: // ':'
                    case 59: // ';'
                    case 60: // '<'
                    case 61: // '='
                    case 62: // '>'
                        break;
                    }
                } else
                {
                    zzScanError(1);
                }
                break;

            case 1: // '\001'
            case 16: // '\020'
            case 17: // '\021'
            case 18: // '\022'
            case 19: // '\023'
            case 20: // '\024'
            case 21: // '\025'
            case 22: // '\026'
            case 23: // '\027'
            case 24: // '\030'
            case 25: // '\031'
            case 26: // '\032'
            case 27: // '\033'
            case 28: // '\034'
            case 29: // '\035'
            case 30: // '\036'
                break;
            }
        } while(true);
    }

    public static final int YYEOF = -1;
    private static final int ZZ_BUFFERSIZE = 16384;
    public static final int INSTR = 8;
    public static final int YYINITIAL = 0;
    public static final int COMMENT = 2;
    public static final int CDATA = 4;
    public static final int TAG = 6;
    private static final int ZZ_LEXSTATE[] = {
        0, 0, 1, 1, 2, 2, 3, 3, 4, 4
    };
    private static final String ZZ_CMAP_PACKED = "\t\000\001\001\001\002\002\000\001\002\022\000\001\001\001\004\001\024\001\030\002\000\001\026\001\025\005\000\001\005\001\t\001\022\n\n\001\007\001\027\001\003\001\023\001\006\001\013\001\000\001\017\001\007\001\r\001\016\001\007\016\007\001\020\006\007\001\f\001\000\001\021\001\000\001\007\001\000\032\b<\000\001\t\b\000\027\000\001\000\037\000\001\000\u0208\000p\000\016\000\001\000\u02E1\000\n\031\206\000\n\031\u026C\000\n\031v\000\n\031v\000\n\031v\000\n\031v\000\n\031w\000\t\031v\000\n\031v\000\n\031v\000\n\031\340\000\n\031v\000\n\031F\000\n\031\u0116\000\n\031\266\000\u0269\000\t\031\u046E\000\n\031&\000\n\031\u012C\000\n\031\u06B0\000\f\000\002\0003\000/\000\u0120\000\u0A70\000\u03F0\000\021\000\uA7FF\000\u0800\000\u1000\000\u0900\000\u04D0\000 \000\u0120\000\n\031\344\000\002\0";
    private static final char ZZ_CMAP[] = zzUnpackCMap("\t\000\001\001\001\002\002\000\001\002\022\000\001\001\001\004\001\024\001\030\002\000\001\026\001\025\005\000\001\005\001\t\001\022\n\n\001\007\001\027\001\003\001\023\001\006\001\013\001\000\001\017\001\007\001\r\001\016\001\007\016\007\001\020\006\007\001\f\001\000\001\021\001\000\001\007\001\000\032\b<\000\001\t\b\000\027\000\001\000\037\000\001\000\u0208\000p\000\016\000\001\000\u02E1\000\n\031\206\000\n\031\u026C\000\n\031v\000\n\031v\000\n\031v\000\n\031v\000\n\031w\000\t\031v\000\n\031v\000\n\031v\000\n\031\340\000\n\031v\000\n\031F\000\n\031\u0116\000\n\031\266\000\u0269\000\t\031\u046E\000\n\031&\000\n\031\u012C\000\n\031\u06B0\000\f\000\002\0003\000/\000\u0120\000\u0A70\000\u03F0\000\021\000\uA7FF\000\u0800\000\u1000\000\u0900\000\u04D0\000 \000\u0120\000\n\031\344\000\002\0");
    private static final int ZZ_ACTION[] = zzUnpackAction();
    private static final String ZZ_ACTION_PACKED_0 = "\005\000\007\001\001\002\005\001\001\000\001\003\013\000\001\004\001\005\001\000\001\006\001\000\001\007\002\000\001\b\001\000\001\t\002\000\001\n\001\000\001\013\001\f\002\000\001\r\001\016\004\000\001\017";
    private static final int ZZ_ROWMAP[] = zzUnpackRowMap();
    private static final String ZZ_ROWMAP_PACKED_0 = "\000\000\000\032\0004\000N\000h\000\202\000\234\000\266\000\320\000\352\000\u0104\000\u011E\000\202\000\u0138\000\u0152\000\u016C\000\u0186\000\u01A0\000\u01BA\000\u01D4\000\u01EE\000\u0208\000\u0222\000\u023C\000\320\000\u0256\000\u0270\000\u0104\000\u028A\000\u02A4\000\u0138\000\202\000\202\000\u016C\000\202\000\u0186\000\202\000\u02BE\000\u02D8\000\u02F2\000\u030C\000\202\000\u0326\000\u0340\000\202\000\u035A\000\202\000\202\000\u0374\000\u038E\000\202\000\202\000\u03A8\000\u03C2\000\u03DC\000\u03F6\000\202";
    private static final int ZZ_TRANS[] = zzUnpackTrans();
    private static final String ZZ_TRANS_PACKED_0 = "\003\006\001\007\022\006\001\b\003\006\005\t\001\n\024\t\021\013\001\f\b\013\006\006\001\r\002\016\004\006\004\016\001\006\001\017\001\006\001\020\001\021\013\006\002\016\002\006\001\022\001\006\004\016\003\006\001\020\001\021\004\006\036\000\001\023\002\000\002\024\002\000\001\025\001\000\004\024\001\000\001\026\017\000\001\027\017\000\001\030\001\000\005\031\001\032\031\031\001\033\024\031\021\034\001\035\031\034\001\036\b\034\005\000\001\037\001\000\004\037\002\000\004\037\002\000\001 \f\000\001!\023\000\002\"\001\000\021\"\001#\005\"\002$\001\000\022$\001#\004$\006\000\001%\030\000\001&\006\000\001'\022\000\001\024\001\000\004\024\002\000\004\024\020\000\002(\004\000\004(\020\000\002)\004\000\004)\021\000\001\027\016\000\001*\f\000\001+\016\000\001+\005\031\001,\031\031\001,\001-\023\031\021\034\001.\016\034\001/\n\034\001.\b\034\005\000\0010!\000\0011\021\000\001(\001\000\004(\002\000\004(\n\000\0022\002\000\001)\0013\004)\002\000\004)\023\000\001+\f\000\001*\001\000\001+\005\031\001,\0014\023\031\006\034\0014\n\034\001.\b\034\016\000\0015\f\000\0022\003\000\0013\"\000\0016\032\000\0017\030\000\0018\026\000\0019\r\0";
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;
    private static final String ZZ_ERROR_MSG[] = {
        "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"
    };
    private static final int ZZ_ATTRIBUTE[] = zzUnpackAttribute();
    private static final String ZZ_ATTRIBUTE_PACKED_0 = "\005\000\001\t\006\001\001\t\005\001\001\000\001\001\013\000\002\t\001\000\001\t\001\000\001\t\002\000\001\001\001\000\001\t\002\000\001\t\001\000\002\t\002\000\002\t\004\000\001\t";
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
    private static final byte TAG_OPEN = 1;
    private static final byte TAG_CLOSE = -1;
    private static final byte INSTR_OPEN = 2;
    private static final byte INSTR_CLOSE = -2;
    private static final byte CDATA_OPEN = 3;
    private static final byte CDATA_CLOSE = -3;
    private static final byte COMMENT_OPEN = 4;
    private static final byte COMMENT_CLOSE = -4;

}
