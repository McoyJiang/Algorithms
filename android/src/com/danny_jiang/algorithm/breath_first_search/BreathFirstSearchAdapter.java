package com.danny_jiang.algorithm.breath_first_search;

import android.os.Message;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.danny_jiang.algorithm.common.AlgorithmAdapter;
import com.danny_jiang.algorithm.data_structure.Graph;
import com.danny_jiang.algorithm.views.AlgorithmBall;
import com.danny_jiang.algorithm.views.AlgorithmLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BreathFirstSearchAdapter extends AlgorithmAdapter{
    private static final String TAG = BreathFirstSearchAdapter.class.getSimpleName();
    private static final int DEQUEUE = 1;

    private volatile Graph graph;

    private List<AlgorithmBall> actorList;
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

        graph.addEdge(1, 2);
        graph.addEdge(1, 5);

        //graph.addEdge(2, 0);
        //graph.addEdge(2, 1);
        graph.addEdge(2, 3);
        graph.addEdge(2, 5);

        //graph.addEdge(3, 2);
        graph.addEdge(3, 5);

        //graph.addEdge(4, 0);
        graph.addEdge(4, 5);

        //graph.addEdge(5, 1);
        //graph.addEdge(5, 2);
        //graph.addEdge(5, 3);
        //graph.addEdge(5, 4);
    }

    @Override
    protected void animation(Message msg) {
        switch (msg.what) {
            case DEQUEUE:
                Gdx.app.postRunnable(() ->
                        actorList.get(msg.arg1).deadStatus());
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

        StringBuilder stringBuilder = new StringBuilder();
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            currentIndex = start = queue.poll();
            await((BeforeWaitCallback) () -> sDecodingThreadHandler.sendMessage(
                    sDecodingThreadHandler.obtainMessage(DEQUEUE, currentIndex, -1)
            ));
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
        }
        Log.e(TAG, "search result : " + stringBuilder.toString());

    }
}