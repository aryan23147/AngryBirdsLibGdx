package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class KidPig extends Pig {

    public KidPig(float X, float Y, World world){
        super("abs/pigalt.png",world,X,Y,0.7f,5, 20);
    }

}
