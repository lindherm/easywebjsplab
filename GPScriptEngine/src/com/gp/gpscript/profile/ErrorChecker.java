package com.gp.gpscript.profile;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
/**
	parser Error handler,
	print error message,and record error grade
*/
public class ErrorChecker extends DefaultHandler
{

   int ErrorCode;	//record error grade
   public ErrorChecker() {
   	ErrorCode=0;
   }


   public void warning (SAXParseException e) {
     // System.out.println("Parsing problem:  "+e.getMessage());
      ErrorCode=1;

   }

 public void error (SAXParseException e) {
    //  System.out.println("Parsing error:  "+e.getMessage());
      ErrorCode=2;

   }

   public void fatalError (SAXParseException e) {
 //     System.out.println("Parsing error:  "+e.getMessage());
  //    System.out.println("Cannot continue.");
      ErrorCode=3;

    //  System.exit(1);
   }

}

