package com.example.vortexjumper;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class Ship extends Entity
{
	protected static final String targetAnimatedSprite = null;
	AnimatedSprite AnimatedShipSprite;
	TiledSprite shipSprite;
	Body shipBody;

	MouseJointDef mouseJointDef;
	Body MouseJointBodyA;
	Body headBody;
	public MouseJoint mouseJoint;
	final FixtureDef headFixtureDef = PhysicsFactory.createFixtureDef(30f, 0.1f, 0.9f);

	public Ship(int i, int j, Scene mScene, final FixedStepPhysicsWorld mPhysicsWorld, FixtureDef shipFixtureDef, TiledTextureRegion shipTextureRegion2, VertexBufferObjectManager vertexBufferObjectManager)
	{
		shipSprite = new TiledSprite(i, j, shipTextureRegion2, vertexBufferObjectManager)
		{
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				super.onManagedUpdate(pSecondsElapsed);
				shipBody.applyForce(-mPhysicsWorld.getGravity().x * shipBody.getMass(), -mPhysicsWorld.getGravity().y * shipBody.getMass(), shipBody.getWorldCenter().x, shipBody.getWorldCenter().y);
				
			};
		};
		
		mScene.attachChild(shipSprite);

		shipBody = PhysicsFactory.createCircleBody(mPhysicsWorld, shipSprite, BodyType.DynamicBody, shipFixtureDef);
		shipBody.setLinearDamping(2f);
		
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(shipSprite, shipBody));
		// shipSprite.animate(200,true);

		MouseJointBodyA = mPhysicsWorld.createBody(new BodyDef());
		mouseJointDef = new MouseJointDef();
		mouseJointDef.bodyA = MouseJointBodyA;
		mouseJointDef.bodyB = shipBody;
		mouseJointDef.dampingRatio = 0.01f;
		mouseJointDef.frequencyHz = 1f;
		mouseJointDef.maxForce = (100.0f * shipBody.getMass());
		mouseJointDef.collideConnected = false;

	}
}
