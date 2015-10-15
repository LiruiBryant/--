package com.lirui.biz;

import org.cocos2d.nodes.CCDirector;

import android.content.SharedPreferences;

public class LocalData {
/**
 * 
 * 保存数据到Shareprefenence种
 * @return
 */
	public static void savaSreToPref(String key,String value)
	{
	 //SharedPreferences sp=	CCDirector.theApp.getSharedPreferences("config", 0);
		
	 SharedPreferences sp= CCDirector.theApp.getPreferences(0);
	sp.edit().putString(key, value).commit();
	
	}
	/**
	 * 
	 * 获取数据
	 * @return
	 */
	public static String getStrFromPref(String key) {

		 SharedPreferences sp= CCDirector.theApp.getPreferences(0);
		 return sp.getString(key, "");
		 
	}
	/**
	 * 
	 * 
	 */

}
