package com.lirui.view;

import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

import com.lirui.biz.GameData;

public class HomeScene extends ModelLayer {

	public HomeScene()
	{
		setIsTouchEnabled(true);
		init();
		menu();
	}

	private void menu() {
		// TODO Auto-generated method stub
		CCMenu menu = CCMenu.menu();
		menu.setPosition(0,0);
		
		CCSprite nor = CCSprite.sprite("map_button_menu.png");
		CCSprite se = CCSprite.sprite("home_button.png");
		//菜单按钮
		
		CCMenuItem item  = CCMenuItemSprite.item(nor, se, this, "menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(winSize.width-70,10);
		menu.addChild(item,tagMenu_menu,tagMenu_menu);
		
		//白天
		nor = CCSprite.sprite("item_day.png");
		item = CCMenuItemSprite.item(nor, nor,this,"menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(80,450);
		menu.addChild(item,tagMenu_day,tagMenu_day);
		//黑夜
		nor = CCSprite.sprite("item_night.png");
		item = CCMenuItemSprite.item(nor, nor,this,"menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(250,450);
		menu.addChild(item,tagMenu_night,tagMenu_night);
		
		this.addChild(menu,tagMenu,tagMenu);
	}
	private int num = 0 ;
	public void  menuCall(Object o)
	{
		//点击事件
		//得到CCmenuitem
		CCMenuItem item = (CCMenuItem)o;
		int tag = item.getTag();
		switch (tag) {
		case tagMenu_menu:
			if(num%2==0){
			HelpLayer help = new HelpLayer();
			this.addChild(help,tagHelp,tagHelp);
			}
			else{
				this.removeChildByTag(tagHelp, true);

				
			}num++;
			break;
case tagMenu_day:

	GameData.GameType  = GameData.DAY;
	vh.changeScene(new SelectScene());
			break;
case tagMenu_night:
	
	GameData.GameType  = GameData.Night;
	vh.changeScene(new SelectScene());
	break;
		default:
			break;
		}
		//通过CCmenuitem的tag处理相应的事件s
	}

	private void init() {

		//加载背景
		
		
		this.addChild(vh.getSprite("pvz_select_survive_mode.png", ccp(0, 0),1.77f,1.5f),tagbf,tagbf);
		//底部背景
		this.addChild(vh.getSprite("bar.9.png", ccp(0, 0)),tagBottom,tagBottom);
		
		//头像

		this.addChild(vh.getSprite("wy_portrait_female_1.jpg", 
				ccp(10, 10)),tagIcon,tagIcon);
		
		//金钱
		this.addChild(vh.getText("金钱:"+GameData.GameMoney,
				ccp(150, 10), 25,ccc3(80, 120, 160)),tagMoney,tagMoney);
		
		//等级
		this.addChild(vh.getText("等级"+GameData.GameLevel, ccp(300, 10), 25,ccc3(80, 120, 160)),tagLevel,tagLevel);
		
		
	}
	
	
	
	private final int tagbf = 1;//背景
	private final int tagBottom = 4;//背景
	private final int tagIcon = 5;//背景
	private final int tagMoney= 10;//背景
	private final int tagLevel = 15;//背景
	private final int tagHelp = 19;//背景
	private final int tagMenu = 20;//背景
	private final int tagMenu_menu = 25;//背景
	private final int tagMenu_day = 30;//背景
	private final int tagMenu_night = 35;//背景
	

}
