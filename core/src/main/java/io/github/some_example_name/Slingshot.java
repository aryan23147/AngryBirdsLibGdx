package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Slingshot {
    private Sprite sprite;
    public float x;
    public float y;
    private Bird bird;
    public Slingshot(float x,float y){
        Texture texture=new Texture(Gdx.files.internal("abs/Slingshot.png"));
        this.sprite=new Sprite(texture);
        this.x=x;
        this.y=y;
        bird=null;
    }
    public void draw(Batch batch){
        sprite.setPosition(x,y);
        sprite.draw(batch);
    }
    public boolean isEmpty(){
        return bird==null;
    }
    public void loadBird(Bird bird){
        if(this.bird==null){
            this.bird=bird;
        }
    }
    public void releaseBird() {
        if (this.bird != null) {
            // Logic to release the bird (launch the bird, reset slingshot)
            this.bird.dispose(); //this line is deletable
            this.bird = null;
        }
    }

}
