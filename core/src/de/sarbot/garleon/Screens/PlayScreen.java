package de.sarbot.garleon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.GarleonGame;
import de.sarbot.garleon.Objects.Joystick;

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

    private Stage hudStage;
    //move this to interface actor and let him fire the joystick actor maybe
    private Joystick stick;
    private SpriteBatch hudBatch;
    private Skin skin;
    private Touchpad.TouchpadStyle padStyle;
    private Texture pad;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private OrthographicCamera camera;
    private float blockSpeed;


    public PlayScreen(GarleonGame gam){
        game = gam;
        if(game.debug>0) System.out.println("create Play Screen");

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
        map = new TmxMapLoader().load("maps/base.tmx");
        mapRenderer = new IsometricTiledMapRenderer(map);

        horseSprite = new Texture("creatures/horse.png");
        horseRegion = TextureRegion.split(horseSprite, 128, 128)[0];
        horseAnimation = new Animation(0.1f, horseRegion);
        horseAnimation.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;

        //interface stuff
        //TODO move everything into one interface actor some how
        hudStage = new Stage();

        hudBatch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);
        skin = new Skin();
        skin.add("touchBackground", new Texture("test/pad.png"));
        skin.add("touchKnob", new Texture("test/knob.png"));
        padStyle = new Touchpad.TouchpadStyle();
        touchBackground = skin.getDrawable("touchBackground");
        touchKnob = skin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        padStyle.background = touchBackground;
        padStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        stick = new Joystick(10, padStyle);
        //setBounds(x,y,width,height)
        stick.setBounds(15, 15, 150, 150);

        hudStage = new Stage(new FillViewport(game.width, game.height, camera));
        hudStage.addActor(stick);
        Gdx.input.setInputProcessor(hudStage);


    }

    @Override
    public void render(float delta) {

        //height = 480;
        //width = height * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        //cam.setToOrtho(false, width, height);
        //float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        hudStage.act();
        handleInput(delta, stick);

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

        hudStage.draw();

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


    public void handleInput(float delta, Joystick stick){
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

        if(stick.getKnobPercentX() > 0.01 || stick.getKnobPercentX() < -0.01){
            game.player.direction.x = stick.getKnobPercentX();
        }

        if(stick.getKnobPercentY() > 0.01 || stick.getKnobPercentY() < -0.01){
            game.player.direction.y = stick.getKnobPercentY();
        }
    }
}
