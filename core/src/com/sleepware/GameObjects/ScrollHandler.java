package com.sleepware.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sleepware.GameObjects.Spoon.Collides;
import com.sleepware.GameWorld.GameLevel;
import com.sleepware.GameWorld.GameWorld;

public class ScrollHandler {
	
	private enum LevelState {
		NORMAL, GENERATE_BAR, GENERATE_FAR_AWAY
	}
	
	public static final int NUMBER_OF_CANDYWALLS = 2;
	public static final int NUMBER_OF_SPOONS = 6;
	public static final int NUMBER_OF_FALLING_FRUIT = 8;

	private Scrollable[][] candywall;
	private Spoon[] spoon;
	private FallingBackgroundFruit[] fallingFruit;

	private GameWorld gameWorld;
	private final float yoghurtStart;

	private float birdHeight;

	private LevelState levelState;
	private GameLevel level;
	
	private final int pipeRestartPoint;
	private final int pipeGap;
	
	public ScrollHandler(GameWorld gameWorld, 
			int grassLeftStart, 
			int grassRightStart, 
			int yoghurtStart,
			int gameHeight,
			int grassSize,
			int spoonSize,
			int headWidth,
			int headHeight,
			int fruitDiameter,
			int fruitMinX,
			int fruitMaxX) {
		
		this.gameWorld = gameWorld;
		level = gameWorld.getLevel();
		this.yoghurtStart = yoghurtStart;
		
		final int grassLength = gameHeight+2;
		
		pipeRestartPoint = (int) ((gameHeight*5/4)+headHeight);
		
        candywall = new Scrollable[NUMBER_OF_CANDYWALLS][2];

        candywall[0][0] = new Scrollable(grassLeftStart-grassSize, 0, grassSize, grassLength, (float)1.5);
        candywall[0][1] = new Scrollable(grassLeftStart-grassSize, candywall[0][0].getTailY(), grassSize, grassLength, (float)1.5);
        
        candywall[1][0] = new Scrollable(grassRightStart, 0, grassSize, grassLength, (float)1.5);
        candywall[1][1] = new Scrollable(grassRightStart, candywall[0][0].getTailY(), grassSize, grassLength, (float)1.5);
        
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			candywall[i][0].setLeader(candywall[i][1],0);
			candywall[i][1].setLeader(candywall[i][0],0);
		}
        
		pipeGap = 59;
		spoon = new Spoon[NUMBER_OF_SPOONS];
		
	
		spoon[0] = new Spoon((gameHeight*3/2), spoonSize, headWidth, headHeight, grassLeftStart, grassRightStart, 1);
		for(int i=1; i<NUMBER_OF_SPOONS; i++)
		{
			spoon[i] = new Spoon(spoon[i-1].getTailY() + pipeGap,
					spoonSize,
					headWidth,
					headHeight,
					grassLeftStart,
					grassRightStart, 1);
			spoon[i].setLeader(spoon[i-1],pipeGap);
		}
		spoon[0].setLeader(spoon[NUMBER_OF_SPOONS-1],pipeGap);

		
		fallingFruit = new FallingBackgroundFruit[NUMBER_OF_FALLING_FRUIT];

		final float offset = (fruitMaxX-fruitMinX)/NUMBER_OF_FALLING_FRUIT;
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i] = new FallingBackgroundFruit(fruitDiameter,(int) yoghurtStart, (int)(fruitMinX+(i*offset)), (int)(fruitMinX+((i+1)*offset)),1);
		}
		
		onRestart();
	}

	public void updateReady(float delta) {

		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
		        candywall[i][j].update(delta);
			}
		}
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
		        candywall[i][j].checkForOverflow();
			}
		}
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++) {
			fallingFruit[i].checkForOverflow();
		}
	}

	public void update(float delta) {
		
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
		        candywall[i][j].update(delta);
			}	
		}
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
		        candywall[i][j].checkForOverflow();
			}	
		}
		
		for(int i=0; i<NUMBER_OF_SPOONS; i++)
		{
			spoon[i].update(delta);
		}
		for(int i=0; i<NUMBER_OF_SPOONS; i++)
		{			
			if(spoon[i].checkForOverflow()) {
				
				switch(levelState) {
				case NORMAL:
					break;
				case GENERATE_BAR:
					spoon[i].incY(pipeGap/4);
					spoon[i].setIsBar(true);
					levelState=LevelState.GENERATE_FAR_AWAY;
					break;
				case GENERATE_FAR_AWAY:
					spoon[i].incY(yoghurtStart);
					levelState=LevelState.NORMAL;
					break;
				default:
					break;
				}
			}
		}
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++) {
			fallingFruit[i].update(delta);
		}
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++) {
			fallingFruit[i].checkForOverflow();
		}
	
	}

	private void setLevelAttributes() {
		
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
				candywall[i][j].setLevelAttributes(level.getScrollSpeed());
			}
		}
		
		for(int i=0; i<NUMBER_OF_SPOONS; i++)
		{
			spoon[i].setLevelAttributes(level.getScrollSpeed(),level.getHorizontalPipeGap(),level.getDeathVelocity());
		}
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i].setLevelAttributes(level.getScrollSpeed());
		}
		
		birdHeight=level.getBirdYPosition();
	}

	public void stop() {
		
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
		        candywall[i][j].stop();
			}
		}
		
		for(int i=0; i<NUMBER_OF_SPOONS; i++)
		{
			spoon[i].stop();
		}
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i].stop();
		}
	}

	public Collides collides(Bird bird) {

		for(int i=0; i<NUMBER_OF_SPOONS; i++) {
			Spoon p = spoon[i];
			if (!p.isScored()
					&& p.getY() + (p.getHeight() / 2) < bird.getY() + bird.getDiameter()) {
				gameWorld.addScore(1);
				p.setScored(true);
				
				if(p.getIsBar()) {
					//Once passed the bar, it's safe to move to next level
					gameWorld.nextLevel();
				}
				break;
			}
		}

		for(int i=0; i<NUMBER_OF_SPOONS; i++) {
			
			Collides c = spoon[i].collides(bird);
			if (c!=Collides.NONE) {
			    return c;
			}
		}
		return Collides.NONE;
	}
	
	public void draw(SpriteBatch batcher, 
			Fruit[] fruit) {

		batcher.enableBlending();

		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++) {
				fallingFruit[i].draw(batcher,fruit);
		}		
	}
		
	public void drawFg(SpriteBatch batcher,
			TextureRegion bar, 
			TextureRegion headUp, 
			TextureRegion headDown, 
			TextureRegion forkup, 
			TextureRegion forkdown, 
			TextureRegion wallImage) {
		
		for(int i=0; i<NUMBER_OF_SPOONS; i++) {
			spoon[i].drawBar(batcher,bar);
			spoon[i].drawHeads(batcher,headUp,headDown,forkup,forkdown);
		}
		
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			for(int j=0; j<2; j++) {
				candywall[i][j].drawBar(batcher,wallImage);
			}
		}
		

	}


	public Scrollable[][] getGrasses() {
		return candywall;
	}
	
	public Spoon[] getPipes() {
		return spoon;
	}
	
	public void onRestart() {
		
		for(int i=0; i<NUMBER_OF_CANDYWALLS; i++) {
			candywall[i][0].onRestart(0);
			candywall[i][1].onRestart(candywall[i][0].getTailY());
		}
		
		//int pipeGap = level.getPipeGap();

		spoon[0].onRestart(pipeRestartPoint);
		for(int i=1; i<NUMBER_OF_SPOONS; i++)
		{
			spoon[i].onRestart(spoon[i].getLeader().getTailY() + pipeGap);
		}
		
		levelState=LevelState.NORMAL;
		setLevelAttributes();
		level.setProgress(NUMBER_OF_SPOONS);
		
		for(int i=0; i<NUMBER_OF_FALLING_FRUIT; i++)
		{
			fallingFruit[i].onRestart();
		}
		
	}

	public void onClick(int screenX, int screenY) {

		if(screenY<=birdHeight) return;
		
		for(int i=0; i<NUMBER_OF_SPOONS; i++)
		{
			if (spoon[i].pointYcollides(screenY)) {
				spoon[i].move(screenX);
				break;
			}
		}
		
	}

	public void endLevel() {
		levelState=LevelState.GENERATE_BAR;
	}
	
	public void nextLevel() {
		setLevelAttributes();
	}

	public void drawCollisions(ShapeRenderer shapeRenderer) {
		for(int i=0; i<NUMBER_OF_SPOONS; i++) {
			spoon[i].drawCollisions(shapeRenderer);
		}
		
	}



}
