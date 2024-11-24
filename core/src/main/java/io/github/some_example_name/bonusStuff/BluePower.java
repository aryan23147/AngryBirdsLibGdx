package io.github.some_example_name.bonusStuff;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.BlueBird;

public class BluePower implements Power {
    private boolean used;

    public BluePower() {
        this.used = false;
    }

    @Override
    public void activate(Bird bird) {
        if (!used) {
            World world = bird.getBody().getWorld();
            Vector2 position = bird.getBody().getPosition();

            // Create 3 smaller birds
            for (int i = -1; i <= 1; i++) {
                Bird smallBird = new BlueBird(world, position.x, position.y, new DefaultPower());
                Vector2 offset = new Vector2(i * 0.5f, -0.5f); // Adjust offset for spread
                smallBird.getBody().setLinearVelocity(bird.getBody().getLinearVelocity().add(offset));
            }
            used = true;
        }
    }

//    @Override
//    public boolean hasUsedPower() {
//        return used;
//    }
}
