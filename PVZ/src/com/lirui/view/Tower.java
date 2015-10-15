package com.lirui.view;

import java.util.Iterator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;

import com.lirui.biz.GameData;

public class Tower extends ModelSprite {

	private int id ;
	private int life;
	private int type;//向日葵还是豌豆
	private  boolean isDestory = false;
	
	
	public Tower(String name,int type)
	{
		super(name);
		//判断是否是向日葵
		this.setScale(0.5f);
		if(type == GameData.Sun)
		{//5秒钟创建一个阳光
			schedule("buildSun",5);
		}else{
			schedule("buildBullet",1.5f);

			
		}
		
		//序列帧动画
		CCAnimation anim = CCAnimation.animation("",0.2f, vh.getSpriteArray(type));
		CCAnimate a =CCAnimate.action(anim);
		this.runAction(CCRepeatForever.action(a));
		
	}
	public static Tower createTower(String name,int id,int life,int type)
	{
		Tower tower = new Tower(name,type);
		tower.id=id;
		tower.life=life;
		
		return tower;
	}
	/**
	 * 生产阳光
	 * s
	 */
	public void buildSun(float f)
	{
		Sun sun = Sun.createSun("sun.png");
		sun.setPosition(this.getPosition().x+40,this.getPosition().y-30);
		GameData.sunList.add(sun);
		this.getParent().addChild(sun);
		
		
		
		
	}
	/**
	 * 
	 * 生产子弹
	 */
	public void buildBullet(float f)
	{
		if(GameData.npcMap.size()!=0)
		{
			Iterator< Integer> it = GameData.npcMap.keySet().iterator();
			while(it.hasNext())
			{
				int key = it.next();
				Npc  npc = GameData.npcMap.get(key);
			//控制子弹创建的条件
		if(npc.getPosition().x - this.getPosition().x>150)return;
		String name;
		if(vh.random(1, 2)==1)
		{
			name = "bullet.png";
		}
		else{name = "bullet_1.png";}
		
		
		Bullet bullet = Bullet.createBullet(name,npc);
		bullet.setAnchorPoint(0,0);
		bullet.setPosition(getPosition().x+5,getPosition().y+5);
		
		this.getParent().addChild(bullet);
		break;
	}}
	}
	public boolean isdestory()
	{
		
		return isDestory;
	}
	public void setlife(int life)
	{
		this.life =life;
	}
	public int getlife() {
		// TODO Auto-generated method stub
		return this.life;
	}
	public void destroy() {		
		this.runAction(CCHide.action());
		isDestory = true;
		GameData.towerMap.remove(id);
		schedule("remove",3);
	}
	public void remove(float f)
	{
		unschedule("remove");
		this.removeSelf();
	}
	
	
}
