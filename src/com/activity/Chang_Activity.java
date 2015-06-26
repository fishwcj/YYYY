package com.activity;

import com.model.user.ChangeUserMsg;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Chang_Activity extends Activity {
	private EditText newpassword;
	private EditText renewpassword;
	private Button ok;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changemessage);
		newpassword = (EditText)this.findViewById(R.id.newpassword);
		renewpassword = (EditText)this.findViewById(R.id.renewpassword);
		ok = (Button)this.findViewById(R.id.ok);
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String newpasswordString = newpassword.getText().toString();
				String renewpasswordString = renewpassword.getText().toString();
				
				if (newpasswordString.equals(renewpasswordString)) {
					boolean tag = ChangeUserMsg.changePassWord(newpasswordString);
					if(tag){
						Toast.makeText(User_Activity.user_Activity, "ÐÞ¸Ä³É¹¦", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Chang_Activity.this,User_Activity.class);
						startActivity(intent);
					}
				}
			}
		});
	}
}
