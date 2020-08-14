package com.garen.finweb.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.garen.common.JsonPage;
import com.garen.sys.dao.ICommonDao;

@Controller
@RequestMapping("/finweb/base")
public final class BaseAction extends com.garen.common.BaseAction {
    /**
     * fin-页面操作-基础公共部分
     * 操作:查询部门
     * 输入、输出参数均为JSON格式
     */

    @Autowired
    private ICommonDao commonDao;

    //******************************************************
    //查询部门、查询身份、查询密码方案、查时间偏移量、查询商户、查询场所、查询管理员、查银行、读身份证查人员信息、查卡类型
    //******************************************************
    /*--------------------------
     * 查询部门 qryDep
     */
    @RequestMapping(value = {"/qryDep"})
    public ModelAndView qryDep(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select dep_serial,dep_parent,dep_order,dep_name,dep_no,dep_rule from dt_dep"; //  where unit_id=:unit_id
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }


    //******************************************************
    //查询部门、查询身份、查询密码方案、查时间偏移量、查询商户、查询场所、查询管理员、查银行、读身份证查人员信息、查卡类型
    //******************************************************
    /*--------------------------
     * 根据权限查询部门 qryDepByAccess
     */
    @RequestMapping(value = {"/qryDepByAccess"})
    public ModelAndView qryDepByAccess(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String glyNo = paramMap.get("gly_no").toString();

        String procName = "pro_fin_qry_dep";
        String procParam = "$gly_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String)paramMap.get("errMsg");
        if(StringUtils.isNotEmpty(msg)){
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*--------------------------
     * 根据权限查询场所 qryAcDepByAccess
     */
    @RequestMapping(value = {"/qryAcDepByAccess"})
    public ModelAndView qryAcDepByAccess(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String glyNo = paramMap.get("gly_no").toString();

        String procName = "pro_fin_qry_acdep";
        String procParam = "$gly_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String)paramMap.get("errMsg");
        if(StringUtils.isNotEmpty(msg)){
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*--------------------------
     * 根据权限查询设备 qryDevByAccess
     */
    @RequestMapping(value = {"/qryDevByAccess"})
    public ModelAndView qryDevByAccess(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String glyNo = paramMap.get("gly_no").toString();

        String procName = "pro_fin_qry_dev";
        String procParam = "$gly_no,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        String msg = (String)paramMap.get("errMsg");
        if(StringUtils.isNotEmpty(msg)){
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

//    /*--------------------------
//     * 根据权限查询商户 qryMerchantByAccess
//     */
//    @RequestMapping(value = {"/qryMerchantByAccess"})
//    public ModelAndView qryMerchantByAccess(JsonPage jp) {
//        Map<String, Object> paramMap = parseJsonMap(jp);
//        log.debug(paramMap);
//        String glyNo = paramMap.get("gly_no").toString();
//
//        String procName = "pro_fin_manage_authority_qryMerch";
//        String procParam = "$gly_no,#errMsg";
//        commonDao.callProc(jp, procName, procParam, paramMap);
//        String msg = (String)paramMap.get("errMsg");
//        if(StringUtils.isNotEmpty(msg)){
//            jp.setRetInfo(-1, msg);
//        }
//        return Json(jp);
//    }



    /*--------------------------
     * 查询身份 qryIdentity
     */
    @RequestMapping("/qryIdentity")
    public ModelAndView qryIdentity(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select tt_order as ident_id,tt_name as ident_name from tt_identity";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查时间偏移量 qrySysOffset
     */
    @RequestMapping("/qrySysOffset")
    public ModelAndView qrySysOffset(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String sql = "select offset from fin_system_set";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查询密码方案 qryPswRule
     */
    @RequestMapping("/qryPswRule")
    public ModelAndView qryPswRule(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String sql = "select scheme_id,scheme_name from fin_tt_pwd_Scheme";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查询商户 qryMerch 20170505整改取消 采用授权查询
     */
//	@RequestMapping("/qryMerch")
//	public ModelAndView qryMerch(JsonPage jp) {
//		Map<String, Object> paramMap = parseJsonMap(jp);
//		log.debug(paramMap);
//		//String sql = "select merchant_account_id,merchant_name,link_man,(select dep_name from fin_merchant_dep where dep_serial=merchant_dep) as merchant_dep, case merchant_account_type when 0 then '经营户' else '其它' end  as merchant_account_type,fee_rate,merchant_addr,merchant_bank_account,merchant_bank,merchant_telephone,merchant_dep as dep_serial,merchant_account_type as type from fin_merchant_account"; //  where unit_id=:unit_id
//		//commonDao.queryForList(jp, sql, paramMap);
//		String procName = "pro_fin_merch_filterqry";
//		String procParam = "$page_no,$page_size,#total,#errMsg";
//		commonDao.callProc(jp, procName, procParam, paramMap);
//		return Json(jp);
//	}


    /*--------------------------
     * 查询管理员
     */
    @RequestMapping("/qryGly")
    public ModelAndView qryGly(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String glyNo = paramMap.get("gly_no").toString();
        String sql = "select gly_no,gly_lname from wt_gly where gly_no='"+glyNo+"'";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }


    /*--------------------------
     * 读卡返回信息 ReadCardInfo
     */
    @RequestMapping("/readCardInfo")
    public ModelAndView readCardInfo(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_acc_card_ReadCardInfo";
        String procParam = "@card_number,@media_id,$account_condition,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        //StringUtils.isEmpty(cs)
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }


    /*--------------------------
     * 查询主附卡
     */
    @RequestMapping("/qryIsMainCard")
    public ModelAndView qryIsMainCard(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_acc_card_qryIsMainCard";
        String procParam = "@account_id,@card_number,@media_id,#errMsg";
        commonDao.callProc(jp, procName, procParam, paramMap);
        //StringUtils.isEmpty(cs)
        String msg = (String) paramMap.get("errMsg");
        log.debug(paramMap.get("errMsg"));
        if (StringUtils.isNotEmpty(msg)) {
            jp.setRetInfo(-1, msg);
        }
        return Json(jp);
    }

    /*
     * 查询核算单位名称
     */
    @RequestMapping("/qryUnit")
    public ModelAndView qryUnit(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        String sql = "select unit_name from fin_account_unit where unit_id=1";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*
     * 充值、取款、充值纠错打印小票
     */
    @RequestMapping("/tradPrint")
    public ModelAndView tradPrint(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String procName = "pro_fin_base_trad_print";
        String procParam = "@account_id,@trad_amt,@trad_before_amt,@event_lx,@gly_no";
        commonDao.callProc(jp, procName, procParam, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查询科目
     */
    @RequestMapping("/qrySubject")
    public ModelAndView qrySubject(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select subject_no,subject_name,subject_direct,subject_type," +
                "parent_subject_no,bz,sj from fin_tt_subject";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查询银行
     */
    @RequestMapping("/qryBank")
    public ModelAndView qryBank(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select bank_id,bank_name,bank_name_short from fin_bank";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 读身份证查人员信息
     */
    @RequestMapping("/qryUserID")
    public ModelAndView qryUserID(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select user_serial,user_no,user_lname,user_dep,user_depname,user_identity,user_type " +
                "  from dt_user where [user_id]=:user_id";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }

    /*--------------------------
     * 查卡类型
     */
    @RequestMapping("/qryCardType")
    public ModelAndView qryCardType(JsonPage jp) {
        Map<String, Object> paramMap = parseJsonMap(jp);
        log.debug(paramMap);
        String sql = "select card_media_type,card_type_name from fin_tt_card_type";
        commonDao.queryForList(jp, sql, paramMap);
        return Json(jp);
    }
}
