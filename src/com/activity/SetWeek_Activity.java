package com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.model.set.TosAdapterView;
import com.model.set.TosGallery;
import com.model.set.Utils;
import com.model.set.WheelTextView;
import com.model.set.WheelView;
import com.yyyy.yyyy.R;


public class SetWeek_Activity extends Activity {

    private String weekArray[];

    private WheelView mWeek_WheelView = null; 
    private TextView mTextView = null;
    @SuppressWarnings("unused")
	private View mDecorView = null;
    private WeekAdapter weekAdapter;
    
    private TosAdapterView.OnItemSelectedListener mListener = new TosAdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
            ((WheelTextView)view).setTextSize(30);
            
            int index = Integer.parseInt(view.getTag().toString());
            int count = parent.getChildCount();
            if(index < count-1){
                ((WheelTextView)parent.getChildAt(index+1)).setTextSize(25);
            }
            if(index>0){
                ((WheelTextView)parent.getChildAt(index-1)).setTextSize(25);
            }
            formatData();
        }
        
        @Override
        public void onNothingSelected(TosAdapterView<?> parent) {
            
        }
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_time);
        weekArray = getResources().getStringArray(R.array.select_time);
        mTextView = (TextView) findViewById(R.id.sel_password);


        mWeek_WheelView = (WheelView)findViewById(R.id.week_view);


        mWeek_WheelView.setScrollCycle(true);

        weekAdapter = new WeekAdapter(weekArray);

        mWeek_WheelView.setAdapter(weekAdapter);
        

        mWeek_WheelView.setSelection(10, true);
        

        ((WheelTextView)mWeek_WheelView.getSelectedView()).setTextSize(30);


        mWeek_WheelView.setOnItemSelectedListener(mListener);

        mWeek_WheelView.setUnselectedAlpha(0.5f);

        mDecorView = getWindow().getDecorView();

    }
    
    

    @SuppressLint("DefaultLocale")
	private void formatData() {

        int pos = mWeek_WheelView.getSelectedItemPosition();
        String text = String.format("%d", pos);
        mTextView.setText(text);
    }


    private class WeekAdapter extends BaseAdapter {
        int mHeight = 50;
        String[] mData = null;

        public WeekAdapter(String[] data) {
            mHeight = (int) Utils.dipToPx(SetWeek_Activity.this, mHeight);
            this.mData = data;
        }

        @Override
        public int getCount() {
            return (null != mData) ? mData.length : 0;
        }

        @Override
        public View getItem(int arg0) {
            return getView(arg0, null, null);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
        
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WheelTextView textView = null;

            if (null == convertView) {
                convertView = new WheelTextView(SetWeek_Activity.this);
                convertView.setLayoutParams(new TosGallery.LayoutParams(-1, mHeight));
                textView = (WheelTextView) convertView;
                textView.setTextSize(25);
                textView.setGravity(Gravity.CENTER);
            }
            
            
            String text = mData[position];
            if (null == textView) {
                textView = (WheelTextView) convertView;
            }
            
            textView.setText(text);
            return convertView;
        }
    }
}
