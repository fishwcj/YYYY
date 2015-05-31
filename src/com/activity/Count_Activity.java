package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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

		time = getResources().getStringArray(R.array.select_time);
		nowDate = GetNowDate.getNowDate("yyyy-MM");
		nowDateY = GetNowDate.getNowDate("yyyy");
		nowDateM = GetNowDate.getNowDate("MM");
		InowDateM = Integer.parseInt(nowDateM);
		InowDateY = Integer.parseInt(nowDateY);
		show_time.setText(nowDate);

		// 初始化
		UpdateView(GetNowDate.getNowDate("yyyy-MM"));
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
					UpdateView(nowDate);
				} else {
					InowDateY--;
					InowDateM = 12;
					nowDate = InowDateY + "-" + InowDateM;
					UpdateView(nowDate);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();  
    }

	/**
	 * @param nowdate
	 *            画统计图
	 */
	private void UpdateView(String nowdate) {
		show_time.setText(nowdate);
		PieChart pie = new PieChart(nowdate);
		Draw_chart.draw_Chart(pie);
		LineChart line = new LineChart(nowdate);
		Draw_chart.draw_Chart(line);
	}
}
