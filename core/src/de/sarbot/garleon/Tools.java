package de.sarbot.garleon;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;

/**
 * Created by sarbot on 20.03.17.
 */
public class Tools {

    public static float isoNorm(float x, float y){
        float norm = (float) Math.sqrt(x*x + (y*2)*(y*2));

        if (norm > 0 ) return norm;
        else return -1;
    }

    public static int vector2orientation(Vector2 vec){
        //left is 0 than clockwise up to 7
        float ang = vec.angle();
        int ori = 4;
        if (ang > 22.5) ori = 3;
        if (ang > 67.5) ori = 2;
        if (ang > 112.5) ori = 1;
        if (ang > 157.5) ori = 0;
        if (ang > 202.5) ori = 7;
        if (ang > 247.5) ori = 6;
        if (ang > 292.5) ori = 5;
        if (ang > 337.5) ori = 4;
        return ori;
    }


}
