package com.mnitools;

import android.view.View;
import android.widget.TextView;

public class InputCheck implements IInputCheck {

	private String viewString = null;//显示字符串
	private TextView editText = null;//键盘显示textView
	public InputCheck(TextView editText,String viewString) {
		// TODO Auto-generated constructor stub
		this.viewString = viewString;
		this.editText = editText;
	}	
	
	@Override
	public void setLisener_number(View button,final String number) {
		// TODO Auto-generated method stub
		
		/**
		 * 按钮事件
		 */
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check(viewString)) { // 检测输入是否合法
					viewString += number;
					editText.setText(viewString.toCharArray(), 0,
							viewString.length());
				}
			}
		});
	}

	@Override
	public void setLisener_float(View button, final String point) {
		// TODO Auto-generated method stub
		// this.number = point;
		/**
		 * 按钮.的事件
		 */
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check(viewString)) {
					if (viewString.length() > 0
							&& (viewString.indexOf(".") < 0)) {
						viewString += point;
						editText.setText(viewString.toCharArray(), 0,
								viewString.length());
					}
				}
			}
		});
	}

	/**
	 * 检测输入是否合法
	 * 
	 * @param viewString
	 *            编辑栏字符串
	 * @return
	 */
	public boolean check(String viewString) {
		boolean TAG = false;
		// 浮点数
		if (viewString.contains(".")) {
			if (viewString.length() - viewString.indexOf(".")< 3) {
				TAG = true;
			}
		} else {// 整数
			if (viewString.length() < 6) {
				TAG = true;
			}
		}
		return TAG;
	}

	@Override
	public void setLisener_clear(View button) {
		// TODO Auto-generated method stub
		/**
		 * 按钮C的事件
		 */
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewString = "";
				editText.setText(viewString.toCharArray(), 0,
						viewString.length());
			}
		});
	}
	
	@Override
	public void setViewString(String string){
		this.viewString = string;
	}
}
