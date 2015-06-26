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
	private HashMap<String, ArrayList<HashMap<String, String>>> map = null;//���ݱ�
	private String id = null;
	public static IBasicDAO _basicDAO = null;

	public UserLogin() {
		this.communicater = new NetWorkCommunicate();
	}

	public boolean passwordIsOK(String id, String password) {
		boolean tag = false;
		this.id = id;
		if (password.length() > 7)//��ֹsqlע��
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
			if (communicater.sendObject(this.id)) {//����1�����������ѯ�û�����
				this.map = (HashMap<String, ArrayList<HashMap<String, String>>>) communicater.receiveObject();
			}
		}
	}
	
	public boolean initData(){
		boolean tag = false;
		ForIndexDAO.changeLoginUser(id);//��indexDB�޸�Ĭ�ϵ�¼�û�
		Index_Activity.indexActivity.finish();
		JZ_Activity.jzActivity.finish();
		_basicDAO = new BasicDAO();
		_basicDAO.connectDataBase("");//��������
		//�򿪻򴴽������ݿ�
		User_DAO.init();//������ݿ�
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
