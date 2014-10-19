package com.gadgets.collection;

import com.gadgets.compass.CompassActivity;
import com.gadgets.filemanger.FileMangerActivity;
import com.gadgets.flashlight.Flashlight;
import com.gadgets.ruler.RulerAcivity;
import com.gadgets.ruler.RulerView;
import com.gadgets.screenshot.ScreenShotService;
import com.gadgets.stopwatch.StopWacthActivity;
import com.jp.sdk.uti.Enter;

import comgadgets.soundrecord.RecordActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainlayout);
		initlayout();
		init();

		// Enter 为你自定义的入口名   
		Enter enter = new Enter();
		// 参数传入context 和int   1为计费模式 ； 0为测试模式
		enter.startService(this, 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.mbutton4:// 手电筒
			Flashlight.getIntance().clickFlashlight();
			break;
		case R.id.mbutton1:// 秒表
			startActivity(new Intent(this, StopWacthActivity.class));
			break;
		case R.id.mbutton6:// 录音机
			startActivity(new Intent(this, RecordActivity.class));
			break;
		case R.id.mbutton5:// 三角尺
			startActivity(new Intent(this, RulerAcivity.class));
			break;
		case R.id.mbutton3:// 指南针
			startActivity(new Intent(this, CompassActivity.class));
			break;
		case R.id.mbutton2:// 查文件
			startActivity(new Intent(this, FileMangerActivity.class));
			break;
		default:
			break;
		}

	}

	Button bt1;
	Button bt2;
	Button bt3;
	Button bt4;
	Button bt5;
	Button bt6;

	void init() {
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
	}

	void initlayout() {
		LinearLayout la1 = (LinearLayout) findViewById(R.id.mlinearLayout1);
		LinearLayout la2 = (LinearLayout) findViewById(R.id.mlinearLayout2);
		bt1 = (Button) findViewById(R.id.mbutton1);
		bt2 = (Button) findViewById(R.id.mbutton2);
		bt3 = (Button) findViewById(R.id.mbutton3);
		bt4 = (Button) findViewById(R.id.mbutton4);
		bt5 = (Button) findViewById(R.id.mbutton5);
		bt6 = (Button) findViewById(R.id.mbutton6);
		TextView t1 = (TextView) findViewById(R.id.mtextView1);
		TextView t2 = (TextView) findViewById(R.id.mtextView2);
		TextView t3 = (TextView) findViewById(R.id.mtextView3);
		TextView t4 = (TextView) findViewById(R.id.mtextView4);
		TextView t5 = (TextView) findViewById(R.id.mtextView5);
		TextView t6 = (TextView) findViewById(R.id.mtextView6);
		DisplayMetrics dm2 = getResources().getDisplayMetrics();
		int w = dm2.widthPixels;
		int h = dm2.heightPixels;
		if (w > h)
			return;
		int wnum = (int) (w / 7);
		int hnum = (int) (h / 15);

		int pad = (int) (w / 30);
		{
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(wnum, pad * 3, 0, 0);
			la1.setLayoutParams(lp);
			la2.setLayoutParams(lp);
		}
		{
			LayoutParams lp1 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp1.setMargins(pad, hnum, pad, 0);
			lp1.width = 2 * wnum - 2 * pad;
			lp1.height = 2 * wnum - 2 * pad;
			// bt1.setWidth(2*pad);
			// bt1.setHeight(2*wnum-2*pad);
			t1.setWidth(2 * wnum);
			bt1.setLayoutParams(lp1);

			// bt2.setWidth(2*wnum-2*pad);
			// bt2.setHeight(2*wnum-2*pad);
			t2.setWidth(2 * wnum);
			bt2.setLayoutParams(lp1);

			// bt3.setWidth(2*wnum-2*pad);
			// bt3.setHeight(2*wnum-2*pad);
			t3.setWidth(2 * wnum);
			bt3.setLayoutParams(lp1);

			// bt4.setWidth(2*wnum-2*pad);
			// bt4.setHeight(2*wnum-2*pad);
			t4.setWidth(2 * wnum);
			bt4.setLayoutParams(lp1);

			// bt5.setWidth(2*wnum-2*pad);
			// bt5.setHeight(2*wnum-2*pad);
			t5.setWidth(2 * wnum);
			bt5.setLayoutParams(lp1);

			// bt6.setWidth(2*wnum-2*pad);
			// bt6.setHeight(2*wnum-2*pad);
			t6.setWidth(2 * wnum);
			bt6.setLayoutParams(lp1);
		}

	}

}
