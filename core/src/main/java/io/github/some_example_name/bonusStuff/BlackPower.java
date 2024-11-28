//package io.github.some_example_name.bonusStuff;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.World;
//import io.github.some_example_name.actors.birds.Bird;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BlackPower implements Power {
//    private boolean used;
//
//    public BlackPower() {
//        this.used = false;
//    }
//
//    @Override
//    public void activate(Bird bird) {
//        if (!used) {
//            World world = bird.getBody().getWorld();
//            Vector2 position = bird.getBody().getPosition();
//
//            // Create radial blast logic (e.g., apply forces to nearby objects)
//            float blastRadius = 3f; // Adjust radius as needed
//            for (Body body : getNearbyBodies(world, position, blastRadius)) {
//                Vector2 force = body.getPosition().sub(position).nor().scl(50); // Adjust force multiplier
//                body.applyLinearImpulse(force, body.getWorldCenter(), true);
//            }
//            used = true;
//        }
//    }
//
//    private List<Body> getNearbyBodies(World world, Vector2 position, float radius) {
//        List<Body> bodies = new ArrayList<>();
//        world.QueryAABB((fixture) -> {
//            bodies.add(fixture.getBody());
//            return true;
//        }, position.x - radius, position.y - radius, position.x + radius, position.y + radius);
//        return bodies;
//    }
//}

package io.github.some_example_name.bonusStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.actors.birds.Bird;

import java.util.ArrayList;
import java.util.List;

public class BlackPower implements Power {
    private static boolean used;
    private  transient Texture ringTexture;
    private static Sprite ringSprite;
    private static float scale;
    private float maxScale;
    private static float alpha;

    public BlackPower() {
        used = false;
        ringTexture = new Texture(Gdx.files.internal("abs/BlackRing.png")); // Replace with your file path
        ringSprite = new Sprite(ringTexture);
        scale = 0.1f;  // Starting scale
        maxScale = 3f; // Maximum scale
        alpha = 1.0f;  // Full opacity
    }

    @Override
    public void activate(Bird bird) {
        if (!used) {
            World world = bird.getBody().getWorld();
            Vector2 position = bird.getBody().getPosition();

            // Set initial position for the ring
            ringSprite.setPosition(position.x * Bird.PPM - ringSprite.getWidth() / 2,
                position.y * Bird.PPM - ringSprite.getHeight() / 2);

            // Apply radial blast logic
            float blastRadius = 3f;
            Vector2 force;
            for (Body body : getNearbyBodies(world, position, blastRadius)) {
                if(position.x>body.getPosition().x){
                    force = position.sub(body.getPosition()).nor().scl(50); // Adjust force multiplier
                }
                else {
                    force = body.getPosition().sub(position).nor().scl(50); // Adjust force multiplier
                }
                body.applyLinearImpulse(force, body.getWorldCenter(), true);
            }

            used = true;
            Sound powerSound = Gdx.audio.newSound(Gdx.files.internal("BlackPower.mp3"));
            powerSound.play();
        }
    }

    private List<Body> getNearbyBodies(World world, Vector2 position, float radius) {
        List<Body> bodies = new ArrayList<>();
        world.QueryAABB((fixture) -> {
            bodies.add(fixture.getBody());
            return true;
        }, position.x - radius, position.y - radius, position.x + radius, position.y + radius);
        return bodies;
    }

    public static void render(SpriteBatch batch, float deltaTime) {
        if (used && alpha > 0) {
            // Update scale and alpha
            scale += deltaTime * 4;   // Adjust growth speed
            alpha -= deltaTime * 3;   // Adjust fade speed

            // Ensure the ring stays centered while scaling
            ringSprite.setScale(scale);
            ringSprite.setAlpha(alpha);
            ringSprite.draw(batch);
        }
    }

    public boolean isFinished() {
        return alpha <= 0;
    }
}
