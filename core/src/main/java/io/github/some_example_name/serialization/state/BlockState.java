package io.github.some_example_name.serialization.state;

import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.blocks.GlassBlock;
import io.github.some_example_name.actors.blocks.StoneBlock;
import io.github.some_example_name.actors.blocks.WoodBlock;

public class BlockState {
    public String type; // "wood", "glass", "stone"
    public float x, y;
    public float vx, vy;
    public float width, height;
    public float hp;

    // Default constructor for JSON serialization
    public BlockState() {}

    public BlockState(Block block) {
        if (block instanceof WoodBlock) {
            this.type = "wood";
        } else if (block instanceof GlassBlock) {
            this.type = "glass";
        } else if (block instanceof StoneBlock) {
            this.type = "stone";
        }
        this.x = block.getX();
        this.y = block.getY();
        this.vx = block.getVelocity().x;
        this.vy = block.getVelocity().y;
        this.width = block.getWidth();
        this.height = block.getHeight();
        this.hp = block.getHp();
    }
}
