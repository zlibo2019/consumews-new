package com.garen.sys.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.common.MsgBean;
import com.garen.sys.biz.ISysBiz;
import com.garen.sys.dao.ICommonDao;
import com.garen.sys.entity.DtUser;

@Controller  
@RequestMapping("/")  
public final class SysAction extends BaseAction {

	protected static Log log = LogFactory.getLog(SysAction.class);   
	
	@Autowired
	private ISysBiz iSysBiz;
	
	@Autowired
	private ICommonDao commonDao;
	
	/*
	 *帐号密码验证
	 */
	@RequestMapping("/sys/login")
	public ModelAndView login(JsonPage jp,HttpSession s) {
		JSONObject paramJson = parseJson(jp);
		DtUser user = iSysBiz.login(jp, paramJson.getString("account"),
				paramJson.getString("password"));
		SysFilter.getSession().setAttribute(SysFilter.USER,user);
		jp.setObj("JSESSIONID=" + SysFilter.getRequest().getSession().getId());
		return Json(jp);
	}
	
	/*
	 * 注销登录
	 * */
	@RequestMapping("/sys/logout")
	public ModelAndView logout() {
		MsgBean mbean = SysFilter.getMsgBean(0,"注销成功");
        SysFilter.getSession().invalidate();
        return Json(mbean);
	}
	
	/*
	 *普通查询
	 */
	@RequestMapping("/test/formjson")
	public ModelAndView queryDep(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		log.debug(jobj);
		log.debug(getJSONObject(jobj,"data"));
		log.debug(getJSONArray(jobj,"data"));
		return Json(jp);
	}
	
	/*
	 * 数据查询
	 * 参数为json
	 * 调用存储过程
	 * 返回结果为json
	 */
	@RequestMapping("/test/queryJson")
	public ModelAndView queryDepJson(JsonPage jp) {
		Map<String,Object> map = parseJsonMap(jp);
		log.debug(map);
		Map<String,String> keyMap = new HashMap<String, String>();
		map = mapKeyTrans(map, keyMap);
		commonDao.callProc(jp,"pro_test","$a,$b,#total", map);
		return Json(jp);
	}

	/*
	 * 数据查询
	 * 参数为json
	 * 调用存储过程
	 * 返回结果为json
	 */
	@RequestMapping("/test")
	public ModelAndView test(JsonPage jp) {
		Map<String,Object> map = parseJsonMap(jp);
		log.debug(map);
		commonDao.callProc(jp,"pro_fin_test","@p1,$p2,@p3,#total", map);
		return Json(jp);
	}
}
