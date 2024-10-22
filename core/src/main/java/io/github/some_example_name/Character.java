package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Character {
    protected World world;
    protected Body body;
    protected float radius;
    protected float mass;
    protected float x, y;
    protected float width;
    protected float height;

    // Constructor: must be called from subclasses like Bird or Pig
    public Character(World world, float radius, float x, float y, float mass, float width, float height) {
        this.world = world;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.width = width;
        this.height = height;

        // Subclasses must define how to create the body
        createBody(world, x, y);
    }

    // Abstract method to create the body - overridden in subclasses
    protected abstract void createBody(World world, float x, float y);

    public abstract void disappear(); // Implemented by subclasses
}
