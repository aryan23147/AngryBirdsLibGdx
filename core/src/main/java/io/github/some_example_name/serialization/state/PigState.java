package io.github.some_example_name.serialization.state;

import io.github.some_example_name.actors.pigs.KidPig;
import io.github.some_example_name.actors.pigs.KingPig;
import io.github.some_example_name.actors.pigs.MediumPig;
import io.github.some_example_name.actors.pigs.Pig;

public class PigState {
    public String type; // "kid", "medium", "king"
    public float x, y;
    public float vx, vy;
    public float hp;

    // Default constructor for JSON serialization/deserialization
    public PigState() {}

    // Constructor to create a PigState from a Pig instance
    public PigState(Pig pig) {
        if (pig instanceof KidPig) {
            this.type = "kid";
        } else if (pig instanceof MediumPig) {
            this.type = "medium";
        } else if (pig instanceof KingPig) {
            this.type = "king";
        }
        this.x = pig.getX();
        this.y = pig.getY();
        this.vx = pig.getVelocity().x;
        this.vy = pig.getVelocity().y;
        this.hp = pig.getHp();
    }

    // Getters and setters (optional, if needed for JSON library)

}
