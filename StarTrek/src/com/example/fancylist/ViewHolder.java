package com.example.fancylist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	ImageView icon = null;
	TextView label = null;
	
	ViewHolder (View base) {
		this.icon = (ImageView) base.findViewById(R.id.icon);
		this.label = (TextView) base.findViewById(R.id.label);
	}
	
	
}
