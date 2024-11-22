package io.github.some_example_name.setUp;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.some_example_name.actors.Bird;
import io.github.some_example_name.actors.Block;
import io.github.some_example_name.actors.Pig;
import io.github.some_example_name.returnStructs.CollisionReturnStruct;

import static io.github.some_example_name.screens.GameScreen.DAMAGE_MULTIPLIER;

public class CollisionManager {

    public static CollisionReturnStruct show(World world, int level) {
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
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Object userDataA = contact.getFixtureA().getBody().getUserData();
                Object userDataB = contact.getFixtureB().getBody().getUserData();
                float collisionForce = impulse.getNormalImpulses()[0];

                if (userDataA instanceof Pig) {
                    System.out.println("A");
                    ((Pig) userDataA).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
                }
                else if (userDataA instanceof Block) {
                    System.out.println("B");
                    ((Block) userDataA).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
                }
                if (userDataB instanceof Pig) {
                    System.out.println("C");
                    ((Pig) userDataB).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
                }
                else if (userDataB instanceof Block) {
                    System.out.println("D");
                    ((Block) userDataB).reduceHP(collisionForce * DAMAGE_MULTIPLIER);
                }
            }
        });

        // Initialize resources and set up the game for the given level
        System.out.println("Starting level: " + level);

        return new CollisionReturnStruct(assetManager, backgroundTexture);
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

    private static void processOtherCollision(Object objectA, Object objectB){
//        float collisionForce = 50;
        float damage = 1;

        if (objectA instanceof Pig) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Pig) objectA).reduceHP(damage);
            System.out.println("Pig damaged by: " + damage);
        }
        else if (objectA instanceof Block) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Block) objectA).reduceHP(damage);
            System.out.println("Block damaged by: " + damage);
        }
        if (objectB instanceof Pig) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Pig) objectB).reduceHP(damage);
            System.out.println("Pig damaged by: " + damage);
        }
        else if (objectB instanceof Block) {
//            float damage = collisionForce * DAMAGE_MULTIPLIER;
            ((Block) objectB).reduceHP(damage);
            System.out.println("Block damaged by: " + damage);
        }
    }

    // Helper function to calculate damage from kinetic energy
    private static int calculateDamage(float kineticEnergy) {
        // You can scale the damage based on the kinetic energy. For example:
        int baseDamage = 5;  // Base damage (could be adjusted)
        float damageMultiplier = 0.01f;  // Damage multiplier based on kinetic energy
        int calculatedDamage = (int) (baseDamage + kineticEnergy * damageMultiplier);

        // Clamp the damage to a reasonable range (e.g., max damage of 100)
        calculatedDamage = Math.min(calculatedDamage, 100);
        return calculatedDamage;
    }
}
