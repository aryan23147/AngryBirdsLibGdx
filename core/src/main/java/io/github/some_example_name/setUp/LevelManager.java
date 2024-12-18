package io.github.some_example_name.setUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.BlackBird;
import io.github.some_example_name.actors.birds.BlueBird;
import io.github.some_example_name.actors.birds.RedBird;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.blocks.GlassBlock;
import io.github.some_example_name.actors.blocks.StoneBlock;
import io.github.some_example_name.actors.blocks.WoodBlock;
import io.github.some_example_name.actors.extras.Ground;
import io.github.some_example_name.actors.pigs.KidPig;
import io.github.some_example_name.actors.pigs.KingPig;
import io.github.some_example_name.actors.pigs.MediumPig;
import io.github.some_example_name.actors.pigs.Pig;
import io.github.some_example_name.bonusStuff.BlackPower;
import io.github.some_example_name.bonusStuff.BluePower;
import io.github.some_example_name.bonusStuff.RedPower;
import io.github.some_example_name.returnStructs.ReturnStruct;
import io.github.some_example_name.serialization.createFromState.createFromState;
import io.github.some_example_name.serialization.state.BirdState;
import io.github.some_example_name.serialization.state.BlockState;
import io.github.some_example_name.serialization.state.GameState;
import io.github.some_example_name.serialization.state.PigState;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public LevelManager() {
    }

    private static Queue<Bird> birdQueue;
    private static List<Bird> allBirds;
    private static Ground ground;
    private static List<Pig> allPigs;
    private static final FileHandle file = Gdx.files.local("levelStatus.txt");
    private static List<Block> allBlocks;
    private static boolean isL1Saved = false;
    private static boolean isL2Saved = false;
    private static boolean isL3Saved = false;


    public ReturnStruct loadObjects(World world,int level){
        Json json=new Json();
        String jsonString;
        if(level==1){
            jsonString= Gdx.files.local("saved_games/game_state1.json").readString();
        }
        else if(level==2){
            jsonString= Gdx.files.local("saved_games/game_state2.json").readString();
        }
        else{
            jsonString= Gdx.files.local("saved_games/game_state3.json").readString();
        }

        GameState gameState=json.fromJson(GameState.class,jsonString);
        allBirds = new ArrayList<>();
        birdQueue = new Queue<>();
        for(BirdState birdState: gameState.birds){
            Bird bird= createFromState.createBirdFromState(world,birdState);
            if(!birdState.isLaunched){
                birdQueue.addLast(bird);
            }
            allBirds.add(bird);

        }
        ground = new Ground(world);
        allPigs=new ArrayList<>();
        for(PigState pigState: gameState.pigs){
            Pig pig=createFromState.createPigFromState(world,pigState);
            allPigs.add(pig);
        }
        allBlocks=new ArrayList<>();
        for(BlockState blockState: gameState.blocks){
            Block block=createFromState.createBlockFromState(world,blockState);
            allBlocks.add(block);
        }
        return new ReturnStruct(birdQueue, allBirds, allPigs, allBlocks, ground);
    }
    public ReturnStruct setupWorldObjectsLevel1(World world) {
        // Initialize game objects
        Bird redBird = new RedBird(world, 125, 150, new RedPower());
        Bird blackBird = new BlackBird(world, 80, 150, new BlackPower());
        Bird blueBird = new BlueBird(world, 20, 150, new BluePower(), 1.0f, 20f);
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
        Bird redBird = new RedBird(world, 125, 150, new RedPower());
        Bird blackBird = new BlackBird(world, 80, 150, new BlackPower());
        Bird blueBird = new BlueBird(world, 20, 150, new BluePower(), 1.0f, 20f);
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
        Bird redBird = new RedBird(world, 125, 150, new RedPower());
        Bird blueBird = new BlueBird(world, 80, 150, new BluePower(), 1.0f, 20f);
        Bird blackBird = new BlackBird(world, 20, 150, new BlackPower());
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

    static {
        loadLevelStatus();
    }

    // Method to load level statuses from the file
    private static void loadLevelStatus() {
        if (file.exists()) {
            String status = file.readString().trim();  // Read the file and remove any leading/trailing whitespace
            if (status.length() >= 3) {
                isL1Saved = status.charAt(0) == 'T';
                isL2Saved = status.charAt(1) == 'T';
                isL3Saved = status.charAt(2) == 'T';
            }
        } else {
            // If the file doesn't exist, default to all levels not saved
            isL1Saved = false;
            isL2Saved = false;
            isL3Saved = false;
        }
    }

    // Method to check if a level is saved
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

    // Method to save a level's status
    public static void saveLevel(int level, boolean isSaved) {
        switch (level) {
            case 1:
                isL1Saved = isSaved;
                break;
            case 2:
                isL2Saved = isSaved;
                break;
            case 3:
                isL3Saved = isSaved;
                break;
            default:
                return;
        }
        // After updating, save the status to the file
        saveLevelStatus();
    }

    // Method to save the current level statuses to the file
    private static void saveLevelStatus() {
        String status = (isL1Saved ? "T" : "F") +
            (isL2Saved ? "T" : "F") +
            (isL3Saved ? "T" : "F");
        file.writeString(status, false);  // Overwrite the file with the new status
    }
}
