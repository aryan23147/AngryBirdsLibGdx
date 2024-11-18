package io.github.some_example_name;

import static io.github.some_example_name.Bird.PPM;
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
    public Block(float x, float y, World world, float width, float height) {
        this.X=x;
        this.Y=y;
        this.width = width;
        this.height = height;
        this.world=world;
        this.hp = 10f;

        sprite=new Sprite(new Texture("abs/WoodBlock.jpg"));
//        sprite.setPosition(x,y);
        sprite.setSize(width,height);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM,height/2/PPM);  // Convert radius from pixels to meters

        // Define the fixture
//        fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 0.5f;  // Adjust density for mass
//        fixtureDef.friction = 1f;  // Adjust friction for surface interaction
//        fixtureDef.restitution = 0.1f;  // Adjust restitution for bounciness

        fixtureDef = createFixture(sprite.getWidth(), sprite.getHeight());

        // Attach the fixture to the body
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        // Dispose of the shape after use
        shape.dispose();
    }

    private FixtureDef createFixture(float width, float height) {
        FixtureDef fixtureDef = new FixtureDef();

        float scaledWidth = width;
        float scaledHeight = height;

        Shape shape;
        PolygonShape rectShape = new PolygonShape();
        rectShape.setAsBox((scaledWidth / 2) / PPM, (scaledHeight / 2) / PPM);
        shape = rectShape;

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 5f;
        fixtureDef.restitution = 0.6f;  // Slight bounce
        return fixtureDef;
    }

    public void reduceHP(float damage) {
        hp -= damage;
        if (hp <= 0) {
            disappear();
        }
    }

    public void disappear() {
        world.destroyBody(this.getBody());
    }

    private Body getBody() {
        return body;
    }
    public float getHp(){
        return hp;
    }

    public void update() {
        // Update the sprite position based on the body's physics position
        X = body.getPosition().x * PPM - width / 2;
        Y = body.getPosition().y * PPM - height / 2;
        sprite.setPosition(X, Y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));  // Set sprite rotation to match body's rotation
    }

    public void dispose(){
        this.sprite.getTexture().dispose();
    }
    public void draw(Batch batch){
        sprite.setPosition(X,Y);
        sprite.draw(batch);

    }
}
