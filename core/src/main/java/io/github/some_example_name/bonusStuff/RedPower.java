package io.github.some_example_name.bonusStuff;

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
        }
    }

//    @Override
//    public boolean hasUsedPower() {
//        return used;
//    }
}
