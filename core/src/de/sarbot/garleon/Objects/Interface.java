package de.sarbot.garleon.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.sarbot.garleon.GarleonGame;
import de.sarbot.garleon.Tools;

import java.util.ArrayList;

/**
 * Created by sarbot on 23.03.17.
 */
public class Interface implements Disposable {

    private GarleonGame game;
    public Stage stage;
    private OrthographicCamera camera;
    private Skin skin;
    private TextureAtlas atlas;
    private Touchpad pad;
    private TouchpadStyle padStyle;
    private Joystick stick;
    private Texture button;
    private Table actionTable;
    private Button.ButtonStyle actionBS;
    private Button actionButton1;
    private Button actionButton2;
    private Button actionButton3;
    private Button actionButton4;


    private Sound melee;

    public class CustomListener extends ClickListener{
        Player player;
        Array<Creature> creatures;
        public CustomListener(Player player, Array<Creature> creatures){
            this.player = player;
            this.creatures = creatures;
        }

        @Override
        public void clicked(InputEvent event, float x, float y){
            player.state = Player.State.Hitting;
            player.stateTime = 0;
            player.meleeSnd.play();
            player.melee(creatures);
        }

    }


    public Interface (GarleonGame game){

        this.game = game;

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);
        stage = new Stage(new FillViewport(game.width, game.height, camera));
        atlas = new TextureAtlas("ui/rpg1.pack");
        skin = new Skin(atlas);
        //buttons
        skin.add("button", "button");
        //controller
        skin.add("touchBackground", new Texture("ui/pad.png"));
        skin.add("touchKnob", new Texture("ui/knob.png"));


        padStyle = new Touchpad.TouchpadStyle();
        padStyle.background = skin.getDrawable("touchBackground");
        padStyle.knob = skin.getDrawable("touchKnob");
        //Create new TouchPad with the created style
        stick = new Joystick(10, padStyle);
        //setBounds(x,y,width,height)
        stick.setBounds(15, 15, 150, 150);

        actionTable = new Table(skin);
        actionTable.setBounds(game.width-100,200, 50, 50);
        actionBS = new Button.ButtonStyle();
        actionBS.up = skin.getDrawable("button");
        actionBS.down = skin.getDrawable("button");
        actionBS.pressedOffsetX = 2;
        actionBS.pressedOffsetY = -2;
        actionButton1 = new Button(actionBS);
        actionButton2 = new Button(actionBS);
        actionButton3 = new Button(actionBS);
        actionButton4 = new Button(actionBS);
        actionButton1.addListener(new CustomListener(game.player, game.creatures));
        actionButton2.addListener(new CustomListener(game.player, game.creatures));
        actionButton3.addListener(new CustomListener(game.player, game.creatures));
        actionButton4.addListener(new CustomListener(game.player, game.creatures));
        //actionButton.setBackground(skin.getDrawable("button"));
        actionTable.row();
        actionTable.add(actionButton1);
        actionTable.row();
        actionTable.add(actionButton2);
        actionTable.row();
        actionTable.add(actionButton3);
        actionTable.row();
        actionTable.add(actionButton4);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(stick);
        stage.addActor(actionTable);


    }

    public void update(float delta) {
        stage.act();
        handleInput(delta, stick);
    }

    public void render() {
        stage.draw();

    }

    @Override
    public void dispose() {
        game.dispose();
        button.dispose();

    }

    public void handleInput(float delta, Joystick stick){


        game.player.direction.x = 0;
        game.player.direction.y = 0;

        if(stick.getKnobPercentX() > 0.01 || stick.getKnobPercentX() < -0.01){
            game.player.direction.x = stick.getKnobPercentX();
        }

        if(stick.getKnobPercentY() > 0.01 || stick.getKnobPercentY() < -0.01){
            game.player.direction.y = stick.getKnobPercentY();
        }

        game.player.body.setLinearVelocity(0,0);
        float norm = Tools.isoNorm(game.player.direction.x, game.player.direction.y);
        float vx = game.player.direction.x * game.player.walkSpeed / norm;
        float vy = game.player.direction.y * game.player.walkSpeed / norm;

        if(game.player.state != Player.State.Hitting) game.player.body.setLinearVelocity(vx, vy);
    }

    /*
    //the old one from playScreen
    public void handleInput(float delta, Joystick stick){
        //reset to defaults
        game.player.direction.x = 0;
        game.player.direction.y = 0;


        //Mouse
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            game.player.direction.x = Gdx.input.getX()-Gdx.graphics.getWidth()/2;
            game.player.direction.y = -Gdx.input.getY()+Gdx.graphics.getHeight()/2;
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            screenX = Gdx.input.getX();
            screenY = Gdx.graphics.getHeight() - Gdx.input.getY(); //inverted y libgdx/box2d
            //System.out.println(camera.unproject(new Vector3(screenX, screenY, 0)));
        }
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
    }*/

}
