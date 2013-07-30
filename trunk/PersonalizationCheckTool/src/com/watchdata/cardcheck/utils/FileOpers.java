package com.watchdata.cardcheck.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.app.Application;
import com.watchdata.cardcheck.dao.pojo.StaticData;
import com.watchdata.cardcheck.panel.TestDataConfigPanel;

/**
 * FileOpers.java
 * 
 * @description: 文件读写类
 * @author: pei.li 2012-3-28
 * @version:1.0.0
 * @modify：
 *@Copyright：watchdata
 */

public class FileOpers {

	private static Logger log = Logger.getLogger(FileOpers.class);
	private PropertiesManager pm = new PropertiesManager();

	public FileOpers() {

	}

	/**
	 * @Title: getFilePath
	 * @Description: 获取选择的文件路径
	 * @param
	 * @return String 文件路径
	 * @throws
	 */
	@SuppressWarnings("static-access")
	public String getFilePath() {
		String filePath = ""; 
		JFileChooser fileChooser = new JFileChooser("C:\\");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "TXT File", "txt");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(Application.frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			filePath = file.getAbsolutePath();
			/*if (!filePath.endsWith("txt")) {
				filePath = filePath + ".txt";
			}*/
			/*filePath = fileChooser.getCurrentDirectory() + file.getName();*/
		} else if (result == fileChooser.CANCEL_OPTION) {
			filePath = null;
		}
		return filePath;
	}

	/**
	 * @Title: readFile
	 * @Description 读取指定路径的文件转换为StaticData对象，存在List中为导入准备数据
	 * @param filePath
	 *            文件路径
	 * @return 从文件中读取的数据转换后的对象集
	 * @throws IOException
	 *             输入输出流异常
	 */
	public List<StaticData> readFile(String filePath) {
		List<StaticData> importData = new ArrayList<StaticData>();
		String strRow = null;
		String[] str = new String[4];
		String appType = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		List<String> tagList = new ArrayList<String>();
		try {
			isr = new InputStreamReader(new FileInputStream(filePath), "GB2312");
			br = new BufferedReader(isr);
			while (br.ready()) {
				strRow = br.readLine();
				String string = strRow.trim();
				if("".equals(strRow.trim()) || null == strRow.trim()){
					return importData;
				}
				if (strRow.contains("//")) {
					str = strRow.split("//");
					if(str.length != 2){
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.apptypeError"),
								pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
					appType = str[1];
					if(appType.length() > 32){
						JOptionPane.showMessageDialog(null, str[0] + pm.getString("mv.testdata.apptypeTooLong"),
								pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
					/*if(appType.length() == 0){
						JOptionPane.showMessageDialog(null, str[0] + pm.getString("mv.testdata.apptypeBlank"),
								pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						return null;
					}*/
					tagList.clear();
				} else {
					StaticData staticData = new StaticData();
					str = strRow.split("\\|");
					staticData.setAppType(appType);
					if(str.length > 4){
						JOptionPane.showMessageDialog(null,"\"" + str[0] + "\"" + pm.getString("mv.testdata.tagError"),
								pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
					if(str.length == 0){
						JOptionPane.showMessageDialog(null,pm.getString("mv.testdata.tagBlank"),
								pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
					
				    if(str.length > 0){
				    	if (TestDataConfigPanel.tagCheck(str[0])) {
							if(tagList.contains(str[0])){
								JOptionPane.showMessageDialog(null, "\"" + str[0] + "\"" + pm.getString("mv.testdata.tagReapted"),
										pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
								return null;
							}else{
								staticData.setTag(str[0]);
								tagList.add(str[0]);
							}
						} else {
							JOptionPane.showMessageDialog(null, "\"" + str[0] + "\"" + pm.getString("mv.testdata.dataTypeError"),
									pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return null;
						}
				    }
				    
				    if(str.length > 1){
				    	staticData.setLength(Integer.valueOf(str[1]));
				    }else if(str.length == 1){
				    	staticData.setLength(0);
				    }
					if (str.length > 2) {
						if(str[2].length() > 2048){
							JOptionPane.showMessageDialog(null, "\"" + str[0] + "\"" + pm.getString("mv.testdata.valueTooLong"),
									pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return null;
						}else{
							staticData.setOriValue(str[2]);
						}	
					}
					if (str.length > 3) {
						if(str[3].length() > 32){
							JOptionPane.showMessageDialog(null, "\"" + str[0] + "\"" + pm.getString("mv.testdata.descTooLong"),
									pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return null;
						}else{
							staticData.setDscrpt(str[3]);
						}	
					}
					importData.add(staticData);
				}
			}
			return importData;

		} catch (IOException e) {
			log.info("file read to import failed.");
			return null;
		} catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(null, "\"" + str[0] + "\"" + pm.getString("mv.testdata.lengthError"),
					pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			return null;
		}finally {
			try {
				isr.close();
				br.close();
			} catch (IOException e) {
				log.info("IOStream close failed.");
			}
		}
	}

	/**
	 * @Title: writeFile
	 * @Description 将界面已选中的行数据写入文件中
	 * @param filePath
	 *            写入文件路径 exportDatas 界面选中行数据转化为的对象集
	 * @return true表示文件写入成功，false表示写文件失败
	 * @throws FileNotFoundException文件路径不正确异常
	 *             IOException 资源关闭时的IO异常
	 */
	public boolean writeFile(String filePath, List<StaticData> exportDatas) {
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		FileOutputStream fo = null;
		String strAppType = "";
		int appNum = 1;
		String tag = "";
		int length = 0;
		String oriValue = "";
		String dscrpt = "";
		try {
			fo = new FileOutputStream(filePath);
			osw = new OutputStreamWriter(fo, "GB2312");
			bw = new BufferedWriter(osw);
			for (int i = 0; i < exportDatas.size(); i++) {
				tag = exportDatas.get(i).getTag();
				length = exportDatas.get(i).getLength();
				if ("".equals(exportDatas.get(i).getOriValue())
						|| exportDatas.get(i).getOriValue() == null) {
					oriValue = "";
				} else {
					oriValue = exportDatas.get(i).getOriValue();
				}
				if ("".equals(exportDatas.get(i).getDscrpt())
						|| exportDatas.get(i).getDscrpt() == null) {
					dscrpt = "";
				} else {
					dscrpt = exportDatas.get(i).getDscrpt();
				}
				if (i == 0) {
					strAppType = exportDatas.get(i).getAppType();
					bw.write("[" + appNum + "]//" + strAppType + "\r\n");
					bw.write(tag + "|" + length + "|" + oriValue + "|" + dscrpt
							+ "|\r\n");
				} else {
					if (strAppType.equals(exportDatas.get(i).getAppType())) {
						bw.write(tag + "|" + length + "|" + oriValue + "|"
								+ dscrpt + "|\r\n");
					} else {
						strAppType = exportDatas.get(i).getAppType();
						appNum++;
						bw.write("[" + appNum + "]//" + strAppType + "\r\n");
						bw.write(tag + "|" + length + "|" + oriValue + "|"
								+ dscrpt + "|\r\n");
					}
				}
			}
			bw.flush();
			return true;
		} catch (Exception e) {
			log.info("file write to export failed.");
			e.printStackTrace();
			return false;
		} finally {
			try {
				fo.close();
				osw.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.info("IOStream close failed.");
			}
		}
	}

	
	 /*public static void main(String[] args) {
	 List<StaticData> exportDatas = new List();
	 FileOpers fileOpers = new FileOpers(); 
	 System.out.println(fileOpers.getFilePath());
	 fileOpers.writeFile(fileOpers.getFilePath(),exportDatas); 
	 }*/
	 

}
