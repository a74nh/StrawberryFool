package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RotatingScrollable extends Scrollable {

	private int rotation;
	protected int rotationVelocity;
	
	public RotatingScrollable(float x, float y, int width, int height, float velocityMultiplier) {
		super(x,y,width,height, velocityMultiplier);
		rotation=0;
		rotationVelocity=0;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		rotation+=(rotationVelocity*delta);
	}
	
	@Override
	public void reset(float newY) {
		super.reset(newY);
		rotation=0;
		rotationVelocity=0;
	}
	
	@Override
	public void stop() {
		rotationVelocity = 0;
	}

	public void draw(SpriteBatch batcher, TextureRegion texture) {	
		batcher.draw(texture,
				 position.x,
			     position.y,
			     width / 2.0f, 
			     height / 2.0f,
			     width, 
			     height, 
			     1, 
			     1,
			     rotation);
	}
	
}
