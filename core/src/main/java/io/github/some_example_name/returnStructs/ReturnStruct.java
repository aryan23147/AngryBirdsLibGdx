package io.github.some_example_name.returnStructs;

import com.badlogic.gdx.utils.Queue;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.extras.Ground;
import io.github.some_example_name.actors.pigs.Pig;

import java.util.List;

public class ReturnStruct {
    public Queue<Bird> birdQueue;
    public List<Bird> birds;
    public List<Pig> pigs;
    public List<Block> blocks;
    public Ground ground;
    public ReturnStruct(Queue<Bird> birdQueue, List<Bird> birds, List<Pig> pigs, List<Block> blocks, Ground ground){
        this.birdQueue = birdQueue;
        this.birds=birds;
        this.pigs=pigs;
        this.blocks=blocks;
        this.ground = ground;
    }
}
