package com.lirui.view;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

public class Bullet extends ModelSprite {

	private int hurrt = 25;
	private Npc npc;
	
	public Bullet(String name,Npc npc) {
		super(name);
		this.npc = npc;
		this.setScale(0.5f);
		float t = ccpDistance(this.getPosition(), npc.getPosition())/350;
		
		CGPoint p = npc.getPosition();
		//子弹轨迹
		CCMoveTo to = CCMoveTo.action(t, ccp(p.x-20, p.y+5));
		CCCallFunc fun = CCCallFunc.action(this, "call");
		this.runAction(CCSequence.actions(to,fun));
	}
public void call()
{
	 		int life  = npc.getlife()-hurrt;
	 		if(life<=0)
	 		{
	 			npc.destroy();
	 		}else {
	 			npc.setLife(life);
	 		}
	 		this.removeSelf();
}
	

	public static Bullet createBullet(String name,Npc npc) {

		Bullet bullet = new Bullet(name,npc);
		
		return bullet;
	}

}
