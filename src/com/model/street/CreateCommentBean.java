package com.model.street;

import com.bean.CommentBean;
import com.bean.StreetMessageBean;
import com.dao.User_DAO;
import com.mnitools.GetNowDate;

public class CreateCommentBean {
	private CommentBean commentBean = null;

	public CreateCommentBean() {
		// TODO Auto-generated constructor stub
		this.commentBean = new CommentBean();
	}

	public CommentBean createBean(String content, StreetMessageBean messageBean) {
		commentBean.setUserid(getUserId());
		commentBean.setUserName(getUserName());
		commentBean.setContent(content);
		commentBean.setTime(getTime());
		commentBean.setImessageid(messageBean.getMessageId());
		commentBean.setIuserid(messageBean.getUserID());
		commentBean.setIuserName(messageBean.getUserName());
		return commentBean;
	}

	public String getTime() {
		String time = GetNowDate.getNowDate("yyyy-MM-dd hh:mm:ss");
		return time;
	}

	public String getUserName() {
		return User_DAO.getUserName();
	}

	public String getUserId() {
		return User_DAO.getUserId();
	}
}
