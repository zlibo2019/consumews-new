package com.garen.sys.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebListener
public final class OnLineListener implements ServletRequestListener,ServletContextListener {
	
	private static OnLineListener onLineListener;
	private  Log log = LogFactory.getLog(OnLineListener.class);
	private int onLine = 0;//当前在线数
	
    public OnLineListener() {
    	onLineListener = this;
    	log.debug("监听器启动" + this);
    }
    
    @Override
    public void requestDestroyed(ServletRequestEvent arg0)  { 
    	synchronized (this) {
			onLine--;
		}
    }
    @Override
    public void requestInitialized(ServletRequestEvent arg0)  { 
    	//log.debug("request 请求");
		synchronized (this) {
			onLine++;
		}
    }
	
    public int getOnLine() {
		return onLine;
	}
  
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//isrvTimer = (ISrvTimer)SysFilter.getSpringBean("srvTimerImpl");
		//isrvTimer.setOnlineListener(this);
		//log.debug("request 监听器");
	}
	
	public static OnLineListener getInst(){
		return onLineListener;
	}
}
