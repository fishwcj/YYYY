package com.model.count;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.activity.Count_Activity;
import com.dao.TJ_DAO;
import com.yyyy.yyyy.R;

public class LineChart implements Chart {
	ArrayList<Float> clothes_consume;// 该月衣服的那天的消费金额
	ArrayList<String> clothes_day;// 该月买衣服的日期
	ArrayList<Float> eat_consume;
	ArrayList<String> eat_day;
	ArrayList<Float> house_consume;
	ArrayList<String> house_day;
	ArrayList<Float> walk_consume;
	ArrayList<String> walk_day;

	ArrayList<Float> house1_consume;// 家居
	ArrayList<String> house1_day;

	ArrayList<Float> learn_consume;// 学习
	ArrayList<String> learn_day;

	ArrayList<Float> play_consume;// 娱乐
	ArrayList<String> play_day;

	ArrayList<Float> friend_consume;// 交往
	ArrayList<String> friend_day;

	ArrayList<Float> doctor_consume;// 就医
	ArrayList<String> doctor_day;
	String date;
	TJ_DAO tj_DAO;
	Activity context = Count_Activity.countActivity;

	public LineChart(String date) {
		this.date = date;
	}

	/**
	 * @author LLL 获取数据库中在衣食住行这四个方面消费的日期以及当天的消费金额
	 */
	private void getDate() {
		tj_DAO = new TJ_DAO();
		clothes_consume = tj_DAO.getConsume(1, date);
		clothes_day = tj_DAO.getDay(1, date);
		eat_consume = tj_DAO.getConsume(2, date);
		eat_day = tj_DAO.getDay(2, date);
		house_consume = tj_DAO.getConsume(3, date);
		house_day = tj_DAO.getDay(3, date);
		walk_consume = tj_DAO.getConsume(4, date);
		walk_day = tj_DAO.getDay(4, date);

		house1_consume = tj_DAO.getConsume(5, date);
		house1_day = tj_DAO.getDay(5, date);

		learn_consume = tj_DAO.getConsume(6, date);
		learn_day = tj_DAO.getDay(6, date);

		play_consume = tj_DAO.getConsume(7, date);
		play_day = tj_DAO.getDay(7, date);

		friend_consume = tj_DAO.getConsume(8, date);
		friend_day = tj_DAO.getDay(8, date);

		doctor_consume = tj_DAO.getConsume(9, date);
		doctor_day = tj_DAO.getDay(9, date);
	}

	/**
	 * 
	 * @return XYMultipleSeriesDataset dataset 画折线的数据
	 */
	private XYMultipleSeriesDataset getDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		getDate();
		if (clothes_consume.size() == 0 && eat_consume.size() == 0
				&& house_consume.size() == 0 && walk_consume.size() == 0
				&& house1_consume.size() == 0 && learn_consume.size() == 0
				&& play_consume.size() == 0 && friend_consume.size() == 0
				&& doctor_consume.size() == 0) {
			dataset = null;
		} else {

			XYSeries series1, series2, series3, series4, series5, series6, series7, series8, series9;
			// 获取衣的所有数据
			if (clothes_consume.size() != 0) {
				series1 = new XYSeries("食");
				for (int i = 0; i < clothes_consume.size(); i++) {
					float x, y;
					x = clothes_consume.get(i);
					y = Float.parseFloat(clothes_day.get(i));
					series1.add(y, x);
				}
				dataset.addSeries(series1);
			}
			// 获取食的所有数据
			if (eat_consume.size() != 0) {
				series2 = new XYSeries("衣");
				for (int i = 0; i < eat_consume.size(); i++) {
					float x, y;
					x = eat_consume.get(i);
					y = Float.parseFloat(eat_day.get(i));
					series2.add(y, x);
				}
				dataset.addSeries(series2);
			}
			// 获取住的所有数据
			if (house_consume.size() != 0) {
				series3 = new XYSeries("行");
				for (int i = 0; i < house_consume.size(); i++) {
					float x, y;
					x = house_consume.get(i);
					y = Float.parseFloat(house_day.get(i));
					series3.add(y, x);
				}
				dataset.addSeries(series3);
			}

			// 获取行的所有数据
			if (walk_consume.size() != 0) {
				series4 = new XYSeries("家居");
				for (int i = 0; i < walk_consume.size(); i++) {
					float x, y;
					x = walk_consume.get(i);
					y = Float.parseFloat(walk_day.get(i));
					series4.add(y, x);
				}
				dataset.addSeries(series4);
			}
			// 获取家居的所有数据
			if (house1_consume.size() != 0) {
				series5 = new XYSeries("学习");
				for (int i = 0; i < house1_consume.size(); i++) {
					float x, y;
					x = house1_consume.get(i);
					y = Float.parseFloat(house1_day.get(i));
					series5.add(y, x);
				}
				dataset.addSeries(series5);
			}
			// 获取学习的数据
			if (learn_consume.size() != 0) {
				series6 = new XYSeries("娱乐");
				for (int i = 0; i < learn_consume.size(); i++) {
					float x, y;
					x = learn_consume.get(i);
					y = Float.parseFloat(learn_day.get(i));
					series6.add(y, x);
				}
				dataset.addSeries(series6);
			}
			// 获取交往的数据
			if (play_consume.size() != 0) {
				series7 = new XYSeries("交往");
				for (int i = 0; i < play_consume.size(); i++) {
					float x, y;
					x = play_consume.get(i);
					y = Float.parseFloat(play_day.get(i));
					series7.add(y, x);
				}
				dataset.addSeries(series7);
			}
			// 获取交往的数据
			if (friend_consume.size() != 0) {
				series8 = new XYSeries("就医");
				for (int i = 0; i < friend_consume.size(); i++) {
					float x, y;
					x = friend_consume.get(i);
					y = Float.parseFloat(friend_day.get(i));
					series8.add(y, x);
				}
				dataset.addSeries(series8);
			}
			// 获取就医的数据
			if (doctor_consume.size() != 0) {
				series9 = new XYSeries("其他");
				for (int i = 0; i < doctor_consume.size(); i++) {
					float x, y;
					x = doctor_consume.get(i);
					y = Float.parseFloat(doctor_day.get(i));
					series9.add(y, x);
				}
				dataset.addSeries(series9);
			}

		}
		return dataset;
	}

	/**
	 * @return renderer 设置画折线图的属性
	 */
	public XYMultipleSeriesRenderer getRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(30);// 设置坐标轴标题文本大小
		renderer.setChartTitleTextSize(40); // 设置图表标题文本大小
		renderer.setLabelsTextSize(30); // 设置刻度表示的文字大小
		renderer.setLegendTextSize(30); // 设置图例文本大小
		renderer.setLabelsColor(Color.BLACK);
		renderer.setMarginsColor(Color.argb(0, 0xF3, 0xF3, 0xF3));// 四周颜色
		renderer.setMargins(new int[] { 50, 40, 50, 0 }); // 四周间隔
		renderer.setChartTitle("消费流水图");
		renderer.setXAxisMin(0);// X轴最小值
		renderer.setXAxisMax(31);// 最大
		renderer.setXTitle("日");// 坐标轴解释
		renderer.setYAxisMin(0);
		renderer.setYTitle("元");
		renderer.setXLabels(16);
		renderer.setPointSize(10);// 设置点的大小
		renderer.setXLabelsColor(Color.BLACK); // 刻度颜色
		renderer.setYLabelsColor(0, Color.BLACK); // 刻度颜色
		renderer.setShowGrid(true); // 显示网格
		renderer.setAxesColor(Color.BLACK);// 坐标轴颜色

		renderer.setBackgroundColor(Color.parseColor("#f3f3f3"));
		renderer.setApplyBackgroundColor(true);

		renderer.setPanEnabled(false);

		if (clothes_consume.size() != 0) {
			XYSeriesRenderer r1 = new XYSeriesRenderer();
			r1.setColor(0xffff00ff);
			r1.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r1);
		}

		if (eat_consume.size() != 0) {
			XYSeriesRenderer r2 = new XYSeriesRenderer();
			r2.setColor(0xff000000);
			r2.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r2);
		}

		if (house_consume.size() != 0) {
			XYSeriesRenderer r3 = new XYSeriesRenderer();
			r3.setColor(0xff0000ff);
			r3.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r3);
		}

		if (walk_consume.size() != 0) {
			XYSeriesRenderer r4 = new XYSeriesRenderer();
			r4.setColor(0xff00ffff);
			r4.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r4);
		}
		
		if (house1_consume.size() != 0) {
			XYSeriesRenderer r5 = new XYSeriesRenderer();
			r5.setColor(0xff444444);
			r5.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r5);
		}
		
		if (learn_consume.size() != 0) {
			XYSeriesRenderer r6 = new XYSeriesRenderer();
			r6.setColor(0xff00ff00);
			r6.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r6);
		}
		
		if (play_consume.size() != 0) {
			XYSeriesRenderer r7 = new XYSeriesRenderer();
			r7.setColor(0xffff0000);
			r7.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r7);
		}
		
		if (friend_consume.size() != 0) {
			XYSeriesRenderer r8 = new XYSeriesRenderer();
			r8.setColor(0xffffff00);
			r8.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r8);
		}
		
		if (doctor_consume.size() != 0) {
			XYSeriesRenderer r9 = new XYSeriesRenderer();
			r9.setColor(Color.MAGENTA);
			r9.setPointStyle(PointStyle.DIAMOND);
			renderer.addSeriesRenderer(r9);
		}

		return renderer;
	}

	@Override
	public void draw() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset = getDataset();
		LinearLayout lineViewLayout = (LinearLayout) context
				.findViewById(R.id.pieView1);
		if (dataset != null) {
			XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
			renderer = getRenderer();
			GraphicalView lineView = ChartFactory.getLineChartView(context,
					dataset, renderer);

			if (lineViewLayout != null) {
				lineViewLayout.removeAllViews();// 先清除原来的图
				LayoutParams show = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);// 确定
				// 画布大小
				lineViewLayout.addView(lineView, show);
			}
		} else {
			Toast.makeText(context, "没有消费记录", Toast.LENGTH_SHORT).show();
			lineViewLayout.removeAllViews();// 先清除原来的图
		}

	}
}
