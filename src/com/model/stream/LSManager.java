package com.model.stream;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity.Stream_Activity;
import com.dao.LS_DAO;
import com.yyyy.yyyy.R;

public class LSManager {
	private LS_DAO ls_DataBaseHelper;
	LinearLayout[][] linearLayoutChild;
	int i = 0;

	public LSManager(LS_DAO ls_DataBaseHelper) {
		// TODO Auto-generated constructor stub
		this.ls_DataBaseHelper = ls_DataBaseHelper;
	}

	/**
	 * 查询所有流水信息更新流水界面
	 * 
	 * @param linearLayout
	 */
	@SuppressLint("NewApi")
	public void updateStreamLayout(LinearLayout linearLayout, String date) {
		// 查询流水返回游标
		Cursor cursor = ls_DataBaseHelper.selectAllAccount(date);
		int number = cursor.getCount();
		// 动态生成xml布局
		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		LP_FW.weight = 1;
		System.out.println("日期是" + date);
		if (number > 0) {
			// linearLayoutChild样式
			LinearLayout.LayoutParams LP_FW1 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			LP_FW1.height = 100;
			LP_FW1.topMargin = 1;
			Activity streamActivity = Stream_Activity.streamActivity;
			LinearLayout[] linearLayoutChild = new LinearLayout[number];
			TextView[][] textView = new TextView[number][3];
			String consumeString; // 消费
			String kindString; // 消费类别
			String dateString; // 流水时间
			String inOrOutString; // 收入支出
			String[] dates; // 切分时间用
			String style; // 文字html处理
			int i = 0; // i做为TextView数组的索引
			// 得到linearLayout的样式
			Drawable drawble = streamActivity.getResources().getDrawable(R.drawable.stream_textview);
			while (cursor.moveToNext()) {
				for (int j = 0; j < 3; j++)
					textView[i][j] = new TextView(streamActivity);
				linearLayoutChild[i] = new LinearLayout(streamActivity);
				linearLayoutChild[i].setBackground(drawble);
				linearLayoutChild[i].setLayoutParams(LP_FW1);
				// 从游标获得结果
				inOrOutString = cursor.getString(cursor.getColumnIndex("inorout"));
				if (inOrOutString.equals("0"))
					consumeString = "-" + cursor.getString(cursor.getColumnIndex("consume"));
				else {
					consumeString = "+" + cursor.getString(cursor.getColumnIndex("consume"));
				}
				kindString = cursor.getString(cursor.getColumnIndex("kind"));
				dateString = cursor.getString(cursor.getColumnIndex("date"));

				dates = dateString.split(" ");
				dateString = Date.valueOf(dates[0]).toString();
				dates = dateString.split("-");

				// 设置第一个textView:显示日期
				style = "<font color=\"#2F4F4F\">" + dates[2] + "日</font>";
				textView[i][0].setText(Html.fromHtml(style));
				textView[i][0].setLayoutParams(LP_FW);
				linearLayoutChild[i].addView(textView[i][0]);

				// 设置第二个textView:显示消费类别
				style = "<span><font color=\"#EE5C42\"><big>" + kindString + "</big></font></span>";
				textView[i][1].setText(Html.fromHtml(style));
				textView[i][1].setLayoutParams(LP_FW);
				textView[i][1].setGravity(Gravity.CENTER);
				linearLayoutChild[i].addView(textView[i][1]);

				// 设置第三个textView:显示消费金额
				style = "<span><font><big>" + consumeString + "</big>元</font></span>";
				textView[i][2].setText(Html.fromHtml(style));
				textView[i][2].setLayoutParams(LP_FW);
				textView[i][2].setGravity(Gravity.CENTER);
				if (inOrOutString.equals("0"))
					textView[i][2].setTextColor(Color.RED);
				else {
					textView[i][2].setTextColor(0xFF9ACD32);
				}
				linearLayoutChild[i].addView(textView[i][2]);
				// 将LinearLayoutChild加入到父容器
				linearLayout.addView(linearLayoutChild[i]);
				i++;
			}
		} else {
			TextView textView = new TextView(Stream_Activity.streamActivity);
			textView.setText("本月没有记录任何消费");
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(LP_FW);
			linearLayout.addView(textView);
		}
	}

	/**
	 * 创建流水界面 分月
	 * 
	 * @param number
	 *            月数
	 */
	@SuppressLint({ "SimpleDateFormat", "NewApi" })
	public void getFrame(int number) {
		LinearLayout linearLayout = (LinearLayout) Stream_Activity.streamActivity.findViewById(R.id.lin);
		linearLayout.removeAllViews();// 先清屏

		// textview样式
		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		LP_FW.weight = 1;

		// linearLayoutChild样式
		LinearLayout.LayoutParams LP_FW1 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LP_FW1.height = 80;
		LP_FW1.topMargin = 1;

		// linearLayoutChild[i][0]样式
		LinearLayout.LayoutParams LP_FW2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayoutChild = new LinearLayout[number][2];
		TextView[][] textView = new TextView[number][2];
		Drawable drawble = Stream_Activity.streamActivity.getResources().getDrawable(R.drawable.ls);

		for (i = number - 1; i >= 0; i--) {
			textView[i][0] = new TextView(Stream_Activity.streamActivity);
			textView[i][1] = new TextView(Stream_Activity.streamActivity);
			linearLayoutChild[i][0] = new LinearLayout(Stream_Activity.streamActivity);
			linearLayoutChild[i][0].setBackground(drawble);
			linearLayoutChild[i][1] = new LinearLayout(Stream_Activity.streamActivity);
			String style = "<font color=\"#9ACD32\"><big><big>" + (i + 1) + "月</big></big></font>";
			textView[i][0].setText(Html.fromHtml(style));
			textView[i][0].setLayoutParams(LP_FW);
			linearLayoutChild[i][0].addView(textView[i][0]);
			style = "<font color=\"#9ACD32\"><big></big></font>";
			textView[i][1].setText(Html.fromHtml(style));
			linearLayoutChild[i][0].addView(textView[i][1]);
			linearLayoutChild[i][0].setLayoutParams(LP_FW1);
			linearLayoutChild[i][1].setLayoutParams(LP_FW2);
			linearLayoutChild[i][1].setOrientation(LinearLayout.VERTICAL);
			linearLayoutChild[i][0].setId(i);// 用id标识点击的位置
			linearLayoutChild[i][1].setId(0);
			linearLayoutChild[i][0].setClickable(true);
			linearLayoutChild[i][0].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int location = v.getId();
					int month = location + 1;
					TextView textView = (TextView) Stream_Activity.streamActivity.findViewById(R.id.thisyear);
					// 构造时间
					String year = (String) textView.getText();
					String dateString = year + "-" + month + "-" + "01";
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date;
					try {
						date = format.parse(dateString);
						dateString = format.format(date);
						LSManager lsManager = new LSManager(ls_DataBaseHelper);
						if (linearLayoutChild[location][1].getId() == 0) {
							lsManager.updateStreamLayout(linearLayoutChild[location][1], dateString);
							linearLayoutChild[location][1].setId(1);
						} else {
							linearLayoutChild[location][1].removeAllViews();
							linearLayoutChild[location][1].setId(0);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			linearLayout.addView(linearLayoutChild[i][0]);
			linearLayout.addView(linearLayoutChild[i][1]);
		}
	}

	/**
	 * 点击上一年
	 */
	public void getLastYear() {
		getFrame(12);
	}

	/**
	 * 点击下一年
	 * 
	 * @param year
	 *            年数
	 */
	@SuppressLint("SimpleDateFormat")
	public void getNextYear(int year) {
		// 得到当前日期
		java.util.Date currentDate = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String currenString = format.format(currentDate);
		String[] jString = currenString.split("-");
		int nowYear = Integer.parseInt(jString[0]);// 当前年
		int number = Integer.parseInt(jString[1]);// 当前月
		if (year == nowYear) {
			getFrame(number);
		} else {
			getFrame(12);
		}

	}
}
