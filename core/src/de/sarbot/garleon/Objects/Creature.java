package de.sarbot.garleon.Objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sarbot on 25.03.17.
 */
public class Creature {

    public Vector2 position;
    public Vector2 size;
    public float scale;
    public float maxHealth;
    public float currentHealth;
    public boolean alive;
    public String name;
    public Vector2 velocity;
    public String group;
    public Texture texture;

    public Creature(){
        position = new Vector2(0,0);
        name = "unnamed Creature";
        texture = new Texture("creatures/spider.png");
        size = new Vector2(10,10);
        scale = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
        alive = true;
        velocity = new Vector2(0,0);
        group = "none";

    }

    public void update(float delta){

    }

    public void render(Batch batch){
        batch.draw(texture, 100, 100);
    }
}
