package com.dao;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;

import android.database.Cursor;

public class Kind_Dao {
	public static Cursor getchildKinds(int firstid){
		String sql = SQLString.getChilds(firstid);
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	public static String getfirstKindName(int firstid){
		String sql = SQLString.getFirstKindName_Ki(firstid);
		String name = Index_Activity.basicDAO.selectString(sql);
		return name;
	}
	
	public static boolean insertNewKind(int firstid, String name){
		boolean tag = false;
		String sql = SQLString.getMaxId(firstid);
		int secondid = Index_Activity.basicDAO.selectInt(sql) + 1;
		sql = SQLString.getInsertNewKind(firstid, secondid, name);
		Index_Activity.basicDAO.insert(sql);
		tag = true;
		return tag;
	}
	
	public static boolean deleteItem(int firstid, int secondid){
		boolean tag = false;
		String sql = SQLString.getDelete(firstid, secondid);
		Index_Activity.basicDAO.drop(sql);
		tag = true;
		return tag;
	}
}
