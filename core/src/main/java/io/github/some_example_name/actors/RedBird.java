package io.github.some_example_name.actors;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {

    public RedBird(World world, float X, float Y) {
        super("abs/Red.png", world, X, Y, 1.0f, 12);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
