package de.sarbot.garleon.Objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.sarbot.garleon.Tools;

import java.util.ArrayList;

/**
 * Created by sarbot on 25.03.17.
 */
public class Creature implements Disposable{

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
    public float frameDuration;
    public float radius;
    public Body body;
    public BodyDef bodyDef;

    //private Skin Creatures;
    private Skin skin;
    private TextureAtlas atlas;
    public Vector2 hpBarSize;
    public float hpBarOffset;
    public float hpRel;


    private TextureRegion[][] regions;
    private TextureRegion[][] regionsWalk;
    private ArrayList<Animation> walkAnimations;
    private ArrayList<Animation> idleAnimations;
    private ArrayList<Animation> dieAnimations;
    private TextureRegion[][] walkRegions;
    private TextureRegion[][] idleRegions;
    private TextureRegion[][] dieRegions;
    private float stateTime;
    private float speednorm;



    public enum State {Idle, Running, Dieing, Hitting};
    public Creature.State state;



    public Creature(){
        position = new Vector2(3500,0);
        name = "unnamed Creature";
        size = new Vector2(128,128);
        scale = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
        hpRel = 1;
        alive = true;
        velocity = new Vector2(0,0);
        group = "none";
        radius = 12;
        hpBarSize = new Vector2(30,4);
        hpBarOffset = 100;
        atlas = new TextureAtlas("ui/interface.pack");
        skin = new Skin(atlas);


        //copy from player class
        frameDuration = (float) 0.2;
        state = Creature.State.Idle;
        //TODO: call setup cause no default textures yet

    }

    public void update(float delta){
        stateTime += delta;
        speednorm = Tools.isoNorm(velocity.x, velocity.y);
        position.x += velocity.x*delta/speednorm;
        position.y += velocity.y*delta/speednorm;
        hpRel = currentHealth/maxHealth;

    }

    public void render(Batch batch){
        int orientation = 0;

        Animation<TextureRegion> stateAnimation = idleAnimations.get(0);
        switch (state){
            case Dieing:
                stateAnimation = dieAnimations.get(0);
                break;
            case Hitting:
                stateAnimation = idleAnimations.get(orientation);
                break;
            case Idle:
                stateAnimation = idleAnimations.get(orientation);
                break;
            case Running:
                stateAnimation = walkAnimations.get(orientation);
                break;
        }
        batch.draw(stateAnimation.getKeyFrame(stateTime), position.x, position.y, 150, 150);
        drawBar(batch);
        //batch.draw(texture, 0,-10);
    }


    public void setupTextures(int idleStart, int idleStop, int walkStart,
                              int walkStop, int attacStart, int attacStop, int dieStart, int dieStop){


        regions = TextureRegion.split(texture, 128,128);
        stateTime = 0;

        walkRegions = new TextureRegion[8][walkStop-walkStart];
        walkAnimations = new ArrayList<Animation>();

        idleRegions = new TextureRegion[8][idleStop-idleStart];
        idleAnimations = new ArrayList<Animation>();

        dieRegions = new TextureRegion[8][dieStop-dieStart];
        dieAnimations = new ArrayList<Animation>();

        for (int ori = 0; ori < 8; ori++ ){
            for (int f = 0; f < walkStop-walkStart; f++){
                walkRegions[ori][f] = regions[ori][f+walkStart];
            }
            for (int g = 0; g < idleStop-idleStart; g++){
                idleRegions[ori][g] = regions[ori][g+idleStart];
            }
            for (int h = 0; h< dieStop-dieStart; h++){
                dieRegions[ori][h] = regions[ori][h+dieStart];
            }
            Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, walkRegions[ori]);
            anim.setPlayMode(Animation.PlayMode.LOOP);
            walkAnimations.add(anim);
            Animation<TextureRegion> animIdle = new Animation<TextureRegion>(frameDuration, idleRegions[ori]);
            animIdle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
            idleAnimations.add(animIdle);

            Animation<TextureRegion> animDie = new Animation<TextureRegion>(frameDuration, dieRegions[ori]);
            animDie.setPlayMode(Animation.PlayMode.LOOP);
            dieAnimations.add(animDie);

        }
    }

    public void drawBar(Batch batch){
        float posX = position.x + (size.x - hpBarSize.x)/2;
        skin.getDrawable("hp_back").draw(batch, posX, position.y + hpBarOffset,
                hpBarSize.x, hpBarSize.y);

        if(hpRel > 0.8) {
            skin.getDrawable("hp_green").draw(
                    batch, posX, position.y + hpBarOffset,
                    hpBarSize.x * hpRel, hpBarSize.y);
        }
        else if (hpRel > 0.3){
            skin.getDrawable("hp_orange").draw(
                    batch, posX, position.y + hpBarOffset,
                    hpBarSize.x* hpRel, hpBarSize.y);
        }
        else{
            skin.getDrawable("hp_red").draw(
                    batch, posX, position.y + hpBarOffset,
                    hpBarSize.x * hpRel, hpBarSize.y);
        }

    }


    @Override
    public void dispose() {
        texture.dispose();
    }

}
