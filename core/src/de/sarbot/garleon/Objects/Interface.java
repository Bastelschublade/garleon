package de.sarbot.garleon.Objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Disposable;
import de.sarbot.garleon.GarleonGame;

/**
 * Created by sarbot on 23.03.17.
 */
public class Interface extends Actor implements Disposable {

    private GarleonGame game;
    private Stage stage;
    private Skin skin;
    private Touchpad pad;
    private TouchpadStyle padStyle;


    public Interface (GarleonGame game){
        this.game = game;
        stage = new Stage();
    }

    public void update(float delta) {

    }

    public void render() {

    }

    @Override
    public void dispose() {
        game.dispose();

    }
}
