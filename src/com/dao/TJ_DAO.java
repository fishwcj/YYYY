/*
 * Author LLL
 * Time:4.14
 * 
 */
package com.dao;

import java.util.ArrayList;

import android.database.Cursor;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;


/**
 * @author LLL
 *
 */

public class TJ_DAO {
	public TJ_DAO() {

	}

	/**
	 * 
	 * 获取某一类的消费总额， 以float_list 的形式返回
	 */
	public ArrayList<Float> getTypeConsume(String date) {

		ArrayList<Float> perc_List;
		Cursor cursor;
		Cursor cursor1;
		float item;
		float total = 0;
		perc_List = new ArrayList<Float>();
		String sql;
		//直接访问数据库的类
		//BasicDAO dao = new BasicDAO();
		
		//String sql = "select totalbudget-remain as total_consume from tabletotalbudget";
		sql = SQLString.getTotalConsumeString(date);
		cursor1 = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		while (cursor1.moveToNext()) {
			total = cursor1.getFloat(cursor1.getColumnIndex("total_consume"));
		}
		cursor1.close();
		if (total == 0) {
			perc_List.add(total);
			perc_List.add(total);
			perc_List.add(total);
			perc_List.add(total);

		} else {
			sql = SQLString.getTypeConsumeString(date);
			//sql = "select budget-remain as sum_consume from tablebudget ";
			cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
			while (cursor.moveToNext()) {
				item = cursor.getFloat(cursor.getColumnIndex("sum_consume"));

				perc_List.add(item);
			}
			cursor.close();
		}
		return perc_List;
	}
	/**
	 * @param type
	 * @param date
	 * @return
	 */
	public ArrayList<String> getDay(int type,String date) {
		ArrayList<String> consumeList = new ArrayList<String>();
		Cursor cursor;
		String item;
		String sql;
		
//		String sql = "select distinct strftime('%d',date)  as day from stream where id = " + type
//				;
		sql = SQLString.getDayString(type,date);
		
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		//cursor = Index_Activity.db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			item = cursor.getString(cursor.getColumnIndex("day"));		
			consumeList.add(item);
			System.out.println(item+"kkkkkkkkkkkkkkk");
		}
		return consumeList;
	}
	/**
	 * @param type 按类型查询 
	 * @param date 按日期查询
	 * @return
	 */
	public ArrayList<Float> getConsume(int type,String date) {
		ArrayList<Float> consumeList = new ArrayList<Float>();
		Cursor cursor;
		float item;
		String sql;
		
		
//		String sql = "select sum(consume)  as totoalConsume from stream where id = " + type
//				+ " group by strftime('%d',date)";
		sql = SQLString.getDayTypeConsumeString(type,date);
		System.out.println(sql+"getConsume");
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		//cursor = Index_Activity.db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			item = cursor.getFloat(cursor.getColumnIndex("totoalConsume"));	
			System.out.println(item+"hhhhhhhhhhhhhh");
			consumeList.add(item);
			
		}
		
		return consumeList;
	}
}
