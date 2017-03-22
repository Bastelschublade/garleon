package de.sarbot.garleon.Screens;

import com.badlogic.gdx.Screen;
import de.sarbot.garleon.GarleonGame;

import java.awt.*;

/**
 * Created by sarbot on 22.03.17.
 */
public class MenuScreen implements Screen{
    private GarleonGame game;

    public MenuScreen(GarleonGame gam){
        game = gam;
    }

    @Override
    public void show() {

        game.setScreen(new PlayScreen(game));
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();

    }
}
