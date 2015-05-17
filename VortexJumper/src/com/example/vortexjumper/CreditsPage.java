package com.example.vortexjumper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.illustostudios.vortexjumper.R;

public class CreditsPage extends Activity implements OnClickListener
{
	ImageButton illustroStudios;
	ImageButton BenSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creditepage);

		illustroStudios = (ImageButton) findViewById(R.id.ibIllustroStudios);
		BenSound = (ImageButton) findViewById(R.id.ibBenSound);

		illustroStudios.setOnClickListener(this);
		BenSound.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case(R.id.ibIllustroStudios):
				
				Intent illustroStudiosIntent = new Intent(Intent.ACTION_VIEW);
				illustroStudiosIntent.setData(Uri.parse("http://www.illustrostudios.com"));
				startActivity(illustroStudiosIntent);
							
				break;
			
			case(R.id.ibBenSound):
				
				Intent BenSoundIntent = new Intent(Intent.ACTION_VIEW);
				BenSoundIntent.setData(Uri.parse("http://www.bensound.com"));
				startActivity(BenSoundIntent);
				
				break;
		}

	}
}
