////package io.github.some_example_name;
//import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleDummy;
//import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleMusic;
//import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleMute;
//import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStyleback;
//import static io.github.some_example_name.setUp.TextButtonStyles.TextButtonStylepause;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.utils.Queue;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Window;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.Timer;
//import io.github.some_example_name.screens.*;
//import io.github.some_example_name.actors.*;
//import io.github.some_example_name.returnStructs.*;
//import io.github.some_example_name.setUp.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class GameScreen implements Screen {
//    private static final float PPM = 1;
//    private boolean isPaused=false;
//    private final Main game;
//    private final int level;
//    private SpriteBatch batch;
//    private BitmapFont font;
//    private Stage stage;
//    private Table table;
//    private TextButton backButton;
//    private AssetManager assetManager;
//    private Texture backgroundTexture;
//    private World world;
//    private Box2DDebugRenderer debugRenderer;
//    private Ground ground;
//    private OrthographicCamera cam;
//    private Window pauseWindow;
//    private TextButton pauseButton;
//    private TextButton winButton;
//    private Window winWindow;
//    private TextButton nextLevelButton;
//    private Window loseWindow;
//    private final boolean isMusicPlaying;
//    private TextButton musiconoffButton;
//    private Slingshot slingshot;
//    private Queue<Bird> birdQueue;
//    private List<Bird> allBirds;
//    private List<Block> allBlocks;
//    private List<Pig> allPigs;
//    LevelManager levelManager;
//    public static final float PIXELS_TO_METERS = 100f;
//    private static final float DAMAGE_MULTIPLIER = 0.05f;
//
//    public static final short CATEGORY_BIRD = 0x0001;    // Binary 00000001
//    public static final short CATEGORY_PIG = 0x0002;     // Binary 00000010
//    public static final short CATEGORY_BLOCK = 0x0003;     // Binary 00000011
//    public static final short CATEGORY_SLINGSHOT = 0x0004; // Binary 00000100
//
//    public GameScreen(Main game, int level) {
//        this.game = game;  // Save the reference to the main game object
//        this.level = level;  // Save the level number
//        isMusicPlaying = game.isMusicPlaying();
//        initializeGameComponents();
//        levelManager = new LevelManager();
//        setupUIComponents();
//        show();
//        ReturnStruct returnStruct = null;
//        if(level==1) returnStruct= levelManager.setupWorldObjectsLevel1(world);
//        else if(level == 2) returnStruct= levelManager.setupWorldObjectsLevel2(world);
//        else if (level == 3) returnStruct= levelManager.setupWorldObjectsLevel3(world);
//        setupListeners();
//
//        if(returnStruct!=null) {
//            this.birdQueue = returnStruct.birdQueue;
//            this.allBirds = returnStruct.birds;
//            this.allPigs = returnStruct.pigs;
//            this.allBlocks = returnStruct.blocks;
//            this.ground = returnStruct.ground;
//        }
//    }
//
//    private void initializeGameComponents() {
//        batch = new SpriteBatch();
//        font = new BitmapFont(Gdx.files.internal("font/Chewy.fnt"));
//        cam = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS, Gdx.graphics.getHeight() / PIXELS_TO_METERS);
//        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        stage = new Stage();
//        world = new World(new Vector2(0, -9.8f), false);
////        world = new World(new Vector2(0, -22f), false);
//        debugRenderer = new Box2DDebugRenderer();
//        slingshot = new Slingshot(130, 50, world);
//    }
//
//    private void setupUIComponents() {
//        // Create the UI buttons, windows, and table layout
//        pauseButton = new TextButton("", TextButtonStylepause);
//        winButton = new TextButton("Win", TextButtonStyleDummy);
//        musiconoffButton = new TextButton("", TextButtonStyleMusic);
//        nextLevelButton = new TextButton("", WindowCreator.createButtonStyle("abs/NextButton.png", font));
//        backButton =new TextButton("",TextButtonStyleback);
//        backButton.setSize(100,150);
//        backButton.setPosition(100, stage.getHeight()-140); // Adjust x and y for placement
//        stage.addActor(backButton);
//
//        // Set up pause, win, and lose windows
//        WindowCreator windowCreator = new WindowCreator(levelManager);
//        pauseWindow = windowCreator.createPauseWindow(font,musiconoffButton,stage,game,level);
//        winWindow = windowCreator.createWinWindow(nextLevelButton, level, game, stage, font);
//        loseWindow = windowCreator.createLoseWindow(level, game, stage, font);
//
//        // Manually set size and position for winButton
//        winButton.setSize(100, 100); // Set the size of the win button
//        winButton.setPosition(Gdx.graphics.getWidth() / 2f +100, Gdx.graphics.getHeight() / 2f + 160); // Set position in the center
//
////         Add buttons to the stage directly
//        stage.addActor(winButton);
//
//        // Configure table layout
//        table = new Table();
//        table.top().left().setFillParent(true);
//        table.add(pauseButton).padTop(5f).padLeft(5f).top().left();
//        stage.addActor(table);
//        Gdx.input.setInputProcessor(stage);
//
//    }
//
//    private void setupListeners() {
//        Gdx.input.setInputProcessor(stage);
//        final boolean[] click = {false};
//
//        pauseButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                togglePause();
//                click[0] =true;
//            }
//        });
//
//        musiconoffButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.toggleMusic();
//                musiconoffButton.setStyle(game.isMusicPlaying() ? TextButtonStyleMusic : TextButtonStyleMute);
//                click[0] =true;
//            }
//        });
//        backButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event,float x,float y){
//                Gdx.app.log("Back Button","button clicked");
//                game.setScreen(new MainMenuScreen(game));
//            }
//        });
//        winButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                winWindow.setVisible(true);
//                winWindow.toFront();
//                click[0] =true;
//            }
//        });
//
//        stage.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if(!click[0]) {
//                    slingshot.releaseBird(x,y);
//                    click[0]=false;
//                }
//            }
//        });
//    }
//
//    public void togglePause() {
//        isPaused = !isPaused;
//        pauseWindow.setVisible(isPaused);
//        pauseWindow.toFront();
//        if (isPaused) {
//            pause();
//        } else {
//            resume();
//        }
//    }
//
//    private void checkAndLoadBird() {
//        Timer timer = new Timer();
//        if(birdQueue.isEmpty() && slingshot.isEmpty() && !allPigs.isEmpty()){
//
//            timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    // Code to execute after 2 seconds
//                    loseWindow.setVisible(true);
//                    loseWindow.toFront();
//                }
//            }, 5f);
//        }
//        else if(allPigs.isEmpty()){
//            timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    // Code to execute after 2 seconds
//                    winWindow.setVisible(true);
//                    winWindow.toFront();
//                }
//            }, 5f);
//        }
//        else if (slingshot.isEmpty()) {
//            Bird bird = birdQueue.removeFirst();  // Get the next bird from the queue
//            slingshot.loadBird(bird);  // Load it into the slingshot
//            bird.setInSlingshot(true);  // Disable gravity for the bird in the slingshot
//            bird.getBody().setTransform(520 / PIXELS_TO_METERS, 420 / PIXELS_TO_METERS, 0);
//        }
//
//        // Any bird left in the queue should fall due to gravity
//        for (Bird b : birdQueue) {
//            b.setInSlingshot(false);  // Enable gravity for birds not in the slingshot
//        }
//    }
//
//    @Override
//    public void show() {
//        assetManager = new AssetManager();
//        assetManager.load("GameBackground.png",Texture.class);
//        assetManager.finishLoading();
//        backgroundTexture = assetManager.get("GameBackground.png", Texture.class);
//
//        world.setContactListener(new ContactListener() {
//
//            Object userDataA;
//            Object userDataB;
//            @Override
//            public void beginContact(Contact contact) {
//                Fixture fixtureA = contact.getFixtureA();
//                Fixture fixtureB = contact.getFixtureB();
//
//                userDataA = fixtureA.getUserData();
//                userDataB = fixtureB.getUserData();
//
//                if (userDataA != null && userDataB != null) {
//                    System.out.println("Collision detected between: "
//                        + userDataA.getClass().getSimpleName() + " and "
//                        + userDataB.getClass().getSimpleName());
////                    handleCollision(userDataA,userDataB);
//                } else {
//                    System.out.println("Collision detected between: "
//                        + (userDataA != null ? userDataA.getClass().getSimpleName() : "Ground")
//                        + " and "
//                        + (userDataB != null ? userDataB.getClass().getSimpleName() : "Ground"));
//                }
//
//            }
//
//
//            @Override
//            public void endContact(Contact contact) {
//                System.out.println("Contact ended");
//            }
//
//            @Override
//            public void preSolve(Contact contact, Manifold oldManifold) {}
//
//
//            //            public void postSolve(Contact contact, ContactImpulse impulse) {
//////                System.out.println("post solve is coming");
//////                Object userDataA = contact.getFixtureA().getBody().getUserData();
//////                Object userDataB = contact.getFixtureB().getBody().getUserData();
//////                float collisionForce = impulse.getNormalImpulses()[0];
//////
//////                if (userDataA instanceof Pig && userDataB instanceof Bird) {
//////                    ((Pig) userDataA).reduceHP((float) collisionForce * DAMAGE_MULTIPLIER);
//////                    System.out.println("uwfnuwfunwfbwnfjwn");
//////                }
////                // Handle other cases similarly
////
////            }
//            @Override
//            public void postSolve(Contact contact, ContactImpulse impulse) {
////                System.out.println("post solve whdhwudhuwhudhuwhu");
////                Object userDataA = contact.getFixtureA().getBody().getUserData();
////                Object userDataB = contact.getFixtureB().getBody().getUserData();
//                if(userDataA!=null && userDataB!=null){
//                    System.out.println(userDataA.getClass()+" "+userDataB.getClass());
//                }
////
//                float collisionForce = impulse.getNormalImpulses()[0]; // Primary collision force
//
//                if (userDataA instanceof Pig && userDataB instanceof Bird) {
//                    // Bird colliding with Pig
//                    ((Pig) userDataA).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
//                    System.out.println("Pig's HP reduced by collision force (Bird hit Pig).");
//                } else if (userDataB instanceof Pig && userDataA instanceof Bird) {
//                    // Bird colliding with Pig (reverse case)
//                    ((Pig) userDataB).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
//                    System.out.println("Pig's HP reduced by collision force (Bird hit Pig).");
//                }
//
//                else if (userDataA instanceof Block && userDataB instanceof Bird) {
//                    // Bird colliding with Block
//                    ((Block) userDataA).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
//                    System.out.println("Block's HP reduced by collision force (Bird hit Block).");
//                } else if (userDataB instanceof Block && userDataA instanceof Bird) {
//                    // Bird colliding with Block (reverse case)
//                    ((Block) userDataB).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
//                    System.out.println("Block's HP reduced by collision force (Bird hit Block).");
//                }
////                if(userDataA!=null && userDataB!=null) {
////                    System.out.println(userDataA.getClass().getSimpleName() + " " + userDataB.getClass().getSimpleName());
////                    // Add more cases as needed for other object interactions}
////                }
//            }
//
//
//        });
//
//        // Initialize resources and set up the game for the given level
//        System.out.println("Starting level: " + level);
//    }
//
//    private void handleCollision(Object userDataA,Object userDataB) {
////        Fixture fixtureA = contact.getFixtureA();
////        Fixture fixtureB = contact.getFixtureB();
////
////        Object userDataA = fixtureA.getBody().getUserData();
////        Object userDataB = fixtureB.getBody().getUserData();
//
////        if (userDataA == null || userDataB == null) return;
//        System.out.println("Reached handleCollison m"+userDataA.getClass()+userDataB.getClass());
//        if (userDataA instanceof Bird || userDataB instanceof Bird) {
//            System.out.println("Reached handleCollison method second if block");
//            if (userDataA instanceof Bird) processCollision(userDataA, userDataB);
//            else processCollision(userDataB, userDataA); // Reverse order
//        }
//    }
//
//    private void processCollision(Object birdObject, Object otherObject) {
//        if (birdObject instanceof Bird) {
//
//            Bird bird = (Bird) birdObject;
//
//            if (otherObject instanceof Pig) {
//                Pig pig = (Pig) otherObject;
//                pig.reduceHP(10);
//                System.out.println("Pigs hp reduced"); // Debugging statement
////                if (pig.getHp() <= 0) {
////                    pig.disappear();
////                    allPigs.remove(pig);
////                }
//            } else if (otherObject instanceof Block) {
//                Block block = (Block) otherObject;
//                block.reduceHP(5);
//                System.out.println("Blocks hp reduced");
////                if (block.getHp() <= 0) {
////                    block.disappear();
////                    allBlocks.remove(block);
////                }
//            }
//        }
//    }
//
//    private void update() {
//        // Assume birds is a list of active birds
//        List<Bird> birdsToRemove = new ArrayList<>();
//        List<Pig> pigsToRemove =new ArrayList<>();
//        List<Block> blocksToRemove =new ArrayList<>();
//        for (Bird bird : allBirds) {
//            if (bird.isLaunched() && bird.getBody().getLinearVelocity().len2() < 0.05f) { // Velocity close to zero
//                birdsToRemove.add(bird);
//            }
//        }
//        for(Bird bird :birdsToRemove){
//            bird.disappear();
//            allBirds.remove(bird); // Remove from active list
//        }
//        for(Pig pig:allPigs){
//            if(pig.getHp()<=0){
//                pigsToRemove.add(pig);
//            }
//        }
//        for(Block block:allBlocks){
//            if(block.getHp()<=0){
//                blocksToRemove.add(block);
//            }
//            else if(block.getHp()<5f){
//                System.out.println("whfuwufh");
//                block.breakIt();
//            }
//        }
//        for(Pig pig:pigsToRemove){
//            pig.disappear();
//            allPigs.remove(pig);
//        }
//        for(Block block:blocksToRemove){
//            block.disappear();
//            allBlocks.remove(block);
//        }
//    }
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        if (backgroundTexture != null) {
//            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        }
//        font.draw(batch, "Playing Level: " + level, 200, 400);
//
//        for (Bird bird : allBirds) {
//            bird.draw(batch);
//        }
//        for (Pig pig : allPigs){
//            pig.draw(batch);
//        }
//        for (Block block : allBlocks){
//            block.draw(batch);
//        }
//
//        slingshot.draw(batch);
//        ground.draw(batch);
//        batch.end();
//
//        update();
////        for (Bird bird : allBirds) {
////            Vector2 bodyPosition = bird.getBody().getPosition(); // Get position in meters
//////            bird.setPosition(bodyPosition.x * PIXELS_TO_METERS, bodyPosition.y * PIXELS_TO_METERS);
//////            bird.update();
////        }
//
//        stage.act(delta);
//        stage.draw();
//        if (!isPaused) world.step(1 / 60f, 6, 2);
//        for (Block block : allBlocks) {
//            block.update();
//        }
//        checkAndLoadBird();
//        checkForEscapeKey();
//        debugRenderer.render(world, batch.getProjectionMatrix().cpy().scale(30,30 , 0));
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    private void checkForEscapeKey() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            game.setScreen(new MainMenuScreen(game));
//        }
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        cam.setToOrtho(false, width, height);
//        cam.update();
//    }
//    @Override
//    public void pause() {
//    }
//    @Override
//    public void resume() {
//    }
//    @Override
//    public void hide() {
//        dispose();
//    }
//    @Override
//    public void dispose() {
//        // Dispose of resources
//        batch.dispose();
//        font.dispose();
//        debugRenderer.dispose();
//        for (Block block : allBlocks) {
//            block.dispose();
//        }
//    }
//}
//
