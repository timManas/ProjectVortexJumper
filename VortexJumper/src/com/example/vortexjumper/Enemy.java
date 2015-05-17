package com.example.vortexjumper;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.LineJointDef;

public class Enemy extends Entity
{
	Rectangle EnemyLineJointRectA;
	Rectangle EnemyLineJointRectB;
	Body LineJointBodyA;
	Body LineJointBodyB;
	final FixtureDef enemyFixtureDef = PhysicsFactory.createFixtureDef(20f, 0.2f, 0.9f);
	final FixtureDef enemyFixtureDef2 = PhysicsFactory.createFixtureDef(20f, 0.2f, 0.9f);

	AnimatedSprite enemyAnimatedSprite;
	Body enemyBody;
	FixtureDef objectFixtureDef;

	AnimatedSprite enemyAnimatedSprite2;
	Body enemyBody2;
	FixtureDef objectFixtureDef2;

	public Enemy(float f, float g, FixedStepPhysicsWorld mPhysicsWorld, Scene mScene, VertexBufferObjectManager vertexBufferObjectManager)
	{
		// TODO Auto-generated method stub
		EnemyLineJointRectA = new Rectangle(f, g + 40, 30f, 30f, vertexBufferObjectManager)
		{
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				LineJointBodyA.applyTorque(1000f);
				LineJointBodyA.setAngularVelocity(10f);
			}
		};
		EnemyLineJointRectA.setColor(0.5f, 0.25f, 0f);
		mScene.attachChild(EnemyLineJointRectA);
		LineJointBodyA = PhysicsFactory.createCircleBody(mPhysicsWorld, EnemyLineJointRectA, BodyType.DynamicBody, enemyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(EnemyLineJointRectA, LineJointBodyA));

		EnemyLineJointRectB = new Rectangle(f, g, 30f, 30f, vertexBufferObjectManager)
		{
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				LineJointBodyB.applyTorque(1000f);
				LineJointBodyB.setAngularVelocity(10f);
			}
		};
		EnemyLineJointRectB.setColor(0.75f, 0.375f, 0f);
		mScene.attachChild(EnemyLineJointRectB);
		LineJointBodyB = PhysicsFactory.createBoxBody(mPhysicsWorld, EnemyLineJointRectB, BodyType.DynamicBody, enemyFixtureDef);
		LineJointBodyB.setLinearDamping(1f);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(EnemyLineJointRectB, LineJointBodyB));

		final LineJointDef lineJointDef = new LineJointDef();
		lineJointDef.initialize(LineJointBodyA, LineJointBodyB, LineJointBodyB.getWorldCenter(), new Vector2(0f, 1f));
		lineJointDef.collideConnected = true;
		lineJointDef.enableLimit = true;
		lineJointDef.lowerTranslation = -220f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		// lineJointDef.lowerTranslation = 0f;
		lineJointDef.upperTranslation = 0f;
		lineJointDef.enableMotor = true;
		lineJointDef.motorSpeed = -20f;
		lineJointDef.maxMotorForce = 120f;
		mPhysicsWorld.createJoint(lineJointDef);
	}

	public Enemy(int i, int j, Scene mScene, FixedStepPhysicsWorld mPhysicsWorld, TiledTextureRegion enemyTextureRegion, TiledTextureRegion enemyTextureRegion2, VertexBufferObjectManager vertexBufferObjectManager)
	{
		// TODO Auto-generated constructor stub

		objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		enemyAnimatedSprite = new AnimatedSprite(i, j, enemyTextureRegion, vertexBufferObjectManager)
		{
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				enemyBody.applyTorque(10f);
				enemyBody.setAngularVelocity(10f);
			}
		};
		enemyBody = PhysicsFactory.createCircleBody(mPhysicsWorld, enemyAnimatedSprite, BodyType.DynamicBody, objectFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(enemyAnimatedSprite, enemyBody, true, true));
		// enemyAnimatedSprite.animate(200, true);
		mScene.attachChild(enemyAnimatedSprite);

		objectFixtureDef2 = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		enemyAnimatedSprite2 = new AnimatedSprite(i, j, enemyTextureRegion2, vertexBufferObjectManager)
		{
			@Override
			protected void onManagedUpdate(final float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				enemyBody2.applyTorque(5f);
				enemyBody2.setAngularVelocity(5f);
			}
		};
		enemyBody2 = PhysicsFactory.createCircleBody(mPhysicsWorld, enemyAnimatedSprite2, BodyType.DynamicBody, objectFixtureDef2);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(enemyAnimatedSprite2, enemyBody2, true, true));
		// enemyAnimatedSprite2.animate(200, true);
		mScene.attachChild(enemyAnimatedSprite2);

		final LineJointDef lineJointDef = new LineJointDef();
		lineJointDef.initialize(enemyBody, enemyBody2, enemyBody2.getWorldCenter(), new Vector2(0f, 1f));
		lineJointDef.collideConnected = true;
		lineJointDef.enableLimit = true;
		lineJointDef.lowerTranslation = -220f / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

		lineJointDef.upperTranslation = 0f;
		lineJointDef.enableMotor = true;
		lineJointDef.motorSpeed = -20f;
		lineJointDef.maxMotorForce = 120f;
		mPhysicsWorld.createJoint(lineJointDef);

	}

}
