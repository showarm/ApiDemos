package com.example.java.mutithread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenshao on 16/12/22.

 1、CountDownLatch和CyclicBarrier都是通过设置的阻塞任务数减操作，而不是网上有些人说的CyclicBarrier就是通过count加知道任务数(JDK 1.7)

 2、CountDownLatch更多是直接结合AQS来做阻塞，而CyclicBarrier是直接用ReentrantLock来实现，虽然ReentrantLock也是使用了AQS来实现

 3、CyclicBarrier的代码结构看起来更简单清晰，CountDownLatch用的很多基础的AQS的方法

 4、CountDownLatch偏向于计数，CyclicBarrier可以指定栅栏结束以后的任务也方便重置当前栅栏

 CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
 CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；
 Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。


 使用java.util.concurrent.ExecutorService的awaitTermination阻塞主线程，等待线程池的所有线程执行完成。
 ------------------------
 * http://www.tuicool.com/articles/BBrMfii

 1、5个运动员相继都准备就绪

 2、教练员响起发令枪

 3、运动员起跑
 *
 */
public class CountDownLatchDemo {

    private static final int RUNNER_NUMBER = 5; // 运动员个数
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // 用于判断发令之前运动员是否已经完全进入准备状态，需要等待5个运动员，所以参数为5
        CountDownLatch readyLatch = new CountDownLatch(RUNNER_NUMBER);
        // 用于判断裁判是否已经发令，只需要等待一个裁判，所以参数为1
        CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < RUNNER_NUMBER; i++) {
            Thread t = new Thread(new Runner((i + 1) + "号运动员", readyLatch, startLatch));
            t.start();
        }
        try {
            readyLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("裁判：所有运动员准备完毕，开始...");
        startLatch.countDown();
    }

    static class Runner implements Runnable {
        private CountDownLatch readyLatch;
        private CountDownLatch startLatch;
        private String name;

        public Runner(String name, CountDownLatch readyLatch, CountDownLatch startLatch) {
            this.name = name;
            this.readyLatch = readyLatch;
            this.startLatch = startLatch;
        }

        public void run() {
            int readyTime = RANDOM.nextInt(1000);
            try {
                Thread.sleep(readyTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：我已经准备完毕.");
            readyLatch.countDown();
            try {
                startLatch.await();  // 等待裁判发开始命令
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "：开跑...");
        }
    }
}
