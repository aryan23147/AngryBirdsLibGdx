package io.github.some_example_name.actors;

import com.badlogic.gdx.physics.box2d.World;

public class StoneBlock extends Block {

    public StoneBlock(float x, float y, World world, float width, float height, boolean isDynamic){
        super(x,y,world,width, height, isDynamic, "abs/StoneBlock.jpg", 50);
    }

}
