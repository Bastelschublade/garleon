package de.sarbot.garleon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.sarbot.garleon.GarleonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1200;
		new LwjglApplication(new GarleonGame(), config);
	}
}
