package com.lirui.view;

import org.cocos2d.actions.grid.CCLens3D;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.CGPoint;

import com.lirui.biz.GameData;

public class Sun extends ModelSprite {

	private int money = 25;
	private boolean isDestroy  = false;
	
	private  Sun(String name)
	{
		super(name);
		this.setScale(0.5);
		schedule("call",4);
	}
	
	public void call(float f)
	{
		unschedule("call");
		CCMoveBy by = CCMoveBy.action(1.5f, ccp(0, -500));
		this.runAction(by);
		
		isDestroy = true;
	}
	
	public static Sun createSun(String name)
	{
		Sun sun  = new Sun(name);
		return sun;
	}
	
	public void move(CGPoint endpoint)
	{
		if(isDestroy)return;
		
		CCMoveTo to = CCMoveTo.action(0.5f, endpoint);
		CCCallFunc fun = CCCallFunc.action(this, "moveCall");
		this.runAction(CCSequence.actions(to,fun));
	}
	public void moveCall()
	{
		GameData.GameMoney += 25;
		GameScene gamescene= (GameScene) this.getParent().getParent();
		gamescene.refreshMoney();
		isDestroy=true;
		//this.removeSelf();
		this.runAction(CCHide.action());
		}
	
	public boolean isDestory()
	{
		return isDestroy;
	}
	
	
}
