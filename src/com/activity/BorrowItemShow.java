package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dao.Borrow_DAO;
import com.yyyy.yyyy.R;

public class BorrowItemShow extends Activity {
	private TextView main;
	private TextView showName;
	private TextView showKind;
	private TextView showMoney;
	private TextView showDate;
	private TextView showConn;
	private TextView showLocation;
	
	private Cursor cursor;
	Intent intent;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_borrow_item_show);
		intent = getIntent();
		id = Integer.parseInt(intent.getStringExtra("id"));
		System.out.println("hahahahaha "+id);
		main = (TextView)this.findViewById(R.id.main);
		showName = (TextView)this.findViewById(R.id.showName);
		showKind = (TextView)this.findViewById(R.id.showKind);
		showMoney = (TextView)this.findViewById(R.id.showMoney);
		showDate = (TextView)this.findViewById(R.id.showDate);
		showConn = (TextView)this.findViewById(R.id.showConn);
		showLocation = (TextView)this.findViewById(R.id.showLocation);
		
		main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BorrowItemShow.this, Index_Activity.class);
				startActivity(intent);

			}
		});
		cursor = Borrow_DAO.getAllMsg(id);
		while(cursor.moveToNext()){
			showName.setText(cursor.getString(cursor.getColumnIndex("name")));
			showKind.setText(cursor.getInt(cursor.getColumnIndex("kind")));
			showMoney.setText( cursor.getFloat(cursor.getColumnIndex("money"))+"");
			showDate.setText(cursor.getString(cursor.getColumnIndex("return_time")));
			showConn.setText(cursor.getString(cursor.getColumnIndex("remark")));
			showLocation.setText(cursor.getString(cursor.getColumnIndex("location")));
		}
	}
}
