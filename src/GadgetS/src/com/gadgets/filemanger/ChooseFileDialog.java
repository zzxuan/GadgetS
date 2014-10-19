package com.gadgets.filemanger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.gadgets.collection.R;

import android.R.string;
import android.animation.AnimatorSet.Builder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseFileDialog {
	Context context;
	File rootFile;
	File rootnow;
	CompleteChoose completeChoose;

	public interface CompleteChoose {
		void finalfile(File dirFile);
	}

	public ChooseFileDialog(Context context, File rootFile,
			CompleteChoose completeChoose) {
		super();
		this.context = context;
		this.rootFile = rootFile;
		rootnow = rootFile;
		this.completeChoose = completeChoose;
		LayoutInflater inflater = LayoutInflater.from(context);
		innerView = inflater.inflate(R.layout.choosefile, null);
		listView = (ListView) innerView.findViewById(R.id.choosefilelistView);
		textView = (TextView) innerView.findViewById(R.id.choosetextView);

	}



	public void Show() {
		showview();
	}

	android.app.AlertDialog.Builder builder;

	View innerView;
	TextView textView;
	ListView listView;

	void showview() {
		builder = new AlertDialog.Builder(context).setTitle("选择目录")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if(completeChoose!=null)
						{
							completeChoose.finalfile(rootnow);
						}
					}
				}).setNegativeButton("取消", null);
		// builder.setItems(getItemStrings(rootnow), this);
		builder.setView(innerView);
		builder.show();
		textView.setText(rootnow.getPath());
		listView.setAdapter((new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, getItemStrings(rootnow))));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				rootnow = filelist.get(arg2);
				listView.setAdapter((new ArrayAdapter<String>(context,
						android.R.layout.simple_list_item_1,
						getItemStrings(rootnow))));
				textView.setText(rootnow.getPath());
			}
		});
	}

	List<File> filelist = new ArrayList<File>();
	List<String> filelistStrings = new ArrayList<String>();

	String[] getItemStrings(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				filelist.clear();
				filelistStrings.clear();
				for (File f : files) {
					if (f.isDirectory()) {
						filelist.add(f);
						filelistStrings.add(f.getName());
					}
				}
				String[] ss = (String[]) filelistStrings
						.toArray(new String[filelistStrings.size()]);
				return ss;
			}
		}
		return new String[] {};
	}

}
