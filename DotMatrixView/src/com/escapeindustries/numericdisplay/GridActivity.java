package com.escapeindustries.numericdisplay;

import com.escapeindustries.numericedisplay.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class GridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridhost);
		ViewGroup gridholder = (ViewGroup)findViewById(R.id.gridholder);
	}

}