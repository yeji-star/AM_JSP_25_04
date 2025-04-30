package com.KoreaIT.java.AM_jsp.dto;

import java.util.Map;

public class Member {

	private int id;
	private String regDate;

	private String loginId;
	private String loginPw;
	private String name;

	@Override
	public String toString() {
		return "Member [id=" + id + ", regDate=" + regDate + ", loginId=" + loginId + ", loginPw=" + loginPw + ", name="
				+ name + "]";
	}

	public Member(Map<String, Object> memberMap) {
		this.id = (int) memberMap.get("id");
		this.regDate = (String) memberMap.get("regDate");

		this.loginId = (String) memberMap.get("loginId");
		this.loginPw = (String) memberMap.get("loginPw");
		this.name = (String) memberMap.get("name");
	}

	public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;

		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
