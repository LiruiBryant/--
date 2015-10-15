package com.lirui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;

import android.R.integer;
import android.view.MotionEvent;
import android.widget.Toast;

import com.lirui.biz.GameData;
import com.lirui.biz.ViewSerivece;



public class SelectScene extends ModelLayer {
//为绘制的植物更方便处理事件
	private ArrayList<CCSprite> spList = new ArrayList<CCSprite>();
//为选中的植物更好的处理事件
	private ArrayList<CCSprite> selectList = new ArrayList<CCSprite>();

	public SelectScene()
	{
		setIsTouchEnabled(true);
		init();
	}

	private void init() {

		//背景
		addChild(vh.getSprite("bk1.jpg", ccp(winSize.width-1274, 0),1.88f,2.17f),tagBg,tagBg);
		
		//顶部背景topbar
		CCSprite topbar = CCSprite.sprite("seedbank.png");
		topbar.setRotation(90);
		topbar.setScale(1.3);
		topbar.setAnchorPoint(0.5f,0.5f);
		topbar.setPosition(winSize.width/2,winSize.height-50);
		
		addChild(topbar,tagTopBar,tagTopBar);
		//放植物背景
		addChild(vh.getSprite("seedstore.png", ccp(winSize.width-1274, 0),1.2f,1.2f),tagPlantBg,tagPlantBg);

		//开始游戏
		addChild(vh.getSprite("button4.png", ccp(winSize.width-140, 0)),tagPlay,tagPlay);
	
		
		//等级
		addChild(vh.getText("等级"+GameData.GameLevel, ccp(15, winSize.height), 18),tagLevel,tagLevel);

		//绘制植物
		
		
		
		for(int i=0;i<5; i++){
			CCSprite sp = CCSprite.sprite(String.format("xx%02d.png", i+1));
			sp.setAnchorPoint(0,0);
			sp.setScale(2.3f);
			sp.setPosition(40+160*(i%3),350-240*(i/3));
			addChild(sp, tagPlant+i, tagPlant+i);
			spList.add(sp);
		}
	}
	
	//添加选中的植物
	private HashMap<Integer, Integer> selectMap  = new HashMap<Integer, Integer>();
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint point = this.convertTouchToNodeSpace(event);
		int i=1;
		for(CCSprite sp : spList){
			if(CGRect.containsPoint(sp.getBoundingBox(), point) 
					&& selectMap.get(sp.getTag())==null){
				CCSprite sprite = CCSprite.sprite(String.format("xx%02d.png", i));
				//sprite.setScale(0.5);
				sprite.setAnchorPoint(0,0);
				sprite.setPosition(sp.getPosition());
				this.addChild(sprite, tagSelectPlant+i, tagSelectPlant+i);
				
				
				//CCMoveTo to = CCMoveTo.action( 0.5f,ccp(400+selectMap.size()*70, winSize.height-100));
				
				CCMoveTo to = CCMoveTo.action(0.5f,ccp(400+selectMap.size()*70, winSize.height-100));
				sprite.runAction(to);
				
				selectList.add(sprite);
				GameData.selectPlantMap.put(tagSelectPlant+i, sp.getTag());
				selectMap.put(sp.getTag(), tagSelectPlant+i);
				return false;
			}
			i++;
		}
		//判断选中的植物是否被点击
		
		for(int j = 0 ; j < selectList.size();j++)
		{
			CCSprite selectSp = selectList.get(j);
			if(CGRect.containsPoint(selectSp.getBoundingBox(), point))
			{
				//移除本身
				selectSp.removeSelf();
				//selectList种对应的书库删除
				selectList.remove(j);
				//selectMap种对应的数据删除
				Iterator<Integer> it = selectMap.keySet().iterator();
				while (it.hasNext()) {
					int id = it.next();
					int tag = selectMap.get(id);
					if(tag == selectSp.getTag()){
						selectMap.remove(id);
						break;
						
					}
					
					
				}
				//GameData.selectplanMap种对应的数据删除
				GameData.selectPlantMap.remove(selectSp.getTag());
				
				//让处在右边的选中植物执行向左移动的动画
				for(int a = j ; a<selectList.size();a++)
				{
					CCSprite movesp = selectList.get(a);
					CCMoveBy by = CCMoveBy.action(1, ccp(-60, 0));
					movesp.runAction(by);
				}
				break;
			}
			
			
		}
		
		
		
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
CGPoint point = this.convertTouchToNodeSpace(event);
		CCSprite tagsp= (CCSprite) this.getChildByTag(tagPlay);
		if(CGRect.containsPoint(tagsp.getBoundingBox(), point))
		{
			vh.changeScene(new GameScene());
		}
		return super.ccTouchesEnded(event);
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesMoved(event);
	}



	private final int tagBg=1;
	private final int tagTopBar=14;
	private final int tagPlantBg=10;
	private final int tagLevel=15;
	private final int tagPlay=20;
	public final static int tagPlant=30;
	private final int tagselected=100;
	private final int tagSelectPlant=150;



}
