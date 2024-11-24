package io.github.some_example_name.bonusStuff;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.actors.birds.Bird;

import java.util.ArrayList;
import java.util.List;

public class BlackPower implements Power {
    private boolean used;

    public BlackPower() {
        this.used = false;
    }

    @Override
    public void activate(Bird bird) {
        if (!used) {
            World world = bird.getBody().getWorld();
            Vector2 position = bird.getBody().getPosition();

            // Create radial blast logic (e.g., apply forces to nearby objects)
            float blastRadius = 3f; // Adjust radius as needed
            for (Body body : getNearbyBodies(world, position, blastRadius)) {
                Vector2 force = body.getPosition().sub(position).nor().scl(50); // Adjust force multiplier
                body.applyLinearImpulse(force, body.getWorldCenter(), true);
            }
            used = true;
        }
    }

//    @Override
//    public boolean hasUsedPower() {
//        return used;
//    }

    private List<Body> getNearbyBodies(World world, Vector2 position, float radius) {
        List<Body> bodies = new ArrayList<>();
        world.QueryAABB((fixture) -> {
            bodies.add(fixture.getBody());
            return true;
        }, position.x - radius, position.y - radius, position.x + radius, position.y + radius);
        return bodies;
    }
}
