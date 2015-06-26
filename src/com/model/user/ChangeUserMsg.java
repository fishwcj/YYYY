package com.model.user;

import com.dao.User_DAO;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

public class ChangeUserMsg {
	//���޸ķ����������޸ı���
	
	//		*������&�û�id
	public static boolean changePassWord(String newpassword){
		boolean tag = false;
		String url = "http://192.168.191.1:8080/Bill/servlet/ChangeUserMessage";
		INetWork communicater = new NetWorkCommunicate();
		if(communicater.connect(url)){
			StringBuilder builder = new StringBuilder();
			builder.append('*');
			builder.append(newpassword);
			builder.append('&');
			builder.append(User_DAO.getUserId());
			if(communicater.sendObject(builder.toString())){
				String ok = (String)communicater.receiveObject();
				if(ok.equals("1")){
					tag = true;
				}
			}
		}
		return tag;
	}
	
	//	&���ǳ�&�û�id
	public static boolean changeName(String name){
		boolean tag = false;
		INetWork communicater = new NetWorkCommunicate();
		String url = "http://192.168.191.1:8080/Bill/servlet/ChangeUserMessage";
		StringBuilder builder = new StringBuilder();
		builder.append('&');
		builder.append(name);
		builder.append('&');
		builder.append(User_DAO.getUserId());
		if(communicater.connect(url)){
			if(communicater.sendObject(builder.toString())){
				String ok = (String)communicater.receiveObject();
				if(ok.equals("1")){
					changeLocalName(name);
//					Toast.makeText(User_Activity.user_Activity, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
					tag = true;
				}
			}
		}
		return tag;
	}
	
	//	#����&�û�id
	public static boolean checkPassWord(String password){
		boolean tag = false;
		INetWork communicate = new NetWorkCommunicate();
		String url = "http://192.168.191.1:8080/Bill/servlet/ChangeUserMessage";
		StringBuilder builder = new StringBuilder();
		builder.append('#');
		builder.append(password);
		builder.append('&');
		builder.append(User_DAO.getUserId());
		if(communicate.connect(url))
			if(communicate.sendObject(builder.toString())){
				String ok = (String)communicate.receiveObject();
				if(ok.equals("1"))
					tag = true;
			}
		return tag;
	}
	
	private static boolean changeLocalName(String name){
		boolean tag = false;
		User_DAO.changeUserName(name);
		tag = true;
		return tag;
	}
}
