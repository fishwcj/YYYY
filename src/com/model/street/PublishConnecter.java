package com.model.street;

import com.activity.Publish_Activity;
import com.activity.Street_Activity;
import com.bean.StreetMessageBean;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

import android.content.Intent;

/**
 * 发布状态
 * @author wcj
 *
 */
public class PublishConnecter {
	private INetWork communicater;

	public PublishConnecter() {
		this.communicater = new NetWorkCommunicate();
	}
	
	/**
	 * 采用其它网络链接方式
	 * @param netWork
	 */
	public <T extends INetWork> void setNetWork(T netWork){
		this.communicater = netWork;
	}
	
	/**
	 * 发送信息对象
	 * @param messageBean
	 */
	public void send(StreetMessageBean messageBean) {
		// TODO Auto-generated method stub
		String urlString = "http://192.168.191.1:8080/Bill/servlet/PublishServlet";
		String ok = "";
		if (communicater.connect(urlString))
			if (communicater.sendObject(messageBean)) {
				ok = (String) communicater.receiveObject();
			}
		if (ok.equals("1")) {
			// 跳转回去刷新
			Intent intent = new Intent(Publish_Activity.publish_Activity,
					Street_Activity.class);
			Publish_Activity.publish_Activity.startActivity(intent);
		}
	}
}
