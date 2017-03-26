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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.GarleonGame;
import de.sarbot.garleon.Objects.Creatures.Oga;
import de.sarbot.garleon.Objects.Creatures.Spider;
import de.sarbot.garleon.Objects.Creatures.Wolf;
import de.sarbot.garleon.Objects.Joystick;
import de.sarbot.garleon.Tools;

import java.awt.geom.RectangularShape;

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

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private CircleShape playerCircle;
    private CircleShape testCircle;
    private PolygonShape testPoly;
    private Body playerBody;
    private Array<Body> bodies;
    private MapLayer collisionLayer;

    private Spider spider;
    private Oga oga;
    private Wolf wolf;


    public PlayScreen(GarleonGame gam){
        game = gam;
        Box2D.init();

    }

    @Override
    public void show() {
        world = new World(new Vector2(0,0), true);
        debugRenderer = new Box2DDebugRenderer();
        bodies = new Array<Body>();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(game.player.position.x, game.player.position.y);
        playerBody = world.createBody(bodyDef);
        CircleShape playerCircle = new CircleShape();
        playerCircle.setRadius(12f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerCircle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        Fixture fixture = playerBody.createFixture(fixtureDef);


        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.width,game.height);
        cam.position.x = Gdx.graphics.getWidth()/2;
        cam.position.y = 310;
        view = new StretchViewport(game.width, game.height, cam); //does nothing atm
        view.apply();
        cam.update(); //calls the view? no..
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("maps/base.tmx");
        parseCollisionLayer();
        mapRenderer = new IsometricTiledMapRenderer(map);
        timer = 0;


        spider = new Spider(8,3500, -80,"Tekla");
        wolf = new Wolf( 8, 3500, -300, "hugo");
        oga = new Oga(8, 3200, -150, "tibbers");



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

        game.player.update(delta, playerBody);


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
        game.player.render(mapRenderer.getBatch());
        //System.out.print(mapRenderer.getUnitScale());
        spider.update(delta);
        wolf.update(delta);
        oga.update(delta);
        spider.render(mapRenderer.getBatch());
        wolf.render(mapRenderer.getBatch());
        oga.render(mapRenderer.getBatch());
        mapRenderer.getBatch().end();

        //render interface
        batch.begin();
        //batch.draw(horseRegion[1], 0f, 0);
        batch.end();

        hudStage.draw();
        debugRenderer.render(world, cam.combined); //cam for map camera for hud
        world.step(1/60f, 6, 2);

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
        playerCircle.dispose();


    }


    public void handleInput(float delta, Joystick stick){
        //reset to defaults
        game.player.direction.x = 0;
        game.player.direction.y = 0;


        //Arrow Keys
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

        //joystick
        if(stick.getKnobPercentX() > 0.01 || stick.getKnobPercentX() < -0.01){
            game.player.direction.x = stick.getKnobPercentX();
        }

        if(stick.getKnobPercentY() > 0.01 || stick.getKnobPercentY() < -0.01){
            game.player.direction.y = stick.getKnobPercentY();
        }

        playerBody.setLinearVelocity(0,0);
        float norm = Tools.isoNorm(game.player.direction.x, game.player.direction.y);
        float vx = game.player.direction.x * game.player.walkSpeed / norm;
        float vy = game.player.direction.y * game.player.walkSpeed / norm;

        playerBody.setLinearVelocity(vx, vy);
    }

    private void parseCollisionLayer(){
        if(game.debug>0){System.out.println("create Collision Bodies");}
        collisionLayer = map.getLayers().get("collisionObjects");

        //print names of layers (debug)
        if(game.debug>0){
            for(MapObject colObj : collisionLayer.getObjects()){
                System.out.println("found Object with name: " + colObj.getName());
            }
        }
        MapObjects collisionObjects = map.getLayers().get("collisionObjects").getObjects();
        //TODO: if none dont run the loop
        for (MapObject obj : collisionObjects){
            Shape shape;
            if (obj instanceof PolygonMapObject){
                System.out.println("found polygon: "+ obj.getName());
                shape = getPolygon((PolygonMapObject) obj);
            }
            /*
            else if(obj instanceof TextureMapObject){//images
                return;
            }
            else if (obj instanceof RectangleMapObject){
                System.out.println("found rectangle: "+ obj.getName());
            }
            else if (obj instanceof CircleMapObject){
                System.out.println("found circle: "+obj.getName());
            }*/

            else if (obj instanceof PolylineMapObject){
                System.out.println("found polyline: "+obj.getName());
                shape = getPolyChain((PolylineMapObject) obj);
            }

            else{
                continue;
            }


            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);
            bodies.add(body);
            shape.dispose();
        }
        return;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {

        PolygonShape polygon = new PolygonShape();
        //float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        //Vector2 offset = new Vector2(polygonObject.getProperties().get("x").toString(), polygonObject.getProperties().get("y").toString());
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];
        float yOff = 16;

        for (int i = 0; i < vertices.length ; i+=2) { //TODO: make sure this does not break out.. its always even number?

            System.out.println(vertices[i]);
            //worldVertices[i] = vertices[i] / 1; //ppt
            Vector2 worldPoint = Tools.tiled2world(vertices[i], vertices[i + 1]);
            worldVertices[i] = worldPoint.x;
            worldVertices[i+1] = worldPoint.y + yOff;

        }

        polygon.set(worldVertices);
        return polygon;

    }

    private static ChainShape getPolyChain(PolylineMapObject polylineObject) {


        //float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        //Vector2 offset = new Vector2(polygonObject.getProperties().get("x").toString(), polygonObject.getProperties().get("y").toString());
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];
        float yOff = 16;

        for (int i = 0; i < vertices.length ; i+=2) { //TODO: make sure this does not break out.. its always even number?

            System.out.println(vertices[i]);
            //worldVertices[i] = vertices[i] / 1; //ppt
            Vector2 worldPoint = Tools.tiled2world(vertices[i], vertices[i + 1]);
            worldVertices[i] = worldPoint.x;
            worldVertices[i+1] = worldPoint.y + yOff;

        }

        //chain.set(worldVertices);
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;

    }

    private void parseMobLayer(){

        return;
    }

}
