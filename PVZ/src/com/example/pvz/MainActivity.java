package com.example.pvz;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.lirui.view.LoginScene;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.os.Build;

public class MainActivity extends Activity  {

	private CCDirector d = CCDirector.sharedDirector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        CCGLSurfaceView view   = new CCGLSurfaceView(this);
        setContentView(view);
        d.attachInView(view);
        d.setDisplayFPS(true);
        d.setAnimationInterval(1.0f/60);
        d.setDeviceOrientation(d.kCCDeviceOrientationPortrait);
        //d.setScreenSize(480, 320);
        d.setScreenSize(1280,720);
        CCScene scene = CCScene.node();
        scene.addChild(new LoginScene());
        d.runWithScene(scene);

        
        
        
       
    }
    
    


    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	d.end();
		super.onDestroy();
	}




	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		d.pause();
		super.onPause();
	}




	@Override
	protected void onResume() {
d.resume();
		super.onResume();
	}




	

}
