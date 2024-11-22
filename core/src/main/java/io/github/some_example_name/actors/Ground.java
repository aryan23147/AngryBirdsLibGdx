package io.github.some_example_name.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {
    private Body groundBody;
    private Sprite sprite;
    final float PIXELS_PER_METER = 32f;  // Conversion factor

    public Ground(World world) {
        // Create the sprite for the ground
        this.sprite = new Sprite(new Texture("abs/ground.png"));
        sprite.setSize(Gdx.graphics.getWidth(), 97);  // Set sprite size in pixels

        // Create the body definition for ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, 0));  // Place ground in meters

        // Create the body in the world
        groundBody = world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundBox = new PolygonShape();
        float worldWidth = Gdx.graphics.getWidth() / PIXELS_PER_METER;  // Convert screen width to world units (meters)
        groundBox.setAsBox(worldWidth+20, 65 / PIXELS_PER_METER);  // Set width and height in meters

        // Create fixture definition and set properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 5f;
        fixtureDef.restitution = 0f;

        // Attach fixture to the body
        groundBody.createFixture(fixtureDef);

        // Clean up the shape
        groundBox.dispose();
    }

    public void draw(Batch batch) {
        // Update the sprite position to match the body's position, converting back to pixels
        sprite.setPosition(groundBody.getPosition().x * PIXELS_PER_METER, groundBody.getPosition().y * PIXELS_PER_METER);

        // Draw the sprite
        sprite.draw(batch);
    }
}
