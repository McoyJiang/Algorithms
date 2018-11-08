package com.danny_jiang.algorithm.data_structure.array;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.danny_jiang.algorithm.views.AlgorithmBall;

public class ArrayElement extends AlgorithmBall {


    public ArrayElement(String text) {
        super(text);
        setTextColor(Color.WHITE);
    }

    @Override
    public void defaultStatus() {
        String path = "data_structure/Array/rectangle.png";
        switch (getText()) {
            case "2":
                path = "data_structure/Array/blue.png";
                break;
            case "5":
                path = "data_structure/Array/brown.png";
                break;
            case "8":
                path = "data_structure/Array/yellow.png";
                break;
            case "9":
                path = "data_structure/Array/red.png";
                break;
            case "6":
                path = "data_structure/Array/green.png";
                break;
        }
        setRegion(new TextureRegion(new Texture(path)));
    }
}
