package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class MediumPig extends Pig {

    public MediumPig(float X, float Y, World world){
        super("abs/pigalt.png",world,X,Y,1.0f,10, 30);
    }
    public MediumPig(float X, float Y, World world,float hp){
        super("abs/pigalt.png",world,X,Y,1.0f,10, hp);
    }

}
