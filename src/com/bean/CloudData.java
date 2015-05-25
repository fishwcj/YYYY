package com.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CloudData implements Serializable {
	/**
	 * ���л���
	 */
	private static final long serialVersionUID = 1L;
	private String userName;	//�û���
	private String userID;		//�û�id
//	private float budgetRemain;	//����Ԥ�����
//	private float budget;		//����Ԥ��
//	private float income;	//����
//	private String date;	//ͬ������
	private ArrayList<String> budgetByKindList = new ArrayList<String>();	//���·���Ԥ��
	private ArrayList<String> streamCountList = new ArrayList<String>();	//������ˮ��Ϣ
	private ArrayList<String> kind = new ArrayList<String>();				//�������
	private ArrayList<String> totalbudget = new ArrayList<String>();		//������Ԥ��
	private String time;//ʱ���
	private ArrayList<String> consumein = new ArrayList<String>();//�����
	private ArrayList<String> target = new ArrayList<String>();//��Ǯ��
	
	
	public ArrayList<String> getKind() {
		return kind;
	}


	public void addKind(String kind) {
		this.kind.add(kind);
	}


	public ArrayList<String> getTotalbudget() {
		return totalbudget;
	}


	public void addTotalbudget(String totalbudget) {
		this.totalbudget.add(totalbudget);
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public ArrayList<String> getConsumein() {
		return consumein;
	}


	public void addConsumein(String consumein) {
		this.consumein.add(consumein);
	}


	public ArrayList<String> getTarget() {
		return target;
	}


	public void addTarget(String target) {
		this.target.add(target);
	}


	/**
	 * �õ��û���
	 * @return
	 */
	public String getUserName(){
		return userName;
	}
	
	
	/**
	 * �����û���
	 * @return
	 */
	public void setUserName(String username){
		this.userName = username;
	}
	
	
	/**
	 * �õ��û�id
	 * @return
	 */
	public String getUserID(){
		return userID;
	}
	
	
	/**
	 * �����û�id
	 * @return
	 */
	public void setUserNameID(String userid){
		this.userID = userid;
	}
	
	/**
	 * ���һ������Ԥ����Ϣ 
	 * @param item ��ϸ�ʽ:  	���&Ԥ��&Ԥ�����
	 */
	public void addBudgetByKindList(String item){
		budgetByKindList.add(item);
	}
	
	/**
	 * �õ��������з���Ԥ����Ϣ
	 */
	public ArrayList<String> getBudgetByKindList(){
		return budgetByKindList;
	}
	
	/**
	 * ���һ����ˮ��Ϣ
	 * @param item ��ϸ�ʽ�� 	���ѽ��&�������&����ʱ��&�����֧��
	 */
	public void addStreamCountList(String item) {
		streamCountList.add(item);
	}
	
	/**
	 * �õ���ˮlist
	 * @return
	 */
	public ArrayList<String> getStreamCountList(){
		return streamCountList;
	}
}
