package de.sarbot.garleon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sarbot on 20.03.17.
 */
public class Player implements Disposable {

    public Vector2 position; //TODO: world in iso koord or real koords?
    public Vector2 direction;
    enum State {Idle, Running, Dieing, Hitting};
    public State state;

    private float walkSpeed;
    private float frameDuration;
    private float stateTime;
    private int orientation; // 0 is left clockwise up to 7

    private Texture texture;
    private TextureRegion[][] regions;
    private TextureRegion[][] regionsWalk;
    private ArrayList<Animation> walkAnimations;
    private ArrayList<Animation> idleAnimations;
    private TextureRegion[][] walkRegions;
    private TextureRegion[][] idleRegions;


    public Player (){

        position = new Vector2(500,0); //what koord is this?
        direction = new Vector2(0, 0);
        walkSpeed = 150;
        frameDuration = 10 / walkSpeed;
        state = State.Idle;

        texture = new Texture("test/troll.png");
        regions = TextureRegion.split(texture, 256,256);


        /*
        animationRight = new Animation<TextureRegion>(frameDuration, (TextureRegion) regions[4][0], regions[4][11]);
        animationLeft = new Animation<TextureRegion>(frameDuration, regions[0]);
        animationRight.setPlayMode(Animation.PlayMode.LOOP);
        animationLeft.setPlayMode(Animation.PlayMode.LOOP);
        */
        stateTime = 0;

        walkRegions = new TextureRegion[8][8];
        walkAnimations = new ArrayList<Animation>();

        idleRegions = new TextureRegion[8][4];
        idleAnimations = new ArrayList<Animation>();

        //create animations for all orientations
        for (int ori = 0; ori < regions.length; ori++ ){
            for (int f = 0; f < 8; f++){
                walkRegions[ori][f] = regions[ori][f+4];
            }
            for (int g = 0; g < 4; g++){
                idleRegions[ori][g] = regions[ori][g];
            }
            Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, walkRegions[ori]);
            anim.setPlayMode(Animation.PlayMode.LOOP);
            walkAnimations.add(anim);
            Animation<TextureRegion> animIdle = new Animation<TextureRegion>(frameDuration, idleRegions[ori]);
            animIdle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
            idleAnimations.add(animIdle);

        }


    }



    public void update(float delta) {
        stateTime += delta;

        state = State.Idle;
        float norm = Tools.isoNorm(direction.x, direction.y);
        if(norm > 0.1){
            orientation = Tools.vector2orientation(direction);
            state = State.Running;
        }
        position.x += direction.x * walkSpeed * delta / norm;
        position.y += direction.y * walkSpeed * delta / norm;


    }

    public void render(Batch batch){

        Animation<TextureRegion> stateAnimation = idleAnimations.get(orientation);

        switch (state){
            case Dieing:
                stateAnimation = idleAnimations.get(orientation);
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



    }

    @Override
    public void dispose() {

    }


}
