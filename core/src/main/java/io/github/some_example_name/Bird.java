package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bird extends Character {
    protected Sprite sprite;
    protected boolean launched;
    private FixtureDef fixtureDef;

    public Bird(String texturePath, World world, float x, float y, float radius, float mass) {
        super(world, radius, x, y, mass, 50, 50);  // Call the Character constructor

        // Load the texture and create the sprite
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);  // Set bird size

        // Set initial state
        this.launched = false;
    }

    @Override
    protected void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);  // Use the radius passed to the constructor

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

    public void draw(Batch batch) {
        // Update sprite position to match the Box2D body
        sprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
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
