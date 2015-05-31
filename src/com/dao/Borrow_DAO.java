package com.dao;

import android.database.Cursor;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;

public class Borrow_DAO {
	public Borrow_DAO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 根据类别获取总和
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
	 * 将添加的借贷item写入到数据库
	 */
//	public void insertBorrowItem(){
//		String sql = SQLString.InsertBorrowItem();
//		dao.insert(sql);
//	}
}
