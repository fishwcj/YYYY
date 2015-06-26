package com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.logic.SampleListFragment;
import com.mnitools.GetNowDate;
import com.model.count.Draw_chart;
import com.model.count.LineChart;
import com.model.count.PieChart;
import com.yyyy.yyyy.R;

public class Count_Activity extends FragmentActivity {
	public static Activity countActivity;
	private Button plus;
	private Button sub;
	private RadioGroup pieOrline;
	private RadioButton pie;
	
	private int kind = 0;
	private TextView show_time;
	LinearLayout ViewLayout;
	String[] time;
	String nowDate;// 年
	String nowDateY;
	String nowDateM;// 月
	int InowDateM;// int型月
	int InowDateY;// int型年
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count);
		ViewLayout = (LinearLayout) this.findViewById(R.id.view);
		countActivity = this;
		plus = (Button) findViewById(R.id.plus);
		sub = (Button) findViewById(R.id.sub);
		show_time = (TextView) findViewById(R.id.show_time);

		pieOrline = (RadioGroup) this.findViewById(R.id.pieOrline);
		pie = (RadioButton) this.findViewById(R.id.pie);
		

		time = getResources().getStringArray(R.array.select_time);
		nowDate = GetNowDate.getNowDate("yyyy-MM");
		nowDateY = GetNowDate.getNowDate("yyyy");
		nowDateM = GetNowDate.getNowDate("MM");
		InowDateM = Integer.parseInt(nowDateM);
		InowDateY = Integer.parseInt(nowDateY);
		show_time.setText(nowDate);

		pieOrline.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int buttonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) Count_Activity.this
						.findViewById(buttonId);

				if (rb == pie) {
					kind = 0;
					UpdateView(show_time.getText().toString(),kind);
				} else {
					kind = 1;
					UpdateView(show_time.getText().toString(),kind);
				}

			}
		});

		// 初始化
		UpdateView(GetNowDate.getNowDate("yyyy-MM"),kind);
		plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show_time.setText(nowDate);
				InowDateM = Integer.parseInt(nowDateM);
				PieChart pie = new PieChart(nowDate);
				Draw_chart.draw_Chart(pie);
				LineChart line = new LineChart(nowDate);
				Draw_chart.draw_Chart(line);

			}
		});
		sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ViewLayout.removeAllViews();

				String nowDate;

				if (InowDateM >= 2) {
					InowDateM--;
					nowDate = InowDateY + "-" + InowDateM;
					UpdateView(nowDate,kind);
				} else {
					InowDateY--;
					InowDateM = 12;
					nowDate = InowDateY + "-" + InowDateM;
					UpdateView(nowDate,kind);
				}
			}
		});
		initSlidingMenu();
	}

	private void initSlidingMenu() {

		// 设置滑动菜单的属性值
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置滑动菜单的视图界面
		menu.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SampleListFragment()).commit();
	}

	/**
	 * @param nowdate
	 *            画统计图
	 */
	private void UpdateView(String nowdate,int kind) {
		show_time.setText(nowdate);
		if(kind == 0){
			PieChart pie = new PieChart(nowdate);
			Draw_chart.draw_Chart(pie);
		}else {

			LineChart line = new LineChart(nowdate);
			Draw_chart.draw_Chart(line);
		}
		
	}
}
