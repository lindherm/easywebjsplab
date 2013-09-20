/**
 * <p>Title: Function Class</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: WatchData</p>
 * @author Huang Peng
 * @version 1.0
 */

package com.gp.gpscript.device;

import java.io.*;
import java.util.Date;

import org.apache.log4j.Logger;

//import com.watchdata.wdcams.loader.Loader;

public  class Function
{private static Logger log = Logger.getLogger(Function.class);
  protected final static String[] hexChars = {"0", "1", "2", "3", "4", "5", "6", "7",
                                              "8", "9", "A", "B", "C", "D", "E", "F"};
  /**
   *to sleep for the specified number of milliseconds.
   *
   *@param: num the length of time to sleep in milliseconds.
   *
   */
  public static void Sleep(long num)
  {
    Date today1=new Date();
    long time1,time2;
    time1=today1.getTime();

    do
    {
      Date today2=new Date();
      time2=today2.getTime();
    }while(num>=(time2-time1));
  }

  /** Hexify a byte array.
    *
    * @param data  Byte array to convert to HexString
    *
    * @return HexString
    */
   public static String hexify(byte[] data)
   {
   	if(data == null) return "null";

    StringBuffer out = new StringBuffer(256);
    int n = 0;

    for(int i=0; i<data.length; i++)
    {
    	if(n>0) out.append(' ');
    	out.append(hexChars[(data[i]>>4) & 0x0f]);
    	out.append(hexChars[data[i] & 0x0f]);
    }
    return out.toString();
  }

    /** Hexify a byte array.
    *
    * @param data  Byte array to convert to HexString
    *
    * @return HexString
    */
   public static String hexify1(byte[] data)
   {
   	if(data == null) return "null";

    StringBuffer out = new StringBuffer(1000);
    int n = 0;

    for(int i=0; i<data.length; i++)
    {
    	if(n==0) out.append(' ');
    	out.append(hexChars[(data[i]>>4) & 0x0f]);
    	out.append(hexChars[data[i] & 0x0f]);
    }
    return out.toString();
  }

  /** Hexify a byte.
    *
    * @param data  Byte to convert to HexString
    *
    * @return HexString
    */
  public static String hexify(byte data)
  {
    StringBuffer out = new StringBuffer(256);
    out.append(hexChars[(data>>4) & 0x0f]);
    out.append(hexChars[data & 0x0f]);

    return out.toString();
  }

  /** Parse bytes encoded as Hexadecimals into a byte array.<p>
  *
  * @param byteString
  *        String containing HexBytes.
  *
  * @return byte array containing the parsed values of the given string.
  */
  public static byte[] parseHexString(String byteString)
  {
      byte[] result = new byte[byteString.length()/2];
      for (int i=0; i<byteString.length(); i+=2) {
          String toParse = byteString.substring(i, i+2);
          result[i/2] = (byte)Integer.parseInt(toParse, 16);
      }
      return result;
  }
  /** encode hex string to ASCII.<p>
  *
  * @param byteString
  *        String containing HexBytes.
  *
  * @return byte array containing the parsed values of the given string.
  */
  public static byte[] HexStringToASCII(String byteString)
  {
      byte[] result = new byte[byteString.length()];
      for (int i=0; i<byteString.length(); i++) {
          char temp = byteString.charAt(i);
          result[i] = (byte)temp;
      }
      return result;
  }
  public static String hexToascii(String str)
  {
      byte[] buffer=HexStringToASCII(str);
      return hexify1(buffer);
  }

  public static byte[] decode(
          String  string)
  {
      byte[]          bytes = new byte[string.length() / 2];
      String          buf = string.toLowerCase();

      for (int i = 0; i < buf.length(); i += 2)
      {
          char    left  = buf.charAt(i);
          char    right = buf.charAt(i+1);
          int     index = i / 2;

          if (left < 'a')
          {
              bytes[index] = (byte)((left - '0') << 4);
          }
          else
          {
              bytes[index] = (byte)((left - 'a' + 10) << 4);
          }
          if (right < 'a')
          {
              bytes[index] += (byte)(right - '0');
          }
          else
          {
              bytes[index] += (byte)(right - 'a' + 10);
          }
      }

      return bytes;
  }

  public static void main(String[] args)
  {
      byte[] a=HexStringToASCII("1234");
      for(int n=0;n<a.length;n++)
      {
          System.out.print(a[n]+" ");
      }
      //System.out.println();
      String buf=hexToascii("1234");
      log.debug(buf);

      String b1="1234";
      byte[] b2=decode(b1);
      for(int n=0;n<b2.length;n++)
      {
          System.out.print(b2[n]+" ");
      }
      String s="1234567890";
      String pan="";
      int len=s.length()/4;
      len=len*4;
      for(int m=0;m<len;m=m+4)
      {
          pan=pan+s.substring(m,m+4)+" ";
      }
      pan=pan+s.substring(len);
      log.debug(pan);
  }
}