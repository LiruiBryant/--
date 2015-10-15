package com.lirui.view;

import org.cocos2d.nodes.CCDirector;

import com.example.pvz.R;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class HelpPop {

	/**
	 * 帮助界面
	 */
	public static void show()
	{
		final Dialog d = ViewHelp.getDialog();
		d.setContentView(R.layout.help);
		d.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.dismiss();
			}
		});
	d.show();
	}
}
