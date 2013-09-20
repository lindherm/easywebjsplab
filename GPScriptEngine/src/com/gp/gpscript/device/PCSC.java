
/**
 * <p>Title: PCSC Class</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: WatchData</p>
 * @author Huang Peng
 * @version 1.0
 */

package com.gp.gpscript.device;

import com.ibm.opencard.terminal.pcsc10.*;
import com.ibm.opencard.terminal.pcsc.*;
//import com.watchdata.wdcams.loader.Loader;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

import org.apache.log4j.Logger;

import opencard.core.util.HexString;


public class PCSC implements Pcsc10Constants
{private static Logger log = Logger.getLogger(PCSC.class);
    private static OCFPCSC1 term=null;
    private static String[] readers=new String[20];
    private static int phCard=0;
    private static int phContext=0;
    private static byte[] ATRData;
    private static byte[] resp;
    private static byte[] command;
    private static byte[] errors={(byte)0xFF,(byte)0xFF};

    /**
     *Provides a list of readers already introduced to the subsystem
     *
     *@return: a list of readers
     *
     */
    public static String[] PCSC_ListReaders()
    {
        try
        {
            OCFPCSC1.loadLib();
            term=new OCFPCSC1();
            /*
            phContext=term.SCardEstablishContext(SCARD_SCOPE_USER);
            if(phContext==0)
                return null;
            //*/
            readers=term.SCardListReaders(null);
            return readers;
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return null;
        }
        catch(UnsatisfiedLinkError e1)
        {
//            e1.printStackTrace();
            log.error("Error: "+e1.toString());
            return null;
        }

    }

    /**
     *Open the Smart Card reader already introduced to the subsystem
     *
     *@param reader the string of the Smart Card reader from PCSC_ListReaders
     *
     *@return: 0 failed
     *@return: others successful
     *
     */
    public static int PCSC_open(String reader)
    {
        try
        {
            OCFPCSC1.loadLib();
            term=new OCFPCSC1();
            ///*
            phContext=term.SCardEstablishContext(SCARD_SCOPE_USER);
            if(phContext==0)
                return 0;
            //*/
            Integer returnedProtocol = new Integer(0);
            phCard=term.SCardConnect(phContext,
                                     reader,
                                     SCARD_SHARE_EXCLUSIVE,
                                     SCARD_PROTOCOL_T0|SCARD_PROTOCOL_T1,
                                     returnedProtocol);
/*
      Date oDate=new Date();
      int nHour,nMin,nSec;
      nHour = oDate.getHours();
      nMin = oDate.getMinutes();
      nSec = oDate.getSeconds();
      NumberFormat numFormat=NumberFormat.getNumberInstance();
      numFormat.setMaximumIntegerDigits(2);
      numFormat.setMinimumIntegerDigits(2);
      String strTime=numFormat.format(nHour)+":"+numFormat.format(nMin)+":"+numFormat.format(nSec);
      ReportGen.GenLog("****************Begin new process****************");
      ReportGen.GenLog("Current Time:   "+strTime);
      ReportGen.GenLog("*************************************************");
//*/
            if(phCard==0)
            {
                ReportGen.GenLog("Open PCSC error!");
                return 0;
            }
            else
                return phCard;
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            ReportGen.GenLog("Open PCSC error!");
            ReportGen.GenLog("Error Note: "+PcscError.getMessage(e.returnCode()));
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return 0;
        }
        catch(UnsatisfiedLinkError e1)
        {
//            e1.printStackTrace();
            log.error("Error: "+e1.toString());
            return 0;
        }
    }

    /**
     *Get the reset data of the current card
     *
     *@return: the reset data
     *
     */
    public static byte[] PCSC_reset()
    {
        if(phCard==0)
            return errors;
        int n=0;
        String data="";
        ATRData=new byte[100];
        Integer returnedProtocol = new Integer(0);
        try
        {
            term.SCardReconnect(phCard,
                                SCARD_SHARE_EXCLUSIVE,
                                SCARD_PROTOCOL_T0 | SCARD_PROTOCOL_T1,
                                SCARD_RESET_CARD,
                                returnedProtocol);
            ATRData=term.SCardGetAttrib(phCard,
                                        SCARD_ATTR_ATR_STRING);


            ReportGen.GenLog("Reset:");
  //          AddMessageThread.addMsg("Reset:");

            if(ATRData!=null)
            {
                //AddMessageThread.addMsg("atr: ("+Function.hexify((byte)(ATRData.length))+") "+Function.hexify1(ATRData));

                for(n=0;n<ATRData.length;n++)
                {
                    data+=Function.hexify(ATRData[n])+" ";
                    if(n==23)
                    {
                        ReportGen.GenLog("atr: "+"("+Function.hexify((byte)ATRData.length)+") "+data);
                        data="";
                        continue;
                    }
                    if(n%24==23)
                    {
                        ReportGen.GenLog("          "+data);
                        data="";
                        continue;
                    }
                }
                if(ATRData.length<23)
                {
                    ReportGen.GenLog("atr: "+"("+Function.hexify((byte)ATRData.length)+") "+data);
                }
                else if(data!="")
                {
                    ReportGen.GenLog("          "+data);
                }
                return ATRData;
            }
            else
            {
                ReportGen.GenLog("atr: (02) FF FF");
//                AddMessageThread.addMsg("atr: (02) FF FF");

                return errors;
            }
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return errors;
        }
    }

    /**
     *Close the current Smart Card reader
     *
     */
    public static void PCSC_close()
    {
        if(phCard==0 || phContext==0)
            return;
        try
        {
            term.SCardDisconnect(phCard,SCARD_LEAVE_CARD);
            phCard=0;
            term.SCardReleaseContext(phContext);
            phContext=0;
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return;
        }
    }

    /**
     *Send data to the card terminal.
     *
     *@param  CLA CLA byte of APDU (byte #0)
     *@param  INS INS byte of APDU (byte #1)
     *@param  P1 P1 byte of APDU (byte #2)
     *@param  P2 P2 byte of APDU (byte #3)
     *@param  toSendData byte array containing command data of APDU. Command data starts at byte #5.
     *@param  LE LE byte of APDU (appended to APDU). This byte is omitted if parameter has value -1.
     *
     *@return: the data returned from the card
     *
     */
    public static byte[] SendApdu(int CLA,
                                  int INS,
                                  int P1,
                                  int P2,
                                  byte[] toSendData,
                                  int LE)
    {

        if(phCard==0)
            return errors;
        resp=new byte[300];

        byte[] comm=new byte[300];
        int n=0,commlen=4;
        comm[0]=(byte)CLA;
        comm[1]=(byte)INS;
        comm[2]=(byte)P1;
        comm[3]=(byte)P2;
        if(toSendData!=null)
        {
            comm[4]=(byte)toSendData.length;
            commlen=5;
            for(n=0;n<toSendData.length;n++)
                comm[5+n]=toSendData[n];
            commlen=5+n;
            if(LE!=-1)
            {
                comm[5+n]=(byte)LE;
                commlen=6+n;
            }
        }
        else
        {
            if(LE!=-1)
            {
                comm[4]=(byte)LE;
                commlen=5;
            }
        }

        AddDescription(INS);

        command=new byte[commlen];
        for(n=0;n<commlen;n++)
            command[n]=comm[n];

        try
        {
            resp=term.SCardTransmit(phCard,
                                    command);
            log.debug(Function.hexify1(command));

            if(resp!=null)
            {
                if(resp.length==2 && resp[0]==(byte)0x61)
                {
                    byte[] GetResponse={(byte)0x0,(byte)0xC0,(byte)0x0,(byte)0x0,resp[1]};
                    resp=term.SCardTransmit(phCard,
                            GetResponse);
                    if(resp!=null)
                    {
                        ReportGen.GenLog(CLA,INS,P1,P2,toSendData,LE,resp);
//                        AddMessageThread.addMsg("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));

                        return resp;
                    }
                    else
                    {
                        ReportGen.GenLog(CLA,INS,P1,P2,toSendData,LE,errors);
//                        AddMessageThread.addMsg("rcv: (02) "+Function.hexify1(errors));

                        return errors;
                    }
                }
                ReportGen.GenLog(CLA,INS,P1,P2,toSendData,LE,resp);
//                AddMessageThread.addMsg("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));
       log.debug("Response is :" + new String(com.watchdata.gpscript.keymgr.util.encoders.Hex.encode(resp)));
               return resp;
            }
            else
            {
                ReportGen.GenLog(CLA,INS,P1,P2,toSendData,LE,errors);
//                AddMessageThread.addMsg("rcv: (02) "+Function.hexify1(errors));

                return errors;
            }
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return errors;
        }
    }

    /**
     *Send data to the card terminal.
     *
     *@param  toSendData send APDU command
     *@param  len the length of APDU command
     *
     *
     *@return: the data returned from the card
     *
     */
    public static byte[] SendApdu(byte[] toSendData,
                                  int len)
    {
        if(phCard==0)
            return errors;
        resp=new byte[512];
        byte[] command=null;

        //if(toSendData!=null && toSendData.length>=5 && len>=5)
        if(toSendData!=null && len>0)
        {
            command=new byte[toSendData.length];
            for(int n=0;n<toSendData.length;n++)
            {
                command[n]=toSendData[n];
            }
        }
        else
            return errors;

        byte INS=command[1];
        AddDescription(INS);

        try
        {
            resp=term.SCardTransmit(phCard,
                                    command);
            log.debug(Function.hexify1(command));
//            AddMessageThread.addMsg("snd: ("+Function.hexify((byte)(command.length))+") "+Function.hexify1(command));

            if(resp!=null)
            {
                if(resp.length==2 && resp[0]==(byte)0x61)
                {
                    byte[] GetResponse={(byte)0x0,(byte)0xC0,(byte)0x0,(byte)0x0,resp[1]};
                    resp=term.SCardTransmit(phCard,
                            GetResponse);
                    if(resp!=null)
                    {
                        ReportGen.GenLog("snd: ("+Function.hexify((byte)(command.length))+") "+Function.hexify1(command));
                        ReportGen.GenLog("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));
//                        AddMessageThread.addMsg("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));

                        return resp;
                    }
                    else
                    {
                        ReportGen.GenLog("snd: ("+Function.hexify((byte)(command.length))+") "+Function.hexify1(command));
                        ReportGen.GenLog("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));
//                        AddMessageThread.addMsg("rcv: (02) "+Function.hexify1(errors));

                        return errors;
                    }
                }
                ReportGen.GenLog("snd: ("+Function.hexify((byte)(command.length))+") "+Function.hexify1(command));
                ReportGen.GenLog("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));
//                AddMessageThread.addMsg("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));

                return resp;
            }
            else
            {
                ReportGen.GenLog("snd: ("+Function.hexify((byte)(command.length))+") "+Function.hexify1(command));
                ReportGen.GenLog("rcv: ("+Function.hexify((byte)(resp.length))+") "+Function.hexify1(resp));
//                AddMessageThread.addMsg("rcv: (02) "+Function.hexify1(errors));

                return errors;
            }
        }
        catch(PcscException e)
        {
//            e.printStackTrace();
            log.error("Error: "+e.toString());
            log.error("Error Note: "+PcscError.getMessage(e.returnCode()));
            return errors;
        }
    }

    /**
     *Add Description to Log
     *@param INS description data
     */
    public static void AddDescription(int INS)
    {
        switch(INS)
        {
            case 0xA4:
            {
                ReportGen.GenLog("Select Application:");
//                AddMessageThread.addMsg("Select Application:");

                break;
            }
            case 0xCA:
            {
                ReportGen.GenLog("Get Data:");
//                AddMessageThread.addMsg("Get Data:");

                break;
            }
            case 0xF2:
            {
                ReportGen.GenLog("Get Status:");
//                AddMessageThread.addMsg("Get Status:");

                break;
            }
            case 0xE6:
            {
                ReportGen.GenLog("Install Application:");
//                AddMessageThread.addMsg("Install Application:");

                break;
            }
            case 0x50:
            {
                ReportGen.GenLog("Initialize Update:");
//                AddMessageThread.addMsg("Initialize Update:");

                break;
            }
            case 0x82:
            {
                ReportGen.GenLog("External Authenticate:");
//                AddMessageThread.addMsg("External Authenticate:");

                break;
            }
            case -126://0x82
            {
                ReportGen.GenLog("External Authenticate:");
//                AddMessageThread.addMsg("External Authenticate:");

                break;
            }
            case 0xE2:
            {
                ReportGen.GenLog("Append Record|Store Data:");
//                AddMessageThread.addMsg("Append Record|Store Data:");

                break;
            }
            case 0xDA:
            {
                ReportGen.GenLog("Put Data:");
//                AddMessageThread.addMsg("Put Data:");

                break;
            }
            case 0xD8:
            {
                ReportGen.GenLog("Put Key:");
//                AddMessageThread.addMsg("Put Key:");

                break;
            }
            case 0x24:
            {
                ReportGen.GenLog("Change PIN:");
//                AddMessageThread.addMsg("Change PIN:");

                break;
            }
            case 0xF0:
            {
                ReportGen.GenLog("Set Status:");
//                AddMessageThread.addMsg("Set Status:");

                break;
            }
            case 0xE4:
            {
                ReportGen.GenLog("Delete Application:");
//                AddMessageThread.addMsg("Delete Application:");

                break;
            }
            case 0xE8:
            {
                ReportGen.GenLog("Load:");
//                AddMessageThread.addMsg("Load:");

                break;
            }
            default:
                break;
        }
    }
}