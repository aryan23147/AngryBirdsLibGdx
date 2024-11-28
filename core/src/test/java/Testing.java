import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.RedBird;
import io.github.some_example_name.actors.extras.Slingshot;
import io.github.some_example_name.bonusStuff.RedPower;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.screens.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Testing {

    private GameScreen gameScreen;
    private Slingshot slingshot;
    private Queue<Bird> birdQueue;
    private Window winWindow;
    private Window loseWindow;
    private World world;
    private Bird bird;

    @BeforeEach
    public void setUp() {
        world = new World(new Vector2(0, -9.8f), false);
        slingshot = new Slingshot(10, 20, world);
        birdQueue = new Queue<>();
        winWindow = new Window("Win", (Skin) null);
        loseWindow = new Window("Lose", (Skin) null);

        Bird bird = new RedBird(new World(new Vector2(0, -9.8f), false), 20, 40, new RedPower());
        this.bird = bird;

        gameScreen = new GameScreen(new Main(), 1, false);
        gameScreen.birdQueue = birdQueue;
        gameScreen.slingshot = slingshot;
        gameScreen.winWindow = winWindow;
        gameScreen.loseWindow = loseWindow;

        gameScreen.allPigs = new ArrayList<>();
    }

    @Test
    public void testCheckAndLoadBird_NoPigsLeft_LevelComplete() {
        // Setup: No pigs, some birds in queue
        birdQueue.addLast(bird);

        slingshot.isEmpty();

        // Call method
        gameScreen.checkAndLoadBird();



        // Assert win window is visible
        assertTrue(winWindow.isVisible());
        assertTrue(gameScreen.isCompleted);
    }

    @Test
    public void testCheckAndLoadBird_BirdQueueEmpty_GameOver() {
        // Setup: No pigs, bird queue empty, slingshot empty
        slingshot.isEmpty();

        // Call method
        gameScreen.checkAndLoadBird();

        // Assert lose window is visible
        assertTrue(loseWindow.isVisible());
        assertTrue(gameScreen.isCompleted);
    }

    @Test
    public void testCheckAndLoadBird_BirdLoadedIntoSlingshot() {
        // Setup: Bird queue has birds, slingshot is empty
        birdQueue.addLast(bird);

        slingshot.isEmpty();

        // Call method
        gameScreen.checkAndLoadBird();

        // Assert bird loaded into slingshot
        assertFalse(slingshot.isEmpty());
        assertTrue(slingshot.isInSlingshot(bird));
    }
}
