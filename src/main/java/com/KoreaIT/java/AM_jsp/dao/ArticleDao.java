package com.KoreaIT.java.AM_jsp.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dto.Article;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class ArticleDao {
	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public List<Article> getForPrintArticles(int limitFrom, int itemsInAPage) {
		SecSql sql = SecSql.from("SELECT article.*, `member`.name");
		sql.append("FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.memberId = `member`.id");
		sql.append("ORDER BY article.id DESC");
		sql.append("LIMIT ?, ?;", limitFrom, itemsInAPage);

		List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> articleMap : articleRows) {
			articles.add(new Article(articleMap));
		}

		return articles;

	}

	public int getTotalCnt() {
		SecSql sql = SecSql.from("SELECT COUNT(*)");
		sql.append("FROM article;");

		return DBUtil.selectRowIntValue(conn, sql);
	}

	public Article getArticleById(int id) {
		SecSql sql = SecSql.from("SELECT article.*, `member`.name");
		sql.append("FROM article");
		sql.append("INNER JOIN `member`");
		sql.append("ON article.memberId = `member`.id");
		sql.append("WHERE article.id = ?;", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

//		List<Article> articles = new ArrayList<>();

		return new Article(articleMap);
	}

	public void doUpdate(String title, String body, int id) {
		
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET title = ?,", title);
		sql.append("`body` = ?", body);
		sql.append("WHERE id = ?;", id);
		
		DBUtil.update(conn, sql);
	}

	public void doDelete(int id) {
		SecSql sql = SecSql.from("DELETE FROM article");
		sql.append("WHERE id = ?;", id);

		DBUtil.delete(conn, sql); // DB에서 가져온 걸 압축풀기한 느낌
		
	}

	public int doWrite(int MemberId, String title, String body) {
		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("memberId = ?,", MemberId);
		sql.append("title = ?,", title);
		sql.append("`body` = ?;", body);
		
		return DBUtil.insert(conn, sql);
		
	}

}
