package com.activity;

/**
 * ����Ϊ����ѡ��ʱ������������ʾ����
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logic.ConsumKindManager;
import com.yyyy.yyyy.R;

public class SelectPicPopupWindow extends Activity implements OnClickListener {

	private TextView spjs;
	private TextView yfsp;
	private TextView cxjt;
	private TextView jjxf;
	private TextView xxjx;
	private TextView xxyl;
	private TextView rjjw;
	private TextView ylbj;
	private TextView qtzx;
	private TextView zdy;
	
	private TextView kind;
	private LinearLayout layout;
	public static Activity selectPicPopupWindow;
	private ConsumKindManager consumKindManager;

	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.alert);  
        selectPicPopupWindow = this;
        spjs = (TextView)this.findViewById(R.id.spjs);
        yfsp = (TextView)this.findViewById(R.id.yfsp);
        cxjt = (TextView)this.findViewById(R.id.cxjt);
        jjxf = (TextView)this.findViewById(R.id.jjxf);
        xxjx = (TextView)this.findViewById(R.id.xxjx);
        xxyl = (TextView)this.findViewById(R.id.xxyl);
        rjjw = (TextView)this.findViewById(R.id.rjjw);
        ylbj = (TextView)this.findViewById(R.id.ylbj);
        qtzx = (TextView)this.findViewById(R.id.qtzx);
        zdy = (TextView)this.findViewById(R.id.zdy);
        
        layout = (LinearLayout)this.findViewById(R.id.two);
        kind = (TextView)JZ_Activity.jzActivity.findViewById(R.id.kind);
        consumKindManager = new ConsumKindManager();
        
        /**
         * ʳƷ��ˮ ��ť�¼�
         */
        spjs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("ʳƷ��ˮ  > ");
				JZ_Activity.consumekind = 1;
				consumKindManager.freshButton(1, layout);
			}
		});
        
        /**
         * �·���Ʒ ��ť�¼�
         */
        yfsp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("�·���Ʒ  > ");
				JZ_Activity.consumekind = 2;
				consumKindManager.freshButton(2, layout);
			}
		});
        
        /**
         * ���н�ͨ ��ť�¼�
         */
        cxjt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("���н�ͨ  > ");
				JZ_Activity.consumekind = 3;
				consumKindManager.freshButton(3, layout);
			}
		});
        
        /**
         * �Ҿ����� ��ť�¼�
         */
        jjxf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("�Ҿ�����  > ");
				JZ_Activity.consumekind = 4;
				consumKindManager.freshButton(4, layout);
			}
		});
        
        /**
         * ѧϰ���� ��ť�¼�
         */
        xxjx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("ѧϰ����  > ");
				JZ_Activity.consumekind = 5;
				consumKindManager.freshButton(5, layout);
			}
		});
        
        /**
         * �������� ��ť�¼�
         */
        xxyl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("��������  > ");
				JZ_Activity.consumekind = 6;
				consumKindManager.freshButton(6, layout);
			}
		});
        
        /**
         * �˼ʽ��� ��ť�¼�
         */
        rjjw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("�˼ʽ���  > ");
				JZ_Activity.consumekind = 7;
				consumKindManager.freshButton(7, layout);
			}
		});
        
        /**
         * ҽ�Ʊ��� ��ť�¼�
         */
        ylbj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("ҽ�Ʊ���  > ");
				JZ_Activity.consumekind = 8;
				consumKindManager.freshButton(8, layout);
			}
		});
        
        /**
         * �������� ��ť�¼�
         */
        qtzx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				kind.setText("��������  > ");
				JZ_Activity.consumekind = 9;
				consumKindManager.freshButton(9, layout);
			}
		});
        
        /**
         * �Զ�������
         */
        zdy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectPicPopupWindow.this,AddKind_Activity.class);
				startActivity(intent);
			}
		});
    }	// ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void onClick(View v) {
		
	}

}
