package io.github.some_example_name.serialization.state;

import io.github.some_example_name.actors.birds.Bird;
import io.github.some_example_name.actors.birds.BlackBird;
import io.github.some_example_name.actors.birds.BlueBird;
import io.github.some_example_name.actors.birds.RedBird;
import io.github.some_example_name.bonusStuff.Power;

public class BirdState {
    public float x,y;
    public float vx,vy;
    public boolean isLaunched;
    public boolean hasUsedPower;
    public String type;
    public Power power;
    public float radius;
    public float mass;
    public BirdState(){
    }
    public BirdState(Bird bird){
        if (bird instanceof RedBird) {
            this.type = "red";
        } else if (bird instanceof BlackBird) {
            this.type = "black";
        } else if (bird instanceof BlueBird) {
            this.type = "blue";
        }
        this.x=bird.getX();
        this.y=bird.getY();
        this.vx=bird.getVelocity().x;
        this.vy=bird.getVelocity().y;
        this.power=bird.getPower();
        this.isLaunched= bird.isLaunched();
        this.hasUsedPower= bird.hasUsedPower();
        this.radius = bird.getRadius();
        this.mass = bird.getMass();
    }
}
