package com.example.vortexjumper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import com.illustostudios.vortexjumper.R;

public class SplashPage extends Activity implements OnClickListener
{
	
	ImageButton logoButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splashpage);
		initialize();
		
		Animation anim1 = AnimationUtils.loadAnimation(SplashPage.this, R.anim.animation);
		logoButton.startAnimation(anim1);
	}
	
	private void initialize()
	{
		// TODO Auto-generated method stub
		logoButton = (ImageButton) findViewById(R.id.bLogo);
		logoButton.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		Intent splashIntent = new Intent("android.intent.action.MAINMENU");
		startActivity(splashIntent);
		
	}

}
