package com.gadgets.compass;

import java.text.DecimalFormat;

import com.gadgets.collection.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View implements Runnable {

	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	

	public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}


	private Paint _mPaint = new Paint();
	private String _message = "正北 0°";
	private float _decDegree = 0;
	private Bitmap _compass;
	private Bitmap _arrow;

	void init() {
		// 载入图片
		_compass = BitmapFactory.decodeResource(getResources(),
				R.drawable.compass);
		// 开启线程否则无法更新画面
		new Thread(this).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 实现图像旋转
		Matrix mat = new Matrix();

		mat.reset();
		mat.setTranslate(0,( getHeight()-getWidth())*0.5f);
		mat.setScale(getWidth() / (float)_compass.getWidth(),
				getWidth() / (float)_compass.getHeight());

		mat.preRotate(-_decDegree, _compass.getWidth() * 0.5f,  _compass.getHeight() * 0.5f);

		// 绘制图像
		canvas.drawBitmap(_compass, mat, _mPaint);
		_mPaint.setColor(Color.WHITE);
		_mPaint.setTextSize(25);
		canvas.drawText(new DecimalFormat("0.00").format(_decDegree)+"°", 0, 25, _mPaint);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(30);
				postInvalidate();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			postInvalidate();
		}
	}

	// 更新指南针角度
	public void setDegree(float degree) {
		// 设置灵敏度
//		if (Math.abs(_decDegree - degree) >= 0.5) {
			_decDegree = degree;
			
//		}
	}
}
