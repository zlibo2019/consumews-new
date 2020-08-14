package com.garen.sys.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.garen.common.IAuthFilter;
import com.garen.common.MsgBean;
import com.garen.sys.web.SysFilter;
import com.garen.utils.FilterUtils;

public class LimitFilterImpl implements IAuthFilter {

	private static  Log log = LogFactory.getLog(LimitFilterImpl.class);
	
	//免登录权限列表
	static protected List<Pattern> nologin = new Vector<Pattern>();
	//仅登录权限列表
	static protected List<Pattern> nolimit = new Vector<Pattern>();
		
	@Override
	public void destroy() {
		nologin.clear();
		nolimit.clear();
	}

	@Override
	public void init(FilterConfig fConfig) {
		String tmp = fConfig.getInitParameter("nologin");
		if(tmp == null) tmp = "";
		for(String t :tmp.split(",")){
			t = t.trim();
			nologin.add(Pattern.compile(t));
		}
		tmp = fConfig.getInitParameter("nolimit");
		if(tmp == null) tmp = "";
		for(String t :tmp.split(",")){
			t = t.trim();
			nolimit.add(Pattern.compile(t));
		}
		log.debug("LimitFilterImpl init");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String doFilter(MsgBean mbean, HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		String ret = SysFilter.FILTER_OK;
		String ctrluri = FilterUtils.filterUri(request);
		HttpSession hseesion = request.getSession();
		Object obj = hseesion.getAttribute(SysFilter.USER);
		//获取当前session的权限集合
		List<Pattern> needlimit = (List<Pattern>)hseesion.getAttribute(SysFilter.MENUS);
		if(null == needlimit){
			//如果为空
			needlimit = new ArrayList<Pattern>();
			hseesion.setAttribute(SysFilter.MENUS,needlimit);
		}
		if(false == FilterUtils.checkURI(nologin, ctrluri)){
			if(obj == null)	{//验证是否登录
				ret = "nologin"; 
				mbean.setRetInfo(-404, "请先登录系统 !");
			} else{
				if(false == FilterUtils.checkURI(nolimit, ctrluri)){//登录即可的
					if(false == FilterUtils.checkURI(needlimit, ctrluri)){//正式验证权限
						ret = "NOLIMIT";
						mbean.setRetInfo(-505, "没有访问权限 !");
					}
				}
			}
		}
		return ret;
	}
	
	//设置权限
	public static void  setNeedLimit(List<String> menuList){
		HttpSession session = SysFilter.getSession();
		@SuppressWarnings("unchecked")
		List<Pattern> needlimit = (List<Pattern>)session.getAttribute(SysFilter.MENUS);
		if(menuList == null) return;
		needlimit.clear();//清空
		for(String menu :menuList){
				if(StringUtils.isNotEmpty(menu))
					needlimit.add(Pattern.compile(menu));
		}
	}
			
		

}
