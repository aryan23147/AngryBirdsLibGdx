package io.github.some_example_name.actors.birds;

import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.bonusStuff.BlackPower;

public class BlackBird extends Bird {

    public BlackBird(World world, float X, float Y, BlackPower blackPower) {
        super("abs/Blackbird.png", world, X, Y, 1.0f, 20, blackPower);
    }


    public void setDensity() {
        super.setDensity(10f);
    }
}
