
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
	public static Float getTotal(int kind){
		String sql = SQLString.getTotal(kind);
		Float total = Index_Activity.basicDAO.selectFloat(sql);
		return total;
	}
	public static Cursor getList(int kind){
		String sql = SQLString.getList(kind);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	/**
	 * 将添加的借贷item写入到数据库
	 */
	public static void insertBorrowItem( int id,String name,int kind,float money,String borrow_date,String return_date,String describe,String location){
		String sql = SQLString.InsertBorrowItem(id,name,kind,money,borrow_date,return_date,describe,location);
		Index_Activity.basicDAO.insert(sql);
	}/*
	
	*获取所有信息
	*/
	public static Cursor getAllMsg(int id){
		Cursor cursor;
		String sql = SQLString.getAllMsgById(id);
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	/*
	 * 
	 * 获取共有多少item
	 */
	public static int getItemCount(){
		int count = 0;
		Cursor cursor;
		String sql = SQLString.getItemCount();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		while (cursor.moveToNext()){
			count  = cursor.getInt(cursor.getColumnIndex("count"));
		}
		return count;
	}
}



