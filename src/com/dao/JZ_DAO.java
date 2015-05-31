package com.dao;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.widget.TextView;

import com.activity.Index_Activity;
import com.activity.JZ_Activity;
import com.dao.basic.SQLString;
import com.mnitools.GetNowDate;
import com.yyyy.yyyy.R;

public class JZ_DAO {
	private String currentString;

	@SuppressLint("SimpleDateFormat")
	public JZ_DAO() {
		// 获得当前日期，用于查询数据库限制条件
		this.currentString = GetNowDate.getNowDate("yyyy-MM");
	}

	/**
	 * 更新记账页面的预算余额显示
	 */
	public void updateBudgetRemain() {
		Activity jz_Activity = JZ_Activity.jzActivity;// 获得JZ_Activity引用
		TextView remainTextView = (TextView) jz_Activity.findViewById(R.id.budgetRemain);

		String sql = SQLString.getUpdateBudgetRemain_JZ(currentString);
		Cursor cursor = (Cursor) Index_Activity.basicDAO.selectCursor(sql);

		if (cursor.moveToNext()) {
			String remainString = cursor.getString(cursor.getColumnIndex("remain"));
			Index_Activity.budget = Float.parseFloat(cursor.getString(cursor.getColumnIndex("totalbudget")));
			remainString = new DecimalFormat("0.0").format(Float.parseFloat(remainString));// 格式化浮点数
			remainTextView.setText(remainString);
		}
	}

	public static void insertStream(float consum1, String kind, String date, int inOrOut, int consumekind) {
		String sql = SQLString.getInsertStream(consum1, kind, date, inOrOut, consumekind);
		Index_Activity.basicDAO.insert(sql);
	}
}
