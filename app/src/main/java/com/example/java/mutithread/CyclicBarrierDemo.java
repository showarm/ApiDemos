package com.example.java.mutithread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by chenshao on 16/12/22.

 1、前端调用restful api,已知商品id以后,调用后端商品起价接口、商品图片信息接口

 2、调用后端商品起价接口、商品图片信息接口

 3、在汇总模块中将多个接口的值拼接组合返回给前端


 *
 */
public class CyclicBarrierDemo {

    private static final int THREAD_NUMBER = 2;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUMBER, new Runnable() {
            public void run() {
                System.out.println("2个接口都调用完成进行后续处理。。。");
            }
        });
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread t = new Thread(new Worker(barrier, i));
            t.start();
        }
    }

    static class Worker implements Runnable {
        private CyclicBarrier barrier;
        private int apiIndex;

        public Worker(CyclicBarrier barrier, int apiIndex) {
            this.barrier = barrier;
            this.apiIndex = apiIndex;
        }

        public void run() {
            int time = RANDOM.nextInt(1000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接口" + apiIndex + "调用完成");
            try {
                barrier.await(); // 等待所有线程都调用过此函数才能进行后续动作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
