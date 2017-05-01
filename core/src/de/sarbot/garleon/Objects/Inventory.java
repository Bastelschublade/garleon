package de.sarbot.garleon.Objects;

import com.badlogic.gdx.utils.Array;

/**
 * Created by sarbot on 28.04.17.
 */
public class Inventory {
    Array<ItemSlot> slots;

    public Inventory(){
        slots = new Array<ItemSlot>(10);
        for(int i = 0; i<10; i++){
            
        }
    }
}
