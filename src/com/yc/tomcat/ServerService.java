package com.yc.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import com.yc.web.core.HttpServletRequest;
import com.yc.web.core.HttpServletResponse;
import com.yc.web.core.Servlet;
import com.yc.web.core.ServletRequest;
import com.yc.web.core.ServletResponse;


public class ServerService implements Runnable {
	private Socket sk;
	private InputStream is;
	private OutputStream os;

	public ServerService(Socket sk) {
		this.sk=sk;
	}

	@Override
	public void run() {
		try {
			this.is = sk.getInputStream();
			this.os = sk.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;//如果出错，则终止运行
		}
		
		//处理请求
		ServletRequest request = new HttpServletRequest(is);
		//解析请求
		request.parse();
		//处理请求
		String url = request.getUrl();
		String urlStr = url.substring(1);
		String projectName = urlStr.substring(0,urlStr.indexOf("/"));
		
		ServletResponse response = new HttpServletResponse("/" + projectName,os);
		
		//是不是动态资源地址
		String clazz = ParseUrlPattern.getClass(url);
		if(clazz == null || "".equals(clazz)) {
			response.sendRedirect(url);
			return;
		}
		/**
		 * 处理动态资源
		 */
		URLClassLoader loader = null;
		URL classPath = null;
		url = url.substring(1);
		
		try {
			classPath = new URL("file",null,TomcatConstants.BASE_PATH+"\\"+projectName+"\\ bin");
			loader = new URLClassLoader(new URL[] {classPath});
			Class<?> cls = loader.loadClass(clazz);
			Servlet servlet = (Servlet) cls.newInstance();
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void send500(Exception e) {
		try {
			String msg = "HTTP/1.1 500 Error\r\n\r\n" + e.getMessage();
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}finally {
			if(os!=null) {
				try {
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}if(sk!=null) {
				try {
					sk.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	

}
