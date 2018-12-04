package test.com.danny_jiang.algorithm.data_structure.queu;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BurgerAdapterTest {

    //阻塞队列，FIFO
    private static ConcurrentLinkedQueue<Integer> concurrentLinkedQueue =
            new ConcurrentLinkedQueue<Integer>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Producer("producer1"));
//        executorService.submit(new Producer("producer2"));
//        executorService.submit(new Producer("producer3"));

        executorService.submit(new Consumer("consumer1"));
        executorService.submit(new Consumer("consumer2"));
        executorService.submit(new Consumer("consumer3"));
        executorService.submit(new Consumer("consumer4"));

    }

    static class Producer implements Runnable {
        private String name;

        public Producer(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 1; i < 20; ++i) {
                System.out.println(name+ "  生产： " + i);
                //concurrentLinkedQueue.add(i);
                try {
                    concurrentLinkedQueue.add(i);
                    Thread.sleep(100); //模拟慢速的生产，产生阻塞的效果
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        }
    }

    static class Consumer implements Runnable {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }
        public void run() {
            for (int i = 1; i < 10; ++i) {
                try {
                    //必须要使用take()方法在获取的时候阻塞
                    System.out.println(name+"消费： " +  concurrentLinkedQueue.poll());
                    Thread.sleep(200);
                    //使用poll()方法 将产生非阻塞效果
                    //System.out.println(name+"消费： " +  concurrentLinkedQueue.poll());

                    //还有一个超时的用法，队列空时，指定阻塞时间后返回，不会一直阻塞
                    //但有一个疑问，既然可以不阻塞，为啥还叫阻塞队列？
                    //System.out.println(name+" Consumer " +  concurrentLinkedQueue.poll(300, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}