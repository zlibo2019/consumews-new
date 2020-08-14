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
@RequestMapping("/finweb/merch")  
public final class MerchAction extends BaseAction {
	/**
	 * fin-页面操作-商户管理
	 * 操作:商户部门、商户
	 * 输入、输出参数均为JSON格式
	 */
	@Autowired
	private ICommonDao commonDao;

	/*--------------------------
	 * 商户部门-部门查询
	 */
//	@RequestMapping("/dep/qryMerchDep")
//	public ModelAndView queryMerchDepJson(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String sql = "select dep_serial,dep_parent,dep_name,dep_no,dep_order from fin_merchant_dep"; //  where unit_id=:unit_id
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);
//	}
	/*--------------------------
	 * 商户部门-部门新增保存
	 */
//	@RequestMapping("/dep/save")
//	public ModelAndView MerchDepSave(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String procName = "pro_fin_merch_depsave";
//		paramMap.put("lx", 0);
//		String procParam = "@dep_name,@dep_parent,$dep_serial,@lx,@gly_no,@ip,#errMsg,#dep";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		Map<String, Object> retMap = new MapBuilder()
//				.put("dep_serial", paramMap.get("dep"))
//				.build();
//		jp.setRetData(retMap);
//		return Json(jp);
//	}
	/*--------------------------
	 * 商户部门-部门修改保存
	 */
//	@RequestMapping("/dep/saveupd")
//	public ModelAndView MerchDepSaveUpd(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		paramMap.put("lx", 1);
//		String procName = "pro_fin_merch_depsave";
//		String procParam = "@dep_name,@dep_parent,@dep_serial,@lx,@gly_no,@ip,#errMsg,#dep";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}
	/*--------------------------
	 * 商户部门-删除
	 */
//	@RequestMapping("/dep/del")
//	public ModelAndView MerchDepDel(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		paramMap.put("lx", 2);
//		String procName = "pro_fin_merch_depsave";
//		String procParam = "$dep_name,$dep_parent,@dep_serial,@lx,@gly_no,@ip,#errMsg,#dep";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}

	//////////////////////////////////////商户部门结束////////////////////////

	/*
	 * 商户列表（注册专用）
	 */
//	@RequestMapping("/register/qryMerchList")
//	public ModelAndView MerchListJson(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String procName = "pro_fin_merch_allfilterqry";
//		String procParam = "$page_no,$page_size,#total,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		return Json(jp);
//	}

	/*
	 * 商户新增-保存
	 */
//	@RequestMapping("/register/save")
//	public ModelAndView MerchRegisterSave(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		paramMap.put("lx", 0);
//		String procName = "pro_fin_merch_account";
//		String procParam = "@merchant_name,$merchant_account_id,$merchant_addr,$merchant_telephone,$link_man,$merchant_bank_account,$merchant_bank,@fee_rate,@merchant_account_type,@merchant_dep,@lx,@gly_no,@ip,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}
	/*
	 * 商户修改-保存
	 */
//	@RequestMapping("/account/saveupd")
//	public ModelAndView MerchAccountSaveUpd(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		paramMap.put("lx", 1);
//		String procName = "pro_fin_merch_account";
//		String procParam = "@merchant_name,@merchant_account_id,$merchant_addr,$merchant_telephone,$link_man,$merchant_bank_account,$merchant_bank,@fee_rate,@merchant_account_type,@merchant_dep,@lx,@gly_no,@ip,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}
	/*
	 * 商户正常列表-可检索
	 */
//	@RequestMapping("/account/qryMerchList")
//	public ModelAndView MerchListQry(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		//String condtion = "";
//		//帐号、商户名称、联系人、商户部门、商户类型
//		//if(paramMap.containsKey("search_txt")){
//			//paramMap.put("search_txt", "%"+paramMap.get("search_txt")+"%");
//			//condtion += " and (merchant_name like :search_txt or link_man like :search_txt )" ;
//					//or merchant_dep=:search_txt or merchant_account_type=:search_txt)";
//					//merchant_account_id like :search_txt or
//		//}
//		//String sql = "select merchant_account_id,merchant_name,link_man,(select dep_name from fin_merchant_dep where dep_serial=merchant_dep) as merchant_dep, case merchant_account_type when 0 then '经营户' else '其它' end  as merchant_account_type,fee_rate,merchant_addr,merchant_bank_account,merchant_bank,merchant_telephone,merchant_dep as dep_serial,merchant_account_type as type from fin_merchant_account "
//		//+" where merchant_state=0 " + condtion; //  where unit_id=:unit_id
//		//commonDao.queryForList(jp, sql, paramMap);
//		String procName = "pro_fin_merch_cond_filterqry";
//		String procParam = "@search_txt,$page_no,$page_size,#total,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		return Json(jp);
//	}
	/*
	 * 商户销户
	 */
//	@RequestMapping("/account/del")
//	public ModelAndView MerchAccountDel(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String procName = "pro_fin_merch_delaccount";
//		String procParam = "@id,@gly_no,@ip,#suc_count,#err_count,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		log.debug(paramMap.get("errMsg"));
//		if(!"".equals(paramMap.get("errMsg"))){
//			jp.setRetInfo(-1, (String)paramMap.get("errMsg"));
//		}
//		Map<String, Object> retMap = new MapBuilder()
//				.put("suc_count", paramMap.get("suc_count"))
//				.put("err_count", paramMap.get("err_count"))
//				.build();
//		jp.setRetData(retMap);
//		return Json(jp);
//	}
	/*
	 * 商户类型
	 */
//	@RequestMapping("/account/type")
//	public ModelAndView MerchAccountLx(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String sql = "select 0 as type,'经营户' as name"; //  where unit_id=:unit_id
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);
//	}
	
	/*
	 * 商户管理-查询商户-全部商户 qryAll
	 */
	@RequestMapping("/account/qryAll")
	public ModelAndView accountqryAll(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("type_id", 0);//--0:查询全部商户 1:查询正常商户 2:查询销户商户
		String procName = "pro_fin_merch_account_qry";
		String procParam = "@type_id,$gly_no,$ip,$page_no,$page_size,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 * 商户管理-查询商户-正常商户 qryNormal
	 */
	@RequestMapping("/account/qryNormal")
	public ModelAndView accountqryNormal(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("type_id", 1);//--0:查询全部商户 1:查询正常商户 2:查询销户商户
		String procName = "pro_fin_merch_account_qry";
		String procParam = "@type_id,$gly_no,$ip,$page_no,$page_size,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*
	 * 商户管理-查询商户-销户商户 qryDel
	 */
	@RequestMapping("/account/qryDel")
	public ModelAndView accountqryDel(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("type_id", 2);//--0:查询全部商户 1:查询正常商户 2:查询销户商户
		String procName = "pro_fin_merch_account_qry";
		String procParam = "@type_id,$gly_no,$ip,$page_no,$page_size,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*
	 * 商户管理-新增商户 add
	 */
	@RequestMapping("/account/add")	
	public ModelAndView accountadd(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("lx", 0);
		String procName = "pro_fin_merch_account";
		String procParam = "@merchant_name,$merchant_account_id,$merchant_addr,$merchant_telephone,"+
				"$link_man,$merchant_bank_account,$merchant_bank,$fee_rate,$merchant_account_type,"+
				"$merchant_dep,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 * 商户管理-修改商户 saveupd
	 */
	@RequestMapping("/account/saveupd")	
	public ModelAndView accountsaveupd(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		paramMap.put("lx", 1);
		String procName = "pro_fin_merch_account";
		String procParam = "@merchant_name,$merchant_account_id,$merchant_addr,$merchant_telephone,"+
				"$link_man,$merchant_bank_account,$merchant_bank,$fee_rate,$merchant_account_type,"+
				"$merchant_dep,@lx,@gly_no,@ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*
	 * 商户管理-删除商户(销户) del
	 */
	@RequestMapping("/account/del")
	public ModelAndView accountdel(JsonPage jp){
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_merch_delaccount";
		String procParam = "@id,@gly_no,@ip,#suc_count,#err_count,#errMsg";
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
		
	
	/////////////////////////商户注册、修改、销户结束//////////////////////////////////////
	
//	/*
//	 * 商户列表（传参为商户部门）
//	 */
//	@RequestMapping("/account/merchList")
//	public ModelAndView MerchList(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		List<Integer> depSerialList=LangUtils.str2IntList(paramMap.get("dep_serial").toString());
//		paramMap.put("depSerialList", depSerialList);
//		log.debug(paramMap);
//		String sql = "select merchant_account_id,merchant_name from fin_merchant_account where merchant_dep in (:depSerialList)"; //  where unit_id=:unit_id
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);
//	}
//	/*
//	 * 商户列表（不传参，为报表专用）
//	 */
//	@RequestMapping("/account/merchListAll")
//	public ModelAndView MerchList2(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String sql = "select merchant_account_id,merchant_name from fin_merchant_account"; //  where unit_id=:unit_id
//		commonDao.queryForList(jp, sql, paramMap);
//		return Json(jp);
//	}
//	/*
//	 * 新增、删除商户权限
//	 */
//	@RequestMapping("/permissions/add")
//	public ModelAndView Permissionsadd(JsonPage jp){
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		String procName = "pro_fin_merch_permissions_add";
//		String procParam = "@merchant_dep_serial,@merchant_lx,@is_include_son,@lx,@gly_no,@operator,$ip,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}
//	
//	/*
//	 * 商户权限查询
//	 */
//	@RequestMapping("/permissions/qryBusiness")
//	public ModelAndView permissionsqryBusiness(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		String procName = "pro_fin_merch_qrybusiness";
//		String procParam = "@gly_no,@merchant_lx";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		//StringUtils.isEmpty(cs)
//		
//		String msg = (String)paramMap.get("errMsg");
//		log.debug(paramMap.get("errMsg"));
//		if(StringUtils.isNotEmpty(msg)){
//			jp.setRetInfo(-1, msg);
//		}
//		return Json(jp);
//	}
	
}
