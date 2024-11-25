package io.github.some_example_name.setUp;

import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleRestart;
import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleSave;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.screens.LevelSelectionScreen;
import io.github.some_example_name.screens.Main;
import io.github.some_example_name.screens.MainMenuScreen;
import io.github.some_example_name.serialization.state.GameState;

public class WindowCreator {
    public static LevelManager levelManager;
    public static float score;
    public static Window winWindow;
    public static Window loseWindow;
    private static Label scoreLabel;
    public WindowCreator(LevelManager levelManager){
        this.levelManager=levelManager;
    }

    public static Window createPauseWindow(BitmapFont font, TextButton musiconoffButton, Stage stage, Main game, int level, GameScreen gameScreen){
        Texture backgroundTexture = new Texture("abs/PauseWindowBackground (3).png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        // Define custom style
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;
        windowStyle.background = backgroundDrawable;

        // Create and style the window
        Window pauseWindow = new Window("", windowStyle);
        TextButton saveGameButton =new TextButton("",TextButtonStyleSave);
        TextButton restartButton= new TextButton("",TextButtonStyleRestart);
        pauseWindow.setSize(450, 540); // Width and height in pixels

        pauseWindow.setPosition(
            (stage.getWidth() - pauseWindow.getWidth()) / 2,  // Center horizontally
            (stage.getHeight() - pauseWindow.getHeight()) / 2  // Center vertically
        );
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
                game.setScreen(new GameScreen(game, level));
            }
        });
        // Define action for the save game button
        saveGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectionScreen(game));
                gameScreen.togglePause();
                levelManager.saveLevel(level, true);
                GameState.saveGame(gameScreen);
                System.out.println("Level " + level + " saved.");
            }
        });

        pauseWindow.addActor(musiconoffButton);
        pauseWindow.addActor(saveGameButton);
        pauseWindow.addActor(restartButton);

        musiconoffButton.setPosition(50, 80);  // Adjust based on desired layout
        saveGameButton.setPosition(175, 80);
        restartButton.setPosition(300, 80);
//        saveGameButton
        stage.addActor(pauseWindow);
        pauseWindow.setVisible(false);

        return pauseWindow;
    }
    public static Window createWinWindow(TextButton nextLevelButton, int level, Main game, Stage stage, BitmapFont font) {
        Texture backgroundTexture = new Texture("abs/WinWindowBackground.png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        Window.WindowStyle winWindowStyle = new Window.WindowStyle();
        winWindowStyle.titleFont = font;
        winWindowStyle.titleFontColor = Color.WHITE;
        winWindowStyle.background = backgroundDrawable;

        winWindow = new Window("", winWindowStyle);
        winWindow.setVisible(false);

        TextButton backButton = new TextButton("", createButtonStyle("abs/BackButton.png", font));
        TextButton restartButton = new TextButton("", createButtonStyle("abs/RestartButton.png", font));
        // Set button sizes
        nextLevelButton.setSize(100, 100); // Set width and height of the nextLevelButton
        backButton.setSize(100, 150); // Set width and height of the backButton

        // Position the buttons manually within the winWindow
        nextLevelButton.setPosition(300, 60); // Adjust x and y for placement
        backButton.setPosition(75, 30); // Adjust x and y for placement
        restartButton.setSize(100, 125); // Set width and height of the nextLevelButton
        restartButton.setPosition(186,55);

        // Add buttons to the winWindow without using table positioning
        winWindow.addActor(nextLevelButton);
        winWindow.addActor(backButton);
        winWindow.addActor(restartButton);

        // Set the window size and position to center it on the screen
        winWindow.setSize(470, 500);
        winWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - winWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - winWindow.getHeight() / 2f
        );

        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level<3){
                    game.setScreen(new GameScreen(game, level + 1));
                }
                else {
                    game.setScreen(new LevelSelectionScreen(game));
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        restartButton.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x ,float y){
                game.setScreen(new GameScreen(game,level));
            }
        });

        stage.addActor(winWindow);
        return winWindow;
    }

    public static Window createLoseWindow(int level, Main game, Stage stage, BitmapFont font) {
        Texture backgroundTexture = new Texture("abs/LoseWindowBackground.png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);

        Window.WindowStyle loseWindowStyle = new Window.WindowStyle();
        loseWindowStyle.titleFont = font;
        loseWindowStyle.titleFontColor = Color.WHITE;
        loseWindowStyle.background = backgroundDrawable;

        loseWindow = new Window("", loseWindowStyle);
        loseWindow.setVisible(false);

        TextButton restartButton = new TextButton("", createButtonStyle("abs/RestartButton.png", font));
        TextButton backButton = new TextButton("", createButtonStyle("abs/BackButton.png", font));

        // Set button sizes
        restartButton.setSize(100, 100); // Set width and height of the nextLevelButton
        backButton.setSize(100, 150); // Set width and height of the backButton

        // Position the buttons manually within the winWindow
        restartButton.setPosition(280, 60); // Adjust x and y for placement
        backButton.setPosition(55, 30); // Adjust x and y for placement

        // Add buttons to the winWindow without using table positioning
        loseWindow.addActor(restartButton);
        loseWindow.addActor(backButton);

        // Set the window size and position to center it on the screen
        loseWindow.setSize(470, 500);
        loseWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - loseWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - loseWindow.getHeight() / 2f
        );

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(loseWindow);

        return loseWindow;
    }
    public static void showScore(BitmapFont font, boolean isWon, int numBirds){
        // Create the score text
        String scoreText = "Your Score is: " + (int)CollisionManager.getScore(numBirds);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        scoreLabel = new Label(scoreText, labelStyle);
        if(isWon) {
            scoreLabel.setAlignment(Align.center); // Center-align the text
            scoreLabel.setPosition(
                winWindow.getWidth() / 2f - scoreLabel.getWidth() / 2f,
                winWindow.getHeight() / 3.2f
            );
        winWindow.addActor(scoreLabel);
        }
        else {
            scoreLabel.setAlignment(Align.center); // Center-align the text
            scoreLabel.setPosition(
                loseWindow.getWidth() / 2f - scoreLabel.getWidth() / 2f,
                loseWindow.getHeight() / 3.2f
            );
        loseWindow.addActor(scoreLabel);
        }
    }

    public static TextButton.TextButtonStyle createButtonStyle(String buttonName, BitmapFont font) {
        // Load the button textures
        Texture buttonUpTexture = new Texture(Gdx.files.internal(buttonName)); // Replace with your actual texture path
        Texture buttonDownTexture = new Texture(Gdx.files.internal(buttonName)); // Replace with your actual texture path

        // Create a Drawable for each button state
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(buttonUpTexture);
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(buttonDownTexture);

        // Create and style the button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = upDrawable;
        buttonStyle.down = downDrawable;
        buttonStyle.font = font; // Set your font

        return buttonStyle;
    }
    public  static Window createLoadedWindow(Stage stage,BitmapFont font,Main game,int level){
        Texture backgroundTexture = new Texture("abs/LoadWindowBackground.png");
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundTexture);
        Window.WindowStyle loadWindowStyle = new Window.WindowStyle();
        loadWindowStyle.titleFont = font;
        loadWindowStyle.titleFontColor = Color.WHITE;
        loadWindowStyle.background = backgroundDrawable;

        Window loadWindow = new Window("", loadWindowStyle);
        loadWindow.setVisible(false);

        TextButton loadButton = new TextButton("", TextButtonStyles.TextButtonStyleDummy);
        TextButton newButton = new TextButton("",TextButtonStyles.TextButtonStyleDummy );

        // Set button sizes
        loadButton.setSize(100, 100); // Set width and height of the nextLevelButton
        newButton.setSize(100, 100); // Set width and height of the backButton
        loadButton.setText("Load");
        newButton.setText("New");
        // Position the buttons manually within the winWindow
        loadButton.setPosition(300, 60); // Adjust x and y for placement
        newButton.setPosition(75, 60); // Adjust x and y for placement

        // Add buttons to the winWindow without using table positioning
        loadWindow.addActor(loadButton);
        loadWindow.addActor(newButton);

        // Set the window size and position to center it on the screen
        loadWindow.setSize(450, 500);
        loadWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - loadWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - loadWindow.getHeight() / 2f
        );

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, level,true));
                System.out.println("Playing saved game");
            }
        });

        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelManager.saveLevel(level, false);
                game.setScreen(new GameScreen(game,level));
                System.out.println("Playing new game");
            }
        });

        stage.addActor(loadWindow);

        return loadWindow;
    }
}
