package de.sarbot.garleon.Objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sarbot on 17.04.17.
 */
public class FloatMessage {
    Vector2 startPosition;
    public String msg;
    float timer;
    float movespeed;

    public FloatMessage(String msg, int posX, int posY){
        this.msg = msg;
        startPosition = new Vector2(posX, posY);
        timer = 0;
        movespeed = 150;
    }
}
