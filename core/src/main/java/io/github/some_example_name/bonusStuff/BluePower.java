package io.github.some_example_name.bonusStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.BlueBird;
import io.github.some_example_name.screens.GameScreen;

public class BluePower implements Power {
    private boolean used;

    public BluePower() {
        this.used = false;
    }

    @Override
    public void activate(Bird bird) {
        if (!used) {
            // Get the current physics world
            World world = bird.getBody().getWorld();

            // Retrieve the bird's current position in Box2D world coordinates
            Vector2 currentPosition = bird.getBody().getPosition();

            // Create 3 smaller birds
            for (int i = -1; i <= 1; i++) {
                // Spawn the new bird exactly at the current bird's position
                Bird smallBird = new BlueBird(world, currentPosition.x * Bird.PPM, currentPosition.y * Bird.PPM, new DefaultPower(), 0.7f, 10f);
                GameScreen.allBirds.add(smallBird);
                // Adjust velocity with horizontal spread
                Vector2 offset = new Vector2(0, i * 5f); // Spread horizontally
                Vector2 velocityWithOffset = bird.getBody().getLinearVelocity().cpy().add(offset);
                smallBird.getBody().setLinearVelocity(velocityWithOffset);
            }
            bird.disappear();
            GameScreen.allBirds.remove(bird);
            used = true;
        }
    }
}
