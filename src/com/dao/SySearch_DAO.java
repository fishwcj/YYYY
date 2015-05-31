package com.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;
import com.mnitools.GetNowDate;

import android.annotation.SuppressLint;
import android.database.Cursor;

public class SySearch_DAO {
	private Cursor cursor;
	private String sql;

	/**
	 * 得到id
	 * @return
	 */
	public String searchId(){
		sql = SQLString.getSearchId_Sy();
		String id = Index_Activity.basicDAO.selectString(sql);
		return id;
	}
	/**
	 * 查询得到新增流水
	 * @param time	上次同步的时间
	 * @return
	 */
	public Cursor searchStreamCount(){
		String time = getLastsytime();
		sql = SQLString.getSearchStreamCount_Sy(time);
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		int i = cursor.getCount();
		System.out.println("新增流水:" + i );
		return cursor;
	}
	
	
	/**
	 * 总预算表
	 * @return
	 */
	public Cursor searchBudget(){
		sql = SQLString.getSearchBudget_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	
	/**
	 * 收入表
	 * @return
	 */
	public Cursor searchincome(){
		sql = SQLString.getSearchincome_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	
	/**
	 * 分类预算表
	 * @return
	 */
	public Cursor searchBudgetByKind(){
		sql = SQLString.getSearchBudgetByKind_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	/**
	 * 时间表
	 * @return
	 */
	public Cursor serchTime(){
		sql = SQLString.getSearchTime_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	/**
	 * 消费类别表
	 * @return
	 */
	public Cursor searchKind(){
		sql = SQLString.getSearchKind_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	public Cursor searchTarget(){
		sql = SQLString.getTarget_Is();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	@SuppressLint("SimpleDateFormat")
	public boolean isNextMonth(String date) throws ParseException{
		String lastDate = getLastsytime();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = format.parse(lastDate);
		format = new SimpleDateFormat("yyyy-MM");
		lastDate = format.format(date1);
		return !(lastDate.equals(date));
	}
	
	public String getLastsytime(){
		sql = SQLString.getSytime_Sy();
		String lastDate = Index_Activity.basicDAO.selectString(sql);
		System.out.println("上一次同步时间" + lastDate);
		return lastDate;
	}
	
	
	/**
	 * 每次同步之后更新时间表
	 */
	@SuppressLint("SimpleDateFormat")
	public void updateTime(){
		String currenString = GetNowDate.getNowDate("yyyy-MM-dd hh:mm:ss");
		System.out.println("现在更新同步时间" + currenString);
		
		sql = SQLString.getUpdateTime_Sy(currenString);
		Index_Activity.basicDAO.update(sql);
	}
}
