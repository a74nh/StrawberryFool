package com.sleepware.ZBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sleepware.GameObjects.Fruit;

public class AssetLoader {
	

	public static final int NUMBER_OF_FRUIT= 4;
	public static final int NUMBER_OF_FINGER_FRAMES= 6;

	private static Texture logoTexture, backgroundTexture, spoonTexture, stripeTexture, crossTexture, farBackgroundTexture;
	public static TextureRegion logoImg, zbLogo, bg, grass, bird, birdDown,
			birdUp, spoonHeadLeftImg, spoonHeadRightImg, bar, playButtonUp, playButtonDown,
			noStar, ball1, ball2, ball3, backgroundImage, forkHeadLeftImg, forkHeadRightImg, yoghurt, farBackgroundImage;
	
	public static Fruit[] fruit;

	private static Texture[] fingerTexture;
	private static TextureRegion[] finger;	
	public static Animation fingerAnimation;

	public static Texture buttonsTexture;
	public static TextureRegion[] buttons;

	public static Sound dead, flap, coin, fall;
	public static BitmapFont buttonFont, titleFont;
	private static Preferences prefs;

	public static float volume;

	
	public static void load() {

		/*********************************************************************/
		/* TEXTURES */
		/*********************************************************************/
		
		logoTexture = new Texture(Gdx.files.internal("sleepwareproductions.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logoImg = new TextureRegion(logoTexture, 0, 0, 1024, 1024);

		
		//backgroundTexture = new Texture(Gdx.files.internal("hurricaneglass2.png"));
		//backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//backgroundImage = new TextureRegion(backgroundTexture, 10, 5, 350, 666);

		farBackgroundTexture = new Texture(Gdx.files.internal("diner3.png"));
		farBackgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		farBackgroundImage = new TextureRegion(farBackgroundTexture, 250, 0, 600, 980);
		farBackgroundImage.flip(false, true);		

		backgroundTexture = new Texture(Gdx.files.internal("tumbler.png"));
		backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		backgroundImage = new TextureRegion(backgroundTexture, 22, 20, 450, 800);
		backgroundImage.flip(false, true);		
		

		//yoghurtTexture = new Texture(Gdx.files.internal("hurricaneglassyoghurt2.png"));
		//yoghurtTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		yoghurt = new TextureRegion(backgroundTexture, 556, 20, 450, 800);
		yoghurt.flip(false, true);		
		
		
		
		spoonTexture = new Texture(Gdx.files.internal("forkandspoon.png"));
		
		spoonHeadLeftImg = new TextureRegion(spoonTexture, 79, 90, 172, 85);
		spoonHeadRightImg = new TextureRegion(spoonHeadLeftImg);
		spoonHeadRightImg.flip(true, false);

		bar = new TextureRegion(spoonTexture, 0, 117, 32, 30);
		bar.flip(false, true);
		
		forkHeadLeftImg = new TextureRegion(spoonTexture, 80, 3, 172, 59);
		forkHeadRightImg = new TextureRegion(forkHeadLeftImg);
		forkHeadRightImg.flip(true, false);		

		buttons = new TextureRegion[2];

		buttonsTexture = new Texture(Gdx.files.internal("clearbuttons.png"));

		buttons[0] = new TextureRegion(buttonsTexture, 0, 0, 150, 50);
		buttons[1] = new TextureRegion(buttonsTexture, 0, 50, 150, 50);
		buttons[0].flip(false, true);
		buttons[1].flip(false, true);
		
		
		fingerTexture = new Texture[NUMBER_OF_FINGER_FRAMES];
		finger = new TextureRegion[NUMBER_OF_FINGER_FRAMES];
		
		for(int i=0; i<3; i++) {
			fingerTexture[i] = new Texture(Gdx.files.internal("finger"+i+".png"));
			fingerTexture[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
			finger[i] = new TextureRegion(fingerTexture[i], 0, 0, 46, 64);
			finger[i].flip(false, true);	
		}
		finger[3]=finger[1];
		finger[4]=finger[0];
		finger[5]=finger[0];

		fingerAnimation = new Animation(0.13f, finger);
		fingerAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		


		stripeTexture = new Texture(Gdx.files.internal("stripe.png"));
		stripeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		grass = new TextureRegion(stripeTexture, 0, 23, 64, 512-23);
		
		
		crossTexture = new Texture(Gdx.files.internal("cross.png"));
		noStar = new TextureRegion(crossTexture, 0, 0, 64, 64);

		/*********************************************************************/
		/* FRUIT */
		/*********************************************************************/

		fruit = new Fruit[NUMBER_OF_FRUIT];
		
		fruit[0] = new Fruit("strawberry", 1f, 0.3f, 0.3f, 206, 244);
		//fruit[1] = new Fruit("apple", 0.3f, 1f, 0.3f, 32, 32);
		fruit[1] = new Fruit("gooseberry", 0.3f, 1f, 0.3f, 378, 481);
		fruit[2] = new Fruit("raspberry", 0.91f, 0.09f, 0.47f, 300, 329);
		fruit[3] = new Fruit("lemon", 1f, 1f, 0.3f, 299, 225);

		//fruit[4] = new Fruit("strawberry2", 1f, 0.3f, 0.3f, 290, 282);

	
		/*********************************************************************/
		/* AUDIO */
		/*********************************************************************/
		
		dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));

		/*********************************************************************/
		/* FONTS */
		/*********************************************************************/
		
		buttonFont = new BitmapFont(Gdx.files.internal("treasure.fnt"));
		buttonFont.setScale(.16f, -.16f);
		buttonFont.setColor(0.0f, 0.5f, 0.0f, 1);

		titleFont = new BitmapFont(Gdx.files.internal("dreamfont.fnt"));
		titleFont.setScale(.5f, -.5f);

		
		/*********************************************************************/
		/* PREFERENCES */
		/*********************************************************************/
		
		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("StrawberryFool");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
		if (!prefs.contains("highLevel")) {
			prefs.putInteger("highLevel", 0);
		}
		if (!prefs.contains("sound")) {
			prefs.putBoolean("sound", true);
		}
		if (!prefs.contains("collisions")) {
			prefs.putBoolean("collisions", true);
		}
		if(getSound()) volume=1; else volume=0;
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void setHighLevel(int val) {
		prefs.putInteger("highLevel", val);
		prefs.flush();
	}

	public static int getHighLevel() {
		return prefs.getInteger("highLevel");
	}
	
	public static void setSound(boolean val) {
		prefs.putBoolean("sound", val);
		prefs.flush();
		if(val) volume=1; else volume=0;
	}

	public static boolean getSound() {
		return prefs.getBoolean("sound");
	}
	
	public static void setCollisions(boolean val) {
		prefs.putBoolean("collisions", val);
		prefs.flush();
	}

	public static boolean getCollisions() {
		return prefs.getBoolean("collisions");
	}

	
	public static void dispose() {
		// We must dispose of the texture when we are finished.
		logoTexture.dispose();
		backgroundTexture.dispose();
		spoonTexture.dispose();
		stripeTexture.dispose();
		crossTexture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		buttonFont.dispose();
		titleFont.dispose();
	}

	public static void drawTitle(SpriteBatch batcher, String s, int x, int y, float colour) {
				
		titleFont.setColor(colour);

		titleFont.draw(batcher, s, x, y);
	}
	
	public static void drawButtonText(SpriteBatch batcher, String s, int x, int y) {
		buttonFont.draw(batcher, s, x, y);
	}
	
	public static float getLineHeight() {
		return buttonFont.getLineHeight();
	}


	
}