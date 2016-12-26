package com.example.java.mutithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenshao on 16/12/22.
 * 死锁：A等B，B等A
 */
public class DeadlockDemo {

    //class A
    public static class A implements Runnable {
        public void run() {
            synchronized (B.class) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A.class) {
                    System.out.println("A println: i am finished");
                }
            }
        }
    }

    //class B
    public static class B implements Runnable {
        public void run() {
            synchronized (A.class) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B.class) {
                    System.out.println("B println: i am finished");
                }
            }
        }
    }

    //main class
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new A());
        executorService.submit(new B());
    }
}
