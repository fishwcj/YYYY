package com.logic;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;

import com.activity.Stream_Activity;
import com.dao.DataBase;
import com.dao.JZ_DAO;
import com.dao.LS_DAO;
import com.mnitools.GetNowDate;
import com.model.count.Draw_chart;
import com.model.count.LineChart;
import com.model.count.PieChart;
import com.model.stream.LSManager;
import com.yyyy.yyyy.R;

@SuppressWarnings({ "unused" })
public class Index_ContorlHelper {
	private String now_date;// 当前日期
	LinearLayout[][] linearLayoutChild;
	int i = 0;

	/**
	 * 构造函数，得到LocalActivityManager和DataBase
	 * 
	 * @param manager
	 * @param dataBase
	 */
	@SuppressLint("SimpleDateFormat")
	public Index_ContorlHelper(Context context) {
		this.now_date = GetNowDate.getNowDate("yyyy-MM");
	}

	/**
	 * 此函数做决定，根据当前和上一次界面判断接下来的行为
	 * 
	 * @param current
	 * @param passed
	 */
	
	public void getDecide(int current, int passed) {
		if (current == 1 || passed == 1) { // 流水
//			getFrameOfThisYear();
			 updateStreamUI(current);
		}
		if (current == 0) { // 记账,不能用else if
			updatejzUI();
		}
		/**
		 * author LLL 
		 * content:统计 
		 * time:4.18
		 * 将pieView、TJ_DrawHelper、ViewBase放在了model包中
		 */
		if (current == 2) {
			/**
			 * 画消费分析图
			 */
			PieChart pie = new PieChart(now_date);
			Draw_chart.draw_Chart(pie);
			LineChart line= new LineChart(now_date);
			Draw_chart.draw_Chart(line);
			
		}
	}

	/**
	 * 处理流水界面
	 * 
	 * @param current
	 */
	private void updateStreamUI(int current) {
		LinearLayout linearLayout = (LinearLayout) Stream_Activity.streamActivity
				.findViewById(R.id.lin);
		// 滑动到流水界面，更新
		if (current == 1) {
			LS_DAO ls_DataBaseHelper = new LS_DAO();
			LSManager lsManager = new LSManager(ls_DataBaseHelper);
			
			String[] jString = now_date.split("-");
			int number = Integer.parseInt(jString[1]);// 得到当前月数
			System.out.println("当前月数：" + number);

			lsManager.getFrame(number);
		}
		// 滑出流水界面,清除流水界面
		else {
			linearLayout.removeAllViews();
		}
	}

	/**
	 * 处理记账界面
	 */
	private void updatejzUI() {
		JZ_DAO jz_DataBaseHelper = new JZ_DAO();
		jz_DataBaseHelper.updateBudgetRemain();
	}
	
	
	
}
