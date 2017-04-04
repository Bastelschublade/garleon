package de.sarbot.garleon;

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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.Objects.Creature;
import de.sarbot.garleon.Screens.BootScreen;
import de.sarbot.garleon.Objects.Player;

import java.util.ArrayList;

public class GarleonGame extends Game {
    //TODO: sinnvoll grafiken hier zu laden, oder werden die bei jedem übergeben game=gam kopiert neu geschrieben?
    //TODO: organize classes (super implement extend parent/child?)



    public int width;
    public int height;
    public float speed;
    public float zoom;
    public int debug; //0 off, 1 basic, 2 loops, 3 everything

    public Player player;
    public Array<Creature> creatures;








	@Override
	public void create () {

	    //setup the window and stuff
        height = 480;
        width = height * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        debug = 1;

        //setup the game
        speed = 1; //general game speed
        zoom = 1; //general zoom (world)
        player = new Player();

        //boot the game
        if (debug>0) System.out.println("booted");
        setScreen(new BootScreen(this));


	}


	@Override
	public void render () {


        super.render();

	}
	
	@Override
	public void dispose () {
	    super.dispose();
		//this.dispose(); //does this makes sense?
	}


}

//TODO: compile the game with ./gradlew html:dist
// dann liegt iwo n dist ordner, der die app enthält