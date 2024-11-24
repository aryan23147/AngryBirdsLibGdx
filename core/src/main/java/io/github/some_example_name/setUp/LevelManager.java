package io.github.some_example_name.setUp;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import io.github.some_example_name.actors.*;
import io.github.some_example_name.returnStructs.ReturnStruct;

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
        Pig pig1 = new MediumPig(1070, 200, world);
        allPigs.add(pig1);
        allBlocks = new ArrayList<>();
        Block block1 = new GlassBlock(960, 200, world, 64, 64, true);
        Block block2 = new WoodBlock(960, 100, world, 64, 64, true);
        Block block3 = new WoodBlock(890, 100, world, 64, 64, true);
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
        Pig pig1 = new MediumPig(1000, 100, world);
        Pig pig2 = new KidPig(1060, 100, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        allBlocks = new ArrayList<>();
        Block block1 = new StoneBlock(900, 200, world, 250, 50, true);
        Block block2 = new GlassBlock(1111, 100, world, 50, 100, true);
        Block block3 = new WoodBlock(900, 100, world, 50, 100, true);
        allBlocks.add(block1);
        allBlocks.add(block2);
        allBlocks.add(block3);

        return new ReturnStruct(birdQueue, allBirds, allPigs, allBlocks, ground);
    }

    public ReturnStruct setupWorldObjectsLevel3(World world) {
        // Initialize game objects
        Bird redBird = new RedBird(world, 125, 150);
        Bird blueBird = new BlueBird(world, 80, 150);
        Bird blackBird = new BlackBird(world, 20, 150);
        birdQueue = new Queue<>();
        birdQueue.addFirst(redBird);
        birdQueue.addLast(blueBird);
        birdQueue.addLast(blackBird);

        allBirds = new ArrayList<>();
        allBirds.add(redBird);
        allBirds.add(blueBird);
        allBirds.add(blackBird);

        ground = new Ground(world);

        allPigs = new ArrayList<>();
        Pig pig1 = new KingPig(885, 200, world);
        Pig pig2 = new MediumPig(911 , 450, world);
        allPigs.add(pig1);
        allPigs.add(pig2);

        allBlocks = new ArrayList<>();
        Block block1 = new WoodBlock(960, 150, world, 50, 50, true);
        allBlocks.add(block1);
        Block block2 = new StoneBlock(960, 70, world, 50, 50, true);
        allBlocks.add(block2);
        Block block3 = new WoodBlock(890, 100, world, 50, 50, true);
        allBlocks.add(block3);
        Block block4 = new GlassBlock(810, 75, world, 50, 150, true);
        allBlocks.add(block4);
        Block block5 = new WoodBlock(810, 275, world, 50, 50, true);
        allBlocks.add(block5);
        Block block6 = new StoneBlock(950, 230, world, 50, 100, true);
        allBlocks.add(block6);
        Block block7 = new StoneBlock(810, 320, world, 175, 50, true);
        allBlocks.add(block7);
        Block block8 = new GlassBlock(810, 500, world, 50, 50, true);
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
