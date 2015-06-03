package com.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	/**
	 * 数据库版本
	 */
	private static final int VERSION = 1;

	public DataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// 该构造函数只有3个参数，在上面函数 的游标固定为null
	public DataBase(Context context, String name, int verson) {
		this(context, name, null, verson);
	}

	// 该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
	public DataBase(Context context, String name) {
		this(context, name, VERSION);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a sqlite database");

		// 获得当前日期 xxxx-xx
		String currentString;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		currentString = format.format(new Date());

		// 测试表2流水表：包括 消费、类型、时间
		db.execSQL("create table stream(consume float, kind varchar(20), id int, date datetime, inorout int)");
		// 测试表3预算表
		String sql = "create table tablebudget(id integer primary key autoincrement,budget float, kind int, remain float, month date)";
		db.execSQL(sql);
		/**
		 * 以下在预算表中定义默认的表项--衣食住行
		 * 
		 * 新增类别则新增的同时监听执行初始化sql语句
		 * 
		 * 以后每次消费既更新数据即可
		 */
		// 食品酒水
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 1, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		// 衣服饰品
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 2, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		// 出行交通
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 3, 0,'"
				+ currentString + "')"; 
		db.execSQL(sql);
		// 家居消费
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 4, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		//学习进修
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 5, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		//休闲娱乐
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 6, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		//人际交往
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 7, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		//医疗消费
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 8, 0,'"
				+ currentString + "')";
		db.execSQL(sql);
		//其它杂项
		sql = "insert into tablebudget(budget, kind, remain, month) values(0, 9, 0,'"
				+ currentString + "')";
		db.execSQL(sql);

		// 测试表4总预算表:应该包含时间（年、月），总预算金额。。测试就略去时间
		sql = "create table tabletotalbudget(totalbudget float,remain float, month date primary key)";
		db.execSQL(sql);
		// 初始化总预算为0
		sql = "insert into tabletotalbudget(totalbudget,remain, month) values (0,0,'"
				+ currentString + "')";
		db.execSQL(sql);
		// 创建表5收入表
		sql = "create table consumein(mony float, month date primary key)";
		db.execSQL(sql);
		sql = "insert into consumein(mony, month) values (0, '" + currentString
				+ "')";
		db.execSQL(sql);
		// 创建时间日期表
		sql = "create table time(lastdate date, sytime datetime)";
		db.execSQL(sql);
		sql = "insert into time(lastdate,sytime) values ('" + currentString
				+ "','2012-01-01 00:00:00')";
		db.execSQL(sql);

		//新增表一级消费
		sql = "create table firstkind(id int primary key, name varchar(20))";
		db.execSQL(sql);
		sql = "insert into firstkind values (1,'食品酒水')";
		db.execSQL(sql);
		sql = "insert into firstkind values (2,'衣服饰品')";
		db.execSQL(sql);
		sql = "insert into firstkind values (3,'出行交通')";
		db.execSQL(sql);
		sql = "insert into firstkind values (4,'家居消费')";
		db.execSQL(sql);
		sql = "insert into firstkind values (5,'学习进修')";
		db.execSQL(sql);
		sql = "insert into firstkind values (6,'休闲娱乐')";
		db.execSQL(sql);
		sql = "insert into firstkind values (7,'人际交往')";
		db.execSQL(sql);
		sql = "insert into firstkind values (8,'医疗保健')";
		db.execSQL(sql);
		sql = "insert into firstkind values (9,'其它杂项')";
		db.execSQL(sql);
		
		// 测试表5消费类型
		sql = "create table kind(id integer primary key autoincrement,firstid int, secondid int, kindname varchar(20))";
		db.execSQL(sql);
		//食品酒水
		sql = "insert into kind(firstid,secondid,kindname) values (1,1,'早午晚餐')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (1,2,'营养补品')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (1,3,'水果零食')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (1,4,'外出聚餐')";
		db.execSQL(sql);
		//衣服饰品
		sql = "insert into kind(firstid,secondid,kindname) values (2,1,'衣服裤子')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (2,2,'鞋子类')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (2,3,'帽子类')";
		db.execSQL(sql);
		
		//出行交通
		sql = "insert into kind(firstid,secondid,kindname) values (3,1,'灰机票')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (3,2,'火车票')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (3,3,'公交地铁')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (3,4,'长途大巴')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (3,5,'各种黑车')";
		db.execSQL(sql);
		
		//家居消费
		sql = "insert into kind(firstid,secondid,kindname) values (4,1,'日常用品')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (4,2,'电子产品')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (4,3,'电话费')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (4,4,'网费')";
		db.execSQL(sql);

		//学习进修
		sql = "insert into kind(firstid,secondid,kindname) values (5,1,'书本杂志')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (5,2,'学习培训')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (5,3,'文具购买')";
		db.execSQL(sql);
		//休闲娱乐
		sql = "insert into kind(firstid,secondid,kindname) values (6,1,'旅游度假')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (6,2,'唱歌电影')";
		db.execSQL(sql);
		//人际交往
		sql = "insert into kind(firstid,secondid,kindname) values (7,1,'送礼请客')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (7,2,'慈善捐助')";
		db.execSQL(sql);
		//医疗保健
		sql = "insert into kind(firstid,secondid,kindname) values (8,1,'医药费')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (8,2,'健身保健')";
		db.execSQL(sql);
		sql = "insert into kind(firstid,secondid,kindname) values (8,3,'美容费')";
		db.execSQL(sql);
		//其它杂项
		sql = "insert into kind(firstid,secondid,kindname) values (9,1,'杂项')";
		db.execSQL(sql);
		
		// 测试表6 用户表 tag为0代表未登录（未注册状态）
		sql = "create table user(id varchar(10), name varchar(20), tag int)";
		db.execSQL(sql);
		sql = "insert into user values ('null','某攒友',0)";
		db.execSQL(sql);
		//测试表7  攒钱表
		sql = "create table target(id integer primary key autoincrement,name varchar(30),time int,lefttime int,content varchar(40),tips varchar(30), advise varchar(30))";
		db.execSQL(sql);
		/*
		 * 借贷管理表
		 * @author LLL
		 * userId 借贷者name,kind 借出还是借入,money 借贷的钱数,return_time 归还日期,remark 备注,loaction 定位地点
		 */
		sql = "create table borrow_manager(id int,name varchar(10), kind int,money float,borrow_time datetime,return_time datetime,remark varchar(100),location varchar(100))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update a sqlite database");
	}

}
