package com.dao;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;
import com.mnitools.GetNowDate;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class YS_DAO {

	private String currentString;
	@SuppressLint("SimpleDateFormat")
	public YS_DAO() {
		currentString = GetNowDate.getNowDate("yyyy-MM");		// 获得当前日期
		System.out.println("YS里面获得的日期是:" + currentString);
	}

	/**
	 * 查询数据库得到所有类别的预算信息
	 * 
	 * @return
	 */
	public Cursor read_budget() {
		String sql = SQLString.getBudget_Ys(currentString);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		System.out.println("查询时间：" + currentString);
		return cursor;
	}

	/**
	 * 读取总预算
	 * 
	 * @return
	 */
	public String read_totalbudget() {
		String totalString = "哦！数据库宕机了...";
		String sql = SQLString.getTotalbudget_Ys(currentString);
		totalString = Index_Activity.basicDAO.selectString(sql);
		return totalString;
	}

	/**
	 * 如果没有该类预算则插入，如果有则更新
	 * 
	 * @param consum_mony
	 *            预算金额
	 * @param kind
	 *            预算类别
	 * @return
	 */
	public boolean add(float[] budget, int[] kind) {
		String sql;
		for (int i = 0; i < budget.length; i++) {
			// 添加预算，对此前的数据更新
			sql = SQLString.getAddBudget_Ys(budget[i], kind[i], currentString);
			Index_Activity.basicDAO.update(sql);
		}
		return true;
	}

	/**
	 * 更新总预算表
	 * 
	 * @param totalbudget
	 *            总预算
	 * @return
	 */
	public boolean addtotal(float totalbudget) {
		String sql = SQLString.getAddtotal_Ys(totalbudget, currentString);
		Index_Activity.basicDAO.update(sql);
		
		sql = SQLString.getAddtotal1_Ys(totalbudget, currentString);
		Index_Activity.basicDAO.update(sql);
		return true;
	}

	/**
	 * 更新总预算表,被每次记一笔时调用
	 */
	private boolean deltotal(float consume) {
		String sql = SQLString.getDeltotal_Ys(consume, currentString);
		Index_Activity.basicDAO.update(sql);
		return true;
	}

	/**
	 * 每记一次账更新分类预算剩余
	 * 
	 * @return
	 */
	public boolean update(float consume, int kind) {
		String sql = SQLString.getUpdate_Ys(consume, kind, currentString);
		Index_Activity.basicDAO.update(sql);
		deltotal(consume);
		return true;
	}

	/**
	 * 更新收入表
	 * 
	 * @param in
	 * @param db
	 * @return
	 */
	public boolean updatein(float in) {
		String sql = SQLString.getUpdatein_Ys(in, currentString);
		Index_Activity.basicDAO.update(sql);
		return true;
	}
}
