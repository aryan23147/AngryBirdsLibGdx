package io.github.some_example_name.bonusStuff;

import io.github.some_example_name.actors.birds.Bird;

public interface Power {
//    boolean hasUsedPower = false;  // Default value

    void activate(Bird bird);      // Abstract method to activate the power

    // Default method to check if the power has already been used
//    default boolean hasUsedPower() {
//        return hasUsedPower;
//    }
}
