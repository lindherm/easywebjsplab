package test.com.wathchdata.cardcheck.logic;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.watchdata.cardcheck.logic.impl.QPBOCHandler;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class QPBOCTest extends AbstractJUnit38SpringContextTests{
	private QPBOCHandler qpbocHandler;
	
	public void testxx() throws Exception {
		qpbocHandler = (QPBOCHandler)applicationContext.getBean("qPbocHandler");
		qpbocHandler.trade("Watchdata W5181 Contactless Reader  1", 100,null,null);
		
	}
}
