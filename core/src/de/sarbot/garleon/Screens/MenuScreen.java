package de.sarbot.garleon.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.garleon.GarleonGame;

/**
 * Created by sarbot on 28.02.17.
 */
public class MenuScreen implements Screen {

    private GarleonGame game;
    private Texture background;
    private Stage stage;
    private TextureAtlas atlas;
    private Table table;
    private TextButton buttonExit, buttonPlay, buttonLevel;
    private BitmapFont menuFont;
    private Label heading;
    private Skin skin;
    private SpriteBatch batch;
    private StretchViewport myViewport;
    private OrthographicCamera myCam;

    public MenuScreen(GarleonGame gam){
        game = gam;
    }



    @Override
    public void show() {
        myCam = new OrthographicCamera(800, 480);
        myCam.position.x = Gdx.graphics.getWidth()/2;
        myCam.position.x = Gdx.graphics.getHeight()/2;
        myCam.update();
        myViewport = new StretchViewport(800, 480, myCam);


        background = new Texture("ui/paper.png");
        atlas = new TextureAtlas("ui/interface.pack");
        menuFont = new BitmapFont();


        stage = new Stage(myViewport);
        //stage.setViewport(myViewport); //button not clickable if viewport on, fixed somehow using own or using resize


        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, 800, 480);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menubutton");
        textButtonStyle.down = skin.getDrawable("menubuttonp");
        textButtonStyle.font = menuFont;
        textButtonStyle.pressedOffsetX = 2;
        textButtonStyle.pressedOffsetY = -2;

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(0,10,0,10);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });
        buttonLevel = new TextButton("Options", textButtonStyle);
        buttonLevel.pad(0,10,0,10);
        buttonLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new LevelScreen(game));
            }
        });

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.pad(0, 10, 0, 10);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(menuFont, Color.WHITE);
        heading = new Label("Heading", headingStyle);
        heading.setFontScale(2);


        table.add(heading);
        table.getCell(heading).padBottom(40);
        table.row();
        table.add(buttonPlay);
        table.row();
        table.add(buttonLevel);
        table.row();
        table.add(buttonExit);
        //table.debug();

        Gdx.input.setInputProcessor(stage); //stage is handling user input
        stage.addActor(table);
        //myViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.begin();
        //batch.draw(background, 0, 0, 800, 480);
        //batch.end();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0,0,800,480);
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        myViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        //stage.setViewport(game.viewport);

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

        stage.dispose();
        atlas.dispose();
        skin.dispose();
        menuFont.dispose();
    }
}
