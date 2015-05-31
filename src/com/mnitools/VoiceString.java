package com.mnitools;

public class VoiceString {
	private int keyIndex = 100;
	
	public String[] dealWithOrigin_Result(String [] origin_results){
		String[] result = {"",""};
		contains(origin_results[0]);
		String kind = getKind(origin_results[0]);
		String originmonyString = getMony(origin_results[0]);
		String mony = dealWithMony(originmonyString);
		
		if(mony != null){
			System.out.println("�õ���Ǯ���ַ�����:" + mony);
			result[1] = mony;
		}else {
			System.out.println("δʶ������Ǯ��");
		}
		if(kind != null){
			result[0] = kind;
		}
		
		return result;
	}
	
	private String getKind(String originString){
		String kind = null;
		if(this.keyIndex < 100){
			kind = originString.substring(0, keyIndex);
		}
		return kind;
	}
	
	private String getMony(String originString){
		String mony = null;
		if(this.keyIndex < 100){
			mony = originString.substring(keyIndex, originString.length());
		}
		return mony;
	}
	
	private void contains(String origin){
		String [] keyWords = {"һ","��","��","��","��","��","��","��","��","ʮ"};
		for(int i = 0; i < keyWords.length; i++){
			keyIndex = ((origin.indexOf(keyWords[i]) >= 0) && (origin.indexOf(keyWords[i]) < keyIndex)) ? origin.indexOf(keyWords[i]) : keyIndex;
		}
	}
	
	private String dealWithMony(String mony){
		System.out.println("��������:" + mony);
		mony = mony.replaceAll("һ","1");
		mony = mony.replaceAll("��","2");
		mony = mony.replaceAll("��", "3");
		mony = mony.replaceAll("��","4");
		mony = mony.replaceAll("��", "5");
		mony = mony.replaceAll("��","6");
		mony = mony.replaceAll("��", "7");
		mony = mony.replaceAll("��","8");
		mony = mony.replaceAll("��", "9");
		
		mony = mony.replaceAll("Ҽ","1");
		mony = mony.replaceAll("��","2");
		mony = mony.replaceAll("��", "3");
		mony = mony.replaceAll("��","4");
		mony = mony.replaceAll("��", "5");
		mony = mony.replaceAll("½","6");
		mony = mony.replaceAll("��", "7");
		mony = mony.replaceAll("��","8");
		mony = mony.replaceAll("��", "9");
		
		int[] index = new int[5];
		index[0] = mony.indexOf("ǧ");
		if(index[0] < 0)
			index[0] = mony.indexOf("Ǫ");
		index[1] = mony.indexOf("��");
		if(index[1] < 0)
			index[1] = mony.indexOf("��");
		index[2] = mony.indexOf("ʮ");
		if(index[3] < 0)
			index[3] = mony.indexOf("ʰ");
		index[3] = mony.indexOf("��");
		if(index[3] < 0)
			index[3] = mony.indexOf("Ԫ");
		index[4] = mony.indexOf("ë");
		if(index[4] < 0)
			index[4] = mony.indexOf("��");
		
		float imony = 0.0f;
		if(index[0] > 0)
			imony += Float.parseFloat(mony.charAt(index[0] - 1) + "") * 1000;//ǧ
		if(index[1] > 0)
			imony += Float.parseFloat(mony.charAt(index[1] - 1) + "") * 100;//��
		if(index[2] > 0)
			imony += Float.parseFloat(mony.charAt(index[2] - 1) + "") * 10;//ʮ
		if(index[3] > 0)
			imony += Float.parseFloat(mony.charAt(index[3] - 1) + "") * 1;//��
		if(index[4] < 0){
			if(index[3] > 0 && (index[3] != mony.length() - 1)){
			imony += Float.parseFloat(mony.charAt(index[3] + 1) + "") * 0.1;//С��
			}
		}else {
			imony += Float.parseFloat(mony.charAt(index[4] - 1) + "") * 0.1;//С��
		}
		
		String lastone = mony.substring(mony.length() - 1);
		if(lastone.equals("1") || lastone.equals("2") || lastone.equals("3") || lastone.equals("4") || lastone.equals("5") || lastone.equals("6") || lastone.equals("7") || lastone.equals("8") || lastone.equals("9")){
			imony = addLast(imony, mony, lastone);
		}
		
		
		String monyString = ((Float)imony).toString();
		return monyString;
	}
	
	private float addLast(float imony, String monyString, String theLastOne){
		String ahead = monyString.charAt(monyString.indexOf(theLastOne) - 1) + "";
		if(ahead.equals("ǧ")){
			imony += Float.parseFloat(theLastOne) * 100;
		}else if(ahead.equals("��")){
			imony += Float.parseFloat(theLastOne) * 10;
		}else if(ahead.equals("ʮ")){
			imony += Float.parseFloat(theLastOne) * 1;
		}else if(ahead.equals("��")){
			imony += Float.parseFloat(theLastOne) * 1;
		}
		return imony;
	}
}
