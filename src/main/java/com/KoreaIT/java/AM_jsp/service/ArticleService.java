package com.KoreaIT.java.AM_jsp.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.AM_jsp.dao.ArticleDao;
import com.KoreaIT.java.AM_jsp.dto.Article;
import com.KoreaIT.java.AM_jsp.util.DBUtil;
import com.KoreaIT.java.AM_jsp.util.SecSql;

public class ArticleService {

	private Connection conn;
	private ArticleDao articleDao;

	public ArticleService(Connection conn) {
		this.conn = conn;

		this.articleDao = new ArticleDao(conn);
	}

	public int getTotalCnt() {

		return articleDao.getTotalCnt();
	}

	public List<Article> getForPrintArticles(int limitFrom, int itemsInAPage) {

		return articleDao.getForPrintArticles(limitFrom, itemsInAPage);
	}

	public Article getArticleById(int id) {

		return articleDao.getArticleById(id);
	}

	public void doUpdate(String title, String body, int id) {

		articleDao.doUpdate(title, body, id);

	}

	public void doDelete(int id) {

		articleDao.doDelete(id);

	}

	public int doWrite(int MemberId, String title, String body) {

		
		return articleDao.doWrite(MemberId, title, body);
		
	}

}
