package io.github.some_example_name;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.sun.org.apache.xpath.internal.operations.Or;

public class SetUpReturnStruct {
    public TextButton back;
    public SpriteBatch batch = null;
    public BitmapFont font = null;
    public OrthographicCamera cam = null;
    public Stage stage = null;
    public World world = null;
    public Box2DDebugRenderer debugRenderer = null;
    public Slingshot slingshot = null;
    public TextButton pause = null;
    public TextButton music;
    public TextButton next;
    public Window pauseWindow, winWindow, loseWindow;

    public SetUpReturnStruct(SpriteBatch batch, BitmapFont font, OrthographicCamera cam, Stage stage, World world, Box2DDebugRenderer debugRenderer, Slingshot slingshot) {
        this.batch = batch;
        this.font = font;
        this.cam = cam;
        this.stage = stage;
        this.world = world;
        this.debugRenderer = debugRenderer;
        this.slingshot = slingshot;
    }

    public SetUpReturnStruct(TextButton pauseButton, TextButton musiconoffButton, TextButton nextLevelButton, TextButton backButton, Window pauseWindow, Window winWindow, Window loseWindow) {
        this.pause = pauseButton;
        this.music = musiconoffButton;
        this.next = nextLevelButton;
        this.back = backButton;
        this.pauseWindow = pauseWindow;
        this.winWindow = winWindow;
        this.loseWindow = loseWindow;
    }
}
