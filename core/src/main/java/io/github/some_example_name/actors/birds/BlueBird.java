package io.github.some_example_name.actors.birds;

import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.bonusStuff.BluePower;
import io.github.some_example_name.bonusStuff.Power;

public class BlueBird extends Bird {

    public BlueBird(World world, float X, float Y, Power power, float radius, float mass) {
        super("abs/BlueBird.png", world, X, Y, radius, mass, power);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
