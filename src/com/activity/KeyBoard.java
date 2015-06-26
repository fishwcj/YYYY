package com.activity;

import com.mnitools.IInputCheck;
import com.mnitools.InputCheck;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KeyBoard extends Activity {
	private TextView number0;
	private TextView number1;
	private TextView number2;
	private TextView number3;
	private TextView number4;
	private TextView number5;
	private TextView number6;
	private TextView number7;
	private TextView number8;
	private TextView number9;
	private TextView number_float;
	private TextView number_clear;
	private TextView number_ok;
	private TextView number;
	private String consume = "";
	LinearLayout linearLayout;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyboard);
		number0 = (TextView)this.findViewById(R.id.number_0);
		number1 = (TextView)this.findViewById(R.id.number_1);
		number2 = (TextView)this.findViewById(R.id.number_2);
		number3 = (TextView)this.findViewById(R.id.number_3);
		number4 = (TextView)this.findViewById(R.id.number_4);
		number5 = (TextView)this.findViewById(R.id.number_5);
		number6 = (TextView)this.findViewById(R.id.number_6);
		number7 = (TextView)this.findViewById(R.id.number_7);
		number8 = (TextView)this.findViewById(R.id.number_8);
		number9 = (TextView)this.findViewById(R.id.number_9);
		number_float = (TextView)this.findViewById(R.id.number_float);
		number_clear = (TextView)this.findViewById(R.id.number_clear);
		number_ok = (TextView)this.findViewById(R.id.ok);
		number = (TextView)this.findViewById(R.id.number);
		
		IInputCheck inputCheck = new InputCheck(number, consume);
		inputCheck.setLisener_number(number0, "0");
		inputCheck.setLisener_number(number1, "1");
		inputCheck.setLisener_number(number2, "2");
		inputCheck.setLisener_number(number3, "3");
		inputCheck.setLisener_number(number4, "4");
		inputCheck.setLisener_number(number5, "5");
		inputCheck.setLisener_number(number6, "6");
		inputCheck.setLisener_number(number7, "7");
		inputCheck.setLisener_number(number8, "8");
		inputCheck.setLisener_number(number9, "9");
		inputCheck.setLisener_clear(number_clear);
		inputCheck.setLisener_float(number_float, ".");
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		this.id = bundle.getInt("id");
		this.linearLayout = (LinearLayout)YS1_Activity.ysActivity.findViewById(id);
		number_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String numberString = number.getText().toString();
				TextView textView = (TextView)linearLayout.getChildAt(1);
				if(id != R.id.total_ys){
					isOverFlow(textView, numberString);
				}else{
					YS1_Activity.total = Float.parseFloat(numberString);
				}
				textView.setText(numberString);
				number.setText("");
				finish();
			}
		});
		
	}
	
	/**
	 * 子预算超过总算则更新总预算显示
	 * @param textView
	 * @param newbudgetString
	 */
	public void isOverFlow(TextView textView, String newbudgetString){
		float oldbudget = Float.parseFloat(textView.getText().toString());
		float newbudget = Float.parseFloat(newbudgetString);
		if((YS1_Activity.childbudget + (newbudget - oldbudget)) > YS1_Activity.total){
			YS1_Activity.childbudget += newbudget - oldbudget;
			YS1_Activity.total = YS1_Activity.childbudget;
			YS1_Activity.totalbudget.setText(((Float)YS1_Activity.total).toString());
		}else {
			YS1_Activity.childbudget += newbudget - oldbudget;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
