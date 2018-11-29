package com.danny_jiang.algorithm.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeRendererUtils {

    public static void drawRoundRect(float x, float y, float width, float height,
                                     ShapeRenderer renderer, float radius) {
        renderer.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        // Four side rectangles, in clockwise order
        renderer.rect(x + radius, y, width - 2*radius, radius);
        renderer.rect(x + width - radius, y + radius, radius, height - 2*radius);
        renderer.rect(x + radius, y + height - radius, width - 2*radius, radius);
        renderer.rect(x, y + radius, radius, height - 2*radius);

        // Four arches, clockwise too
        renderer.arc(x + radius, y + radius, radius, 180f, 90f);
        renderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
        renderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        renderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }

    public static void drawStack(float x, float y, float width, float height,
                                     ShapeRenderer renderer, float radius) {
        renderer.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        // Four side rectangles, in clockwise order
        renderer.rect(x + radius, y, width - 2*radius, radius);
        renderer.rect(x + width - radius, y + radius, radius, height - 2*radius);
        renderer.rect(x + radius, y + height - radius, width - 2*radius, radius);
        renderer.rect(x, y + radius, radius, height - 2*radius);

        // Four arches, clockwise too
        renderer.arc(x + radius, y + radius, radius, 180f, 90f);
        renderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
        renderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        renderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
}
