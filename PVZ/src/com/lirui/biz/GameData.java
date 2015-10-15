package com.lirui.biz;

import java.util.ArrayList;
import java.util.HashMap;

import com.lirui.view.Tower;

import android.R.integer;

public class GameData {

	public static  int GameMoney;
	public static  int GameLevel;
	public static final int DAY = 1;
	public static final int Night =2;
	public static int GameType =0;
	public static final int Sun =1;//向日葵类型
	public static final int Plant =2;//豌豆类型
	public static final int Npc = 3;
	public static int TowerType= 0;
	public static ArrayList<com.lirui.view.Sun> sunList;
	
	public static HashMap<Integer, com.lirui.view.Npc> npcMap;

	public static HashMap<Integer, Integer> selectPlantMap;
	public static HashMap<Integer, Tower> towerMap;


	/**
	 * 游戏初始化
	 * 
	 */
	public static void init() {
		//连接网络 
		
		
		//加载图片
		
		//初始化数据库
		
		//其他
		
		GameMoney = 500;
		GameLevel = 1;
		selectPlantMap = new HashMap<Integer, Integer>();
		sunList = new ArrayList<com.lirui.view.Sun>();
		npcMap = new HashMap<Integer, com.lirui.view.Npc>();
		towerMap =new HashMap<Integer, Tower>();
	}

}
