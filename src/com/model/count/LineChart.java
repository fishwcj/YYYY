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

import com.activity.Count_Activity;
import com.dao.TJ_DAO;
import com.inteface.Chart;
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
	String date;
	TJ_DAO tj_DAO;
	Activity context = Count_Activity.countActivity;

	public LineChart(String date) {
		this.date = date;
	}
/**
 * @author LLL
 * 获取数据库中在衣食住行这四个方面消费的日期以及当天的消费金额
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
	}
/**
 * 
 * @return XYMultipleSeriesDataset dataset 画折线的数据
 */
	private XYMultipleSeriesDataset getDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		getDate();
		if (clothes_consume.size() == 0 && eat_consume.size() == 0
				&& house_consume.size() == 0 && walk_consume.size() == 0) {
			dataset = null;
		} else {

			XYSeries series1, series2, series3, series4;
			// 获取衣的所有数据
			series1 = new XYSeries("衣");
			for (int i = 0; i < clothes_consume.size(); i++) {
				float x, y;
				x = clothes_consume.get(i);
				y = Float.parseFloat(clothes_day.get(i));
				series1.add(y, x);
			}
			// 获取食的所有数据
			series2 = new XYSeries("食");
			for (int i = 0; i < eat_consume.size(); i++) {
				float x, y;
				x = eat_consume.get(i);
				y = Float.parseFloat(eat_day.get(i));
				series2.add(y, x);
			}
			// 获取住的所有数据
			series3 = new XYSeries("住");
			for (int i = 0; i < house_consume.size(); i++) {
				float x, y;
				x = house_consume.get(i);
				y = Float.parseFloat(house_day.get(i));
				series3.add(y, x);
			}
			// 获取行的所有数据
			series4 = new XYSeries("行");
			for (int i = 0; i < walk_consume.size(); i++) {
				float x, y;
				x = walk_consume.get(i);
				y = Float.parseFloat(walk_day.get(i));
				series4.add(y, x);
			}
			dataset.addSeries(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);
			dataset.addSeries(series4);

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
		renderer.setLabelsTextSize(30); // 设置轴标签文本大小
		renderer.setLegendTextSize(30); // 设置图例文本大小
		renderer.setMargins(new int[] { 20, 50, 15, 0 }); // 设置4边留白
		renderer.setChartTitle("消费流水图");
		renderer.setXAxisMin(1);
		renderer.setXAxisMax(31);
		renderer.setXTitle("日");
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(1000);
		renderer.setYTitle("元");
		renderer.setXLabels(15);
		renderer.setPanEnabled(false, false);
		
//renderer.setFitLegend(true);
		XYSeriesRenderer r1 = new XYSeriesRenderer();
		r1.setColor(Color.BLUE);
		r1.setPointStyle(PointStyle.DIAMOND);
		renderer.addSeriesRenderer(r1);
		XYSeriesRenderer r2 = new XYSeriesRenderer();
		r2.setColor(Color.GREEN);
		r2.setPointStyle(PointStyle.X);
		renderer.addSeriesRenderer(r2);

		XYSeriesRenderer r3 = new XYSeriesRenderer();
		r3.setColor(Color.RED);
		r3.setPointStyle(PointStyle.SQUARE);
		renderer.addSeriesRenderer(r3);

		XYSeriesRenderer r4 = new XYSeriesRenderer();
		r4.setColor(Color.YELLOW);
		r4.setPointStyle(PointStyle.TRIANGLE);
		;
		renderer.addSeriesRenderer(r4);

		return renderer;
	}

	@Override
	public void draw() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset = getDataset();
		if (dataset != null) {
			XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
			renderer = getRenderer();
			GraphicalView lineView = ChartFactory.getLineChartView(context,
					dataset, renderer);
			LinearLayout lineViewLayout = (LinearLayout) context
					.findViewById(R.id.LineView);
			if (lineViewLayout != null) {
				lineViewLayout.removeAllViews();// 先清除原来的图
				LayoutParams show = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);// 确定
				// 画布大小
				lineViewLayout.addView(lineView, show);
			}
		}
	}
}
