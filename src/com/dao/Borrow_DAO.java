
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
	 * ����ӵĽ��itemд�뵽���ݿ�
	 */
	public static void insertBorrowItem( int id,String name,int kind,float money,String borrow_date,String return_date,String describe,String location){
		String sql = SQLString.InsertBorrowItem(id,name,kind,money,borrow_date,return_date,describe,location);
		Index_Activity.basicDAO.insert(sql);
	}/*
	
	*��ȡ������Ϣ
	*/
	public static Cursor getAllMsg(int id){
		Cursor cursor;
		String sql = SQLString.getAllMsgById(id);
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	/*
	 * 
	 * ��ȡ���ж���item
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



