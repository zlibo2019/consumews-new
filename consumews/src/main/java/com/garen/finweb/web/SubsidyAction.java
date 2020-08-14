package com.garen.finweb.web;

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
@RequestMapping("/finweb/sub") 
public final class SubsidyAction extends BaseAction {
	/**
	 * fin-页面操作-补助管理
	 * 操作:补贴发放、补贴发放纠错、补贴发放查询、导入结果查询
	 * 方法：pay、correct、query、impQuery
	 * 输入、输出参数均为JSON格式
	 */
	
	@Autowired
	private ICommonDao commonDao;
	
	//******************************************************
	//补贴发放 pay：查身份、查部门、查规则、创建规则、修改规则、补贴录入、录入人员查询、补贴导入检测、
	//			补贴导入、发放补贴、查人员名单、删除人员名单、清空人员名单、查新批次号、人员名单模糊查询
	//******************************************************
	/*--------------------------
	 * 补贴发放-查身份、查部门--公共
	 */
	/*--------------------------
	 * 补贴发放-查规则 qryRule
	 */
	@RequestMapping("/pay/qryRule")
	public ModelAndView payqryRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_rule"; //0:查询规则  1:创建规则 2:修改规则
		String procParam = "0,$sub_month,$begin_date,$end_date,$batch_no,$enable_enddate,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}	
	/*--------------------------
	 * 补贴发放-创建规则 addRule
	 */	
	@RequestMapping("/pay/addRule")
	public ModelAndView payaddRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_rule"; //0:查询规则  1:创建规则 2:修改规则
		String procParam = "1,@sub_month,@begin_date,@end_date,@batch_no,@enable_enddate,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放-修改规则 addRule
	 */	
	@RequestMapping("/pay/editRule")
	public ModelAndView payeditRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_rule"; //0:查询规则  1:创建规则 2:修改规则
		String procParam = "2,@sub_month,@begin_date,@end_date,@batch_no,@enable_enddate,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-补贴录入 entrySub
	 */	
	@RequestMapping("/pay/entrySub")
	public ModelAndView payentrySub(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_entrySub";
		String procParam = "@account_id,@sub_amt,@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-录入人员查询 entryUserQry
	 */	
	@RequestMapping("/pay/entryUserQry")
	public ModelAndView payentryUserQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_entryUserQry";
		String procParam = "$dep_serial,$user_duty,@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-补贴导入检测 impSubCheck
	 */	
	@RequestMapping("/pay/impSubCheck")
	public ModelAndView payimpSubCheck(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 参数不能为空");
			return Json(jp);			
		}
		String gly_no = jobj.getString("gly_no");
		JSONArray jarray = getJSONArray(jobj, "data");
		if (jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}	
		Integer operate_code = 0;
		String msg = "";
		 
		//0:取导入批次码
		JsonPage jptmp = new JsonPage();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.clear();
		paramMap.put("operlx", 0);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_impSub";
		String procParam = "@operlx,$gly_no,$xh,$user_no,$user_lname,$sub_amt,$operate_code,#errMsg";
		List<Map<String,Object>> dMaplst = commonDao.callProc(jptmp, procName, procParam, paramMap);		
		msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jptmp.setRetInfo(-1, msg);
			return Json(jptmp);
		}
		if(dMaplst.size() > 0){
			Map<String, Object> dmap = (Map<String,Object>)dMaplst.get(0);
			operate_code = Integer.parseInt(dmap.get("operate_code") + "");	
			dmap = null;
		}
		else{
			operate_code = 0;
		}	
		dMaplst = null;	
		jptmp = null;
		
		//1:导入表
		paramMap.clear();
		for (int i = 0; i < jarray.size(); i++){
			msg = "";
			paramMap = (Map<String,Object>)jarray.get(i);
			//金额转换： 元转换分
			String sub_amt = paramMap.get("sub_amt") + "";	
			if(sub_amt.indexOf(".") < 1 || sub_amt.length() - sub_amt.indexOf(".") - 1 != 2){ 
				msg = "补贴金额格式不正确，请按照模版格式要求输入";
				break;
			}
			sub_amt = sub_amt.replace(".", "");
			
			//log.debug(paramMap);
			Map<String, Object> inparamMap = new HashMap<String, Object>();
			inparamMap.put("operlx", 1);
			inparamMap.put("gly_no", gly_no);
			inparamMap.put("xh", Integer.parseInt((String)paramMap.get("xh")));
			inparamMap.put("user_no", paramMap.get("user_no") + "");
			inparamMap.put("user_lname", paramMap.get("user_lname") + "");
			inparamMap.put("sub_amt", Integer.parseInt(sub_amt));
			inparamMap.put("operate_code", operate_code);
			//log.debug(inparamMap);
			procParam = "@operlx,@gly_no,$xh,$user_no,$user_lname,$sub_amt,$operate_code,#errMsg";
			commonDao.callProc(jp, procName, procParam, inparamMap);
			msg = (String)paramMap.get("errMsg");
			if(StringUtils.isNotEmpty(msg)){
				break;
			}
		}
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
			return Json(jp);
		}		
		
		//2:检验导入明细
		paramMap.clear();
		paramMap.put("operlx", 2);
		paramMap.put("gly_no", gly_no);
		paramMap.put("operate_code", operate_code);
		procParam = "@operlx,@gly_no,$xh,$user_no,$user_lname,$sub_amt,$operate_code,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);		
		msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		else{
			Map<String, Object> retMap = new MapBuilder()
					.put("operate_code", operate_code)
					.build();
			jp.setRetData(retMap);			
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-补贴导入 impSub
	 */	
	@RequestMapping("/pay/impSub")
	public ModelAndView payimpSub(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_impSub";
		String procParam = "3,@gly_no,$xh,$user_no,$user_lname,$sub_amt,@operate_code,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放-发放补贴 paySub
	 */	
	@RequestMapping("/pay/paySub")
	public ModelAndView paypaySub(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_paySub";
		String procParam = "@sub_month,@batch_no,@gly_no,$ip,#count,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("count", paramMap.get("count"));
		tmpMap.put("subamts", paramMap.get("subamts"));
		jp.setRetData(tmpMap);	
		return Json(jp);
		
//	--原处理逻辑 20160817因分页传参数值 修改屏蔽 改为从DB获取具体数据 		
//		JSONObject jobj = parseJson(jp);
//		String gly_no = jobj.getString("gly_no");
//		String ip = jobj.getString("ip");
//		JSONArray jarray = getJSONArray(jobj, "data");
//		if (jarray.size()<1){
//			jp.setRetInfo(-1, "未检测到data具体参数信息");
//			return Json(jp);
//		}
//		for (int i = 0; i < jarray.size(); i++){
//			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
//			if(!paramMap.containsKey("account_id") || !paramMap.containsKey("sub_amt")){ 
//				jp.setRetInfo(-1, "账号或补贴金额部分参数缺失");
//				return Json(jp);
//			}		
//		}
//		
//		//获取bill_id
//		String sql = "select bill_id from fin_sub_master where operator=:gly_no and sub_state=0";
//		Map<String, Object> tmpMap = new HashMap<String, Object>();
//		tmpMap.put("gly_no", gly_no);
//		Integer bill_id = commonDao.queryForInt(sql, tmpMap);
//		//更新补贴主表 状态为  1 记账
//		sql = "update fin_sub_master set sub_state=1 where bill_id=:bill_id";
//		tmpMap.clear();
//		tmpMap.put("bill_id", bill_id);
//		commonDao.update(sql, tmpMap);
//		
//		
//		Integer accessCnt = 0;
//		Integer sumSub = 0;
//		String procName = "pro_fin_sub_pay_paySub";
//		String procParam = "@bill_id,@account_id,@sub_amt,@gly_no,$ip,#errMsg";
//		JSONArray rstArry = new JSONArray();
//		for (int i = 0; i < jarray.size(); i++){
//			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
//			JSONObject obj = new JSONObject(); 
//			
//			Integer account_id = Integer.parseInt(paramMap.get("account_id").toString()); 
//			Integer sub_amt = Integer.parseInt(paramMap.get("sub_amt").toString()); 
//
//			if(sub_amt == 0){
//				accessCnt++;
//				sumSub += sub_amt;
//				continue;
//			}	
//			tmpMap.clear();
//			tmpMap.put("bill_id", bill_id);
//			tmpMap.put("account_id", account_id);
//			tmpMap.put("sub_amt", sub_amt);
//			tmpMap.put("gly_no", gly_no);
//			tmpMap.put("ip", ip);
//			log.debug(tmpMap);
//			commonDao.callProc(jp, procName, procParam, tmpMap);	
//			String msg = (String)tmpMap.get("errMsg");
//			log.debug("errMsg:" + msg);
//			if(StringUtils.isNotEmpty(msg)){
//				obj.put("account_id", account_id);
//				obj.put("message", msg);
//				rstArry.add(obj);
//			}
//			else{
//				accessCnt++;
//				sumSub += sub_amt;
//			}
//		}
//		//返回错误记录及信息
//		if(rstArry.size() > 0){
//			jp.setRecord(rstArry);
//		}	
//		//返回成功汇总
//		tmpMap.clear();
//		tmpMap.put("count", accessCnt);
//		tmpMap.put("subamts", sumSub);
//		jp.setRetData(tmpMap);
//		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-查人员名单 qryUserList
	 */
	@RequestMapping("/pay/qryUserList")
	public ModelAndView payqryUserList(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_qryUserList";
		String procParam = "@gly_no,$page_no,$page_size,#total,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("subamts", paramMap.get("subamts"))
				.build();
		jp.setRetData(retMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放-删除人员名单 delUserList
	 */	
	@RequestMapping("/pay/delUserList")
	public ModelAndView paydelUserList(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String sql = "delete from s from fin_sub_slave s" +
//				" inner join fin_sub_master m on m.bill_id=s.bill_id and m.sub_state=0 and m.operator=:gly_no" +
//				" inner join fin_account a on s.account_id=a.account_id " + 
//				" inner join dt_user u on a.user_serial=u.user_serial" +
//				" where u.user_serial in (:user_serial)";
//		paramMap.put("user_serial", LangUtils.str2IntList(paramMap.get("user_serial").toString()));
//		commonDao.update(sql, paramMap);
//		return Json(jp);
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_delUserList"; 
		String procParam = "@user_serial,@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	/*--------------------------
	 * 补贴发放-清空人员名单 clearUserList
	 */	
	@RequestMapping("/pay/clearUserList")
	public ModelAndView payclearUserList(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_clearUserList"; 
		String procParam = "@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-查新批次号 qryBatch
	 */	
	@RequestMapping("/pay/qryBatch")
	public ModelAndView payqryBatch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_qryBatch"; 
		String procParam = "@sub_month,@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 补贴发放-人员名单模糊查询 userListFilter
	 */
	@RequestMapping("/pay/userListFilter")
	public ModelAndView payuserListFilter(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_pay_userListFilter";
		String procParam = "@gly_no,@filter,$page_no,$page_size,#total,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("subamts", paramMap.get("subamts"))
				.build();
		jp.setRetData(retMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	//******************************************************
	//补贴发放纠错 correct：筛选查询、发放纠错
	//******************************************************
	/*--------------------------
	 * 补贴发放纠错-筛选查询 filterQry
	 */	
	@RequestMapping("/correct/filterQry")
	public ModelAndView correctfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_correct_filterQry";
		String procParam = "$gly_no,$sub_month_begin,$sub_month_end,$batch_no,"+
				"$undo_state,$fuzzy,$page_no,$page_size,#total,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("subamts", paramMap.get("subamts"))
				.build();
		jp.setRetData(retMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放纠错-发放纠错 correct
	 */	
	@RequestMapping("/correct/correct")
	public ModelAndView correctcorrect(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_correct_correct";
		String procParam = "@gly_no,@slave_ids,$begin_date,$end_date,$sub_amt,$ip,"+
				"#suc_count,#err_count,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("suc_count", paramMap.get("suc_count"))
				.put("err_count", paramMap.get("err_count"))
				.build();
		jp.setRetData(retMap);			
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放纠错-查补贴记录类型 qryState
	 */
	@RequestMapping("/correct/qryState")
	public ModelAndView correctqryState(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String procName = "pro_fin_sub_correct_qryState";
		String procParam = "#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放纠错-查批次号 qryBatch
	 */	
	@RequestMapping("/correct/qryBatch")
	public ModelAndView correctqryBatch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_correct_qryBatch"; 
		String procParam = "@sub_month_begin,@sub_month_end,$gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//补贴发放查询 query：查管理员、查部门、查补贴记录类型、按批次查询、按人员查询
	//******************************************************
	/*--------------------------
	 * 补贴发放查询-查管理员、查部门  -- 公共
	 */

	/*--------------------------
	 * 补贴发放查询-查补贴记录类型 qryState
	 */
	@RequestMapping("/query/qryState")
	public ModelAndView queryqryState(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String procName = "pro_fin_sub_query_qryState";
		String procParam = "#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 补贴发放查询-按批次查询 batchQry
	 */
	@RequestMapping("/query/batchQry")
	public ModelAndView querybatchQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		//log.debug(paramMap);
		String procName = "pro_fin_sub_query_batchQry";
		String procParam = "@begin_month,@end_month,$gly_no,$batch_no,$record_type,"+
				"$fuzzy,$page_no,$page_size,#total,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("subamts", paramMap.get("subamts"))
				.build();
		jp.setRetData(retMap);	
		//jp.setTotal(Integer.parseInt((String)paramMap.get("totals")));
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}		
	/*--------------------------
	 * 补贴发放查询-按人员查询 userQry
	 */	
	@RequestMapping("/query/userQry")
	public ModelAndView queryuserQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		//log.debug(paramMap);
		String procName = "pro_fin_sub_query_userQry";
		String procParam = "@begin_month,@end_month,$user_lname,$user_no,$dep_serial,"+
				"$record_type,$page_no,$page_size,#total,#subamts,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("subamts", paramMap.get("subamts"))
				.build();
		jp.setRetData(retMap);
		//jp.setTotal(Integer.parseInt((String)paramMap.get("totals")));
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//导入结果查询 impQuery：查管理员、查导入状态、筛选查询
	//******************************************************
	/*--------------------------
	 * 导入结果查询-查管理员  -- 公共
	 */
	
	/*--------------------------
	 * 导入结果查询-查导入状态 qryState
	 */	
	@RequestMapping("/impQuery/qryState")
	public ModelAndView impQueryqryState(JsonPage jp) {	
		Map<String, Object> paramMap = parseJsonMap(jp);
		//log.debug(paramMap);
		String procName = "pro_fin_sub_impQuery_qryState";
		String procParam = "#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	/*--------------------------
	 * 导入结果查询-筛选查询 filterQry
	 */	
	@RequestMapping("/impQuery/filterQry")
	public ModelAndView impQueryfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		//log.debug(paramMap);
		String procName = "pro_fin_sub_impQuery_filterQry";
		String procParam = "$sub_month_begin,$sub_month_end,$gly_no,$batch_no,"+
				"$stateCode,$fuzzy,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 导入结果查询-查批次号 qryBatch
	 */	
	@RequestMapping("/impQuery/qryBatch")
	public ModelAndView impQueryqryBatch(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_sub_impQuery_qryBatch"; 
		String procParam = "@sub_month_begin,@sub_month_end,$gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
}
