package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.some_example_name.Bird.PPM;

public class Pig {
    protected Sprite sprite;
    private FixtureDef fixtureDef;
    public static float ppm = 32;  // Pixels per meter conversion factor
    private float x;
    private float y;
    private SpriteBatch batch;
    private Body body;


    public Pig(String texturePath, World world, float x, float y, float radius, float mass) {
        this.x=x;
        this.y=y;

        // Load the texture and create the sprite
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(radius*2*ppm, radius*2*ppm);  // Set bird size in pixels
//        sprite.setRotation(float());
        createBody(world,x,y,radius);
        body.setLinearDamping(0.2f);  // Damping slows down sliding
        // Set initial state
//        this.launched = false;
    }

    protected void createBody(World world, float x, float y,float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / ppm, y / ppm);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);  // Convert radius from pixels to meters

//        // Define the fixture
//        this.fixtureDef = new FixtureDef();
//        this.fixtureDef.shape = shape;
//        this.fixtureDef.density = 1f;
//        this.fixtureDef.friction = 5f;
//        this.fixtureDef.restitution = 0.6f;  // Pig bounces a bit

        // Create and attach the fixture
        fixtureDef = createFixture(sprite.getWidth(), sprite.getHeight());

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

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
        return fixtureDef;
    }

    public void setDensity(float density) {
        // This method doesn't modify the density after the fixture is created,
        // but you could destroy and recreate the fixture if necessary.
        fixtureDef.density = density;
    }

    public void draw(Batch batch) {
        // Update sprite position to match the Box2D body and convert meters to pixels
        sprite.setPosition(body.getPosition().x * ppm - sprite.getWidth() / 2, body.getPosition().y * ppm - sprite.getHeight() / 2);
        sprite.draw(batch);  // Draw the bird
    }


    public void disappear() {
        // Logic to remove bird from world, like world.destroyBody(body);
    }

    public void dispose() {
        sprite.getTexture().dispose();  // Free the texture memory
    }
}
