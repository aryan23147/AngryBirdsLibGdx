package io.github.some_example_name.actors.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.some_example_name.actors.extras.Character;
import io.github.some_example_name.bonusStuff.Power;

import static io.github.some_example_name.screens.GameScreen.*;

public class Bird extends Character {
    protected Sprite sprite;
    private boolean launched;
    private FixtureDef fixtureDef;
    public static float PPM = 32f;  // Pixels per meter conversion factor
    private Power power;
    private boolean hasUsedPower = false;

    private boolean inSlingshot = true;

    public Bird(String texturePath, World world, float x, float y, float radius, float mass, Power power) {
        super(world, radius, x, y, mass, radius * 2 * PPM, radius * 2 * PPM);  // Convert radius to pixel size for sprite

        // Load the texture and create the sprite
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);  // Set bird size in pixels
        this.sprite.setPosition(x,y);
        createBody(world, x, y);
        // Set initial state
        this.launched = false;
        this.power=power;
    }

    public float getWidth(){
        return this.width;
    }
    public float getHeight(){
        return this.height;
    }
    public boolean isLaunched() {
        return launched;
    }
    public Power getPower(){
        return this.power;
    }
    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    protected void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);
        body.setLinearDamping(0.2f);  // Damping slows down sliding

        // Create and attach the fixture
        fixtureDef = createFixture(sprite.getWidth());
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Dispose of the shape after use
        fixtureDef.shape.dispose();
    }

    private FixtureDef createFixture(float width) {
        FixtureDef fixtureDef = new FixtureDef();

        Shape shape;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((width / 2) / PPM); // Convert to meters
        shape = circleShape;

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 5f;
        fixtureDef.restitution = 0.6f;  // Slight bounce

        fixtureDef.filter.categoryBits = CATEGORY_BIRD;   // Bird's category
        fixtureDef.filter.maskBits = CATEGORY_PIG | CATEGORY_BLOCK; // Only collides with pigs
        return fixtureDef;
    }

    public void setDensity(float density) {
        // This method doesn't modify the density after the fixture is created,
        // but you could destroy and recreate the fixture if necessary.
        fixtureDef.density = density;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setInSlingshot(boolean inSlingshot) {
        this.inSlingshot = inSlingshot;
        body.setActive(!inSlingshot);  // Disable physics when in slingshot
    }

    public void resetLaunchState() {
        this.launched = false; // Reset the launch status
        this.getBody().setLinearVelocity(0, 0); // Stop any velocity the bird might have
        this.getBody().setAngularVelocity(0); // Stop rotation, if applicable
        this.getBody().setTransform(x, y, 0); // Move bird back to start position
    }

    public void update() {
        // Only update sprite position to follow Box2D body's position in world coordinates

        float scalingFactor = 0.93f; // Use this to tweak the alignment
        float spriteX = (body.getPosition().x * PPM - sprite.getWidth() / 2) * scalingFactor - body.getPosition().x * 0.01f; // Fractional offset
        float spriteY = (body.getPosition().y * PPM - sprite.getHeight() / 2) * scalingFactor - body.getPosition().y * 0.01f; // Fractional offset

        sprite.setPosition(spriteX, spriteY);

//        sprite.setPosition(0,0);

        // Set origin for rotation
        sprite.setOriginCenter();

        // Apply rotation
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void draw(Batch batch) {
        // Update sprite position to match the Box2D body and convert meters to pixels
        update();
//        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.draw(batch);  // Draw the bird
    }

    @Override
    public void disappear() {
        world.destroyBody(this.getBody());
    }

    public void dispose() {
        sprite.getTexture().dispose();  // Free the texture memory
    }

    public Body getBody() {
        return this.body;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public float getMass() {
        return mass;
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public void activatePower() {
        if (power != null && !hasUsedPower) {
            power.activate(this);
            hasUsedPower=true;
        }
    }

    public boolean hasUsedPower() {
        return hasUsedPower;
    }

    public void setSprite(Sprite sprite) {
        this.sprite=sprite;
    }
}
