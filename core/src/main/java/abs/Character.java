package abs;
import io.github.some_example_name.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character {
    protected World world;
    protected Sprite sprite;
    protected Body body;
    protected float radius;
    protected float mass;
    protected int hp;
    protected float velocity;
    protected float x, y;
    protected FixtureDef fixtureDef;

    // Constructor: must be called from subclasses like Bird or Pig
    public Character(World world, float radius, float x, float y, float mass) {
        this.world=world;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.mass=mass;

        // Subclasses must define how to create the body and fixture
        createBody(world, x, y);
    }

    // Abstract method to be defined by each character type
    protected abstract void createBody(World world, float x, float y);

    public abstract void disappear(); // Implemented by subclasses
}
