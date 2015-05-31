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
		currentString = GetNowDate.getNowDate("yyyy-MM");		// ��õ�ǰ����
		System.out.println("YS�����õ�������:" + currentString);
	}

	/**
	 * ��ѯ���ݿ�õ���������Ԥ����Ϣ
	 * 
	 * @return
	 */
	public Cursor read_budget() {
		String sql = SQLString.getBudget_Ys(currentString);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		System.out.println("��ѯʱ�䣺" + currentString);
		return cursor;
	}

	/**
	 * ��ȡ��Ԥ��
	 * 
	 * @return
	 */
	public String read_totalbudget() {
		String totalString = "Ŷ�����ݿ�崻���...";
		String sql = SQLString.getTotalbudget_Ys(currentString);
		totalString = Index_Activity.basicDAO.selectString(sql);
		return totalString;
	}

	/**
	 * ���û�и���Ԥ������룬����������
	 * 
	 * @param consum_mony
	 *            Ԥ����
	 * @param kind
	 *            Ԥ�����
	 * @return
	 */
	public boolean add(float[] budget, int[] kind) {
		String sql;
		for (int i = 0; i < budget.length; i++) {
			// ���Ԥ�㣬�Դ�ǰ�����ݸ���
			sql = SQLString.getAddBudget_Ys(budget[i], kind[i], currentString);
			Index_Activity.basicDAO.update(sql);
		}
		return true;
	}

	/**
	 * ������Ԥ���
	 * 
	 * @param totalbudget
	 *            ��Ԥ��
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
	 * ������Ԥ���,��ÿ�μ�һ��ʱ����
	 */
	private boolean deltotal(float consume) {
		String sql = SQLString.getDeltotal_Ys(consume, currentString);
		Index_Activity.basicDAO.update(sql);
		return true;
	}

	/**
	 * ÿ��һ���˸��·���Ԥ��ʣ��
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
	 * ���������
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
