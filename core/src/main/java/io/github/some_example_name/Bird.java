package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bird  {
    protected Sprite sprite;
    protected Body body;
    private float width;
    private float height;
    private FixtureDef fixtureDef;
    public Bird(String tp, World world,float X,float Y) {
        Texture texture = new Texture(Gdx.files.internal(tp));  // Load the texture
        this.sprite = new Sprite(texture);  // Create a sprite from the texture
        this.width=50;
        this.height=50;// Set the size of the actor
        sprite.setSize(width,height);  // Set the sprite size
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Stone will move with forces
        bodyDef.position.set(X, Y); // Initial position

        // Create the body in the Box2D world
        body = world.createBody(bodyDef);

        // Define the shape (circle for the stone)
        CircleShape shape = new CircleShape();
        shape.setRadius(10f); // Set radius of the stone

        // Define the fixture (attach the shape to the body)
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f; // Slight bounciness

        // Attach fixture to the body
        body.createFixture(fixtureDef);

        // Clean up the shape after use

        shape.dispose();
        // Set the position of the sprite to match the body
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
    }
    public void setDensity(float d){
        fixtureDef.density=d;
    }

    public void draw(Batch batch) {
        // Update the sprite's position and size to match the actor
        sprite.setPosition(body.getPosition().x,body.getPosition().y);
        sprite.setSize(width, height);
        sprite.draw(batch);  // Draw the sprite using the batch
    }


//    public void act(float delta) {
//        super.act(delta);
//        // Additional logic for updating the bird's behavior can go here
//    }

    public void dispose() {

        sprite.getTexture().dispose();  // Dispose the texture when done
    }
}
