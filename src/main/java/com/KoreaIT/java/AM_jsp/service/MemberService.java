package com.KoreaIT.java.AM_jsp.service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dao.MemberDao;
import com.KoreaIT.java.AM_jsp.dto.Member;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class MemberService {
	private Connection conn;
	private MemberDao memberDao;

public MemberService(Connection conn) {
		this.conn = conn;
		
		this.memberDao = new MemberDao(conn);
	}

public boolean isLoginIdDup(String loginId) {

	return memberDao.isLoginIdDup(loginId);
}

public int doJoin(String loginId, String loginPw, String name) {

	
	return memberDao.doJoin(loginId, loginPw, name);
}

public Member getMemberByLoginId(String loginId) {
	

	return memberDao.getMemberByLoginId(loginId);
}

}
