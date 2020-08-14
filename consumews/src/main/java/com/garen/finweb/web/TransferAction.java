package com.garen.finweb.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.garen.utils.LangUtils;
import com.garen.utils.MapBuilder;

@Controller  
@RequestMapping("/finweb/transfer")  
public final class TransferAction extends BaseAction {
	/**
	 * 20170824新增
	 * 莱恩水控圈存相关
	 * 操作:明细查询、汇总查询
	 * 方法：
	 * 输入、输出参数均为JSON格式
	 */
	
	@Autowired
	private ICommonDao commonDao;
	
	/*--------------------------
	 * 个人圈存明细
	 */
	@RequestMapping("/transPersonDetail")
	public ModelAndView transPersonDetail(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_transfer_person_detailqry";
		String procParam = "@start_date,@end_date,$dep_serial,$user_lname,$user_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	
	
	/*--------------------------
	 * 个人圈存汇总
	 */
	@RequestMapping("/transPersonCount")
	public ModelAndView transPersonCount(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_transfer_person_countqry";
		String procParam = "@start_date,@end_date,$dep_serial,$user_lname,$user_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	
	
	
	/*--------------------------
	 * 设备圈存明细
	 */
	@RequestMapping("/transDevDetail")
	public ModelAndView transDevDetail(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_transfer_dev_detailqry";
		String procParam = "@start_date,@end_date,$acdep_serial,$bh,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	
	
	
	/*--------------------------
	 * 部门圈存汇总
	 */
	@RequestMapping("/transDepCount")
	public ModelAndView transDepCount(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_transfer_dep_countqry";
		String procParam = "@start_date,@end_date,$dep_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	

}
