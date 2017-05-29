package de.sarbot.garleon.Objects.Creatures;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sarbot on 25.03.17.
 */
public class Goblin extends Human {

    public Goblin(int lvl, String name, Array<Vector2> positions){
        super(positions);
        super.name = name;
        //super.position.x = positions.get(0).x;
        //super.position.y = positions.get(0).y;
        super.maxHealth = lvl * 100;

        //TODO: maybe has to be same sized texture (exact)
        super.texture = new Texture("creatures/goblin.png");
        super.state = State.Running;
        super.setupTextures(0,4, 12, 20, 16, 20, 20, 28);
        super.hpBarOffset = 50;

        super.state = State.Running;
        //TODO: call animation setup after new texture either setter in creature or all setup in seperate method and call


    }

}
