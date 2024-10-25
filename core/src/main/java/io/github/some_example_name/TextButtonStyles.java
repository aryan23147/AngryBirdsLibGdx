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
    public static TextButton.TextButtonStyle TextButtonpause = new TextButton.TextButtonStyle();

    static {
        Texture up = new Texture("abs/ButtonBackground.png");
        Drawable updraw = new TextureRegionDrawable(up);
        updraw.setMinWidth(250);
        updraw.setMinHeight(100);

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));

        TextButtonStyleNormal.up = updraw;
        TextButtonStyleNormal.fontColor = Color.WHITE;
        TextButtonStyleNormal.overFontColor = Color.BLACK;
        TextButtonStyleNormal.font = font;

        Texture exitTexture = new Texture(Gdx.files.internal("abs/BackButton.png"));
        Drawable backDraw = new TextureRegionDrawable(exitTexture);
        backDraw.setMinHeight(100);
        backDraw.setMinWidth(100);

        TextButtonStyleback.up = backDraw;
        TextButtonStyleback.font = font;
        TextButtonStyleback.fontColor = Color.WHITE;
        TextButtonStyleback.overFontColor = Color.BLACK;

        Texture PauseTexture =new Texture(Gdx.files.internal(("PauseButton.png")));
    }
}
