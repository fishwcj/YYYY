package com.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.SrStreetBeanList;
import com.bean.StreetMessageBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.logic.SampleListFragment;
import com.model.street.GUIManager;
import com.model.street.StreetConnecter;
import com.model.street.StreetGUI;
import com.model.user.IsLogin;
import com.yyyy.yyyy.R;

public class Street_Activity extends FragmentActivity {
	public static Activity street_Activity;
	public static LinearLayout rootlinearLayout;
	public static ArrayList<StreetMessageBean> MessageBeanlist;
	private SwipeRefreshLayout swipeView;
	private ScrollView scrollView;
	private TextView bj;
	private TextView sjz;
	private SlidingMenu menu;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street);
		street_Activity = this;
		rootlinearLayout = (LinearLayout) this.findViewById(R.id.root);
		bj = (TextView) this.findViewById(R.id.bj);
		sjz = (TextView) this.findViewById(R.id.sjz);
		scrollView = (ScrollView) this.findViewById(R.id.touch);
		swipeView = (SwipeRefreshLayout) this.findViewById(R.id.swipe);
		swipeView.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light,
				android.R.color.holo_green_light, android.R.color.holo_green_light);
		ConnectThread connectThread = new ConnectThread();
		new Thread(connectThread).start();
		swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipeView.setRefreshing(true);
				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						StreetConnecter connecter = new StreetConnecter();
						connecter.connect();
						swipeView.setRefreshing(false);
					}
				}, 3000);
			}

		});

		scrollView.setOnTouchListener(new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					// ���Լ�����ScrollView�Ĺ����¼�
					if (scrollView.getScrollY() == 0) {
						swipeView.setEnabled(true);
					} else {
						swipeView.setEnabled(false);
					}
				}
				return false;
			}
		});

		bj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (IsLogin.isLogin()) {
					Intent intent = new Intent(Street_Activity.this, Publish_Activity.class);
					startActivity(intent);
				} else {
					Toast.makeText(Street_Activity.this, "Ŷ�죡�㻹û�е�¼~", Toast.LENGTH_SHORT).show();
				}
			}
		});

		sjz.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Street_Activity.this, Index_Activity.class);
				startActivity(intent);
			}
		});
		 //��ʼ�������˵�  
        initSlidingMenu();
	}

	private void initSlidingMenu() {  
		
        // ���û����˵�������ֵ  
        menu = new SlidingMenu(this);  
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  
        menu.setShadowWidthRes(R.dimen.shadow_width);  
        menu.setShadowDrawable(R.drawable.shadow);  
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);  
        menu.setFadeDegree(0.35f);  
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);  
        // ���û����˵�����ͼ����  
        menu.setMenu(R.layout.menu_frame);    
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment()).commit();  
    }  
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("��ʼ����");
			SrStreetBeanList list = (SrStreetBeanList) msg.obj;
			if (list != null) {
				ArrayList<StreetMessageBean> messageBeans = list.getList();
				GUIManager guiManager = new GUIManager();
				guiManager.createStreetGUI(messageBeans);// ��ʾ������
			} else {
				System.out.println("��ʼ��������");
				StreetGUI streetGUI = new StreetGUI(rootlinearLayout);
				streetGUI.createErrorGUI();
				Toast.makeText(Street_Activity.this, "�ף����翪С����~", Toast.LENGTH_SHORT).show();
			}

		}
	};

	public class ConnectThread implements Runnable {
		@Override
		public void run() {
			StreetConnecter connecter = new StreetConnecter();
			SrStreetBeanList list = connecter.connect();
			Message message = Message.obtain();
			message.obj = list;
			handler.sendMessage(message);
		}
	}
}
