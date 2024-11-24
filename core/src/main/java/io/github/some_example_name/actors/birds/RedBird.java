package io.github.some_example_name.actors.birds;

import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.bonusStuff.RedPower;

public class RedBird extends Bird {

    public RedBird(World world, float X, float Y, RedPower redPower) {
        super("abs/Red.png", world, X, Y, 1.0f, 12, redPower);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
