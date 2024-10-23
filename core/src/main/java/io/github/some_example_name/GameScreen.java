package io.github.some_example_name;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameScreen implements Screen {
    private final Game game;
    private int level;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
//    private Skin skin;
//    private Table table;
    private TextButton back;
    private Bird redBird;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ground ground;
    private OrthographicCamera cam;
    private Pig pig;
    private Box box1;
    private Box box2;
    private Box box3;
    public GameScreen(Game game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
        float w=Gdx.graphics.getWidth();
        float h=Gdx.graphics.getHeight();
        cam=new OrthographicCamera();
        cam.setToOrtho(false,w,h);
//        stage =new Stage();
//        skin = new Skin(Gdx.files.internal("Skin/uiskin.json"));
//        table=new Table();

        assetManager=new AssetManager();
        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();
        ground=new Ground(world);
        redBird = new RedBird(world,80,150);
        pig=new MediumPig(650,200,world);
        box1=new Box(550,90,world,64,64);
        box2=new Box(550,55,world,64,64);
        box3=new Box(500,55,world,64,64);
//        cam=new OrthographicCamera(30,30*(Gdx.gr))




    }

    @Override
    public void show() {
        assetManager.load("GameBackground.png",Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);
        // Initialize resources and setup the game for the given level
        System.out.println("Starting level: " + level);
//        stage.addActor(table);
//        table.setFillParent(true);
//        table.add(redBird);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        font.draw(batch, "Playing Level: " + level, 200, 400);
//        font.draw(batch, "Press ESC to return to Main Menu", 200, 300);
//        ground.draw(batch);
        pig.draw(batch);
        redBird.draw(batch);
        ground.draw(batch);
        box1.draw(batch);
        box2.draw(batch);
        box3.draw(batch);
        batch.end();

        // Return to Main Menu if ESC is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
//        stage.act(delta);
//        stage.draw();
        world.step(1/60f, 6, 2);
        debugRenderer.render(world, cam.combined);

        // Add the game logic for this level here (e.g., enemy spawning, player movement)
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        font.dispose();
        debugRenderer.dispose();
    }
}
