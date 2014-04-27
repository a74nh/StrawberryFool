package com.sleepware.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sleepware.GameWorld.GameRenderer;
import com.sleepware.GameWorld.GameWorld;
import com.sleepware.ZBHelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	// This is the constructor, not the class declaration
	public GameScreen() {

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		//float gameWidth = 136;
		//float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		float gameWidth = 272;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		int minX = 11;
		int maxX = (int)gameWidth-11;
		
		world = new GameWorld((int) gameWidth, (int) gameHeight, minX, maxX);
		Gdx.input.setInputProcessor(new InputHandler(world, screenWidth, gameWidth, screenHeight, gameHeight));
		Gdx.input.setCatchBackKey(true);
		renderer = new GameRenderer(world, (int)gameWidth, (int) gameHeight, minX, maxX);
		world.setRenderer(renderer);
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
