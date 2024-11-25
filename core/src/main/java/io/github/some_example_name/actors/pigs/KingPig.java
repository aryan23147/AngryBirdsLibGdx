package io.github.some_example_name.actors.pigs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {

    public KingPig(float X, float Y, World world){
        super("abs/KingPig.png",world,X,Y,1.3f,50,100);
    }
    public KingPig(float X, float Y, World world,float hp){

        super("abs/KingPig.png",world,X,Y,1.3f,50,hp);
        System.out.println("King pig with hp "+hp+" is created");
    }
    public void draw(Batch batch){
        super.damageAppearance("abs/DamagedKingPig.png", 100f);
        super.draw(batch);
    }
}
