package com.garen.finweb.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;

@Controller  
@RequestMapping("/finweb/togeth")  
public final class TogetherAction extends BaseAction {
	/**
	 * fin-页面操作-综合设置
	 * 操作:押金和管理费设置、密码规则设置
	 * 方法：accountfilterQry
	 * 输入、输出参数均为JSON格式
	 */
	@Autowired
	private ICommonDao commonDao;
	/*-------------------------- 
	 * 管理费和押金设置-筛选查询 filterQry (分页)
	 */
	@RequestMapping("/feesit/filterQry")
	public ModelAndView feesitfilterQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_togeth_filterqry";
		String procParam = "$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*
	 * 消费类型
	 */
	@RequestMapping("/feesit/type")
	public ModelAndView devChargeType(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//String sql = "select event_id,event_name from fin_tt_event_lend where event_id in (3,22) order by event_id";
		String sql = "select event_id,event_name from fin_tt_event_lend where event_id in (3,5,107) order by event_id";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	/*-------------------------- 
	 * 管理费和押金设置-新增保存(身份多选)
	 */
	@RequestMapping("/feesit/save")
	public ModelAndView accountSave(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//paramMap.put("lx", 0);
		String procName = "pro_fin_togeth_feesaddm";
		String procParam = "@identity_id,@event_id,$deposit_amt,$fee_rate,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*-------------------------- 
	 * 管理费和押金设置-修改保存
	 */
	@RequestMapping("/feesit/update")
	public ModelAndView accountUpdate(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("lx", 1);
		String procName = "pro_fin_togeth_feesave";
		String procParam = "$id,@identity_id,@event_id,$deposit_amt,$fee_rate,@lx,@gly_no,@ip,#errMsg,#isflg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*-------------------------- 
	 * 管理费和押金设置-删除
	 */
	@RequestMapping("/feesit/del")
	public ModelAndView accountDel(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("lx", 2);
		String procName = "pro_fin_togeth_feesave";
		String procParam = "@id,$identity_id,@event_id,$deposit_amt,$fee_rate,@lx,@gly_no,@ip,#errMsg,#isflg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 *密码规则设置-列表（分页）
	 */
	@RequestMapping("/pwdrules/filterQry")
	public ModelAndView pwdrulesfilterQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_togeth_pwdruleslist";
		String procParam = "$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*
	 *密码规则设置-新增
	 */
	@RequestMapping("/pwdrules/save")
	public ModelAndView pwdrulesSave(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		paramMap.put("lx", 0);
		String procName = "pro_fin_togeth_pwdrules";
		String procParam = "$scheme_id,@scheme_name,@pwd_personal,$bz,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 *密码规则设置-修改
	 */
	@RequestMapping("/pwdrules/update")
	public ModelAndView pwdrulesUpdate(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		paramMap.put("lx", 1);
		String procName = "pro_fin_togeth_pwdrules";
		String procParam = "@scheme_id,@scheme_name,@pwd_personal,$bz,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 *密码规则设置-删除
	 */
	@RequestMapping("/pwdrules/del")
	public ModelAndView pwdrulesDel(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		paramMap.put("lx", 2);
		String procName = "pro_fin_togeth_pwdrules";
		String procParam = "@scheme_id,$scheme_name,$pwd_personal,$bz,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
}
