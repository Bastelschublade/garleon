package de.sarbot.garleon.Objects.Creatures;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sarbot on 25.03.17.
 */
public class Spider extends Animal {

    public Spider(int lvl, String name, Array<Vector2> positions){
        super(positions);
        super.name = name;
        super.position.x = positions.get(0).x;
        super.position.y = positions.get(0).y;
        super.maxHealth = lvl * 100;
        super.currentHealth = maxHealth;

        //TODO: maybe has to be same sized texture (exact)
        super.texture = new Texture("creatures/spider.png");
        super.setupTextures(0,4, 4, 12, 12, 16, 16, 24);
        super.hpBarOffset = 40;
        super.currentHealth = currentHealth/2;


        //TODO: call animation setup after new texture either setter in creature or all setup in seperate method and call


    }
}
