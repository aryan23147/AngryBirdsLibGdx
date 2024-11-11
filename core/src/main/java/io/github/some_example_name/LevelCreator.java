package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class LevelCreator {
    public LevelCreator(){}
    static Queue<Bird> birdQueue;
    static List<Bird> allBirds;
    static Ground ground;
    static List<Pig> allPigs;
    static List<Block> allBlocks;

    public static ReturnStruct setupWorldObjectsLevel1(World world) {
        // Initialize game objects
        Bird redBird = new RedBird(world, 125, 150);
        Bird blackBird = new BlackBird(world, 80, 150);
        Bird blueBird = new BlueBird(world, 20, 150);
        birdQueue = new Queue<>();
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);

        allBirds = new ArrayList<>();
        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);

        ground = new Ground(world);

        allPigs = new ArrayList<>();
        Pig pig1 = new MediumPig(1050, 200, world);
        allPigs.add(pig1);
        allBlocks = new ArrayList<>();
        Block block1 = new Block(960, 200, world, 64, 64);
        Block block2 = new Block(960, 100, world, 64, 64);
        Block block3 = new Block(890, 100, world, 64, 64);
        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);

        return new ReturnStruct(birdQueue,allBirds, allPigs, allBlocks, ground);
    }
    public static ReturnStruct setupWorldObjectsLevel2(World world) {
        // Initialize game objects
        Bird redBird = new RedBird(world, 125, 150);
        Bird blackBird = new BlackBird(world, 80, 150);
        Bird blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);

        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);

        ground = new Ground(world);

        Pig pig1 = new MediumPig(970, 180, world);
        Pig pig2 = new KidPig(1040, 180, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        Block block1 = new Block(1000, 200, world, 250, 50);
        Block block2 = new Block(1100, 100, world, 50, 80);
        Block block3 = new Block(900, 100, world, 50, 80);
        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);

        return new ReturnStruct(birdQueue,allBirds, allPigs, allBlocks, ground);
    }
    public static ReturnStruct setupWorldObjectsLevel3(World world) {
        // Initialize game objects
        Bird redBird = new RedBird(world, 125, 150);
        Bird blackBird = new BlackBird(world, 80, 150);
        Bird blueBird = new BlueBird(world, 20, 150);
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blackBird);
        birdQueue.addLast(blueBird);

        allBirds.add(redBird);
        allBirds.add(blackBird);
        allBirds.add(blueBird);

        ground = new Ground(world);

        Pig pig1 = new KingPig(935, 200, world);
        Pig pig2 = new MediumPig(935, 600, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        Block block1 = new Block(1010, 150, world, 50, 50);
        Block block2 = new Block(1010, 70, world, 50, 50);
        Block block3 = new Block(930, 70, world, 50, 50);
        Block block4 = new Block(875, 75, world, 50, 150);
        Block block5 = new Block(870, 260, world, 50, 50);
        Block block6 = new Block(990, 230, world, 50, 100);
        Block block7 = new Block(920, 320, world, 175, 50);
        Block block8 = new Block(860, 400, world, 50, 50);

        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);
        allBlocks.add(block4);
        allBlocks.add(block5);
        allBlocks.add(block6);
        allBlocks.add(block7);
        allBlocks.add(block8);

        return new ReturnStruct(birdQueue,allBirds, allPigs, allBlocks, ground);
    }
}
