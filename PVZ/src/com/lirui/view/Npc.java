package com.lirui.view;

import java.util.ArrayList;
import java.util.Iterator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.types.CGPoint;

import com.lirui.biz.GameData;

public class Npc extends ModelSprite {

	private ArrayList<CGPoint> roadPointArray;
	private int id ;
	private int life = 100;
	private int hurrt = 30;
	private boolean isDestroy = false;
	
	private  Npc(String name,ArrayList<CGPoint> roadPointArray)
	{
		super(name);
		this.roadPointArray = roadPointArray;
		this.setScale(0.5f);
		
		this.setAnchorPoint(0,0);
		this.setPosition(roadPointArray.get(0));
		
		walk();
		schedule("refresh",0.5f);
	}
	/**
	 * 检测所在的图片id是否有植物，有的话就攻击
	 * @param f
	 */
	private int n =0;
	public void refresh(float f)
	{
//		if(n==3){
//			//销毁可以小会的植物
//		Iterator<Integer> it = GameData.towerMap.keySet().iterator();
//		int key =it.next();
//		while (it.hasNext()) {
//			Tower tower = GameData.towerMap.get(it.next());
//			if(tower.isdestory())
//			{
//				GameData.towerMap.remove(key);
//				tower.removeSelf();
//			}
//		}
//		}n++;
		//拿到植物id
		CCTMXTiledMap tiladMap = (CCTMXTiledMap) this.getParent();
		int x =(int)(this.getPosition().x/(tiladMap.getTileSize().width));
		int y =(int)((tiladMap.getContentSize().height-this.getPosition().y)
				/(tiladMap.getTileSize().height));

		CCTMXLayer layer = tiladMap.layerNamed("块层 1");
		int tiledId=layer.tileGIDAt(ccp(x, y));
		//如果npc所在的图块有z植物
		if(GameData.towerMap.containsKey(tiledId))
		{
			this.stopAction(seq);
			 Tower tower = GameData.towerMap.get(tiledId);
			 int life = tower.getlife()-hurrt;
			 if(life <=0)
			 {
				 tower.destroy();
				 walk();
			 }else{
				 tower.setlife(life);
			 }
		}
		
	}
	
	
	public static Npc createNpc(String name,int id,ArrayList<CGPoint> roadPointArray)
	{
		Npc npc = new Npc(name,roadPointArray);
		npc.id = id;
		return npc;
	}
	private int num =1;
	/**
	 * 行走功能
	 */
	private CCSequence seq;
	public void walk()
	{
		float t = ccpDistance(this.getPosition(), roadPointArray.get(num))/40;
		CCMoveTo to=CCMoveTo.action(t, roadPointArray.get(num));
		CCCallFunc fun = CCCallFunc.action(this, "walkCall");
		seq =CCSequence.actions(to, fun);
		this.runAction(seq);	
		
		CCAnimation anim = CCAnimation.animation(" ",0.2f, vh.getSpriteArray(GameData.Npc));
		CCAnimate a =CCAnimate.action(anim);
		this.runAction(CCRepeatForever.action(a));
	}
	
	public void walkCall()
	{
		stopAllActions();
		if(num == 1)
		{
			num++;
			walk();
		}else{destroy();}
	}
	/**
	 * 销毁
	 */
	public void destroy() {
GameData.npcMap.remove(id);
		this.removeSelf();
	}
	public int getlife() {
		return this.life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
	
	
}
