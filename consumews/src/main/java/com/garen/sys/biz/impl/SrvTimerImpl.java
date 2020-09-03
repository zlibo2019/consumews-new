package com.garen.sys.biz.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;

import com.alibaba.fastjson.JSONObject;
import com.garen.finweb.web.DevAction;
import com.garen.sys.dao.ICommonDao;
import com.garen.sys.utils.AESCoder;
import com.garen.sys.utils.DESCoder;
import com.itextpdf.text.pdf.languages.DevanagariLigaturizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garen.sys.biz.ISrvTimer;
import com.garen.sys.dao.IFinCmdDao;
import com.garen.sys.entity.FinCmd;
import com.garen.sys.web.OnLineListener;

@Service("srvTimerImpl")
public class SrvTimerImpl implements ISrvTimer {

	private OnLineListener onlineListener;
	
	protected static Log log = LogFactory.getLog(ISrvTimer.class);   
	@Autowired
	private IFinCmdDao finCmdDao;

	@Autowired
	private ICommonDao commonDao;

	//服务状态 : 1 启用 2 停止中 3 停止
	private int status = 0;
	private int stopCount;//停止中超时计时
	public SrvTimerImpl(){
		onlineListener = OnLineListener.getInst();
		log.debug("service 定时器1" + onlineListener);
	}
	@PostConstruct
	private void doTimer(){//定时器监控数据源连接池
		log.debug("service 定时器2");
		TimerTask task = new TimerTask(){
			 public void run(){
				 try{
					 FinCmd fincmd = getCmd();
					 String cmdContent = fincmd.getCmdContent();
					 int execState = fincmd.getExecState();
					 if("1".equals(cmdContent)){
						 status = 1;
						 //如果库为1 则不修改库了
						 if(1 != execState) updateCmd("1",1);
					 }else if("2".equals(cmdContent) ){
						 if(execState != 1)
							 status = 2;
						 else status = 3;
					 }
					 if(status == 2){//停止中超时状态
						 if(stopCount == 3) {
							 status = 3;
							 updateCmd("2",2);//超时更改状态为失败
						 }else stopCount++;
					 }
					 setOnlineCount();
					 log.debug("服务状态监控中..." + status);
				 }catch(Exception ex){
					 log.debug("连接池监控错误" + ex.getMessage());
				 }
			}
		};
		Timer t = new Timer();
		t.schedule(task,3 * 1000,20 * 1000);//延迟3秒启动，然后每隔xx秒执行一次
	}


	@PostConstruct
	private void verifyDevTimer(){//定时器监控数据源连接池
		log.debug("service dev 定时器3");
		TimerTask task = new TimerTask(){
			public void run(){
				try{
					String sql = "select dev_sb_id,isnull(dev_sb_verify,'') dev_sb_verify from st_device"; //where dev_lb = 13";
					List<Map<String,Object>> listMap = commonDao.queryForList(sql);
					for (int i = 0; i < listMap.size(); i++){
						Map<String, Object> curMap = (Map<String,Object>)listMap.get(i);
						String devSbId = curMap.get("dev_sb_id").toString();
						String devSbVerify = curMap.get("dev_sb_verify").toString();
						if(devSbVerify.equals("")){
							sql = "delete from st_device where dev_sb_id = "+devSbId;
							commonDao.update(sql,null);
						}else{
							String cryptData = AESCoder.encrypt(devSbId, "1234567890123zlb", "0123456789012345");
							if(!cryptData.equals(devSbVerify)){
								sql = "delete from st_device where dev_sb_id = "+devSbId;
								commonDao.update(sql,null);
							}
						}
					}
				}catch(Exception ex){
					log.debug("dev监控错误" + ex.getMessage());
				}
			}
		};
		Timer t = new Timer();
		t.schedule(task,3 * 1000,1 * 1000);//延迟3秒启动，然后每隔xx秒执行一次24 * 3600000
	}


	//更新指令
	private void updateCmd(String content,int state){
		stopCount = 0;
		FinCmd fincmd = new FinCmd();
		fincmd.setServiceId(1);
		fincmd.setCmdContent(content);
		fincmd.setExecState(state);
		finCmdDao.updateByField(fincmd,"service_id,cmd_content");
	}
	
	private FinCmd getCmd(){
		FinCmd fincmd = new FinCmd();
		fincmd.setServiceId(1);
		//fincmd.setExecState(0);
		fincmd = finCmdDao.get(fincmd);
		if(fincmd == null) fincmd = new FinCmd();
		return fincmd;
	}
	
	@Override
	public boolean getSvrStatus() {
		return status > 1?false:true;
	}

	public void setOnlineCount() {
		if(onlineListener == null) return;
		int count = onlineListener.getOnLine();
		log.debug("设置在线数量" + count);
		if(count == 0 && status == 2){
			updateCmd("2",1);
			status = 3;
			log.debug("服务停止了");
		}
	}
	
	@Override
	public void setOnlineListener(OnLineListener onlineListener) {
		this.onlineListener = onlineListener;
	}

}
