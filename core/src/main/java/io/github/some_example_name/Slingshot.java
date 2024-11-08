package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Slingshot {
    private Sprite sprite;
    private float x, y; // Position of the slingshot anchor
    private Bird bird;
    private Vector2 pullStartPosition; // Where pulling starts
    private float maxPullDistance = 100f; // Maximum pull distance
    private float forceScale = 0.1f; // Scale for launch force

    public Slingshot(float x, float y) {
        Texture texture = new Texture(Gdx.files.internal("abs/Slingshot.png"));
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.bird = null;
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
            bird.launched=true;

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
