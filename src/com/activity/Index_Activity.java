package com.activity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.dao.JZ_DAO;
import com.dao.basic.BasicDAO;
import com.inteface.IBasicDAO;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.logic.BackgroundColor;
import com.logic.Index_ContorlHelper;
import com.logic.SampleListFragment;
import com.model.cloud.CloudMessageManager;
import com.model.user.Init;
import com.yyyy.yyyy.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("deprecation")
@SuppressLint("InflateParams")
public class Index_Activity extends FragmentActivity {

	Context context = null;

	private ViewPager viewPager;
	@SuppressWarnings("unused")
	private PagerTitleStrip pagerTitleStrip;
	private List<View> views;
	public static Activity indexActivity;
	public static float remain;// 剩余预算
	public static float budget;// 总预算
	LocalActivityManager manager = null;
	public static IBasicDAO basicDAO = null;
	static int SIGN = 0;// 第一次启动，SIGN = 0;标志位
	int current = 0;
	int passed = -1;
	Index_ContorlHelper index_ContorlHelper;
	
	private SlidingMenu menu;//侧滑菜单

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
//			ArrayList<String> results = data.getStringArrayListExtra(SpeechRecognizer.RESULTS_RECOGNITION);
			((JZ_Activity) JZ_Activity.jzActivity).onResults(data.getExtras());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		basicDAO.closeDB();// 关闭数据库
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (SIGN != 0) {
			// 更新记账界面预算显示
			JZ_DAO jz_DataBaseHelper = new JZ_DAO();
			jz_DataBaseHelper.updateBudgetRemain();
			BackgroundColor backgroundColor = new BackgroundColor();
			backgroundColor.refreshback();
			System.out.println("调用了resum");
		}
		SIGN++;
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		context = Index_Activity.this;
		indexActivity = this;
		manager = new LocalActivityManager(this, true);
		basicDAO = new BasicDAO();
		basicDAO.connectDataBase("");
		manager.dispatchCreate(savedInstanceState);

		// 获得监听对象
		index_ContorlHelper = new Index_ContorlHelper(Index_Activity.this);
		// 获得viewPager
		viewPager = (ViewPager) this.findViewById(R.id.viewpager);

		views = new ArrayList<View>();

		Intent intent_jzIntent = new Intent(context, JZ_Activity.class);
		intent_jzIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		views.add(getView("JZ_Activity", intent_jzIntent));

		Intent intent_streamIntent = new Intent(context, Stream_Activity.class);
		intent_streamIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View streamView = getView("Stream_Activity", intent_streamIntent);
		views.add(streamView);

		Intent intent_countIntent = new Intent(context, Count_Activity.class);
		intent_countIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		views.add(getView("Count_Activity", intent_countIntent));

		// 第一次启动更新记账界面预算显示
		JZ_DAO jz_DataBaseHelper = new JZ_DAO();
		jz_DataBaseHelper.updateBudgetRemain();

		// 请求是否有推送消息
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		CloudMessageManager manager1 = new CloudMessageManager();
		manager1.getMessage(mNotificationManager);

		viewPager.setAdapter(new MyAdapter());
		try {
			@SuppressWarnings("unused")
			Init init = new Init();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("初始化报错");
			e.printStackTrace();
		}
		/**
		 * 页面滑动改变状态监听
		 */
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			/**
			 * 当页面被选中时执行此函数刷新页面
			 */
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				System.out.println("选择了-------------" + arg0);
				passed = current;
				current = arg0;
				// 监听
				index_ContorlHelper.getDecide(current, passed);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		// 更新界面颜色
		BackgroundColor backgroundColor = new BackgroundColor();
		backgroundColor.refreshback();
		// 更新消费
		String consumed = new DecimalFormat("0.0").format(budget - remain);
		System.out.println("格式化之后的浮点数" + consumed);
		JZ_Activity.consumed.setText(consumed);

		 //初始化滑动菜单  
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
	
	
	
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	class MyAdapter extends PagerAdapter {
		public MyAdapter() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			System.out.println("销毁的view为" + position);
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(views.get(position));
			System.out.println("初始化的view为" + position);
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
	}
}
