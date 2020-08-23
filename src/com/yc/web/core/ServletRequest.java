package com.yc.web.core;


/**
 * 包装http请求的处理
 * @author xiaoxin
 * @date 2020年8月21日
 */
public interface ServletRequest {
	
	/**
	 * 解析请求的方法
	 */
	public void parse();
	
	/**
	 * 获取请求参数的方法
	 * @param key
	 * @return
	 */
	
	public String getParameter(String key);
	
	
	/**
	 * 获取解析出来的请求地址
	 * @return
	 */
	public String getUrl();
	
	
	/**
	 * 请求方式
	 * @return
	 */
	public String getMethod();
	
	
	/**
	 * 获取session
	 * @return
	 */
	public HttpSession getSession();
	
	
	/**
	 * 获取cookie
	 * @return
	 */
	public Cookie[] getCookies();
	
	
	/**
	 * 检查判断是否有JSessionId
	 * @return
	 */
	public boolean checkJSessionId();
	
	
	/**
	 * 获取用户的JSessionId
	 * @return
	 */
	public String getJSessionId();

}
