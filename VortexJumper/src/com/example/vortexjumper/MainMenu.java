package com.example.vortexjumper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.illustostudios.vortexjumper.R;

public class MainMenu extends Activity implements OnClickListener
{

	ImageButton NewGame;
	ImageButton Credits; // Credits
	ImageView GameLogo;
	MediaPlayer introSong;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mainmenu);
		initialize();

		Animation mainMenuAnimation1 = AnimationUtils.loadAnimation(MainMenu.this, R.anim.mainmenu_animation);
		Animation mainMenuAnimation2 = AnimationUtils.loadAnimation(MainMenu.this, R.anim.mainmenu_animation2);
		Animation mainMenuAnimationGameLogo = AnimationUtils.loadAnimation(MainMenu.this, R.anim.mainmenu_animation_game_logo);

		NewGame.startAnimation(mainMenuAnimation1);
		Credits.startAnimation(mainMenuAnimation2);
		GameLogo.startAnimation(mainMenuAnimationGameLogo);
		introSong = MediaPlayer.create(this, R.raw.bensound_epic);
		introSong.start();
	}

	private void initialize()
	{

		NewGame = (ImageButton) findViewById(R.id.ibNewGame);
		Credits = (ImageButton) findViewById(R.id.ibOptions);
		GameLogo = (ImageView) findViewById(R.id.ivGameLogo);

		NewGame.setOnClickListener(this);
		Credits.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case (R.id.ibNewGame):

				Intent NewGameIntent = new Intent("android.intent.action.GAMEACTIVITYV2");
				startActivity(NewGameIntent);
				introSong.release();

				break;

			case (R.id.ibOptions):

				Intent CreditsPageIntent = new Intent("android.intent.action.CREDITSPAGE");
				startActivity(CreditsPageIntent);
				introSong.release();

				break;
		}

	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		introSong.release();

	}
}
