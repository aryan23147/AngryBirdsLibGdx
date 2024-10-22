package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig extends Character {
    private Sprite sprite;
    private Body body;

    public Pig(float radius, float mass, String texturePath, int hp, float x, float y, World world) {
        super(world, radius, x, y, mass);
        this.world = world;

        // Create sprite and load texture
        Texture texture = new Texture(texturePath);
        sprite = new Sprite(texture);
        sprite.setSize(radius * 2, radius * 2); // Set sprite size based on radius

        // Box2D body setup
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Pigs don't move
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = mass / (float) Math.PI * radius * radius; // Set density relative to size
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Dynamic for movement
        bodyDef.position.set(x, y);

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        CircleShape shape = new CircleShape();
        shape.setRadius(10f);  // Set the radius of the bird

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
        // Set the position of the sprite to match the body
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (hp <= 0) {
            this.disappear();
        }
    }

    public void disappear() {
        world.destroyBody(body); // Remove from Box2D world
        sprite.getTexture().dispose(); // Dispose texture
    }

//    public void draw(Batch batch) {
//        // Sync sprite position and size with body
//        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
//        sprite.draw(batch);
//    }
}
