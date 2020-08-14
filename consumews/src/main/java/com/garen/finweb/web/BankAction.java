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

@Controller  
@RequestMapping("/finweb/bank")  
public final class BankAction extends BaseAction {
	/**
	 * fin-页面操作-圈存报表 bank
	 * 输入、输出参数均为JSON格式
	 */	
	
	@Autowired
	private ICommonDao commonDao;
	//******************************************************
	//电子转账报表 bank：电子转账设备日报
	//******************************************************
	/*--------------------------
	 * 电子转账设备日报
	 */
	@RequestMapping("/devdailyQry")
	public ModelAndView devdailyQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_dev_trans_daily_qry";
		String procParam = "@start_date,@end_date,$bank_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		return Json(jp);
	}	
	
	/*
	 * 电子转账银行日报 bankdailyQry
	 */
	@RequestMapping("/bankdailyQry")
	public ModelAndView bankdailyQry(JsonPage jp){
	
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_bank_trans_daily_qry";
		String procParam = "@start_date,@end_date,$bank_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	


	
	/*
	 * 电子转账明细查询 bankdetailQry
	 */
	@RequestMapping("/bankdetailQry")
	public ModelAndView bankdetailQry(JsonPage jp){
	
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_bank_detail_qry";
		String procParam = "@start_date,@end_date,$bank_id,$bh,$bill_no,$user_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*
	 * 电子转账设备汇总 devcountQry
	 */
	@RequestMapping("/devcountQry")
	public ModelAndView devcountQry(JsonPage jp){
	
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_dev_trans_count_qry";
		String procParam = "@start_date,@end_date,$bank_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*
	 * 电子转账银行汇总 bankcountQry
	 */
	@RequestMapping("/bankcountQry")
	public ModelAndView bankcountQry(JsonPage jp){
	
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_bank_trans_count_qry";
		String procParam = "@start_date,@end_date,$bank_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	
	
	
	
	
	
	
}
