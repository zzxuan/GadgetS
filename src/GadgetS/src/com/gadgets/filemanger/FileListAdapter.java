package com.gadgets.filemanger;

import java.io.File;
import java.util.ArrayList;

import com.gadgets.collection.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	Context context;
	ArrayList<File> files;

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
		notifyDataSetChanged();
	}

	public FileListAdapter(Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
		files = new ArrayList<File>();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		// convertView为null的时候初始化convertView。
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.flielistitem, null);
			holder.img = (ImageView) convertView
					.findViewById(R.id.fileimageView);
			holder.filename = (TextView) convertView
					.findViewById(R.id.filenametextView);
			holder.filepath = (TextView) convertView
					.findViewById(R.id.filepathtextView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		File file = files.get(arg0);
		if (file.isDirectory()) {
			holder.img.setBackgroundResource(R.drawable.folderimg);
		} else {
			holder.img.setBackgroundResource(R.drawable.fileimg);
		}

		holder.filename.setText(file.getName());
		holder.filepath.setText(file.getPath());
		return convertView;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView filename;
		public TextView filepath;
	}
}
