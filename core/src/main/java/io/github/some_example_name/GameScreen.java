package io.github.some_example_name;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleDummy;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMusic;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleMute;
import static io.github.some_example_name.TextButtonStyles.TextButtonStyleback;
import static io.github.some_example_name.TextButtonStyles.TextButtonStylepause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {
    private static final float PPM = 1;
    private static boolean isPaused=false;
    private final Main game;
    private final int level;
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private Table table;
    private TextButton backButton;
    private AssetManager assetManager;
    private Texture backgroundTexture;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Ground ground;
    private OrthographicCamera cam;
    private  Window pauseWindow;
    private TextButton pauseButton;
    private TextButton winButton;
    private Window winWindow;
    private TextButton nextLevelButton;
    private Window loseWindow;
    private final boolean isMusicPlaying;
    private TextButton musiconoffButton;
    private Slingshot slingshot;
    private Queue<Bird> birdQueue;
    private List<Bird> allBirds;
    private List<Block> allBlocks;
    private List<Pig> allPigs;
    LevelManager levelManager;
    static final float PIXELS_TO_METERS = 100f;
    private static final float DAMAGE_MULTIPLIER = 0.1f;


    public GameScreen(Main game, int level) {
        this.game = game;  // Save the reference to the main game object
        this.level = level;  // Save the level number
        isMusicPlaying = game.isMusicPlaying();

        SetUpReturnStruct return1 = GameSetUp.initializeGameComponents();
        this.batch = return1.batch;
        this.font = return1.font;
        this.cam = return1.cam;
        this.stage = return1.stage;
        this.world = return1.world;
        this.debugRenderer = return1.debugRenderer;
        this.slingshot = return1.slingshot;

        levelManager = new LevelManager();

        SetUpReturnStruct return2 = GameSetUp.setupUIComponents(stage, levelManager, pauseWindow, winWindow, loseWindow, game, level, font);
        this.pauseButton = return2.pause;
        this.musiconoffButton = return2.music;
        this.nextLevelButton = return2.next;
        this.backButton = return2.back;
        this.pauseWindow = return2.pauseWindow;
        this.winWindow = return2.winWindow;
        this.loseWindow = return2.loseWindow;

        show();
        ReturnStruct returnStruct = null;
        if(level==1) returnStruct= levelManager.setupWorldObjectsLevel1(world);
        else if(level == 2) returnStruct= levelManager.setupWorldObjectsLevel2(world);
        else if (level == 3) returnStruct= levelManager.setupWorldObjectsLevel3(world);

        GameSetUp.setupListeners(stage, pauseButton, musiconoffButton, backButton, game, slingshot, this);

        if(returnStruct!=null) {
            this.birdQueue = returnStruct.birdQueue;
            this.allBirds = returnStruct.birds;
            this.allPigs = returnStruct.pigs;
            this.allBlocks = returnStruct.blocks;
            this.ground = returnStruct.ground;
        }

        Block wallLeft = new Block(0, 50, world, 1, 720, false);
        Block wallRight = new Block(1380, 50, world, 1, 720, false);
        batch.begin();
        wallLeft.draw(batch);
        wallRight.draw(batch);
        batch.end();


    }
    public void togglePause() {
        isPaused = !isPaused;
        pauseWindow.setVisible(isPaused);
        pauseWindow.toFront();
        if (isPaused) {
            pause();
        } else {
            resume();
        }
    }

    private void checkAndLoadBird() {
        final boolean[] isWon = {false};
        if(allPigs.isEmpty()){
            isWon[0] =true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Code to execute after 2 seconds
                    winWindow.setVisible(true);
                    winWindow.toFront();
                }
            }, 2f);
        }

        else if(birdQueue.isEmpty() && slingshot.isEmpty()){

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if(!isWon[0]) {
                        // Code to execute after 2 seconds
                        loseWindow.setVisible(true);
                        loseWindow.toFront();
                    }
                }
            }, 5f);
        }
        else if (slingshot.isEmpty()) {
            Bird bird = birdQueue.removeFirst();  // Get the next bird from the queue
            slingshot.loadBird(bird);  // Load it into the slingshot
            bird.setInSlingshot(true);  // Disable gravity for the bird in the slingshot
            bird.getBody().setTransform(520 / PIXELS_TO_METERS, 420 / PIXELS_TO_METERS, 0);
        }

        // Any bird left in the queue should fall due to gravity
        for (Bird b : birdQueue) {
            b.setInSlingshot(false);  // Enable gravity for birds not in the slingshot
        }
    }

    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManager.load("GameBackground.png",Texture.class);
        assetManager.finishLoading();
        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                Object userDataA = fixtureA.getUserData();
                Object userDataB = fixtureB.getUserData();

                if (userDataA != null && userDataB != null) {
                    System.out.println("Collision detected between: "
                        + userDataA.getClass().getSimpleName() + " and "
                        + userDataB.getClass().getSimpleName());
                    handleCollision(userDataA,userDataB);
                } else {
                    System.out.println("Collision detected between: "
                        + (userDataA != null ? userDataA.getClass().getSimpleName() : "Ground")
                        + " and "
                        + (userDataB != null ? userDataB.getClass().getSimpleName() : "Ground"));
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Object userDataA = contact.getFixtureA().getBody().getUserData();
                Object userDataB = contact.getFixtureB().getBody().getUserData();
                float collisionForce = impulse.getNormalImpulses()[0];

                if (userDataA instanceof Pig && userDataB instanceof Bird) {
                    ((Pig) userDataA).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
                }
            }

        });

        // Initialize resources and set up the game for the given level
        System.out.println("Starting level: " + level);
    }

    private void handleCollision(Object userDataA,Object userDataB) {
        if (userDataA instanceof Bird || userDataB instanceof Bird) {
            if (userDataA instanceof Bird) processCollision(userDataA, userDataB);
            else processCollision(userDataB, userDataA); // Reverse order
        }
    }

    private void processCollision(Object birdObject, Object otherObject) {
        if (birdObject instanceof Bird) {
            Bird bird = (Bird) birdObject;
            float mass = bird.getMass();  // Assuming `getMass()` returns the bird's mass
            Vector2 velocity = bird.getVelocity();  // Assuming `getVelocity()` returns the bird's velocity as a Vector2

            // Calculate the kinetic energy (KE = 1/2 * m * v^2)
            float kineticEnergy = 0.5f * mass * velocity.len2();  // len2() gives v^2 (velocity squared)

            System.out.println("Kinetic Energy of bird: " + kineticEnergy);

            if (otherObject instanceof Pig) {
                Pig pig = (Pig) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                pig.reduceHP(damage);
                System.out.println("Pigs hp reduced by " + damage); // Debugging statement
            } else if (otherObject instanceof Block) {
                Block block = (Block) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                block.reduceHP(damage);
                System.out.println("Blocks hp reduced by " + damage); // Debugging statement
            }
        }
    }

    // Helper function to calculate damage from kinetic energy
    private int calculateDamage(float kineticEnergy) {
        // You can scale the damage based on the kinetic energy. For example:
        int baseDamage = 5;  // Base damage (could be adjusted)
        float damageMultiplier = 0.01f;  // Damage multiplier based on kinetic energy
        int calculatedDamage = (int) (baseDamage + kineticEnergy * damageMultiplier);

        // Clamp the damage to a reasonable range (e.g., max damage of 100)
        calculatedDamage = Math.min(calculatedDamage, 100);
        return calculatedDamage;
    }


    private void update() {
        // Assume birds is a list of active birds
        List<Bird> birdsToRemove = new ArrayList<>();
        List<Pig> pigsToRemove =new ArrayList<>();
        List<Block> blocksToRemove =new ArrayList<>();
        for (Bird bird : allBirds) {
            if (bird.isLaunched() && bird.getBody().getLinearVelocity().len2() < 0.05f) { // Velocity close to zero
                birdsToRemove.add(bird);
            }
        }
        for(Bird bird :birdsToRemove){
            bird.disappear();
            allBirds.remove(bird); // Remove from active list
        }
        for(Pig pig:allPigs){
            if(pig.getHp()<=0){
                pigsToRemove.add(pig);
            }
        }
        for(Block block:allBlocks){
            if(block.getHp()<=0){
                blocksToRemove.add(block);
            }
        }
        for(Pig pig:pigsToRemove){
            pig.disappear();
            allPigs.remove(pig);
        }
        for(Block block:blocksToRemove){
            block.disappear();
            allBlocks.remove(block);
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        font.draw(batch, "Playing Level: " + level, 200, 400);

        for (Bird bird : allBirds) {
            bird.draw(batch);
        }
        for (Pig pig : allPigs){
            pig.draw(batch);
        }
        for (Block block : allBlocks){
            block.draw(batch);
        }

        slingshot.draw(batch);
        ground.draw(batch);
        batch.end();

        update();

        stage.act(delta);
        stage.draw();
        if (!isPaused) world.step(1 / 60f, 6, 2);
        for (Block block : allBlocks) {
            block.update();
        }
        checkAndLoadBird();
        checkForEscapeKey();
        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(30,30 , 0));
        Gdx.input.setInputProcessor(stage);
    }

    private void checkForEscapeKey() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
        cam.update();
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        font.dispose();
        debugRenderer.dispose();
        for (Block block : allBlocks) {
            block.dispose();
        }
    }
}
