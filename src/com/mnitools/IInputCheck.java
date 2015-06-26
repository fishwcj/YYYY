package com.mnitools;

import android.view.View;

public interface IInputCheck {
	abstract void setLisener_number(View button,String number);//键入0~9是检测
	abstract void setLisener_float(View button,String point);//键入.时检测
	abstract void setLisener_clear(View button);//键入清空时检测
	abstract boolean check(String consumeString);//检测是否合法的输入
	abstract void setViewString(String string);//设置字符串
}
