package com.logic;

import java.net.MalformedURLException;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.activity.Borrow_Return;
import com.activity.Count_Activity;
import com.activity.Index_Activity;
import com.activity.JZ_Activity;
import com.activity.Stream_Activity;
import com.activity.Street_Activity;
import com.activity.Target_Activity;
import com.activity.User_Activity;
import com.activity.YS1_Activity;
import com.model.cloud.CloudSendHelper;
import com.yyyy.yyyy.R;

public class SampleListFragment extends Fragment {
	private TextView person;
	private TextView jz;
	private TextView ys;
	private TextView ls;
	private TextView fx;
	private TextView zyj;
	private TextView jd;
	private TextView zq;
	private TextView set;
	private TextView sy;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu, null);
		person = (TextView) view.findViewById(R.id.person);
		jz = (TextView) view.findViewById(R.id.jz);
		ys = (TextView) view.findViewById(R.id.ys);
		ls = (TextView) view.findViewById(R.id.ls);
		fx = (TextView) view.findViewById(R.id.fx);
		zyj = (TextView) view.findViewById(R.id.zyj);
		jd = (TextView) view.findViewById(R.id.jd);
		zq = (TextView) view.findViewById(R.id.zqmb);
		set = (TextView) view.findViewById(R.id.set);
		sy = (TextView) view.findViewById(R.id.tb);

		// 监听
		Action listener = new Action();
		person.setOnClickListener(listener);
		jz.setOnClickListener(listener);
		ys.setOnClickListener(listener);
		ls.setOnClickListener(listener);
		fx.setOnClickListener(listener);
		zyj.setOnClickListener(listener);
		jd.setOnClickListener(listener);
		zq.setOnClickListener(listener);
		set.setOnClickListener(listener);
		sy.setOnClickListener(listener);
		return view;
	}

	public class Action implements View.OnClickListener {
		Intent intent = new Intent();

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.person:
				intent.setClass(Index_Activity.indexActivity, User_Activity.class);
				startActivity(intent);
				break;
			case R.id.jz:
				intent.setClass(Index_Activity.indexActivity, Index_Activity.class);
				startActivity(intent);
				break;
			case R.id.ys:
				intent.setClass(Index_Activity.indexActivity, YS1_Activity.class);
				startActivity(intent);
				break;
			case R.id.ls:
				intent.setClass(Index_Activity.indexActivity, Stream_Activity.class);
				startActivity(intent);
				break;
			case R.id.fx:
				intent.setClass(Index_Activity.indexActivity, Count_Activity.class);
				startActivity(intent);
				break;
			case R.id.zyj:
				intent.setClass(Index_Activity.indexActivity, Street_Activity.class);
				startActivity(intent);
				break;
			case R.id.jd:
				intent.setClass(Index_Activity.indexActivity, Borrow_Return.class);
				startActivity(intent);
				break;
			case R.id.zqmb:
				intent.setClass(Index_Activity.indexActivity, Target_Activity.class);
				startActivity(intent);
				break;
			case R.id.set:
				intent.setClass(Index_Activity.indexActivity, User_Activity.class);
				startActivity(intent);
				break;
			case R.id.tb:
				sychronization();
				break;
			default:
				Toast.makeText(JZ_Activity.jzActivity, "未找到对应事件", Toast.LENGTH_LONG).show();
				break;
			}
		}

	}

	private void sychronization() {
		CloudSendHelper cloudSendHelper = new CloudSendHelper();
		try {
			try {
				if (cloudSendHelper.checkAndSend()) {
				} else {
					Toast.makeText(JZ_Activity.jzActivity, "同步前请登录哦！", Toast.LENGTH_LONG).show();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
