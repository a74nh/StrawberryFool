package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class StaticImage {

	protected Rectangle boundingRectangle;
	
	public StaticImage(int x, int y, int width, int height) {
		
		boundingRectangle = new Rectangle(x, y, width, height);
	}

	public float getX() {
		return boundingRectangle.x;
	}

	public float getY() {
		return boundingRectangle.y;
	}

	public void setX(float x) {
		boundingRectangle.x=x;
	}

	public void setY(float y) {
		boundingRectangle.y=y;
	}
	
	public float getWidth() {
		return boundingRectangle.width;
	}

	public float getHeight() {
		return boundingRectangle.height;
	}

	public void setWidth(int width) {
		boundingRectangle.width=width;
	}
	
	public void setHeight(int height) {
		boundingRectangle.height=height;
	}
	
	public void draw(SpriteBatch batcher, TextureRegion texture) {
		batcher.draw(texture, boundingRectangle.x, boundingRectangle.y, boundingRectangle.width, boundingRectangle.height);
	}

	public void drawShape(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect(boundingRectangle.x, boundingRectangle.y, boundingRectangle.width, boundingRectangle.height);

	}

	public Rectangle getBoundingRectangle() {
		return boundingRectangle;
	}


}
