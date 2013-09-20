package com.gp.gpscript.script;

import java.util.*;

import org.apache.log4j.Logger;

import com.gp.gpscript.keymgr.util.encoders.Hex;


public class TLVList {
	private static Logger log = Logger.getLogger(TLVList.class);
  public byte encodingMode;
  public int  length;

  private byte[] tlvStream = null;

  private ArrayList tlvList = new ArrayList();

  public static int byteToInt(byte num)
  {
      byte[] b = new byte[1];
      b[0] = num;
      String strTag =  new String(Hex.encode(b));
      Integer intTag = Integer.valueOf(strTag,16);
      return intTag.intValue();
  }


  /**
   *  Parses tlvStream,breaking it down into individual TLVS
   *  @param  tlvStream  The combined TLV. This value may be empty in which case an empty list is created
   *  @param  encoding   Value representing the encoding method.
   *  @return Return error code
   *   0 OK
   *  -1 tlvStream is empty
   *  -2 Invalid tag for the encoding method
   *  -3 value to large for given encode method
   *  -4 cannot decode tlvStream using the specified encoding method
   *  -5 unsupported encoding method
   *  -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
   */
  public static int parseParameters(byte[] tlvStream,byte encoding) {

      TLV tlv = null;
      int pos = 0,tlvCount=0;
      ArrayList tlvList = new ArrayList();

      if(tlvStream == null || tlvStream.length == 0)
          return -1;

      if(encoding != TLV.DGI && encoding != TLV.EMV)
          return -5;

      while(pos < tlvStream.length) {
        if(encoding == TLV.EMV)
            pos = getOneTLV_EMV(tlvStream,pos,encoding,tlvList);
        else if(encoding == TLV.DGI)
            pos = getOneTLV_DGI(tlvStream,pos,encoding,tlvList);
        if(pos < 0) break;
        ++tlvCount;
     }

     tlvList = null;

     if(pos < 0)
        return pos;

     return 0;

  }


  /**
   *  Constructor Function. Parses tlvStream,breaking it down into individual TLVS
   *  @param  tlvStream  The combined TLV. This value may be empty in which case an empty list is created
   *  @param   encoding   Value representing the encoding method.
   */
  public  TLVList(byte[] tlvBuffer,byte encoding)
  {

      TLV tlv = null;
      int pos = 0,tlvCount=0;

      tlvStream = tlvBuffer;
      encodingMode = encoding;

      if(tlvBuffer != null)
      {

          while(pos < tlvStream.length)
          {
              if(encoding == TLV.EMV)
                  pos = getOneTLV_EMV(tlvStream,pos,encoding,tlvList);
              else if(encoding == TLV.DGI)
                  pos = getOneTLV_DGI(tlvStream,pos,encoding,tlvList);
              if(pos < 0) break;
              ++tlvCount;
          }

          if(pos != tlvStream.length)
          {
              tlvList.subList(0,tlvCount).clear();
              tlvCount = 0;
          }
          else
          {
              length = tlvCount;
          }
      }
  }
/*  public  TLVList(byte[] tlvBuffer,byte encoding) {

      TLV tlv = null;
      int pos = 0,tlvCount=0;

      tlvStream = tlvBuffer;
      encodingMode = encoding;

      while(pos < tlvStream.length) {
        if(encoding == TLV.EMV)
            pos = getOneTLV_EMV(tlvStream,pos,encoding,tlvList);
        else if(encoding == TLV.DGI)
            pos = getOneTLV_DGI(tlvStream,pos,encoding,tlvList);
        if(pos < 0) break;
        ++tlvCount;
     }

     if(pos != tlvStream.length)
     {
        tlvList.subList(0,tlvCount).clear();
        tlvCount = 0;
     }
     else
        length = tlvCount;

  }
*/

  /**
   *  Parses tlvStream,Get one TLV from tlvStream
   *  @param  tlvStream  The combined TLV. This value may be empty in which case an empty list is created
   *  @param  pos        Current position on tlvStream
   *  @param  encoding   Value representing the encoding method.
   *  @param  tlvList    Holding individual TLV
   *  @return Return error code
   *   0 OK
   *  -1 tlvStream is empty
   *  -2 Invalid tag for the encoding method
   *  -3 value to large for given encode method
   *  -4 cannot decode tlvStream using the specified encoding method
   *  -5 unsupported encoding method
   *  -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
   */
  public static int getOneTLV_EMV(byte[] tlvStream,int pos,byte encoding,ArrayList tlvList) {
    int tag=0,j=0;
    byte[] arrayTag = null;
    byte[] len = null;
    byte[] value = null;
    int valueLen = 0;

      // At least one byte Tag and one byte Len
      if(pos+1 > tlvStream.length)
          return  -1;

       //TAG
       if((tlvStream[pos] & 0x1f) == 0x1f)
       {
          if(pos+1 < tlvStream.length)
          {
             if(tlvStream[pos+1] > 0x80)  return -2;
             arrayTag = new byte[2];
             arrayTag[0] = tlvStream[pos];
             arrayTag[1] =  tlvStream[++pos];
          }
       }
       else
       {
             arrayTag = new byte[1];
             arrayTag[0] = tlvStream[pos];
       }

       String strTag =  new String(Hex.encode(arrayTag));
       Integer intTag = Integer.valueOf(strTag,16);
       tag = intTag.intValue();

       //LEN
       if(++pos >= tlvStream.length)  return -4;

       if((tlvStream[pos] & 0x80) == 0x80)
       {
         int lenByteCount = tlvStream[pos] & 0x7f;
         if(lenByteCount==0 || lenByteCount>3)  return -3;

         len = new byte[lenByteCount+1];
         len[0] = tlvStream[pos];

         if(pos + lenByteCount +1 >= tlvStream.length)  return -4;
         for(int i=0;i<lenByteCount;i++)
         {
           len[i+1] = tlvStream[++pos];
           valueLen = valueLen*256 + byteToInt(len[i+1]);
         }
       }
       else
       {
          len = new byte[1];
          len[0] = tlvStream[pos];
          valueLen = byteToInt(tlvStream[pos]);
       }


       //VALUE
       if(pos + valueLen >= tlvStream.length)  return -4;

       value = new byte[valueLen];
       for(int i=0; i<valueLen;i++)
          value[i] = tlvStream[++pos];
       pos++;


       if(find(tlvList,tag) ==0)
       {
           tlvList.add(new TLV(tag,value,encoding));
       }
       else
           return -6;

       return pos;
 }

 /**
  *  Parses DGI encoding tlvStream,Get one TLV from tlvStream
  *  @param  tlvStream  The combined TLV. This value may be empty in which case an empty list is created
  *  @param  pos        Current position on tlvStream
  *  @param  encoding   Value representing the encoding method.
  *  @param  tlvList    Holding individual TLV
  *  @return Return error code
  *     0 OK
  *    -1 tlvStream is empty
  *    -2 Invalid tag for the encoding method
  *    -3 value to large for given encode method
  *    -4 cannot decode tlvStream using the specified encoding method
  *    -5 unsupported encoding method
  *    -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
  */
 public static int getOneTLV_DGI(byte[] tlvStream,int pos,byte encoding,ArrayList tlvList) {
   int tag=0,j=0;
   byte[] arrayTag = null;
   byte[] len = null;
   byte[] value = null;
   int valueLen = 0;

     //Tag: two bytes,Len: one byte
     if(pos +3 > tlvStream.length)
         return  -4;

      //TAG
      arrayTag = new byte[2];
      arrayTag[0] = tlvStream[pos];
      arrayTag[1] =  tlvStream[++pos];

      String strTag =  new String(Hex.encode(arrayTag));
      Integer intTag = Integer.valueOf(strTag,16);
      tag = intTag.intValue();

      //LEN
      len = new byte[1];
      len[0] = tlvStream[++pos];
      valueLen = tlvStream[pos];

      //VALUE
      if(pos + valueLen >= tlvStream.length)  return -4;

      value = new byte[valueLen];
      for(int i=0; i<valueLen;i++)
         value[i] = tlvStream[++pos];
      pos++;

      if(find(tlvList,tag) ==0)
      {
          tlvList.add(new TLV(tag,value,encoding));
      }
      else
          return -6;

      return pos;
}


/**
 *  Search the specified list for a specified tag,and returns a TLV object
 *  @param  tlvList    the specified TLV list for searching
 *  @param tag        Value represents the Tag
 *  @return Return error code
 *       0 found
 *       -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
 */
 public static int find(ArrayList tlvList,int tag) {

     Iterator iter = tlvList.iterator();
     TLV tlv;
     while(iter.hasNext()) {
       tlv =(TLV)iter.next();
       if(tlv.getTag() ==  tag)
         return -6;
     }
     return 0;
 }


 /**
  *  Search the specified list for a specified tag,and returns a TLV object
  *  @param tag    Value represents the Tag
  *  @return  A TLV object which contains the data stored for that Tag
  */
  public TLV find(int tag) {

      Iterator iter = tlvList.iterator();
      TLV tlv;
      while(iter.hasNext()) {
         tlv =(TLV)iter.next();
         if(tlv.getTag() ==  tag)
           return tlv;
      }

      return null;
  }


  /**
   *  Search the specified list for a specified tag,and returns a zero based index for that TLV
   *  *  @param  tag        Value represents the Tag
   *  @return  Index that indicates the location of the TLV within the list
   */
  public int findIndex(int tag) {

      Iterator iter = tlvList.iterator();
      TLV tlv;
      while(iter.hasNext()) {
         tlv =(TLV)iter.next();
         if(tlv.getTag() ==  tag)
         {
            return tlvList.indexOf(tlv);
         }
      }
      return -1;
  }

  /**
   *  Search the specified list for a specified index,and returns a TLV object
   *  @param  Index   Zero based index into the list of stored TLV items
   *  @return A TLV object which contains the data stored at that index.
   */
  public TLV index(int index) {
      TLV tlv = (TLV) tlvList.get(index);
      return tlv;
  }


  /**
   *  Delete the the specified TLV from the list by tag value
   *  @param  Index  Zero based index number that represents the tag that will be deleted from list
   *  @return none
   */
  public void deleteByIndex(int index) {
      tlvList.remove(index);
  }


  /**
   *  Delete the the specified TLV from the list by tag value
   *  @param  tag  Value represents the tag that will be deleted from list
   *  @return none
   */
  public void deleteByTag(int tag) {
    Iterator iter = tlvList.iterator();
    TLV tlv;
    while(iter.hasNext())
    {
       tlv =(TLV)iter.next();
       if(tlv.getTag() ==  tag)
       {
           tlvList.remove(tlvList.indexOf(tlv));
           if(tlvList.size()==0)
               break;
           iter=tlvList.iterator();
       }
    }
  }



 /**
  *  Appends the specified TLV data to the end of the existing list of TLVs
  *@param  tag     Tag to be used to be added to the list.
  *@param  value   Value to be used to be added to the list
  *@return return code
  *     the same as function parseParameters's return code
  */
 public int append(int tag ,byte[] value)
 {
     TLV myTLV;
     int errorCode = TLV.parseParameters(tag,value,encodingMode);
     if( errorCode!=0)
       switch(errorCode)
       {
        case -1:
             return -3;
        case -2:
             return -1;
        case -3:
             return -2;
        case -4:
             return -5;
       }

     myTLV = new TLV(tag,value,encodingMode);

     if(find(tag) == null)
     {
         tlvList.add(myTLV);
         length++;
     }
     else
     {
         return -6;
     }
     return 0;
 }

 /**
  *  Appends the specified TLV data to the end of the existing list of TLVs
  *@param   tlvStream the combined TLV data in single stream of bytes
  *@return  return code
  *     the same as function parseParameters's return code
  */
 public int append(byte[] tlvStream)
 {

   int pos = 0,tlvCount = 0;

   if( (parseParameters(tlvStream,encodingMode)) ==0)
   {
       while(pos < tlvStream.length) {
          if(encodingMode == TLV.EMV)
               pos = getOneTLV_EMV(tlvStream,pos,encodingMode,tlvList);
          else if(encodingMode == TLV.DGI)
               pos = getOneTLV_DGI(tlvStream,pos,encodingMode,tlvList);
          if(pos < 0) break;
          ++tlvCount;
       }

       if(pos != tlvStream.length)
       {
          if(tlvCount > 0)
             tlvList.subList(length,length + tlvCount).clear();
          return pos;
       }
       else
          length += tlvCount;
   }

    return 0;
 }

 /**
  * Append the data to the existing data for the specified tag
  * @param tag  Tag to be used to be added to the list
  * @param data Data to be added to the existing tag
  * @return
  *     -1 tag is not existing
  *     -2 resultant data value to large for given encoding method
  */

 public int appendValue(int tag,byte[] data)
 {
    TLV newTLV = null,tlv = find(tag);
    byte[] value = null;
    int index =0;

    if(tlv == null)
       return -1;

    if(encodingMode == TLV.EMV)
    {
       if( tlv.getValue().length + data.length > 65535)
          return -2;
    }else
    {
       if( tlv.getValue().length + data.length > 252)
         return -2;
    }

    value = new byte[tlv.getValue().length + data.length];
    System.arraycopy(tlv.getValue(),0,value,0, tlv.getValue().length);
    System.arraycopy(data,0,value,tlv.getValue().length,data.length);

    newTLV = new TLV(tag,value,encodingMode);

    index = findIndex(tag);

    tlv = (TLV)tlvList.set(index,newTLV);
    tlv = null;

    return 0;
 }

 /**
  * Append the data to the existing data for the specified zero-based index into the TLVStream
  *
  * @param index  Zero-based index into the TLVStream
  * @param data Data to be added to the existing tag
  * @return
  *     -1 tag is not existing
  *     -2 resultant data value to large for given encoding method
  */
 public int appendValueIndex(int index,byte[] data)
 {
    TLV newTLV = null,tlv = index(index);
    byte[] value = null;

    if(tlv == null)
       return -1;

    if(encodingMode == TLV.EMV)
    {
       if( tlv.getValue().length + data.length > 65535)
          return -2;
    }else
    {
       if( tlv.getValue().length + data.length > 252)
         return -2;
    }

    value = new byte[tlv.getValue().length + data.length];
    System.arraycopy(tlv.getValue(),0,value,0, tlv.getValue().length);
    System.arraycopy(data,0,value,tlv.getValue().length,data.length);

    newTLV = new TLV(tlv.getTag(),value,encodingMode);

    tlv = (TLV)tlvList.set(index,newTLV);
    tlv = null;

    return 0;
 }

  /**
   * Returns the content of the entire list,including T,L and V values in a
   * TLV format as a ByteString object
   *
   * @return  the content of the entire list
   */
  public byte[] toByteString()
  {
      Iterator iter = tlvList.iterator();
      Iterator iter1 = tlvList.iterator();
      TLV tlv;
      int totalLen = 0;

      while(iter.hasNext()) {
         tlv =(TLV)iter.next();
         totalLen += tlv.getTLV().length;
      }

      if(totalLen == 0)
         return null;

      byte[] buffer =  new byte[totalLen];
      int offset = 0;

      while(iter1.hasNext()) {
         tlv =(TLV)iter1.next();
         System.arraycopy(tlv.getTLV(),0,buffer,offset,tlv.getTLV().length);
         offset += tlv.getTLV().length;
      }

      return buffer;
  }


  public static void main( String[] args){

      String  testVec = "313133313133313133313133";
      String  testVec1 = "81828384858687";
      byte[]  value1 = Hex.decode(testVec);
      byte[]  value2 = Hex.decode(testVec1);

      byte[]  value = new byte[200];

      for(int i=0;i<200;i++)
           value[i] =(byte)i;

      TLV MyTLV = new TLV(0x03,value1,TLV.EMV);
      TLV MyTLV2 = new TLV(0x9f03,value,TLV.EMV);

      byte tlvStream[] = new byte[MyTLV.getTLV().length + MyTLV2.getTLV().length];
      System.arraycopy(MyTLV.getTLV(),0,tlvStream,0,MyTLV.getTLV().length);
      System.arraycopy(MyTLV2.getTLV(),0,tlvStream,MyTLV.getTLV().length,MyTLV2.getTLV().length);


      log.debug("parseParameters");
      log.debug(TLVList.parseParameters(tlvStream,TLV.EMV));


      TLVList myList = new TLVList(tlvStream,TLV.EMV);

      myList.appendValue(0x03,value2);

      log.debug("toByteString()");
      log.debug( new String(Hex.encode(myList.toByteString())));

/*
      MyTLV = new TLV(0x05,value,TLV.EMV);
      MyTLV2 = new TLV(0x9f04,value1,TLV.EMV);

      byte tlvStream1[] = new byte[MyTLV.getTLV().length + MyTLV2.getTLV().length];
      System.arraycopy(MyTLV.getTLV(),0,tlvStream1,0,MyTLV.getTLV().length);
      System.arraycopy(MyTLV2.getTLV(),0,tlvStream1,MyTLV.getTLV().length,MyTLV2.getTLV().length);

      myList.append(tlvStream1);
      System.out.println("after append the length is");
      System.out.println(myList.length);
*/

      MyTLV = myList.find(0x3);

      if(MyTLV != null)
      {
    	  log.debug(new String(Hex.encode(MyTLV.getL())));
    	  log.debug(new String(Hex.encode(MyTLV.getLV())));
    	  log.debug(MyTLV.getTag() );
    	  log.debug(new String(Hex.encode(MyTLV.getTLV())));
    	  log.debug(new String(Hex.encode(MyTLV.getTV())));
    	  log.debug(new String(Hex.encode(MyTLV.getValue())));
      }
      else
    	  log.error("not found tag");

/*
      myList.append(0x9f44,value);
      System.out.println("after append 0x9f44 the length is");
      System.out.println(myList.length);

      MyTLV = myList.find(0x9f44);

      if(MyTLV != null)
      {
        System.out.println(new String(Hex.encode(MyTLV.getL())));
        System.out.println(new String(Hex.encode(MyTLV.getLV())));
        System.out.println(MyTLV.getTag() );
        System.out.println(new String(Hex.encode(MyTLV.getTLV())));
        System.out.println(new String(Hex.encode(MyTLV.getTV())));
        System.out.println(new String(Hex.encode(MyTLV.getValue())));
      }
      else
        System.out.println("not found tag");

      MyTLV = myList.find(0x9f05);
      if(MyTLV != null)
      {
         System.out.println(new String(Hex.encode(MyTLV.getL())));
         System.out.println(new String(Hex.encode(MyTLV.getLV())));
         System.out.println(MyTLV.getTag() );
         System.out.println(new String(Hex.encode(MyTLV.getTLV())));
         System.out.println(new String(Hex.encode(MyTLV.getTV())));
         System.out.println(new String(Hex.encode(MyTLV.getValue())));
      }
      else
        System.out.println("not found tag");
*/
/*
      MyTLV = myList.index(0);
      System.out.println(new String(Hex.encode(MyTLV.getL())));
      System.out.println(new String(Hex.encode(MyTLV.getLV())));
      System.out.println(MyTLV.getTag() );
      System.out.println(new String(Hex.encode(MyTLV.getTLV())));
      System.out.println(new String(Hex.encode(MyTLV.getTV())));
      System.out.println(new String(Hex.encode(MyTLV.getValue())));

      System.out.println(myList.findIndex(0x03));
      System.out.println(myList.findIndex(0x9f05));
      System.out.println(myList.findIndex(0x9f01));

      myList.deleteByTag(0x9f05);

      System.out.println(myList.findIndex(0x03));
      System.out.println(myList.findIndex(0x9f05));
      System.out.println(myList.findIndex(0x9f01));
*/
  }


}