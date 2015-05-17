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

public class EnemyX
{
	AnimatedSprite EnemyXAnimatedSprite;
	Body EnemyXbody;
	FixtureDef EnemyXobjectFixtureDef;
	int EnemyXrandomIntX;
	int EnemyXrandomIntY;

	public EnemyX(final int i, final int j, Scene mScene, final FixedStepPhysicsWorld mPhysicsWorld, TiledTextureRegion targetTextureRegion2, VertexBufferObjectManager vbom)
	{
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 10; ++idx)
		{
			EnemyXrandomIntX = randomGenerator.nextInt(500);
		}

		for (int idx = 1; idx <= 10; ++idx)
		{
			EnemyXrandomIntY = randomGenerator.nextInt(500);
		}

		EnemyXobjectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		EnemyXAnimatedSprite = new AnimatedSprite(EnemyXrandomIntX + i, EnemyXrandomIntY + j, targetTextureRegion2, vbom)
		{
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				EnemyXbody.applyAngularImpulse(500f);
			};
		};

		EnemyXbody = PhysicsFactory.createCircleBody(mPhysicsWorld, EnemyXAnimatedSprite, BodyType.DynamicBody, EnemyXobjectFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(EnemyXAnimatedSprite, EnemyXbody, true, true));

		EnemyXAnimatedSprite.animate(200, true);
		mScene.attachChild(EnemyXAnimatedSprite);
	}
}
