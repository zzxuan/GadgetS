package com.gadgets.ruler;

import java.nio.DoubleBuffer;

import com.gadgets.collection.R;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class RulerView extends View {

	public RulerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public RulerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	void init() {
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint2 = new Paint();
		paint2.setColor(Color.WHITE);
		paint2.setStrokeWidth(3);
		paint2.setStyle(Paint.Style.FILL);
		try {
			mm = getResources().getDimension(R.dimen.mm);
			in = getResources().getDimension(R.dimen.in);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	float in = 10;
	float mm = 5;
	float square3 = 1.7320508075689f;

	Paint paint;
	Paint paint2;

	int outcolor = 0xFF66CC66;
	int incolor = 0xFF996600;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		canvas.drawLine(0, 0, 0, getHeight(), paint2);
		canvas.drawLine(0, 0, getWidth(), 0, paint2);
		int num = (int) (getHeight() / mm);
		{
			int w = getWidth();
			int h = getHeight();
			if (square3 * h / 3 > w) {
				float fh = square3 * w;
				// canvas.drawLine(w, 0, 0, fh, paint2);
				// canvas.drawText(60 + "°", w - paint.getTextSize() * 3,
				// 0 + paint.getTextSize(), paint);

				Path outPath = new Path();
				outPath.moveTo(0, 0);
				outPath.lineTo(w, 0);
				outPath.lineTo(0, fh);
				outPath.close();
				paint2.setColor(outcolor);
				canvas.drawPath(outPath, paint2);

				float padx = w * 0.2f;
				float pady = padx * square3;
				Path mPath = new Path();
				mPath.moveTo(padx, pady);
				mPath.lineTo(-2 * padx + w, pady);
				mPath.lineTo(padx, h - 2 * pady);
				mPath.close();

				paint2.setColor(incolor);
				canvas.drawPath(mPath, paint2);

			} else {
				float fw = square3 * h / 3;
				// canvas.drawLine(fw, 0, 0, h, paint2);

				Path outPath = new Path();// 外侧三角形
				outPath.moveTo(0, 0);
				outPath.lineTo(fw, 0);
				outPath.lineTo(0, h);
				outPath.close();
				paint2.setColor(outcolor);
				canvas.drawPath(outPath, paint2);

				float padx = fw * 0.2f;
				float pady = padx * square3;
				Path mPath = new Path();// 内侧三角形
				mPath.moveTo(padx, pady);
				mPath.lineTo(-2 * padx + fw, pady);
				mPath.lineTo(padx, h - 2 * pady);
				mPath.close();
				paint2.setColor(incolor);
				canvas.drawPath(mPath, paint2);
				// canvas.drawText(60 + "°", fw - paint.getTextSize() * 3,
				// 0 + paint.getTextSize(), paint);
			}
		}
		// 画刻度
		for (int i = 0; i <= num; i++) {
			float h = i * mm;
			if (i % 10 == 0) {
				canvas.drawLine(0, h, 15, h, paint);
				canvas.drawText((i / 10) + "", 16,
						(h + paint.getTextSize() * 0.5f), paint);
			} else if (i % 5 == 0) {
				canvas.drawLine(0, h, 10, h, paint);
			} else {
				canvas.drawLine(0, h, 5, h, paint);
			}
		}
		paint2.setColor(Color.WHITE);
		canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint2);
		// 画刻度
		for (int i = 0; i <= num; i++) {
			float h = i * in * 0.1f;
			if (i % 10 == 0) {
				canvas.drawLine(getWidth(), h, getWidth() - 15, h, paint);
				canvas.drawText((i / 10) + "", getWidth() - 18,
						(h + paint.getTextSize() * 0.5f), paint);
			} else if (i % 5 == 0) {
				canvas.drawLine(getWidth(), h, getWidth() - 10, h, paint);
			} else {
				canvas.drawLine(getWidth(), h, getWidth() - 5, h, paint);
			}
		}
	}
}
