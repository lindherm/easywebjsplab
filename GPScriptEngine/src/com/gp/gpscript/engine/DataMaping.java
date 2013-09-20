package com.gp.gpscript.engine;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class DataMaping {

	Logger log = Logger.getLogger(DataMaping.class);
	@SuppressWarnings("unchecked")
	HashMap tagRecordMap;

	/**
	 * get script tag's value
	 * <p>
	 * Title: getDataElement
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param index
	 * @param delement
	 * @param varHashMap
	 * @return
	 * @throws Exception
	 */
	public String getDataElement(int index, String delement, HashMap varHashMap) throws Exception {
		try {
			tagRecordMap = (HashMap) varHashMap.get("" + index);
			String tag = delement.toLowerCase();
			if (tagRecordMap.containsKey(tag)) {
				String tgvalue = (String) tagRecordMap.get(tag);
				if (tgvalue != null && tgvalue instanceof String) {
					return (String) tgvalue;
				} else {
					return "";
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception("获取数据元素出错!");
		}
	}
}
