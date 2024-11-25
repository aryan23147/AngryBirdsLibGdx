package io.github.some_example_name.serialization.state;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.pigs.Pig;
import io.github.some_example_name.screens.GameScreen;

public class GameState {
    public int level;
    public List<BirdState> birds = new ArrayList<>();
    public List<BlockState> blocks = new ArrayList<>();
    public List<PigState> pigs = new ArrayList<>();
    public boolean isPaused;
    public GameState(){};
    public GameState(GameScreen screen) {
        this.level = screen.level;
        this.isPaused = GameScreen.isPaused;
        for (Bird bird : GameScreen.allBirds) {
            birds.add(new BirdState(bird));
        }
        for (Block block : screen.allBlocks) {
            blocks.add(new BlockState(block));
        }
        for (Pig pig : screen.allPigs) {
            pigs.add(new PigState(pig));
        }
    }
    public static void saveGame(GameScreen screen) {
        GameState gameState = new GameState(screen);
        Json json = new Json();
        String jsonString = json.toJson(gameState);
        String filePath="saved_games/game_state.json";
        Gdx.files.local(filePath).writeString(jsonString, false);  // Save JSON to file
    }
}
