package com.inteface;

import android.view.View;

public interface IInputCheck {
	abstract void setLisener_number(View button,String number);//����0~9�Ǽ��
	abstract void setLisener_float(View button,String point);//����.ʱ���
	abstract void setLisener_clear(View button);//�������ʱ���
	abstract boolean check(String consumeString);//����Ƿ�Ϸ�������
	abstract void setViewString(String string);//�����ַ���
}
