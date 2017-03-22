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
	private Player player;


	@Override
	public void create () {
        height = 480;
        width = height * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        speed = 400; //cam speed right now can be reused if cam "fixed" to player
        cam = new OrthographicCamera();
        cam.setToOrtho(false, width,height);
        cam.position.x = Gdx.graphics.getWidth()/2;
        cam.position.y = 310;
        cam.update();
        view = new StretchViewport(width, height, cam); //does nothing atm
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("test/map2.tmx");
        mapRenderer = new IsometricTiledMapRenderer(map);

        horseSprite = new Texture("test/horse.png");
        horseRegion = TextureRegion.split(horseSprite, 128, 128)[0];
        horseAnimation = new Animation(0.1f, horseRegion);
        horseAnimation.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;

        player = new Player();
	}


	@Override
	public void render () {

        //height = 480;
        //width = height * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        //cam.setToOrtho(false, width, height);
	    float delta = Gdx.graphics.getDeltaTime();
	    timer += delta;
	    handleInput(delta);

	    player.update(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.position.x = player.position.x;
        cam.position.y = player.position.y;
        cam.update();

        //render map
        mapRenderer.setView(cam);
        mapRenderer.render();

        //render objects into map
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(horseRegion[3], 900, 300);
        mapRenderer.getBatch().draw(horseAnimation.getKeyFrame(timer), 1000, 250);
        mapRenderer.getBatch().draw(horseRegion[1], 0f, 2*32f, 100, 100);
        player.render(mapRenderer.getBatch());
        //System.out.print(mapRenderer.getUnitScale());
        mapRenderer.getBatch().end();

        //render interface
        batch.begin();
        batch.draw(horseRegion[1], 0f, 0);
        batch.end();

	}
	
	@Override
	public void dispose () {
		//this.dispose(); //does this makes sense?
	}

	public void handleInput(float delta){
	    //Arrow Keys
        player.direction.x = 0;
        player.direction.y = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.direction.x += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.direction.x -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.direction.y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.direction.y -= 1;
        }
    }
}

//TODO: compile the game with ./gradlew html:dist
// dann liegt iwo n dist ordner, der die app enthält