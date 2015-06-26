package com.dao.basic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.activity.Index_Activity;
import com.dao.UserDataBase;

public class ForIndexDAO {

	public static void changeLoginUser(String id) {
		UserDataBase userDataBase = new UserDataBase(Index_Activity.indexActivity, "index.db");
		SQLiteDatabase db = userDataBase.getWritableDatabase();
		String sql = "replace into user_info select * from user_now";
		db.execSQL(sql);

		sql = "delete from user_now";
		db.execSQL(sql);

		sql = "select * from user_info where userid = '" + id + "'";// 在用户表中查询是否存在过用户
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.getCount() == 0) {// 未存在过此用户，新建数据库
			sql = "insert into user_now values('" + id + "','" + "user" + id + ".db" + "')";
			db.execSQL(sql);
			System.out.println("userNow 改为了" + "user" + id + ".db");
		} else {
			sql = "insert into user_now select * from user_info where userid = '" + id + "'";
			System.out.println("已有用户");
			db.execSQL(sql);
		}
		db.close();
	}

	public static void setId(String id) {
		UserDataBase userDataBase = new UserDataBase(Index_Activity.indexActivity, "index.db");
		SQLiteDatabase db = userDataBase.getWritableDatabase();
		String sql = "update user_now set userid = '" + id + "'";
		db.execSQL(sql);
		db.close();
	}
}
