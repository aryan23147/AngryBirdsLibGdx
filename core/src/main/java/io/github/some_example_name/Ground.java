package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground {
    private BodyDef groundBodyDef;
    private Body groundBody;
    private Sprite sprite;
    final float PIXELS_PER_METER = 100f;  // Example definition

    public Ground(World world) {
        // Create the sprite for the ground
        this.sprite = new Sprite();
        sprite.setSize(Gdx.graphics.getWidth() / PIXELS_PER_METER, 10);  // Use world units for sprite size

        // Create the body definition
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 0));  // Place ground at y = 0

        // Create the body in the world
        groundBody = world.createBody(groundBodyDef);

        // Define the shape of the ground
        PolygonShape groundBox = new PolygonShape();
        float worldWidth = Gdx.graphics.getWidth() / PIXELS_PER_METER;  // Convert screen width to world units
        groundBox.setAsBox(worldWidth / 2, 0.1f);  // Set width and height in world units

        // Create fixture and attach it to the body
        groundBody.createFixture(groundBox, 0.0f);

        // Clean up the shape
        groundBox.dispose();
    }

    public void draw(Batch batch) {
        // Update the sprite position to match the body's position
        sprite.setPosition(groundBody.getPosition().x, groundBody.getPosition().y);

        // Draw the sprite
        sprite.draw(batch);
    }
}
