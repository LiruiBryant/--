package com.example.pvz;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import com.lirui.biz.ViewSerivece;
import com.lirui.view.ModelLayer;

/**
 * 
 * 失败的弹窗
 * @author lirui
 *
 */
public class FailLayer extends ModelLayer {

	public FailLayer()
	{
		setIsTouchEnabled(true);
		init();
	}

	private void init() {

		CCSprite sprite  = CCSprite.sprite("sub_video_bg.png");
		sprite.setPosition(winSize.width/2,winSize.height/2);
		this.addChild(sprite,tagBg,tagBg);
		//提示
		
		
		CCLabel label = CCLabel.labelWithString("连接失败，请重试", font, 20);
			
			label.setPosition(winSize.width/2,winSize.height/2+30);
			label.setColor(ccColor3B.ccBLUE);
			this.addChild(label,tagInfo,tagInfo);
			
			//退出按钮
			
			CCSprite btn= CCSprite.sprite("menu_bn_quit.png");
			btn.setPosition(winSize.width/2,winSize.height/2-40);		
			this.addChild(btn,tagtagBtn,tagtagBtn);
		
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {

		CGPoint apoint  = this.convertPrevTouchToNodeSpace(event);
		CCSprite sp = (CCSprite) this.getChildByTag(tagtagBtn);
		if(CGRect.containsPoint(sp.getBoundingBox(), apoint)){
			CCSprite btn= CCSprite.sprite("menu_bn_quit2.png");
			btn.setPosition(winSize.width/2,winSize.height/2-25);		
			this.addChild(btn,tagtagBtns,tagtagBtns);
		}
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint apoint  = this.convertPrevTouchToNodeSpace(event);
		CCSprite sp = (CCSprite) this.getChildByTag(tagtagBtn);
		if(CGRect.containsPoint(sp.getBoundingBox(), apoint)){
		
		CCDirector.theApp.finish();
		}
		
		
		return super.ccTouchesEnded(event);
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesMoved(event);
	}

	private final int tagBg = 1;
	private final int tagInfo= 5;
	private final int tagtagBtn = 10;
	private final int tagtagBtns = 15;

}
