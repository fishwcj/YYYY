package com.activity;

/**
 * 1.添加测试+按钮事件，用于测试
 * 2.添加确定按钮
 * @author wcj
 * @time 15-3-31晚
 */

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
//import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.speech.VoiceRecognitionService;
import com.bean.Constant;
import com.dao.JZ_DAO;
import com.dao.YS_DAO;
import com.inteface.IInputCheck;
import com.logic.BackgroundColor;
import com.mnitools.InputCheck;
import com.model.cloud.CloudSendHelper;
import com.yyyy.yyyy.R;

public class JZ_Activity extends Activity implements RecognitionListener{

	public static TextView budgetRemain;
	private TextView kind;
	public TextView consume;
	private TextView setting;
	private Button number_1;
	private Button number_2;
	private Button number_3;
	private Button number_4;
	private Button number_5;
	private Button number_6;
	private Button number_7;
	private Button number_8;
	private Button number_9;
	private Button number_0;
	private Button syButton; // 测试按钮
	private TextView number_in;
	private TextView number_out;
	private TextView voice;
	private Button button_ok;
	private Button number_float;
	private Button number_clear;
	public static String consumString = "";
	public static TextView consumed;
	public static LinearLayout linearLayout;
	private int inOrOut = 0; // 0代表支出，1代表收入，默认支出
	public static int consumekind = 1; // 消费类别参数（默认为食）
	private ArrayList<String> kindList = new ArrayList<String>();
	public static Activity jzActivity;
	private TextView zyj;
	private TextView zq;
	private TextView jd;
	/*
	 * voice begin
	 */
    private static final String TAG = "Sdk2Api";
    private static final int REQUEST_UI = 1;
//    private TextView txtLog;
//    private Button btn;

    public static final int STATUS_None = 0;
    public static final int STATUS_WaitingReady = 2;
    public static final int STATUS_Ready = 3;
    public static final int STATUS_Speaking = 4;
    public static final int STATUS_Recognition = 5;
    private SpeechRecognizer speechRecognizer;
    private int status = STATUS_None;
    private long speechEndTime = -1;
    private static final int EVENT_ERROR = 11;
    /*
     *voice end
     */

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("hehe调用了Resume");
	};

    protected void onDestroy() {
        speechRecognizer.destroy();
        super.onDestroy();
    }
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jz1);
		jzActivity = this;
		System.out.println("JZ被创建");
		System.out.println("JZ线程:" + Thread.currentThread().getId());
		/**
		 * 加载组件
		 */
		consume = (TextView) this.findViewById(R.id.consume);
		budgetRemain = (TextView) this.findViewById(R.id.budgetRemain);
		number_0 = (Button) this.findViewById(R.id.number_0);
		number_1 = (Button) this.findViewById(R.id.number_1);
		number_2 = (Button) this.findViewById(R.id.number_2);
		number_3 = (Button) this.findViewById(R.id.number_3);
		number_4 = (Button) this.findViewById(R.id.number_4);
		number_5 = (Button) this.findViewById(R.id.number_5);
		number_6 = (Button) this.findViewById(R.id.number_6);
		number_7 = (Button) this.findViewById(R.id.number_7);
		number_8 = (Button) this.findViewById(R.id.number_8);
		number_9 = (Button) this.findViewById(R.id.number_9);
		number_in = (TextView) this.findViewById(R.id.button_in);
		number_out = (TextView) this.findViewById(R.id.button_out);
		number_float = (Button) this.findViewById(R.id.number_float);
		number_clear = (Button) this.findViewById(R.id.number_clear);
		button_ok = (Button) this.findViewById(R.id.ok);
		consumed = (TextView) this.findViewById(R.id.comsumed);
		linearLayout = (LinearLayout) this.findViewById(R.id.background);
		zyj = (TextView) this.findViewById(R.id.zyj);
		setting = (TextView) this.findViewById(R.id.setting);
		zq = (TextView) this.findViewById(R.id.zq);
		jd = (TextView) this.findViewById(R.id.jd);
		// 测试按钮
		syButton = (Button) this.findViewById(R.id.sy);
		kind = (TextView) this.findViewById(R.id.kind);
		voice = (TextView)this.findViewById(R.id.voice);

		kindList.add("酒足饭饱");
		kindList.add("穿金戴银");
		kindList.add("酒足饭饱");
		kindList.add("斯是陋室");
		kindList.add("踏破铁鞋");

		final IInputCheck inputCheck = new InputCheck(consume, consumString);// 输入检测接口回调
		inputCheck.setLisener_number(number_0, "0");
		inputCheck.setLisener_number(number_1, "1");
		inputCheck.setLisener_number(number_2, "2");
		inputCheck.setLisener_number(number_3, "3");
		inputCheck.setLisener_number(number_4, "4");
		inputCheck.setLisener_number(number_5, "5");
		inputCheck.setLisener_number(number_6, "6");
		inputCheck.setLisener_number(number_7, "7");
		inputCheck.setLisener_number(number_8, "8");
		inputCheck.setLisener_number(number_9, "9");
		inputCheck.setLisener_clear(number_clear);
		inputCheck.setLisener_float(number_float, ".");

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));

        speechRecognizer.setRecognitionListener(this);
		/**
		 * 语音
		 */
		voice.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(JZ_Activity.this);
                boolean api = sp.getBoolean("api", false);
                if (api) {
                    switch (status) {
                        case STATUS_None:
                            start();
                            status = STATUS_WaitingReady;
                            break;
                        case STATUS_WaitingReady:
                            cancel();
                            status = STATUS_None;
                            break;
                        case STATUS_Ready:
                            cancel();
                            status = STATUS_None;
                            break;
                        case STATUS_Speaking:
                            stop();
                            status = STATUS_Recognition;
                            break;
                        case STATUS_Recognition:
                            cancel();
                            status = STATUS_None;
                            break;
                    }
                } else {
                    start();
                }
            }
		});
		
		/**
		 * 借贷管理
		 */
		jd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(JZ_Activity.this, Borrow_Return.class);
				startActivity(intent);
			}
		});

		/**
		 * 攒钱目标
		 */
		zq.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JZ_Activity.this,
						Target_Activity.class);
				JZ_Activity.this.startActivity(intent);
			}
		});

		/**
		 * 设置按钮事件
		 */
		setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(jzActivity, User_Activity.class);
				startActivity(intent);
			}
		});

		/**
		 * 攒友街
		 */
		zyj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JZ_Activity.this,
						Street_Activity.class);
				startActivity(intent);
			}
		});

		/**
		 * 测试同步按钮
		 */
		syButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CloudSendHelper cloudSendHelper = new CloudSendHelper();
				try {
					try {
						if (cloudSendHelper.checkAndSend()) {
							// Toast.makeText(JZ_Activity.jzActivity, "同步成功!",
							// Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(JZ_Activity.jzActivity, "同步前请登录哦！",
									Toast.LENGTH_LONG).show();
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		/**
		 * 点击类型事件
		 */
		kind.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(JZ_Activity.this,
						SelectPicPopupWindow.class));
			}
		});

		/**
		 * 收入的事件
		 */
		number_in.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number_in.setTextColor(Color.RED);
				number_out.setTextColor(Color.WHITE);
				inOrOut = 1;
				consume.setTextColor(Color.GREEN);
			}
		});

		/**
		 * 支出的事件
		 */
		number_out.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number_in.setTextColor(Color.WHITE);
				number_out.setTextColor(Color.RED);
				inOrOut = 0;
				consume.setTextColor(Color.RED);
				System.out.println("切换到支出类别：inOrOut应该=0，实际为" + inOrOut);
			}
		});

		/**
		 * 按钮ok的事件
		 */
		button_ok.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				consumString = consume.getText().toString();
				if (consumString.length() > 0) {
					SimpleDateFormat sDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					String date = sDateFormat.format(new java.util.Date());
					float consume1 = Float.parseFloat(consumString);
					String kind = null;
					// 从ArrayList里面找到类型的中文描述
					if (inOrOut == 0)
						// kind = kindList.get(consumekind);
						kind = JZ_Activity.this.kind.getText().toString();
					else {
						kind = "收入";
					}
					JZ_DAO.insertStream(consume1, kind, date, inOrOut,
							consumekind);
					consumString = ""; // 插入后清空输入框
					consume.setText(consumString.toCharArray(), 0,
							consumString.length());
					YS_DAO ys_DataBaseHelper = new YS_DAO();

					// 如果是支出则更新预算表，收入则更新收入表
					if (inOrOut == 0) {
						// 同时更新数据库2张预算表
						System.out.println("更新前");
						ys_DataBaseHelper.update(consume1, consumekind);
						System.out.println("更新后");
						// 更新显示余额
						Float remain = Float.parseFloat(budgetRemain.getText()
								.toString());
						remain = remain - consume1;
						budgetRemain.setText(remain.toString());
						System.out.println("插入成功");
						// db.close();
					} else {
						// 更新收入表
						ys_DataBaseHelper.updatein(consume1);
						System.out.println("收入成功");
						// db.close();
					}
				}
				BackgroundColor backgroundColor = new BackgroundColor();
				backgroundColor.refreshback();
				// 更新消费
				String consumed = new DecimalFormat("0.0")
						.format(Index_Activity.budget - Index_Activity.remain);
				JZ_Activity.consumed.setText(consumed);
				inputCheck.setViewString("");
				Toast.makeText(JZ_Activity.this, "成功记入一笔!", Toast.LENGTH_LONG)
						.show();
			}
		});
		/*
		 * 按钮设置预算的按钮
		 */
		budgetRemain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JZ_Activity.this, YS1_Activity.class);
				startActivity(intent);
			}
		});
	}

	/*
	 * 语音方法
	 */
    @Override
    public void onReadyForSpeech(Bundle params) {
        status = STATUS_Ready;
        System.out.println("准备就绪，可以开始说话");
    }

    @Override
    public void onBeginningOfSpeech() {
        status = STATUS_Speaking;
//        btn.setText("说完了");
        System.out.println("检测到用户的已经开始说话");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        speechEndTime = System.currentTimeMillis();
        status = STATUS_Recognition;
        System.out.println("检测到用户的已经停止说话");
//        btn.setText("识别中");
    }

    @Override
    public void onError(int error) {
        status = STATUS_None;
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        System.out.println("识别失败：" + sb.toString());
//        btn.setText("开始");
    }

    @Override
    public void onResults(Bundle results) {
    	System.out.println("进入了rusult");
        long end2finish = System.currentTimeMillis() - speechEndTime;
        status = STATUS_None;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        System.out.println("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
        	System.out.println("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
        	System.out.println("origin_result=[warning: bad json]\n" + json_res);
        }
//        btn.setText("开始");
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }
        System.out.println("结果：" + nbest.get(0) + strEnd2Finish);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
            System.out.println("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
                System.out.println("------------EVENTERROR:" + reason);
                break;
            case VoiceRecognitionService.EVENT_ENGINE_SWITCH:
                int type = params.getInt("engine_type");
                break;
        }
    }
	
    private void start() {
        Intent intent = new Intent();
        bindParams(intent);
        System.out.println("开始设置监听");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        {

            String args = sp.getString("args", "");
            if (null != args) {
                intent.putExtra("args", args);
            }
        }
        boolean api = sp.getBoolean("api", false);
        if (api) {
            speechEndTime = -1;
            speechRecognizer.startListening(intent);
        } else {
            intent.setAction("com.baidu.action.RECOGNIZE_SPEECH");
            getParent().startActivityForResult(intent, REQUEST_UI);
        }
            System.out.println("开始setAction");
            intent.setAction("com.baidu.action.RECOGNIZE_SPEECH");
            System.out.println("开始startActivity");
            getParent().startActivityForResult(intent, REQUEST_UI);
            System.out.println("start调用完毕");
    }
    
    private void stop() {
        speechRecognizer.stopListening();
    }

    private void cancel() {
        speechRecognizer.cancel();
        status = STATUS_None;
    }
    
    public void bindParams(Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("tips_sound", true)) {
        	System.out.println("sp.getBoolean3---------------------");
            intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
            intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
            intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
            intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
            intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
            intent.putExtra(Constant.EXTRA_SAMPLE, 16000);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        	System.out.println("result == ok");
            ArrayList<String> results = data.getStringArrayListExtra(SpeechRecognizer.RESULTS_RECOGNITION);
            onResults(data.getExtras());
            System.out.println(results.toString());
        }
    }
}
