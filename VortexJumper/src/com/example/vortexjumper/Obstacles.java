package com.example.vortexjumper;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Obstacles extends Entity
{

	Body RevoluteJointBodyA;
	Body RevoluteJointBodyB;
	Rectangle RevoluteJointRectA;

	AnimatedSprite obstacleBODYAnimatedSprite;
	Body obstacleBODY;
	FixtureDef obstacleBODYFixtureDef;

	AnimatedSprite obstacleCRANEAnimatedSprite;
	Body obstacleCRANE;
	FixtureDef obstacleCRANEFixtureDef;

	public Obstacles(float f, float g, float f2, float g2, Scene mScene, FixedStepPhysicsWorld mPhysicsWorld, FixtureDef enemyFixtureDef, VertexBufferObjectManager vertexBufferObjectManager)
	{

		// TODO Auto-generated constructor stub
		RevoluteJointRectA = new Rectangle(f, g, 30f, 30f, vertexBufferObjectManager);
		RevoluteJointRectA.setColor(0f, 0f, 0.65f);
		mScene.attachChild(RevoluteJointRectA);
		RevoluteJointBodyA = PhysicsFactory.createBoxBody(mPhysicsWorld, RevoluteJointRectA, BodyType.KinematicBody, enemyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(RevoluteJointRectA, RevoluteJointBodyA));

		Rectangle RevoluteJointRectB = new Rectangle(f, g + 40f, 10f, 90f, vertexBufferObjectManager);
		RevoluteJointRectB.setColor(0f, 0f, 0.8f);
		mScene.attachChild(RevoluteJointRectB);
		RevoluteJointBodyB = PhysicsFactory.createBoxBody(mPhysicsWorld, RevoluteJointRectB, BodyType.DynamicBody, enemyFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(RevoluteJointRectB, RevoluteJointBodyB));

		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(RevoluteJointBodyA, RevoluteJointBodyB, RevoluteJointBodyA.getWorldCenter());
		revoluteJointDef.collideConnected = false;
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.maxMotorTorque = 5000f;
		revoluteJointDef.motorSpeed = -1f;
		mPhysicsWorld.createJoint(revoluteJointDef);
	}

	public Obstacles(int i, int j, Scene mScene, FixedStepPhysicsWorld mPhysicsWorld, TiledTextureRegion obstacleBASETextureRegion, TiledTextureRegion obstacleCRANETextureRegion, VertexBufferObjectManager vertexBufferObjectManager)
	{

		obstacleBODYFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		obstacleBODYAnimatedSprite = new AnimatedSprite(i, j, obstacleBASETextureRegion, vertexBufferObjectManager);
		mScene.attachChild(obstacleBODYAnimatedSprite);

		obstacleBODY = PhysicsFactory.createBoxBody(mPhysicsWorld, obstacleBODYAnimatedSprite, BodyType.KinematicBody, obstacleBODYFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(obstacleBODYAnimatedSprite, obstacleBODY, true, true));
		// obstacleBODYAnimatedSprite.animate(200,true);

		obstacleCRANEFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
		obstacleCRANEAnimatedSprite = new AnimatedSprite(i + 50f, j, obstacleCRANETextureRegion, vertexBufferObjectManager);
		mScene.attachChild(obstacleCRANEAnimatedSprite);

		obstacleCRANE = PhysicsFactory.createBoxBody(mPhysicsWorld, obstacleCRANEAnimatedSprite, BodyType.DynamicBody, obstacleCRANEFixtureDef);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(obstacleCRANEAnimatedSprite, obstacleCRANE, true, true));
		// obstacleCRANEAnimatedSprite.animate(200,true);

		final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(obstacleBODY, obstacleCRANE, obstacleBODY.getWorldCenter());
		revoluteJointDef.collideConnected = false;
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.maxMotorTorque = 500f;
		revoluteJointDef.motorSpeed = 10f;
		mPhysicsWorld.createJoint(revoluteJointDef);

	}
}
