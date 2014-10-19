package com.gadgets.stopwatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gadgets.collection.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopWacthActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acvtivty_stopwatch);
		init();
	}

	TextView timetTextView;

	long zeroTime;

	long time;

	private SimpleDateFormat sdf;
	private boolean flag;
	Date date;

	Button btss;

	void init() {
		timetTextView = (TextView) findViewById(R.id.stopwatchtextView_time);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		date = cal.getTime();
		zeroTime = date.getTime();
		sdf = new SimpleDateFormat("HH:mm:ss SSS");
		timetTextView.setText(sdf.format(date));
		time = zeroTime;

		btss = (Button) findViewById(R.id.button_sstart);
	}

	/**
	 * 开始停止
	 * 
	 * @param view
	 */
	public void startwatch(View view) {
		if (!flag) {
			flag = true;
			((Button) view).setText("停止");
			new Thread(new Runnable() {

				public void run() {
					while (flag) {
						try {
							long t1 = System.currentTimeMillis();
							date = new Date(time);
							timetTextView.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									timetTextView.setText(sdf.format(date));
								}
							});
							time += 33;
							long t2 = System.currentTimeMillis();
							Thread.sleep(33 - (t2 - t1));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		} else {
			flag = false;
			((Button) view).setText("开始");
		}
	}

	/**
	 * 归零
	 * 
	 * @param view
	 */
	public void turn0watch(View view) {
		flag = false;
		time = zeroTime;
		date = new Date(time);
		timetTextView.setText(sdf.format(date));
		btss.setText("开始");
	}
}
