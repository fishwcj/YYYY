package com.model.street;

import java.util.ArrayList;

import com.activity.Comment_Activity;
import com.activity.Street_Activity;
import com.bean.CommentBean;
import com.bean.StreetMessageBean;
import com.yyyy.yyyy.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class StreetGUI {
	private LinearLayout linearLayout;// ���಼��

	public StreetGUI(LinearLayout linearLayout) {
		this.linearLayout = linearLayout;
		linearLayout.removeAllViews();
	}

	/**
	 * ������ϢչʾGUI
	 * 
	 * @param message
	 *            һ����Ϣ����
	 */
	@SuppressLint("InflateParams")
	public void createGUI(StreetMessageBean message,int location) {
			LayoutInflater inflater = LayoutInflater
					.from(Street_Activity.street_Activity);
			View view = inflater.inflate(R.layout.message_templet, null);
			((TextView)view.findViewById(R.id.name)).setText(message.getUserName());//�����ǳ�
			((TextView)view.findViewById(R.id.message)).setText(message.getMessage());//������Ϣ��
			((TextView)view.findViewById(R.id.address)).setText(message.getAddress());//���õ�ַ
			LinearLayout imglinearLayout = (LinearLayout)view.findViewById(R.id.img);
			ArrayList<byte[]> imgbyte;
			imgbyte = message.getImglist();
			int commentsNumber = message.getComments();
			String commentsString = "����(" + commentsNumber + ")";
			TextView commentsTextView = (TextView)view.findViewById(R.id.comments);
			commentsTextView.setText(commentsString);//��������
			commentsTextView.setId(location);
			commentsTextView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Street_Activity.street_Activity,Comment_Activity.class);
					intent.putExtra("location", v.getId());
					Street_Activity.street_Activity.startActivity(intent);
				}
			});
			
			//��ʾͼƬ
			if(imgbyte != null && imgbyte.size() != 0){
				for(int i = 0; i < imgbyte.size(); i++){
					byte[]byte1 = imgbyte.get(i);
					Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
					LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.img_template, null);
					ImageView imgView = (ImageView)linearLayout.getChildAt(0);
					imgView.setImageBitmap(bitmap);
					imglinearLayout.addView(linearLayout);
				}
			}
			linearLayout.addView(view);
	}
	
	/**
	 * ��������ʧ��
	 */
	public void createErrorGUI(){
		LayoutInflater inflater = LayoutInflater.from(Street_Activity.street_Activity);
		View view  = inflater.inflate(R.layout.connect_error, null);
		linearLayout.addView(view);
		((Button)view.findViewById(R.id.tryagin)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Street_Activity.street_Activity,Street_Activity.class);
				Street_Activity.street_Activity.startActivity(intent);
			}
		});
	}
	
	/**
	 * ������
	 * @param commentBean
	 */
	public void createCommentGUI(CommentBean commentBean){
		LayoutInflater inflater = LayoutInflater
				.from(Comment_Activity.comment_Activity);
		View view = inflater.inflate(R.layout.comment_templet, null);
		((TextView)view.findViewById(R.id.name_c)).setText(commentBean.getUserName());
		((TextView)view.findViewById(R.id.content_c)).setText(commentBean.getContent());
		((TextView)view.findViewById(R.id.time_c)).setText(commentBean.getTime());
		((TextView)view.findViewById(R.id.repet)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		linearLayout.addView(view);
	}
}
