package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird {

    public BlueBird(World world, float X, float Y) {
        super("abs/BlueBird.png", world, X, Y, 1.0f, 10f);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
