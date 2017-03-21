package de.sarbot.garleon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GarleonGame extends Game {
    //TODO: sinnvoll grafiken hier zu laden, oder werden die bei jedem übergeben game=gam kopiert neu geschrieben?

    public int width;
    public int height;
    public float speed;

    private TiledMap map;
    private IsometricTiledMapRenderer mapRenderer;
    private OrthographicCamera cam;
	private StretchViewport view;
	private SpriteBatch batch;

	private Texture horseSprite;
	private TextureRegion[] horseRegion;
	private Animation<TextureRegion> horseAnimation;
	private float timer;


	@Override
	public void create () {
        width = 800;
        height = 480;
        speed = 400; //cam speed right now can be reused if cam "fixed" to player
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800,800);
        cam.position.x = Gdx.graphics.getWidth()/2;
        cam.position.y = 310;
        cam.update();
        view = new StretchViewport(width, height, cam);
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
	public void render () {
	    float delta = Gdx.graphics.getDeltaTime();
	    timer += delta;
	    handleInput(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(horseRegion[3], 900, 300);
        mapRenderer.getBatch().draw(horseAnimation.getKeyFrame(timer), 1000, 250);
        mapRenderer.getBatch().end();
        batch.begin();
        batch.end();

	}
	
	@Override
	public void dispose () {
		//this.dispose(); //does this makes sense?
	}

	public void handleInput(float delta){
	    //Arrow Keys
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            cam.position.x += speed*delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            cam.position.x -= speed*delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            cam.position.y += speed*delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            cam.position.y -= speed*delta;
        }
    }
}

//TODO: compile the game with ./gradlew html:dist
// dann liegt iwo n dist ordner, der die app enthält