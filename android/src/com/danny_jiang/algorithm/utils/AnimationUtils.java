package com.danny_jiang.algorithm.utils;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.danny_jiang.algorithm.views.AlgorithmBall;

public class AnimationUtils {
    public static Action curveSwitchActors(AlgorithmBall actorFirst, AlgorithmBall actorSecond, Runnable switchRunnable) {
        float actorFirstX = actorFirst.getX();
        float actorFirstY = actorFirst.getY();

        float actorSecondX = actorSecond.getX();
        float actorSecondY = actorSecond.getY();

        Bezier<Vector2> bezierFirst = new Bezier<Vector2>(
                new Vector2(actorFirstX, actorFirstY),
                new Vector2(actorFirstX - actorFirstX / 3, actorFirstY - actorFirstY / 6),
                new Vector2(actorSecondX - actorSecondX / 4, actorSecondY - actorSecondY / 6),
                new Vector2(actorSecondX, actorSecondY)
        );
        Bezier<Vector2> bezierSecond = new Bezier<Vector2>(
                new Vector2(actorSecondX, actorSecondY),
                new Vector2(actorSecondX + actorSecondX / 4, actorSecondY + actorSecondY / 6),
                new Vector2(actorFirstX + actorFirstX / 3, actorFirstY + actorFirstY / 6),
                new Vector2(actorFirstX, actorFirstY)
        );
        BezierAction bezierFirstAction = BezierAction.createAction(bezierFirst, 1f);
        bezierFirstAction.setTarget(actorFirst);
        BezierAction bezierSecondAction = BezierAction.createAction(bezierSecond, 1f);
        bezierSecondAction.setTarget(actorSecond);

        ParallelAction parallel = Actions.parallel(bezierFirstAction, bezierSecondAction);
        return Actions.sequence(parallel, Actions.run(switchRunnable));
    }
}
