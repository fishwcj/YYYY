package com.dao;

//import android.app.Activity;
import com.activity.Index_Activity;
import com.dao.basic.SQLString;
import android.database.Cursor;

public class LS_DAO {
	/**
	 * 查询所有流水账
	 * 
	 * @param manager
	 * @param dataBase
	 * @return
	 */
	public Cursor selectAllAccount(String dateString) {
		
		String sql = SQLString.getSelectAllAccount_LS(dateString);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
}
