package io.github.some_example_name.setUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.actors.extras.Slingshot;
import io.github.some_example_name.returnStructs.SetUpReturnStruct;
import io.github.some_example_name.screens.GameScreen;
import io.github.some_example_name.screens.Main;
import io.github.some_example_name.screens.MainMenuScreen;

import static io.github.some_example_name.screens.GameScreen.PIXELS_TO_METERS;
import static io.github.some_example_name.setUp.TextButtonStyles.*;
import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleMute;

public class GameSetUp {
    public static SetUpReturnStruct initializeGameComponents() {
        SpriteBatch batch = new SpriteBatch();
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        OrthographicCamera cam = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS, Gdx.graphics.getHeight() / PIXELS_TO_METERS);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Stage stage = new Stage();
//        World world = new World(new Vector2(0, -5f), false);
//        World world = new World(new Vector2(0, -9.8f), false);
        World world = new World(new Vector2(0, -22f), false);
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        Slingshot slingshot = new Slingshot(130, 50, world);
        return new SetUpReturnStruct(batch, font, cam, stage, world, debugRenderer, slingshot);
    }

    public static SetUpReturnStruct setupUIComponents(Stage stage, LevelManager levelManager, Window pauseWindow, Window winWindow, Window loseWindow, Main game, int level, BitmapFont font,GameScreen gs) {
        // Create the UI buttons, windows, and table layout
        TextButton pauseButton = new TextButton("", TextButtonStylepause);
//        TextButton winButton = new TextButton("Win", TextButtonStyleDummy);
        TextButton musiconoffButton = new TextButton("", TextButtonStyleMusic);
        TextButton nextLevelButton = new TextButton("", WindowCreator.createButtonStyle("abs/NextButton.png", font));
        TextButton backButton =new TextButton("",TextButtonStyleback);
        backButton.setSize(100,150);
        backButton.setPosition(100, stage.getHeight()-140); // Adjust x and y for placement
        stage.addActor(backButton);

        // Set up pause, win, and lose windows
        pauseWindow = WindowCreator.createPauseWindow(font,musiconoffButton,stage,game,level,gs);
        winWindow = WindowCreator.createWinWindow(nextLevelButton, level, game, stage, font);
        loseWindow = WindowCreator.createLoseWindow(level, game, stage, font);

        // Configure table layout
        Table table = new Table();
        table.top().left().setFillParent(true);
        table.add(pauseButton).padTop(5f).padLeft(5f).top().left();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        return new SetUpReturnStruct(pauseButton, musiconoffButton, nextLevelButton, backButton, pauseWindow, winWindow, loseWindow);
    }

    public static void setupListeners(Stage stage, TextButton pauseButton, TextButton musiconoffButton, TextButton backButton, Main game, Slingshot slingshot, GameScreen gameScreen) {
        Gdx.input.setInputProcessor(stage);
        final boolean[] click = {false};


        musiconoffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleMusic();
                musiconoffButton.setStyle(game.isMusicPlaying() ? TextButtonStyleMusic : TextButtonStyleMute);
                click[0] =true;
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                Gdx.app.log("Back Button","button clicked");
                game.setScreen(new MainMenuScreen(game));
            }
        });
//        winButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                winWindow.setVisible(true);
//                winWindow.toFront();
//                click[0] =true;
//            }
//        });

        pauseButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.togglePause();
            }
        });

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!click[0]) {
                    System.out.println("\nClicked at: ("+x+", "+y+")\n");
                    if(x>=15 && x<=89 && y>=653 && y<=703){

                    }
                    else{slingshot.releaseBird(x,y);}
                    click[0]=false;
                }
            }
        });
    }


}
