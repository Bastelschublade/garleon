package de.sarbot.garleon.Objects.Creatures;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.sarbot.garleon.Objects.Creature;

/**
 * Created by sarbot on 25.03.17.
 */
public class Animal extends Creature {

    public Animal(Array<Vector2> poss){
        super(poss);
        super.group = "Tier";

    }
}
