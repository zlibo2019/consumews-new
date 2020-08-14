package com.garen.finweb.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garen.common.BaseAction;
import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;
import com.garen.utils.MapBuilder;

@Controller  
@RequestMapping("/finweb/rule") 
public final class RuleAction extends BaseAction {
	/**
	 * fin-页面操作-消费规则
	 * 操作:餐别设置、人群设置、默认规则、消费规则、默认场所规则、消费场所规则
	 * 方法：meal、crowd、default、finRule、defaultSite、finRuleSite
	 * 输入、输出参数均为JSON格式
	 */
	
	@Autowired
	private ICommonDao commonDao;
	//******************************************************
	//餐别设置 meal：查询餐别、添加餐别、修改餐别、删除餐别
	//******************************************************	
	/*--------------------------
	 * 餐别设置-查询餐别 qryMeal
	 */
	@RequestMapping("/meal/qryMeal")
	public ModelAndView mealqryMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_meal";
		String procParam = "0,$meal_id,$meal_name,$begin_time,$end_time,$overlap_chk,$gly_no,$ip,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 餐别设置-添加餐别 addMeal
	 */
	@RequestMapping("/meal/addMeal")
	public ModelAndView mealaddMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_meal";
		String procParam = "1,$meal_id,@meal_name,@begin_time,@end_time,@overlap_chk,@gly_no,$ip,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	

	/*--------------------------
	 * 餐别设置-修改餐别 editMeal
	 */
	@RequestMapping("/meal/editMeal")
	public ModelAndView mealeditMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_meal";
		String procParam = "2,@meal_id,@meal_name,@begin_time,@end_time,@overlap_chk,@gly_no,$ip,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 餐别设置-删除餐别 delMeal
	 */
	@RequestMapping("/meal/delMeal")
	public ModelAndView mealdelMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_meal";
		String procParam = "3,@meal_id,$meal_name,$begin_time,$end_time,$overlap_chk,$gly_no,$ip,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	
	
	//******************************************************
	//人群设置 crowd：查询人群、添加人群、修改人群、删除人群、查部门、查身份、按部门筛选身份、按部门身份指定、按人员指定、
	//		按部门身份查询、按人员查询、人员筛选查询、规则关系删除、人员关系删除、规则关系批量变更、人员关系批量变更
	//******************************************************	
	/*--------------------------
	 * 人群设置-查询人群 qryCrowd
	 */
	@RequestMapping("/crowd/qryCrowd")
	public ModelAndView crowdqryCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd";
		String procParam = "0,$crowd_id,$crowd_name,$gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-添加人群 addCrowd
	 */
	@RequestMapping("/crowd/addCrowd")
	public ModelAndView crowdaddCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd";
		String procParam = "1,$crowd_id,@crowd_name,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-修改人群 editCrowd
	 */
	@RequestMapping("/crowd/editCrowd")
	public ModelAndView crowdeditCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd";
		String procParam = "2,@crowd_id,@crowd_name,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-删除人群 delCrowd
	 */
	@RequestMapping("/crowd/delCrowd")
	public ModelAndView crowddelCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd";
		String procParam = "3,@crowd_id,$crowd_name,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-查部门 qryDep--公共
	 */
	
	/*--------------------------
	 * 人群设置-查身份 qryIdentity--公共
	 */
	
	/*--------------------------
	 * 人群设置-按部门筛选身份 depFilterIden
	 */
	@RequestMapping("/crowd/depFilterIden")
	public ModelAndView crowddepFilterIden(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_depFilterIden";
		String procParam = "@dep_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-按部门身份指定 setByDep
	 */
	@RequestMapping("/crowd/setByDep")
	public ModelAndView crowdsetByDep(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_setByDep";
		String procParam = "@crowd_id,@dep_serial,@tt_order,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-按人员指定 setByUser
	 */
	@RequestMapping("/crowd/setByUser")
	public ModelAndView crowdsetByUser(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_setByUser";
		String procParam = "@crowd_id,@user_serial,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 人群设置-按部门身份查询 qryByDep
	 */
	@RequestMapping("/crowd/qryByDep")
	public ModelAndView crowdqryByDep(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_qryByDepOrUser";
		String procParam = "0,@crowd_id,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}	
	
	/*--------------------------
	 * 人群设置-按人员查询 qryByUser
	 */
	@RequestMapping("/crowd/qryByUser")
	public ModelAndView crowdqryByUser(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_qryByDepOrUser";
		String procParam = "1,@crowd_id,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}		
	
	/*--------------------------
	 * 人群设置-人员筛选查询 filterQry
	 */
	@RequestMapping("/crowd/filterQry")
	public ModelAndView crowdfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_filterQry_gly";
		String procParam = "$user_no,$user_lname,$dep_serial,$tt_order,$fuzzy,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-规则关系删除 delRuleRelated
	 */
	@RequestMapping("/crowd/delRuleRelated")
	public ModelAndView crowddelRuleRelated(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_delRuleOrUser";
		String procParam = "0,@rule_id,$user_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-人员关系删除 delUserRelated
	 */
	@RequestMapping("/crowd/delUserRelated")
	public ModelAndView crowddelUserRelated(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_delRuleOrUser";
		String procParam = "1,$rule_id,@user_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 人群设置-规则关系批量变更 chgRuleRelated
	 */	
	@RequestMapping("/crowd/chgRuleRelated")
	public ModelAndView crowdchgRuleRelated(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_chgRuleRelated";
		String procParam = "@dep_serial,@identity_id,@crowd_id,#suc_count,#err_count,#errMsg";
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
	 * 人群设置-人员关系批量变更 chgUserRelated
	 */	
	@RequestMapping("/crowd/chgUserRelated")
	public ModelAndView crowdchgUserRelated(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_crowd_chgUserRelated";
		String procParam = "@user_serial,@crowd_id,#suc_count,#err_count,#errMsg";
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
	//******************************************************
	//默认规则 default:
	//消费规则 finRule：查人群、查餐别、查询规则、新增规则、修改规则、删除规则、查场所、
	//		规则限制查询、规则限制修改、规则指定、规则明细查询、规则明细删除、查时段
	//******************************************************	
	/*--------------------------
	 * 消费规则-查人群 qryCrowd
	 */	
	@RequestMapping("/finRule/qryCrowd")
	public ModelAndView finRuleqryCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String glyNo = paramMap.get("gly_no").toString();
//		String sql = "select crowd_id,crowd_name from fin_crowd_master"; // where crowd_id<>1
        // modify by zlibo 20200724
		String procName = "pro_fin_init_crowd";
		String procParam = "$gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);

		String sql = "select crowd_id,crowd_name from fin_crowd_master where operator = '"+glyNo+"'"; // where crowd_id<>1
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	
	/*--------------------------consumeRule
	 * 消费规则-查餐别 qryMeal
	 */
	@RequestMapping(value={"/default/qryMeal","/finRule/qryMeal"})
	public ModelAndView finRuleqryMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_meal";
		String procParam = "0,$meal_id,$meal_name,$begin_time,$end_time,$overlap_chk,$gly_no,$ip,#errCode,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		int code= Integer.parseInt((String)paramMap.get("errCode"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(code, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费规则-查询规则 qryRule
	 */
	@RequestMapping(value={"/default/qryRule","/finRule/qryRule"})
	public ModelAndView finRuleqryRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule";
		String procParam = "0,$gly_no,@crowd_id,$meal_id,$rule_id,$rule_name,$rule_type," +
				"$limit_amt,$limit_numb,$sub_enable,$sub_type,$sub_amt,$sub_rate," +
				"$sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-新增规则 addRule
	 */
	@RequestMapping(value={"/default/addRule","/finRule/addRule"})
	public ModelAndView finRuleaddRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule";
		String procParam = "1,@gly_no,@crowd_id,@meal_id,$rule_id,@rule_name,@rule_type," +
				"@limit_amt,@limit_numb,@sub_enable,@sub_type,@sub_amt,@sub_rate," +
				"@sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费规则-修改规则 editRule
	 */
	@RequestMapping(value={"/default/editRule","/finRule/editRule"})
	public ModelAndView finRuleditRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule";
		String procParam = "2,@gly_no,$crowd_id,$meal_id,@rule_id,@rule_name,@rule_type," +
				"@limit_amt,@limit_numb,@sub_enable,@sub_type,@sub_amt,@sub_rate," +
				"@sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-删除规则 delRule
	 */
	@RequestMapping(value={"/default/delRule","/finRule/delRule"})
	public ModelAndView finRuledelRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule";
		String procParam = "3,$gly_no,$crowd_id,$meal_id,@rule_id,$rule_name,$rule_type," +
				"$limit_amt,$limit_numb,$sub_enable,$sub_type,$sub_amt,$sub_rate," +
				"$sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-查场所 qrySite
	 */
	@RequestMapping(value={"/default/qrySite","/finRule/qrySite"})
	public ModelAndView finRuleqrySite(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String sql = "select dep_serial,dep_parent,dep_order,dep_name,dep_no,dep_rule from dt_ac_dep";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-规则限制查询 limitQry
	 */
	@RequestMapping(value={"/default/limitQry","/finRule/limitQry"})
	public ModelAndView finRulelimitQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_limit";
		String procParam = "0,$crowd_id,$single_limit,$limit_pwd_enable,$day_limit_amt," +
				"$day_limit_numb,$day_sub_amt,$sub_valid_days,$limit_enable,$limit_amt," +
				"$tallyEn,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费规则-规则限制修改 limitSave
	 */
	@RequestMapping(value={"/default/limitSave","/finRule/limitSave"})
	public ModelAndView finRulelimitSave(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_limit";
		String procParam = "2,@crowd_id,@single_limit,@limit_pwd_enable,@day_limit_amt," +
				"@day_limit_numb,@day_sub_amt,@sub_valid_days,@limit_enable,@limit_amt," +
				"@tallyEn,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费规则-规则指定 ruleSpecified
	 */
	@RequestMapping(value={"/default/ruleSpecified","/finRule/ruleSpecified"})
	public ModelAndView finRuleruleSpecified(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_ruleSpecified";
		String procParam = "@rule_id,@acdep_id,@interval,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-规则明细查询 ruleDetailQry
	 */
	@RequestMapping(value={"/default/ruleDetailQry","/finRule/ruleDetailQry"})
	public ModelAndView finRuleruleDetailQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_ruleDetailQry";
		String procParam = "@crowd_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*--------------------------
	 * 消费规则-规则明细删除 ruleDetailDel
	 */
	@RequestMapping(value={"/default/ruleDetailDel","/finRule/ruleDetailDel"})
	public ModelAndView finRuleruleDetailDel(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_ruleDetailDel";
		String procParam = "@acdep_ruleid,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	

	/*--------------------------
	 * 消费规则-查时段 qryTimeSlot
	 */
	@RequestMapping(value={"/default/qryTimeSlot","/finRule/qryTimeSlot"})
	public ModelAndView finRuleqryTimeSlot(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRule_qryTimeSlot";
		String procParam = "@crowd_id,@meal_id,@rule_id,@acdep_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	//******************************************************
	//默认场所规则defaultSite:
	//消费场所规则 finRuleSite：查人群、查餐别、查时段、查询规则、新增规则、修改规则、删除规则、规则限制查询、规则限制保存
	//******************************************************	
	/*--------------------------
	 * 消费场所规则-查人群 qryCrowd
	 */	
	@RequestMapping("/finRuleSite/qryCrowd")
	public ModelAndView finRuleSiteqryCrowd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String glyNo = paramMap.get("gly_no").toString();
		String sql = "select crowd_id,crowd_name from fin_crowd_master where crowd_id<>1 and operator = '"+glyNo+"'";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费场所规则-查餐别 qryMeal
	 */	
	@RequestMapping("/finRuleSite/qryMeal")//select meal_id,meal_name,begin_time,end_time from fin_meal
	public ModelAndView finRuleSiteqryMeal(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite_qryMealTimeSlot";
		String procParam = "0,@crowd_id,$meal_id,$gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}	
	
	/*--------------------------
	 * 消费场所规则-查时段 qryTimeSlot
	 */	
	@RequestMapping("/finRuleSite/qryTimeSlot")
	public ModelAndView finRuleSiteqryTimeSlot(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite_qryMealTimeSlot";
		String procParam = "1,$crowd_id,@meal_id,$gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	
	/*--------------------------
	 * 消费场所规则-查询规则 qryRule
	 */	
	@RequestMapping(value={"/defaultSite/qryRule","/finRuleSite/qryRule"})
	public ModelAndView finRuleSiteqryRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite";
		String procParam = "0,$gly_no,@crowd_id,$rule_id,$meal_id,$meal_name,$begin_time,$end_time," +
				"$rule_name,$rule_type,$limit_amt,$limit_numb,$sub_enable,$sub_type,$sub_amt,$sub_rate," +
				"$sub_valid_days,$ip,#errMsg";	
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	
	/*--------------------------
	 * 消费场所规则-新增规则 addRule
	 */	
	@RequestMapping(value={"/defaultSite/addRule","/finRuleSite/addRule"})
	public ModelAndView finRuleSiteaddRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite";
		String procParam = "1,@gly_no,@crowd_id,$rule_id,@meal_id,@meal_name,@begin_time,@end_time," +
				"@rule_name,@rule_type,@limit_amt,@limit_numb,@sub_enable,@sub_type,@sub_amt,@sub_rate," +
				"@sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}	
	
	/*--------------------------
	 * 消费场所规则-修改规则 editRule
	 */	
	@RequestMapping(value={"/defaultSite/editRule","/finRuleSite/editRule"})
	public ModelAndView finRuleSiteeditRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite";
		String procParam = "2,@gly_no,@crowd_id,@rule_id,$meal_id,$meal_name,@begin_time,@end_time," +
				"@rule_name,@rule_type,@limit_amt,@limit_numb,@sub_enable,@sub_type,@sub_amt,@sub_rate," +
				"@sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	
	/*--------------------------
	 * 消费场所规则-删除规则 delRule
	 */	
	@RequestMapping(value={"/defaultSite/delRule","/finRuleSite/delRule"})
	public ModelAndView finRuleSitedelRule(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite";
		String procParam = "3,@gly_no,@crowd_id,@rule_id,$meal_id,$meal_name,$begin_time,$end_time," +
				"$rule_name,$rule_type,$limit_amt,$limit_numb,$sub_enable,$sub_type,$sub_amt,$sub_rate," +
				"$sub_valid_days,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);		
	}
	
	/*--------------------------
	 * 消费规则-规则限制查询 limitQry
	 */
	@RequestMapping(value={"/defaultSite/limitQry","/finRuleSite/limitQry"})
	public ModelAndView finRuleSitelimitQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite_limit";
		String procParam = "0,$crowd_id,$single_limit,$limit_pwd_enable,$day_limit_amt," +
				"$day_limit_numb,$day_sub_amt,$sub_valid_days,$limit_enable,$limit_amt," +
				"$tallyEn,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
	/*--------------------------
	 * 消费规则-规则限制修改 limitSave
	 */
	@RequestMapping(value={"/defaultSite/limitSave","/finRuleSite/limitSave"})
	public ModelAndView finRuleSitelimitSave(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_rule_finRuleSite_limit";
		String procParam = "2,@crowd_id,@single_limit,@limit_pwd_enable,@day_limit_amt," +
				"@day_limit_numb,@day_sub_amt,@sub_valid_days,@limit_enable,@limit_amt," +
				"@tallyEn,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	
}
