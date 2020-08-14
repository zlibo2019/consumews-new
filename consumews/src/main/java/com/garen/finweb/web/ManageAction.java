package com.garen.finweb.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;
import com.garen.utils.MapBuilder;

@Controller  
@RequestMapping("/finweb/manage")  
public final class ManageAction extends BaseAction {
	/**
	 * fin-页面操作-系统管理 manage
	 * 操作:操作员授权、报表权限、核心业务IP授权验证
	 * 方法：permissions、authority、check
	 * 输入、输出参数均为JSON格式
	 */
	
	@Autowired
	private ICommonDao commonDao;
	//******************************************************
	//操作员授权 permissions：查询显示、设置筛选查询、保存设置
	//******************************************************
	/*--------------------------
	 * 操作员授权-查询显示 filterQry
	 */
	@RequestMapping("/permissions/filterQry")
	public ModelAndView permissionsfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_filterQry";
		String procParam = "$glyId,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}
	/*--------------------------
	 * 操作员授权-筛选查询设置-管理员 qrysetGly
	 */
	@RequestMapping("/permissions/qrySetGly")
	public ModelAndView qrySetGly(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_qryset";
		String procParam = "0,@gly_no,$glyId,$ip,#isall,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("isall", paramMap.get("isall"))
				.build();
		jp.setRetData(retMap);		
		return Json(jp);
	}	
	/*--------------------------
	 * 操作员授权-筛选查询设置-商户 qrySetMerch
	 */
	@RequestMapping("/permissions/qrySetMerch")
	public ModelAndView qrysetMerch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_qryset";
		String procParam = "1,@gly_no,$glyId,$ip,#isall,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("isall", paramMap.get("isall"))
				.build();
		jp.setRetData(retMap);		
		return Json(jp);
	}	
	/*--------------------------
	 * 操作员授权-筛选查询设置-IP qrySetIP
	 */
	@RequestMapping("/permissions/qrySetIP")
	public ModelAndView qrysetIP(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_qryset";
		String procParam = "2,@gly_no,$glyId,$ip,#isall,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("isall", paramMap.get("isall"))
				.build();
		jp.setRetData(retMap);		
		return Json(jp);
	}	
	/*--------------------------
	 * 操作员授权-保存设置-管理员 saveSetGly
	 */
	@RequestMapping("/permissions/saveSetGly")
	public ModelAndView saveSetGly(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_saveSet";
		String procParam = "0,@gly_no,@isall,$detail,$glyId,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 操作员授权-保存设置-商户 saveSetMerch
	 */
	@RequestMapping("/permissions/saveSetMerch")
	public ModelAndView saveSetMerch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_saveSet";
		String procParam = "1,@gly_no,@isall,$detail,$glyId,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 操作员授权-保存设置-IP saveSetIP
	 */
	@RequestMapping("/permissions/saveSetIP")
	public ModelAndView saveSetIP(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_permissions_saveSet";
		String procParam = "2,@gly_no,@isall,$detail,$glyId,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//报表权限 authority：查授权商户、查授权管理员
	//******************************************************	
	/*--------------------------
	 * 报表权限-查授权商户 qryMerch
	 */
	@RequestMapping("/authority/qryMerch")
	public ModelAndView authorityqryMerch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_authority_qryMerch";
		String procParam = "@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 报表权限-查授权管理员 qryGly
	 */
	@RequestMapping("/authority/qryGly")
	public ModelAndView authorityqryGly(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_authority_qryGly";
		String procParam = "@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	
	//******************************************************
	//核心业务IP授权验证 check：IP授权验证
	//******************************************************	
	/*--------------------------
	 * 核心业务IP授权验证-IP授权验证 ipGly
	 */
	@RequestMapping("/check/ipGly")
	public ModelAndView checkipGly(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_manage_check_ipGly";
		String procParam = "@gly_no,@ip,@op_name,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}	
		return Json(jp);
	}		
}
