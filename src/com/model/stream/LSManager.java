package com.model.stream;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import android.R.integer;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity.Stream_Activity;
import com.dao.LS_DAO;
import com.dao.YS_DAO;
import com.yyyy.yyyy.R;

public class LSManager {
	private LS_DAO ls_DataBaseHelper;
	private LayoutInflater inflater;
	private LinearLayout[][] linearLayoutChild;
	private int i = 0;
	private String dateOfday = "";

	public LSManager(LS_DAO ls_DataBaseHelper) {
		// TODO Auto-generated constructor stub
		this.ls_DataBaseHelper = ls_DataBaseHelper;
		this.inflater = LayoutInflater.from(Stream_Activity.streamActivity);
	}

	/**
	 * 查询所有流水信息更新流水界面
	 * 
	 * @param linearLayout
	 */
	@SuppressLint({ "NewApi", "InflateParams" })
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
			LinearLayout[] linearLayoutChild = new LinearLayout[number];
			String consumeString; // 消费
			String kindString; // 消费类别
			String dateString; // 流水时间
			String inOrOutString; // 收入支出
			String[] dates; // 切分时间用
			int i = 0; // i做为TextView数组的索引
			while (cursor.moveToNext()) {
				for (int j = 0; j < 3; j++)
					linearLayoutChild[i] = (LinearLayout) inflater
							.inflate(R.layout.streamchild_templte, null);

				TextView day = (TextView) linearLayoutChild[i].findViewById(R.id.day);
				TextView kind = (TextView) linearLayoutChild[i].findViewById(R.id.kind);
				TextView consume = (TextView) linearLayoutChild[i].findViewById(R.id.consume);

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

				if (!dates[2].equals(dateOfday)) {
					day.setText(dates[2]);
					this.dateOfday = dates[2];
				}
				kind.setText(kindString);
				consume.setText(consumeString);

				if (inOrOutString.equals("0"))
					consume.setTextColor(Color.RED);
				else {
					consume.setTextColor(0xFF9ACD32);
				}
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
	@SuppressLint({ "SimpleDateFormat", "NewApi", "InflateParams", "DefaultLocale" })
	public void getFrame(int number) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");

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

		linearLayoutChild = new LinearLayout[number][2];
		TextView[][] textView = new TextView[number][2];

		for (i = number - 1; i >= 0; i--) {
			textView[i][0] = new TextView(Stream_Activity.streamActivity);
			textView[i][1] = new TextView(Stream_Activity.streamActivity);
			linearLayoutChild[i][0] = (LinearLayout) inflater.inflate(R.layout.stream_templet, null);
			linearLayoutChild[i][1] = new LinearLayout(Stream_Activity.streamActivity);

			linearLayoutChild[i][1].setOrientation(LinearLayout.VERTICAL);
			linearLayoutChild[i][0].setId(i);// 用id标识点击的位置

			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, 1);
			String first = format.format(c.getTime());
			first = first.replaceAll("-", ".");

			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.MONTH, i);
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			String last = format.format(ca.getTime());
			last = last.replaceAll("-", ".");
			String date = first + "-" + last;
			TextView dateTextView = (TextView) linearLayoutChild[i][0].findViewById(R.id.monthfirsttolast);
			dateTextView.setText(date);

			TextView month = (TextView) linearLayoutChild[i][0].findViewById(R.id.month);
			String monthString = String.format("%02d", i + 1);
			month.setText(monthString);

			// 查询本月余额
			String yearAndMonth = Stream_Activity.year + "-" + String.format("%02d", (i + 1));
			String remain = YS_DAO.read_remain(yearAndMonth);
			if (remain != null && remain.length() > 0) {
				TextView remainTextView = (TextView) linearLayoutChild[i][0].findViewById(R.id.monthleft);
				remainTextView.setText(remain);
			}

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
