package io.github.some_example_name.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.some_example_name.actors.Bird.PPM;
import static io.github.some_example_name.screens.GameScreen.*;

public class Slingshot {
    private Sprite sprite;
    private float x, y; // Position of the slingshot anchor
    private Bird bird;
    private Body body;

    private Vector2 pullStartPosition; // Where pulling starts
    private float maxPullDistance = 100f; // Maximum pull distance
    private float forceScale = 0.075f; // Scale for launch force
//    private float forceScale = 0.1f; // Scale for launch force

    public Slingshot(float x, float y, World world) {
        Texture texture = new Texture(Gdx.files.internal("abs/Slingshot.png"));
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.bird = null;

        float[] slingshotVertices = new float[] {
            50 / PPM, 180 / PPM,
            160 / PPM, 180 / PPM,
            100 / PPM, 0 / PPM
        };
        createSlingshotBody(world, x, y, slingshotVertices);
    }

    protected void createSlingshotBody(World world, float x, float y, float[] vertices) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;  // Slingshot doesn't move
        bodyDef.position.set(x / PPM, y / PPM);      // Convert position to meters

        // Create the body
        this.body = world.createBody(bodyDef);

        // Define the shape of the slingshot using a PolygonShape
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);  // Vertices should be defined relative to the body's position

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;      // Static bodies typically don't need density
        fixtureDef.friction = 0.5f;   // Adjust based on desired friction
        fixtureDef.restitution = 0.2f; // Small bounce for interaction

        fixtureDef.filter.categoryBits = CATEGORY_SLINGSHOT; // Slingshot's category
        fixtureDef.filter.maskBits = CATEGORY_PIG | CATEGORY_BLOCK;// Only collides with pigs

        // Attach the fixture to the body
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);  // Set user data for collision callbacks

        // Dispose of the shape
        shape.dispose();
    }


    public void draw(Batch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);

        // Optional: Add code to draw the slingshot bands if you want visual feedback
    }

    public boolean isEmpty() {
        return bird == null;
    }

    public void loadBird(Bird bird) {
        if (this.bird == null) {
            this.bird = bird;
            // Place bird at the slingshot's position initially
//            bird.setPosition(x, y);
        }
    }

    public void startPull(Vector2 touchStart) {
        if (bird != null) {
            pullStartPosition = touchStart; // Track where the pull started
        }
    }

    public void pull(Vector2 touchCurrent) {
        if (bird != null && pullStartPosition != null) {
            Vector2 pullVector = touchCurrent.cpy().sub(new Vector2(x, y));
            float pullDistance = pullVector.len();

            if (pullDistance > maxPullDistance) {
                pullVector.nor().scl(maxPullDistance); // Limit the pull distance
            }

            // Set bird's position based on pullback
            bird.setPosition(x + pullVector.x, y + pullVector.y);
        }
    }

    public void releaseBird(float x, float y) {
        if (bird != null) {
            Vector2 launchDirection = new Vector2(x, y);
            float pullDistance = launchDirection.len();

            // Calculate the launch force based on pullback distance and scale
            Vector2 launchForce = launchDirection.nor().scl(pullDistance * forceScale);

            // Call the launch method with the calculated force
            launch(bird, launchForce);
            bird.setLaunched(true);

            Sound birdShotSound = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
            birdShotSound.play();

            // Reset the bird in the slingshot after release
            bird = null;
        }
    }

    private void launch(Bird bird, Vector2 force) {
        // Activate bird's physics body and apply the force for launching
        bird.getBody().setActive(true);
        bird.getBody().applyLinearImpulse(force, bird.getBody().getWorldCenter(), true);
    }
}
