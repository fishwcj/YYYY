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
			System.out.println("得到的钱的字符串是:" + mony);
			result[1] = mony;
		}else {
			System.out.println("未识别到消费钱数");
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
		String [] keyWords = {"一","二","三","四","五","六","七","八","九","十"};
		for(int i = 0; i < keyWords.length; i++){
			keyIndex = ((origin.indexOf(keyWords[i]) >= 0) && (origin.indexOf(keyWords[i]) < keyIndex)) ? origin.indexOf(keyWords[i]) : keyIndex;
		}
	}
	
	private String dealWithMony(String mony){
		System.out.println("接下来是:" + mony);
		mony = mony.replaceAll("一","1");
		mony = mony.replaceAll("二","2");
		mony = mony.replaceAll("三", "3");
		mony = mony.replaceAll("四","4");
		mony = mony.replaceAll("五", "5");
		mony = mony.replaceAll("六","6");
		mony = mony.replaceAll("七", "7");
		mony = mony.replaceAll("八","8");
		mony = mony.replaceAll("九", "9");
		
		mony = mony.replaceAll("壹","1");
		mony = mony.replaceAll("贰","2");
		mony = mony.replaceAll("叄", "3");
		mony = mony.replaceAll("肆","4");
		mony = mony.replaceAll("伍", "5");
		mony = mony.replaceAll("陆","6");
		mony = mony.replaceAll("柒", "7");
		mony = mony.replaceAll("扒","8");
		mony = mony.replaceAll("玖", "9");
		
		int[] index = new int[5];
		index[0] = mony.indexOf("千");
		if(index[0] < 0)
			index[0] = mony.indexOf("仟");
		index[1] = mony.indexOf("百");
		if(index[1] < 0)
			index[1] = mony.indexOf("佰");
		index[2] = mony.indexOf("十");
		if(index[3] < 0)
			index[3] = mony.indexOf("拾");
		index[3] = mony.indexOf("块");
		if(index[3] < 0)
			index[3] = mony.indexOf("元");
		index[4] = mony.indexOf("毛");
		if(index[4] < 0)
			index[4] = mony.indexOf("角");
		
		float imony = 0.0f;
		if(index[0] > 0)
			imony += Float.parseFloat(mony.charAt(index[0] - 1) + "") * 1000;//千
		if(index[1] > 0)
			imony += Float.parseFloat(mony.charAt(index[1] - 1) + "") * 100;//百
		if(index[2] > 0)
			imony += Float.parseFloat(mony.charAt(index[2] - 1) + "") * 10;//十
		if(index[3] > 0)
			imony += Float.parseFloat(mony.charAt(index[3] - 1) + "") * 1;//个
		if(index[4] < 0){
			if(index[3] > 0 && (index[3] != mony.length() - 1)){
			imony += Float.parseFloat(mony.charAt(index[3] + 1) + "") * 0.1;//小数
			}
		}else {
			imony += Float.parseFloat(mony.charAt(index[4] - 1) + "") * 0.1;//小数
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
		if(ahead.equals("千")){
			imony += Float.parseFloat(theLastOne) * 100;
		}else if(ahead.equals("百")){
			imony += Float.parseFloat(theLastOne) * 10;
		}else if(ahead.equals("十")){
			imony += Float.parseFloat(theLastOne) * 1;
		}else if(ahead.equals("零")){
			imony += Float.parseFloat(theLastOne) * 1;
		}
		return imony;
	}
}
