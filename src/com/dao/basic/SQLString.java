package com.dao.basic;

public class SQLString {

	/*
	 * JZ_DAO
	 */
	public static String getUpdateBudgetRemain_JZ(String currentString) {
		String sql = "select remain,totalbudget from tabletotalbudget where month = '" + currentString + "'";
		return sql;
	}

	public static String getInsertStream(float consum1, String kind, String date, int inOrOut, int consumekind) {
		String sql = "insert into stream values(" + consum1 + ", '" + kind + "'," + consumekind + ",'" + date
				+ "'," + inOrOut + ")";
		return sql;
	}

	/*
	 * LS_DAO
	 */
	public static String getSelectAllAccount_LS(String dateString) {
		String sql = "select consume, kind, date, inorout from stream where date >= '" + dateString
				+ "' and date < date('" + dateString + "', '+1 month') order by date desc";
		return sql;
	}

	/*
	 * SySearch_DAO
	 */
	public static String getSearchId_Sy() {
		String sql = "select id from user";
		return sql;
	}

	public static String getSearchStreamCount_Sy(String time) {
		String sql = "select * from stream where date > '" + time + "'";
		return sql;
	}

	public static String getSearchBudget_Sy() {
		String sql = "select * from tabletotalbudget";
		return sql;
	}

	public static String getSearchincome_Sy() {
		String sql = "select * from consumein";
		return sql;
	}

	public static String getSearchBudgetByKind_Sy() {
		String sql = "select * from tablebudget";
		return sql;
	}

	public static String getSearchTime_Sy() {
		String sql = "select * from time";
		return sql;
	}

	public static String getSearchKind_Sy() {
		String sql = "select * from kind";
		return sql;
	}

	public static String getSytime_Sy() {
		String sql = "select sytime from time";
		return sql;
	}

	public static String getUpdateTime_Sy(String currenString) {
		String sql = "update time set sytime = '" + currenString + "'";
		return sql;
	}

	public static String getReadUpdateTime_Sy(){
		String sql = "select sytime from time";
		return sql;
	}
	/*
	 * TJ_DAO
	 */
	// 获取总的已经花的钱
	public static String getTotalConsumeString(String date) {

		String sql = "select totalbudget-remain as total_consume from tabletotalbudget where month ='" + date
				+ "'";
		return sql;
	}

	// 获取每一类别的总的花过的钱数
	public static String getTypeConsumeString(String date) {

		String sql = "select budget-remain as sum_consume from tablebudget where month = '" + date + "'";
		return sql;
	}

	// 获取每一天的某一类别的已花钱数，作为折线图的纵坐标
	public static String getDayTypeConsumeString(int type, String date) {

		String sql = "select sum(consume)  as totoalConsume from stream where id = " + type
				+ " and date >= '" + date + "-01" + "' and date <= date('" + date
				+ "-01','+1 month') group by strftime('%d',date)";

		return sql;
	}

	// 获取花钱的日期，作为折线图的横坐标
	public static String getDayString(int type, String date) {

		String sql = "select distinct strftime('%d',date)  as day from stream where id = " + type
				+ " and date >= '" + date + "-01" + "' and date <= date('" + date + "-01','+1 month')";

		return sql;
	}

	/*
	 * YS_DAO
	 */
	public static String getBudget_Ys(String currentString) {
		String sql = "select budget from tablebudget where month = '" + currentString + "'";
		return sql;
	}

	public static String getTotalbudget_Ys(String currentString) {
		String sql = "select totalbudget from tabletotalbudget where month = '" + currentString + "'";
		return sql;
	}

	public static String getRemain(String currentString) {
		String sql = "select remain from tabletotalbudget where month = '" + currentString + "'";
		return sql;
	}

	public static String getAddBudget_Ys(float budget, int kind, String currentString) {
		String sql = "update tablebudget set budget = " + budget + ", remain = remain - budget + " + budget
				+ " where kind = " + kind + " and month = '" + currentString + "'";
		return sql;
	}

	public static String getAddtotal_Ys(float totalbudget, String currentString) {
		String sql = "update tabletotalbudget set remain = remain - totalbudget + " + totalbudget
				+ " where month = '" + currentString + "'";
		return sql;
	}

	public static String getAddtotal1_Ys(float totalbudget, String currentString) {
		String sql = "update tabletotalbudget set totalbudget = " + totalbudget + " where month = '"
				+ currentString + "'";
		return sql;
	}

	public static String getDeltotal_Ys(float consume, String currentString) {
		String sql = "update tabletotalbudget set remain = remain -" + consume + " where month = '"
				+ currentString + "'";
		return sql;
	}

	public static String getUpdate_Ys(float consume, int kind, String currentString) {
		String sql = "update tablebudget set remain = remain -" + consume + " where kind = " + kind
				+ " and month = '" + currentString + "'";
		return sql;
	}

	public static String getUpdatein_Ys(float in, String currentString) {
		String sql = "update consumein set mony = mony + " + in + " where month = '" + currentString + "'";
		return sql;
	}

	/*
	 * Init
	 */
	public static String getLastdate_In() {
		String sql = "select lastdate from time";
		return sql;
	}

	public static String getUpdateLastdate_In(String currentString) {
		String sql = "update time set lastdate = " + currentString;
		return sql;
	}

	public static String getInitNewTotalBudget_In(String currentString) {
		String sql = "insert into tabletotalbudget(totalbudget,remain, month) values (0,0,'" + currentString
				+ "')";
		return sql;
	}

	public static String getInitNewincome_In(String currentString) {
		String sql = "insert into consumein(mony, month) values (0, '" + currentString + "')";
		return sql;
	}

	public static String getInitNewKindBudget_In(int kind, String currentString) {
		String sql = "insert into tablebudget(budget, kind, remain, month) values(0, " + kind + ", 0,'"
				+ currentString + "')";
		return sql;
	}

	/*
	 * IsLogin
	 */
	public static String getIsLogin_Is() {
		String sql = "select tag from user";
		return sql;
	}

	public static String getHasTarget_Is() {
		String sql = "select * from target";
		return sql;
	}

	public static String getInitTarget_Is(String name, int time, String content) {// 第一次设置攒钱目标初始化
		String sql = "insert into target(name,time,lefttime,content,tips,advise) values('" + name + "',"
				+ time + ",-1,'" + content + "','刚刚设置目标，消费数据智能分析中，一周后来看结果吧~','坚持就是胜利，要想获得精准结果，要每天坚持记录哦！')";
		return sql;
	}

	public static String getTarget_Is() {
		String sql = "select * from target";
		return sql;
	}

	public static String getUpdateTargetName_Is(String name) {
		String sql = "update target set name = '" + name + "'";
		return sql;
	}

	public static String getUpdateTargetContent_Is(String content) {
		String sql = "update target set content = '" + content + "'";
		return sql;
	}

	public static String getUpdateTargetLeftTime_Is(int lefttime) {
		String sql = "update target set lefttime = " + lefttime;
		return sql;
	}

	public static String getUpdateTargetAdvise_Is(String advise) {
		String sql = "update target set advise = '" + advise + "'";
		return sql;
	}

	public static String getUpdateTargetTips_Is(String tips) {
		String sql = "update target set tips = '" + tips + "'";
		return sql;
	}

	public static String getUpdateTarget_Is(String name, String content, int time) {
		String sql = "update target set name = '" + name + "',time = " + time + ",content = '" + content
				+ "'";
		return sql;
	}

	/*
	 * Regist
	 */
	public static String getUpdateStateId_Re(String idAndPassword) {
		String sql = "update user set id = " + idAndPassword;
		return sql;
	}

	public static String getUpdateStateTag_Re() {
		String sql = "update user set tag = 1";
		return sql;
	}

	/*
	 * User_DAO
	 */
	public static String getChangUseName(String name){
		String sql = "update user set name = '" + name + "'";
		return sql;
	}
	
	public static String getUserId_Us() {
		String sql = "select id from user";
		return sql;
	}

	public static String getUserName_Us() {
		String sql = "select name from user";
		return sql;
	}

	public static String getALLMessages_Us() {
		String sql = "select * from user";
		return sql;
	}

	public static String getFreshButton_Co(int mainkind) {
		String sql = "select kindname from kind where firstid = " + mainkind;
		return sql;
	}

	public static String getInitStream(float comsume, String kind, int id, String date, int inorout) {
		String sql = "insert into stream values(" + comsume + ",'" + kind + "'," + id + ",'" + date + "',"
				+ inorout + ")";
		return sql;
	}

	public static String getInitTablebudget(float budget, int kind, float remain, String month) {
		String sql = "insert into tablebudget(budget, kind, remain, month) values(" + budget + "," + kind + "," + remain + ",'" + month
				+ "')";
		return sql;
	}

	public static String getInitTabletotalbudget(float totalbudget, float remain, String month) {
		String sql = "replace into tabletotalbudget values(" + totalbudget + "," + remain + ",'" + month
				+ "')";
		return sql;
	}

	public static String getInitConsumein(float mony, String month) {
		String sql = "replace into consumein values(" + mony + ",'" + month + "')";
		return sql;
	}

	public static String getInitTime(String lastdate, String sytime) {
		String sql = "insert into time values('" + lastdate + "','" + sytime + "')";
		return sql;
	}

	public static String getInitKind(int id, int firstid, int secondid, String kindname) {
		String sql = "replace into kind(id,firstid,secondid,kindname) values(" + id + "," + firstid + "," + secondid + ",'" + kindname + "')";
		return sql;
	}

	public static String getInitTarget(String name, int time, int lefttime, String content, String tips,
			String advise) {
		String sql = "replace into target(name,time,lefttime,content,tips,advise) values ('" + name + "'," + time + "," + lefttime + ",'" + content
				+ "','" + tips + "','" + advise + "')";
		return sql;
	}

	public static String getInitUser(String id, String name) {
		String sql = "insert into user values ('" + id + "','" + name + "',1)";
		return sql;
	}

	public static String getInit() {
		String sql = "delete from tablebudget;delete from tabletotalbudget;delete from consumein;delete from time;delete from user;delete from kind;";
		return sql;
	}

	/*
	 * Kind_Dao
	 */
	public static String getFirstKindName_Ki(int firstid) {
		String sql = "select name from firstkind where id = " + firstid;
		return sql;
	}

	public static String getMaxId(int firstid) {
		String sql = "select max(secondid) from kind where firstid = " + firstid;
		return sql;
	}

	public static String getInsertNewKind(int firstid, int secondid, String name) {
		String sql = "insert into kind(firstid,secondid,kindname) values (" + firstid + "," + secondid + ",'"
				+ name + "')";
		return sql;
	}

	public static String getChilds(int firstid) {
		String sql = "select * from kind where firstid = " + firstid;
		return sql;
	}

	public static String getDelete(int firstid, int secondid) {
		String sql = "delete from kind where firstid = " + firstid + " and secondid = " + secondid;
		return sql;
	}

	/**
	 * Borrow_Return Manager
	 * @author LLL
	 * @return
	 */
	
	public static String getTotal(int kind){
		String sql = "select sum(money) as totalborrow from borrow_manager where kind = "+kind; 
		return sql;
	}
	public static String  getList(int kind) {
		String sql = "select id,name,money,return_time from borrow_manager where kind = "+kind;
		return sql;
	}
	public static String InsertBorrowItem(int id,String name,int kind,float money,String borrow_date,String return_date,String describe,String location){
		String sql = "insert into borrow_manager(id,name,kind,money,borrow_time,return_time,remark,location) values ("+id+",'"+name+"',"+kind+","+money+",'"+borrow_date+"','"+return_date+"','"+describe+"','"+location+"')";
		return sql;
	}
	public static String  getAllMsgById(int id) {
		String sql = "select name,kind,money,return_time,remark,location from borrow_manager where id= '"+id+"'";
		return sql;
	}
	public static String getItemCount(){
		String sql = "select count(*) as count from borrow_manager";
		return sql;
	}

}
