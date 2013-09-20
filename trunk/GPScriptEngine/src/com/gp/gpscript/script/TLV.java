package com.gp.gpscript.script;

import java.lang.Integer;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.util.encoders.Hex;

public class TLV {
	private static Logger log = Logger.getLogger(TLV.class);
  public static final byte EMV = 1;
  public static final byte DGI = 2;
  public static final byte L16 = 3;

  public byte encodingMode;
  public int size =0;


  private int     intTag;
  private byte[]  byteArrayTag;
  private byte[]  len;
  private byte[]  value;

  private byte[]  TV;
  private byte[]  LV;
  private byte[]  TLV;

  /**
   *Calling this fun. first before calling TLV contructor
   *@param   tag    The Tag that will be assigned to the TLV.
   *@param   value  The Value data that will be assigned to the TLV.
   *@param   encoding Value representint the encodeing method
   *@return  the created object with contains TLV data
   */
  public static int parseParameters(int tag, byte[] value, byte encoding) {

      byte[] byteArrayTag = null;

      if ( encoding != EMV && encoding != DGI &&encoding != L16)
        return -4;

     //check the value parameter
//     if(value == null ||  value.length ==0 )
     if(value == null )
     {
         return -2;
     }
     else
     {
         if(encoding == DGI)
         {
           //the length of data in a data grouping must not exceed 242 bytes
           // Common Personalization Specification
           if(value.length > 242) return -1;
         }
         else
         {
           if( value.length > 65535) return -1;
         }
     }

     //check the tag parameter
     if(encoding == DGI)
     {
       if( tag > 65535)  return -3;
     }
     else if(encoding == EMV)
     {
        //Rules for BER_TLV Data Objects  ( EMV Spec 2000 Book 2 Annex B)
        if((Integer.toHexString(tag).length())%2 != 0)
            byteArrayTag = Hex.decode("0" + Integer.toHexString(tag) );
        else
            byteArrayTag = Hex.decode( Integer.toHexString(tag) );

        if(byteArrayTag.length ==0 || byteArrayTag.length >2)
            return -3;

        if( ((byteArrayTag[0] & 0x1f) != 0x1f) )
        {
           if ( byteArrayTag.length ==2)
              return -3;
        }
        else
        {
           if( byteArrayTag.length !=2 || TLVList.byteToInt(byteArrayTag[1]) > 0x80)
             return -3;
        }
     }
     else
       return 0;

       return 0;
  }

  /**
   *Class TLV Contructor function
   *@param   tag    The Tag that will be assigned to the TLV.
   *@param   value  The Value data that will be assigned to the TLV.
   *@param   encoding Value representint the encodeing method
   *@return  the created object with contains TLV data
   */
  public TLV(int tag, byte[] value,byte encoding) {

   this.encodingMode = encoding;
   this.value = value;
   this.intTag = tag;

   if(encoding == EMV)
   {
      //TAG
      if((Integer.toHexString(tag).length())%2 != 0)
          this.byteArrayTag = Hex.decode("0" + Integer.toHexString(tag) );
      else
          this.byteArrayTag = Hex.decode( Integer.toHexString(tag) );

      //LEN
      if(value.length <128)
      {
           if( (Integer.toHexString(value.length)).length()%2 != 0)
              len = Hex.decode("0" + Integer.toHexString(value.length) );
           else
              len = Hex.decode(Integer.toHexString(value.length) );
      }
      else if (value.length <256)
      {
           len = new byte[2];
           len[0] = (byte)0x81;
           len[1] = (byte)value.length;
      }
      else
      {
           len = new byte[3];
           len[0] = (byte)0x82;
           len[1] = (byte)(value.length/256);
           len[2] = (byte)(value.length%256);
      }
  }
  else if(encoding == DGI)
  {
     //TAG must be two bytes
     switch (Integer.toHexString(tag).length())
     {
       case 3:
          this.byteArrayTag = Hex.decode("0" + Integer.toHexString(tag) );
          break;
       case 2:
          this.byteArrayTag = Hex.decode("00" + Integer.toHexString(tag) );
          break;
       case 1:
          this.byteArrayTag = Hex.decode("000" + Integer.toHexString(tag) );
          break;
       default: //>=4
          String strTag=Integer.toHexString(tag);
          this.byteArrayTag = Hex.decode(strTag.substring(strTag.length()-4));
          break;
     }

     //LEN must be one byte
     if(value.length <256)
     {
        if( (Integer.toHexString(value.length)).length()%2 != 0)
            len = Hex.decode("0" + Integer.toHexString(value.length) );
        else
            len = Hex.decode(Integer.toHexString(value.length) );
     }
   }
   else //L16
   {
     switch (Integer.toHexString(tag).length())
     {
       case 3:
          this.byteArrayTag = Hex.decode("0" + Integer.toHexString(tag) );
          break;
       case 2:
          this.byteArrayTag = Hex.decode("00" + Integer.toHexString(tag) );
          break;
       case 1:
          this.byteArrayTag = Hex.decode("000" + Integer.toHexString(tag) );
          break;
       default: //>=4
          String strTag=Integer.toHexString(tag);
          this.byteArrayTag = Hex.decode(strTag.substring(strTag.length()-4));
          break;
     }
     len = new byte[2];
     len[0] = (byte)(value.length/256);
     len[1] = (byte)(value.length%256);

   }

   LV = new byte[len.length + value.length];
   System.arraycopy(len,0,LV,0,len.length);
   System.arraycopy(value,0,LV,len.length,value.length);

    TV = new byte[byteArrayTag.length + value.length];
    System.arraycopy(byteArrayTag,0,TV,0,byteArrayTag.length);
    System.arraycopy(value,0,TV,byteArrayTag.length,value.length);

    size = byteArrayTag.length +len.length + value.length;

    TLV = new byte[byteArrayTag.length +len.length + value.length];
    System.arraycopy(byteArrayTag,0,TLV,0,byteArrayTag.length);
    System.arraycopy(len,0,TLV,byteArrayTag.length,len.length);
    System.arraycopy(value,0,TLV,byteArrayTag.length + len.length,value.length);

  }

  /**
   *Retrieve the L value of the TLV object
   *@param   none
   *@return  the L of the TLV,suitably encoded
   */
   public byte[] getL(){
    return len;
   }

   /**
    *Retrieve the LV value of the TLV object
    *@param   none
    *@return  the LV of the TLV,suitably encoded
    */
   public byte[] getLV(){
      return LV;
   }

   /**
    *Return a number containing Tag associated with the object.
    *@param   none
    *@return  the Tag element of the TLV.
    */
   public int getTag() {
      return intTag;
   }

   /**
    *Retrieve the TLV value of the TLV object
    *@param   none
    *@return  the TLV of the TLV,suitably encoded
    */
   public byte[] getTLV() {
      return TLV;
   }

   /**
    *Retrieve the TV value of the TLV object
    *@param   none
    *@return  the TV of the TLV,suitably encoded
    */
   public byte[] getTV() {
        return TV;
   }

   /**
    *Retrieve the Value of the TLV object
    *@param   none
    *@return  the Value of the TLV,suitably encoded
    */
   public byte[] getValue() {
       return value;
   }

   public static void main( String[] args){

       String  testVec = "313133313133313133313133";
       byte[]  value = Hex.decode(testVec);

       byte[]  value2B = new byte[200];

       for(int i=0;i<200;i++)
            value2B[i] =(byte)i;


       log.debug("parse Parameters,should be -4");
       log.debug(parseParameters(0x0314,value,EMV));

       log.debug("parse Parameters,should be OK");
       log.debug(parseParameters(0x0314,value,DGI));

       log.debug("parse Parameters,should be OK");
       log.debug(parseParameters(0x9f14,value,EMV));

       log.debug("parse Parameters,should be -4");
       log.debug(parseParameters(0x59f14,value,EMV));

       log.debug("parse Parameters,should be -2");
       log.debug(parseParameters(0x9f14,null,EMV));




       TLV MyTLV = new TLV(0x03,value,EMV);
       log.debug("0x03  L LV Tag TLV TV Value");
       log.debug(new String(Hex.encode(MyTLV.getL())));
       log.debug(new String(Hex.encode(MyTLV.getLV())));
       log.debug(MyTLV.getTag() );
       log.debug(new String(Hex.encode(MyTLV.getTLV())));
       log.debug(new String(Hex.encode(MyTLV.getTV())));
       log.debug(new String(Hex.encode(MyTLV.getValue())));

       MyTLV = new TLV(0x9f03,value,EMV);
       log.debug("0x9f03 L LV Tag TLV TV Value");
       log.debug(new String(Hex.encode(MyTLV.getL())));
       log.debug(new String(Hex.encode(MyTLV.getLV())));
       log.debug(MyTLV.getTag() );
       log.debug(new String(Hex.encode(MyTLV.getTLV())));
       log.debug(new String(Hex.encode(MyTLV.getTV())));
       log.debug(new String(Hex.encode(MyTLV.getValue())));

       MyTLV = new TLV(0x03,value2B,EMV);
       log.debug("0x03 L LV Tag TLV TV Value");
       log.debug(new String(Hex.encode(MyTLV.getL())));
       log.debug(new String(Hex.encode(MyTLV.getLV())));
       log.debug(MyTLV.getTag() );
       log.debug(new String(Hex.encode(MyTLV.getTLV())));
       log.debug(new String(Hex.encode(MyTLV.getTV())));
       log.debug(new String(Hex.encode(MyTLV.getValue())));

       MyTLV = new TLV(0x9f03,value2B,EMV);
       log.debug("0x9f03 L LV Tag TLV TV Value");
       log.debug(new String(Hex.encode(MyTLV.getL())));
       log.debug(new String(Hex.encode(MyTLV.getLV())));
       log.debug(MyTLV.getTag() );
       log.debug(new String(Hex.encode(MyTLV.getTLV())));
       log.debug(new String(Hex.encode(MyTLV.getTV())));
       log.debug(new String(Hex.encode(MyTLV.getValue())));

/*
       System.out.println("0x03");
       MyTLV = new TLV(0x03,value,DGI);
       System.out.println(new String(Hex.encode(MyTLV.getL())));
       System.out.println(new String(Hex.encode(MyTLV.getLV())));
       System.out.println(MyTLV.getTag() );
       System.out.println(new String(Hex.encode(MyTLV.getTLV())));
       System.out.println(new String(Hex.encode(MyTLV.getTV())));
       System.out.println(new String(Hex.encode(MyTLV.getValue())));

       System.out.println("0x0043");
       MyTLV = new TLV(0x0043,value,DGI);
       System.out.println(new String(Hex.encode(MyTLV.getL())));
       System.out.println(new String(Hex.encode(MyTLV.getLV())));
       System.out.println(MyTLV.getTag() );
       System.out.println(new String(Hex.encode(MyTLV.getTLV())));
       System.out.println(new String(Hex.encode(MyTLV.getTV())));
       System.out.println(new String(Hex.encode(MyTLV.getValue())));

       System.out.println("0x0143");
       MyTLV = new TLV(0x0143,value,DGI);
       System.out.println(new String(Hex.encode(MyTLV.getL())));
       System.out.println(new String(Hex.encode(MyTLV.getLV())));
       System.out.println(MyTLV.getTag() );
       System.out.println(new String(Hex.encode(MyTLV.getTLV())));
       System.out.println(new String(Hex.encode(MyTLV.getTV())));
       System.out.println(new String(Hex.encode(MyTLV.getValue())));

       System.out.println("0x1143");
       MyTLV = new TLV(0x1443,value,DGI);
       System.out.println(new String(Hex.encode(MyTLV.getL())));
       System.out.println(new String(Hex.encode(MyTLV.getLV())));
       System.out.println(MyTLV.getTag() );
       System.out.println(new String(Hex.encode(MyTLV.getTLV())));
       System.out.println(new String(Hex.encode(MyTLV.getTV())));
       System.out.println(new String(Hex.encode(MyTLV.getValue())));
*/
   }

}