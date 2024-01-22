package com.leisure.duncraw;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.leisure.duncraw.GameApplication;
import com.leisure.duncraw.input.*;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		GameApplication game = new GameApplication(()->new SplashScreenInput());
		initialize(game, config);
	}
}
