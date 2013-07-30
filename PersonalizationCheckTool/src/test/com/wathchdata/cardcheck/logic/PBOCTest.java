package test.com.wathchdata.cardcheck.logic;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.watchdata.cardcheck.logic.impl.PBOCHandler;


/**
 * 
 * @description:
 * @author: juan.jiang May 10, 2010
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class PBOCTest extends AbstractJUnit38SpringContextTests {

	private PBOCHandler pbocHandler;
	
	public void testxx() throws Exception {
		pbocHandler = (PBOCHandler)applicationContext.getBean("pbocHandler");
		//pbocHandler.doTrade(5000, "Generic EMV Smartcard Reader 0",null);
		
	}



}
