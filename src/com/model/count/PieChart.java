package com.model.count;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.activity.Count_Activity;
import com.dao.TJ_DAO;
import com.yyyy.yyyy.R;

public class PieChart implements Chart {
	ArrayList<Float> consume_type_list;
	TJ_DAO tj_DataBaseHelper;
	Activity context = Count_Activity.countActivity;
	float percent[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	// ��ʱ����л�ͼ
	String date;

	public PieChart(String date) {
		this.date = date;
	}

	/**
	 * �����ݿ��ȡÿ�����Ļ�����Ǯ����ʱ����в�ѯ
	 */
	public CategorySeries getSeries(String date) {
		System.out.println("lilingling" + date);
		CategorySeries series = new CategorySeries("���ѷ���");
		consume_type_list = new ArrayList<Float>();
		TJ_DAO tj_DataBaseHelper = new TJ_DAO();
		consume_type_list = tj_DataBaseHelper.getTypeConsume(date);
		for (int i = 0; i < 9; i++) {
			percent[i] = (float) consume_type_list.get(i);
		}
		if (percent[0] != 0) {
			series.add("ʳ", percent[0]);
		}
		if (percent[1] != 0) {
			series.add("��", percent[1]);
		}
		if (percent[2] != 0) {
			series.add("��", percent[2]);
		}
		if (percent[3] != 0) {
			series.add("�Ҿ�", percent[3]);
		}
		if (percent[4] != 0) {
			series.add("ѧϰ", percent[4]);
		}
		if (percent[5] != 0) {
			series.add("����", percent[5]);
		}
		if (percent[6] != 0) {
			series.add("����", percent[6]);
		}
		if (percent[7] != 0) {
			series.add("��ҽ", percent[7]);
		}
		if (percent[8] != 0) {
			series.add("����", percent[8]);
		}
		return series;
	}

	/**
	 * �Խ�Ҫ���ı�ͼ������������
	 */
	public DefaultRenderer getRenderer() {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setMargins(new int[] { 10, 20, 30, 0 });// ����ͼ��֮��ļ�ӵ�λΪpx
		//renderer.setLabelsTextSize(50);// ���ñ�ǩ�������С
		renderer.setLabelsColor(Color.BLACK);
		renderer.setDisplayValues(true);// �Ƿ���ʾֵ
		renderer.setChartTitle("����������");
		//renderer.setChartTitleTextSize(50);
		//renderer.setLegendTextSize(50);// ����ͼ���������С
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
		renderer.setFitLegend(true);
		// ���ÿ�����������ٷֱȲ�����ÿһ����������
		if (percent[0] != 0) {
			SimpleSeriesRenderer r1 = new SimpleSeriesRenderer();
			r1.setColor(0xffff00ff);//��ɫ
			renderer.addSeriesRenderer(r1);
		}// ���ñ�ͼ��ɫ����
		if (percent[1] != 0) {
			SimpleSeriesRenderer r2 = new SimpleSeriesRenderer();
			r2.setColor(0xff000000);//��ɫ
			renderer.addSeriesRenderer(r2);
		}// ���ñ�ͼ��ɫ��ʳ
		if (percent[2] != 0) {
			SimpleSeriesRenderer r3 = new SimpleSeriesRenderer();
			r3.setColor(0xff0000ff);//����
			renderer.addSeriesRenderer(r3);
		} // ���ñ�ͼ��ɫ��ס
		if (percent[3] != 0) {
			SimpleSeriesRenderer r4 = new SimpleSeriesRenderer();
			r4.setColor(0xff00ffff);//ǳ��
			renderer.addSeriesRenderer(r4);
		} // ���ñ�ͼ��ɫ����
		if (percent[4] != 0) {
			SimpleSeriesRenderer r5 = new SimpleSeriesRenderer();
			r5.setColor(0xff444444);//��ɫ
			renderer.addSeriesRenderer(r5);
		} // ���ñ�ͼ��ɫ���Ҿ�
		if (percent[5] != 0) {
			SimpleSeriesRenderer r6 = new SimpleSeriesRenderer();
			r6.setColor(0xff00ff00);//��ɫ
			renderer.addSeriesRenderer(r6);
		} // ���ñ�ͼ��ɫ��ѧϰ
		if (percent[6] != 0) {
			SimpleSeriesRenderer r7 = new SimpleSeriesRenderer();
			r7.setColor(0xffff0000);//��ɫ
			renderer.addSeriesRenderer(r7);
		} // ���ñ�ͼ��ɫ������
		if (percent[7] != 0) {
			SimpleSeriesRenderer r8 = new SimpleSeriesRenderer();
			r8.setColor(0xffffff00);//��ɫ
			renderer.addSeriesRenderer(r8);
		}// ���ñ�ͼ��ɫ������
		if (percent[8] != 0) {
			SimpleSeriesRenderer r9 = new SimpleSeriesRenderer();
			r9.setColor(Color.MAGENTA);//Ʒ��
			renderer.addSeriesRenderer(r9);
		} // ���ñ�ͼ��ɫ����ҽ

		return renderer;
	}

	@Override
	public void draw() {
		CategorySeries series = getSeries(date);
		DefaultRenderer renderer = getRenderer();
		GraphicalView pieView = ChartFactory.getPieChartView(context, series,
				renderer);
		LinearLayout pieViewLayout = (LinearLayout) context
				.findViewById(R.id.pieView1);
		if (pieViewLayout != null) {
			pieViewLayout.removeAllViews();// �����ԭ����ͼ
		}
		if (percent[0] == 0 && percent[1] == 0 && percent[2] == 0
				&& percent[3] == 0 && percent[4] == 0 && percent[5] == 0
				&& percent[6] == 0 && percent[7] == 0 && percent[8] == 0) {
			Toast.makeText(context, "û�����Ѽ�¼", Toast.LENGTH_SHORT).show();
		} else {

			// ȷ��������С
			LayoutParams show = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			pieViewLayout.addView(pieView, show);// ����ͼ��Ϊ�Ӳ��ּ��뵽��������
		}
	}
}
