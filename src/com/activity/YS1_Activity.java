package com.activity;

import java.util.ArrayList;
import java.util.Iterator;

import com.dao.YS_DAO;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.logic.SampleListFragment;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YS1_Activity extends FragmentActivity {
	public static Activity ysActivity;
	public static TextView totalbudget;
	public static float childbudget = 0.0f;
	public static float total = 0.0f;
	YS_DAO ysDao = null;

	private LinearLayout totalLinearLayout;
	private LinearLayout spjs;
	private LinearLayout yfsp;
	private LinearLayout cxjt;
	private LinearLayout jjxf;

	private LinearLayout xxjx;
	private LinearLayout xxyl;
	private LinearLayout rjjw;
	private LinearLayout ylbj;

	private LinearLayout qtzx;

	private ArrayList<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();

	private TextView ok;
	private SlidingMenu menu;// 侧滑菜单

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ys1);
		ysActivity = this;
		this.ysDao = new YS_DAO();

		totalbudget = (TextView) this.findViewById(R.id.totalbudget);
		totalLinearLayout = (LinearLayout) this.findViewById(R.id.total_ys);
		spjs = (LinearLayout) this.findViewById(R.id.spjs_yy);
		yfsp = (LinearLayout) this.findViewById(R.id.yfsp_yy);
		cxjt = (LinearLayout) this.findViewById(R.id.cxjt_yy);
		jjxf = (LinearLayout) this.findViewById(R.id.jjxf_yy);

		xxjx = (LinearLayout) this.findViewById(R.id.xxjx_yy);
		xxyl = (LinearLayout) this.findViewById(R.id.xxyl_yy);
		ylbj = (LinearLayout) this.findViewById(R.id.ylbj_yy);
		rjjw = (LinearLayout) this.findViewById(R.id.rjjw_yy);

		qtzx = (LinearLayout) this.findViewById(R.id.qtzx_yy);
		ok = (TextView) this.findViewById(R.id.ok_y);

		linearLayoutList.add(spjs);
		linearLayoutList.add(yfsp);
		linearLayoutList.add(cxjt);
		linearLayoutList.add(jjxf);
		linearLayoutList.add(xxjx);
		linearLayoutList.add(xxyl);
		linearLayoutList.add(ylbj);
		linearLayoutList.add(rjjw);
		linearLayoutList.add(qtzx);

		Iterator<LinearLayout> iterator = linearLayoutList.iterator();
		while (iterator.hasNext()) {
			iterator.next().setOnClickListener(onClickListener);
		}
		totalLinearLayout.setOnClickListener(onClickListener);

		showBudget();// 读取预算对号入座

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				float[] budget = new float[linearLayoutList.size()];
				int[] kind = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
				for (int i = 0; i < linearLayoutList.size(); i++) {
					TextView textView = (TextView) linearLayoutList.get(i).getChildAt(1);
					budget[i] = Float.parseFloat(textView.getText().toString());
				}
				ysDao.add(budget, kind);
				ysDao.addtotal(total);
				Intent intent = new Intent(YS1_Activity.this, Index_Activity.class);
				startActivity(intent);
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
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SampleListFragment())
				.commit();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			LinearLayout vLinearLayout = (LinearLayout) v;
			callVKeyBoard(v.getId(), vLinearLayout.getChildAt(1));
		}

	};

	private void callVKeyBoard(int viewId, View childView) {
		Intent intent = new Intent(YS1_Activity.this, KeyBoard.class);
		intent.putExtra("id", viewId);
		startActivity(intent);
	}

	private void showBudget() {
		ysDao = new YS_DAO();
		Cursor cursor = ysDao.read_budget();
		Iterator<LinearLayout> iterator = linearLayoutList.iterator();
		while (cursor.moveToNext()) {
			TextView textView = (TextView) iterator.next().getChildAt(1);
			childbudget += cursor.getFloat(0);
			String budget = cursor.getFloat(0) + "";
			textView.setText(budget);
		}

		String totalString = ysDao.read_totalbudget();
		totalbudget.setText(totalString);
		total = Float.parseFloat(totalString);
	}
}
