package com.leisure.duncraw;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.leisure.duncraw.input.*;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(0);
    config.setWindowedMode(1020, 620);
		config.setTitle("Dungeon Crawler");

    GameApplication game = new GameApplication(()->new SplashScreenInput());
		new Lwjgl3Application(game, config);
	}
}
