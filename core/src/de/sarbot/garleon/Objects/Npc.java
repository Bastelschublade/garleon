package de.sarbot.garleon.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sarbot on 05.05.17.
 */
public class Npc {
    public Vector2 position;
    public Vector2 size;
    public float maxHealth;
    public float currentHealth;
    public float relHealth;
    public int orientation;
    public String name;
    private TextureRegion textureRegion;

    public Npc (){
        position = new Vector2(3600,-80);
        orientation = 0;
        name = "dummy npc";
    }

}
