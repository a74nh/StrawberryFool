package com.sleepware.GameWorld;

import com.sleepware.GameObjects.Bird;
import com.sleepware.GameObjects.ButtonHandler;
import com.sleepware.GameObjects.Spoon.Collides;
import com.sleepware.GameObjects.Hud;
import com.sleepware.GameObjects.StaticImage;
import com.sleepware.GameObjects.ScrollHandler;
import com.sleepware.GameObjects.Title;
import com.sleepware.GameObjects.Yoghurt;
import com.sleepware.GameWorld.GameLevel.GameType;
import com.sleepware.ZBHelpers.AssetLoader;

public class GameWorld {
	
	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, PAUSED, OPTIONS
	}
	
	private Bird bird;
	private ScrollHandler scroller;
	private int score = 0;
	private float runTime = 0;
	private final int midPointX;
	private final int birdMidpoint;
	private GameRenderer renderer;
	
	private GameState currentState;
	private StaticImage background;
	private Yoghurt yoghurt;
	private ButtonHandler buttonhandler;

	private GameLevel level;

	private final int minX;
	private final int maxX;
	private Hud hud;
	private Title title;

	
	public GameWorld(int gameWidth, int gameHeight, int minX, int maxX) {
		currentState = GameState.MENU;
		this.midPointX = gameWidth/2;
		
		//gameWidth should be 272
		//gameHeight depends upon device. Assume minimum of 400
		
		this.minX = minX;
		this.maxX = maxX;
		
		final int halfheight = gameHeight/2;
		
		int groundStart = halfheight + 200;
		
		//Bird starts halfway between the grasses
		birdMidpoint = ( (minX-maxX) / 2 ) + maxX;
		
		final int maxBirdMovement = ( (maxX-minX) / 2 ) * 7 / 10;

		final int grassSize = 11;
		
		final int spoonSize = 10;
		final int spoonHandleWidth = 60;
		final int spoonHandleHeight = 34;
		
		final int birdDiameter = 40;
		final int fallingFruitDiameter = birdDiameter*2/3;
		final int staticFruitDiameter = birdDiameter*2/3;
		
		level = new GameLevel(this,gameWidth);

		bird = new Bird(this, birdMidpoint, gameHeight, birdDiameter, maxBirdMovement);	
		scroller = new ScrollHandler(this, minX, maxX, gameWidth, groundStart, grassSize, spoonSize, spoonHandleWidth, spoonHandleHeight, fallingFruitDiameter);
		background = new StaticImage(minX,0,maxX,groundStart);
		yoghurt = new Yoghurt(0,groundStart,gameWidth,gameHeight-groundStart, staticFruitDiameter, minX, maxX);
		hud = new Hud(this, groundStart, minX, maxX, AssetLoader.font, AssetLoader.shadow);
		title = new Title(minX, maxX, gameWidth, gameHeight, spoonSize, spoonHandleWidth, spoonHandleHeight);
		buttonhandler = new ButtonHandler(this, gameWidth, gameHeight);
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case READY:
		case MENU:
		case OPTIONS:
			updateReady(delta);
			break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
		
		if(currentState==GameState.MENU) {
			title.update(delta);
		}
	}

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive()) {
			Collides c = scroller.collides(bird);
			if(c!=Collides.NONE) {
				scroller.stop();
				bird.dying(c);
				AssetLoader.dead.play(AssetLoader.volume);
				renderer.prepareTransition(255, 255, 255, .3f);
	
				AssetLoader.fall.play(AssetLoader.volume);
			}
		} else if (bird.collidesSide(minX, maxX)) {
			
			AssetLoader.dead.play(AssetLoader.volume);
			renderer.prepareTransition(255, 255, 255, .3f);

			scroller.stop();
			bird.dead();

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				setState(GameState.HIGHSCORE);
			} else {
				setState(GameState.GAMEOVER);
			}
			
			if (level.getLevel() > AssetLoader.getHighLevel()) {
				AssetLoader.setHighLevel(level.getLevel());
			}
		}
		
	}

	public Bird getBird() {
		return bird;

	}

	public int getMidPointY() {
		return midPointX;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public StaticImage getBackground() {
		return background;
	}
	
	public Yoghurt getYoghurt() {
		return yoghurt;
	}
	
	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
		level.incProgress();
	}

	/**************************************
	 * STATE TRANSITION FUNCTIONS
	 */
	
	private void setState(GameState newState) {
		currentState = newState;
		buttonhandler.onStateChange();
	}
	
	public void start() {
		assert(currentState == GameState.READY);
		setState(GameState.RUNNING);
	}

	public void ready(GameType gametype) {
		assert(currentState == GameState.MENU);
		level.setGameType(gametype);
		reset();
		setState(GameState.READY);
	}
	
	public void pause() {
		assert(currentState == GameState.RUNNING);
		setState(GameState.PAUSED);
	}
	
	public void unpause() {
		assert(currentState == GameState.PAUSED);
		setState(GameState.RUNNING);
	}
	
	public void quitToMenu() {
		assert(currentState == GameState.PAUSED ||
				currentState == GameState.READY ||
				currentState == GameState.GAMEOVER ||
				currentState == GameState.HIGHSCORE );
		reset();
		setState(GameState.MENU);
	}
	
	public void restart() {
		assert(currentState == GameState.GAMEOVER ||
				currentState == GameState.HIGHSCORE );
		reset();
		setState(GameState.READY);
	}

	public void options() {
		assert(currentState == GameState.MENU );
		setState(GameState.OPTIONS);
	}
	
	public void quitOptions() {
		assert(currentState == GameState.OPTIONS );
		setState(GameState.MENU);
	}
	
	private void reset() {
		runTime=0;
		score = 0;
		level.onRestart();
		bird.onRestart();
		scroller.onRestart();
		renderer.prepareTransition(0, 0, 0, 1f);
	}
	

	/**************************************
	 * STATE QUERY FUNCTIONS
	 */
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public GameState getState() {
		return currentState;
	}
	
	
	

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public ScrollHandler getScrollHandler() {
		return scroller;
	}

	public void endLevel() {
		//We need to tell everything else to bring the level to a close
		scroller.endLevel();
		//bird.endLevel();
	}

	public void nextLevel() {
		//Level is finished. Safe to move to next level
		level.incLevel();
		scroller.nextLevel();
		bird.nextLevel();
	}
	
	public GameLevel getLevel() {
		return level;
	}

	public Hud getHud() {
		return hud;
	}

	public Title getTitle() {
		return title;
	}

	public ButtonHandler getButtonhandler() {
		return buttonhandler;
	}



}
