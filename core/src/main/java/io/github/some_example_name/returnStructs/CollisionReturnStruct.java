package io.github.some_example_name.returnStructs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class CollisionReturnStruct {
    public Texture backgroundTexture;
    public AssetManager assetManager;

    public CollisionReturnStruct(AssetManager assetManager, Texture backgroundTexture) {
        this.assetManager = assetManager;
        this.backgroundTexture = backgroundTexture;
    }
}
