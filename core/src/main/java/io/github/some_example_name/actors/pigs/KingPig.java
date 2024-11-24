package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {

    public KingPig(float X, float Y, World world){
        super("abs/KingPig.png",world,X,Y,1.3f,50,70);
    }

}
