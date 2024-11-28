package io.github.some_example_name.serialization.createFromState;

import com.badlogic.gdx.physics.box2d.World;

import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.BlackBird;
import io.github.some_example_name.actors.birds.BlueBird;
import io.github.some_example_name.actors.birds.RedBird;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.blocks.GlassBlock;
import io.github.some_example_name.actors.blocks.StoneBlock;
import io.github.some_example_name.actors.blocks.WoodBlock;
import io.github.some_example_name.actors.pigs.KidPig;
import io.github.some_example_name.actors.pigs.KingPig;
import io.github.some_example_name.actors.pigs.MediumPig;
import io.github.some_example_name.actors.pigs.Pig;
import io.github.some_example_name.bonusStuff.BlackPower;
import io.github.some_example_name.bonusStuff.BluePower;
import io.github.some_example_name.bonusStuff.Power;
import io.github.some_example_name.bonusStuff.RedPower;
import io.github.some_example_name.serialization.state.BirdState;
import io.github.some_example_name.serialization.state.BlockState;
import io.github.some_example_name.serialization.state.PigState;

public class createFromState {
    public  static Bird createBirdFromState(World world, BirdState birdState) {
        Bird bird;
        switch (birdState.type) {
            case "red":
                bird = new RedBird(world, birdState.x, birdState.y,(RedPower) birdState.power);
                break;
            case "black":
                bird = new BlackBird(world, birdState.x, birdState.y, (BlackPower) birdState.power);
                break;
            case "blue":
                bird = new BlueBird(world, birdState.x, birdState.y,birdState.power,birdState.radius,birdState.mass);
                break;
            default:
                throw new IllegalArgumentException("Unknown bird type: " + birdState.type);
        }

        bird.getBody().setLinearVelocity(birdState.vx, birdState.vy);
        bird.setLaunched(birdState.isLaunched);
        return bird;
    }
    public static Block createBlockFromState(World world, BlockState blockState) {
        Block block;
        switch (blockState.type) {
            case "wood":
                block = new WoodBlock(blockState.x, blockState.y, world, blockState.width, blockState.height, false);
                break;
            case "glass":
                block = new GlassBlock(blockState.x, blockState.y, world, blockState.width, blockState.height, false);
                break;
            case "stone":
                block = new StoneBlock(blockState.x, blockState.y, world, blockState.width, blockState.height, false);
                break;
            default:
                throw new IllegalArgumentException("Unknown block type: " + blockState.type);
        }

        block.getBody().setLinearVelocity(blockState.vx, blockState.vy);
        block.setHp(blockState.hp);
        return block;
    }
    public  static Pig createPigFromState(World world, PigState pigState) {
        Pig pig;
        switch (pigState.type){
            case "kid":
                pig = new KidPig(pigState.x, pigState.y, world,pigState.hp);
                break;
            case "medium":
                pig = new MediumPig(pigState.x, pigState.y, world,pigState.hp);
                break;
            case "king":
                pig = new KingPig(pigState.x, pigState.y, world,pigState.hp);
                break;
            default:
                throw new IllegalArgumentException("Unknown pig type: " + pigState.type);
        }

        pig.getBody().setLinearVelocity(pigState.vx, pigState.vy);
        pig.setHp(pigState.hp);
        return pig;
    }

}
