package com.model.user;

import java.util.ArrayList;
import java.util.HashMap;

import com.activity.Index_Activity;
import com.activity.JZ_Activity;
import com.dao.User_DAO;
import com.dao.basic.BasicDAO;
import com.dao.basic.ForIndexDAO;
import com.dao.basic.IBasicDAO;
import com.mnitools.NetWorkCommunicate;

public class UserLogin {
	private NetWorkCommunicate communicater;
	private HashMap<String, ArrayList<HashMap<String, String>>> map = null;//数据表
	private String id = null;
	public static IBasicDAO _basicDAO = null;

	public UserLogin() {
		this.communicater = new NetWorkCommunicate();
	}

	public boolean passwordIsOK(String id, String password) {
		boolean tag = false;
		this.id = id;
		if (password.length() > 7)//防止sql注入
			return tag;
		String key = id + "&" + password;
		String urlString = "http://192.168.191.1:8080/Bill/servlet/UserCheckServlet";
		if (communicater.connect(urlString)) {
			if (communicater.sendObject(key)) {
				String okString = (String) communicater.receiveObject();
				if (okString.equals("1"))
					tag = true;
			}
		}
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public void getData(){
		String urlString = "http://192.168.191.1:8080/Bill/servlet/UserCheckServlet";
		if (communicater.connect(urlString)) {
			if (communicater.sendObject(this.id)) {//发送1，代表请求查询用户数据
				this.map = (HashMap<String, ArrayList<HashMap<String, String>>>) communicater.receiveObject();
			}
		}
	}
	
	public boolean initData(){
		boolean tag = false;
		ForIndexDAO.changeLoginUser(id);//打开indexDB修改默认登录用户
		Index_Activity.indexActivity.finish();
		JZ_Activity.jzActivity.finish();
		_basicDAO = new BasicDAO();
		_basicDAO.connectDataBase("");//参数无用
		//打开或创建新数据库
		User_DAO.init();//清空数据库
		User_DAO.initStream(map.get("stream"));
		User_DAO.initKind(map.get("kind"));
		User_DAO.initConsumein(map.get("consumein"));
		User_DAO.initTablebudget(map.get("tablebudget"));
		User_DAO.initTabletotalbudget(map.get("tabletotalbudget"));
		User_DAO.initTarget(map.get("target"));
		User_DAO.initTime(map.get("time"));
		User_DAO.initUser(map.get("user"));
		_basicDAO.closeDB();
		tag = true;
		return tag;
	}
}
