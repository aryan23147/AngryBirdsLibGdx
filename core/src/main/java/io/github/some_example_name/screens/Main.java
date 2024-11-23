package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Main extends Game {
    private Music backgroundMusic;
    private boolean isMusicPlaying;

    @Override
    public void create() {
        // Load and start playing background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        isMusicPlaying = true;

        // Start with the main menu screen
        this.setScreen(new SplashScreen(this));
    }

    // Methods to toggle music from any screen
    public void toggleMusic() {
        if (isMusicPlaying) {
            backgroundMusic.pause();
        } else {
            backgroundMusic.play();
        }
        isMusicPlaying = !isMusicPlaying;
    }
    //    if(!isMusicPlaying){
//        music.play();
//        isMusicPlaying=true;
//        musiconoffButton.setStyle(TextButtonStyleMusic);
//    }
//                else{
//        musiconoffButton.setStyle(TextButtonStyleMute);
//        isMusicPlaying=false;
//        music.stop();
//    }
    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }
    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    @Override
    public void render() {
        // Delegate render to the active screen
        super.render();
    }

    @Override
    public void dispose() {
        // Dispose of game resources
        backgroundMusic.dispose();
    }
}
