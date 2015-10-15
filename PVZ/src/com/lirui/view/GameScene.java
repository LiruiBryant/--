package com.lirui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.R.bool;
import android.text.format.Time;
import android.transition.ChangeClipBounds;
import android.view.MotionEvent;
import android.widget.VideoView;

import com.lirui.biz.GameData;

public class GameScene extends ModelLayer
{
	private ArrayList<CCSprite> touchList = new ArrayList<CCSprite>();
   private CGSize mapSize;
	private CCTMXTiledMap tiladMap;
	private ArrayList<CGPoint> roadList = new ArrayList<CGPoint>();
	public GameScene()
	{
		setIsTouchEnabled(true);
		initMap();
		parseMap();
		initView();
		
		//僵尸机制，一波8个,完全消失才继续下一波
		schedule("createNpc",4);
		
	}
	private CGPoint middlePoint;
	
	
	public void createNpc(float f)
	{
		unschedule("createNpc");
		schedule("createNpcItem",2);
		
		
	}int Npcid = 0;
	private boolean isNext = false;
	public void createNpcItem(float f)
	{
		if(!isNext &&GameData.npcMap.size()<8){
			ArrayList<CGPoint> road = new ArrayList<CGPoint>();
			road.add(roadList.get(0));
			int i = vh.random(1,5);
			
			road.add(roadList.get(i*2-1));
			road.add(roadList.get(i*2));
			
			Npc npc = Npc.createNpc("z_1_01.png",Npcid,road);
			
			GameData.npcMap.put(Npcid, npc);
			tiladMap.addChild(npc,tagNpc-Npcid,tagNpc-Npcid);
			
		}
		else{isNext = true;}
		if(GameData.npcMap.size() == 0)
		{
			isNext = false;
		}
		Npcid++;
	}

	private boolean isTouPlant = false;
	private CCSprite touchSprite;//点击植物新创建的饿 
	private String name;
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
CGPoint point  = this.convertTouchToNodeSpace(event);
		for(CCSprite sp:touchList)
		{
			if(CGRect.containsPoint(sp.getBoundingBox(), point)){
				 name= "";
				if(sp.getTag()==TagSun)//向日葵
				{
					name = "p_1_01.png";
					GameData.TowerType=GameData.Sun;
				}
				else {
					//豌豆
					name = "p_2_01.png";
					GameData.TowerType=GameData.Plant;

				}	
				touchSprite=CCSprite.sprite(name);

				touchSprite.setPosition(sp.getPosition());
				this.addChild(touchSprite,tagTouchPlant,tagTouchPlant);
				isTouPlant = true;
				break;

			}
		}
		
		CGPoint mappoint  = tiladMap.convertTouchToNodeSpace(event);
		//销毁阳光
		
		for(int i = GameData.sunList.size()-1 ; i >=0;i--)
		{
			Sun sun = GameData.sunList.get(i);
			if(sun.isDestory())
			{
				GameData.sunList.remove(i);
			}
		}
		
		//处理阳光点击
		for(Sun sun : GameData.sunList)
		{
			if(CGRect.containsPoint(sun.getBoundingBox(), mappoint))
			{
				CGPoint endpoint = ccp(250, winSize.height-30);
				sun.move(endpoint);
			}
		}
		
		
		
		
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// 手指离开屏幕之前手点的位置有图片;该位置能创建植物
		CGPoint mapPoint = tiladMap.convertTouchToNodeSpace(event);
		
		if(isTouPlant&&isCanCreatePlant(mapPoint)&&
				!GameData.towerMap.containsKey(Plantid)
				&&(GameData.TowerType == GameData.Sun && GameData.GameMoney >=50)||
				(GameData.TowerType == GameData.Plant && GameData.GameMoney >=100)){
						
			int life =0;
			if(GameData.TowerType == GameData.Sun){
				life =100;
				GameData.GameMoney-=50;
			}else{
				life =200;
				GameData.GameMoney-=100;

			}
			refreshMoney();
			
			Tower tower =Tower.createTower(name, Plantid, life, GameData.TowerType);
			tower.setPosition(mapPoint);
			
			GameData.towerMap.put(Plantid, tower);
			tiladMap.addChild(tower);
			//plantIdMap.put(Plantid, Plantid);
			
		}
		if(isTouPlant){
		isTouPlant = false;
		this.removeChildByTag(tagTouchPlant, true);

		}
		return super.ccTouchesEnded(event);
	}
	/**
	 * 刷新金钱
	 */
public  void refreshMoney() {
		// TODO Auto-generated method stub
		CCLabel money2 = (CCLabel) this.getChildByTag(TagTopBar).getChildByTag(TagMoney);
		money2.setString(String.valueOf(GameData.GameMoney));
	}

private int Plantid;
//保存该图块已经放了植物de id 
//private HashMap<Integer, Integer> plantIdMap = new HashMap<Integer, Integer>();
	private boolean isCanCreatePlant( CGPoint mapPoint) {
		// TODO Auto-generated method stub
		//得到行和列
		int x =(int)(mapPoint.x/(tiladMap.getTileSize().width));
		int y =(int)((mapSize.height-mapPoint.y)/(tiladMap.getTileSize().height));

		middlePoint = ccp(x*(tiladMap.getTileSize().width)+20, 
				mapSize.height-y*(tiladMap.getTileSize().height)-5);
		
		CCTMXLayer layer = tiladMap.layerNamed("块层 1");
		Plantid  =layer.tileGIDAt(ccp(x, y));
		HashMap<String, String> map =tiladMap.propertiesForGID(Plantid);
		if(map!=null&&map.get("buildable")!=null)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
CGPoint point  = this.convertTouchToNodeSpace(event);
		if(isTouPlant==true){//点中植物进行拖动
			//店中了植物，进行对新创建的植物记性拖动
			touchSprite.setPosition(point);
			
			
		}else{}
		//tiladMap.touchMove(event, tiladMap);
		
		
		
		
		
		return super.ccTouchesMoved(event);
	}

	private void initView() {
		// 顶部栏
		CCSprite topBar = CCSprite.sprite("sdbank.png");
		topBar.setScale(1.5f);
		//topBar.setRotation(90);
		topBar.setAnchorPoint(0.5f,0.5f);
		topBar.setPosition(winSize.width/2,winSize.height-100);
		
		this.addChild(topBar,TagTopBar,TagTopBar);
		//金钱
		CCLabel money = CCLabel.labelWithString(String.valueOf(GameData.GameMoney), font, 16);
		money.setAnchorPoint(0,0);
		money.setPosition(15,5);
		money.setColor(ccColor3B.ccRED);
		topBar.addChild(money,TagMoney,TagMoney);
		
		//植物
		Iterator<Integer> it = GameData.selectPlantMap.keySet().iterator();
		while (it.hasNext()) {
int key = it.next();
switch (GameData.selectPlantMap.get(key)) {
case SelectScene.tagPlant://向日葵
	
	CCSprite sun = CCSprite.sprite("seed_flower.png");
	sun.setAnchorPoint(0,0);
	sun.setScale(0.7f);
	sun.setPosition(450,winSize.height-140);
	touchList.add(sun);
	this.addChild(sun,TagSun,TagSun);
	break;
case SelectScene.tagPlant+1://豌豆
	
	CCSprite plant = CCSprite.sprite("seed_pea.png");
plant.setAnchorPoint(0,0);
plant.setScale(0.7f);
plant.setPosition(580,winSize.height-140);
touchList.add(plant);

this.addChild(plant,TagPlant,TagPlant);

	break;

default:
	break;
}
		}
		
	}

	private void parseMap() {
		// TODO Auto-generated method stub
		CCTMXObjectGroup group= tiladMap.objectGroupNamed("road01");
		if(group==null)return ;
	ArrayList<HashMap<String, String>> array = 	group.objects;
	for(HashMap<String, String> map : array )
	{
		String key = "x";
		int x = Integer.parseInt(map.get(key));
		key = "y";
		int y = Integer.parseInt(map.get(key));
		roadList.add(ccp(x, y));
	}
	}

	private void initMap() {
		if(GameData.GameType == GameData.DAY){
		tiladMap = CCTMXTiledMap.tiledMap("itcast_map_day.tmx");
		}
		else {
			tiladMap = CCTMXTiledMap.tiledMap("itcast_map_night.tmx");
		}
		mapSize = tiladMap.getContentSize();
		tiladMap.setAnchorPoint(0.5f,0.5f);
		tiladMap.setScale(2.175f);
		tiladMap.setScaleX(3f);
		tiladMap.setPosition(mapSize.width/2,winSize.height/2);
		
		CCMoveBy by =  CCMoveBy.action(1, ccp(winSize.width-mapSize.width,0));
		CCDelayTime delay = CCDelayTime.action(2);
		CCSequence seq =CCSequence.actions(delay,by );
		tiladMap.runAction(seq);
		
		this.addChild(tiladMap,TagMap,TagMap);

		
		
	}

	
	private final int TagMap = 1;
	private final int TagTopBar = 10;
	private final int TagSun = 20;
	private final int TagPlant = 30;
	private final int TagMoney = 40;
	private final int tagTouchPlant = 100;
private final int tagNpc = 200;
	
	
}
