package com.example.vortexjumper;

import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Target
{

	AnimatedSprite targetAnimatedSprite;
	Body Targetbody;
	FixtureDef objectFixtureDef;
	int randomIntX;
	int randomIntY;
	
	public Target(final int i, final int j, Scene mScene, final FixedStepPhysicsWorld mPhysicsWorld, TiledTextureRegion targetTextureRegion2, VertexBufferObjectManager vbom)
	{
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 10; ++idx)
		{
			randomIntX = randomGenerator.nextInt(500);
		}
		
		for (int idx = 1; idx <= 10; ++idx)
		{
			randomIntY = randomGenerator.nextInt(500);
		}

		// TODO Auto-generated constructor stub
		objectFixtureDef  = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		targetAnimatedSprite = new AnimatedSprite(randomIntX + i, randomIntY + j, targetTextureRegion2 , vbom)
		{
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				Targetbody.applyAngularImpulse(500f);
			};
		};
		
		
		Targetbody = PhysicsFactory.createCircleBody(mPhysicsWorld, targetAnimatedSprite, BodyType.DynamicBody, objectFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(targetAnimatedSprite, Targetbody, true, true));
		
		
		targetAnimatedSprite.animate(200, true);
		mScene.attachChild(targetAnimatedSprite);
	}

}
