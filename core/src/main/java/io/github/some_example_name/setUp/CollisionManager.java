package io.github.some_example_name.setUp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.blocks.Block;
import io.github.some_example_name.actors.blocks.GlassBlock;
import io.github.some_example_name.actors.blocks.StoneBlock;
import io.github.some_example_name.actors.blocks.WoodBlock;
import io.github.some_example_name.actors.extras.Ground;
import io.github.some_example_name.actors.pigs.KidPig;
import io.github.some_example_name.actors.pigs.KingPig;
import io.github.some_example_name.actors.pigs.MediumPig;
import io.github.some_example_name.actors.pigs.Pig;
import io.github.some_example_name.returnStructs.CollisionReturnStruct;

import java.util.List;

//import static io.github.some_example_name.screens.GameScreen.DAMAGE_MULTIPLIER;
//import static io.github.some_example_name.GameScreen1.DAMAGE_MULTIPLIER;

public class CollisionManager {

    private static float totalDamage;
    private static final float DAMAGE_MULTIPLIER = 0.1f;
    public static CollisionReturnStruct show(World world, int level, float damage) {
        totalDamage=damage;
        AssetManager assetManager = new AssetManager();
        assetManager.load("GameBackground.png", Texture.class);
        assetManager.finishLoading();
        Texture backgroundTexture = assetManager.get("GameBackground.png", Texture.class);

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
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

        // Initialize resources and set up the game for the given level
        System.out.println("Starting level: " + level);

        return new CollisionReturnStruct(assetManager, backgroundTexture, totalDamage);
    }

    private static void handleCollision(Object userDataA, Object userDataB) {
        if (userDataA instanceof Bird || userDataB instanceof Bird) {
            if (userDataA instanceof Bird) processBirdCollision(userDataA, userDataB);
            else processBirdCollision(userDataB, userDataA); // Reverse order
        }
        else{
            processOtherCollision(userDataA, userDataB);
        }
    }

    private static void processBirdCollision(Object birdObject, Object otherObject) {
        if (birdObject instanceof Bird) {
            Bird bird = (Bird) birdObject;
            float mass = bird.getMass();  // Assuming `getMass()` returns the bird's mass
            Vector2 velocity = bird.getVelocity();  // Assuming `getVelocity()` returns the bird's velocity as a Vector2

            // Calculate the kinetic energy (KE = 1/2 * m * v^2)
            float kineticEnergy = 0.5f * mass * velocity.len2();  // len2() gives v^2 (velocity squared)

            System.out.println("Kinetic Energy of bird: " + (int)kineticEnergy);

            if (otherObject instanceof KingPig) {
                Pig pig = (Pig) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                pig.reduceHP(damage);
                Sound pigCollisionSound = Gdx.audio.newSound(Gdx.files.internal("PigCollision.mp3"));
                pigCollisionSound.play();
                pig.damageAppearance("abs/DamagedKingPig.png", 100f);
                totalDamage+=damage;
                System.out.println("Pigs hp reduced by " + damage); // Debugging statement
            }
            else if (otherObject instanceof MediumPig) {
                Pig pig = (Pig) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                pig.reduceHP(damage);
                Sound pigCollisionSound = Gdx.audio.newSound(Gdx.files.internal("PigCollision.mp3"));
                pigCollisionSound.play();
                pig.damageAppearance("abs/DamagedPig.png", 40f);
                totalDamage+=damage;
                System.out.println("Pigs hp reduced by " + damage); // Debugging statement
            }
            else if (otherObject instanceof KidPig) {
                Pig pig = (Pig) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                pig.reduceHP(damage);
                Sound pigCollisionSound = Gdx.audio.newSound(Gdx.files.internal("PigCollision.mp3"));
                pigCollisionSound.play();
                pig.damageAppearance("abs/DamagedPig.png", 15f);
                totalDamage+=damage;
                System.out.println("Pigs hp reduced by " + damage); // Debugging statement
            }


            else if (otherObject instanceof WoodBlock) {
                Block block = (WoodBlock) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                block.reduceHP(damage);
                Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("WoodCollision.mp3"));
                woodCollisionSound.play();
                block.damageAppearance("abs/DamagedWoodenBlock.png", 30f);
                totalDamage+=damage;
                System.out.println("Blocks hp reduced by " + damage); // Debugging statement
            }
            else if (otherObject instanceof GlassBlock) {
                Block block = (GlassBlock) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                block.reduceHP(damage);
                Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("GlassCollision.mp3"));
                woodCollisionSound.play();
                block.damageAppearance("abs/DamagedGlassBlock.png", 10);
                totalDamage+=damage;
                System.out.println("Blocks hp reduced by " + damage); // Debugging statement
            }
            else if (otherObject instanceof StoneBlock) {
                Block block = (StoneBlock) otherObject;
                int damage = calculateDamage(kineticEnergy);  // Calculate damage based on KE
                block.reduceHP(damage);
                Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("StoneCollision.mp3"));
                woodCollisionSound.play();
                block.damageAppearance("abs/DamagedStoneBlock.png", 50);
                totalDamage+=damage;
                System.out.println("Blocks hp reduced by " + damage); // Debugging statement
            }

        }
    }

    private static void processOtherCollision(Object objectA, Object objectB){
//        float collisionForce = 50;
        float damage = 1;

        if (objectA instanceof Pig) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Pig) objectA).reduceHP(damage);
            Sound pigCollisionSound = Gdx.audio.newSound(Gdx.files.internal("PigCollision.mp3"));
            pigCollisionSound.play();
            totalDamage+=damage;
            System.out.println("Pig damaged by: " + damage);
        }
        else if (objectA instanceof WoodBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectA;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("WoodCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedWoodenBlock.png", 30f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
        else if (objectA instanceof GlassBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectA;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("GlassCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedGlassBlock.png", 15f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
        else if (objectA instanceof StoneBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectA;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("StoneCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedStoneBlock.png", 50f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
        if (objectB instanceof Pig) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Pig) objectB).reduceHP(damage);
            Sound pigCollisionSound = Gdx.audio.newSound(Gdx.files.internal("PigCollision.mp3"));
            pigCollisionSound.play();
            totalDamage+=damage;
            System.out.println("Pig damaged by: " + damage);
        }
        else if (objectB instanceof WoodBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectB;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("WoodCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedWoodenBlock.png", 30f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
        else if (objectB instanceof GlassBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectB;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("GlassCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedGlassBlock.png", 15f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
        else if (objectB instanceof StoneBlock) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            Block block = (Block) objectB;
            block.reduceHP(damage);
            Sound woodCollisionSound = Gdx.audio.newSound(Gdx.files.internal("StoneCollision.mp3"));
            woodCollisionSound.play();
            block.damageAppearance("abs/DamagedStoneBlock.png", 50f);
            totalDamage+=damage;
            System.out.println("Block damaged by: " + damage);
        }
    }

    // Helper function to calculate damage from kinetic energy
    private static int calculateDamage(float kineticEnergy) {
        // You can scale the damage based on the kinetic energy. For example:
        int baseDamage = 5;  // Base damage (could be adjusted)
        float damageMultiplier = 0.01f;  // Damage multiplier based on kinetic energy
        int calculatedDamage = (int) (baseDamage + kineticEnergy * damageMultiplier);

        // Clamp the damage to a reasonable range (e.g., max damage 100 points)
        calculatedDamage = Math.min(calculatedDamage, 100);
        return calculatedDamage;
    }

    public static float getScore(int numBirds, int level, List<Pig> allPigs, List<Block> allBlocks) {
        float damageDealt = 0;
        float totalHp1 = 105;
        float totalHp2 = 145;
        float totalHp3 = 400;
        float endHp = 0;
        for(Pig pig : allPigs){
            endHp += pig.getHp();
        }
        for(Block block : allBlocks){
            endHp += block.getHp();
        }
        if(level == 1){
            damageDealt=totalHp1-endHp;
        }
        else if(level == 2){
            damageDealt=totalHp2-endHp;
        }
        else if(level == 3){
            damageDealt=totalHp3-endHp;
        }
        return damageDealt*10 + numBirds*250;
    }
}
