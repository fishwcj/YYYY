package com.dao.basic;

public interface IBasicDAO {
	abstract boolean connectDataBase(String dbName);
	abstract void closeDB();
	abstract Object selectCursor(String sql);//不能直接返回具体类型，降低耦合
	abstract float selectFloat(String sql);
	abstract int selectInt(String sql);
	abstract String selectString(String sql);
	abstract boolean insert(String sql);
	abstract boolean update(String sql);
	abstract boolean drop(String sql);
	abstract boolean isConnect();
}
