package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;
import com.model.user.IsLogin;
import com.model.user.UserLogin;

import android.database.Cursor;

public class User_DAO {
	
	public static String getUserId(){
		String sql = SQLString.getUserId_Us();
		String userId = Index_Activity.basicDAO.selectString(sql);
		return userId;
	}
	
	public static String getUserName(){
		String sql = SQLString.getUserName_Us();
		String userId = Index_Activity.basicDAO.selectString(sql);
		return userId;
	}
	
	public static boolean isLogin(){
		return IsLogin.isLogin();
	}
	
	public static void changeUserName(String name){
		String sql = SQLString.getChangUseName(name);
		Index_Activity.basicDAO.update(sql);
	}
	
	public static Cursor getAllMessages(){
		String sql = SQLString.getALLMessages_Us();
		Cursor cursor = (Cursor)Index_Activity.basicDAO.selectCursor(sql);
		return cursor;
	}
	
	//以下使用UserLogin.basicDAO
	public static void init(){
		String sql = SQLString.getInit();
		UserLogin._basicDAO.update(sql);
	}
	
	//comsume&kind&id&date&inorout
	public static void initStream(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
//			String sql = SQLString.getInitStream(Float.parseFloat(map.get("consume")), map.get("kind"),Integer.parseInt(map.get("id")),map.get("date"),Integer.parseInt(map.get("inorout")));
//			String month = map.get("date").replace(".0", "");
			String sql = SQLString.getInitStream(Float.parseFloat(map.get("consume")), map.get("kind"),1,map.get("date"),Integer.parseInt(map.get("inorout")));
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//budget&kind&remain&month
	public static void initTablebudget(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			String month = map.get("month").replace("-01", "");
			String sql = SQLString.getInitTablebudget(Float.parseFloat(map.get("budget")), Integer.parseInt(map.get("kind")), Float.parseFloat(map.get("remain")), month);
			System.out.println("分类预算初始化：" + sql);
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//totalbudget&remain&month
	public static void initTabletotalbudget(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			String month = map.get("month").replace("-01", "");
			String sql = SQLString.getInitTabletotalbudget(Float.parseFloat(map.get("totalbudget")), Float.parseFloat(map.get("remain")), month);
			System.out.println("执行了总预算初始化：" + sql);
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//mony&month
	public static void initConsumein(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			String sql = SQLString.getInitConsumein(Float.parseFloat(map.get("mony")), map.get("month"));
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//lastdate&sytime
	public static void initTime(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			String sql = SQLString.getInitTime(map.get("lastdate"), map.get("sytime"));
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//firstid&secondid&kindname
	public static void initKind(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			String sql = SQLString.getInitKind(Integer.parseInt(map.get("id")),Integer.parseInt(map.get("firstid")), Integer.parseInt(map.get("secondid")), map.get("kindname"));
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	//name&time&lefttime&content&tips&advise
	public static void initTarget(ArrayList<HashMap<String, String>> list){
			Iterator<HashMap<String, String>> iterator = list.iterator();
			while(iterator.hasNext()){
				HashMap<String, String> map = iterator.next();
				String sql = SQLString.getInitTarget(map.get("name"), Integer.parseInt(map.get("time")), Integer.parseInt(map.get("lefttime")), map.get("content"), map.get("tips"), map.get("advise"));
				UserLogin._basicDAO.insert(sql);
			}
	}
	
	//id&name
	public static void initUser(ArrayList<HashMap<String, String>> list){
		Iterator<HashMap<String, String>> iterator = list.iterator();
		while(iterator.hasNext()){
			HashMap<String, String> map = iterator.next();
			System.out.println("执行了初始化用户" + map.get("id") + "   " + map.get("name"));
			String sql = SQLString.getInitUser(map.get("id"), map.get("name"));
			UserLogin._basicDAO.insert(sql);
		}
	}
	
	public static String getLastSyTime(){
		String sql = SQLString.getReadUpdateTime_Sy();
		return Index_Activity.basicDAO.selectString(sql);
	}
}
