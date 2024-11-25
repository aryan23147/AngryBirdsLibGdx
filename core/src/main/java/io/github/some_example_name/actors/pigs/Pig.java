package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.some_example_name.actors.birds.Bird.PPM;
import static io.github.some_example_name.screens.GameScreen.*;
//import static io.github.some_example_name.GameScreen1.*;

public class Pig {
    protected Sprite sprite;
    private FixtureDef fixtureDef;
    public static float ppm = 32;  // Pixels per meter conversion factor
    private float x;
    private float y;
    private SpriteBatch batch;
    private Body body;
    private float hp;
    private World world;

    public Pig(String texturePath, World world, float x, float y, float radius, float mass, float hp) {
        this.x=x;
        this.y=y;
        this.world=world;

        // Load the texture and create the sprite
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(radius*2*ppm, radius*2*ppm);  // Set bird size in pixels
//        sprite.setRotation(2f);
        createBody(world,x,y,radius);
        this.sprite.setPosition(x,y);
        body.setLinearDamping(0.2f);  // Damping slows down sliding
        this.hp=hp;
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    protected void createBody(World world, float x, float y,float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / ppm, y / ppm);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);  // Convert radius from pixels to meters

        // Create and attach the fixture
        fixtureDef = createFixture(sprite.getWidth(), sprite.getHeight());

        // Attach the fixture to the body
         Fixture fixture = body.createFixture(fixtureDef);
         fixture.setUserData(this);

        // Dispose of the shape after use
        shape.dispose();
    }

    private FixtureDef createFixture(float width, float height) {
        FixtureDef fixtureDef = new FixtureDef();

        float scaledWidth = width;
        float scaledHeight = height;

        Shape shape;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((scaledWidth / 2) / PPM); // Convert to meters
        shape = circleShape;

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 5f;
        fixtureDef.restitution = 0.6f;  // Slight bounce

        fixtureDef.filter.categoryBits = CATEGORY_BLOCK;       // Pig's category
        fixtureDef.filter.maskBits = CATEGORY_BIRD | CATEGORY_SLINGSHOT | CATEGORY_BLOCK | CATEGORY_PIG; // Collides with birds and slingshot

        return fixtureDef;
    }
    public void reduceHP(float damage) {
        hp -= damage;
    }
    public void update(){
        float scalingFactor = 0.93f; // Use this to tweak the alignment
        float spriteX = (body.getPosition().x * PPM - sprite.getWidth() / 2) * scalingFactor - body.getPosition().x * 0.01f; // Fractional offset
        float spriteY = (body.getPosition().y * PPM - sprite.getHeight() / 2) * scalingFactor - body.getPosition().y * 0.01f; // Fractional offset

        sprite.setPosition(spriteX, spriteY);

        // Set origin for rotation
        sprite.setOriginCenter();

        // Apply rotation
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void draw(Batch batch) {
        // Update sprite position to match the Box2D body and convert meters to pixels
//        sprite.setPosition(body.getPosition().x * ppm - sprite.getWidth() / 2, body.getPosition().y * ppm - sprite.getHeight() / 2);
        update();
        sprite.draw(batch);  // Draw the bird
    }

    public void disappear() {
        world.destroyBody(this.getBody());
    }

    public Body getBody() {
        return body;
    }
    public float getHp(){
        return hp;
    }

    public void dispose() {
        sprite.getTexture().dispose();  // Free the texture memory
    }
    public Vector2 getVelocity(){
        return this.getBody().getLinearVelocity();
    }
    public void setHp(float hp){
        this.hp=hp;
    }
}
