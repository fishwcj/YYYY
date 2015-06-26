package com.model.user;

import com.activity.Index_Activity;
import com.activity.User_Activity;
import com.dao.basic.ForIndexDAO;
import com.dao.basic.SQLString;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

import android.content.Intent;
import android.widget.Toast;

public class Regist {
	private String idAndPassword;
	private INetWork communicater;
	public Regist() {
		this.communicater = new NetWorkCommunicate();
	}

	/**
	 * 使用其它链接方式
	 * @param netWork
	 */
	public <T extends INetWork> void setNetWork(T netWork){
		this.communicater = netWork;
	}
	
	public void getNewID() {
		if (!IsLogin.isLogin()) {
			connect();
			updateState(idAndPassword);
			Intent intent = new Intent(User_Activity.user_Activity, Index_Activity.class);
			User_Activity.user_Activity.startActivity(intent);
		} else {
			Toast.makeText(User_Activity.user_Activity, "已经获取!不要贪得无厌-_-||",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 连接服务器获得新id
	 */
	public void connect() {
		String urlString = "http://192.168.191.1:8080/Bill/servlet/RegistServlet";
		if(communicater.connect(urlString))
			if(communicater.sendObject("")){
				idAndPassword = (String)communicater.receiveObject();
			}
	}

	/**
	 * 更改数据库表
	 * 
	 * @param idAndPassword
	 */
	public void updateState(String idAndPassword) {
		String sql = SQLString.getUpdateStateId_Re(idAndPassword);
		Index_Activity.basicDAO.update(sql);
		sql = SQLString.getUpdateStateTag_Re();
		Index_Activity.basicDAO.update(sql);
		ForIndexDAO.setId(idAndPassword);//更新登录数据库
		System.out.println("id号为"+ idAndPassword);
	}
}
