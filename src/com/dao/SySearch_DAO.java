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
	 * �õ�id
	 * @return
	 */
	public String searchId(){
		sql = SQLString.getSearchId_Sy();
		String id = Index_Activity.basicDAO.selectString(sql);
		return id;
	}
	/**
	 * ��ѯ�õ�������ˮ
	 * @param time	�ϴ�ͬ����ʱ��
	 * @return
	 */
	public Cursor searchStreamCount(){
		String time = getLastsytime();
		sql = SQLString.getSearchStreamCount_Sy(time);
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		int i = cursor.getCount();
		System.out.println("������ˮ:" + i );
		return cursor;
	}
	
	
	/**
	 * ��Ԥ���
	 * @return
	 */
	public Cursor searchBudget(){
		sql = SQLString.getSearchBudget_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	
	/**
	 * �����
	 * @return
	 */
	public Cursor searchincome(){
		sql = SQLString.getSearchincome_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	
	/**
	 * ����Ԥ���
	 * @return
	 */
	public Cursor searchBudgetByKind(){
		sql = SQLString.getSearchBudgetByKind_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	/**
	 * ʱ���
	 * @return
	 */
	public Cursor serchTime(){
		sql = SQLString.getSearchTime_Sy();
		cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	/**
	 * ��������
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
		System.out.println("��һ��ͬ��ʱ��" + lastDate);
		return lastDate;
	}
	
	
	/**
	 * ÿ��ͬ��֮�����ʱ���
	 */
	@SuppressLint("SimpleDateFormat")
	public void updateTime(){
		String currenString = (new GetNowDate()).getNowDate("yyyy-MM-dd hh:mm:ss");
		System.out.println("���ڸ���ͬ��ʱ��" + currenString);
		
		sql = SQLString.getUpdateTime_Sy(currenString);
		Index_Activity.basicDAO.update(sql);
	}
}
