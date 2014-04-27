package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	
	private boolean hasLeader=false;
	private Scrollable leader;
	private int leaderDistance;
	private float velocityMultiplier;

	public Scrollable(float x, float y, int width, int height, float velocityMultiplier) {
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.width = width;
		this.height = height;
		this.velocityMultiplier=velocityMultiplier;
	}

	public void setLevelAttributes(int scrollSpeed) {
		velocity.y=scrollSpeed*velocityMultiplier;
	}
	
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}

	public boolean checkForOverflow() {
		// If the Scrollable object is no longer visible:
		if (position.y + height < 0) {
			
			if(hasLeader) {
				reset(leader.getTailY()+leaderDistance);
				return true;
			}
		}
		return false;
	}
	
	// Reset: Should Override in subclass for more specific behavior.
	public void reset(float newY) {
		position.y = newY;
	}
	
	public void onRestart(float y) {
		reset(y);
	}
	
	public void stop() {
		velocity.y = 0;
	}
	
	public float getTailY() {
		return position.y + height;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setLeader(Scrollable leader, int leaderDistance) {
		hasLeader=true;
		this.leader=leader;
		this.leaderDistance=leaderDistance;
	}
	
	public Scrollable getLeader() {
		return leader;
	}
	
	public void draw(SpriteBatch batcher, TextureRegion texture) {
		batcher.draw(texture, position.x,position.y,width,height);
	}
	
	public void incY(float y) {
		position.y+=y;
	}
}
