package com.hj.domain;

public class User {
	private String uid;
	private String username;
	private String student_ID;
	private String password;
	private String phonenumber;
	private String wechat;
	private String department;
	private String role;
	private int totaltimes;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStudent_ID() {
		return student_ID;
	}

	public void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTotaltimes() {
		return totaltimes;
	}

	public void setTotaltimes(int totaltimes) {
		this.totaltimes = totaltimes;
	}

}
