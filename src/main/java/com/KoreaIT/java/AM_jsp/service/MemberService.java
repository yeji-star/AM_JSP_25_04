package com.KoreaIT.java.AM_jsp.service;

import java.sql.Connection;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dto.Member;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class MemberService {
private Connection conn;

public boolean isLoginIdDup(String loginId) {
	SecSql sql = SecSql.from("SELECT COUNT(*) > 0 AS cnt FROM `member`");			
	sql.append("WHERE loginId = ?;", loginId);
	
	return DBUtil.selectRowBooleanValue(conn, sql);
}

public int doJoin(String loginId, String loginPw, String name) {
	SecSql sql = SecSql.from("INSERT INTO `member`");
	sql.append("SET regDate = NOW(),");
	sql.append("loginId = ?,", loginId);
	sql.append("loginPw = ?,", loginPw);
	sql.append("`name` = ?;", name);
	
	return DBUtil.insert(conn, sql);
}

public Member getMemberByLoginId(String loginId) {
	
	SecSql sql = SecSql.from("SELECT *");
	sql.append("FROM `member`");
	sql.append("WHERE loginId = ?;", loginId);
	
	Map<String, Object> memberMap = DBUtil.selectRow(conn, sql); // pw를 꺼내와야해서 row로 씀
	
	return new Member(memberMap);
}

}
