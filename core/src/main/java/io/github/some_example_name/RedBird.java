package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {

    public RedBird(World world,float X,float Y) {
        super("splash.png",world,X,Y);
    }
    public void setDensity(){
        super.setDensity(10f);
    }

}