package de.sarbot.garleon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.GarleonGame;

/**
 * Created by sarbot on 22.03.17.
 */
public class PlayScreen implements Screen{
    private GarleonGame game;
    private TiledMap map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera cam;
    private StretchViewport view;
    private SpriteBatch batch;
    private Texture horseSprite;
    private TextureRegion[] horseRegion;
    private Animation<TextureRegion> horseAnimation;
    private float timer;

    public PlayScreen(GarleonGame gam){
        game = gam;

    }


    @Override
    public void show() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.width,game.height);
        cam.position.x = Gdx.graphics.getWidth()/2;
        cam.position.y = 310;
        view = new StretchViewport(game.width, game.height, cam); //does nothing atm
        view.apply();
        cam.update(); //calls the view? no..
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("test/map2.tmx");
        mapRenderer = new IsometricTiledMapRenderer(map);

        horseSprite = new Texture("test/horse.png");
        horseRegion = TextureRegion.split(horseSprite, 128, 128)[0];
        horseAnimation = new Animation(0.1f, horseRegion);
        horseAnimation.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;

    }

    @Override
    public void render(float delta) {

        //height = 480;
        //width = height * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        //cam.setToOrtho(false, width, height);
        //float delta = Gdx.graphics.getDeltaTime();
        timer += delta;
        handleInput(delta);

        game.player.update(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.x = game.player.position.x;
        cam.position.y = game.player.position.y;
        cam.update();

        //render map
        mapRenderer.setView(cam);
        mapRenderer.render();

        //render objects into map
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(horseRegion[3], 900, 300);
        mapRenderer.getBatch().draw(horseAnimation.getKeyFrame(timer), 1000, 250);
        mapRenderer.getBatch().draw(horseRegion[1], 0f, 2*32f, 100, 100);
        game.player.render(mapRenderer.getBatch());
        //System.out.print(mapRenderer.getUnitScale());
        mapRenderer.getBatch().end();

        //render interface
        batch.begin();
        //batch.draw(horseRegion[1], 0f, 0);
        batch.end();

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


    public void handleInput(float delta){
        //Arrow Keys
        game.player.direction.x = 0;
        game.player.direction.y = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            game.player.direction.x += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            game.player.direction.x -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            game.player.direction.y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            game.player.direction.y -= 1;
        }
    }
}
