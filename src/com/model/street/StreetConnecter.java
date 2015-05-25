package com.model.street;

import com.bean.SrStreetBeanList;
import com.inteface.INetWork;
import com.mnitools.NetWorkCommunicate;

//import android.os.StrictMode;

public class StreetConnecter{
	private String adrees = "�й�ʯ�ʹ�ѧ";
	private INetWork communicater;
	public StreetConnecter() {
		// TODO Auto-generated constructor stub
		this.communicater = new NetWorkCommunicate();
	}
	
	/**
	 * ���������������ӷ�ʽ
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
				streetBean = (SrStreetBeanList)obj;//ת��Ϊ���л�����
			}
		}else{
//			StreetGUI streetGUI = new StreetGUI(Street_Activity.rootlinearLayout);
//			streetGUI.createErrorGUI();
		}
		return streetBean;
	}
}
