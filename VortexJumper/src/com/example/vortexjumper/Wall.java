package com.example.vortexjumper;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Wall
{
	public Body groundWallBody;
	public Body roofWallBody;
	public Body leftWallBody;
	public Body rightWallBody;
	public Scene mScene;
	
	public Wall(int cameraWidth, int cameraHeight, FixedStepPhysicsWorld mPhysicsWorld, Scene mScene, VertexBufferObjectManager vertexBufferObjectManager)
	{
		// TODO Auto-generated constructor stub
		final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		final Rectangle ground = new Rectangle(	cameraWidth / 2f	, 0f				, cameraWidth - 4f		, 8f				, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(	cameraWidth / 2f	, cameraHeight		, cameraWidth - 4f		, 8f				, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(	0f					, cameraHeight / 2f	, 8f					, cameraHeight - 4f	, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(	cameraWidth 		, cameraHeight / 2f	, 8f					, cameraHeight - 4f	, vertexBufferObjectManager);
		ground.setColor(0f, 0f, 0f);
		roof.setColor(0f, 0f, 0f);
		left.setColor(0f, 0f, 0f);
		right.setColor(0f, 0f, 0f);
		groundWallBody = PhysicsFactory.createBoxBody(mPhysicsWorld, ground, BodyType.StaticBody, WALL_FIXTURE_DEF);
		roofWallBody = PhysicsFactory.createBoxBody(mPhysicsWorld, roof, BodyType.StaticBody, WALL_FIXTURE_DEF);
		leftWallBody = PhysicsFactory.createBoxBody(mPhysicsWorld, left, BodyType.StaticBody, WALL_FIXTURE_DEF);
		rightWallBody = PhysicsFactory.createBoxBody(mPhysicsWorld, right, BodyType.StaticBody, WALL_FIXTURE_DEF);
		mScene.attachChild(ground);
		mScene.attachChild(roof);
		mScene.attachChild(left);
		mScene.attachChild(right);
	}


}
