package com.gadgets.filemanger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.gadgets.collection.R;
import com.gadgets.filemanger.ChooseFileDialog.CompleteChoose;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.security.KeyChain;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileMangerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemanger);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filemenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		if (item.getItemId() == R.id.itemfileadd) {
			adddirfile();
		}
		return true;
	}

	EditText searEditText;
	TextView rootTextView;
	ListView filelListView;
	private FileListAdapter fileListAdapter;
	File root;

	void init() {
		searEditText = (EditText) findViewById(R.id.seracheditText);
		// searEditText.setFocusable(false);
		rootTextView = (TextView) findViewById(R.id.roottextView);
		// rootTextView.requestFocus();

		filelListView = (ListView) findViewById(R.id.filelistView);
		fileListAdapter = new FileListAdapter(this);
		filelListView.setAdapter(fileListAdapter);
		filelListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try {
					if (fileListAdapter.getFiles() != null) {
						File file = fileListAdapter.getFiles().get(arg2);
						if (file.isDirectory()) {
							updateview(file);
						} else {
							Intent intent = AndroidFileUtil.openFile(file
									.getPath());
							startActivity(intent);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(FileMangerActivity.this, "没有找到可打开的程序...",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		filelListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				itemlongclick(arg2);
				return false;
			}
		});
		File path = new File("/");
		updateview(path);
	}

	void updateview(File file) {
		File[] f = file.listFiles();
		rootTextView.setText(file.getPath());
		fileListAdapter.setFiles(arraytolist(f));
		root = file;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			File file = root.getParentFile();
			if (file != null) {
				updateview(root.getParentFile());
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 搜索文件
	 * 
	 * @param view
	 */
	public void searchfile(View view) {
		try {

			final String key = searEditText.getText().toString();
			if (key != null && (!key.equals("")) && root != null) {
				final ProgressDialog dialog = new ProgressDialog(this);
				dialog.setTitle("正在搜索...");
				dialog.setMessage("请稍后...");
				dialog.show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							files.clear();
							pretime = System.currentTimeMillis();
							searchfile(key, root);
							FileMangerActivity.this
									.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											fileListAdapter.setFiles(files);
											if (files.size() > 0) {
												Toast.makeText(
														FileMangerActivity.this,
														"共搜索到 " + files.size()
																+ " 条!",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												Toast.makeText(
														FileMangerActivity.this,
														"没有找到,请在别的目录下尝试",
														Toast.LENGTH_SHORT)
														.show();
											}
										}
									});
							dialog.dismiss();
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(FileMangerActivity.this,
									e.getMessage() + "..." + files.size(),
									Toast.LENGTH_SHORT).show();
						}
					}
				}).start();

			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(FileMangerActivity.this,
					e.getMessage() + "..." + files.size(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	ArrayList<File> arraytolist(File[] f) {
		if (f == null) {
			return new ArrayList<File>();
		} else {
			ArrayList<File> files = new ArrayList<File>();
			for (File file : f) {
				files.add(file);
			}
			return files;
		}
	}

	ArrayList<File> files = new ArrayList<File>();
	long pretime;

	void searchfile(String key, File root) {
		File[] f = root.listFiles();
		if (f != null) {
			for (File file : f) {
				if (file.getName().contains(key)) {
					files.add(file);
				}
				if (file.isDirectory()) {
					searchfile(key, file);
				}
				if (files.size() > 1000)
					break;
				long curtime = System.currentTimeMillis();
				if (curtime - pretime > 1000 * 15)
					break;
			}
		}
	}

	void itemlongclick(int arg2) {
		final File file = fileListAdapter.getFiles().get(arg2);
		if (file.isDirectory()) {
			new AlertDialog.Builder(this)
					.setTitle("列表框")
					.setItems(new String[] { "删除", "重名名", "移动", "复制", "详情" },
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									switch (arg1) {
									case 0:// 删除
										deletefile(file);
										break;
									case 1:// 重名名
										renamefile(file);
										break;
									case 2:// 移动
										movefile(file);
										break;
									case 3:// 复制
										copyfile(file);
										break;
									case 4:// 详情
										filemsgshow(file);
										break;
									default:
										break;
									}

								}
							}).show();
		} else {
			new AlertDialog.Builder(this)
					.setTitle("列表框")
					.setItems(
							new String[] { "发送文件", "删除", "重名名", "移动", "复制",
									"详情" }, new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									switch (arg1) {
									case 0:// 分享
										Intent intent = new Intent(
												Intent.ACTION_SEND);

										intent.setType("*/*");
										intent.putExtra(Intent.EXTRA_STREAM,
												Uri.fromFile(file));// 此处一定要用Uri.fromFile(file),其中file为File类型，否则附件无法发送成功。
										startActivity(Intent.createChooser(
												intent, "发送"));
										break;
									case 1:// 删除
										deletefile(file);
										break;
									case 2:// 重名名
										renamefile(file);
										break;
									case 5:// 详情
										filemsgshow(file);
										break;
									case 3:// 移动
										movefile(file);
										break;
									case 4:// 复制
										copyfile(file);
										break;
									default:
										break;
									}
								}

							}).show();
		}
	}

	protected void copyfile(File file) {
		// TODO Auto-generated method stub
		final File f = file;
		new ChooseFileDialog(this, new File("/"), new CompleteChoose() {

			@Override
			public void finalfile(File dirFile) {
				// TODO Auto-generated method stub
				try {
					if (f.isDirectory()) {
						if (!(dirFile.getPath()).startsWith(f.getPath())) {
							FileCopyUtil.copyFolder(f.getPath(),
									dirFile.getPath() + "/" + f.getName());
						} else {
							Toast.makeText(FileMangerActivity.this, "不能复制",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						FileCopyUtil.copyFile(f.getPath(), dirFile.getPath()
								+ "/" + f.getName());
					}
					updateview(root);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(FileMangerActivity.this, "复制失败...",
							Toast.LENGTH_SHORT).show();
				}
			}
		}).Show();
	}

	private void movefile(File file) {
		// TODO Auto-generated method stub
		final File f = file;
		new ChooseFileDialog(this, new File("/"), new CompleteChoose() {

			@Override
			public void finalfile(File dirFile) {
				// TODO Auto-generated method stub
				try {
					f.renameTo(new File(dirFile.getPath() + "/" + f.getName()));
					updateview(root);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(FileMangerActivity.this, "移动失败...",
							Toast.LENGTH_SHORT).show();
				}

			}
		}).Show();
	}

	void filemsgshow(File f) {
		new AlertDialog.Builder(this).setTitle(f.getName())
				.setMessage(getfilemsg(f)).show();
	}

	String getfilemsg(File f) {
		String string = "";
		try {
			if (f.isDirectory()) {
				string += "这是个文件夹" + "\n";
				string += "大小:"
						+ FileSizeUtil.getAutoFileOrFilesSize(f.getPath())
						+ "\n";
				string += "修改时间:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date(f.lastModified())) + "\n";
			} else {
				string += "大小:"
						+ FileSizeUtil.getAutoFileOrFilesSize(f.getPath())
						+ "\n";
				string += "修改时间:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date(f.lastModified())) + "\n";
			}

		} finally {

		}
		return string;
	}

	void renamefile(File file) {
		final File f = file;
		final EditText editText = new EditText(this);
		editText.setText(file.getName());
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						try {
							String string = f.getParent();
							File newFile = new File(string + "/"
									+ editText.getText().toString());
							f.renameTo(newFile); // 执行重命名
							updateview(root);
						} catch (Exception e) {
							Toast.makeText(FileMangerActivity.this, "重名名失败...",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	void deletefile(File file) {
		final File f = file;
		new AlertDialog.Builder(this).setTitle("确认删除").setMessage("确定吗？")
				.setPositiveButton("是", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						try {
							deleteAllFiles(f);
							updateview(root);
						} catch (Exception e) {

							Toast.makeText(FileMangerActivity.this,
									"文件受保护，删除失败...", Toast.LENGTH_SHORT).show();
						}
					}
				}).setNegativeButton("否", null).show();
	}

	private void deleteAllFiles(File root) {
		if (root != null) {
			if (root.isDirectory()) {
				File[] filelist = root.listFiles();
				for (File file : filelist) {
					deleteAllFiles(file);
				}
				root.delete();
			} else {
				root.delete();
			}
		}
	}

	void adddirfile() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("请输入文件夹名称")
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						try {

							String string = editText.getText().toString();
							if (root.isDirectory()) {
								File file = new File(root.getPath() + "/"
										+ string);
								file.mkdirs();
								if (file.exists()) {
									Toast.makeText(FileMangerActivity.this,
											"新建文件夹成功", Toast.LENGTH_SHORT)
											.show();
									updateview(root);
								} else {
									Toast.makeText(FileMangerActivity.this,
											"新建文件夹失败", Toast.LENGTH_SHORT)
											.show();
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(FileMangerActivity.this, "新建文件夹失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				}).setNegativeButton("取消", null).show();
	}
}
