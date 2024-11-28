package io.github.some_example_name.actors.extras;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import io.github.some_example_name.actors.birds.Bird;
//
//import static io.github.some_example_name.actors.birds.Bird.PPM;
//import static io.github.some_example_name.screens.GameScreen.*;
////import static io.github.some_example_name.GameScreen1.*;
//
//public class Slingshot {
//    private Sprite sprite;
//    private float x, y; // Position of the slingshot anchor
//    private Bird bird, launchedBird;
//    private Body body;
//
//    private Vector2 pullStartPosition; // Where pulling starts
//    private float maxPullDistance = 100f; // Maximum pull distance
////    private float forceScale = 0.05f; // Scale for launch force
//    private float forceScale = 0.075f; // Scale for launch force
////    private float forceScale = 0.1f; // Scale for launch force
//
//    public Slingshot(float x, float y, World world) {
//        Texture texture = new Texture(Gdx.files.internal("abs/Slingshot.png"));
//        this.sprite = new Sprite(texture);
//        this.x = x;
//        this.y = y;
//        this.bird = null;
//
//        float[] slingshotVertices = new float[] {
//            50 / PPM, 180 / PPM,
//            160 / PPM, 180 / PPM,
//            100 / PPM, 0 / PPM
//        };
//        createSlingshotBody(world, x, y, slingshotVertices);
//    }
//
//    protected void createSlingshotBody(World world, float x, float y, float[] vertices) {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;  // Slingshot doesn't move
//        bodyDef.position.set(x / PPM, y / PPM);      // Convert position to meters
//
//        // Create the body
//        this.body = world.createBody(bodyDef);
//
//        // Define the shape of the slingshot using a PolygonShape
//        PolygonShape shape = new PolygonShape();
//        shape.set(vertices);  // Vertices should be defined relative to the body's position
//
//        // Define the fixture
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 0f;      // Static bodies typically don't need density
//        fixtureDef.friction = 0.5f;   // Adjust based on desired friction
//        fixtureDef.restitution = 0.2f; // Small bounce for interaction
//
//        fixtureDef.filter.categoryBits = CATEGORY_SLINGSHOT; // Slingshot's category
//        fixtureDef.filter.maskBits = CATEGORY_PIG | CATEGORY_BLOCK;// Only collides with pigs
//
//        // Attach the fixture to the body
//        Fixture fixture = body.createFixture(fixtureDef);
//        fixture.setUserData(this);  // Set user data for collision callbacks
//
//        // Dispose of the shape
//        shape.dispose();
//    }
//
//
//    public void draw(Batch batch) {
//        sprite.setPosition(x, y);
//        sprite.draw(batch);
//
//        // Optional: Add code to draw the slingshot bands if you want visual feedback
//    }
//
//    public boolean isEmpty() {
//        return bird == null;
//    }
//
//    public void loadBird(Bird bird) {
//        if (this.bird == null) {
//            this.bird = bird;
//            // Place bird at the slingshot's position initially
////            bird.setPosition(x, y);
//        }
//    }
//
//    public void startPull(Vector2 touchStart) {
//        if (bird != null) {
//            pullStartPosition = touchStart; // Track where the pull started
//        }
//    }
//
//    public void pull(Vector2 touchCurrent) {
//        if (bird != null && pullStartPosition != null) {
//            Vector2 pullVector = touchCurrent.cpy().sub(new Vector2(x, y));
//            float pullDistance = pullVector.len();
//
//            if (pullDistance > maxPullDistance) {
//                pullVector.nor().scl(maxPullDistance); // Limit the pull distance
//            }
//
//            // Set bird's position based on pullback
//            bird.setPosition(x + pullVector.x, y + pullVector.y);
//        }
//    }
//
//    public void releaseBird(float x, float y) {
//        if(launchedBird == null){
//            Vector2 launchDirection = new Vector2(x, y);
//            float pullDistance = launchDirection.len();
//
//            // Calculate the launch force based on pullback distance and scale
//            Vector2 launchForce = launchDirection.nor().scl(pullDistance * forceScale);
//
//            // Call the launch method with the calculated force
//            launch(bird, launchForce);
//            bird.setLaunched(true);
//
//            Sound birdShotSound = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
//            birdShotSound.play();
//
//            // Reset the bird in the slingshot after release
//            launchedBird=bird;
//            bird = null;
//        }
//
//
//        else if(!launchedBird.hasUsedPower()){
//            launchedBird.activatePower();
////            check = true;
//        }
//        else if (bird!=null){
//            Vector2 launchDirection = new Vector2(x, y);
//            float pullDistance = launchDirection.len();
//
//            // Calculate the launch force based on pullback distance and scale
//            Vector2 launchForce = launchDirection.nor().scl(pullDistance * forceScale);
//
//            // Call the launch method with the calculated force
//            launch(bird, launchForce);
//            bird.setLaunched(true);
//
//            Sound birdShotSound = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
//            birdShotSound.play();
//
//            // Reset the bird in the slingshot after release
//            launchedBird=bird;
//            bird = null;
//        }
////        boolean check = false;
////        if(launchedBird!=null){
////            launchedBird.activatePower();
////            check = true;
////        }
////        if (launchedBird == null || bird != null && launchedBird.hasUsedPower()) {
////            Vector2 launchDirection = new Vector2(x, y);
////            float pullDistance = launchDirection.len();
////
////            // Calculate the launch force based on pullback distance and scale
////            Vector2 launchForce = launchDirection.nor().scl(pullDistance * forceScale);
////
////            // Call the launch method with the calculated force
////            launch(bird, launchForce);
////            bird.setLaunched(true);
////
////            Sound birdShotSound = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
////            birdShotSound.play();
////
////            // Reset the bird in the slingshot after release
////            launchedBird=bird;
////            bird = null;
////        }
//    }
//
//    private void launch(Bird bird, Vector2 force) {
//        // Activate bird's physics body and apply the force for launching
//        if(bird!=null) {
//            bird.getBody().setActive(true);
//            bird.getBody().applyLinearImpulse(force, bird.getBody().getWorldCenter(), true);
//        }
//    }
//
//    public boolean isInSlingshot(Bird bird) {
//        return this.bird.equals(bird);
//    }
//}
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import io.github.some_example_name.actors.birds.Bird;

import static io.github.some_example_name.actors.birds.Bird.PPM;
import static io.github.some_example_name.screens.GameScreen.*;

public class Slingshot {
    private Sprite sprite;
    private float x, y; // Position of the slingshot anchor
    private Bird bird;
    private Body body;
    private MouseJoint mouseJoint;
    private World world;
    private Vector2 pullStartPosition;
    private float maxPullDistance = 100f; // Maximum pull distance
    private float forceScale = 0.35f; // Scale for launch force
    private ShapeRenderer shapeRenderer;
    public Slingshot(float x, float y, World world) {
        Texture texture = new Texture(Gdx.files.internal("abs/Slingshot.png"));
        this.sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        this.world = world;
        this.bird = null;
        this.shapeRenderer=new ShapeRenderer();
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
        shape.set(vertices);

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;
        fixtureDef.filter.categoryBits = CATEGORY_SLINGSHOT; // Slingshot's category
        fixtureDef.filter.maskBits = CATEGORY_PIG | CATEGORY_BLOCK;// Only collides with pigs
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void draw(Batch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
        if (bird != null && pullStartPosition != null && mouseJoint!=null) {

            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1); // Set the color of the line (red)

            // Draw the line from the slingshot to the bird's current position
//            Vector2 birdPosition = bird.getPosition();

//            shapeRenderer.line(mouseJoint.getTarget().x, mouseJoint.getTarget().y, (this.bird.getX()+((this.bird.sprite.getWidth())/2)), (this.bird.getY())+((this.bird.sprite.getHeight())/2));  // Convert to world coordinates
            shapeRenderer.line(mouseJoint.getTarget().x, mouseJoint.getTarget().y, this.pullStartPosition.x,this.pullStartPosition.y);
            shapeRenderer.end();
        }
    }

    public boolean isEmpty() {
        return bird == null;
    }

    public void loadBird(Bird bird) {
        if (this.bird == null) {
            this.bird = bird;
        }
    }

    // Handles touch or mouse input for pulling and releasing the bird
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            Vector2 touchPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            touchPosition.y = Gdx.graphics.getHeight() - touchPosition.y; // Convert to screen coordinates


            if (bird != null && mouseJoint == null) {
                // Start the pull
                if(!this.bird.sprite.getBoundingRectangle().contains(touchPosition)){
                    return;
                }
                pullStartPosition = new Vector2(this.bird.getX()+((this.bird.sprite.getWidth())/2), this.bird.getY()+((this.bird.sprite.getHeight())/2));  // Convert to world coordinates

                createMouseJoint(pullStartPosition);
            } else if (bird != null && mouseJoint != null) {
                // While dragging, update bird's position based on touch
//                pull(touchPosition);
                System.out.println("Pulling");
                System.out.println(touchPosition.x+" "+touchPosition.y);
                mouseJoint.setTarget(touchPosition);
            }
        } else {
            if (bird != null && mouseJoint != null) {
                // Release the bird when the touch is released
                releaseBird(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            }
        }
    }

    public void startPull(Vector2 touchStart) {
        if (bird != null) {

        }
    }

    public void pull(Vector2 touchCurrent) {
        if (bird != null && mouseJoint != null) {
            // Limit the pull distance
//            Vector2 pullVector = touchCurrent.cpy().sub(pullStartPosition);
//            float pullDistance = pullVector.len();
//
//            if (pullDistance > maxPullDistance) {
//                pullVector.nor().scl(maxPullDistance);
//            }
//
//            // Move the bird with the mouse joint
////            mouseJoint.setTarget(new Vector2(x + pullVector.x, y + pullVector.y));
//            mouseJoint.setTarget(touchCurrent);
//            System.out.println(pullStartPosition.x+" "+pullStartPosition.y);
//            System.out.println(pullVector.x+" "+pullVector.y);
            System.out.println("Pulling");
            System.out.println(touchCurrent.x+" "+touchCurrent.y);
            mouseJoint.setTarget(touchCurrent);
        }
    }

    public void releaseBird(Vector2 touchRelease) {
        if (bird != null && mouseJoint != null) {
            // Remove the mouse joint and apply force to launch the bird
            world.destroyJoint(mouseJoint);
            Vector2 launchDirection = pullStartPosition.cpy().sub(mouseJoint.getTarget());
//            Vector2 launchDirection = mouseJoint.getTarget().cpy().sub(pullStartPosition);
            mouseJoint=null;
            float pullDistance = launchDirection.len();

            // Calculate the launch force
            Vector2 launchForce = launchDirection.nor().scl(pullDistance * forceScale);

            // Launch the bird
            launch(bird, launchForce);

            Sound birdShotSound = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
            birdShotSound.play();
            this.bird.setInSlingshot(false);
            this.bird.setLaunched(true);
            bird = null; // Reset the bird after release
        }
    }

    private void createMouseJoint(Vector2 touchPosition) {
        if (bird != null) {
            MouseJointDef mjDef = new MouseJointDef();
            mjDef.bodyA = body;  // Static body (slingshot)
            mjDef.bodyB = bird.getBody();  // Bird's body
            mjDef.target.set(touchPosition);  // Initial target position is where the touch started
            mjDef.maxForce = 1000.0f * bird.getBody().getMass(); // Set max force for dragging
            mouseJoint = (MouseJoint) world.createJoint(mjDef);
            System.out.println("Mouse joint created");
        }
    }

    private void launch(Bird bird, Vector2 force) {
        if (bird != null) {
            bird.getBody().setActive(true);
            bird.getBody().applyLinearImpulse(force, bird.getBody().getWorldCenter(), true);
        }
    }

    public boolean isInSlingshot(Bird bird) {
        return this.bird != null && this.bird.equals(bird);
    }
}
