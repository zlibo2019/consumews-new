package com.garen.sys.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.garen.common.IAuthFilter;
import com.garen.common.MsgBean;
import com.garen.sys.biz.ISrvTimer;
import com.garen.sys.web.SysFilter;

public class SrvTimerFilterImpl implements IAuthFilter {
	
	private ISrvTimer isrvTimer;
	
	private static  Log log = LogFactory.getLog(SrvTimerFilterImpl.class);
	
	@Override
	public String doFilter(MsgBean mbean, HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String ret = SysFilter.FILTER_GO;
		if(isrvTimer.getSvrStatus() == false){
			ret = "stopsrv"; 
			mbean.setRetInfo(-308, "服务正在结算，请稍候访问 !");
		}
		return ret;
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig fConfig) {
		isrvTimer = (ISrvTimer)SysFilter.getSpringBean("srvTimerImpl");
		log.debug("filter过滤器");
	}

}
