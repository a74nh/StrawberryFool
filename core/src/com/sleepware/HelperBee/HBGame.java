package com.sleepware.HelperBee;

import com.badlogic.gdx.Game;
import com.sleepware.Screens.GameScreen;
import com.sleepware.Screens.SplashScreen;
import com.sleepware.ZBHelpers.AssetLoader;

public class HBGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		//setScreen(new SplashScreen(this));
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}