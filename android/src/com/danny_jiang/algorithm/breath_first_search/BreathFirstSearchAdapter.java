package com.danny_jiang.algorithm.breath_first_search;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.Graph;
import com.danny_jiang.algorithm.views.AlgorithmLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BreathFirstSearchAdapter extends AlgorithmAdapter{
    private static final String TAG = BreathFirstSearchAdapter.class.getSimpleName();
    private static final int DEQUEUE = 1;
    private static final int START = 2;
    private static final int ENQUEUE = 3;

    private volatile Graph graph;

    private Label label;
    private List<GraphBall> actorList;
    private Map<Integer, List<AlgorithmLine>> lineMap = new HashMap<>();
    private volatile int currentIndex;

    @Override
    protected void inflateStage() {
        actorList = new ArrayList<>();

        for (int i = 0; i < graph.V; i++) {
            GraphBall algorithmBall = new GraphBall("" + i);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.defaultStatus();
            actorList.add(algorithmBall);
        }

        GraphBall ball = actorList.get(0);
        ball.setPosition(200, 1300);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(1);
        ball.setPosition(900, 1300);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(2);
        ball.setPosition(450, 1100);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(3);
        ball.setPosition(450, 800);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(4);
        ball.setPosition(150, 500);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(5);
        ball.setPosition(900, 600);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        LinkedList<Integer>[] lists = graph.adj;
        for (int i = 0; i < lists.length; i++) {
            GraphBall srcBall = actorList.get(i);
            LinkedList<Integer> linkedList = lists[i];
            for (Integer integer : linkedList) {
                GraphBall dstBall = actorList.get(integer);

                AlgorithmLine line = new AlgorithmLine(srcBall.getOriginX(), srcBall.getOriginY(),
                        dstBall.getOriginX(), dstBall.getOriginY(), 10);
                line.setLineColor(Color.GRAY);
                lineMap.get(i).add(line);
                stage.addActor(line);
            }
        }

        for (GraphBall bal : actorList) {
            stage.addActor(bal);
        }

        // add Queue
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont(Gdx.files.internal("font/default.fnt"));
        style.fontColor = new Color(1, 0, 0, 1);
        label = new Label("Queue: []", style);
        label.setPosition(20, 380);
        stage.addActor(label);
    }

    @Override
    protected void initData() {
        graph = new Graph(6);

        graph.addEdge(0, 2);
        graph.addEdge(0, 4);

        graph.addEdge(1, 5);

        graph.addEdge(2, 1);
        graph.addEdge(2, 3);

        graph.addEdge(3, 5);

        graph.addEdge(4, 5);

        lineMap.put(0, new ArrayList<>());
        lineMap.put(1, new ArrayList<>());
        lineMap.put(2, new ArrayList<>());
        lineMap.put(3, new ArrayList<>());
        lineMap.put(4, new ArrayList<>());
        lineMap.put(5, new ArrayList<>());
    }

    @Override
    protected void animation(Message msg) {
        final int index = msg.arg1;
        Log.e(TAG, "animation: index is " + index);
        final LinkedList<Integer> linkedList = (LinkedList<Integer>) msg.obj;
        switch (msg.what) {
            case ENQUEUE:
                Gdx.app.postRunnable(() -> {
                    ParallelAction parallel = Actions.parallel();
                    List<AlgorithmLine> algorithmLines = lineMap.get(index);
                    for (AlgorithmLine algorithmLine : algorithmLines) {
                        RunnableAction run1 = Actions.run(() ->
                                algorithmLine.setLineColor(Color.GREEN));
                        RunnableAction run2 = Actions.run(() ->
                                algorithmLine.setLineColor(Color.GRAY));
                        RunnableAction run3 = Actions.run(() ->
                                algorithmLine.setLineColor(Color.GREEN));
                        parallel.addAction(Actions.sequence(run1, Actions.delay(0.3f),
                                run2, Actions.delay(0.3f), run3));
                    }

                    final StringBuilder stringBuilder = new StringBuilder();
                    RunnableAction activeRun = Actions.run(() -> {
                        for (int i = 0; i < linkedList.size(); i++) {
                            Integer integer = linkedList.get(i);
                            actorList.get(integer).activeStatus();
                            stringBuilder.append(i == linkedList.size() - 1 ? integer : integer + ", ");
                        }
                        actorList.get(index).deadStatus();
                    });

                    RunnableAction setLabelRun = Actions.run(() -> {
                        label.setText("current Queue is " + linkedList.toString());
                    });
                    stage.addAction(Actions.sequence(Actions.delay(0.3f), parallel,
                            Actions.delay(0.5f), activeRun, setLabelRun));
                });
                break;
            case DEQUEUE:
                Gdx.app.postRunnable(() -> {
                            RunnableAction iteratorActorRun = Actions.run(() -> {
                                actorList.get(index).iteratorStatus();
                                label.setText(index + "is polled from Queue,\n" +
                                        "find its adjacent vertices,\n" +
                                        "and put them into Queue");
                            });

                            stage.addAction(Actions.sequence(iteratorActorRun,
                                    Actions.delay(0.3f)));
                        }
                );
                break;
            case START:
                Gdx.app.postRunnable(() -> {
                    actorList.get(0).iteratorStatus();
                    label.setText("First dequeue 0 from Queue,\n " +
                            "and put its adjacent vertices into Queue");
                });
                break;
        }
    }

    @Override
    protected void algorithm() {
        Log.e(TAG, "Following is Breadth First Traversal " +
                "(starting from vertex 2)");
        await();
        int start = 0;
        // Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[graph.V];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[start] = true;
        queue.add(start);
        await((BeforeWaitCallback) () ->
                {
                    Message message = sDecodingThreadHandler.obtainMessage(START, queue);
                    message.arg1 = currentIndex;
                    sDecodingThreadHandler.sendMessage(message);
                }
        );
        StringBuilder stringBuilder = new StringBuilder();
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            currentIndex = start = queue.poll();
            await((BeforeWaitCallback) () ->
                    {
                        Message message = sDecodingThreadHandler.obtainMessage(DEQUEUE, queue);
                        message.arg1 = currentIndex;
                        sDecodingThreadHandler.sendMessage(message);
                    }
            );
            stringBuilder.append(start + " ");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = graph.adj[start].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
            await((BeforeWaitCallback) () ->
                    {
                        Message message = sDecodingThreadHandler.obtainMessage(ENQUEUE, queue);
                        message.arg1 = currentIndex;
                        sDecodingThreadHandler.sendMessage(message);
                    }
            );
        }
        Log.e(TAG, "search result : " + stringBuilder.toString());
    }
}