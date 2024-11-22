package io.github.some_example_name.actors;

import static io.github.some_example_name.actors.Bird.PPM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Block {
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private float X;
    private float Y;
    private float width;
    private float height;
    private SpriteBatch batch;
    private Sprite sprite;
    private float hp;
    private World world;

    public Block(float x, float y, World world, float width, float height, boolean isDynamic) {
        this.X = x;
        this.Y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.hp = 30f;

        sprite = new Sprite(new Texture("abs/WoodBlock.jpg"));
        sprite.setSize(width, height);
        sprite.setOrigin(width / 2, height / 2); // Set the sprite's origin to its center

        bodyDef = new BodyDef();
        if(isDynamic) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }
        else{
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set((x + width / 2) / PPM, (y + height / 2) / PPM); // Adjust for center alignment

        this.body = world.createBody(bodyDef);

        // Create a rectangle shape for the block's body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2) / PPM, (height / 2) / PPM); // Box2D uses half-widths and half-heights

        // Define the fixture
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;

        // Attach the fixture to the body
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Dispose of the shape after use
        shape.dispose();
    }

    public void reduceHP(float damage) {
        hp -= damage;
//        if (hp <= 0) {
//            disappear();
//        }
    }

    public void disappear() {
        world.destroyBody(this.getBody());
    }

    private Body getBody() {
        return body;
    }

    public float getHp() {
        return hp;
    }

    public void update() {
        // Update the sprite position based on the body's position
        X = (body.getPosition().x * PPM - width / 2)*0.93f; // Center-align sprite
        Y = (body.getPosition().y * PPM - height / 2)*0.93f;
        sprite.setPosition(X, Y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle())); // Match rotation
    }

    public void dispose() {
        this.sprite.getTexture().dispose();
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
