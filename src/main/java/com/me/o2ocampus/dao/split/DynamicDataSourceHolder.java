package com.me.o2ocampus.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {
	
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	// local variable belonged to thread
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
	public static final String DB_MASTER = "master";
	public static final String DB_SLAVE = "slave";
	
	public static Object getDBType() {
		String dbtype = contextHolder.get();
		return dbtype == null?DB_MASTER:dbtype;
	}
	
	public static boolean setDBType(String dbtype) {
		if(dbtype.equals(DB_MASTER)) {
			logger.info("using data source:"+dbtype);
			contextHolder.set(dbtype);
			return true;
		}else if(dbtype.equals(DB_MASTER)) {
			logger.info("using data source:"+dbtype);
			contextHolder.set(dbtype);
			return true;
		}else {
			logger.info("doesn't support data source:"+dbtype);
			return false;
		}
	}
	
	public static void clearDBType() {
		contextHolder.remove();
	}

}
