package com.yc.web.core;


/**
 * Servlet接口定义
 * @author xiaoxin
 * @date 2020年8月21日
 */
public interface Servlet {
	public void init();
	
	public void service(ServletRequest request,ServletResponse response);
	
	public void doGet(ServletRequest request,ServletResponse response);
	
	public void doPost(ServletRequest resRequest,ServletResponse response);

}
