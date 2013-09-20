/**
 * <p>Title: ReportGen Class</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: WatchData</p>
 * @author Huang Peng
 * @version 1.0
 */

package com.gp.gpscript.device;

import java.io.*;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import org.apache.log4j.Logger;

//import com.watchdata.wdcams.loader.Loader;


public class ReportGen
{private static Logger logger = Logger.getLogger(ReportGen.class);
    private static String m_fileName=null;//Log file name
    private static boolean isGenLog=true;

    /**
     *when send APDU command,Generate log or not
     *
     *@param isLog true or false,Generate log or not,default is true,
     * if you want not to log,you must set islog true or false first.
     *
     */
    public static void SetIsLog(boolean isLog)
    {
        isGenLog=isLog;
    }

    /**
     *Set log name by local date(yyyyMMddHHmmss)+".log"
     *
     */
    public static void SetLogName()
    {
        String fileName=null;
        try
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            Date today=new Date();
            fileName=sdf.format(today)+".log";

            String root=System.getProperty("user.dir")+"\\Reports\\";
            m_fileName=root+fileName;
        }
        catch(Exception e)
        {
//            e.printStackTrace();
        	logger.error(e.getMessage());
        }
    }
    /**
     *Get the name of the log file.
     *
     *@return: the string of the log file name
     *
     */
    public static String GetLogName()
    {
        return m_fileName;
    }

    /**
     *Save data to the log.
     *
     *@param  CLA CLA byte of APDU (byte #0)
     *@param  INS INS byte of APDU (byte #1)
     *@param  P1 P1 byte of APDU (byte #2)
     *@param  P2 P2 byte of APDU (byte #3)
     *@param  toSendData byte array containing command data of APDU. Command data starts at byte #5.
     *@param  LE LE byte of APDU (appended to APDU). This byte is omitted if parameter has value -1.
     *@param  resp the receive data from the card
     *
     *@return: 0 failed
     *@return: 1 successful
     *
     */
    public static int GenLog(int CLA,
                             int INS,
                             int P1,
                             int P2,
                             byte[] toSendData,
                             int LE,
                             byte[] resp)
    {
        if(isGenLog==false || m_fileName==null)
            return 1;

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

        String filename,path;

        String commdata="";
        String respdata="";

/*
 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
  Date today=new Date();
  filename=sdf.format(today)+".txt";

    try
    {
      String root=System.getProperty("user.dir")+"\\Reports\\";
      filename=root+filename;
    }
    catch(Exception e)
    {
    }
*/
        filename=m_fileName;
        if(filename==null || filename.equals(""))
            return 0;

        File f=new File(filename);
        RandomAccessFile file=null;
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(IOException e)
            {
//                e.printStackTrace();
            	logger.error("Error: "+e.toString());
                return 0;
            }
        }
        try
        {
            file=new RandomAccessFile(filename,"rw");
            long filelen=file.length();
            file.seek(filelen);
            for(n=0;n<commlen;n++)
            {
                commdata+=Function.hexify(comm[n])+" ";
                if(n==23)
                {
                    file.writeBytes("snd: "+"("+Function.hexify((byte)commlen)+") "+commdata+"\r\n");
                    commdata="";
                    continue;
                }
                if(n%24==23)
                {
                    file.writeBytes("          "+commdata+"\r\n");
                    commdata="";
                    continue;
                }
            }
            if(commlen<23)
                file.writeBytes("snd: "+"("+Function.hexify((byte)commlen)+") "+commdata+"\r\n");
            else if(commdata!="")
                file.writeBytes("          "+commdata+"\r\n");
            for(n=0;n<resp.length;n++)
            {
                respdata+=Function.hexify(resp[n])+" ";
                if(n==23)
                {
                    file.writeBytes("rcv: "+"("+Function.hexify((byte)resp.length)+") "+respdata+"\r\n");
                    respdata="";
                    continue;
                }
                if(n%24==23)
                {
                    file.writeBytes("          "+respdata+"\r\n");
                    respdata="";
                    continue;
                }
            }
            if(resp.length<23)
                file.writeBytes("rcv: "+"("+Function.hexify((byte)resp.length)+") "+respdata+"\r\n");
            else if(respdata!="")
                file.writeBytes("          "+respdata+"\r\n");

            file.close();
        }
        catch(IOException e)
        {
//            e.printStackTrace();
        	logger.error("Error: "+e.toString());
            try
            {
                file.close();
            }
            catch(IOException e1)
            {
//                e1.printStackTrace();
            	logger.error("Error: "+e1.toString());
            }
            return 0;
        }

        return 1;
    }


    /**
     *Save data to the log file.
     *
     *@param  data - the data that will be saved.
     *
     *@return: 0 failed
     *@return: 1 successful
     *
     */
    public static int GenLog(String data)
    {
        data = com.watchdata.gpscript.util.PropertiesManager.encodeString(data);
        if(isGenLog==false || m_fileName==null)
            return 1;

        String filename,path;

/*
 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
  Date today=new Date();
  filename=sdf.format(today)+".txt";
    try
    {
      String root=System.getProperty("user.dir")+"\\Reports\\";
      filename=root+filename;
    }
    catch(Exception e)
    {
    }
*/
        filename=m_fileName;
        if(filename==null || filename.equals(""))
            return 0;
        File f=new File(filename);
        RandomAccessFile file=null;
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(IOException e)
            {
//                e.printStackTrace();
            	logger.error("Error: "+e.toString());
                return 0;
            }
        }
        try
        {
            file=new RandomAccessFile(filename,"rw");
            long filelen=file.length();
            file.seek(filelen);
            file.writeBytes(data+"\r\n");

            file.close();
        }
        catch(IOException e)
        {
//            e.printStackTrace();
        	logger.error("Error: "+e.toString());
            try
            {
                file.close();
            }
            catch(IOException e1)
            {
//                e1.printStackTrace();
            	logger.error("Error: "+e1.toString());
            }
            return 0;
        }

        return 1;
    }

    /**
     *Generate the report from the log file.
     *
     *@param  logfilename - the name of the log file that will generate the report,e.g. XXX.txt.
     *
     *@return: 0 failed
     *@return: 1 successful
     *
     */
    public static int GenReport(String logfilename)
    {
        String filename,path,logname;
        RandomAccessFile logfile=null;
        RandomAccessFile file=null;
        Date today=new Date();

        logname=logfilename;
        File log=new File(logname);
        if(!log.exists())
        {
            logger.error("Log "+logname+" doesn't exist,Report can't generate!");
            return 0;
        }
        Date lognameDate=new Date(log.lastModified());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_lognameDate=sdf.format(lognameDate);
        String str_today=sdf.format(today);
        int num=logfilename.lastIndexOf("\\");
        //filename=logfilename.substring(num,num+9)+".html";
        filename=logfilename.substring(num,num+15)+".html";

        try
        {
            String root=System.getProperty("user.dir")+"\\Reports\\";
            filename=root+filename;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        	
        }

        File f=new File(filename);
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(IOException e)
            {
//                e.printStackTrace();
                logger.error("Error: "+e.toString());
                return 0;
            }
        }

        try
        {
            logfile=new RandomAccessFile(logname,"r");
            file=new RandomAccessFile(filename,"rw");
            file.writeBytes("<html><head><title>Report</title>");
            file.writeBytes("<meta http-equiv=\"content-type\" content=\"text/html;charset=gb2312\"> </head><body>\r\n");
            file.writeBytes("<p align=left><img border=0 src=images/Watch_logo.gif width=196 height=48></p>\r\n");
            file.writeBytes("<p align=center><b>Personalisation APDUs</b></p>\r\n");
            //file.writeBytes("Tested at: "+lognameDate.toLocaleString()+"<br>\r\n");
            file.writeBytes("Tested at: "+str_lognameDate+"<br>\r\n");
            //file.writeBytes("Generated at: "+today.toLocaleString()+"<br>\r\n");
            file.writeBytes("Generated at: "+str_today+"<br>\r\n");
            file.writeBytes("Tested by:TCPS"+"<br>\r\n");
            file.writeBytes("<p align=left><b>ICC Personalisation Details</b></p>\r\n");
            file.writeBytes("<hr>\r\n");
            String data,data1;
            int n=0,m=0;
            while((data=logfile.readLine())!=null)
            {
                n++;
                m++;
                if(data.charAt(0)==' ')
                {
                    //data1="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+data;
                    data1="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+data;
                    data=data1;
                }
                file.writeBytes("<p align=left>"+data+"</p>\r\n");
            }
            file.writeBytes("<hr>\r\n");
            file.writeBytes("</body></html>\r\n");


            file.close();
            logfile.close();
        }
        catch(IOException e)
        {
//            e.printStackTrace();
            logger.error("Error: "+e.toString());
            try
            {
                file.close();
                logfile.close();
            }
            catch(IOException e1)
            {
//                e1.printStackTrace();
                logger.error("Error: "+e1.toString());
            }
            return 0;
        }

        return 1;
    }

    /**
     *Save data to the file.
     *
     *@param  filename - the file name that will be saved.
     *@param  data - the data that will be saved.
     *
     *@return: 0 failed
     *@return: 1 successful
     *
     */
    public static int WriteFile(String filename,String data)
    {
        if(filename==null || filename.equals(""))
            return 0;
        String root=System.getProperty("user.dir")+"\\Reports\\";
        filename=root+filename;
        File f=new File(filename);
        RandomAccessFile file=null;
        if(!f.exists())
        {
            try
            {
                f.createNewFile();
            }
            catch(IOException e)
            {
//                e.printStackTrace();
            	logger.error("Error: "+e.toString());
                return 0;
            }
        }
        try
        {
            file=new RandomAccessFile(filename,"rw");
            long filelen=file.length();
            file.seek(filelen);
            data = com.watchdata.gpscript.util.PropertiesManager.encodeString(data);
            file.writeBytes(data);

            file.close();
        }
        catch(IOException e)
        {
//            e.printStackTrace();
        	logger.error("Error: "+e.toString());
            try
            {
                file.close();
            }
            catch(IOException e1)
            {
//                e1.printStackTrace();
            	logger.error("Error: "+e1.toString());
            }
            return 0;
        }

        return 1;
    }

    public static String formatString(String str)
    {
        String app="";
        int max=str.length()/2;
        int n=0;
        for(n=0;n<2*max;n=n+2)
        {
            app=app+str.substring(n,n+2)+" ";
        }
        app=app+str.substring(n);
        return app;
    }
    public static String formatString(String str,int length)
    {
        String app="";
        int max=str.length()/length;
        int n=0;
        for(n=0;n<length*max;n=n+length)
        {
            //app=app+str.substring(n,n+length)+"&nbsp;<br>";
            app=app+formatString(str.substring(n,n+length))+"&nbsp;<br>";
        }
        app=app+formatString(str.substring(n));

        return app;
    }
    /**
      *Generate a table in html file
      *@param fileName - the html file name,e.g. 030226.html
      *@param rows - the row data of table
      *@param columnHeads - the head data of table
      *
      *@return: 0 failed
      *@return: 1 successful
      */
     public static int GenTable(String fileName,String[][] rows,String[][] columnHeads)
     {
         String root=System.getProperty("user.dir")+"\\Reports\\";
         fileName=root+fileName;
         File f=new File(fileName);
         RandomAccessFile file=null;
         if(rows==null || columnHeads==null)
             return 0;
         int rows_columnlen=rows[0].length;
         int rows_rowlen=rows.length;
         int Heads_columnlen=columnHeads[0].length;
         int Heads_rowlen=columnHeads.length;

         if(!f.exists())
         {
             try
             {
                 f.createNewFile();
             }
             catch(IOException e)
             {
//                 e.printStackTrace();
            	 logger.error("Error: "+e.toString());
                 return 0;
             }
         }
         try
         {
             file=new RandomAccessFile(fileName,"rw");
             long filelen=file.length();
             file.seek(filelen);
             file.writeBytes("<p></p>\r\n");
             file.writeBytes("<table border=1 width=100% cellpadding=0 cellspacing=0>\r\n");

             for(int ID=0;ID<Heads_rowlen;ID++)
             {
                 file.writeBytes("<tr>\r\n");
                 int colspan=1;
                 for(int m=0;m<Heads_columnlen;m++)
                 {
                     if(columnHeads[ID][m].equals(""))
                     {
                         colspan=Heads_columnlen;
                         Heads_columnlen=1;
                     }
                     else
                     {
                         Heads_columnlen=columnHeads[0].length;
                     }
                 }
                 for(int n=0;n<Heads_columnlen;n++)
                 {
                     file.writeBytes("<th colspan="+colspan+" align=left>"
                                     +columnHeads[ID][n]+"</th>\r\n");
                 }
                 file.writeBytes("</tr>\r\n");
             }

             for(int ID=0;ID<rows_rowlen;ID++)
             {
                 file.writeBytes("<tr>\r\n");
                 int colspan=1;
                 /*
                 for(int m=0;m<rows_columnlen;m++)
                 {
                     if(rows[ID][m].equals(""))
                     {
                         colspan=rows_columnlen;
                         rows_columnlen=1;
                     }
                 }
                 //*/
                 for(int n=0;n<rows_columnlen;n++)
                 {
                     file.writeBytes("<td colspan="+colspan+" align=left>"+rows[ID][n]+"</td>\r\n");
                 }
                 file.writeBytes("</tr>\r\n");
             }

             file.writeBytes("</table>\r\n");
             file.close();
         }
         catch(IOException e)
         {
//             e.printStackTrace();
        	 logger.error("Error: "+e.toString());
             try
             {
                 file.close();
             }
             catch(IOException e1)
             {
//                 e.printStackTrace();
            	 logger.error("Error: "+e1.toString());
             }
             return 0;
         }

         return 1;
     }

     public static int GenTable(String fileName,String[][] rows,String[] columnHeads)
     {
         String root=System.getProperty("user.dir")+"\\Reports\\";
         fileName=root+fileName;
         File f=new File(fileName);
         RandomAccessFile file=null;
         if(rows==null || columnHeads==null)
             return 0;
         int rows_columnlen=rows[0].length;
         int rows_rowlen=rows.length;
         int Heads_columnlen=columnHeads.length;
         int Heads_rowlen=1;

         if(!f.exists())
         {
             try
             {
                 f.createNewFile();
             }
             catch(IOException e)
             {
//                 e.printStackTrace();
            	 logger.error("Error: "+e.toString());
                 return 0;
             }
         }
         try
         {
             file=new RandomAccessFile(fileName,"rw");
             long filelen=file.length();
             file.seek(filelen);
             file.writeBytes("<p></p>\r\n");
             file.writeBytes("<table border=1 width=100% cellpadding=0 cellspacing=0>\r\n");


             file.writeBytes("<tr>\r\n");
             int colspan=1;
             for(int m=0;m<Heads_columnlen;m++)
             {
                 if(columnHeads[m].equals(""))
                 {
                     colspan=Heads_columnlen;
                     Heads_columnlen=1;
                 }
                 else
                 {
                     Heads_columnlen=columnHeads.length;
                 }
             }
             for(int n=0;n<Heads_columnlen;n++)
             {
                 file.writeBytes("<th colspan="+colspan+" align=left>"+columnHeads[n]+"</th>\r\n");
             }
             file.writeBytes("</tr>\r\n");


             for(int ID=0;ID<rows_rowlen;ID++)
             {
                 file.writeBytes("<tr>\r\n");
                 colspan=1;
                 for(int m=0;m<rows_columnlen;m++)
                 {
                     if(rows[ID][m].equals(""))
                     {
                     colspan=rows_columnlen;
                     rows_columnlen=1;
                 }
                 }
                 for(int n=0;n<rows_columnlen;n++)
                 {
                     file.writeBytes("<td colspan="+colspan+" align=left>"+rows[ID][n]+"</td>\r\n");
                 }
                 file.writeBytes("</tr>\r\n");
             }

             file.writeBytes("</table>\r\n");

             file.close();
         }
         catch(IOException e)
         {
//             e.printStackTrace();
        	 logger.error("Error: "+e.toString());
             try
             {
                 file.close();
             }
             catch(IOException e1)
             {
//                 e1.printStackTrace();
            	 logger.error("Error: "+e1.toString());
             }
             return 0;
         }

         return 1;
     }

     /**
       *Generate a tcps report in html file
       *@param fileName - the html file name,e.g. 030226.html
       *
       *@return: 0 failed
       *@return: 1 successful
      */
     int TcpsReportFormat(String fileName)
     {
         File f=new File(fileName);
         RandomAccessFile file=null;

         if(!f.exists())
         {
             try
             {
                 f.createNewFile();
             }
             catch(IOException e)
             {
//                 e.printStackTrace();
            	 logger.error("Error: "+e.toString());
                 return 0;
             }
         }
         try
         {
             file=new RandomAccessFile(fileName,"rw");
             long filelen=file.length();
             file.seek(filelen);
             file.writeBytes("<p></p>\r\n");

             file.close();
         }
         catch(IOException e)
         {
//             e.printStackTrace();
        	 logger.error("Error: "+e.toString());
             try
             {
                 file.close();
             }
             catch(IOException e1)
             {
//                 e.printStackTrace();
            	 logger.error("Error: "+e1.toString());
             }
             return 0;
         }

         return 1;
     }

    /**
     *View the report.
     *
     *@param  Reptname - the report name that will be viewed, e.g. XXXX.html
     *
     *@return: 0 failed
     *@return: 1 successful
     *
     */
    public static int ViewReport(String Reptname)
    {
        String command="C:\\Program Files\\Internet Explorer\\Iexplore.exe ";

        File rept=new File(Reptname);
        if(!rept.exists())
        {
        	logger.error(Reptname+" can't find");
            return 0;
        }
        try
        {
            Runtime.getRuntime().exec(command+rept.getAbsolutePath());
        }
        catch(IOException e)
        {
//            e.printStackTrace();
        	logger.error("Error: "+e.toString());
            return 0;
        }
        return 1;
    }
    public static void main(String[] args)
    {
        String str="01234";
        String buf=formatString(str,2);
        logger.debug(buf);
    }
}