package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Slingshot {
    private Sprite sprite;
    public float x;
    public float y;
    public Slingshot(float x,float y){
        Texture texture=new Texture(Gdx.files.internal("abs/Slingshot.png"));
        this.sprite=new Sprite(texture);
        this.x=x;
        this.y=y;
    }
    public void draw(Batch batch){
        sprite.setPosition(x,y);
        sprite.draw(batch);
    }
}
