package com.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class UserDataBase extends SQLiteOpenHelper {

	/**
	 * ���ݿ�汾
	 */
	private static final int VERSION = 1;

	public UserDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// �ù��캯��ֻ��3�������������溯�� ���α�̶�Ϊnull
	public UserDataBase(Context context, String name, int verson) {
		this(context, name, null, verson);
	}

	// �ù��캯��ֻ��2�������������溯�� �Ļ���ɽ���汾�Ź̶���
	public UserDataBase(Context context, String name) {
		this(context, name, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table user_now(userid varchar(10) primary key,dbname varchar(20))";
		db.execSQL(sql);
		sql = "insert into user_now values('000', 'firstuser.db')";
		db.execSQL(sql);
		sql = "create table user_info(userid varchar(10) primary key,dbname varchar(20))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
