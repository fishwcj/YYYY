package com.logic;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity.Index_Activity;
import com.activity.JZ_Activity;
import com.activity.SelectPicPopupWindow;
import com.dao.basic.SQLString;
import com.yyyy.yyyy.R;

public class ConsumKindManager {
	private TextView kind;// 类型
	private LayoutInflater inflater;
	Drawable drawable = SelectPicPopupWindow.selectPicPopupWindow.getResources().getDrawable(
			R.drawable.blackbutton);

	public ConsumKindManager() {
		kind = (TextView) JZ_Activity.jzActivity.findViewById(R.id.kind);
		this.inflater = LayoutInflater.from(Index_Activity.indexActivity);
	}

	/**
	 * 更新右边二级菜单
	 * 
	 * @param mainkind
	 *            左边以及菜单编号
	 * @param layout
	 *            右边布局
	 */
	@SuppressLint("NewApi")
	public void freshButton(int mainkind, LinearLayout layout) {
		layout.removeAllViews();
		String sql = SQLString.getFreshButton_Co(mainkind);
		Cursor cursor = (Cursor) Index_Activity.basicDAO.selectCursor(sql);
		int number = cursor.getCount();
		LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		LP_FW.height = 80;
		TextView[] textView = new TextView[number];
		cursor.moveToNext();
		for (int i = 0; i < number; i++) {
			LinearLayout secondKind = (LinearLayout)inflater.inflate(R.layout.kind_second, null);
			String kindname = cursor.getString(cursor.getColumnIndex("kindname"));
			TextView secondKindView = (TextView)secondKind.findViewById(R.id.secondkind);
			secondKindView.setText(kindname);
			secondKindView.setId(number + 1);
			secondKindView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					TextView view = (TextView) v;
					String kindname = kind.getText().toString() + view.getText().toString();
					kind.setText(kindname);
					SelectPicPopupWindow.selectPicPopupWindow.finish();
				}
			});
			cursor.moveToNext();
			layout.addView(secondKind);
		}
	}
}
