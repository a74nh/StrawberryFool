package com.sleepware.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.GameWorld.GameWorld.GameState;
import com.sleepware.ZBHelpers.AssetLoader;

public class SimpleButton {

	private int x, y, width, height;

	private TextureRegion buttonUp;
	private TextureRegion buttonDown;

	private Rectangle bounds;

	private boolean isPressed = false;

	private ActionFunction actionFunction;

	private GameState gamestate;

	private String title;

	public interface ActionFunction {
	    void onClick(SimpleButton button, GameWorld world);
	    void onStateChange(SimpleButton button);
	}
	
	
	public SimpleButton(int x, int y, int width, int height,
			TextureRegion buttonUp, TextureRegion buttonDown, ActionFunction actionFunction, GameState gamestate, String title) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		this.actionFunction=actionFunction;
		this.gamestate=gamestate;
		this.title=title;

		bounds = new Rectangle(x, y, width, height);

	}

	public boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	
	private void drawText(SpriteBatch batcher, String s, int x, int y) {
		AssetLoader.shadow.draw(batcher, s, x, y);
		AssetLoader.font.draw(batcher, s, x, y);
	}
	
	
	public void draw(SpriteBatch batcher) {
		if (isPressed) {
			batcher.draw(buttonDown, x, y, width, height);
		} else {
			batcher.draw(buttonUp, x, y, width, height);
		}
		drawText(batcher,title,x+5,y+5);
	}

	public boolean isTouchDown(int screenX, int screenY) {

		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			return true;
		}

		return false;
	}

	public boolean isTouchUp(int screenX, int screenY) {
		
		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			AssetLoader.flap.play();
			return true;
		}
		
		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}

	
	public void doAction(GameWorld world) {
		actionFunction.onClick(this, world);
	}
	
	public void onStateChange() {
		actionFunction.onStateChange(this);
	}
	
	
	public GameState getGameState() {
		return gamestate;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
}
