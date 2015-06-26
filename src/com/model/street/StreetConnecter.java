package com.model.street;

import com.bean.SrStreetBeanList;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

//import android.os.StrictMode;

public class StreetConnecter{
	private String adrees = "中国石油大学";
	private INetWork communicater;
	public StreetConnecter() {
		// TODO Auto-generated constructor stub
		this.communicater = new NetWorkCommunicate();
	}
	
	/**
	 * 采用其它网络链接方式
	 * @param netWork
	 */
	public <T extends INetWork> void setNetWork(T netWork){
		this.communicater = netWork;
	}
	
	public SrStreetBeanList connect() {
		// TODO Auto-generated method stub
		SrStreetBeanList streetBean = null;
		String urlString = "http://192.168.191.1:8080/Bill/servlet/StreetServlet";
		if(communicater.connect(urlString)){
			if(communicater.sendObject(adrees)){
				Object obj = communicater.receiveObject();
				streetBean = (SrStreetBeanList)obj;//转化为序列化对象
			}
		}else{
//			StreetGUI streetGUI = new StreetGUI(Street_Activity.rootlinearLayout);
//			streetGUI.createErrorGUI();
		}
		return streetBean;
	}
}
