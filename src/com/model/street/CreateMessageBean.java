package com.model.street;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.bean.StreetMessageBean;
import com.dao.User_DAO;

public class CreateMessageBean {
	
	public CreateMessageBean() {
	}
	
	public StreetMessageBean create(String content,String tag, float price,String adrs,ArrayList<byte[]> list) {
		StreetMessageBean messageBean = new StreetMessageBean();
		messageBean.setMessage(content);
		messageBean.setUserName(getUserName());
		messageBean.setDatetime(getDate());
		messageBean.setUserID(getUserId());
		messageBean.setTag(tag);
		messageBean.setPrice(price);
		messageBean.setAddress(adrs);
		messageBean.setImglist(list);
		return messageBean;
	}

	private String getUserId() {
		String userid = User_DAO.getUserId();
		return userid;
	}

	private String getUserName() {
		String name = User_DAO.getUserName();
		return name;
	}

	@SuppressLint("SimpleDateFormat")
	private String getDate() {
		String currentString = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		currentString = format.format(new Date());
		return currentString;
	}
}
