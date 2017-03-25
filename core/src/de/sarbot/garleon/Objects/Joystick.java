package de.sarbot.garleon.Objects;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import de.sarbot.garleon.GarleonGame;

/**
 * Created by sarbot on 24.03.17.
 */
public class Joystick extends Touchpad {



    public Joystick(float deadzoneRadius, TouchpadStyle style) {

        super(deadzoneRadius, style);
    }

    @Override
    public void act(float delta){
        super.act(delta); //runs the default act for this actor (moving the knob on the pad)

        //todo dont hardcode here

    }

}
