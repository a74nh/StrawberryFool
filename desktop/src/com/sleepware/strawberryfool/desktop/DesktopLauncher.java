package com.sleepware.strawberryfool.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sleepware.strawberryfool.StrawberryFool;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Strawberry Fool";
		//config.useGL20 = false;
		config.width = 272 *2;
		config.height = 408 *2;
		new LwjglApplication(new StrawberryFool(), config);
	}
}
