package com.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.dao.LS_DAO;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.logic.SampleListFragment;
import com.model.stream.LSManager;
import com.yyyy.yyyy.R;

public class Stream_Activity extends FragmentActivity {
	public static Activity streamActivity;
	private TextView lastyear;
	private TextView thisyear;
	private TextView nextyear;
	private int TAG = 0;
	private Integer year;
	private SlidingMenu menu;

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("进了resume");
	};

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stream);
		streamActivity = this;
		System.out.println("Stream被创建");
		// 获得现在年份
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		year = Integer.parseInt(format1.format(new Date()));
		lastyear = (TextView) this.findViewById(R.id.lastyear);
		thisyear = (TextView) this.findViewById(R.id.thisyear);
		nextyear = (TextView) this.findViewById(R.id.nextyear);
		thisyear.setText(year.toString());
		lastyear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TAG++;
				year--;
				if (TAG == 1) {
					nextyear.setClickable(true);
					nextyear.setText("下一年");
				}
				thisyear.setText(year.toString());
				System.out.println("TAG=" + TAG);

				LS_DAO ls_DataBaseHelper = new LS_DAO();
				LSManager lsManager = new LSManager(ls_DataBaseHelper);
				lsManager.getLastYear();
			}
		});

		nextyear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TAG--;
				year++;
				thisyear.setText(year.toString());
				if (TAG == 0) {
					nextyear.setText("");
					nextyear.setClickable(false);
				}
				LS_DAO ls_DataBaseHelper = new LS_DAO();
				LSManager lsManager = new LSManager(ls_DataBaseHelper);
				lsManager.getNextYear(year);
				System.out.println("TAG=" + TAG);
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
}
