package com.model.user;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.activity.Index_Activity;
import com.dao.basic.SQLString;

import android.annotation.SuppressLint;

public class Init {

	private Date currentDate = new Date();
	private String currentString = "";
	private Date oldDate = new Date();
	private String oldDateString = "";
	private String sql = "";
	@SuppressLint("SimpleDateFormat")
	public Init() throws ParseException{
		sql = SQLString.getLastdate_In();
		oldDateString = Index_Activity.basicDAO.selectString(sql);
		System.out.println("�ϴε�¼��:" + oldDateString);
		
		// �õ���һ�������� xxxx-xx ���ʾ
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		System.out.println("��һ����"+oldDateString);
		this.oldDate = format.parse(this.oldDateString);

		// �õ����������� xxxx-xx ���ʾ
		currentString = format.format(currentDate);
		System.out.println("��һ����"+currentString);
		currentDate = format.parse(currentString);

		// �Ƚ� ���������һ�������Ԥ���ȵ�
		if (oldDate.before(currentDate)) {
			System.out.println("�����µ�һ�£���ʼ��Ԥ��...");
			initNew();
			sql = SQLString.getUpdateLastdate_In(currentString);
			Index_Activity.basicDAO.update(sql);
			
			//���ɼӹ��ܡ������û����ñ���Ԥ������·����Ѿ���¯��
			System.out.println("��ʼ��Ԥ��ɹ���");
		}else{
			System.out.println("û�н����µ�һ�£����ó�ʼ��");
		}
	}

	public void initNew() {
		initNewTotalBudget();
		initNewKindBudget();
		initNewincome();
		initNewCount();
	}
	
	/**
	 * ��ʼ��ÿ����Ԥ��
	 */
	private void initNewTotalBudget() {
		sql = SQLString.getInitNewTotalBudget_In(currentString);
		Index_Activity.basicDAO.insert(sql);
	}
	
	/**
	 * ��ʼ�������
	 */
	private void initNewincome(){
		sql = SQLString.getInitNewincome_In(currentString);
		Index_Activity.basicDAO.insert(sql);
	}
	
	/**
	 * ��ʼ��ÿ�·���Ԥ���
	 */
	private void initNewKindBudget() {

		for(int kind = 1; kind < 5; kind++){
			sql = SQLString.getInitNewKindBudget_In(kind, currentString);
			Index_Activity.basicDAO.insert(sql);
		}
	}
	
	/**
	 * ��ʼ��ÿ������ͳ��
	 */
	private void initNewCount() {

	}
}
