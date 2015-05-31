package com.mnitools;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 获得格式化日期字符串
 * @author wcj
 *
 */
public class GetNowDate{

	@SuppressLint("SimpleDateFormat")

	public static String getNowDate(String formString) {
		// TODO Auto-generated method stub
		String currentTime = "";
		SimpleDateFormat format = new SimpleDateFormat(formString);
		currentTime = format.format(new Date());
		System.out.println("时间是:" + currentTime);
		return currentTime;
	}

}
