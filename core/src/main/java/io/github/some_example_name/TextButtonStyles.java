package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextButtonStyles {
    public static TextButton.TextButtonStyle TextButtonStyleNormal = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleback = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStylepause = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleRestart = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleSave = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleMusic = new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleMute=new TextButton.TextButtonStyle();
    public static TextButton.TextButtonStyle TextButtonStyleDummy=new TextButton.TextButtonStyle();

    static {
        Texture up = new Texture("abs/ButtonBackground.png");
        Drawable updraw = new TextureRegionDrawable(up);
        updraw.setMinWidth(250);
        updraw.setMinHeight(100);

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));

        // Normal button style
        TextButtonStyleNormal.up = updraw;
        TextButtonStyleNormal.fontColor = Color.WHITE;
        TextButtonStyleNormal.overFontColor = Color.BLACK;
        TextButtonStyleNormal.font = font;

        // Back button style
        Texture exitTexture = new Texture(Gdx.files.internal("abs/BackButton.png"));
        Drawable backDraw = new TextureRegionDrawable(exitTexture);
        backDraw.setMinHeight(100);
        backDraw.setMinWidth(100);
        TextButtonStyleback.up = backDraw;
        TextButtonStyleback.font = font;
        TextButtonStyleback.fontColor = Color.WHITE;
        TextButtonStyleback.overFontColor = Color.BLACK;

        // Pause button style
        Texture pauseTexture = new Texture(Gdx.files.internal("abs/PauseButton.png"));
        Drawable pauseBt = new TextureRegionDrawable(pauseTexture);
        pauseBt.setMinHeight(100);
        pauseBt.setMinWidth(100);
        TextButtonStylepause.up = pauseBt;
        TextButtonStylepause.font = font;
        TextButtonStylepause.fontColor = Color.WHITE;
        TextButtonStylepause.overFontColor = Color.BLACK;

        // Restart button style
        Texture restartTexture = new Texture(Gdx.files.internal("abs/RestartButton.png"));
        Drawable restartDraw = new TextureRegionDrawable(restartTexture);
        restartDraw.setMinHeight(100);
        restartDraw.setMinWidth(100);
        TextButtonStyleRestart.up = restartDraw;
        TextButtonStyleRestart.font = font;
        TextButtonStyleRestart.fontColor = Color.BLACK;
//        TextButtonStyleRestart.overFontColor = Color.BLACK;

        // Save game button style
        Texture saveTexture = new Texture(Gdx.files.internal("abs/SaveGameButton.png"));
        Drawable saveDraw = new TextureRegionDrawable(saveTexture);
        saveDraw.setMinHeight(100);
        saveDraw.setMinWidth(100);
        TextButtonStyleSave.up = saveDraw;
        TextButtonStyleSave.font = font;
        TextButtonStyleSave.fontColor = Color.BLACK;
//        TextButtonStyleSave.overFontColor = Color.BLACK;

        // Music on/off button style
        Texture musicTexture = new Texture(Gdx.files.internal("abs/MusicButton.png"));
        Drawable musicDraw = new TextureRegionDrawable(musicTexture);
        musicDraw.setMinHeight(100);
        musicDraw.setMinWidth(100);
        TextButtonStyleMusic.up = musicDraw;
        TextButtonStyleMusic.font = font;
        TextButtonStyleMusic.fontColor = Color.YELLOW;
//        TextButtonStyleMusic.overFontColor = Color.BLACK;
        Texture muteTexture = new Texture(Gdx.files.internal("abs/MuteButton.png"));
        Drawable muteDraw = new TextureRegionDrawable(muteTexture);
        muteDraw.setMinHeight(100);
        muteDraw.setMinWidth(100);
        TextButtonStyleMute.up = muteDraw;
        TextButtonStyleMute.font = font;
        TextButtonStyleMute.fontColor = Color.YELLOW;
        Texture dummyTexture = new Texture(Gdx.files.internal("abs/ButtonBackground.png"));
        Drawable dummyDraw = new TextureRegionDrawable(dummyTexture);
        dummyDraw.setMinHeight(100);
        dummyDraw.setMinWidth(100);
        TextButtonStyleDummy.up = dummyDraw;
        TextButtonStyleDummy.font = font;
        TextButtonStyleDummy.fontColor = Color.WHITE;
        TextButtonStyleDummy.overFontColor=Color.BLACK;
    }
}
