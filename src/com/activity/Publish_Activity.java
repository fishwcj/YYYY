package com.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

import com.model.street.CreateMessageBean;
import com.model.street.PublishConnecter;
import com.yyyy.yyyy.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bean.StreetMessageBean;

public class Publish_Activity extends Activity {
	private TextView fs;
	private TextView pjz;
	private EditText content;
	private EditText tag;
	private EditText price;
	private TextView getAddress;
	private String adress = "";
	private LocationClient locationClient = null;
	private static final int UPDATE_TIME = 50000;
	public static Activity publish_Activity;
	private LinearLayout imglinearLayout;
	private ImageView addImg;
	private ArrayList<byte[]> imgBase64 = new ArrayList<byte[]>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);
		fs = (TextView) this.findViewById(R.id.fs);
		pjz = (TextView) this.findViewById(R.id.pjz);
		content = (EditText) this.findViewById(R.id.content);
		tag = (EditText) this.findViewById(R.id.tag);
		price = (EditText) this.findViewById(R.id.price);
		getAddress = (TextView) this.findViewById(R.id.myaddress);
		imglinearLayout = (LinearLayout)this.findViewById(R.id.img);
		addImg = (ImageView)this.findViewById(R.id.addimg);
		
		publish_Activity = this;

		
		//添加图片
		addImg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getImageFromAlbum();
			}
		});
		/**
		 * 定位
		 */
		getAddress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				locationClient = new LocationClient(Publish_Activity.this);
				// 设置定位条件
				LocationClientOption option = new LocationClientOption();
				option.setOpenGps(true); // 是否打开GPS
				option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
				option.setIsNeedAddress(true);
				option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
				option.setScanSpan(UPDATE_TIME); // 设置定时定位的时间间隔。单位毫秒
				locationClient.setLocOption(option);

				// 注册位置监听器
				locationClient.registerLocationListener(new BDLocationListener() {

					@Override
					public void onReceiveLocation(BDLocation location) {
						// TODO Auto-generated method stub
						if (location == null) {
							return;
						}
						System.out.println("地址为:" + location.getAddrStr() + "  街道：" + location.getStreet());
						adress = location.getAddrStr();
						getAddress.setText(adress);
					}

				});
				locationClient.start();
				locationClient.requestLocation();
				locationClient.stop();
			}
		});

		/**
		 * 发送事件
		 */
		fs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String conntentString = content.getText().toString();
				String tagString = tag.getText().toString();
				float priceNumber = Float.parseFloat(price.getText().toString());
				if (conntentString.length() > 0) {
					CreateMessageBean creater = new CreateMessageBean();// 封装信息
					StreetMessageBean messageBean = creater.create(conntentString, tagString, priceNumber,
							adress,imgBase64);
//					PublishConnecter connecter = new PublishConnecter();
//					connecter.send(messageBean);
					ProgressDialog pd = ProgressDialog.show(publish_Activity, "上传中", "正在上传,请稍候...");
					SendMessage sendMessage = new SendMessage();
					sendMessage.setMessageBean(messageBean,pd);
					new Thread(sendMessage).start();
					content.setText("");
				} else {
					Toast.makeText(Publish_Activity.this, "亲，写点内容再发吧~!", Toast.LENGTH_LONG).show();
				}
			}
		});

		pjz.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Publish_Activity.this, Index_Activity.class);
				startActivity(intent);
			}
		});
	}
	
	public class SendMessage implements Runnable{

		private StreetMessageBean messageBean;
		private ProgressDialog pd;
		public void setMessageBean(StreetMessageBean messageBean,ProgressDialog pd){
			this.messageBean = messageBean;
			this.pd = pd;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(messageBean != null){
				PublishConnecter connecter = new PublishConnecter();
				connecter.send(messageBean);
				pd.dismiss();
			}
		}
		
	}
	protected void getImageFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");// 相片类型
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			System.out.println("获取图片失败");
			return;
		}

		Bitmap bm = null;
		ContentResolver resolver = getContentResolver();
		if (requestCode == 1) {

			Uri originalUri = data.getData(); // 获得图片的uri

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
			System.out.println("URI：" + originalUri.toString());

			LayoutInflater inflater = LayoutInflater.from(Publish_Activity.this);
			LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.img_template, null);
			ImageView imgView = (ImageView)linearLayout.getChildAt(0);
			// 显得到bitmap图片
			imgView.setImageBitmap(bm);
			imglinearLayout.addView(linearLayout);
			ByteArrayOutputStream btos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, btos);
			byte[] byte1 = btos.toByteArray();
//			BASE64Encoder encoder = new BASE64Encoder();
//			String base64 = new String(Base64.encodeBase64(byte1));
//			byte1 = Base64.encodeBase64(byte1);
			imgBase64.add(byte1);
		}
	}
}
