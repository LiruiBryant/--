package com.lirui.view;

import java.util.concurrent.ScheduledExecutorService;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import com.example.pvz.FailLayer;
import com.lirui.biz.GameData;
import com.lirui.biz.ViewSerivece;

import android.R;
import android.test.suitebuilder.TestSuiteBuilder.FailedToCreateTests;

public class LoginScene extends ModelLayer {

	//private CGSize winSize = CCDirector.sharedDirector().getWinSize();;
	public LoginScene()
	{
		GameData.init();
		init();
schedule("call",0.5f);
	}
	public void call(float t)
	{
		unschedule("call");//把call定时器销毁;
		FailLayer layer = new FailLayer();
		this.addChild(layer,tagFailPop,tagFailPop);
		
		vh.changeScene(new HomeScene());
	}

	private void init() {
		// 背景
		CCSprite bg = CCSprite.sprite("cover.jpg");
		bg.setScale(1.4f);
		bg.setScaleX(1.6f);
		bg.setAnchorPoint(0,0);
		this.addChild(bg,tagbf,tagbf);
		//累计天数
		CCSprite dayBg = CCSprite.sprite("well_detail.png");
		dayBg.setPosition(winSize.width/2,50);
		this.addChild(dayBg,tagDay,tagDay);
		
		CCLabel label = CCLabel.labelWithString("当前天数为"
				+ViewSerivece.getDays()+"天"
				, font, 30);
		label.setAnchorPoint(0,0);
		label.setPosition(15,25);
		dayBg.addChild(label,tagDayinfo,tagDayinfo);
		//进度条
		CCSprite bar = CCSprite.sprite("sc_publish_spin.png");
		bar.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(bar,tagProgress,tagProgress);
		//旋转
		CCRotateBy by =CCRotateBy.action(1, 180);
		bar.runAction(CCRepeatForever.action(by));
		
		//版本号
		 label = CCLabel.labelWithString("版本号"+ViewSerivece.getVersion(), font, 20);
		label.setAnchorPoint(0,0);
		label.setPosition(15,25);
		label.setColor(ccColor3B.ccBLUE);
		this.addChild(label,tagDayinfo,tagDayinfo);
		
	}
	
	private final int tagbf = 1;//背景
	private final int tagDay = 5;//背景
	private final int tagDayinfo= 10;//背景
	private final int tagProgress = 15;//背景
	private final int tagVersion = 20;//背景
	private final int tagFailPop = 25;//背景

	
}
