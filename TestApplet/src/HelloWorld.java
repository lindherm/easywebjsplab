

import java.applet.Applet;
import java.util.Date;

public class HelloWorld extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7304976481063742847L;

	@SuppressWarnings("deprecation")
	public String  getTimeStr(){
		return new Date().toLocaleString();
	}
	
	public boolean isSex(String name){
		return true;
	}
}
