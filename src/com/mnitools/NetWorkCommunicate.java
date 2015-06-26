package com.mnitools;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.widget.Toast;

import com.activity.JZ_Activity;

/**
 * 用于通过HTTP协议进行序列化对象传输
 * 
 * @author wcj
 *
 */
public class NetWorkCommunicate implements INetWork {
	private HttpURLConnection con = null;// 链接对象

	/**
	 * 建立链接
	 * @throws IOException 
	 */
	@Override
	public boolean connect(String urlString){
		// TODO Auto-generated method stub
		boolean tag = false;
		URL url = null;
		try {
			url = new URL(urlString);
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-java-serialized-object");
			con.setRequestMethod("POST");
			con.setConnectTimeout(3000);
			System.out.println("尝试连接");
			con.connect();//链接
			tag = true;// 链接成功
		}catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("创建url失败");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("链接服务器失败");
					
			Looper.prepare();
			Handler handler = new Handler(Looper.getMainLooper());
			Message msg = Message.obtain();
			Toast.makeText(JZ_Activity.jzActivity, "咦？网络开小差了~",
					Toast.LENGTH_SHORT).show();
			msg.obj = null;
			handler.sendMessage(msg);
//			Looper.loop();
			con.disconnect();
			
		}
		return tag;
	}

	/**
	 * 发送对象
	 */
	@Override
	public <T extends Serializable> boolean sendObject(T object) {
		// TODO Auto-generated method stub
		boolean tag = false;
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(con.getOutputStream());

			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			int rsponseCode = con.getResponseCode();
			System.out.println("返回码:" + rsponseCode);
			if (rsponseCode == 200) {// 发送成功
				objectOutputStream.close();
				tag = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("建立对象输出流异常");
		}
		return tag;
	}

	/**
	 * 接收返回对象
	 */
	@Override
	public Object receiveObject() {
		// TODO Auto-generated method stub
		InputStream ist = null;
		Object obj = null;
		ObjectInputStream objectInputStream = null;
		try {
			ist = con.getInputStream();
			objectInputStream = new ObjectInputStream(ist);
			obj = objectInputStream.readObject();
			System.out.println(obj);
			objectInputStream.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("找不到类");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("建立流时出现异常");
		} finally {
			con.disconnect();// 断开网络链接
		}
		return obj;
	}

}
