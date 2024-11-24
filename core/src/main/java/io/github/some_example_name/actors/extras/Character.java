package io.github.some_example_name.actors.extras;

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
    }

    public abstract void disappear(); // Implemented by subclasses
}
