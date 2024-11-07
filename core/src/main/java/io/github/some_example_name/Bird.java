package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bird extends Character {
    protected Sprite sprite;
    protected boolean launched;
    private FixtureDef fixtureDef;
    public static float ppm = 32;  // Pixels per meter conversion factor
    private boolean inSlingshot = true;

    public Bird(String texturePath, World world, float x, float y, float radius, float mass) {
        super(world, radius, x, y, mass, radius * 2*ppm, radius * 2*ppm );  // Convert radius to pixel size for sprite

        // Load the texture and create the sprite
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);  // Set bird size in pixels

        // Set initial state
        this.launched = false;
    }

    @Override
    protected void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / ppm, y / ppm);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);  // Convert radius from pixels to meters

        // Define the fixture
        this.fixtureDef = new FixtureDef();
        this.fixtureDef.shape = shape;
        this.fixtureDef.density = 1f;
        this.fixtureDef.friction = 0.5f;
        this.fixtureDef.restitution = 0.6f;  // Bird bounces a bit

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        shape.dispose();
    }

    public void setDensity(float density) {
        // This method doesn't modify the density after the fixture is created,
        // but you could destroy and recreate the fixture if necessary.
        fixtureDef.density = density;
    }

    public void setPosition(float x, float y){
        this.x=x;
        this.y=y;
    }
//    public void update() {
//        // Update graphical position based on Box2D body position
//        Vector2 bodyPosition = body.getPosition();
//        this.setPosition(bodyPosition.x, bodyPosition.y); // Or a specific offset if needed
//    }
    public void setInSlingshot(boolean inSlingshot) {
        this.inSlingshot = inSlingshot;
        body.setActive(!inSlingshot);  // Disable physics when in slingshot
    }

    public void update() {
        if (!inSlingshot) {
            setPosition(x, y);
        }
    }


    public void draw(Batch batch) {
        // Update sprite position to match the Box2D body and convert meters to pixels
        update();
        sprite.setPosition(body.getPosition().x * ppm - sprite.getWidth() / 2, body.getPosition().y * ppm - sprite.getHeight() / 2);
        sprite.draw(batch);  // Draw the bird
    }

    @Override
    public void disappear() {
        // Logic to remove bird from world, like world.destroyBody(body);
    }

    public void dispose() {
        sprite.getTexture().dispose();  // Free the texture memory
    }
}
