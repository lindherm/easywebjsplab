package com.gerenhua.cardcheck.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * <p>Title:国际化</p>
 * <p>Description:国际化相关类</p>
 * <p>Copyright: Beijing Watchdata Copyright (c)2010</p>
 * <p>Company: Beijing Watchdata CO,.Ltd</p>
 * @author wu.yao
 * @version 1.0
 */
public class PropertiesManager {

	 String baseName = "resource";
	 public ResourceBundle res;
    public PropertiesManager() {
        try
        {
            res = ResourceBundle.getBundle( baseName, Locale.getDefault() );
           
//            ResourceBundle res = ResourceBundle.getBundle( baseName, new Locale("zh","CN","WINDOWS") );
//            res = ResourceBundle.getBundle( baseName, Locale.ENGLISH );
        }
        catch ( MissingResourceException exp )
        {
            exp.printStackTrace();
        }
    }

    public String getString(String key){
        return res.getString(key);
    }

    public String getString(String key,String defValue){
    	 try
         {
    		 return res.getString(key);
         }
         catch ( MissingResourceException exp )
         {
             exp.printStackTrace();
             return defValue;
         }
    }

    public static String encodeString(String input){
        try{
            input = new String(input.getBytes("GBK"), "ISO8859_1");
        }catch(Exception e){
            e.printStackTrace();
            e.printStackTrace();
        }
        return input;
    }
	
    /**
	 * Create the composite
	 * 
	 * @return int i 0=zh_Cn ,1=en
	 */   
    public int getLocal(){
    	int i = 0;
    	if(Locale.getDefault().toString().equalsIgnoreCase("zh_CN")) i = 0;
    	else if(Locale.getDefault().toString().equalsIgnoreCase("en")) i = 1;
    	return i;
    }
    
    private static PropertiesManager instance = null;    
	public static synchronized PropertiesManager getInstance() {    
	//这个方法比上面有所改进，不用每次都进行生成对象，只是第一次　　　 　    
	//使用时生成实例，提高了效率！    
	if (instance==null)    
	instance = new PropertiesManager();    
	return instance;
	}  
	
	public static void main(String args[]){
		PropertiesManager pm = new PropertiesManager();
		pm.getString(" mw.DataDecryptComposite.result", "File decrypt");
		
		
		
		System.out.println(Locale.getAvailableLocales());
		System.out.println(Locale.getDefault());
		System.out.println(Locale.getISOCountries());
		System.out.println(Locale.getISOLanguages());
	}
}