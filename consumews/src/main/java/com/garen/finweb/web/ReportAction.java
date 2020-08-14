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

@Controller
@RequestMapping("/finweb/report")
public final class ReportAction extends BaseAction {
    /**
     * fin-页面操作-报表查询
     * 操作:核算单位、pos、商户、商户部门、操作员、资产、科目、报表设置、系统、部门、个人、圈存、系统收支
     * 方法：unit、pos、merchant、merchantdep、operator、finance、subject、templet、
     * sys、real、dep、personCount、sysTotal
     * 输入、输出参数均为JSON格式
     */
    @Autowired
    private ICommonDao commonDao;

    //unit=====================================================================================
    /*
     * 核算单位收支统计表 pro_fin_report_unit_payment_filterqry
     */
    @RequestMapping("/unit/payment")
    public ModelAndView unitPaymentFilterQry(JsonPage jp) {
		/*Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select (select  unit_name from fin_account_unit  a where a.unit_id=unit_id) unit_id,"
		+" sum(cast(begin_amt as bigint)) begin_amt,sum(cast(sub_amt as bigint)) sub_amt,sum(cast(recharge_amt as bigint)) recharge_amt,"
		+" sum(cast(other_income as bigint)) other_income,sum(cast(xf_amt as bigint)) xf_amt,sum(cast(refund_amt as bigint)) refund_amt,sum(cast(other_pay as bigint)) other_pay,"
		+" sum(cast(end_amt as bigint)) end_amt from fin_report_cardholer_payment"
		+" where bill_date between :start_date and :end_date group by unit_id";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);*/
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_unit_payment_filterqry";
        String procParam = "@start_date,@end_date,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);

    }

    //pos=====================================================================================
    /*
     * pos营业日报 pro_fin_report_pos_daily
     */
    @RequestMapping("/pos/daily")
    public ModelAndView posDailyFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_pos_daily";
        String procParam = "@start_date,@end_date,$merchant_account_id,$bh,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * pos营业汇总 pro_fin_report_pos_count
     */
    @RequestMapping("/pos/count")
    public ModelAndView posCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_pos_count";
        String procParam = "@start_date,@end_date,$merchant_account_id,$bh,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * pos餐别汇总报表   pro_fin_report_pos_mealcount
     */
    @RequestMapping("/pos/mealcount")
    public ModelAndView posMealCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_pos_mealcount";
        String procParam = "@start_date,@end_date,$merchant_account_id,$meal,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //merchant=====================================================================================
    /*
     * 商户营业日报 pro_fin_report_merchant_daily
     */
    @RequestMapping("/merchant/daily")
    public ModelAndView merchantDailyFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_daily";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户营业汇总 pro_fin_report_merchant_count
     */
    @RequestMapping("/merchant/count")
    public ModelAndView merchantCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_count";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户部门营业汇总  pro_fin_report_merchant_depcount
     */
    @RequestMapping("/merchant/depcount")
    public ModelAndView merchantDepCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_depcount";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户餐别汇总报表   pro_fin_report_merchant_mealcount
     */
    @RequestMapping("/merchant/mealcount")
    public ModelAndView merchantMealCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_mealcount";
        String procParam = "@start_date,@end_date,$merchant_account_id,$meal,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户清算明细表  pro_fin_report_merchant_clearmx
     */
    @RequestMapping("/merchant/clear")
    public ModelAndView merchantClearFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_clearmx";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户清算汇总表  pro_fin_report_merchant_clearcount
     */
    @RequestMapping("/merchant/clearCount")
    public ModelAndView merchantClearCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_clearcount";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户部门清算汇总表  pro_fin_report_merchant_clearDepCount
     */
    @RequestMapping("/merchant/clearDepCount")
    public ModelAndView merchantClearDepCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchant_clearDepCount";
        String procParam = "@start_date,@end_date,$dep_serial,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户列表 -- 通过部门查询而来pro_fin_report_condition_merchantByDep_query
     */
    @RequestMapping("/merchant/listbyDep")
    public ModelAndView queryMerchByDepJson(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_condition_merchantByDep_query";
        String procParam = "$dep_serial,@gly_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 商户列表 pro_fin_report_condition_merchant_query
     */
    @RequestMapping("/merchant/list")
    public ModelAndView queryMerchJson(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_condition_merchant_query";
        String procParam = "@gly_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //merchantdep=====================================================================================
    /*
     * 商户部门餐别汇总报表   pro_fin_report_merchantdep_mealcount
     */
    @RequestMapping("/merchantdep/mealcount")
    public ModelAndView merchantDepMealCountFilterQry(JsonPage jp) {
		/*Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String depsql = " and 1=1 ";
		if(StringUtils.isNotEmpty((String)paramMap.get("dep_serial"))){
			List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
			paramMap.put("depSerialList", depSerialList);
			depsql = " and merchant_dep in (:depSerialList) ";
		}
		String mealsql = " and 1=1";
		if(StringUtils.isNotEmpty((String)paramMap.get("meal"))){
			List<Integer> mealList=LangUtils.str2IntList(paramMap.get("meal").toString());
			paramMap.put("mealList", mealList);
			mealsql = " and meal_id in (:mealList) ";
		}
		String sql = "select c.dep_name,b.merchant_name,a.merchant_dep,a.merchant_account_id,(select meal_name from fin_meal where meal_id=a.meal_id) meal_name,"
				+ " a.trad_people,a.count_numb,a.trad_amt,a.trad_cash_amt,a.trad_sub_amt"
				+ " from (select merchant_dep,merchant_account_id, meal_id,"
				+ " sum(trad_people) trad_people,sum(count_numb) count_numb,"
				+ " sum(trad_amt) trad_amt,sum(trad_cash_amt) trad_cash_amt,sum(trad_sub_amt) trad_sub_amt"
				+ " from fin_report_meal_count" 
				+ " where bill_date between  :start_date and :end_date "+ depsql + mealsql
				+ " group by merchant_dep,merchant_account_id,meal_id ) a" 
				+ " left join fin_merchant_account b on a.merchant_account_id=b.merchant_account_id"
				+ " left join fin_merchant_dep c on a.merchant_dep= c.dep_serial"
				+ " order by a.merchant_dep,a.merchant_account_id,a.meal_id";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);*/
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_merchantdep_mealcount";
        String procParam = "@start_date,@end_date,$dep_serial,$meal,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //////////////////权限需要的部门、商户和设备列表///////////////////
    /*--------------------------
     * 商户部门列表 pro_fin_report_condition_merchantDep_query
     */
    @RequestMapping("/merchantDep/list")
    public ModelAndView queryMerchDepJson(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_condition_merchantDep_query";
        String procParam = "@gly_no,@lx,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //operator=====================================================================================
    /*
     * 操作员收支明细表   payment-code  20170518修改
     */
    @RequestMapping("/operator/payment")
    public ModelAndView operatorPaymentFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_operator_payment_qry";
        String procParam = "@start_date,@end_date,$operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员收支汇总表  paymentcount-code
     */
    @RequestMapping("/operator/paymentcount")
    public ModelAndView operatorPaymentCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_paymentcount";
        String procParam = "@start_date,@end_date,$cond_gly,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员收支日报表   code-paymentdaily
     */
    @RequestMapping("/operator/paymentdaily")
    public ModelAndView operatorPaymentDailyFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_paymentdaily";
        String procParam = "@start_date,@end_date,$operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员操作明细  pro_fin_report_operator_operateDetailQry
     * 20170601添加
     */
    @RequestMapping("/operator/operateDetail")
    public ModelAndView operateDetail(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_operateDetailQry";
        String procParam = "@start_date,@end_date,@operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员操作汇总表   count-code
     */
    @RequestMapping("/operator/count")
    public ModelAndView operatorCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_count";
        String procParam = "@start_date,@end_date,$operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员操作日报表   daily-code
     */
    @RequestMapping("/operator/daily")
    public ModelAndView operatorDailyFilterQry(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String sql = "select a.bill_date,b.event_name,a.occur_amt,a.occur_numb from (select bill_date,event_id,sum(occur_numb) occur_numb, sum(occur_amt) occur_amt from fin_report_operator"
//				+ " where bill_date between :start_date and :end_date and operator=:operator"
//				+ " group by bill_date,event_id ) a left join fin_tt_event_lend b on a.event_id = b.event_id"
//				+ " order by a.bill_date,a.event_id";
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);

        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_daily";
        String procParam = "@start_date,@end_date,$operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员销户明细   pro_fin_report_operator_destroy-------20170505取消了不展示--------
     */
    @RequestMapping("/operator/destroy")
    public ModelAndView operatorDestroyFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_destroy";
        String procParam = "@start_date,@end_date,$operator,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 操作员销户汇总   pro_fin_report_operator_destroyCount-------20170505取消了不展示--------
     */
    @RequestMapping("/operator/destroyCount")
    public ModelAndView operatordestroyCount(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_operator_destroyCount";
        String procParam = "@start_date,@end_date,$operator,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 押金流水查询  pro_fin_report_depositQry
     * 20170605添加
     */
    @RequestMapping("/operator/depositQry")
    public ModelAndView depositQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_depositQry";
        String procParam = "@start_date,@end_date,$operator,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //finance=====================================================================================
    /*
     * 资产负债表  code-balance
     */
    @RequestMapping("/finance/balance")
    public ModelAndView balanceFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_balance";
        String procParam = "@start_date,@end_date,#lx,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        //StringUtils.isEmpty(cs)
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
            return Json(jp);
        }

        String sql = "select a.subject_no,b.subject_name,b.subject_type,begin_amt,end_amt"
                + " from fin_balance a inner join fin_tt_subject b on a.subject_no=b.subject_no and b.subject_type in (1,2,3) "
                //+ " where a.bill_date=:start_date"
                + " order by b.subject_type";
        commonDao.queryForList(jp, sql, paramMap);

        return Json(jp);
    }

    /*
     * 总账表 code-ledger
     */
    @RequestMapping("/finance/ledger")
    public ModelAndView getLeder(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_finance_ledger";
        String procParam = "@start_date,@end_date,$glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //subject=====================================================================================
    /*
     * 科目日报表  pro_fin_report_subject_daily_filterqry
     */
    @RequestMapping("/subject/daily")
    public ModelAndView subjectDailyFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_subject_daily_filterqry";
        String procParam = "@end_date,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 科目汇总表  pro_fin_report_subject_count_filterqry------20170227整改 取消此功能-----------
     */
    @RequestMapping("/subject/dailyCount")
    public ModelAndView subjectDailyCountFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_subject_count_filterqry";
        String procParam = "@start_date,@end_date,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 科目记账明细  pro_fin_report_subject_accDetails
     */
    @RequestMapping("/subject/accDetails")
    public ModelAndView subjectaccDetails(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_subject_accDetails";
        String procParam = "@start_date,@end_date,@subject_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //templet=====================================================================================
    /*
     * 报表设置页面-报表模板列表pro_fin_report_templet_filterqry
     */
    @RequestMapping("/templet/templetList")
    public ModelAndView templetFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String procName = "pro_fin_report_templet_filterqry";
        String procParam = "$page_no,$page_size,#total,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 报表设置页面-查询单个报表code-get
     */
    @RequestMapping("/templet/get")
    public ModelAndView templet(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String sql = "select id,report_name,report_content,report_title,bz,sj,client,operator from fin_report_templet"
                + " where report_name=:report_name";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 报表设置页面-报表模板新增pro_fin_report_templet[0]
     */
    @RequestMapping("/templet/save")
    public ModelAndView templetSave(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        paramMap.put("lx", 0);
        String procName = "pro_fin_report_templet";
        String procParam = "$id,@report_name,$report_content,$report_title,$bz,@lx,@gly_no,@ip,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 报表设置页面-报表模板修改pro_fin_report_templet[1]
     */
    @RequestMapping("/templet/update")
    public ModelAndView templetUpdate(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        paramMap.put("lx", 1);
        String procName = "pro_fin_report_templet";
        String procParam = "@id,@report_name,$report_content,$report_title,$bz,@lx,@gly_no,@ip,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 报表设置页面-报表模板删除pro_fin_report_templet[2]
     */
    @RequestMapping("/templet/del")
    public ModelAndView templetDel(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        paramMap.put("lx", 2);
        String procName = "pro_fin_report_templet";
        String procParam = "@id,$report_name,$report_content,$report_title,$bz,@lx,@gly_no,@ip,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //sys=====================================================================================
    /*
     * 获取当前系统的开始时间和结束时间code-billdate
     */
    @RequestMapping("/sys/billdate")
    public ModelAndView getLastBillDate(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select convert(char(10),dateadd(month, datediff(month, 0, isnull(last_bill_date,GETDATE()-1)), 0),120) as start_date,"
                + "convert(char(10),isnull(last_bill_date,GETDATE()-1),120) as end_date,"
                + "convert(char(10),dateadd(month, datediff(month, 0, GETDATE()), 0),120) as start_date2,"
                + "convert(char(10),GETDATE(),120) as end_date2 from fin_system_set ";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 获取当前系统的公司名称code-getUnit
     */
    @RequestMapping("/sys/getUnit")
    public ModelAndView getUnitName(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select reg_unit from wt_reg";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 获取当前系统的打印时间code-printTime
     */
    @RequestMapping("/sys/printTime")
    public ModelAndView getPrintTime(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select convert(varchar,GETDATE(),120) as print_time";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 获取操作员的实际姓名code-operatorName
     */
    @RequestMapping("/sys/operatorName")
    public ModelAndView getOperatorName(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select isnull(gly_lname,'') gly_lname from wt_gly where Gly_no=:operator";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 报表专用场所code-qryPlace
     */
    @RequestMapping("/sys/qryPlace")
    public ModelAndView baseqryPlace(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select dep_serial,dep_parent,dep_order,dep_name,dep_no,dep_rule from dt_ac_dep_view"; //  where unit_id=:unit_id
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 报表专用设备code-devlist
     */
    @RequestMapping("/sys/devlist")
    public ModelAndView devQryList(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        List<Integer> depSerialList = LangUtils.str2IntList(paramMap.get("dep_serial").toString());
        paramMap.put("depSerialList", depSerialList);
        log.debug(paramMap);
        String sql = "select a.bh,a.mc from st_device a inner join dt_dev_view c on a.bh=c.dev_serial " +
                " where (a.dev_lb=13 or (a.dlx=2 and dev_lb=99)) and c.acdep_serial in (:depSerialList)";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    //real=====================================================================================
    /*
     * 20161219
     * pos实时营业报表、商户营业实时报表、pos餐别实时营业报表、商户餐别实时营业报表
     * 部门补贴收支汇总报表、部门商户计次营业汇总报表
     */
    /*
     * pos实时营业报表code-pos
     * 条件：商户单选，pos列表多选（关联查询）
     */
    @RequestMapping("/real/pos")
    public ModelAndView posRealReport(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        //商户条件
        String mernsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("merchant_account_id"))) {
            List<Integer> accountIdList = LangUtils.str2IntList(paramMap.get("merchant_account_id").toString());
            paramMap.put("accountIdList", accountIdList);
            mernsql = " and merchant_account_id in (:accountIdList) ";
        }
        String devsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("bh"))) {
            List<Integer> bhList = LangUtils.str2IntList(paramMap.get("bh").toString());
            paramMap.put("bhList", bhList);
            devsql = " and device_id in (:bhList) ";
        }
        String sql = "select a.device_id,a.merchant_account_id,c.merchant_name,b.mc,b.ip,"
                + " a.count_numb,a.trad_count_numb,a.trad_cash_amt,a.trad_sub_amt,(a.trad_cash_amt+a.trad_sub_amt) total_amt "
                + " from (select merchant_account_id,device_id,"
                + " sum(count_numb-count_numb_reversal-count_numb_cancel+count_numb_cancel_reversal) count_numb,"
                + " sum(trad_count_numb-xf_reversal_numb-xf_cancel_numb+xf_cancel_reversal_numb) trad_count_numb, "
                + " sum(trad_cash_amt-xf_reversal_cash_amt-xf_cancel_cash_amt+xf_cancel_reversal_cash_amt) trad_cash_amt,"
                + " sum(trad_sub_amt-xf_reversal_sub_amt-xf_cancel_sub_amt+xf_cancel_reversal_sub_amt) trad_sub_amt"
                + " from fin_pos_real_total where 1=1 " + mernsql + devsql
                + " group by merchant_account_id,device_id ) a "
                + " left join st_device b on a.device_id=b.dev_logic_bh"
                + " left join fin_merchant_account c on a.merchant_account_id= c.merchant_account_id";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 商户营业实时报表code-merchant
     * 条件：商户单选
     */
    @RequestMapping("/real/merchant")
    public ModelAndView merchantRealReport(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        //商户条件
        String mernsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("merchant_account_id"))) {
            List<Integer> accountIdList = LangUtils.str2IntList(paramMap.get("merchant_account_id").toString());
            paramMap.put("accountIdList", accountIdList);
            mernsql = " and merchant_account_id in (:accountIdList) ";
        }
        String sql = "select a.merchant_account_id,c.merchant_name,"
                + " a.count_numb,a.trad_count_numb,a.trad_cash_amt,a.trad_sub_amt,(a.trad_cash_amt+a.trad_sub_amt) total_amt"
                + " from (select merchant_account_id,"
                + " sum(count_numb-count_numb_reversal-count_numb_cancel+count_numb_cancel_reversal) count_numb,"
                + " sum(trad_count_numb-xf_reversal_numb-xf_cancel_numb+xf_cancel_reversal_numb) trad_count_numb,"
                + " sum(trad_cash_amt-xf_reversal_cash_amt-xf_cancel_cash_amt+xf_cancel_reversal_cash_amt) trad_cash_amt,"
                + " sum(trad_sub_amt-xf_reversal_sub_amt-xf_cancel_sub_amt+xf_cancel_reversal_sub_amt) trad_sub_amt"
                + " from fin_pos_real_total where 1=1 " + mernsql
                + " group by merchant_account_id) a"
                + " left join fin_merchant_account c on a.merchant_account_id= c.merchant_account_id";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * pos餐别实时营业报表code-posMeal
     * 条件：商户单选，pos多选（关联商户），餐别多选
     */
    @RequestMapping("/real/posMeal")
    public ModelAndView posMealRealReport(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        //商户条件
        String mernsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("merchant_account_id"))) {
            List<Integer> accountIdList = LangUtils.str2IntList(paramMap.get("merchant_account_id").toString());
            paramMap.put("accountIdList", accountIdList);
            mernsql = " and merchant_account_id in (:accountIdList) ";
        }
        String devsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("bh"))) {
            List<Integer> bhList = LangUtils.str2IntList(paramMap.get("bh").toString());
            paramMap.put("bhList", bhList);
            devsql = " and device_id in (:bhList) ";
        }
        String mealsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("meal"))) {
            List<Integer> mealList = LangUtils.str2IntList(paramMap.get("meal").toString());
            paramMap.put("mealList", mealList);
            mealsql = " and meal_id in (:mealList) ";
        }
        String sql = "select a.device_id,a.merchant_account_id,a.meal_id,c.merchant_name,b.mc,b.ip,d.meal_name,"
                + " a.count_numb,a.trad_count_numb,a.trad_cash_amt,a.trad_sub_amt,(a.trad_cash_amt+a.trad_sub_amt) total_amt"
                + " from (select merchant_account_id,device_id,meal_id,"
                + " sum(count_numb-count_numb_reversal-count_numb_cancel+count_numb_cancel_reversal) count_numb,"
                + " sum(trad_count_numb-xf_reversal_numb-xf_cancel_numb+xf_cancel_reversal_numb) trad_count_numb,"
                + " sum(trad_cash_amt-xf_reversal_cash_amt-xf_cancel_cash_amt+xf_cancel_reversal_cash_amt) trad_cash_amt,"
                + " sum(trad_sub_amt-xf_reversal_sub_amt-xf_cancel_sub_amt+xf_cancel_reversal_sub_amt) trad_sub_amt"
                + " from fin_pos_real_total where 1=1 " + mernsql + devsql + mealsql
                + " group by merchant_account_id,device_id,meal_id ) a"
                + " left join st_device b on a.device_id=b.dev_logic_bh"
                + " left join fin_merchant_account c on a.merchant_account_id= c.merchant_account_id"
                + " left join fin_meal d on a.meal_id=d.meal_id";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 商户餐别实时营业报表code-merchantMeal
     * 条件：商户单选，餐别多选
     */
    @RequestMapping("/real/merchantMeal")
    public ModelAndView merchantMealRealReport(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        //商户条件
        String mernsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("merchant_account_id"))) {
            List<Integer> accountIdList = LangUtils.str2IntList(paramMap.get("merchant_account_id").toString());
            paramMap.put("accountIdList", accountIdList);
            mernsql = " and merchant_account_id in (:accountIdList) ";
        }
        String mealsql = " and 1=1";
        if (StringUtils.isNotEmpty((String) paramMap.get("meal"))) {
            List<Integer> mealList = LangUtils.str2IntList(paramMap.get("meal").toString());
            paramMap.put("mealList", mealList);
            mealsql = " and meal_id in (:mealList) ";
        }
        String sql = "select a.merchant_account_id,a.meal_id,c.merchant_name,d.meal_name,"
                + " a.count_numb,a.trad_count_numb,a.trad_cash_amt,a.trad_sub_amt,(a.trad_cash_amt+a.trad_sub_amt) total_amt"
                + " from (select merchant_account_id,meal_id,"
                + " sum(count_numb-count_numb_reversal-count_numb_cancel+count_numb_cancel_reversal) count_numb,"
                + " sum(trad_count_numb-xf_reversal_numb-xf_cancel_numb+xf_cancel_reversal_numb) trad_count_numb,"
                + " sum(trad_cash_amt-xf_reversal_cash_amt-xf_cancel_cash_amt+xf_cancel_reversal_cash_amt) trad_cash_amt,"
                + " sum(trad_sub_amt-xf_reversal_sub_amt-xf_cancel_sub_amt+xf_cancel_reversal_sub_amt) trad_sub_amt"
                + " from fin_pos_real_total where 1=1 " + mernsql + mealsql + mealsql
                + " group by merchant_account_id,meal_id) a"
                + " left join fin_merchant_account c on a.merchant_account_id= c.merchant_account_id"
                + " left join fin_meal d on a.meal_id=d.meal_id";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    //dep=====================================================================================
    /*
     * 部门收支统计表 fin_daily_dep_payment
     */
    @RequestMapping("/dep/payment")
    public ModelAndView depPaymentFilterQry(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_dep_payment_filterqry";
        String procParam = "@start_date,@end_date,$dep_serial,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);

    }

    /*
     * 部门补贴收支汇总报表 pro_fin_report_dep_subcount_filterqry
     * 条件：部门列表(多选)
     */
    @RequestMapping("/dep/paymentCount")
    public ModelAndView depPaymentCount(JsonPage jp) {
		/*Map<String, Object> paramMap = parseJsonMap(jp);
		String depsql = " and 1=1 ";
		if(StringUtils.isNotEmpty((String)paramMap.get("dep_serial"))){
			List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
			paramMap.put("depSerialList", depSerialList);
			depsql = " and dep_serial in (:depSerialList) ";
		}
		log.debug(paramMap);
		String sql="select dep_serial,dep_name,sum(begin_sub_amt) begin_sub_amt,sum(sub_amt) sub_amt,"
				+ " sum(auto_sub_amt) auto_sub_amt, sum(sub_amt+auto_sub_amt) total_amt,"
				+ " sum(xf_sub_amt) xf_sub_amt,sum(draw_sub_amt) draw_sub_amt,sum(sub_zero_amt) sub_zero_amt,"
				+ " sum(xf_sub_amt+draw_sub_amt+sub_zero_amt) total_sub_amt,sum(sub_bamt) sub_bamt"
				+ " from fin_daily_dep"
				+ " where bill_date between :start_date and :end_date" + depsql
				+ " group by dep_serial,dep_name ";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);*/
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        // modify by zlibo 20201723
        String procName = "pro_fin_report_dep_subcount_filterqry_gly";
        String procParam = "@start_date,@end_date,$dep_serial,#errMsg,$gly_no";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    /*
     * 部门商户计次营业汇总报表 pro_fin_report_dep_merchantCount
     * 查询条件：部门（多选）、商户（多选）、餐别（多选）
     */
    @RequestMapping("/dep/merchantCount")
    public ModelAndView depMerchantCount(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_dep_merchantCount";
        String procParam = "@start_date,@end_date,$dep_serial,$merchant_account_id,$meal,@glyId,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    //personCount=====================================================================================
    /*
     * 消费个人汇总统计报表 mealRow  餐别在行上-纵向pro_fin_report_person_count
     * 条件：部门列表(多选)，餐别多选
     */
    @RequestMapping("/personCount/mealRow")
    public ModelAndView personCountmealRow(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        // modify by zlibo 20201723
        String procName = "pro_fin_report_person_count_gly";
        String procParam = "@start_date,@end_date,$dep_serial,$meal,#errMsg,$gly_no";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    /*
     * 消费个人汇总统计报表 mealCol 餐别在列上-横向pro_fin_report_person_count_meal
     * 条件：部门列表(多选)
     */
    @RequestMapping("/personCount/mealCol")
    public ModelAndView personCountmealCol(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        // modify by zlibo 20201723
        String procName = "pro_fin_report_person_count_meal_gly";
        String procParam = "@start_date,@end_date,$dep_serial,#errMsg,$gly_no";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    //sysTotal=====================================================================================
    /*
     * 系统收支日报
     * 20170518新增
     */
    @RequestMapping("/sysTotal/paymentdaily")
    public ModelAndView paymentdaily(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        // modify by zlibo 20200806
        String procName = "pro_fin_report_sys_paymentdaily_qry_gly";
        String procParam = "@start_date,@end_date,#errMsg,@glyId";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 系统收支汇总
     * 20170519新增
     */
    @RequestMapping("/sysTotal/paymentcount")
    public ModelAndView paymentcount(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_sys_paymentcount_qry_gly";
        String procParam = "@start_date,@end_date,#errMsg,@glyId";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    /*
     * 系统操作日报
     * 20170519新增
     */
    @RequestMapping("/sysTotal/operatedaily")
    public ModelAndView operatedaily(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_sys_operatedaily_qry_gly";
        String procParam = "@start_date,@end_date,#errMsg,@glyId";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    /*
     * 系统操作汇总
     * 20170519新增
     */
    @RequestMapping("/sysTotal/operatecount")
    public ModelAndView operatecount(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_sys_operatecount_qry_gly";
        String procParam = "@start_date,@end_date,#errMsg,@glyId";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    //custom 功能定制=====================================================================================
    /*
     * 午餐首次刷卡明细
     * 20170505-比德文定制
     * 查询中午每个人第一次消费的明细，不包括消费和消费冲正能相互抵消的数据
     */
    @RequestMapping("/custom/mealFristDetail")
    public ModelAndView custommealFristDetail(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_report_meal_detail_custom";
        String procParam = "@start_date,@end_date,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String) paramMap.get("errMsg");
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }


}
