package io.github.some_example_name.actors.blocks;

import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {

    public GlassBlock(float x, float y, World world, float width, float height, boolean isDynamic){
        super(x,y,world,width, height, isDynamic, "abs/GlassBlock.png", 15);
    }
}
