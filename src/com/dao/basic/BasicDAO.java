package com.dao.basic;

import com.activity.Index_Activity;
import com.dao.DataBase;
import com.dao.UserDataBase;

import android.database.Cursor;
/**
 * ���ݿ������������̬
 */
import android.database.sqlite.SQLiteDatabase;

public class BasicDAO implements IBasicDAO {

	public SQLiteDatabase db = null;

	/**
	 * ����sqlite���ݿ�
	 * 
	 * @return
	 */
	public boolean connectDataBase(String dbname) {
		boolean tag = false;
		UserDataBase userDataBase = new UserDataBase(Index_Activity.indexActivity, "index.db");
		SQLiteDatabase index_db = userDataBase.getWritableDatabase();
		String sql = "select dbname from user_now";
		Cursor userdb = index_db.rawQuery(sql, null);
		if(userdb.moveToNext()){
			String dbnameString = userdb.getString(0);
			System.out.println("�򿪵����ݿ��� " + dbnameString);
			DataBase dataBase = new DataBase(Index_Activity.indexActivity, dbnameString);
			db = dataBase.getWritableDatabase();
			if (db != null)
				tag = true;
		}
		return tag;
	}

	public Cursor selectCursor(String sql) {
		Cursor cursor = null;
		if (isConnect())
			cursor = db.rawQuery(sql, null);
		else {
			System.out.println("δ�������ݿ�");
		}
		return cursor;
	}

	public float selectFloat(String sql) {
		float result = 0f;
		if (isConnect()) {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				result = cursor.getFloat(0);
			}
		} else {
			System.out.println("δ�������ݿ�");
		}
		return result;
	}

	public int selectInt(String sql) {
		int result = 0;
		if (isConnect()) {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToLast()) {
				result = cursor.getInt(0);
			}
		} else {
			System.out.println("δ�������ݿ�");
		}
		return result;
	}

	public String selectString(String sql) {
		String result = null;
		if (isConnect()) {
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				result = cursor.getString(0);
			}
		} else {
			System.out.println("δ�������ݿ�");
		}
		return result;
	}

	public boolean insert(String sql) {
		boolean tag = false;
		if (isConnect()) {
			db.execSQL(sql);
			tag = true;
		} else {
			System.out.println("δ�������ݿ�");
		}
		return tag;
	}

	public boolean update(String sql) {
		boolean tag = false;
		if (isConnect()) {
			db.execSQL(sql);
			tag = true;
		} else {
			System.out.println("δ�������ݿ�");
		}
		return tag;
	}

	public boolean drop(String sql) {
		boolean tag = false;
		if (isConnect()) {
			db.execSQL(sql);
			tag = true;
		} else {
			System.out.println("δ�������ݿ�");
		}
		return tag;
	}

	/**
	 * ������ݿ��Ƿ�����
	 * 
	 * @return
	 */
	public boolean isConnect() {
		boolean tag = (db != null) ? true : false;
		return tag;
	}

	@Override
	public void closeDB() {
		if (isConnect()){
			db.close();
		}
	}
}
