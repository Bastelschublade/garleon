package de.sarbot.garleon.Objects;

import com.badlogic.gdx.utils.Array;

/**
 * Created by sarbot on 28.04.17.
 */
public class ItemSlot {
    public Item item;
    public int amount;
    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public ItemSlot(Item item, int amount){
        this.item = item;
        this.amount = amount;
    }

    public boolean isEmpty(){
        return item == null || amount <= 0;
    }

    public boolean add(Item item, int amount){
        if(this.item == item || this.item == null){
            this.item = item;
            this.amount += amount;
            notifyListeners();
            return true;
        }
        return false;
    }

    public void addListener(SlotListener slotListener){
        slotListeners.add(slotListener);
    }

    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    @Override
    public String toString(){
        return "Slot[" + item.name + ": " + amount + "]";
    }
}
