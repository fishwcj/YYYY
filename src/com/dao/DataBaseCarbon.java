package com.dao;

//import java.text.SimpleDateFormat;
//import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseCarbon extends SQLiteOpenHelper{

		/**
		 * ���ݿ�汾
		 */
		private static final int VERSION = 1;

		public DataBaseCarbon(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		// �ù��캯��ֻ��3�������������溯�� ���α�̶�Ϊnull
		public DataBaseCarbon(Context context, String name, int verson) {
			this(context, name, null, verson);
		}

		// �ù��캯��ֻ��2�������������溯�� �Ļ���ɽ���汾�Ź̶���
		public DataBaseCarbon(Context context, String name) {
			this(context, name, VERSION);
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("create a sqlite database");

			// ��õ�ǰ���� xxxx-xx
//			String currentString;
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
//			currentString = format.format(new Date());

			// ���Ա�2��ˮ������ ���ѡ����͡�ʱ��
			db.execSQL("create table stream(consume float, kind varchar(20), id int, date datetime, inorout int)");
			// ���Ա�3Ԥ���
			String sql = "create table tablebudget(budget float, kind int, remain float, month date)";
			db.execSQL(sql);

			// ���Ա�4��Ԥ���:Ӧ�ð���ʱ�䣨�ꡢ�£�����Ԥ��������Ծ���ȥʱ��
			sql = "create table tabletotalbudget(totalbudget float,remain float, month date)";
			db.execSQL(sql);
			
			// ������5�����
			sql = "create table consumein(mony float, month date)";
			db.execSQL(sql);
			// ����ʱ�����ڱ�
			sql = "create table time(lastdate date, sytime datetime)";
			db.execSQL(sql);

			// ���Ա�5��������
			sql = "create table kind(firstid int, secondid int, kindname varchar(20))";
			db.execSQL(sql);

			// ���Ա�6 �û��� tagΪ0����δ��¼��δע��״̬��
			sql = "create table user(id varchar(10), name varchar(20), tag int)";
			db.execSQL(sql);
			//���Ա�7  ��Ǯ��
			sql = "create table target(name varchar(30),time int,lefttime int,content varchar(40),tips varchar(30), advise varchar(30))";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			System.out.println("update a sqlite database");
		}
}
