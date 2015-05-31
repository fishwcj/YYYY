package com.dao;

import android.database.Cursor;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;

public class Borrow_DAO {
	public Borrow_DAO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��������ȡ�ܺ�
	 * @return
	 */
	public Float getTotal(int kind){
		String sql = SQLString.getTotal(kind);
		Float total = Index_Activity.basicDAO.selectFloat(sql);
		return total;
	}
	public Cursor getList(int kind){
		String sql = SQLString.getList(kind);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	/**
	 * ����ӵĽ��itemд�뵽���ݿ�
	 */
//	public void insertBorrowItem(){
//		String sql = SQLString.InsertBorrowItem();
//		dao.insert(sql);
//	}
}
