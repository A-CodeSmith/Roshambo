package com.acodesmith.roshambo.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.acodesmith.roshambo.Application;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Application.TITLE + " v" + Application.VERSION;
		config.width  = Application.VIRTUAL_WIDTH;
		config.height = Application.VIRTUAL_HEIGHT;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.addIcon("icons/icon-32x.png", Files.FileType.Internal);
		new LwjglApplication(new Application(), config);
	}
}
