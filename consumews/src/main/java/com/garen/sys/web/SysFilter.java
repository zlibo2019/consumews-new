package com.garen.sys.web;


import com.garen.common.AbstractFilter;
import com.garen.sys.filter.LimitFilterImpl;
import com.garen.sys.filter.SrvTimerFilterImpl;
import com.garen.sys.filter.UpLoadFilterImpl;


final public class SysFilter extends AbstractFilter {

	public static final String USER = "USER";//用户key
	public static final String MENUS = "MENUS";//用户菜单key
	
	public SysFilter(){
		//结算服务过滤器
		regFilter(new SrvTimerFilterImpl());
		//上传附件过滤器
		regFilter(new UpLoadFilterImpl());
		//访问权限控制
		regFilter(new LimitFilterImpl());
	}
	
}
