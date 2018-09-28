package com.danny_jiang.algorithm.utils;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 * You can use this class to generate the bezier curve action
 */

public class BezierAction extends TemporalAction{
    protected float x, y;
    private Bezier<Vector2> bezier;

    /**
     * Set bezier curve param
     * @param bezierParam
     */
    public void setBezier (Bezier<Vector2> bezierParam) {
        bezier = bezierParam;
    }

    @Override
    protected void begin () {
        x = actor.getX();
        y = actor.getY();
    }

    @Override
    protected void end () {

    }

    @Override
    public void reset () {
        super.reset();
        bezier = null;
    }

    private Vector2 out = new Vector2();

    @Override
    protected void update (float percent) {

        bezier.valueAt(out, percent);
        target.setPosition(out.x, out.y);
    }

    public static BezierAction createAction (Bezier<Vector2> bezierParam, float duration) {
        Pool<BezierAction> pool = Pools.get(BezierAction.class);
        BezierAction action = pool.obtain();
        action.setDuration(duration);
        action.setBezier(bezierParam);
        action.setPool(pool);
        return action;
    }
}

