package com.danny_jiang.algorithm.breath_first_search;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.Graph;
import com.danny_jiang.algorithm.views.AlgorithmBall;
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

    private volatile Graph graph;

    private List<AlgorithmBall> actorList;
    private Map<Integer, List<AlgorithmLine>> lineMap = new HashMap<>();
    private volatile int currentIndex;

    @Override
    protected void inflateStage() {
        actorList = new ArrayList<>();

        for (int i = 0; i < graph.V; i++) {
            AlgorithmBall algorithmBall = new AlgorithmBall("" + i);
            algorithmBall.setIndex(i);
            algorithmBall.setSize(100, 100);
            algorithmBall.activeStatus();
            actorList.add(algorithmBall);
        }

        AlgorithmBall ball = actorList.get(0);
        ball.setPosition(200, 1200);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(1);
        ball.setPosition(900, 1200);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(2);
        ball.setPosition(450, 1000);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(3);
        ball.setPosition(450, 700);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(4);
        ball.setPosition(150, 400);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        ball = actorList.get(5);
        ball.setPosition(900, 500);
        ball.setOrigin(ball.getX() + ball.getWidth() / 2,
                ball.getY() + ball.getHeight() / 2);

        LinkedList<Integer>[] lists = graph.adj;
        for (int i = 0; i < lists.length; i++) {
            AlgorithmBall srcBall = actorList.get(i);
            LinkedList<Integer> linkedList = lists[i];
            for (Integer integer : linkedList) {
                AlgorithmBall dstBall = actorList.get(integer);

                AlgorithmLine line = new AlgorithmLine(srcBall.getOriginX(), srcBall.getOriginY(),
                        dstBall.getOriginX(), dstBall.getOriginY(), 10);
                line.setLineColor(Color.PURPLE);
                lineMap.get(i).add(line);
                stage.addActor(line);
            }
        }

        for (AlgorithmBall bal : actorList) {
            stage.addActor(bal);
        }
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
    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case DEQUEUE:
                final int index = msg.arg1;
                final LinkedList<Integer> linkedList = (LinkedList<Integer>) msg.obj;
                Gdx.app.postRunnable(() -> {
                            ParallelAction parallel = Actions.parallel();
                            List<AlgorithmLine> algorithmLines = lineMap.get(index);
                            for (AlgorithmLine algorithmLine : algorithmLines) {
                                RunnableAction run = Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        algorithmLine.setLineColor(Color.GREEN);
                                    }
                                });
                                parallel.addAction(run);
                            }
                            RunnableAction last = Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    for (Integer integer : linkedList) {
                                        actorList.get(integer).deadStatus();
                                    }
                                }
                            });

                            stage.addAction(Actions.sequence(Actions.delay(0.3f), parallel,
                                    Actions.delay(0.5f), last));
                        }
                );
                break;
            case START:
                Gdx.app.postRunnable(() -> actorList.get(0).deadStatus());
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
                        Message message = sDecodingThreadHandler.obtainMessage(DEQUEUE, queue);
                        message.arg1 = currentIndex;
                        sDecodingThreadHandler.sendMessage(message);
                    }
            );
        }
        Log.e(TAG, "search result : " + stringBuilder.toString());
    }
}