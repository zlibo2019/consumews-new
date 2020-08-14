package com.garen.sys.entity;

import javax.persistence.*;


@Table(name="fin_cmd")
public class FinCmd {
	/**
	*null
	*/
	@Column(name = "service_id")
	private Integer serviceId;

	/**
	*null
	*/
	@Column(name = "cmd_content")
	private String cmdContent;

	/**
	*null
	*/
	@Column(name = "service_name")
	private String serviceName;

	/**
	*null
	*/
	@Column(name = "client")
	private String client;

	/**
	*null
	*/
	@Column(name = "bz")
	private String bz;

	/**
	*null
	*/
	@Column(name = "exec_state")
	private Integer execState;

	/**
	*null
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable=false)
	private Integer id;

	/**
	*null
	*/
	@Column(name = "sj")
	@com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd")
	private java.util.Date sj;



	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId=serviceId;
	}

	public String getCmdContent() {
		return cmdContent;
	}

	public void setCmdContent(String cmdContent) {
		this.cmdContent=cmdContent;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName=serviceName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client=client;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz=bz;
	}

	public Integer getExecState() {
		return execState;
	}

	public void setExecState(Integer execState) {
		this.execState=execState;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id=id;
	}

	public java.util.Date getSj() {
		return sj;
	}

	public void setSj(java.util.Date sj) {
		this.sj=sj;
	}

}
