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
@RequestMapping("/finweb/acc")  
public final class AccountAction extends BaseAction {
	/**
	 * fin-页面操作-持卡人管理 acc
	 * 操作:开户、销户、充值、批量充值、取款、充值纠错、消费纠错、卡片管理、账户管理、读卡器管理
	 * 方法：openAcc、closeAcc、recharge、bRecharge、withdraw、rchCorrect、
	 * 		finCorrect、card、account、cardReader
	 * 输入、输出参数均为JSON格式
	 */
	
	@Autowired
	private ICommonDao commonDao;
	//******************************************************
	//开户 openAcc：查询身份、查询密码方案、查询部门、筛选查询、添加设置、列表导入、开户操作、页面列表刷新、发卡
	//******************************************************
	/*--------------------------
	 * 开户-查询身份、查询密码方案、查询部门--公共
	 */
	/*--------------------------
	 * 开户-筛选查询 filterQry
	 */
	@RequestMapping("/openAcc/filterQry")
	public ModelAndView openAccfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_openacc_filterqry";
		String procParam = "$user_no,$dep_serial,$identity_id,$user_id,@gly_no," +
				"$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}	
	/*--------------------------
	 * 开户-添加设置 setCfg
	 */
	@RequestMapping("/openAcc/setCfg")
	public ModelAndView openAccsetCfg(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		String condtion = "";
		if(paramMap.containsKey("scheme_id")){
			condtion += ",scheme_id=:scheme_id";
		}
		if(paramMap.containsKey("finger_enable")){
			condtion += ",finger_enable=:finger_enable";
		}	
		if(paramMap.containsKey("account_init_amt")){
			condtion += ",account_init_amt=:account_init_amt";
		}	
		if(paramMap.containsKey("account_end_date")){
			condtion += ",account_end_date=:account_end_date";
		}
		if("".equals(condtion)){
			jp.setRetInfo(-1, "至少选择一项需要更新的项");
			return Json(jp);
		}
		else{
			condtion = condtion.substring(1);
		}
		//String sql2 = "update fin_open_account set scheme_id=:scheme_id,finger_enable=:finger_enable,"+
		//		" account_init_amt=:account_init_amt,account_end_date=:account_end_date"+
		//		" where id in(:id) and account_state=0"; 
		String sql = "update fin_open_account set " + condtion +
				" where id in(:id) and account_state=0"; 
		paramMap.put("id", LangUtils.str2IntList(paramMap.get("id").toString()));
		commonDao.update(sql, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 开户-列表导入 impUser
	 */
	@RequestMapping("/openAcc/impUser")
	public ModelAndView openAccimpUser(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 参数不能为空");
			return Json(jp);			
		}
		String gly_no = jobj.getString("gly_no");
		JSONArray jarray = getJSONArray(jobj, "data");
		if (null == jarray || jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}		
		//log.debug(jarray);
		//数据检测开始
		//取所有DB数据到内存待比较 
		String sql = "select u.user_no,u.user_lname,f.account_id from dt_user u " + 
				" left join fin_account f on u.user_serial=f.user_serial and f.account_state<>2 " + 
				" where u.user_type<51 ";
		List<Map<String,Object>> dMaplst = commonDao.queryForList(sql);
		//log.debug(dMaplst);
		
		JSONArray rstArry = new JSONArray();
		for (int i = 0; i < jarray.size(); i++){
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			if(!paramMap.containsKey("xh")){ 
				jp.setRetInfo(-1, "序号不能为空");
				return Json(jp);
			}		
			String xh = String.valueOf(paramMap.get("xh"));	
			JSONObject obj = new JSONObject(); 
			if(!paramMap.containsKey("user_no")){ 
				obj.put("xh", xh);
				obj.put("user_no", "");
				obj.put("message", "学/工号不能为空");
				rstArry.add(obj);
				continue;
			}	
			
			String userno = paramMap.get("user_no")+"";
			if(!paramMap.containsKey("user_lname")){ 
				obj.put("xh", xh);
				obj.put("user_no", userno);
				obj.put("message", "姓名不能为空");
				rstArry.add(obj);
				continue;
			}			
			//log.debug(paramMap);
			
			//String userno = paramMap.get("user_no").toString();
			String userlname = paramMap.get("user_lname")+"";
			boolean hasFlag = false;
			for (int j = 0; j < dMaplst.size(); j++){
				Map<String, Object> dmap = (Map<String,Object>)dMaplst.get(j);
				if(dmap.get("user_no").equals(paramMap.get("user_no"))){
					if(!dmap.get("user_lname").equals(paramMap.get("user_lname"))){
						//姓名不同
						obj.put("xh", xh);
						obj.put("user_no", userno);
						obj.put("user_lname", userlname);
						obj.put("message", "姓名内容不符");
						rstArry.add(obj);	
					}		
					else if(dmap.containsKey("account_id") && dmap.get("account_id") != null){
						//已开户
						obj.put("xh", xh);
						obj.put("user_no", userno);
						obj.put("user_lname", userlname);
						obj.put("message", "已存在账户，无需重新开户");
						rstArry.add(obj);
					}
					hasFlag = true;
					break;
				}
			}
			if(!hasFlag){
				//不存在
				obj.put("xh", xh);
				obj.put("user_no", userno);
				obj.put("user_lname", userlname);
				obj.put("message", "工号人员不存在");
				rstArry.add(obj);
			}
		}
		dMaplst = null;
		if(rstArry.size() > 0){
			//检测结果中存在异常  组织返回
			jp.setRecord(rstArry);
			jp.setRetInfo(-1, "存在异常数据，导入失败");
			return Json(jp); 
		}
		//数据检测完成

		//开始循环 导入人员
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("gly_no", gly_no);
		sql = "update fin_open_account set is_expired=1,sj=GETDATE() where operator=:gly_no";
		commonDao.update(sql, tmpMap);
		sql = "select top 1 scheme_id from fin_tt_pwd_Scheme where default_scheme=1";
		Integer scheme_id = commonDao.queryForInt(sql, null);
		
		sql = "insert into fin_open_account(user_serial,account_state,is_expired,scheme_id,account_init_amt,finger_enable,sj,operator) "+
				" select user_serial,0 as account_state,0 as is_expired,:scheme_id as scheme_id,0 as account_init_amt," + 
				" 0 as finger_enable,GETDATE() as sj,:gly_no as operator " +
				" from dt_user where user_no=:user_no and user_lname=:user_lname and user_type<51";
		for (int i = 0; i < jarray.size(); i++){
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			Map<String, Object> inparamMap = new HashMap<String, Object>();
			inparamMap.put("scheme_id", scheme_id);
			inparamMap.put("gly_no", gly_no);
			inparamMap.put("user_no", (String)paramMap.get("user_no"));
			inparamMap.put("user_lname", (String)paramMap.get("user_lname"));
			commonDao.update(sql, inparamMap);
		}
		//结束循环 导入人员完成
		
		//返回结果结
		sql = "select a.id,a.user_serial,u.user_no,u.user_lname,u.user_sex,d.dep_name,p.scheme_name,u.[user_id],a.account_end_date," + 
				" account_init_amt,case a.finger_enable when 0 then '禁用' when 1 then '启用' else '' end as finger_enable," +
				" case a.account_state when 0 then '未开户' when 1 then '已开户' when 2 then '已发卡' when 3 then '重复操作'" +
				" else '' end as account_state" +
				" from fin_open_account a" +
				" inner join dt_user u on a.user_serial=u.user_serial" +
				" inner join dt_dep d on u.user_dep=d.dep_serial"+
				" inner join fin_tt_pwd_Scheme p on a.scheme_id=p.scheme_id" +
				" where a.operator=:gly_no and a.is_expired=0";
		tmpMap.clear();
		tmpMap.put("gly_no", gly_no);
		commonDao.queryForList(jp, sql, tmpMap);
		return Json(jp);
	}
	/*--------------------------
	 * 开户-开户操作 open
	 */
	@RequestMapping("/openAcc/open")
	public ModelAndView openAccopen(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_openacc_open";
		String procParam = "@id,@gly_no,$scheme_id,$finger_enable,$account_init_amt,"+
				"$account_end_date,$ip,#suc_count,#err_count,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
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
	 * 开户-页面列表刷新 refresh
	 */
	@RequestMapping("/openAcc/refresh")
	public ModelAndView openAccrefresh(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_openacc_refresh";
		String procParam = "@gly_no,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}
	/*--------------------------
	 * 开户-发卡addCard
	 */
	@RequestMapping("/openAcc/addCard")
	public ModelAndView openAccaddCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_openAcc_addCard";
		String procParam = "@id,@gly_no,@card_number,@media_id,@is_main_card," +
				"@old_card_serial,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 开户-发卡-读写卡发卡后部分writeafter
	 */
	@RequestMapping("/openAcc/writeAfter")
	public ModelAndView openAccwriteAfter(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_openAcc_add_writeafter";
		String procParam = "@id,@card_serial,@gly_no,@old_card_serial,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//销户 closeAcc：查部门、筛选查询、列表导入、销户操作
	//******************************************************
	/*--------------------------
	 * 销户-筛选查询 filterQry
	 */	
	@RequestMapping("/closeAcc/filterQry")
	public ModelAndView closeAccfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_closeacc_filterqry";
		String procParam = "$user_no,$dep_serial,$identity_id,$year,$month,$day,@cx_type,$page_no,$page_size,#total,@gly_no,#errMsg"; 
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 销户-列表导入 impUser
	 */
	@RequestMapping("/closeAcc/impUser")
	public ModelAndView closeAccimpUser(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 参数不能为空");
			return Json(jp);			
		}
		String gly_no = jobj.getString("gly_no");
		JSONArray jarray = getJSONArray(jobj, "data");
		if (null == jarray || jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}	
		//log.debug(jarray);
		//数据检测开始
		//取所有DB数据到内存待比较 
		String sql = "select a.account_id,a.account_state,u.user_serial,u.user_no,u.user_lname,u.user_sex, " + 
				" u.user_depname,u.[user_id],a.cash_amt,isnull(t.sub_amt,0) as sub_amt,a.deposit_amt " + 
				" ,a.account_state as account_state_code,case a.account_state when 0 then '正常' " +
				" when 1 then '冻结' when 2 then '销户' else '' end as account_state_name" +
				" from dt_user u" +
				" left join (select a1.* from fin_account a1 inner join ("+
				" select user_serial,max(account_id) account_id from fin_account"+
				" group by user_serial) b1 on a1.account_id=b1.account_id)	"+
				" a on u.user_serial=a.user_serial" +
				" left join (" +
				" select aa.account_id,sum(ss.sub_amt) as sub_amt" +
				" from dt_user uu " +
				" left join fin_account aa on uu.user_serial=aa.user_serial" +
				" left join fin_sub_account ss on aa.account_id=ss.account_id" +
				" where uu.user_type<51 group by aa.account_id" +
				" ) t on t.account_id=a.account_id" +
				" where u.user_type<51";
		List<Map<String,Object>> dMaplst = commonDao.queryForList(sql);
		Map<String, Object> rstMap = new HashMap<String, Object>();
		//log.debug(dMaplst);
		
		JSONArray rstArry = new JSONArray();
		JSONArray rstokArry = new JSONArray();
		for (int i = 0; i < jarray.size(); i++){
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			if(!paramMap.containsKey("xh")){ 
				jp.setRetInfo(-1, "序号不能为空");
				return Json(jp);
			}	
			String xh = String.valueOf(paramMap.get("xh"));
			JSONObject obj = new JSONObject(); 
			if(!paramMap.containsKey("user_no")){ 
				obj.put("xh", xh);
				obj.put("user_no", "");
				obj.put("user_lname", "");
				obj.put("message", "学/工号不能为空");
				rstArry.add(obj);
				continue;
			}			
			//log.debug(paramMap);
			
			String userno = paramMap.get("user_no") + "";
			String userlname = paramMap.get("user_lname") + "";
			boolean hasFlag = false;
			for (int j = 0; j < dMaplst.size(); j++){
				Map<String, Object> dmap = (Map<String,Object>)dMaplst.get(j);
				if(dmap.get("user_no").equals(paramMap.get("user_no"))){
					if(!dmap.get("user_lname").equals(paramMap.get("user_lname"))){
						//姓名不同
						obj.put("xh", xh);
						obj.put("user_no", userno);
						obj.put("user_lname", userlname);
						obj.put("message", "姓名内容不符");
						rstArry.add(obj);						
					}
					else	
					if(!dmap.containsKey("account_id")){
						//未开户
						obj.put("xh", xh);
						obj.put("user_no", userno);
						obj.put("user_lname", userlname);
						obj.put("message", "未开户，无需销户");
						rstArry.add(obj);						
					}
					else if(dmap.containsKey("account_state") && 
							"2".equals(dmap.get("account_state")+"") ){
						//已销户
						obj.put("xh", xh);
						obj.put("user_no", userno);
						obj.put("user_lname", userlname);
						obj.put("message", "已销户，无需重新销户");
						rstArry.add(obj);
					}
					else{
						//返回成功结果
						//account_id,user_serial,user_no,user_lname,user_sex,
						//dep_name,[user_id],cash_amt,sub_amt,deposit_amt,--state
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", dmap.get("user_no"));
						objt.put("user_lname", dmap.get("user_lname"));
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_id", dmap.get("user_id"));
						objt.put("cash_amt", dmap.get("cash_amt"));
						objt.put("sub_amt", dmap.get("sub_amt"));
						objt.put("deposit_amt", dmap.get("deposit_amt"));
						objt.put("account_state_code", dmap.get("account_state_code"));
						objt.put("account_state_mc", dmap.get("account_state_name"));	
						rstokArry.add(objt);
					}
					hasFlag = true;
					break;
				}
			}
			if(!hasFlag){
				//不存在
				obj.put("xh", xh);
				obj.put("user_no", userno);
				obj.put("user_lname", userlname);
				obj.put("message", "工号人员不存在");
				rstArry.add(obj);
			}
		}	
		dMaplst = null;
		if(rstArry.size() > 0){
			//检测结果中存在异常  组织返回
			jp.setRecord(rstArry);
			jp.setRetInfo(-1, "存在异常数据，导入失败");
			return Json(jp); 
		}
		//数据检测完成
	
		//返回结果	
		if(rstokArry.size() > 0){		
			jp.setRecord(rstokArry);
			jp.setTotal(rstokArry.size());
		}
		return Json(jp);
	}
	/*--------------------------
	 * 销户-销户操作 close
	 */	
	@RequestMapping("/closeAcc/close")
	public ModelAndView closeAccclose(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_closeacc_close";
		String procParam = "@account_id_str,@if_deposit,@if_sub,@destroy_type,@auto_backcard,"+
				"$read_media_id,$read_card_number,@gly_no,$ip,#errMsg,#suc_count,#err_count";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
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
	//充值 recharge：查部门、查充值明细、筛选查询、充值操作
	//******************************************************
	/*--------------------------
	 * 充值-查充值明细 qryRecharge
	 */
	@RequestMapping("/recharge/qryRecharge")
	public ModelAndView qryRecharge(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_recharge_listqry";
		String procParam = "@gly_no,#incomes,#pays,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		Map<String, Object> retMap = new MapBuilder()
				.put("incomes", paramMap.get("incomes"))
				.put("pays", paramMap.get("pays"))
				.build();
		jp.setRetData(retMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 充值-筛选查询 filterQry
	 */
	@RequestMapping("/recharge/filterQry")
	public ModelAndView rechargefilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_recharge_filterqry";
		String procParam = "$cx_type,$user_no,$dep_serial,$user_lname,$user_id,$gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 充值-充值操作 recharge
	 */
	@RequestMapping("/recharge/recharge")
	public ModelAndView recharge(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_recharge";
		String procParam = "@account_id,@cash_amt,@recharge_amt,$read_media_id," +
				"$read_card_number,@gly_no,$ip,#user_no,#user_lname,#dep_name,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//批量充值 bRecharge：查部门、筛选查询、列表导入、充值操作
	//******************************************************
	/*--------------------------
	 * 批量充值-查部门--公共
	 */ 
	/*--------------------------
	 * 批量充值-筛选查询 filterQry
	 */
	@RequestMapping("/bRecharge/filterQry")
	public ModelAndView bRechargefilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_bRecharge_filterqry";
		String procParam = "$user_no,$dep_serial,$user_lname,$user_id,$gly_no," +
				"$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		jp.setTotal((Integer)paramMap.get("total"));
		return Json(jp);
	}
	/*--------------------------
	 * 批量充值-列表导入 impUser -------------
	 */
	@RequestMapping("/bRecharge/impUser")
	public ModelAndView bRechargeimpUser(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 参数不能为空");
			return Json(jp);			
		}
		String gly_no = jobj.getString("gly_no");
		JSONArray jarray = getJSONArray(jobj, "data");
		if (null == jarray || jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}
		String sql = "select a.account_id,a.account_state,u.user_serial,u.user_no,u.user_lname,t.tt_name as user_duty," + 
				" u.[user_id],u.user_depname,a.account_end_date,a.account_state as account_state_code,case a.account_state" +
				" when 0 then '正常' when 1 then '冻结' when 2 then '销户' else '' end as account_state_name," + 
				" a.cash_amt from dt_user u left join fin_account a on u.user_serial=a.user_serial" +
				" left join tt_identity t on u.user_identity=t.tt_order" +
				" where u.user_type<51";
		List<Map<String,Object>> dMaplst = commonDao.queryForList(sql);
		log.debug(jarray);
		JSONArray rstOkArry = new JSONArray();
		JSONArray rstErrArry = new JSONArray();
		for (int i = 0; i < jarray.size(); i++){
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			//log.debug(paramMap);
			if(!paramMap.containsKey("xh")){ 
				jp.setRetInfo(-1, "序号不能为空");
				return Json(jp);
			}		
			JSONObject obj = new JSONObject(); 
			String xh = String.valueOf(paramMap.get("xh"));	
			//log.debug(xh);
			if(!paramMap.containsKey("user_no") || !paramMap.containsKey("user_lname") || 
				!paramMap.containsKey("recharge_amt")){ 
				obj.clear();
				obj.put("xh", xh);
				obj.put("message", "输入参数不允许为空");
				rstErrArry.add(obj);
				continue;
			}
			String user_no = paramMap.get("user_no") + "";
			String user_lname = paramMap.get("user_lname") + "";
			String recharge_amt = paramMap.get("recharge_amt") + "";			
			
			if(recharge_amt.indexOf(".") < 1 || recharge_amt.length() - recharge_amt.indexOf(".") - 1 != 2){ 
				jp.setRetInfo(-1, "充值金额格式不正确，请按照模版格式要求输入");
				return Json(jp);
			}	
			
			//0820 增加额度限制 单笔不超过1万
			if(Integer.parseInt(recharge_amt.replace(".", "")) > 1000000){
				//单笔超1万
				obj.clear();
				obj.put("xh", xh);
				obj.put("user_no", user_no);
				obj.put("user_lname", user_lname);
				obj.put("recharge_amt", recharge_amt);
				obj.put("message", "超单笔系统限额");
				rstErrArry.add(obj);
				continue;
			}
				
			boolean hasFlag = false;
			for (int j = 0; j < dMaplst.size(); j++){
				Map<String, Object> dmap = (Map<String,Object>)dMaplst.get(j);
				if(dmap.get("user_no").equals(paramMap.get("user_no"))){
					if(!dmap.get("user_lname").equals(paramMap.get("user_lname"))){
						//姓名不同
						obj.clear();
						obj.put("xh", xh);
						obj.put("user_no", user_no);
						obj.put("user_lname", user_lname);
						obj.put("recharge_amt", recharge_amt);
						obj.put("message", "姓名内容不符");
						rstErrArry.add(obj);						
					}
					else	
					if(!dmap.containsKey("account_id")){
						//未开户
						obj.put("xh", xh);
						obj.put("user_no", user_no);
						obj.put("user_lname", user_lname);
						obj.put("recharge_amt", recharge_amt);
						obj.put("message", "未开户");
						rstErrArry.add(obj);						
					}
					else if(dmap.containsKey("account_state") && 
							"2".equals(String.valueOf(paramMap.get("account_state"))) ){
						//已销户
						obj.put("xh", xh);
						obj.put("user_no", user_no);
						obj.put("user_lname", user_lname);
						obj.put("recharge_amt", recharge_amt);
						obj.put("message", "已销户");
						rstErrArry.add(obj);
					}
					else{
						//返回成功结果
						//account_id,user_serial,user_depname,user_lname,user_duty,user_no,
						//account_end_date,account_state_code,account_state_name,cash_amt
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_lname", dmap.get("user_lname"));
						objt.put("user_duty", dmap.get("user_duty"));
						objt.put("user_id", dmap.get("user_id"));
						objt.put("user_no", dmap.get("user_no"));
						objt.put("account_end_date", dmap.get("account_end_date"));
						objt.put("account_state_code", dmap.get("account_state_code"));
						objt.put("account_state_name", dmap.get("account_state_name"));						
						objt.put("cash_amt", dmap.get("cash_amt"));
						objt.put("cash_money", recharge_amt);		
						recharge_amt = recharge_amt.replace(".", "");
						Integer cash_after_money = (Integer)dmap.get("cash_amt") + 
								Integer.parseInt(recharge_amt);
						objt.put("cash_after_money", cash_after_money);
						rstOkArry.add(objt);
						//log.debug(objt);
					}
					hasFlag = true;
					break;
				}				
			}
			if(!hasFlag){
				//不存在
				obj.put("xh", xh);
				obj.put("user_no", user_no);
				obj.put("user_lname", user_lname);
				obj.put("recharge_amt", recharge_amt);
				obj.put("message", "工号人员不存在");
				rstErrArry.add(obj);
			}
		}		
		
		//返回结果	
		if(rstOkArry.size() > 0){		
			jp.setRecord(rstOkArry);
			jp.setTotal(rstOkArry.size());
		}		
		if(rstErrArry.size() > 0){
			jp.setRetDatas(rstErrArry);
			jp.setRetInfo(-1, "存在异常数据，导入失败");
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 批量充值-充值操作 recharge
	 */
	@RequestMapping("/bRecharge/recharge")
	public ModelAndView bRechargerecharge(JsonPage jp) {
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 字段不能为空");
			return Json(jp);			
		}	
		String ip = jobj.getString("ip");
		String gly_no = jobj.getString("gly_no");
		JSONArray jarray = getJSONArray(jobj, "data");
		if (null == jarray || jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}
		
		String procName = "pro_fin_acc_recharge";
		String procParam = "@account_id,@cash_amt,@recharge_amt,$read_media_id," +
				"$read_card_number,@gly_no,$ip,#user_no,#user_lname,#dep_name,#errMsg";
		Integer suc_count = 0;
		Integer err_count = 0;	
		Integer rechargeamts = 0;
		JSONArray rstArry = new JSONArray();
		for (int i = 0; i < jarray.size(); i++){
			JSONObject obj = new JSONObject(); 
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			if(!paramMap.containsKey("account_id")){	
				obj.put("account_id", "");
				obj.put("recharge_state", 1);
				obj.put("message", "account_id 参数不能为空");
				rstArry.add(obj);
				continue;			
			}	
			
			Integer account_id = Integer.parseInt(paramMap.get("account_id").toString());
			if(!paramMap.containsKey("recharge_amt")){
				obj.put("account_id", account_id);
				obj.put("recharge_state", 1);
				obj.put("message", "recharge_amt 参数不能为空");
				rstArry.add(obj);
				continue;			
			}		
			Integer recharge_amt = Integer.parseInt(paramMap.get("recharge_amt").toString());	
			Integer cash_amt = Integer.parseInt(paramMap.get("cash_amt").toString());
			
			Map<String, Object> inparamMap = new HashMap<String, Object>();
			inparamMap.put("account_id", account_id);
			inparamMap.put("cash_amt", cash_amt);
			inparamMap.put("recharge_amt", recharge_amt);
			inparamMap.put("gly_no", gly_no);
			inparamMap.put("ip", ip);
			//log.debug(inparamMap);
			commonDao.callProc(jp, procName, procParam, inparamMap);
			String msg = (String)inparamMap.get("errMsg");
			if(StringUtils.isNotEmpty(msg)){
				obj.put("account_id", account_id);
				obj.put("user_no", inparamMap.get("user_no"));
				obj.put("user_lname", inparamMap.get("user_lname"));
				obj.put("dep_name", inparamMap.get("dep_name"));
				obj.put("recharge_state", 1);
				obj.put("message", msg);
				rstArry.add(obj);
				err_count++;
			}
			else{
//				obj.put("account_id", account_id);
//				obj.put("user_no", inparamMap.get("user_no"));
//				obj.put("user_lname", inparamMap.get("user_lname"));
//				obj.put("dep_name", inparamMap.get("dep_name"));				
//				obj.put("recharge_state", 0);
//				obj.put("message", "");
//				rstArry.add(obj);
//				log.debug(obj);
				suc_count++;
				rechargeamts = rechargeamts + recharge_amt;				
			}	
		}
		if(rstArry.size() > 0){
			jp.setRecord(rstArry);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("err_count", err_count)
				.put("suc_count", suc_count)
				.put("rechargeamts", rechargeamts)
				.build();
		jp.setRetData(retMap);
		return Json(jp);
	}	
	
	//******************************************************
	//取款 withdraw：查部门、查取款明细、筛选查询、取现金操作、取补贴操作
	//******************************************************
	/*--------------------------
	 * 取款-筛选查询 filterQry
	 */
	@RequestMapping("/withdraw/filterQry")
	public ModelAndView withdrawfilterQry(JsonPage jp) {
			Map<String, Object> paramMap = parseJsonMap(jp);
			log.debug(paramMap);
			String procName = "pro_fin_acc_widthdraw_filterqry";
			String procParam = "$user_no,$dep_serial,$user_lname,$user_id,@gly_no,@cx_type"; 
			commonDao.callProc(jp, procName, procParam, paramMap);
			return Json(jp);
	}
	/*--------------------------
	 * 取款-查取款明细 qryWithdraw
	 * 与充值明细查询调用同一个存储过程
	 */
	@RequestMapping("/withdraw/qryWithdraw")
	public ModelAndView withdrawqryWithdraw(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_recharge_listqry"; 
		String procParam = "@gly_no,#incomes,#pays,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);

		Map<String, Object> retMap = new MapBuilder()
				.put("incomes", paramMap.get("incomes"))
				.put("pays", paramMap.get("pays"))
				.build();
		jp.setRetData(retMap);
		return Json(jp);
	}
	/*--------------------------
	 * 取款-取现金/补贴操作	cashOut
	 */
	@RequestMapping("/withdraw/cashOut")
	public ModelAndView withdrawcashOut(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_withdraw";
		String procParam = "@account_id,@cash_amt_before,@withdraw_amt,@gly_no,$read_media_id,$read_card_number,@withdraw_lx,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//读卡器管理 cardReader：读卡器类型查询、卡座信息查询、系统参数设置、卡片参数设置、日志
	//******************************************************
	/*--------------------------
	 * 读卡器管理-读卡器类型查询 qryCardReader
	 */
	@RequestMapping("/cardReader/qryCardReader")
	public ModelAndView qryCardReader(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_cardreader_qryCardReader";
		String procParam = "$id";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}	
	/*--------------------------
	 * 读卡器管理-卡座信息 qryCardSlot
	 */
	@RequestMapping("/cardReader/qryCardSlot")
	public ModelAndView cardReaderqryCardSlot(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_cardreader_qryCardSlot";
		String procParam = "$cardreader_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 读卡器管理-系统参数设置 sysKeySet
	 */
	@RequestMapping("/cardReader/sysKeySet")
	public ModelAndView cardReadersysKeySet(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_cardreader_sysKeySet";
		String procParam = "";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 读卡器管理-卡片参数设置 cardTypeSet
	 */
	@RequestMapping("/cardReader/cardTypeSet")
	public ModelAndView cardReadercardTypeSet(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_cardreader_cardTypeSet";
		String procParam = "$is_write";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 读卡器管理-日志设置 cardreaderLog
	 */
	@RequestMapping("cardReader/cardreaderLog")
	public ModelAndView cardreaderLog(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_log";
		String procParam = "$lx,$log_name,@log_bz,$log_table,$log_key,$log_value,"+
		       "$log_content_old,$log_content_new,$log_state,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//帐户管理 account：筛选查询、保存设置、重置帐户密码
	//******************************************************
	/*--------------------------
	 * 帐户管理-筛选查询 filterQry
	 */
	@RequestMapping("/account/filterQry")
	public ModelAndView accountfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		// modify by zlibo 20201723
		String procName = "pro_fin_acc_account_filterQry_gly";
		String procParam = "$user_no,$dep_serial,$identity_id,$user_lname,@cx_type,$page_no,$page_size,#total,$gly_no";
//		String procName = "pro_fin_acc_account_filterQry";
//		String procParam = "$user_no,$dep_serial,$identity_id,$user_lname,@cx_type,$page_no,$page_size,#total";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 账户管理-重置帐户密码 setPswAaccount
	 */
	@RequestMapping("/account/setPswAccount")
	public ModelAndView setPswAaccount(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_account_setpwd";
		String procParam = "@account_id,@account_pwd,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 帐户管理-保存 save
	 */
	@RequestMapping("/account/save")
	public ModelAndView accountsave(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_account_save";
		String procParam = "@account_id,@event_id,$account_end_date,$account_pwd,$read_card_number,"+
				"$read_media_id,@gly_no,$ip,#suc_count,#err_count,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
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
	//卡片管理 card：筛选查询,持卡记录查询,发卡,退卡,挂失,解挂,补卡,更新有效期,重置卡片密码,黑名单验证,
	//清除发补卡中间状态,卡片状态验证（发/补）、卡号导入
	//******************************************************
	/*--------------------------
	 * 卡片管理-筛选查询 filterQry
	 */
	@RequestMapping("/card/filterQry")
	public ModelAndView cardfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_filterQry";
		String procParam = "$account_id,$user_no,$dep_serial,$identity_id,$user_lname,$account_condition,"+
				"@cx_type,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-筛选查询2(包含离职人员)-卡片充值管理 filterQry2 
	 */
	@RequestMapping("/card/filterQry2")
	public ModelAndView cardfilterQry2(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		// modify by zlibo 20201723
		String procName = "pro_fin_acc_card_filterQry2_gly";
		String procParam = "$account_id,$user_no,$dep_serial,$identity_id,$user_lname,$user_id,$card_state,"+
				"$account_condition,@cx_type,$page_no,$page_size,#total,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		jp.setTotal((Integer)paramMap.get("total"));		
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-筛选查询3(不包含离职人员)-批量发卡 filterQry3 
	 */
	@RequestMapping("/card/filterQry3")
	public ModelAndView cardfilterQry3(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);

		log.debug(paramMap);
		// modify by zlibo 20201723
		String procName = "pro_fin_acc_card_filterQry3_gly";
		String procParam = "$account_id,$user_no,$dep_serial,$identity_id,$user_lname,$user_id,$card_state,"+
				"$account_condition,@cx_type,$page_no,$page_size,#total,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}	
		jp.setTotal((Integer)paramMap.get("total"));		
		return Json(jp);
	}	
	/*--------------------------
	 * 卡片管理-持卡记录查询 cardListQry
	 */
	@RequestMapping("/card/cardListQry")
	public ModelAndView cardListQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_cardListQry";
		String procParam = "@account_id";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-帐户信息查询accountQryInfo
	 */
	@RequestMapping("/card/accountQryInfo")
	public ModelAndView cardaccountQryInfo(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_accountQryInfo";
		String procParam = "@account_id";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片押金验证cardDeposit
	 */
	@RequestMapping("/card/cardDeposit")
	public ModelAndView cardDeposit(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_deposit_test";
		String procParam = "@account_id,@old_card_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-发卡addCard
	 */
	@RequestMapping("/card/addCard")
	public ModelAndView cardaddCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_addcard";
		String procParam = "@account_id,@card_number,@media_id,@is_main_card,@gly_no,@old_card_serial,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-读写卡发卡后部分writeafter
	 */
	@RequestMapping("/card/writeAfter")
	public ModelAndView cardwriteAfter(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_add_writeafter";
		//String procParam = "@account_id,@card_number,@media_id,@is_main_card,@gly_no,@old_card_serial,#errMsg";
		String procParam = "@account_id,@card_serial,@gly_no,@old_card_serial,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-挂失lossCard
	 */
	@RequestMapping("/card/lossCard")
	public ModelAndView cardlossCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_loss";
		String procParam = "@account_id,@card_serial,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-解挂unlossCard
	 */
	@RequestMapping("/card/unlossCard")
	public ModelAndView unlossCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_unlossCard";
		String procParam = "@account_id,@card_serial,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-退卡backCard
	 */
	@RequestMapping("/card/backCard")
	public ModelAndView backCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_backCard";
		String procParam = "@account_id,@card_serial,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-退卡后半部分backCardAfter
	 */
	@RequestMapping("/card/backCardAfter")
	public ModelAndView backCardAfter(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_backCard_after";
		String procParam = "@account_id,@card_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-更新有效期setValidityCard
	 */
	@RequestMapping("/card/setValidityCard")
	public ModelAndView setValidityCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_setValidityCard";
		String procParam = "@account_id,@card_serial,@end_date,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-重置卡片密码setPswCard
	 */
	@RequestMapping("/card/setPswCard")
	public ModelAndView setPswCard(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_setPswCard";
		String procParam = "@account_id,@card_serial,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-保存卡片认证码saveAuthcode
	 */
	@RequestMapping("/card/saveAuthcode")
	public ModelAndView saveAuthcode(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_saveAuthcode";
		String procParam = "@card_serial,@card_number,@media_id,@card_authcode,$sector_list,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-同步钱包、同步补贴、同步累计
	 */
	@RequestMapping("/card/onlineQueryInfo")
	public ModelAndView cardOnlineQueryInfo(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_acc_card_onlineQueryInfo";
		String procParam = "@media_id,@card_serial,$card_number,@card_trad_serial,@device_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-离线记录 readOffline
	 */
	@RequestMapping("/card/readOffline")
	public ModelAndView cardReadOffline(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_acc_card_read_offline";
		String procParam = "@jdevid,@jdev_bh,@jcmdline,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片有效性验证 cardValid
	 */
	@RequestMapping("/card/cardValid")
	public ModelAndView cardValid(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_valid";
		String procParam = "@account_id,@card_number,@media_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}	
	/*--------------------------
	 * 卡片管理-清除发补卡中间状态 clearCardzt
	 */
	@RequestMapping("/card/clearcardzt")
	public ModelAndView clearcardzt(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_clearcardzt";
		String procParam = "@account_id,@card_serial,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片状态验证cardState
	 */
	@RequestMapping("/card/cardState")
	public ModelAndView cardState(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_state";
		String procParam = "@card_number,@media_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-发补卡状态验证1 CardVerification
	 */
	@RequestMapping("/card/cardVerification")
	public ModelAndView cardVerification(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_verification";
		String procParam = "@account_id,@id,@card_number,@media_id,@is_main_card,@gly_no,@old_card_serial,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-发补卡状态验证提示 CardVerificationts
	 */
	@RequestMapping("/card/cardVerificationts")
	public ModelAndView cardVerificationts(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_verificationts";
		String procParam = "@account_id,@card_number,@media_id,#card_serial,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("card_serial", paramMap.get("card_serial"))
				.build();
		jp.setRetData(retMap);

		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-主卡做附卡发放验证 isMainCardTest
	 */
	@RequestMapping("/card/isMainCardTest")
	public ModelAndView  isMainCardTest(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_isMainTest";
		String procParam = "@card_number,@media_id,@is_main_card,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片升级 cardUpdate
	 */
	@RequestMapping("/card/cardUpdate")
	public ModelAndView  cardUpdate(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_update";
		String procParam = "@juser_card,@jin_type,@jCardLogicID,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-是否是升级中的卡片 ifCardUpdate
	 */
	@RequestMapping("/card/ifCardUpdate")
	public ModelAndView  ifCardUpdate(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_ifupdate";
		String procParam = "@card_number,@media_id,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片状态 cardStateQry
	 */
	@RequestMapping("/card/cardStateQry")
	public ModelAndView cardStateQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_stateqry";
		String procParam = "";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}
	/*--------------------------
	 * 卡片管理-卡片明细列表 cardDetailQry
	 */
	@RequestMapping("/card/cardDetailQry")
	public ModelAndView cardDetailQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		// modify by zlibo 20201723
		String procName = "pro_fin_acc_card_detailQry_gly";
		String procParam = "$dep_serial,$identity_id,$card_no,$card_lx,$card_state,"+
				"$is_main_card,$card_number,$media_id,#sum_charge,#errMsg,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		String msg = (String)paramMap.get("errMsg");
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		Map<String, Object> retMap = new MapBuilder()
				.put("sum_charge", paramMap.get("sum_charge"))
				.build();
		jp.setRetData(retMap);		
		return Json(jp);
	} 
	/*--------------------------
	 * 批量发卡-筛选查询 cardBatchQry
	 */
	@RequestMapping("/card/cardBatchQry")
	public ModelAndView cardBatchQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_batchqry";
		String procParam = "$user_no,$dep_serial,$identity_id,$user_id,@lx,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 批量发卡-筛选查询 cardBatchQryOpenacc
	 */
	@RequestMapping("/card/cardBatchQryOpenacc")
	public ModelAndView cardBatchQryOpenacc(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_card_batchqry_openacc";
		String procParam = "@openacc_id,@gly_no,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp); 
	}	
	/*--------------------------
	 * 卡片管理-卡号导入 impCard
	 */
	@RequestMapping("/card/impCard")
	public ModelAndView cardimpCard(JsonPage jp) {
		//定义jobj: 入参 gly_no,card_lx,data[xh,user_no,user_lname,user_card]
		JSONObject jobj = parseJson(jp);
		if(!jobj.containsKey("gly_no")){
			jp.setRetInfo(-1, "gly_no 参数不能为空");
			return Json(jp);			
		}
		String gly_no = jobj.getString("gly_no");
		if(!jobj.containsKey("card_lx")){
			jp.setRetInfo(-1, "卡类型参数不能为空");
			return Json(jp);			
		}
		String cardlx = jobj.getString("card_lx") + "";	
		//取卡类型名称
		String card_lxmc = "";
		String strsql = "select isnull(card_type_name,'') as card_type_name from fin_tt_card_type " +
				" where card_media_type=" + cardlx;
		log.debug(strsql);
		Map<String, Object> mapCardlx = commonDao.queryForMap(strsql);
		if(mapCardlx.containsKey("card_type_name")){
			card_lxmc = mapCardlx.get("card_type_name") + "";
		}
		
		//取持卡最大数量限制
		Integer hold_card_numb = 0;
		strsql = "select isnull(hold_card_numb,0) as hold_card_numb from fin_system_set ";
		//log.debug(strsql);
		Map<String, Object> mapCardNum = commonDao.queryForMap(strsql);
		if(mapCardNum.containsKey("hold_card_numb")){
			hold_card_numb = Integer.parseInt(mapCardNum.get("hold_card_numb") + "");
		}		
			
		//定义jarray: data数据域 data[xh,user_no,user_lname,user_card]
		JSONArray jarray = getJSONArray(jobj, "data");
		if (null == jarray || jarray.size()<1){
			jp.setRetInfo(-1, "未检测到data具体参数信息");
			return Json(jp);
		}	
		//log.debug(jarray);
		//数据检测开始
		//取所有DB数据到内存待比较 
		String sql = "select a.account_id,a.account_state,u.user_serial,u.user_no,u.user_lname,u.user_sex, " + 
				"  u.user_depname,t.tt_name as user_identity,CONVERT(varchar(20),a.account_end_date,120) as account_end_date, "+
				" case isnull(q.finger_enable,0) when 0 then '禁用' when 1 then '启用' else '' end as finger_enable"+
				" ,isnull(card_count,0) as card_count,isnull(datediff(DAY,getdate(),a.account_end_date),-1) as acc_yxq " + 
				" from dt_user u" +
				" left join (select a1.* from fin_account a1 inner join ("+
				" select user_serial,max(account_id) account_id from fin_account"+
				" group by user_serial) b1 on a1.account_id=b1.account_id)	"+
				" a on u.user_serial=a.user_serial" +
				" left join (" +
				" select account_id,COUNT(1) as finger_enable from fin_card" +
				" where is_main_card=0  and card_state=0 and  media_id =113 group by account_id) q  " +
				" on a.account_id =q.account_id " +
				" left join (" +
				" select account_id,COUNT(1) as card_count from fin_card where (card_state in (0,4,6,7) or "+
				" (card_state=1 and is_card_replace=0)) and media_id<>113 group by account_id) c on a.account_id =c.account_id " +
				" left join tt_identity t on u.user_identity=t.tt_order " +
				" where u.user_type<51";
		//定义dMaplst：数据库内存数据--人员信息
		List<Map<String,Object>> dMaplst = commonDao.queryForList(sql);
		//Map<String, Object> rstMap = new HashMap<String, Object>();
		//log.debug(dMaplst);
		
		sql = "select u.user_no,u.user_lname,c.account_id from fin_card c left join fin_account a on "+
				" c.account_id=a.account_id left join dt_user u on a.user_serial=u.user_serial where "+
				" (c.card_state in (0,4,6,7) or (c.card_state=1 and c.is_card_replace=0)) and c.media_id<>113 "+
				" and card_number=:usercard ";			
		JSONArray rstArry = new JSONArray();
		JSONArray rstokArry = new JSONArray();
		for (int i = 0; i < jarray.size(); i++){
			//定义paramMap：data[i] = [xh,user_no,user_lname,user_card]
			Map<String, Object> paramMap = (Map<String,Object>)jarray.get(i);
			if(!paramMap.containsKey("xh")){ 
				jp.setRetInfo(-1, "序号不能为空");
				return Json(jp);
			}
			String xh = String.valueOf(paramMap.get("xh"));
			if(!paramMap.containsKey("user_no")){ 
				JSONObject obj = new JSONObject(); 
				obj.put("xh", xh);
				obj.put("user_no", "");
				int kong = 1;
				if(paramMap.containsKey("user_lname")){
					obj.put("user_lname", paramMap.get("user_lname"));
				}
				else{
					kong = kong + 1;
					obj.put("user_lname", "");
				}
				if(paramMap.containsKey("user_card")){
					obj.put("user_card", paramMap.get("user_card"));
				}
				else{
					kong = kong + 1;
					obj.put("user_card", "");
				}		
				if (kong != 3){
					obj.put("message", "学/工号不能为空");
					rstArry.add(obj);					
				}
				continue;
			}	
			String userno = paramMap.get("user_no") + "";
			if(!paramMap.containsKey("user_lname")){ 
				JSONObject obj = new JSONObject(); 
				obj.put("xh", xh);
				obj.put("user_no", userno);
				obj.put("user_lname", "");
				if(paramMap.containsKey("user_card")){
					obj.put("user_card", paramMap.get("user_card"));
				}
				else{
					obj.put("user_card", "");
				}					
				obj.put("message", "姓名不能为空");
				rstArry.add(obj);
				continue;
			}			
			String userlname = paramMap.get("user_lname") + "";
			if(!paramMap.containsKey("user_card")){ 
				JSONObject obj = new JSONObject(); 
				obj.put("xh", xh);
				obj.put("user_no", userno);
				obj.put("user_lname", userlname);
				obj.put("user_card", "");
				obj.put("message", "卡号不能为空");
				rstArry.add(obj);
				continue;
			}
			//log.debug("userlname:"+userlname);		
			String usercard = paramMap.get("user_card") + "";
			usercard=usercard.toUpperCase();
			
			boolean hasFlag = false;
			for (int j = 0; j < dMaplst.size(); j++){
				//定义dmap：DB人员信息[i] = [account_id,account_state,user_serial,user_no,user_lname,....]
				Map<String, Object> dmap = (Map<String,Object>)dMaplst.get(j);	
				if(userno.equals(dmap.get("user_no"))){
					if(!userlname.equals(dmap.get("user_lname"))){
				//if(dmap.get("user_no").equals(paramMap.get("user_no"))){
					//if(!dmap.get("user_lname").equals(paramMap.get("user_lname"))){
						//姓名不同
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("account_state", dmap.get("account_state"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", userno);
						objt.put("user_lname", userlname);
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_identity", dmap.get("user_identity"));
						objt.put("card_no", usercard);
						objt.put("card_lx", cardlx);
						objt.put("card_lxmc", card_lxmc);
						objt.put("account_end_date", dmap.get("account_end_date"));
						objt.put("finger_enable", dmap.get("finger_enable"));							
						objt.put("state", 1);
						objt.put("state_mc", "未通过验证");
						objt.put("bz", "姓名内容不符:"+dmap.get("user_lname"));
						rstokArry.add(objt);						
					}
					else if(!dmap.containsKey("account_id")){
						//未开户
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", "");
						objt.put("account_state", "");
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", userno);
						objt.put("user_lname", userlname);
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_identity", dmap.get("user_identity"));
						objt.put("card_no", usercard);
						objt.put("card_lx", cardlx);
						objt.put("card_lxmc", card_lxmc);
						objt.put("account_end_date", "");
						objt.put("finger_enable", "");							
						objt.put("state", 1);
						objt.put("state_mc", "未通过验证");
						objt.put("bz", "账户未开户");
						rstokArry.add(objt);						
					}
					else if(Integer.parseInt(dmap.get("acc_yxq")+"") < 0){
						//账户过有效期
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("account_state", dmap.get("account_state"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", userno);
						objt.put("user_lname", userlname);
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_identity", dmap.get("user_identity"));
						objt.put("card_no", usercard);
						objt.put("card_lx", cardlx);
						objt.put("card_lxmc", card_lxmc);
						objt.put("account_end_date", dmap.get("account_end_date"));
						objt.put("finger_enable", dmap.get("finger_enable"));							
						objt.put("state", 1);
						objt.put("state_mc", "未通过验证");
						objt.put("bz", "账户已过有效期");
						rstokArry.add(objt);							
					}					
					else if(Integer.parseInt(dmap.get("card_count")+"") >= hold_card_numb){
						//超过最大持卡数量
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("account_state", dmap.get("account_state"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", userno);
						objt.put("user_lname", userlname);
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_identity", dmap.get("user_identity"));
						objt.put("card_no", usercard);
						objt.put("card_lx", cardlx);
						objt.put("card_lxmc", card_lxmc);
						objt.put("account_end_date", dmap.get("account_end_date"));
						objt.put("finger_enable", dmap.get("finger_enable"));							
						objt.put("state", 1);
						objt.put("state_mc", "未通过验证");
						objt.put("bz", "已达到最大持卡数量限制");
						rstokArry.add(objt);						
					}
					else if(dmap.containsKey("account_state") && 
							("2".equals(dmap.get("account_state") + "") || "1".equals(dmap.get("account_state") + ""))){
						//已冻结、销户				
						JSONObject objt = new JSONObject(); 
						objt.put("account_id", dmap.get("account_id"));
						objt.put("account_state", dmap.get("account_state"));
						objt.put("user_serial", dmap.get("user_serial"));
						objt.put("user_no", userno);
						objt.put("user_lname", userlname);
						objt.put("user_sex", dmap.get("user_sex"));
						objt.put("dep_name", dmap.get("user_depname"));
						objt.put("user_identity", dmap.get("user_identity"));
						objt.put("card_no", usercard);
						objt.put("card_lx", cardlx);
						objt.put("card_lxmc", card_lxmc);
						objt.put("account_end_date", dmap.get("account_end_date"));
						objt.put("finger_enable", dmap.get("finger_enable"));							
						objt.put("state", 1);
						objt.put("state_mc", "未通过验证");
						objt.put("bz", "账户已冻结或销户");						
						rstokArry.add(objt);
					}				
					else{
//						sql = "select account_id from fin_card where (card_state in (0,4,6,7) or "+
//								" (card_state=1 and is_card_replace=0)) and media_id<>113 and card_number=:usercard ";
						Map<String, Object> tmpcardMap = new MapBuilder()
								.put("usercard", usercard)
								.build();						
						List<Map<String,Object>> cardlst = commonDao.queryForList(sql, tmpcardMap);
						if(cardlst.size() > 0){
							//卡片已存在
							Map<String, Object> dcardmap = (Map<String,Object>)dMaplst.get(0);
							JSONObject objt = new JSONObject(); 
							objt.put("account_id", dmap.get("account_id"));
							objt.put("account_state", dmap.get("account_state"));
							objt.put("user_serial", dmap.get("user_serial"));
							objt.put("user_no", dmap.get("user_no"));
							objt.put("user_lname", dmap.get("user_lname"));
							objt.put("user_sex", dmap.get("user_sex"));
							objt.put("dep_name", dmap.get("user_depname"));
							objt.put("user_identity", dmap.get("user_identity"));
							objt.put("card_no", usercard);
							objt.put("card_lx", cardlx);
							objt.put("card_lxmc", card_lxmc);
							objt.put("account_end_date", dmap.get("account_end_date"));
							objt.put("finger_enable", dmap.get("finger_enable"));							
							objt.put("state", 1);
							objt.put("state_mc", "未通过验证");
							objt.put("bz", "卡片已存在(工号:" + dmap.get("user_no") + ",姓名:" + dmap.get("user_lname") + ")");
							rstokArry.add(objt);							
						}
						else{
							//返回成功结果
							JSONObject objt = new JSONObject(); 
							objt.put("account_id", dmap.get("account_id"));
							objt.put("account_state", dmap.get("account_state"));
							objt.put("user_serial", dmap.get("user_serial"));
							objt.put("user_no", dmap.get("user_no"));
							objt.put("user_lname", dmap.get("user_lname"));
							objt.put("user_sex", dmap.get("user_sex"));
							objt.put("dep_name", dmap.get("user_depname"));
							objt.put("user_identity", dmap.get("user_identity"));
							objt.put("card_no", usercard);
							objt.put("card_lx", cardlx);
							objt.put("card_lxmc", card_lxmc);
							objt.put("account_end_date", dmap.get("account_end_date"));
							objt.put("finger_enable", dmap.get("finger_enable"));							
							objt.put("state", 0);
							objt.put("state_mc", "正常");  //验证通过
							objt.put("bz", "");
							rstokArry.add(objt);								
						}
					}
					//log.debug(paramMap);
					hasFlag = true;
					break;
				}
			}
			if(!hasFlag){
				//不存在	
				JSONObject objt = new JSONObject(); 
				objt.put("account_id", "");
				objt.put("account_state", "");
				objt.put("user_serial", "");
				objt.put("user_no", userno);
				objt.put("user_lname", userlname);
				objt.put("user_sex", "");
				objt.put("dep_name", "");
				objt.put("user_identity", "");
				objt.put("card_no", usercard);
				objt.put("card_lx", cardlx);
				objt.put("card_lxmc", card_lxmc);
				objt.put("account_end_date", "");
				objt.put("finger_enable", "");				
				objt.put("state", 1);
				objt.put("state_mc", "未通过验证");
				objt.put("bz", "工号人员不存在");
				rstokArry.add(objt);				
			}
		}	
		dMaplst = null;
		if(rstArry.size() > 0){
			//检测结果中存在异常  组织返回
			jp.setRecord(rstArry);
			jp.setRetInfo(-1, "存在异常数据，导入失败");
			return Json(jp); 
		}
		//数据检测完成
		log.debug(rstokArry);
		//返回结果	
		if(rstokArry.size() > 0){		
			jp.setRecord(rstokArry);
			jp.setTotal(rstokArry.size());
		}
		return Json(jp);
	}	
	
	//******************************************************
	//充值纠错 rchCorrect：筛选查询,收支明细查询，充值纠错
	//******************************************************
	/*--------------------------
	 * 充值纠错-充值纠错 correct
	 */
	@RequestMapping("/rchCorrect/correct")
	public ModelAndView rchCorrect(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_rchCorrect";
		String procParam = "@account_id,@cash_amt_before,@rchCorrect_amt,$read_card_number,$read_media_id,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	//******************************************************
	//消费纠错 finCorrect：消费筛选查询,纠错操作
	//******************************************************
	/*--------------------------
	 * 消费纠错-消费筛选查询 finfilterQry
	 */
	@RequestMapping("/finCorrect/finfilterQry")
	public ModelAndView finCorrectfinfilterQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_finCorrect_finfilterQry";
		String procParam = "@cx_type,@bill_begin,@bill_end,$user_no,$user_lname,$dep_serial,$user_id,$merchant_account_id,$acdep_serial,$page_no,$page_size,#total,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	/*--------------------------
	 * 消费纠错-纠错操作 correct
	 */
	@RequestMapping("/finCorrect/correct")
	public ModelAndView finCorrect(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_acc_finCorrect";
		String procParam = "@id,@account_id,@undo_amt_before,@trad_amt,$read_media_id,$read_card_number,@bill_date,@gly_no,$ip,#errMsg";
		commonDao.callProc(jp, procName, procParam, paramMap);
		//StringUtils.isEmpty(cs)
		String msg = (String)paramMap.get("errMsg");
		log.debug(paramMap.get("errMsg"));
		if(StringUtils.isNotEmpty(msg)){
			jp.setRetInfo(-1, msg);
		}
		return Json(jp);
	}
	
	
	
	/*--------------------------
	 * 操作日志-查询 
	 */
	@RequestMapping("/log/logQry")
	public ModelAndView logQry(JsonPage jp) {
		Map<String, Object> paramMap = parseJsonMap(jp);
		log.debug(paramMap);
		String procName = "pro_fin_logqry";
		String procParam = "@sdate,@edate,$gly_no";
		commonDao.callProc(jp, procName, procParam, paramMap);
		return Json(jp);
	}

}
