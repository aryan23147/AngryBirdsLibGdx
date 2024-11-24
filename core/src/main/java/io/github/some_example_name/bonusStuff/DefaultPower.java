package io.github.some_example_name.bonusStuff;

import io.github.some_example_name.actors.birds.Bird;

public class DefaultPower implements Power {
    @Override
    public void activate(Bird bird) {
        // No functionality for the default power
    }

//    @Override
//    public boolean hasUsedPower() {
//        return true; // Always treated as used since it has no effect
//    }
}
