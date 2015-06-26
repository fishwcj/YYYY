package com.mnitools;

import java.io.Serializable;

/**
 * 网络数据传输规范
 * @author wcj
 *
 */
public interface INetWork {
	abstract boolean connect(String urlString);//建立网络链接
	abstract <T extends Serializable>  boolean sendObject(T object);//要求发送的对象必须序列化
	abstract Object receiveObject();//接收返回数据
}
