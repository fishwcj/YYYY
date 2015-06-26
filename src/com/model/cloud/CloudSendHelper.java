package com.model.cloud;

import java.net.MalformedURLException;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.activity.JZ_Activity;
import com.bean.CloudData;
import com.dao.SySearch_DAO;
import com.mnitools.INetWork;
import com.mnitools.NetWorkCommunicate;
import com.model.user.IsLogin;

/**
 * �ƶ�ͬ��������ͬ������
 */
public class CloudSendHelper {

	private CloudData cloudData = new CloudData();
	private SySearch_DAO search;
	private Cursor cursor;
	private INetWork communicater;

	@SuppressLint("SimpleDateFormat")
	public CloudSendHelper() {
		this.search = new SySearch_DAO();
		this.communicater = new NetWorkCommunicate();
	}

	/**
	 * �����������ӷ�ʽ��Ĭ��httpConnection���ӣ�
	 * 
	 * @param netWorkWay
	 *            ʵ����INetWork�����ӷ�ʽ
	 */
	public <T extends INetWork> void setNetWorkWay(T netWorkWay) {
		this.communicater = netWorkWay;
	}

	/**
	 * ����Ƿ��½����½��ͬ�����������ѵ�½
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public boolean checkAndSend() throws MalformedURLException,
			ClassNotFoundException {
		boolean ok = false;
		if (IsLogin.isLogin()) {
			ok = send();
		}
		return ok;
	}

	/**
	 * ���ӷ�����������cloudData����
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	@SuppressLint("ShowToast")
	public boolean send() throws MalformedURLException, ClassNotFoundException {
		boolean tag = false;
		new Thread() {
			public void run() {
				setcloudData();// ��װ����
				String url = "http://192.168.191.1:8080/Bill/servlet/ReceiveServlet";
				if (communicater.connect(url))// �������ӳɹ�
					if (communicater.sendObject(cloudData)) {// �������ݳɹ�
						Object responseObject = communicater.receiveObject();// ��ȡ���ض���
						System.out.println("���ض���" + responseObject.toString());

						Looper.prepare();
						new Handler(Looper.getMainLooper());
						Toast.makeText(JZ_Activity.jzActivity, "ͬ���ɹ�",
								Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
			};
		}.start();
		tag = true;
		return tag;
	}

	/**
	 * �����ݶ����װ
	 * 
	 * @param cloudData
	 */
	public void setcloudData() {

		// ��װ�û�id
		cloudData.setUserNameID(search.searchId());

		// ��װ��ˮ
		cursor = search.searchStreamCount();
		String stream = "";
		String kind = "";
		String consume = "";
		String inorout = "";
		String date = "";
		while (cursor.moveToNext()) {
			consume = cursor.getString(cursor.getColumnIndex("consume"));
			date = cursor.getString(cursor.getColumnIndex("date"));
			inorout = cursor.getString(cursor.getColumnIndex("inorout"));
			kind = cursor.getString(cursor.getColumnIndex("kind"));
			stream = consume + "&" + kind + "&" + date + "&" + inorout;
			cloudData.addStreamCountList(stream);
		}

		// ��װÿ�·���Ԥ��
		// id&budget&kind&remain&month
		cursor = search.searchBudgetByKind();
		while (cursor.moveToNext()) {
			cloudData.addBudgetByKindList(cursor.getString(cursor
					.getColumnIndex("id"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("budget"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("kind"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("remain"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("month")));
		}

		// ��װ��Ԥ��
		// totalbudget&remain&month
		cursor = search.searchBudget();
		if (cursor.moveToNext()) {
			cloudData.addTotalbudget(cursor.getString(cursor.getColumnIndex("totalbudget"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("remain"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("month")));
		} else {
			System.out.println("��ȡ��Ԥ��ʱδ�����κ�����");
		}

		// ��װ����
		// mony&month
		cursor = search.searchincome();
		if (cursor.moveToNext()) {
			cloudData.addConsumein(cursor.getString(cursor.getColumnIndex("mony"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("month")));
		} else {
			System.out.println("δ��ȡ������");
		}

		// ��װʱ��
		cursor = search.serchTime();
		if (cursor.moveToNext()) {
			cloudData.setTime(cursor.getString(cursor.getColumnIndex("lastdate"))
					+ "&"
					+ cursor.getString(cursor.getColumnIndex("sytime")));
		}

		// ��װ�������
		//id&firstid&secondid&kindname
		cursor = search.searchKind();
		while (cursor.moveToNext()) {
			cloudData.addKind(cursor.getString(cursor.getColumnIndex("id"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("firstid"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("secondid"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("kindname")));
		}
		
		//��װĿ��
		//id&name&time&lefttime&conten&tips&advise
		cursor = search.searchTarget();
		while(cursor.moveToNext()){
			cloudData.addTarget(cursor.getString(cursor.getColumnIndex("id"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("name"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("time"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("lefttime"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("content"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("tips"))
							+ "&"
							+ cursor.getString(cursor.getColumnIndex("advise")));
		}
		// ����ͬ��ʱ��
		search.updateTime();
		System.out.println("�������׼����ϣ�");
	}
}
