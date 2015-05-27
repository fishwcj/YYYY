package com.activity;

import com.model.user.IsLogin;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Activity extends Activity {
	private EditText targetname;//目标名字
	private EditText targettime;
	private EditText targetcontent;
	private TextView targetok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		targetname = (EditText)this.findViewById(R.id.target_name);
		targettime = (EditText)this.findViewById(R.id.target_time);
		targetcontent = (EditText)this.findViewById(R.id.target_content);
		targetok = (TextView)this.findViewById(R.id.target_ok);
		targetok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = targetname.getText().toString();
				String time = targettime.getText().toString();
				String content = targetcontent.getText().toString();
				if(name.length() != 0){
					if(time.length() != 0){
						if(content.length() != 0){
							int _time = Integer.parseInt(time);
							if(IsLogin.initTarget(name, _time, content)){
								Intent intent = new Intent(Add_Activity.this,Target_Activity.class);
								Add_Activity.this.startActivity(intent);
							}
						}else{
							Toast.makeText(Add_Activity.this, "确定不填一下军令状？", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(Add_Activity.this, "你不设置一个任务完成的期限吗", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(Add_Activity.this, "别忘了名字~", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
