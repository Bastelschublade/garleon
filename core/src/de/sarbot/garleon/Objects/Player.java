package de.sarbot.garleon.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import de.sarbot.garleon.Tools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sarbot on 20.03.17.
 */
public class Player implements Disposable {

    public Vector2 position; //TODO: world in iso koord or real koords?
    public Vector2 direction;
    public float radius;
    enum State {Idle, Running, Dieing, Hitting};
    public State state;
    public Body body;
    public float attackSpeed;
    public float angle;
    public float angleDelta;
    public float range;


    public float walkSpeed;
    private float frameDuration;
    public float stateTime;
    private int orientation; // 0 is left clockwise up to 7

    private Texture texture;
    private Texture headTex;
    public Vector2 textureOffset; //offset to display it correct in hitbox
    private TextureRegion[][] regions;
    private ArrayList<Animation> walkAnimations;
    private ArrayList<Animation> idleAnimations;
    private ArrayList<Animation> hitAnimations;
    private TextureRegion[][] walkRegions;
    private TextureRegion[][] idleRegions;
    private TextureRegion[][] hitRegions;

    public Sound meleeSnd;


    public Player(){

        position = new Vector2(3500,-80); //what koord is this?
        direction = new Vector2(0, 0);
        walkSpeed = 150;
        radius = 12;
        frameDuration = 10 / walkSpeed;
        state = State.Idle;
        attackSpeed = 1; //factor for animation speed and state toggle not cooldown
        range = 20; //between hitboxes

        meleeSnd = Gdx.audio.newSound(Gdx.files.internal("sounds/melee.wav"));
        angle = 0;
        angleDelta = 10;


        //TODO sinnvoll erstellen body/player eigenschaften noch trennbar?
        /*
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);
        //body = world.createBody(bodyDef);
        CircleShape playerCircle = new CircleShape();
        playerCircle.setRadius(12f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerCircle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        Fixture fixture = body.createFixture(fixtureDef);
        */

        textureOffset = new Vector2(-64, -64+25);

        texture = new Texture("characters/hero/example.png");
        headTex = new Texture("characters/hero/malehead1.png");
        regions = TextureRegion.split(texture, 128,128);


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

        hitRegions = new TextureRegion[8][4];
        hitAnimations = new ArrayList<Animation>();

        //create animations for all orientations
        for (int ori = 0; ori < regions.length; ori++ ){
            for (int f = 0; f < 4; f++){
                idleRegions[ori][f] = regions[ori][f];
            }
            for (int g = 0; g < 8; g++){
                walkRegions[ori][g] = regions[ori][g+4];
            }
            for (int h = 0; h < 4; h++){
                hitRegions[ori][h] = regions[ori][h+12];
            }
            Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, walkRegions[ori]);
            anim.setPlayMode(Animation.PlayMode.LOOP);
            walkAnimations.add(anim);
            Animation<TextureRegion> animIdle = new Animation<TextureRegion>(frameDuration, idleRegions[ori]);
            animIdle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
            idleAnimations.add(animIdle);
            Animation<TextureRegion> animHit = new Animation<TextureRegion>((float) (frameDuration /attackSpeed), hitRegions[ori]);
            hitAnimations.add(animHit);

        }


    }



    public void update(float delta, Body pBody) {
        stateTime += delta;
        //update states
        if(state!=State.Hitting || stateTime > 0.4 / attackSpeed) {
            state = State.Idle;
            float norm = Tools.isoNorm(direction.x, direction.y);
            if (norm > 0.1) {
                orientation = Tools.vector2orientation(direction);
                angle = direction.angle();
                state = State.Running;
            }
        }
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
        //else orientation = Tools.vector2orientation(direction);



    }

    public void render(Batch batch){
        Animation<TextureRegion> stateAnimation = idleAnimations.get(orientation);
        switch (state){
            case Dieing:
                stateAnimation = idleAnimations.get(orientation);
                break;
            case Hitting:
                stateAnimation = hitAnimations.get(orientation);
                break;
            case Idle:
                stateAnimation = idleAnimations.get(orientation);
                break;
            case Running:
                stateAnimation = walkAnimations.get(orientation);
                break;
        }
        batch.draw(stateAnimation.getKeyFrame(stateTime), position.x + textureOffset.x, position.y + textureOffset.y, 128, 128);
    }

    public Vector2 melee(Array<Creature> creatures){
        Vector2 target = new Vector2(position.x, position.y);
        target.x += MathUtils.cosDeg(angle);
        target.y += MathUtils.sinDeg(angle);
        //option1 calc array between player and target and if angle matches angle and distance matches range deal dmg

        for(Creature crea : creatures){
            //check distance and angle
            Vector2 delta = crea.position.cpy();
            delta.sub(position); //does not effekt crea.position ;)
            if(delta.len() < range + radius + crea.radius){
                float dmg = 0.1f * crea.currentHealth;
                crea.currentHealth -= dmg;
                Tools.message( "Dmg: " + dmg, 0);

            }
        }

        //option2 check distance from attac target (angle->range) this will result in an circle impact infront of the player
        System.out.println(angle);
        return target;

    }

    @Override
    public void dispose() {
        texture.dispose();
        headTex.dispose();
    }


}
