package io.github.some_example_name;

import static io.github.some_example_name.Bird.ppm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box {
    private Body body;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private float X;
    private float Y;
    private float width;
    private float height;
    private SpriteBatch batch;
    private Sprite sprite;
    public Box(float x,float y, World world,float width,float height) {
        this.X=x;
        this.Y=y;
        this.width = width;
        this.height = height;

        sprite=new Sprite(new Texture("abs/WoodBlock.jpg"));
//        sprite.setPosition(x,y);
        sprite.setSize(width,height);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / ppm, y / ppm);  // Convert x, y from pixels to meters

        this.body = world.createBody(bodyDef);

        // Create a circle shape for the bird's body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/ppm,height/2/ppm);  // Convert radius from pixels to meters

        // Define the fixture
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;  // Adjust density for mass
        fixtureDef.friction = 1f;  // Adjust friction for surface interaction
        fixtureDef.restitution = 0.1f;  // Adjust restitution for bounciness

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape after use
        shape.dispose();
    }
    public void update() {
        // Update the sprite position based on the body's physics position
        X = body.getPosition().x * ppm - width / 2;
        Y = body.getPosition().y * ppm - height / 2;
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
