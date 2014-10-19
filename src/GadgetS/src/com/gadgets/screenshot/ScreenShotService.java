package com.gadgets.screenshot;

import com.gadgets.collection.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ScreenShotService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Toast.makeText(this, "sda", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	public void onKeyDown(int keyCode, KeyEvent event) {
	      if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
	          //记住这个按键
	    	  startActivity(new Intent(this,MainActivity.class));
	    	  Log.i("sda","sadsadasd");
	    	  Toast.makeText(this, "sda", Toast.LENGTH_SHORT).show();
	    }
	 }
	
	

}
