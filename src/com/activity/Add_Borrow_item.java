package com.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

//import com.bean.BorrowItem;
//import com.dao.Borrow_DAO;
import com.yyyy.yyyy.R;

public class Add_Borrow_item extends Activity {
	private TextView add_friend;
	private EditText time;
	private Button sure;

	private int year;
	private int month;
	private int day;

	DatePickerDialog dateDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add__borrow_item);

		// 从好友中添加名字
		add_friend = (TextView) this.findViewById(R.id.add_friend);
		// 点击的确定按钮
		sure = (Button) this.findViewById(R.id.ok);

		// 归还日期
		time = (EditText) this.findViewById(R.id.back_time);
		// 获取当前时间
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		// 设置监听器
		final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
				year = myyear;
				month = monthOfYear;
				day = dayOfMonth;
				updateDate();

			}

			// 更新时间显示
			private void updateDate() {
				time.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
						+ c.get(Calendar.DAY_OF_MONTH) + "---" + year + "-" + (month + 1) + "-" + day);
			}
		};
		/**
		 * 点击从好友列表中添加借贷人昵称
		 */
		add_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Add_Borrow_item.this, User_List.class);
				startActivity(intent);
			}
		});
		/**
		 * 点击设置归还时间
		 */
		time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dateDialog = new DatePickerDialog(Add_Borrow_item.this, dateListener, year, month, day);
				dateDialog.setTitle("请选择日期");
				dateDialog.show();

			}
		});
		/**
		 * 点击确定按钮
		 */
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * 将添加的内容写入到数据库
				 */
				// Borrow_DAO dao = new Borrow_DAO();

				/**
				 * 添加完成回到借贷管理页面
				 */
				Intent intent = new Intent();
				intent.setClass(Add_Borrow_item.this, Borrow_Return.class);
				startActivity(intent);
			}
		});
	}

}
