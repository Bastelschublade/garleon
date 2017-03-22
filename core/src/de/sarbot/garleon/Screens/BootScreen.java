package de.sarbot.garleon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.GarleonGame;

/**
 * Created by sarbot on 22.03.17.
 */
public class BootScreen implements Screen {

    private GarleonGame game;
    private OrthographicCamera cam;
    private StretchViewport view;


    public BootScreen(GarleonGame gam){
        game = gam;
        if(game.debug > 0){
            System.out.println("OK | BootScreen() called");
        }

    }

    @Override
    public void show() {

        game.setScreen(new MenuScreen(game));
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
