package io.github.some_example_name;

import com.badlogic.gdx.utils.Queue;
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
