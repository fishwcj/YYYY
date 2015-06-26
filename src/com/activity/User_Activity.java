package com.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.logic.BackgroundColor;
import com.model.user.ChangeUserMsg;
import com.model.user.IsLogin;
import com.model.user.Regist;
import com.model.user.UserLogin;
import com.yyyy.yyyy.R;

public class User_Activity extends Activity {
	public static Activity user_Activity;
	private TextView back;

	private EditText id;
	private EditText password;
	private ImageView portrait;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		user_Activity = this;
		String sql = "select * from user";
		Cursor cursor = (Cursor) Index_Activity.basicDAO.selectCursor(sql);
		cursor.moveToLast();
		if (IsLogin.isLogin()) {
			setContentView(R.layout.activity_logined);
			final TextView name;
			TextView id;
			TextView changeOthers;
			TextView changeUserName;
			TextView changePassword;
			name = (TextView) this.findViewById(R.id.name);
			id = (TextView) this.findViewById(R.id.ID);
			changeOthers = (TextView) this.findViewById(R.id.logined);
			portrait = (ImageView) this.findViewById(R.id.portrait);
			name.setText(cursor.getString(cursor.getColumnIndex("name")));// ��ʾ�ǳ�
			Integer integer_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
			String longid = String.format("%06d", integer_id);
			id.setText("ID:" + longid);// ��ʾid
			changeUserName = (TextView) findViewById(R.id.changeusername);
			changePassword = (TextView) this.findViewById(R.id.changepassword);

			try {
				portrait.setImageBitmap(getPortrat());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// �޸�ͷ��
			portrait.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					getImageFromAlbum();
				}
			});

			// �޸��ǳ�
			changeUserName.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					final EditText inputServer = new EditText(User_Activity.user_Activity);
					inputServer.setFocusable(true);

					AlertDialog.Builder builder = new AlertDialog.Builder(User_Activity.user_Activity);
					builder.setTitle("�޸��ǳ�").setIcon(R.drawable.ic_launcher).setView(inputServer)
							.setNegativeButton("ȡ��", null);
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							final ProgressDialog pd = new ProgressDialog(user_Activity);
							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									String newname = inputServer.getText().toString();
									System.out.println("���뵽�޸��̣߳�" + newname);
									ChangeUserMsg.changeName(newname);
									pd.dismiss();
								}
							}).start();
							name.setText(inputServer.getText().toString());
						}
					});
					builder.show();
				}
			});

			// �޸�����
			changePassword.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					final EditText inputServer = new EditText(User_Activity.user_Activity);
					inputServer.setFocusable(true);

					AlertDialog.Builder builder = new AlertDialog.Builder(User_Activity.user_Activity);
					builder.setTitle("����ԭ����").setIcon(R.drawable.ic_launcher).setView(inputServer)
							.setNegativeButton("ȡ��", null);
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							final ProgressDialog pd = ProgressDialog.show(User_Activity.this, "��֤����",
									"������֤�У����Ժ󡭡�");
							new Thread(new Runnable() {

								@SuppressLint("ShowToast")
								@Override
								public void run() {
									boolean tag = false;
									String password = inputServer.getText().toString();
									tag = ChangeUserMsg.checkPassWord(password);
									pd.dismiss();
									if (tag) {
										Intent intent = new Intent(User_Activity.user_Activity,
												Chang_Activity.class);
										startActivity(intent);
									} else {
										Toast.makeText(User_Activity.user_Activity, "�����������",
												Toast.LENGTH_SHORT);
									}
								}
							}).start();
						}
					});
					builder.show();
				}
			});

			changeOthers.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(User_Activity.this, Login_Activity.class);
					startActivity(intent);
				}
			});

		} else {
			setContentView(R.layout.activity_user);
			Button login;
			TextView get;

			login = (Button) this.findViewById(R.id.login);
			get = (TextView) this.findViewById(R.id.get);
			id = (EditText) this.findViewById(R.id.userid);
			password = (EditText) this.findViewById(R.id.userpassword);
			login.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final ProgressDialog pd = ProgressDialog.show(User_Activity.this, "��¼", "��¼�У����Ժ󡭡�");
					String idString = id.getText().toString();
					String passwordString = password.getText().toString();
					UserLogin userLogin = new UserLogin();

					/*
					 * 1��������� 2��������� 3����ʼ���û�
					 */
					if (userLogin.passwordIsOK(idString, passwordString)) {
						pd.dismiss();
						final ProgressDialog _pd = ProgressDialog.show(User_Activity.this, "��¼�ɹ�",
								"�û����ݳ�ʼ�������Ժ󡭡�");
						userLogin.getData();
						if (userLogin.initData()) {
							_pd.dismiss();
							Intent intent = new Intent(User_Activity.this, Index_Activity.class);
							UserLogin._basicDAO.closeDB();
							startActivity(intent);
						}
					}
				}
			});

			/**
			 * ��ȡ��ע���û�
			 */
			get.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final ProgressDialog pd = ProgressDialog.show(User_Activity.this, "ע��", "�Զ�ע���У����Ժ󡭡�");
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Regist register = new Regist();
							register.getNewID();
							pd.dismiss();
						}
					}).start();
				}
			});
		}
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				User_Activity.this.finish();

				BackgroundColor backgroundColor = new BackgroundColor();
				backgroundColor.refreshback();
			}
		});
	}

	protected void getImageFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// ��Ƭ����
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			System.out.println("��ȡͼƬʧ��");
			return;
		}

		Bitmap bm = null;
		ContentResolver resolver = getContentResolver();
		if (requestCode == 1) {

			Uri originalUri = data.getData(); // ���ͼƬ��uri

			 try {
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			bm = getRes("");
			System.out.println("URI��" + originalUri.toString());

			// �Եõ�bitmapͼƬ
			portrait.setImageBitmap(bm);
			try {
				FileOutputStream os = User_Activity.this.openFileOutput("portrat.png", MODE_PRIVATE);
				ByteArrayOutputStream btos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.PNG, 100, btos);
				byte[] byte1 = btos.toByteArray();
				System.out.println("�ֽڳ��ȣ�"+ byte1.length);
				os.write(byte1);
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Bitmap getRes(String name) {
		// �����Դ
		Resources rec = getResources();
		// BitmapDrawable
		BitmapDrawable bitmapDrawable = (BitmapDrawable) rec.getDrawable(R.drawable.ys);
		// �õ�Bitmap
		Bitmap bitmap = bitmapDrawable.getBitmap();
		return bitmap;
	}
	
	private Bitmap getPortrat() throws IOException{
		FileInputStream ins = User_Activity.this.openFileInput("portrat.png");
		int length = ins.available();
		byte[] byte1 = new byte[length];
		ins.read(byte1);
		return BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
	}
}
