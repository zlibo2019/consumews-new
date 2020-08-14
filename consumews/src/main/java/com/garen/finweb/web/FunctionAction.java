package com.garen.finweb.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;
import com.garen.utils.LangUtils;
import com.garen.utils.MapBuilder;
@Controller  
@RequestMapping("/finweb/func")  
public final class FunctionAction extends BaseAction {
	/**
	 * fin-页面操作-功能查询
	 * 操作:持卡人账户查询、持卡人明细账、补贴批次账户查询
	 * 方法：accountfilterQry
	 * 输入、输出参数均为JSON格式
	 */
	@Autowired
	private ICommonDao commonDao;
	
	//acc=====================================================================================
	/*-------------------------- 
	 * 持卡人账户查询-在职状态 qryUserType
	 */	
	@RequestMapping("/acc/qryUserType")
	public ModelAndView accqryUserType(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 1 as type,'在职' as name union select 2 as type,'离职' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}	
	/*-------------------------- 
	 * 持卡人账户查询-押金状态 qryDpstState
	 */	
	@RequestMapping("/acc/qryDpstState")
	public ModelAndView accqryDpstState(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 1 as type,'有押金' as name union select 2 as type,'无押金' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}	
	/*-------------------------- 
	 * 持卡人账户查询-账户状态 qryAccState
	 */	
	@RequestMapping("/acc/qryAccState")
	public ModelAndView accqryAccState(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 0 as type,'正常' as name union select 1 as type,'冻结' as name "+
				" union select 2 as type,'销户' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}	
	/*-------------------------- 
	 * 持卡人账户查询-账户余额 qryAccAmt
	 */	
	@RequestMapping("/acc/qryAccAmt")
	public ModelAndView accqryAccAmt(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 1 as type,'有余额' as name union select 2 as type,'无余额' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}		
	
	/*-------------------------- 
	 * 持卡人账户查询-筛选查询 filterQry (分页)
	 */
	@RequestMapping("/acc/filterQry")
	public ModelAndView accountfilterQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		// modify by zlibo 20201723
		String procName = "pro_fin_func_acount_filterqry_gly";
		String procParam = "@cx_type,$user_lname,$user_no,$dep_serial,$identity_id,$account_state,"+
				"$usertype_state,$deposit_state,$acc_amt_state,"+
				"$page_no,$page_size,#cash_total,#sub_total,#small_total,#deposit_total,#total,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("cash_total", paramMap.get("cash_total"))
				.put("sub_total", paramMap.get("sub_total"))
				.put("small_total", paramMap.get("small_total"))
				.put("deposit_total", paramMap.get("deposit_total"))
				.build();
		jp.setRetData(retMap);	
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}			
		return Json(jp);
	}
	
	
	//trad=====================================================================================	 
	/*--------------------------
	 * 持卡人明细账-筛选查询 filterQry
	 */
	@RequestMapping("/trad/userfilterQry")
	public ModelAndView userfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_traduser_filterqry";
		String procParam = "$user_no,$dep_serial,$user_lname,$user_id,$gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 持卡人明细账-单个人 (分表分页)
	 */
	@RequestMapping("/trad/filterQry")
	public ModelAndView tradfilterQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_trad_filterqry";
		String procParam = "@cx_type,@user_serial,@start_date,@end_date,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 交易流水查询 (分表分页)
	 */
	@RequestMapping("/trad/flowQry")
	public ModelAndView tradflowQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_tradflow_filterqry_gly";
		String procParam = "@cx_type,@start_date,@end_date,$user_no,$user_lname,$dep_serial,"+
				"$user_id,$merchant_dep,$merchant_account,$acdep_serial,$bh,$trad_type,"+
				"$page_no,$page_size,#total,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}
	/*
	 * 交易类型展示
	 */
	@RequestMapping("/trad/tradType")
	public ModelAndView tradType(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select trad_type_id as trad_type,trad_type_name from fin_tt_trad_type";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 消费明细查询 (分表分页)
	 */
	@RequestMapping("/trad/consume")
	public ModelAndView tradConsumeQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_tradconsume_filterqry";
		String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,$bh,@glyId,"+
				"$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}	
	
	
	
	
	
	//sub、merch、user、finCorrect、daily =================================================== 	
	/*--------------------------
	 * 补贴批次账户查询-筛选查询 filterQry 
	 */
	@RequestMapping("/sub/userfilterQry")
	public ModelAndView subUserfilterQry(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String procName = "pro_fin_func_subuser_filterqry";
//		String procParam = "$user_no,$dep_serial,$user_lname,$user_id,$gly_no,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		String msg = (String)paramMap.get("errMsg");
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_subuser_filterqry";
		String procParam = "$user_no,$dep_serial,$user_lname,$user_id,$gly_no,$page_no,$page_size,"+
				"#sub_total,#sub_zero_total,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("sub_total", paramMap.get("sub_total"))
				.put("sub_zero_total", paramMap.get("sub_zero_total"))
				.build();
		jp.setRetData(retMap);		
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);		
	}
	/*
	 * 补贴批次账户查询 (分页)
	 */
//	@RequestMapping("/sub/filterQry")
//	public ModelAndView subfilterQry(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		
//		/*String sql = "select a.account_id,"
//		+"case a.sub_type_id when 1 then '手动' when 2 then '日补' when 3 then '免费餐' when 4 then '固定餐补' when 5 then '比率餐补' else '' end as sub_type,"
//				+"a.sub_begin_date,a.sub_end_date,a.sub_amt,a.sub_zero_amt, case a.is_bill when 0 then '未记账' when 1 then '已记账' else '' end as bill"+
//		" from fin_sub_account a inner join fin_account b on a.account_id=b.account_id where b.user_serial=:user_serial";
//		commonDao.queryForList(jp, sql, paramMap);*/
//		String procName = "pro_fin_func_sub_filterqry";
//		String procParam = "@user_serial,$page_no,$page_size,#total,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		return Json(jp);
//	}
		
	/*
	 * 查商户列表
	 */
	@RequestMapping("/merch/merchQry")
	public ModelAndView merchfilterQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_fun_merch_filterqry";
		String procParam = "$merchant_account_id,$merchant_name,$dep_serial"; //,#total
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*
	 * 商户交易明细 (分表分页)---------20170227取消----------------------------------------------
	 */
	@RequestMapping("/merch/tradQry")
	public ModelAndView merchTradQry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		/*String sql = "select (select mc from ST_Device where dev_logic_bh=a.device_id) device_id, "
		+" (select meal_name from fin_meal where meal_id=a.meal_id)meal_id,pos_flow,"
		+" (select trad_type_name from fin_tt_trad_type where trad_type_id=a.trad_type_id) trad_type_id,"
		+" (select event_name from fin_tt_event_lend where event_id=a.event_id) as event_name,"
		+" trad_amt,trad_sj,sj,bill_date from fin_trad_flow a where merchant_account_id=:merchant_account_id"
		+" order by a.device_id,trad_sj";
		commonDao.queryForList(jp, sql, paramMap);*/
		String procName = "pro_fin_func_merchtrad_filterqry";
		String procParam = "@merchant_account_id,@start_date,@end_date,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}

	
	/*
	 * 上班人员查询(查考勤报表)
	 */
	//工号、姓名、部门、身份、人群、签到时间、设备
	//条件：开始日期（时间）、结束日期（时间）、人群
	@RequestMapping("/user/workUser")
	public ModelAndView userOnWork(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_user_workUser";
		String procParam = "@start_date,@end_date,$crowd_id,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);		
				
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		//商户条件
//		String condition = "";
//		if(StringUtils.isNotEmpty((String)paramMap.get("crowd_id"))){
//			List<Integer> crowdIdList=LangUtils.str2IntList(paramMap.get("crowd_id").toString());
//			paramMap.put("crowdIdList", crowdIdList);
//			condition = " where b.crowd_id in (:crowdIdList) ";
//		}
//		
//		String sql = " select bb.user_no,bb.user_lname,bb.user_depname,cc.tt_name,isnull(dd.crowd_name,'默认人群') crowd_name,aa.sj"
//				+ " from (select a.user_serial,min(a.sj) sj"
//				+ " from (select user_serial,sj,dev_serial from kt_jl"
//				+ " where sj between :start_date and :end_date) a"
//				+ " group by a.user_serial) aa"
//				+ " left join dt_user bb on aa.user_serial=bb.user_serial"
//				+ " left join tt_identity cc on bb.user_identity = cc.tt_order"
//				+ " left join fin_crowd_detail b on aa.user_serial=b.user_serial" 
//				+ " left join fin_crowd_master dd on b.crowd_id=dd.crowd_id"
//				+ condition;
//				
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);
	}
	
	/*
	 * 消费纠错明细查询 (分表分页)
	 */
	@RequestMapping("/finCorrect/qry")
	public ModelAndView finCorrectqry(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_finCorrect_qry";
		String procParam = "@start_date,@end_date,$merchant_account,$device_bh,$page_no,$page_size,"+
				"#correct_total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("correct_total", paramMap.get("correct_total"))
				.build();
		jp.setRetData(retMap);		
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}		
		return Json(jp);
	}	

	/*
	 * 日结日志查询
	 */
	@RequestMapping("/daily/qryLog")
	public ModelAndView dailyqryLog(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_func_daily_qrylog";
		String procParam = "@record_cnt,$page_no,$page_size,#current_day,#next_day,#is_auto_bill,#auto_bill_time,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		Map<String, Object> retMap = new MapBuilder()
				.put("current_day", paramMap.get("current_day"))
				.put("next_day", paramMap.get("next_day"))
				.put("is_auto_bill", paramMap.get("is_auto_bill"))
				.put("auto_bill_time", paramMap.get("auto_bill_time"))
				.build();
		jp.setRetData(retMap);			
		return Json(jp);
	}		
}
