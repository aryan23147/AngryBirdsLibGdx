package io.github.some_example_name.bonusStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import io.github.some_example_name.actors.birds.Bird;

public class RedPower implements Power {
    private boolean used;

    public RedPower() {
        this.used = false;
    }

    @Override
    public void activate(Bird bird) {
        if (!used) {
            bird.getBody().setLinearVelocity(bird.getBody().getLinearVelocity().scl(2)); // Double the velocity
            used = true;
            Sound powerSound = Gdx.audio.newSound(Gdx.files.internal("RedPower.mp3"));
            powerSound.play();
        }
    }
}
