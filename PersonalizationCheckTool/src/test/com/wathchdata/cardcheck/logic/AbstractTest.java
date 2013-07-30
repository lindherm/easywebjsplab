package test.com.wathchdata.cardcheck.logic;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.watchdata.cardcheck.dao.IAppInfoDao;


/**
 * 
 * @description:
 * @author: juan.jiang May 10, 2010
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AbstractTest extends AbstractJUnit38SpringContextTests {

	private IAppInfoDao appInfoDao;
	
	public void testxx() {
		appInfoDao = (IAppInfoDao)applicationContext.getBean("appInfoDao");
		System.out.println(appInfoDao.getAppId());
		
	}



}
