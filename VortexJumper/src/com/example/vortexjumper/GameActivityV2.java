package com.example.vortexjumper;

import java.io.IOException;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.illustostudios.vortexjumper.R;

public class GameActivityV2 extends BaseGameActivity implements
		IAccelerationListener, IOnSceneTouchListener
{

	// ====================================================
	// CONSTANTS
	// ====================================================

	public int CameraWidth;
	public int CameraHeight;

	// ====================================================
	// VARIABLES
	// ====================================================
	public Scene mScene;
	public FixedStepPhysicsWorld mPhysicsWorld;

	private Camera mCamera;
	Boolean hitCondition = false;
	Body LineJointBodyA;
	Body LineJointBodyB;

	// ====================================================
	// BACKGROUND
	// ====================================================

	private ITexture mColouredBackgroundTexture;
	private RepeatingSpriteBackground mRepSpriteColouredBackground; // mainBackground
	ITextureRegion colouredBackgroundITextureRegion;

	private ITexture mColouredBackgroundTexture2;
	private RepeatingSpriteBackground mRepSpriteColouredBackground2; // mainBackground
	ITextureRegion colouredBackgroundITextureRegion2;

	private ITexture mColouredBackgroundTexture3;
	private RepeatingSpriteBackground mRepSpriteColouredBackground3; // mainBackground
	ITextureRegion colouredBackgroundITextureRegion3;

	private ITexture mColouredBackgroundTexture4;
	private RepeatingSpriteBackground mRepSpriteColouredBackground4; // mainBackground
	ITextureRegion colouredBackgroundITextureRegion4;

	private ITexture mColouredBackgroundTexture5;
	private RepeatingSpriteBackground mRepSpriteColouredBackground5; // mainBackground
	ITextureRegion colouredBackgroundITextureRegion5;

	// ====================================================
	// SHIP
	// ====================================================

	TiledTextureRegion shipTextureRegion;
	BuildableBitmapTextureAtlas shipBldBitmapTextureAtlas;
	Ship shipObj;
	final FixtureDef shipFixtureDef = PhysicsFactory.createFixtureDef(1f, 0f, 1f, false);

	// ====================================================
	// ENEMY
	// ====================================================

	Enemy enemyObj1;

	TiledTextureRegion enemyTextureRegion;
	BuildableBitmapTextureAtlas enemyBldBitmapTextureAtlas;
	Enemy enemySpriteObj1;

	TiledTextureRegion enemyTextureRegion2;
	BuildableBitmapTextureAtlas enemyBldBitmapTextureAtlas2;
	Enemy enemySpriteObj2;

	final FixtureDef enemyFixtureDef = PhysicsFactory.createFixtureDef(20f, 0.2f, 0.9f);
	final FixtureDef enemyFixtureDef2 = PhysicsFactory.createFixtureDef(20f, 0.2f, 0.9f);

	// ====================================================
	// TARGET
	// ====================================================

	private ITexture TargetITexture;
	private TiledTextureRegion TargetTextureRegion;
	Body Targetbody;
	Target objTarget;

	// ====================================================
	// ENEMYX
	// ====================================================

	private ITexture EnemyXITexture;
	private TiledTextureRegion EnemyXTextureRegion;
	Body EnemyXbody;
	EnemyX objEnemyX;

	// ====================================================
	// OBSTACLES
	// ====================================================

	private ITexture obstacleBASEITexture;
	private TiledTextureRegion obstacleBASETextureRegion;
	Obstacles obstacleBASEobj;

	private ITexture obstacleCRANEITexture;
	private TiledTextureRegion obstacleCRANETextureRegion;
	Obstacles obstacleCRANEobj;

	Obstacles obstacles2X;

	Obstacles obstacles3X;
	Obstacles obstacles3Y;

	Obstacles obstacles4X;
	Obstacles obstacles4Y;
	Obstacles obstacles4Z;

	Obstacles obstacles5X;
	Obstacles obstacles5Y;
	Obstacles obstacles5Z;

	Obstacles obstacles6X;
	Obstacles obstacles6Y;
	Obstacles obstacles6Z;

	Obstacles obstacles7W;
	Obstacles obstacles7X;
	Obstacles obstacles7Y;
	Obstacles obstacles7Z;

	Obstacles obstacles8X;
	Obstacles obstacles8Y;
	Obstacles obstacles8Z;
	Obstacles obstacles8A;
	Obstacles obstacles8B;

	Obstacles obstacles9X;
	Obstacles obstacles9Y;
	Obstacles obstacles9Z;
	Obstacles obstacles9A;
	Obstacles obstacles9B;
	Obstacles obstacles9C;

	int currentLevel;

	// ====================================================
	// MISC
	// ====================================================

	int counterStage;
	int counterEnemy;
	int life;

	Sound CrashTarget;
	MediaPlayer bgMusic;

	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions)
	{
		Engine mEngine = new FixedStepEngine(pEngineOptions, 60);
		return mEngine;
	}

	// ====================================================
	// CREATE ENGINE OPTIONS
	// ====================================================
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		CameraWidth = displayMetrics.widthPixels;
		CameraHeight = displayMetrics.heightPixels;
		mCamera = new Camera(0, 0, CameraWidth, CameraHeight);

		//EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RelativeResolutionPolicy(CameraWidth, CameraHeight), mCamera);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getAudioOptions().setNeedsSound(true);

		return engineOptions;
	}

	// ====================================================
	// CREATE RESOURCES
	// ====================================================
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{

		SoundFactory.setAssetBasePath("sfx/");
		try
		{
			CrashTarget = SoundFactory.createSoundFromAsset(this.getSoundManager(), this, "target.wav");
		}
		catch (final IOException e)
		{
			Debug.e(e);
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// BACKGROUND
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mColouredBackgroundTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/level1Rev5.png", TextureOptions.REPEATING_NEAREST);
		this.mColouredBackgroundTexture.load();
		colouredBackgroundITextureRegion = TextureRegionFactory.extractFromTexture(this.mColouredBackgroundTexture);
		mRepSpriteColouredBackground = new RepeatingSpriteBackground(CameraWidth, CameraHeight, colouredBackgroundITextureRegion, this.getVertexBufferObjectManager());

		this.mColouredBackgroundTexture2 = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/levelA_bg.png", TextureOptions.REPEATING_NEAREST);
		this.mColouredBackgroundTexture2.load();
		colouredBackgroundITextureRegion2 = TextureRegionFactory.extractFromTexture(this.mColouredBackgroundTexture2);
		mRepSpriteColouredBackground2 = new RepeatingSpriteBackground(CameraWidth, CameraHeight, colouredBackgroundITextureRegion2, this.getVertexBufferObjectManager());

		this.mColouredBackgroundTexture3 = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/levelB_bg.png", TextureOptions.REPEATING_NEAREST);
		this.mColouredBackgroundTexture3.load();
		colouredBackgroundITextureRegion3 = TextureRegionFactory.extractFromTexture(this.mColouredBackgroundTexture3);
		mRepSpriteColouredBackground3 = new RepeatingSpriteBackground(CameraWidth, CameraHeight, colouredBackgroundITextureRegion3, this.getVertexBufferObjectManager());

		this.mColouredBackgroundTexture4 = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/levelC_bg.png", TextureOptions.REPEATING_NEAREST);
		this.mColouredBackgroundTexture4.load();
		colouredBackgroundITextureRegion4 = TextureRegionFactory.extractFromTexture(this.mColouredBackgroundTexture4);
		mRepSpriteColouredBackground4 = new RepeatingSpriteBackground(CameraWidth, CameraHeight, colouredBackgroundITextureRegion4, this.getVertexBufferObjectManager());

		this.mColouredBackgroundTexture5 = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/levelD_bg.png", TextureOptions.REPEATING_NEAREST);
		this.mColouredBackgroundTexture5.load();
		colouredBackgroundITextureRegion5 = TextureRegionFactory.extractFromTexture(this.mColouredBackgroundTexture5);
		mRepSpriteColouredBackground5 = new RepeatingSpriteBackground(CameraWidth, CameraHeight, colouredBackgroundITextureRegion5, this.getVertexBufferObjectManager());

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// TARGET

		this.TargetITexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/targetRev1.png", TextureOptions.BILINEAR);
		this.TargetTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.TargetITexture, 2, 1);
		this.TargetITexture.load();

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ENEMY X

		this.EnemyXITexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/enemyXRev1.png", TextureOptions.BILINEAR);
		this.EnemyXTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.EnemyXITexture, 2, 1);
		this.EnemyXITexture.load();

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// OBSTACLE BASE

		this.obstacleBASEITexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/obstableBase.png", TextureOptions.BILINEAR);
		this.obstacleBASETextureRegion = TextureRegionFactory.extractTiledFromTexture(this.obstacleBASEITexture, 1, 1);
		this.obstacleBASEITexture.load();

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// OBSTACLE Crane

		this.obstacleCRANEITexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/ObstacleBaseRev1.png", TextureOptions.BILINEAR);
		this.obstacleCRANETextureRegion = TextureRegionFactory.extractTiledFromTexture(this.obstacleCRANEITexture, 1, 1);
		this.obstacleCRANEITexture.load();

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// SHIP

		shipBldBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		shipTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(shipBldBitmapTextureAtlas, this, "shipRev6.png", 2, 1);
		try
		{
			shipBldBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
			shipBldBitmapTextureAtlas.load();
		}
		catch (TextureAtlasBuilderException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ENEMY

		enemyBldBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		enemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyBldBitmapTextureAtlas, this, "enemyVirus.png", 1, 1);
		try
		{
			enemyBldBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
			enemyBldBitmapTextureAtlas.load();
		}
		catch (TextureAtlasBuilderException e)
		{
			e.printStackTrace();
		}

		enemyBldBitmapTextureAtlas2 = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
		enemyTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(enemyBldBitmapTextureAtlas2, this, "enemyVirus.png", 1, 1);
		try
		{
			enemyBldBitmapTextureAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
			enemyBldBitmapTextureAtlas2.load();
		}
		catch (TextureAtlasBuilderException e)
		{
			e.printStackTrace();
		}

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	// ====================================================
	// CREATE SCENE
	// ====================================================
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{

		mScene = new Scene();
		mScene.setBackground(this.mRepSpriteColouredBackground);
		bgMusic = MediaPlayer.create(this, R.raw.bensound_dubstep);
		bgMusic.start();
		bgMusic.setLooping(true);

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	// ====================================================
	// POPULATE SCENE
	// ====================================================

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback)
	{

		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0f, -SensorManager.GRAVITY_EARTH * 2), false, 8, 3);
		mScene.registerUpdateHandler(mPhysicsWorld);

		counterStage = 1;
		life = 10;
		counterEnemy = 0;
		startLevel1();
		currentLevel = 1;

		createWall();

		shipObj = new Ship(200, 400, mScene, this.mPhysicsWorld, shipFixtureDef, shipTextureRegion, this.getVertexBufferObjectManager());

		mPhysicsWorld.setContactListener(new ContactListener()
		{
			@Override
			public void beginContact(Contact contact)
			{
				if (contact.isTouching())
				{
					if (isBodyContacted(shipObj.shipBody, contact) && isBodyContacted(objEnemyX.EnemyXbody, contact))
					{
						bgMusic.stop();
						mEngine.runOnUpdateThread(new Runnable()
						{
							@Override
							public void run()
							{
								removeSprite(objEnemyX.EnemyXAnimatedSprite);
								runDialogBox();
								mEngine.stop();
							}
						});
					}

					if (isBodyContacted(shipObj.shipBody, contact) && isBodyContacted(objTarget.Targetbody, contact))
					{
						createExplosions(objTarget.targetAnimatedSprite.getX(), objTarget.targetAnimatedSprite.getY());
						CrashTarget.play();
						counterEnemy++;

						mEngine.runOnUpdateThread(new Runnable()
						{

							@Override
							public void run()
							{
								removeSprite(objTarget.targetAnimatedSprite);
								// counter++;

								Random randomGenerator = new Random();
								for (int idx = 0; idx <= 9; ++idx)
								{
									counterStage = randomGenerator.nextInt(9);
								}

								String infoTemp = String.valueOf(counterStage);
								Log.i("GameActivityV2", infoTemp);

								removeCurrentLevelObstacle();

								if (counterStage == 0 | counterStage == 1)
									counterStage = 9;

								// =============================================================================
								if (counterStage == 2)
								{
									startLevel2();
									currentLevel = 2;

								}
								else if (counterStage == 3)
								{
									startLevel3();
									currentLevel = 3;
								}
								else if (counterStage == 4)
								{
									startLevel4();
									currentLevel = 4;
								}
								else if (counterStage == 5)
								{
									startLevel5();
									currentLevel = 5;

								}
								else if (counterStage == 6)
								{
									startLevel6();
									currentLevel = 6;

								}
								else if (counterStage == 7)
								{
									startLevel7();
									currentLevel = 7;
								}
								else if (counterStage == 8)
								{
									startLevel8();
									currentLevel = 8;
								}
								else if (counterStage == 9)
								{
									startLevel9();
									currentLevel = 9;
								}
							}

							private void removeCurrentLevelObstacle()
							{
								// TODO Auto-generated method stub
								if (currentLevel == 2)
									lvl2RemoveObstacle();

								if (currentLevel == 3)
									lvl3RemoveObstacle();

								if (currentLevel == 4)
									lvl4RemoveObstacle();

								if (currentLevel == 5)
									lvl5RemoveObstacle();

								if (currentLevel == 6)
									lvl6RemoveObstacle();

								if (currentLevel == 7)
									lvl7RemoveObstacle();

								if (currentLevel == 8)
									lvl8RemoveObstacle();

								if (currentLevel == 9)
									lvl9RemoveObstacle();

							}
						});
					}

				}

			}

			public void runDialogBox()
			{
				final String counter1 = "Hint = Not all bombs detonate";
				final String counter2 = "Hint = Tilt your phone at angles";
				final String counter3 = "Hint = Do not touch the red bombs";
				final String counter4 = "Hint = Yellow bombs are good";
				final String counter5 = "Hint = Level changed";
				final String counter6 = "Hint = More bombs added";
				final String counter7 = "Easy right? ";
				final String counter8 = "Bummer";
				final String counter9 = "Try Again";

				GameActivityV2.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{

						AlertDialog.Builder alert = new AlertDialog.Builder(GameActivityV2.this);
						if (counterStage == 1)
							alert.setTitle(counter1);
						if (counterStage == 2)
							alert.setTitle(counter2);
						if (counterStage == 3)
							alert.setTitle(counter3);
						if (counterStage == 4)
							alert.setTitle(counter4);
						if (counterStage == 5)
							alert.setTitle(counter5);
						if (counterStage == 6)
							alert.setTitle(counter6);
						if (counterStage == 7)
							alert.setTitle(counter7);
						if (counterStage == 8)
							alert.setTitle(counter8);
						if (counterStage == 9)
							alert.setTitle(counter9);

						alert.setIcon(R.drawable.gameoverlogo);
						alert.setPositiveButton("OK", new OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int whichButton)
							{
								gameOverGetMainMenu();
							}
						});

						alert.create().show();

					}
				});

			}

			@Override
			public void endContact(Contact contact)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse)
			{
				// TODO Auto-generated method stub
			}

		});

		mScene.setOnSceneTouchListener(this);
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void createWall()
	{
		Wall createWall = new Wall(CameraWidth, CameraHeight, this.mPhysicsWorld, this.mScene, this.getVertexBufferObjectManager());
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		if (pSceneTouchEvent.isActionDown())
		{
			shipObj.mouseJointDef.target.set(shipObj.shipBody.getWorldCenter());
			shipObj.mouseJoint = (MouseJoint) mPhysicsWorld.createJoint(shipObj.mouseJointDef);
			final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			shipObj.mouseJoint.setTarget(vec);
			Vector2Pool.recycle(vec);

		}
		else if (pSceneTouchEvent.isActionMove())
		{
			final Vector2 vec = Vector2Pool.obtain(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			shipObj.mouseJoint.setTarget(vec);
			Vector2Pool.recycle(vec);
			return true;
		}
		else if (pSceneTouchEvent.isActionCancel() || pSceneTouchEvent.isActionOutside() || pSceneTouchEvent.isActionUp())
		{
			mPhysicsWorld.destroyJoint(shipObj.mouseJoint);
		}
		return true;
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData)
	{
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData)
	{

		final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
		this.mPhysicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);

	}

	@Override
	public void onResumeGame()
	{
		super.onResumeGame();
		this.enableAccelerationSensor(this);
	}

	@Override
	public void onPauseGame()
	{
		super.onPauseGame();
		this.disableAccelerationSensor();
	}

	void createExplosions(final float posX, final float posY)
	{

		this.mEngine.registerUpdateHandler(new FPSLogger());

		int mNumPart = 15;
		int mTimePart = 5;

		final PointParticleEmitter particleEmitter = new PointParticleEmitter(posX, posY);
		IEntityFactory<Entity> recFact = new IEntityFactory<Entity>()
		{
			@Override
			public Rectangle create(float pX, float pY)
			{
				Rectangle rect = new Rectangle(posX, posY, 10, 10, getVertexBufferObjectManager());
				rect.setColor(Color.BLUE);
				return rect;
			}
		};

		final ParticleSystem<Entity> particleSystemX = new ParticleSystem<Entity>(recFact, particleEmitter, 500, 500, mNumPart);
		particleSystemX.addParticleInitializer(new VelocityParticleInitializer<Entity>(-50, 50, -50, 50));
		particleSystemX.addParticleModifier(new AlphaParticleModifier<Entity>(0, 0.6f * mTimePart, 1, 0));
		particleSystemX.addParticleModifier(new RotationParticleModifier<Entity>(0, mTimePart, 0, 360));

		mScene.attachChild(particleSystemX);
		mScene.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback()
		{
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler)
			{
				particleSystemX.detachSelf();
				mScene.sortChildren();
				mScene.unregisterUpdateHandler(pTimerHandler);
			}
		}));

	}

	public boolean isBodyContacted(Body pBody, Contact pContact)
	{
		if (pContact.getFixtureA().getBody().equals(pBody) || pContact.getFixtureB().getBody().equals(pBody))
			return true;
		return false;
	}

	public boolean areBodiesContacted(Body pBody1, Body pBody2, Contact pContact)
	{
		if (pContact.getFixtureA().getBody().equals(pBody1) || pContact.getFixtureB().getBody().equals(pBody1))
			if (pContact.getFixtureA().getBody().equals(pBody2) || pContact.getFixtureB().getBody().equals(pBody2))
				return true;
		return false;
	}

	private void addNewEnemySpriteObject()
	{
		int EnemyRandomIntX = 0;
		int EnemyRandomIntY = 0;
		
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 10; ++idx)
		{
			EnemyRandomIntX = randomGenerator.nextInt(500);
			EnemyRandomIntY = randomGenerator.nextInt(500);
		}
		//enemyObj1 = new Enemy(0f, 0f, this.mPhysicsWorld, this.mScene, this.getVertexBufferObjectManager());
		enemyObj1 = new Enemy(EnemyRandomIntX, EnemyRandomIntY,  mScene,  this.mPhysicsWorld,  enemyTextureRegion,  enemyTextureRegion2, this.getVertexBufferObjectManager());;
		
	}

	private void addNewTargetSpriteObject()
	{
		objTarget = new Target(50, 100, mScene, this.mPhysicsWorld, this.TargetTextureRegion, this.getVertexBufferObjectManager());
	}

	private void addNewEnemyXSpriteObject()
	{
		objEnemyX = new EnemyX(50, 100, mScene, this.mPhysicsWorld, this.EnemyXTextureRegion, this.getVertexBufferObjectManager());
	}

	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================

	private void startLevel1()
	{
		objTarget = new Target(50, 100, mScene, this.mPhysicsWorld, this.TargetTextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void startLevel2()
	{
		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground2);
		obstacles2X = new Obstacles(CameraWidth / 2, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void lvl2RemoveObstacle()
	{
		removeSprite(obstacles2X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles2X.obstacleBODYAnimatedSprite);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel3()
	{
		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground3);

		obstacles3X = new Obstacles(CameraWidth / 2, CameraHeight / 4, this.mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles3Y = new Obstacles(CameraWidth / 2, 3 * (CameraHeight / 4), this.mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void lvl3RemoveObstacle()
	{
		// TODO Auto-generated method stub
		removeSprite(obstacles3X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles3X.obstacleBODYAnimatedSprite);
		obstacles3X.onDetached();
		obstacles3X.dispose();

		removeSprite(obstacles3Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles3Y.obstacleBODYAnimatedSprite);
		obstacles3Y.onDetached();
		obstacles3Y.dispose();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel4()
	{
		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground4);
		obstacles4X = new Obstacles(CameraWidth / 2, CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles4Y = new Obstacles(CameraWidth / 2, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles4Z = new Obstacles(CameraWidth / 2, 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void lvl4RemoveObstacle()
	{
		// TODO Auto-generated method stub
		removeSprite(obstacles4X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles4X.obstacleBODYAnimatedSprite);
		obstacles4X.onDetached();
		obstacles4X.dispose();

		removeSprite(obstacles4Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles4Y.obstacleBODYAnimatedSprite);
		obstacles4Y.onDetached();
		obstacles4Y.dispose();

		removeSprite(obstacles4Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles4Z.obstacleBODYAnimatedSprite);
		obstacles4Z.onDetached();
		obstacles4Z.dispose();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel5()
	{

		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground5); // Obstacles
		obstacles5X = new Obstacles(3 * (CameraWidth / 4), CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles5Y = new Obstacles(CameraWidth / 2, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles5Z = new Obstacles(CameraWidth / 4, 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		// addNewEnemyXSpriteObject();
	}

	public void lvl5RemoveObstacle()
	{
		removeSprite(obstacles5X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles5X.obstacleBODYAnimatedSprite);
		obstacles5X.onDetached();
		obstacles5X.dispose();

		removeSprite(obstacles5Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles5Y.obstacleBODYAnimatedSprite);
		obstacles5Y.onDetached();
		obstacles5Y.dispose();

		removeSprite(obstacles5Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles5Z.obstacleBODYAnimatedSprite);
		obstacles5Z.onDetached();
		obstacles5Z.dispose();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel6()
	{

		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground);
		obstacles6X = new Obstacles(CameraWidth / 4, CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles6Y = new Obstacles(CameraWidth / 2, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles6Z = new Obstacles(3 * (CameraWidth / 4), 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void lvl6RemoveObstacle()
	{
		removeSprite(obstacles6X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles6X.obstacleBODYAnimatedSprite);
		obstacles6X.onDetached();
		obstacles6X.dispose();

		removeSprite(obstacles6Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles6Y.obstacleBODYAnimatedSprite);
		obstacles6Y.onDetached();
		obstacles6Y.dispose();

		removeSprite(obstacles6Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles6Z.obstacleBODYAnimatedSprite);
		obstacles6Z.onDetached();
		obstacles6Z.dispose();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel7()
	{

		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground2);
		obstacles7W = new Obstacles(CameraWidth / 2, CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles7X = new Obstacles(CameraWidth / 4, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles7Y = new Obstacles(3 * (CameraWidth / 4), CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles7Z = new Obstacles(CameraWidth / 2, 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
	}

	public void lvl7RemoveObstacle()
	{
		// TODO Auto-generated method stub
		removeSprite(obstacles7W.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles7W.obstacleBODYAnimatedSprite);
		obstacles7W.onDetached();
		obstacles7W.dispose();

		removeSprite(obstacles7X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles7X.obstacleBODYAnimatedSprite);
		obstacles7X.onDetached();
		obstacles7X.dispose();

		removeSprite(obstacles7Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles7Y.obstacleBODYAnimatedSprite);
		obstacles7Y.onDetached();
		obstacles7Y.dispose();

		removeSprite(obstacles7Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles7Z.obstacleBODYAnimatedSprite);
		obstacles7Z.onDetached();
		obstacles7Z.dispose();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel8()
	{
		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground3);
		obstacles8X = new Obstacles(CameraWidth / 4, CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles8Y = new Obstacles(3 * (CameraWidth / 4), CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles8Z = new Obstacles(CameraWidth / 2, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles8A = new Obstacles(CameraWidth / 4, 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles8B = new Obstacles(3 * (CameraWidth / 4), 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		addNewEnemyXSpriteObject();
	}

	public void lvl8RemoveObstacle()
	{
		removeSprite(obstacles8X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles8X.obstacleBODYAnimatedSprite);
		obstacles8X.onDetached();
		obstacles8X.dispose();

		removeSprite(obstacles8Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles8Y.obstacleBODYAnimatedSprite);
		obstacles8Y.onDetached();
		obstacles8Y.dispose();

		removeSprite(obstacles8Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles8Z.obstacleBODYAnimatedSprite);
		obstacles8Z.onDetached();
		obstacles8Z.dispose();

		removeSprite(obstacles8A.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles8A.obstacleBODYAnimatedSprite);
		obstacles8A.onDetached();
		obstacles8A.dispose();

		removeSprite(obstacles8B.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles8B.obstacleBODYAnimatedSprite);
		obstacles8B.onDetached();
		obstacles8B.dispose();

	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startLevel9()
	{
		addNewTargetSpriteObject();
		mScene.setBackground(mRepSpriteColouredBackground4);

		obstacles9X = new Obstacles(CameraWidth / 4, CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles9Y = new Obstacles(CameraWidth / 4, CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles9Z = new Obstacles(CameraWidth / 4, 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());

		obstacles9A = new Obstacles(3 * (CameraWidth / 4), CameraHeight / 4, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles9B = new Obstacles(3 * (CameraWidth / 4), CameraHeight / 2, mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());
		obstacles9C = new Obstacles(3 * (CameraWidth / 4), 3 * (CameraHeight / 4), mScene, this.mPhysicsWorld, this.obstacleBASETextureRegion, this.obstacleCRANETextureRegion, this.getVertexBufferObjectManager());

		addNewEnemySpriteObject();
	}

	public void lvl9RemoveObstacle()
	{
		// TODO Auto-generated method stub

		removeSprite(obstacles9X.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9X.obstacleBODYAnimatedSprite);
		obstacles9X.onDetached();
		obstacles9X.dispose();

		removeSprite(obstacles9Y.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9Y.obstacleBODYAnimatedSprite);
		obstacles9Y.onDetached();
		obstacles9Y.dispose();

		removeSprite(obstacles9Z.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9Z.obstacleBODYAnimatedSprite);
		obstacles9Z.onDetached();
		obstacles9Z.dispose();

		removeSprite(obstacles9A.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9A.obstacleBODYAnimatedSprite);
		obstacles9A.onDetached();
		obstacles9A.dispose();

		removeSprite(obstacles9B.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9B.obstacleBODYAnimatedSprite);
		obstacles9B.onDetached();
		obstacles9B.dispose();

		removeSprite(obstacles9C.obstacleCRANEAnimatedSprite);
		removeSprite(obstacles9C.obstacleBODYAnimatedSprite);
		obstacles9C.onDetached();
		obstacles9C.dispose();

	}

	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================
	// ===============================================================================================================================================================================================

	public void removeSprite(final AnimatedSprite weird)
	{
		final PhysicsConnector facePhysicsConnector = mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(weird);
		mPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
		mPhysicsWorld.destroyBody(facePhysicsConnector.getBody());
		mScene.unregisterTouchArea(weird);
		mScene.detachChild(weird);
		System.gc();
	}

	public void gameOverGetMainMenu()
	{
		Intent splashIntent = new Intent("android.intent.action.MAINMENU");
		startActivity(splashIntent);

		mEngine.clearUpdateHandlers();
		mScene.detachChildren();
		mScene.reset();
		mPhysicsWorld.clearForces();
		mPhysicsWorld.clearPhysicsConnectors();
		mPhysicsWorld.reset();

		super.onGameDestroyed();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		bgMusic.release();
	}

} // End of GameActivityV2
// XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

