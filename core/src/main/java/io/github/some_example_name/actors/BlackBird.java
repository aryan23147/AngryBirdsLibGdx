package io.github.some_example_name.actors;

import com.badlogic.gdx.physics.box2d.World;

public class BlackBird extends Bird {

    public BlackBird(World world, float X, float Y) {
        super("abs/Blackbird.png", world, X, Y, 1.0f, 20);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
