package com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.dao.Borrow_DAO;
import com.activity.BorrowItemShow;
import com.yyyy.yyyy.R;

public class Borrow_Return extends Activity {
	private TextView additem;// 添加借贷项目；
	private TextView main;// 返回主页面

	private TextView borrow_total;// 总的借出或借入的钱数
	private ListView borrow_list;// 借出的明细
	private TextView show_total;

	private RadioGroup inOrOut;
	private RadioButton out;
	private RadioButton in;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_borrow__return1);


		additem = (TextView) this.findViewById(R.id.add_borrow_item);
		main = (TextView) this.findViewById(R.id.main);
		borrow_total = (TextView) this.findViewById(R.id.borrow);
		show_total = (TextView) this.findViewById(R.id.show_total);

		inOrOut = (RadioGroup) this.findViewById(R.id.inOrOut);
		out = (RadioButton) this.findViewById(R.id.out);
		in = (RadioButton) this.findViewById(R.id.in);

		borrow_list = (ListView) this.findViewById(R.id.borrow1);
		/*
		 * 添加借贷项目
		 */
		additem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Borrow_Return.this, Add_Borrow_item.class);
				startActivity(intent);
			}
		});
		/*
		 * 返回到主页面
		 */
		main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Borrow_Return.this, Index_Activity.class);
				startActivity(intent);

			}
		});
		tolerantShow();//默认显示接触的信息

		inOrOut.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int buttonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) Borrow_Return.this
						.findViewById(buttonId);

				if (rb == out) {
					borrow_list.setAdapter(getAdapter(0));
					show_total.setText("借出");
					rb.setBackground(getResources().getDrawable(
							R.drawable.button_shape_down));
					rb.setTextColor(Color.BLUE);
					in.setBackground(getResources().getDrawable(
							R.drawable.button_shape1));
					in.setTextColor(Color.WHITE);
				} else {
					borrow_list.setAdapter(getAdapter(1));
					show_total.setText("借入");
					rb.setBackground(getResources().getDrawable(
							R.drawable.button_shape_down1));

					rb.setTextColor(Color.BLUE);
					out.setBackground(getResources().getDrawable(
							R.drawable.button_shape));
					out.setTextColor(Color.WHITE);
				}

			}
		});
		borrow_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView id1 = (TextView)Borrow_Return.this.findViewById(R.id.id);
				    String id2 =  id1.getText().toString();
					Intent intent = new Intent();
					intent.putExtra("id", id2);
					//intent.putExtra("borrow_date", bo)
					intent.setClass(Borrow_Return.this, BorrowItemShow.class);
					startActivity(intent);
			}
		});
	}

	/*
	 * 
	 * 默认显示借出的信息
	 */
	@SuppressLint("NewApi")
	private void tolerantShow(){
		borrow_list.setAdapter(getAdapter(0));
		show_total.setText("借出");
		out.setBackground(getResources().getDrawable(
				R.drawable.button_shape_down));
		out.setTextColor(Color.BLUE);
		in.setBackground(getResources().getDrawable(R.drawable.button_shape1));
		in.setTextColor(Color.WHITE);
	}
	/*
	 * 按类别从数据库获取数据
	 */
	private List<Map<String, Object>> getData(int kind) {
		String total_Borrow = Borrow_DAO.getTotal(kind).toString();
		borrow_total.setText(total_Borrow);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Cursor cursor = Borrow_DAO.getList(kind);
		while (cursor.moveToNext()) {
			map.put("id", cursor.getInt(cursor.getColumnIndex("id")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("money", cursor.getFloat(cursor.getColumnIndex("money")));
			map.put("date",
					cursor.getString(cursor.getColumnIndex("return_time")));
			list.add(map);
		}
		return list;
	}

	/*
	 * 得到list的适配器
	 */
	private SimpleAdapter getAdapter(int kind) {
		SimpleAdapter adapterBorrow = new SimpleAdapter(this, getData(kind),
				R.layout.borrow_return_list_item, new String[] { "Id","name",
						"money", "date" }, new int[] { R.id.id, R.id.name,R.id.money,
						R.id.returnDate });
		return adapterBorrow;
	}
}
