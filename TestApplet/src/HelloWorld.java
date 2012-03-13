

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
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
	public void writeFile(){
		File file=new File("D:/1.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
