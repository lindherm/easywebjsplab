/**
 * <p>Title: CardPrinter Class</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: WatchData</p>
 * @author Huang Peng
 * @version 1.0
 */

package com.gp.gpscript.device;

import org.apache.log4j.Logger;

//import com.watchdata.wdcams.loader.Loader;

public class CardPrinter {
	private static Logger log = Logger.getLogger(CardPrinter.class);
    /**
     *Open the Card Printer port
     *
     *@param port the Card Printer port,e.g. "LPT1"
     *
     *@return: 0 failed
     *@return: others successful,return value "fd" is the handle of the port
     */
    public native int CreateFile(String port);

    /**
     *Close the Card Printer port
     *
         *@param fd the handle of the Card Printer port,fd was set earlier by CreateFile
     *
     */
    public native void CloseHandle(int fd);

    /**
     *Send a command data to Card Printer
     *@param fd - the handle of the Card Printer port,fd was set earlier by CreateFile
     *@param comm - the command data
     *
     *@return: 0 failed
     *@return: >0 successful
     */
    public native int WriteFile(int fd, String comm);

    /**
     *Send a command data to Card Printer
     *@param comm - the command data
     *
     *@return: 0 failed
     *@return: >0 successful
     */
    public native int Send(String comm);

    /**
     *Open the Card Printer port
     *
     *@param port the Card Printer port,e.g. "LPT1"
     *
     *@return: 0 failed
     *@return: others successful,return value "fd" is the handle of the port
     */

    public native int sendCard();

    public native int ejectCard();

    public native int printCard(String cardFaceText);

    public native int printMag(String[] trackdata);

    /**
     *Send a command data to Card Printer
     *@param comm - the command data
     *
     *@return: 0 failed
     *@return: >0 successful
     */

    public static int SendComm(String comm) {
        try {
            System.loadLibrary("NbsPrinter");
            CardPrinter term = new CardPrinter();
            int returncode = term.Send(comm);
            return returncode;
        }
        catch (UnsatisfiedLinkError e) {
//            e.printStackTrace();
            log.error("Error: " + e.toString());
            return 0;
        }
        catch (SecurityException e1) {
//            e1.printStackTrace();
            log.error("Error: " + e1.toString());
            return 0;
        }
    }

    /**
     *Move Smart Card to Programming Station
     *
     */
    public void MS() {
        try {
            System.loadLibrary("NbsPrinter");
            this.sendCard();
            //Send("MS");
        }
        catch (Exception e) {
//            e.printStackTrace();
        	log.error(e.getMessage());
        }
    }

    /**
     *Exit Loaded Card to Output
     *
     */
    public void MO() {
        try {
            System.loadLibrary("NbsPrinter");
            this.ejectCard();
            //Send("MO");
        }
        catch (Exception e) {
//            e.printStackTrace();
        	log.error(e.getMessage());
        }
    }

    /**
     *Write Magnetic Stripe
     *@param trackdata - the data of Magnetic Stripe.
     *       It must have two strings include Track1 data and Track2 Data.
     *
     *@return: -1 failed
     *@return:  1 successful
     */
    public int WriteMag(String[] trackdata) {
        if (trackdata.length != 2)
            return -1;
        /*
        try {
            //System.out.println("Write Magnetic Stripe");
            Send("&C 1"); //Set Coercivity high
            Send("&B 1 " + trackdata[0]); // Write Buffer Single Track 1
            Send("&B 2 " + trackdata[1]); // Write Buffer Single Track 2
            Send("&E*"); //Write Track Buffers
            Function.Sleep(8000);
            return 1;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }*/
        printMag(trackdata);
        return 1;
    }

    /**
     *Print Card Body.
     *@param CardFaceText - the data of text.It must have four strings.
     *
     *@return: -1 failed
     *@return:  1 successful
     */
    public int PrintText(String[] CardFaceText) {
        //if(CardFaceText.length!=4)
        //	return -1;
        int num = CardFaceText.length;
        try {
            //System.out.println("Print Text");
            Send("+C 4"); //Adjust Thermal Transfer Intensity Level
            Send("F"); //Clear Monochrome Image Buffers
            if (num > 0)
                Send("T 400 200 0 1 0 50 1 " + CardFaceText[0]); //Write Text
            if (num > 1)
                Send("T 400 300 0 1 0 50 1 " + CardFaceText[1]); //Write Text
            if (num > 2)
                Send("T 400 400 0 1 0 50 1 " + CardFaceText[2]); //Write Text
            if (num > 3)
                Send("T 400 500 0 1 0 50 1 " + CardFaceText[3]); //Write Text
            Send("I"); //Print Card Monochrome Panel
            return 1;
        }
        catch (Exception e) {
//            e.printStackTrace();
        	log.error(e.getMessage());
            return -1;
        }
    }

}