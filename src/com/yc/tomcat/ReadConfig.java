package com.yc.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 采用单例模式读取配置文件
 * @author xiaoxin
 * @date 2020年8月21日
 */
public class ReadConfig extends Properties {
	private static final long serialVersionUID = -613052409643698399L;
	
	private static ReadConfig instance = new ReadConfig();
	private ReadConfig() {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("web.properties")){
			load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ReadConfig getInstance() {
		return instance;
	}
	

}
