package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

public class KidPig extends Pig {

    public KidPig(float X, float Y, World world){
        super("abs/pigalt.png",world,X,Y,0.7f,5, 20);
    }
    public KidPig(float X,float Y,World world,float hp){


        super("abs/pigalt.png",world,X,Y,0.7f,5, hp);
        System.out.println("Kid pig with hp "+hp+" is created");
    }
    public void draw(Batch batch){
        super.damageAppearance("abs/DamagedPig.png", 15f);
        super.draw(batch);
    }
}
