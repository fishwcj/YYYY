package com.model.user;

import com.dao.User_DAO;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

public class ChangeUserMsg {
	//先修改服务器，在修改本地
	
	//		*新密码&用户id
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
	
	//	&新昵称&用户id
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
//					Toast.makeText(User_Activity.user_Activity, "修改成功", Toast.LENGTH_SHORT).show();
					tag = true;
				}
			}
		}
		return tag;
	}
	
	//	#密码&用户id
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
