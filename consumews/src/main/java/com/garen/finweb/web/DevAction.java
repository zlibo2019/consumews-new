package com.garen.finweb.web;

import java.util.ArrayList;
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
@RequestMapping("/finweb/dev")  
public final class DevAction extends BaseAction {
	@Autowired
	private ICommonDao commonDao;	
	
	//mode=====================================================================================
	/*
	 * 场所自动消费模式列表 qryModeList
	 */
	@RequestMapping("/mode/qryModeList")
	public ModelAndView placeModeList(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
		//paramMap.put("dep_serial", depSerialList);
		// or acid= or 
		log.debug(paramMap);
		//String sql = "select id, acdep_serial,(select dep_name from dt_ac_dep where dep_serial=acdep_serial) as dep_name,(select a.dep_name from dt_ac_dep a ,dt_ac_dep b where a.dep_serial=b.dep_parent and b.dep_serial=acdep_serial) as dep_parent,begin_date,end_date,case xf_model_id when 1 then '自动定额' else '计次' end as xf_model_id,xf_model_id as type,fixed_amt,rule_enable_date from fin_acdep_mode  where acdep_serial in (:depSerialList)"; //  where unit_id=:unit_id
		//commonDao.queryForList(jp, sql, paramMap);
		String procName = "pro_fin_dev_placemode_filterqry";
		String procParam = "@dep_serial,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*
	 * 自动模式新增 save
	 */
	@RequestMapping("/mode/save")
	public ModelAndView placeModeSave(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//paramMap.put("lx", 0);
		//String procName = "pro_fin_merch_placemode";
		//String procParam = "$id,@dep_serial,@begin_date,@end_date,@xf_model_id,$fixed_amt,$rule_enable_date,@lx,@gly_no,@ip,#errMsg";
		String procName = "pro_fin_dev_placemode_add";
		String procParam = "@dep_serial,@begin_date,@end_date,@xf_model_id,$fixed_amt,$rule_enable_date,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(!"".equals(paramMap.get("errMsg"))){
			jp.setRetInfo(-1, (String)paramMap.get("errMsg"));
		}
		return Json(jp);
	}
	/*
	 * 自动模式修改 saveupd
	 */
	@RequestMapping("/mode/saveupd")
	public ModelAndView placeModeSaveUpd(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		paramMap.put("lx", 1);
		String procName = "pro_fin_merch_placemode";
		String procParam = "@id,$dep_serial,@begin_date,@end_date,@xf_model_id,$fixed_amt,$rule_enable_date,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 * 自动模式删除 del
	 */
	@RequestMapping("/mode/del")
	public ModelAndView placeModeDel(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		paramMap.put("lx", 2);
		String procName = "pro_fin_merch_placemode";
		String procParam = "@id,$dep_serial,$begin_date,$end_date,$xf_model_id,$fixed_amt,$rule_enable_date,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 * 场所自动消费模式列表 type
	 */
	@RequestMapping("/mode/type")
	public ModelAndView placeModeType(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 1 as type,'自动定额' as name union select 2 as type,'计次' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	/*
	 * 扣款费模式列表 chargeType
	 */
	@RequestMapping("/mode/chargeType")
	public ModelAndView devChargeType(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String sql = "select 0 as type,'先扣补贴' as name union select 1 as type,'只扣补贴' as name union select 2 as type,'只扣现金' as name union select 3 as type,'先扣现金' as name";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}	
	
//	/*
//	 * 设备列表（设备指定商户专用） qryDevList
//	 */
//	@RequestMapping("/mode/qryDevList")
//	public ModelAndView devList(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		//List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
//		//paramMap.put("depSerialList", depSerialList);
//		//log.debug(paramMap);``
//		//String sql = "select c.acdep_serial,(select dep_name from dt_ac_dep where dep_serial=c.acdep_serial) as dep_name,a.bh,a.mc, b.merchant_account_id,(select merchant_name from fin_merchant_account where merchant_account_id=b.merchant_account_id ) as merchant_name,case charge_mode when 0 then '先扣补贴' when 1 then '只扣补贴' when 2 then '只扣个人' when 3 then '先扣个人' end  as charge_mode,charge_mode as type,a.ip,a.dev_logic_bh from st_device a inner join dt_dev c on a.bh=c.dev_serial left join fin_device b on a.bh=b.dev_serial where c.acdep_serial in (:depSerialList)";
//		//commonDao.queryForList(jp, sql, paramMap);
//		String procName = "pro_fin_dev_mode_devlist";
//		String procParam = "@dep_serial,$search_txt,$page_no,$page_size,#total,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		return Json(jp);
//	}
//	/*
//	 *商户指定  devSet
//	 */
//	@RequestMapping("/mode/devSet")
//	public ModelAndView devSet(JsonPage jp) {
//		//Map<String, Object> paramMap = parseJsonMap(jp);
//		JSONObject jobj = parseJson(jp);
//		String gly_no = jobj.getString("gly_no");
//		String ip = jobj.getString("ip");
//		JSONArray jarray = getJSONArray(jobj, "data");
//		String procName = "pro_fin_merch_devset";
//		String procParam = "@bh,@merchant_account_id,@type,@gly_no,@ip,#errMsg";
//		List<String> liststr = new ArrayList<>();
//		//循环各个json
//		for(Object jo : jarray){
//			Map<String, Object> paramMap = (Map<String,Object>)jo;
//			paramMap.put("gly_no", gly_no);
//			paramMap.put("ip", ip);
//			commonDao.callProc(jp, procName, procParam, paramMap);
//			String msg = (String)paramMap.get("errMsg");
//			if(StringUtils.isNotEmpty(msg)){
//				liststr.add("设备ID:"+Integer.parseInt((String)paramMap.get("bh"))+":"+msg);
//			}
//		}
//		jp.setObj(liststr);
//		
//		//for (int i = 0; i < jarray.size(); i++){
//			//Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
//			//commonDao.callProc(jp, procName, procParam, paramMap);
//			
//		//}
//		if(liststr.isEmpty() == false){
//			jp.setRetInfo(-1, "");
//		}
//		//retJson.obj id info obj
//		return Json(jp);
//	}

	
	//merchant=====================================================================================
	////////////////商户及扣款模式页面（二期修改）////////////////////////
	/*
	 * 设备列表（设备指定商户） qryDevList
	 */
	@RequestMapping("/merchant/qryDevList")
	public ModelAndView merchantDevList(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		//List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
		//paramMap.put("depSerialList", depSerialList);
		//log.debug(paramMap);
		//String sql = "select c.acdep_serial,(select dep_name from dt_ac_dep where dep_serial=c.acdep_serial) as dep_name,a.bh,a.mc, b.merchant_account_id,(select merchant_name from fin_merchant_account where merchant_account_id=b.merchant_account_id ) as merchant_name,case charge_mode when 0 then '先扣补贴' when 1 then '只扣补贴' when 2 then '只扣个人' when 3 then '先扣个人' end  as charge_mode,charge_mode as type,a.ip,a.dev_logic_bh from st_device a inner join dt_dev c on a.bh=c.dev_serial left join fin_device b on a.bh=b.dev_serial where c.acdep_serial in (:depSerialList)";
		//commonDao.queryForList(jp, sql, paramMap);
		String procName = "pro_fin_dev_merchant_devlist";
		String procParam = "@merchant_account_id,$search_txt,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*
	 * 设备列表（未指定商户的设备（可检索））undev
	 */
	@RequestMapping("/merchant/undev")
	public ModelAndView DevList(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		String procName = "pro_fin_dev_merchant_undevlist";
		String procParam = "$search_txt,$page_no,$page_size,#total,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	
	/*
	 *设备商户指定======给一个商户指定多个设备 devSet
	 */
	@RequestMapping("/merchant/devSet")
	public ModelAndView merchantDevSet(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//paramMap.put("lx", 0);
		String procName = "pro_fin_dev_merchant_devset";
		String procParam = "@bh,@merchant_account_id,@tallyEn,@type,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*
	 *设备商户指定======修改扣款模式 mode
	 */
	@RequestMapping("/merchant/mode")
	public ModelAndView merchantMode(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//paramMap.put("lx", 0);
		String procName = "pro_fin_dev_merchant_modeset";
		String procParam = "@bh,@type,@tallyEn,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	/*
	 *设备商户指定======解除设备关联 devdel
	 */
	@RequestMapping("/merchant/devdel")
	public ModelAndView merchantDevdel(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		//paramMap.put("lx", 0);
		String procName = "pro_fin_dev_merchant_devdel";
		String procParam = "@bh,@gly_no,@ip,#suc_count,#err_count,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		log.debug(paramMap.get("errMsg"));
		if(!"".equals(paramMap.get("errMsg"))){
			jp.setRetInfo(-1, (String)paramMap.get("errMsg"));
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("suc_count", paramMap.get("suc_count"))
				.put("err_count", paramMap.get("err_count"))
				.build();
		jp.setRetData(retMap);	
		return Json(jp);
	}
	
	
	//dev=====================================================================================
	/*
	 * 设备列表（带商户查询设备的，报表专用）
	 */
	@RequestMapping("/dev/devListByMerchant")
	public ModelAndView devQryListByMerchant(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		List<Integer> merchantList=LangUtils.str2IntList(paramMap.get("merchant_account_id").toString());
		paramMap.put("merchantList", merchantList);
		log.debug(paramMap);
		String sql = "select a.dev_serial as bh,b.mc from fin_device a inner join st_device b on a.dev_serial=b.bh"
				+ " and a.merchant_account_id in (:merchantList)";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
	/*
	 * 设备列表（带场所查询设备的）
	 */
	@RequestMapping("/dev/devlist")
	public ModelAndView devQryList(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
		paramMap.put("depSerialList", depSerialList);
		log.debug(paramMap);
		String sql = "select a.bh,a.mc from st_device a inner join dt_dev c on a.bh=c.dev_serial where (a.dev_lb=13 or (a.dlx=2 and dev_lb=99)) and c.acdep_serial in (:depSerialList)";
		commonDao.queryForList(jp, sql, paramMap);
		return Json(jp);
	}
}
