package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public LevelManager() {
    }

    private static Queue<Bird> birdQueue;
    private static List<Bird> allBirds;
    private static Ground ground;
    private static List<Pig> allPigs;
    private static List<Block> allBlocks;
    private static boolean isL1Saved = false;
    private static boolean isL2Saved = false;
    private static boolean isL3Saved = false;

    public ReturnStruct setupWorldObjectsLevel1(World world) {
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

        return new ReturnStruct(birdQueue, allBirds, allPigs, allBlocks, ground);
    }

    public ReturnStruct setupWorldObjectsLevel2(World world) {
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
        Pig pig1 = new MediumPig(970, 180, world);
        Pig pig2 = new KidPig(1040, 180, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        allBlocks = new ArrayList<>();
        Block block1 = new Block(1000, 200, world, 250, 50);
        Block block2 = new Block(1100, 100, world, 50, 80);
        Block block3 = new Block(900, 100, world, 50, 80);
        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);

        return new ReturnStruct(birdQueue, allBirds, allPigs, allBlocks, ground);
    }

    public ReturnStruct setupWorldObjectsLevel3(World world) {
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
        Pig pig1 = new KingPig(885, 200, world);
        Pig pig2 = new MediumPig(895, 600, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        allBlocks = new ArrayList<>();
        Block block1 = new Block(960, 150, world, 50, 50);
        Block block2 = new Block(960, 70, world, 50, 50);
        Block block3 = new Block(890, 100, world, 50, 50);
        Block block4 = new Block(825, 75, world, 50, 150);
        Block block5 = new Block(820, 260, world, 50, 50);
        Block block6 = new Block(940, 230, world, 50, 100);
        Block block7 = new Block(880, 320, world, 175, 50);
        Block block8 = new Block(840, 500, world, 50, 50);

        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);
        allBlocks.add(block4);
        allBlocks.add(block5);
        allBlocks.add(block6);
        allBlocks.add(block7);
        allBlocks.add(block8);

        return new ReturnStruct(birdQueue, allBirds, allPigs, allBlocks, ground);
    }

    public static boolean isLevelSaved(int level) {
        switch (level) {
            case 1:
                return isL1Saved;
            case 2:
                return isL2Saved;
            case 3:
                return isL3Saved;
            default:
                return false;
        }
    }

    public static void saveLevel(int level, boolean bool) {
        switch (level) {
            case 1:
                isL1Saved = bool;
                break;
            case 2:
                isL2Saved = bool;
                break;
            case 3:
                isL3Saved = bool;
                break;
            default:
                break;
        }
    }
}
