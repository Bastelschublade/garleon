package de.sarbot.garleon.Objects;

/**
 * Created by sarbot on 28.04.17.
 */
public class Item {
    public int id;
    public String name;
    public String icon;

    //later
    public float price;
    public float weight;
    int slot; //where to wear this item -1 = not
    boolean activ;


    public Item (int id){
        this.id = id;
        //TODO: create dict or json DB to read settings from id
        this.name = "unnamed";
        this.icon = "knife";
    }
}
