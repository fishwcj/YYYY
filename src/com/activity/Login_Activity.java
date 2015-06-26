package com.activity;

import com.model.user.UserLogin;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_Activity extends Activity {
	public static Activity login_Activity = null;
	private EditText id;
	private EditText password;
	private Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login_Activity = this;
		id = (EditText) this.findViewById(R.id.userid);
		password = (EditText) this.findViewById(R.id.userpassword);
		login = (Button) this.findViewById(R.id.userlogin);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final ProgressDialog pd = ProgressDialog.show(Login_Activity.this, "登录", "登录中，请稍后……");
				String idString = id.getText().toString();
				String passwordString = password.getText().toString();
				UserLogin userLogin = new UserLogin();

				/*
				 * 1、检测密码 2、获得数据 3、初始化用户
				 */
				if (userLogin.passwordIsOK(idString, passwordString)) {
					pd.dismiss();
					final ProgressDialog _pd = ProgressDialog.show(Login_Activity.this, "登录成功",
							"用户数据初始化，请稍后……");
					userLogin.getData();
					if (userLogin.initData()) {
						_pd.dismiss();
						Intent intent = new Intent(Login_Activity.this, Index_Activity.class);
						UserLogin._basicDAO.closeDB();
						startActivity(intent);
					}
				}
			}
		});
	}
}
