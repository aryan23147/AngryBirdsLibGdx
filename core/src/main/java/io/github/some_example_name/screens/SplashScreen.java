package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SplashScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Texture splashTexture;
    private Image splashImage;
    private float animationTime;  // Time to track animation duration
    private Texture BackgroundTexture;
    private Batch batch;

    public SplashScreen(Main game) {
        this.game = game;
        batch=new SpriteBatch();
    }

    @Override
    public void show() {

        splashTexture = new Texture(Gdx.files.internal("abs/LoadingScreenBirds.png"));
        splashImage = new Image(splashTexture);
        BackgroundTexture=new Texture(Gdx.files.internal("abs/LoadingScreenBackground.png"));
        // Set up the stage

        stage = new Stage(new ScreenViewport());
        stage.addActor(splashImage);
        splashImage.setFillParent(false);
        splashImage.setPosition(
            (stage.getWidth() - splashImage.getWidth()) / 2,  // Center horizontally
            (stage.getHeight() - splashImage.getHeight()) / 2  // Center vertically
        );


        animationTime = 0;  // Reset animation time
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if(BackgroundTexture!=null){
            batch.draw(BackgroundTexture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();
        // Update the stage (processes input and animation)
        stage.act(delta);
        stage.draw();

        // Track animation time
        animationTime += delta;

        // After 3 seconds, transition to the Main Menu screen
        if (animationTime >= 3.0f) {
            game.setScreen(new MainMenuScreen(game));  // Switch to the Main Menu
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        splashTexture.dispose();
    }
}
