package io.github.some_example_name;
import com.badlogic.gdx.Game;

public class Main extends Game {
    @Override
    public void create() {
        // Start with the main menu screen
        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        // Delegate render to the active screen
        super.render();
    }

    @Override
    public void dispose() {
        // Dispose of game resources
    }
}
