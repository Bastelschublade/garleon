package de.sarbot.garleon.Objects.Creatures;


import com.badlogic.gdx.graphics.Texture;

/**
 * Created by sarbot on 25.03.17.
 */
public class Oga extends Human {

    public Oga(int lvl, float x, float y, String name){
        super();
        super.name = name;
        super.frameDuration = (float) 0.1;
        super.position.x = x;
        super.position.y = y;
        super.maxHealth = lvl * 100;
        super.currentHealth = maxHealth;

        //TODO: maybe has to be same sized texture (exact)
        super.texture = new Texture("creatures/oga.png");
        super.state = State.Running;
        super.setupTextures(0,4, 4, 12, 12, 16, 20, 24);
        super.hpBarOffset = 130;

        //TODO: call animation setup after new texture either setter in creature or all setup in seperate method and call


    }
}
