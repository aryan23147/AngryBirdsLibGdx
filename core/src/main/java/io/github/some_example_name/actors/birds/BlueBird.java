package io.github.some_example_name.actors.birds;

import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.bonusStuff.BluePower;
import io.github.some_example_name.bonusStuff.Power;

public class BlueBird extends Bird {

    public BlueBird(World world, float X, float Y, Power power) {
        super("abs/BlueBird.png", world, X, Y, 1.0f, 10f, power);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
