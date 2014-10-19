package comgadgets.soundrecord;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gadgets.collection.R;
import com.gadgets.filemanger.ChooseFileDialog;
import com.gadgets.filemanger.FileCopyUtil;
import com.gadgets.filemanger.FileMangerActivity;

import android.R.bool;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends Activity {

	private static final String LOG_TAG = "AudioRecordTest";
	// 语音文件保存路径
	private String FileName = null;

	// 界面控件
	private Button startRecord;
	private Button startPlay;
	Button expButton;
	// private Button stopRecord;
	// private Button stopPlay;

	// 语音操作对象
	private MediaPlayer mPlayer = null;
	private MediaRecorder mRecorder = null;
	private Date date;
	private SimpleDateFormat sdf;

	TextView textViewSound;
	private long zeroTime;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		// 开始录音
		startRecord = (Button) findViewById(R.id.button_sstart);
		startRecord.setText("录音");
		// 绑定监听器
		startRecord.setOnClickListener(new startRecordListener());

		// 开始播放
		startPlay = (Button) findViewById(R.id.button_play);
		startPlay.setText("播放  ");
		// 绑定监听器
		startPlay.setOnClickListener(new startPlayListener());

		// 设置sdcard的路径
		FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileName += "/gadgets/record/recordsound.3gp";
		File file = new File(FileName);
		//file.getParentFile().getParentFile().delete();
		if (!file.getParentFile().exists())// 如果不存在
		{
			file.getParentFile().mkdirs();// 测试下这个能否创建多级目录结构
		}
		textViewSound = (TextView) findViewById(R.id.textView_sound);
		
		expButton=(Button)findViewById(R.id.button_exp);
		
		
		expButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String string=Environment.getExternalStorageDirectory().getAbsolutePath();
				new ChooseFileDialog(RecordActivity.this, new File(string), new ChooseFileDialog.CompleteChoose() {
					
					@Override
					public void finalfile(File dirFile) {
						// TODO Auto-generated method stub
						try {
							final File f = new File(FileName);
						String newpathString=dirFile.getPath()
								+ "/" + System.currentTimeMillis()+".3gp";
							FileCopyUtil.copyFile(f.getPath(), newpathString);
							Toast.makeText(RecordActivity.this, "导出成功,文件路径为:"+newpathString,
									Toast.LENGTH_SHORT).show();	
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(RecordActivity.this, "导出失败",
									Toast.LENGTH_SHORT).show();	
						}
					}
				}).Show();
			}
		});
		init();
	}

	void init() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		date = cal.getTime();
		zeroTime = date.getTime();
		sdf = new SimpleDateFormat("mm:ss");
		textViewSound.setText("录制时长: " + sdf.format(date));
	}

	// 开始录音
	boolean isstart = false;

	class startRecordListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!isstart || mRecorder == null) {
				mRecorder = new MediaRecorder();
				mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setOutputFile(FileName);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				try {
					mRecorder.prepare();
				} catch (IOException e) {
					Log.e(LOG_TAG, "prepare() failed");
				}
				mRecorder.start();
				isstart = true;
				startRecord.setText("停止");
				startPlay.setEnabled(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						long time = zeroTime;
						while (isstart) {
							date = new Date(time);
							RecordActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {

										textViewSound.setText("录制时长: "
												+ sdf.format(date));
									} catch (Exception e) {
										// TODO: handle exception
										Toast.makeText(RecordActivity.this,
												e.getMessage(),
												Toast.LENGTH_LONG).show();
									}
								}
							});
							time += 1000;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}).start();
			} else {
				isstart = false;
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;
				startRecord.setText("录音");
				startPlay.setEnabled(true);
			}
		}

	}

	// 播放录音
	boolean isplay = false;

	class startPlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mPlayer == null) {
				mPlayer = new MediaPlayer();
				try {
					mPlayer.setDataSource(FileName);
					mPlayer.prepare();
					mPlayer.start();
					startPlay.setText("停止");
					isplay = true;
					mPlayer.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer arg0) {
							// TODO Auto-generated method stub
							mPlayer.release();
							mPlayer = null;
							startPlay.setText("播放");
							isplay = false;
						}
					});
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							long time = zeroTime;
							while (isplay) {
								date = new Date(time);
								RecordActivity.this
										.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												try {

													textViewSound.setText("播放时长: "
															+ sdf.format(date));
												} catch (Exception e) {
													// TODO: handle exception
													Toast.makeText(
															RecordActivity.this,
															e.getMessage(),
															Toast.LENGTH_LONG)
															.show();
												}
											}
										});
								time += 1000;
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}).start();
				} catch (IOException e) {
					Log.e(LOG_TAG, "播放失败");
				}
			} else {
				mPlayer.release();
				mPlayer = null;
				startPlay.setText("播放");
				isplay = false;
			}
		}

	}
}