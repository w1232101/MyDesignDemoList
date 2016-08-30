/*
 * Copyright (c) 互讯科技 版权所有
 */

package com.example.administrator.designdemo.bean;

public class User {
	String number;//":"13586195699","
	String account;//":"001","
	String schoolId;//":"zsxx","
	String schoolname;//":"哲商实验小学","
	String deptId;//":"230","
	String personId;//":"611","
	String sex;//":"女","
	String name;//":"李冬春","
	String nickname;//":"春春","
	String smallavatar;//":"http://121.41.50.121/PrimaryAndMiddleSchool_1_1/EnfoPic//HeadPic//1a9c7e74-f2e5-44e7-8b40-47a9ae252563.jpg","
	String token;//":"123456","
	String login_result;//":"1"}int0
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSmallavatar() {
		return smallavatar;
	}
	public void setSmallavatar(String smallavatar) {
		this.smallavatar = smallavatar;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLogin_result() {
		return login_result;
	}
	public void setLogin_result(String login_result) {
		this.login_result = login_result;
	}
  
	public static String parameter(String PlatformType ,String userName ,String password ,String role ,String customerid ,String datetime, String string ){
		String paremse="http://121.41.50.121/PrimaryAndMiddleSchool_1_1/EnfoService/Login_Service_V1_1.ashx?PlatformType=%s&userName=%s&password=%s&role=%s&customerid=%s&datetime=%s";
		return String.format(paremse, PlatformType,userName,password,role,customerid,datetime);}

}
