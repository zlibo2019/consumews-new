package com.garen.sys.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.garen.common.IAuthFilter;
import com.garen.common.MsgBean;
import com.garen.sys.web.SysFilter;
import com.garen.utils.FilterUtils;

public class UpLoadFilterImpl implements IAuthFilter {
	
	private static  Log log = LogFactory.getLog(UpLoadFilterImpl.class);
	
	//上传文件大小,50M
	public static int MAX_POST_LENGTH = 1024 * 1024 * 50;
		
	@Override
	public String doFilter(MsgBean mbean, HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		String ret = SysFilter.FILTER_GO;
		int contentLen = FilterUtils.getContentLength(request);
		if(contentLen > MAX_POST_LENGTH ){
			ret = "POSTBIG";
			mbean.setInfo("上传文件太大 !");
			response.setStatus(500);
		}
		return ret;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig fConfig) {
		log.debug("UpLoadFilterImpl init");
	}

}
