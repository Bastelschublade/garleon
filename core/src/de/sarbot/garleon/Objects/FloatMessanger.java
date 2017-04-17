package de.sarbot.garleon.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sarbot on 17.04.17.
 */
public class FloatMessanger extends Actor {

ArrayList< FloatMessage> messages;
BitmapFont font;

    public FloatMessanger (){
        messages = new ArrayList<FloatMessage>();
        font = new BitmapFont();

    }

    public void newMessage(String msg, float x, float y){
        FloatMessage newMsg = new FloatMessage(msg, (int) x,(int) y);
        messages.add(newMsg);
        System.out.println("new flmsg: " + msg);
    }

    public void update (float delta) {
        Iterator<FloatMessage> iterator = messages.iterator();
        while(iterator.hasNext()){
            FloatMessage msg = iterator.next();
            msg.timer += delta;
            if(msg.timer > 0.5){
                iterator.remove();
            }
        }

    }

    @Override
    public void act(float delta){
        update(delta);
    }



    @Override
    public void draw (Batch batch, float parentAlpha){
        Iterator<FloatMessage> iterator = messages.iterator();
        while (iterator.hasNext()){
            FloatMessage flmsg = iterator.next();
            font.draw(batch, flmsg.msg, flmsg.startPosition.x, flmsg.startPosition.y + flmsg.timer
                    * flmsg.movespeed);
        }
    }


    public void render () {

    }
}
