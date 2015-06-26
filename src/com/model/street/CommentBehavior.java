package com.model.street;

import com.bean.CommentBean;
import com.bean.CommentBeanList;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;

public class CommentBehavior {
	private INetWork communicater;
	
	public CommentBehavior(){
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
	 * 加载评论信息
	 * @param imessageid 原始的状态id
	 * @return
	 */
	public CommentBeanList getComments(int imessageid){
		String urlString = "http://192.168.191.1:8080/Bill/servlet/SearchCommentServlet";
		CommentBeanList commentBeanList= null;
		if(communicater.connect(urlString))
			if(communicater.sendObject(imessageid)){
				commentBeanList = (CommentBeanList)communicater.receiveObject();
			}
		return commentBeanList;
	}
	
	/**
	 * 发送评论
	 * @param commentBean 评论信息
	 * @return
	 */
	public boolean sendComment(CommentBean commentBean){
		boolean tag = false;
		String urlString = "http://192.168.191.1:8080/Bill/servlet/CommentServlet";
		String ok = null;
		if(communicater.connect(urlString))
			if(communicater.sendObject(commentBean)){
				ok = (String)communicater.receiveObject();
			}
		if(ok != null && ok.equals("1"))
			tag = true;
		return tag;
	}
}
