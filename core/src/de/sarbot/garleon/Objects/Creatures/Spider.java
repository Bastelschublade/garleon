package de.sarbot.garleon.Objects.Creatures;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sarbot on 25.03.17.
 */
public class Spider extends Animal {

    public Spider(int lvl, float x, float y, String name){
        super();
        super.name = name;
        super.position.x = x;
        super.position.y = y;
        super.maxHealth = lvl * 100;

        //super.texture = new Texture("creatures/spider.png");


    }
}
