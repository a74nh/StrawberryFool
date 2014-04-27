package com.sleepware.GameWorld;

import com.sleepware.ZBHelpers.AssetLoader;


public class GameLevel {

	public enum GameType {
		STORY, ARCADE;
	}
	
	public enum BirdMotion {
		LINEAR, SINE, NONE, DYING, DEAD;
		
		public static final int numBirdMotions = BirdMotion.values().length;

		public static BirdMotion getEnum(int value) {
		     return BirdMotion.values()[value % numBirdMotions];
		}
	}
	
	private int progress;
	private int level;
	private GameWorld gameWorld;
	private int gameWidth;
	GameType gameType;
	
	public GameLevel(GameWorld gameWorld, int gameWidth) {
		this.gameWorld=gameWorld;
		this.gameWidth=gameWidth;
		gameType=GameType.ARCADE;
		onRestart();
	}
	
	public int getMaxProgress() {
		return 5 + level;
	}
	
	/*public int getPipeGap() {
		return 59;
	}*/
	
	public int getVerticalPipeGap() {
		return 145 - (level*5);
	}
	
	public int getScrollSpeed() {
		return -79 - (level*5);
	}
	
	public int getOutOfBounds() {
		return gameWidth/5 + (level*10);
	}

	public BirdMotion getBirdMotion() {
		//return BirdMotion.SINE;
		if(level==0) return BirdMotion.NONE;
		
		return BirdMotion.getEnum((level-1)%2);
	}
	
	public int getBirdYPosition() {
		return 10 + (5*level);
	}
	
	public float getBirdSpeed() {
		return 50+(level*12);
	}
	
	public int getLevel() {
		return level;
	}
	
	public void incLevel() {
		level++;
	}
	
	public void incProgress() {
		progress++;
		
		if (progress>getMaxProgress()) {
			progress=0;
			gameWorld.endLevel();
		}
	}

	public void setProgress(int progress) {
		this.progress+=progress;
	}

	public void setGameType(GameType gameType) {
		this.gameType=gameType;
		progress=0;
	}
	
	public void onRestart() {
		switch(gameType) {
		case ARCADE:
			level=1;
			break;
		case STORY:
			level=AssetLoader.getHighLevel();
			break;
		default:
			break;
		
		}
		progress=0;
	}

	public int getDeathVelocity() {
		return 120;
	}


}
