package com.garen.sys.entity;

import javax.persistence.*;



@Table(name="dt_user")
public class DtUser{
	
	/**
	*null
	*/
	@Column(name = "user_lname")
	private String userLname;

	/**
	*null
	*/
	@Id
	@Column(name = "user_no",nullable=false)
	private String userNo;

	/**
	*null
	*/
	@Column(name = "user_id")
	private String userId;

	
	/**
	*null
	*/
	@Column(name = "user_bz")
	private String userBz;

	/**
	*null
	*/
	@Id
	@Column(name = "user_serial",nullable=false)
	private Long userSerial;

	/**
	*null
	*/
	@Column(name = "user_password")
	private String userPassword;

	public String getUserLname() {
		return userLname;
	}

	public void setUserLname(String userLname) {
		this.userLname = userLname;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserBz() {
		return userBz;
	}

	public void setUserBz(String userBz) {
		this.userBz = userBz;
	}

	public Long getUserSerial() {
		return userSerial;
	}

	public void setUserSerial(Long userSerial) {
		this.userSerial = userSerial;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


}
