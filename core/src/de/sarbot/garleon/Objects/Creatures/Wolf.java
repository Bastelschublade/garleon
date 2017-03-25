package de.sarbot.garleon.Objects.Creatures;


import com.badlogic.gdx.graphics.Texture;

/**
 * Created by sarbot on 25.03.17.
 */
public class Wolf extends Animal {

    public Wolf(int lvl, float x, float y, String name){
        super();
        super.name = name;
        super.position.x = x;
        super.position.y = y;
        super.maxHealth = lvl * 100;

        //TODO: maybe has to be same sized texture (exact)
        super.texture = new Texture("creatures/wolf.png");
        super.state = State.Running;
        super.setupTextures(0,8, 8, 16, 16, 20, 20, 28);

        //TODO: call animation setup after new texture either setter in creature or all setup in seperate method and call


    }
}
