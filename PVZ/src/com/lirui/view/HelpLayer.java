package com.lirui.view;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;
import android.widget.Toast;

public class HelpLayer extends ModelLayer {

	public HelpLayer()
	{
		setIsTouchEnabled(true);
		init();
		
		this.setPosition(0,-150);
		//this.setScaleY(2);
		CCMoveBy by = CCMoveBy.action( 0.8f, ccp(0, 150));
		this.runAction(by);
	}

	private void init() {
		// TODO Auto-generated method stub
		CCSprite sp = CCSprite.sprite("seedbank.png");
		sp.setScaleX(0.8f);
		sp.setScaleY(0.5f);
		sp.setAnchorPoint(0,0);
		sp.setPosition(winSize.width-70,80);
		this.addChild(sp,tagbf,tagbf);
		
		
		CCSprite btn = CCSprite.sprite("reputation.png");
		btn.setAnchorPoint(0,0);
		btn.setPosition(sp.getContentSize().width/2,sp.getContentSize().height/2);
		sp.addChild(btn,tagBtn,tagBtn);


	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO Auto-generated method stub
		CGPoint aPoint = this.getChildByTag(tagbf).convertPrevTouchToNodeSpace(event);
		
		CCSprite sp = (CCSprite) this.getChildByTag(tagbf).getChildByTag(tagBtn);

		if(CGRect.containsPoint(sp.getBoundingBox(), aPoint))
		{
			CCDirector.theApp.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//在ui线程中进行
					HelpPop.show();
				}
			});
			
		}
		return super.ccTouchesEnded(event);
	}

	private final int tagbf = 1;//背景
	private final int tagBtn = 5;//背景
}
