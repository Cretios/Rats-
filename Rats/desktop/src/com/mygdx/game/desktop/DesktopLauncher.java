package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RatsGdxGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Kill the Rats";
		// config.useGL20 = true;
		// config.width = 1280;
		// config.height = 720;
		config.width = 720;
		config.height = 640;

		new LwjglApplication(new RatsGdxGame(), config);
	}
}
