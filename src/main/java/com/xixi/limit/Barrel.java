package com.xixi.limit;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 			漏桶算法将请求缓存在桶中，服务流程匀速处理。超出桶容量的部分丢弃。
 * 			漏桶算法主要用于保护内部的处理业务，保障其稳定有节奏的处理请求，但是无法根据流量的波动弹性调整响应能力。
 * 			现实中，类似容纳人数有限的服务大厅开启了固定的服务窗口。
 * 		优缺点
 * 		有效的挡住了外部的请求，保护了内部的服务不会过载
 * 内部服务匀速执行，无法应对流量洪峰，无法做到弹性处理突发任务
 * 任务超时溢出时被丢弃。现实中可能需要缓存队列辅助保持一段时间
 */
public class Barrel {


    public static void main(String[] args) {
        //桶，用阻塞队列实现，容量为3
        final LinkedBlockingQueue<Integer> que = new LinkedBlockingQueue(3);

        //定时器，相当于服务的窗口，2s处理一个
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            int v = que.poll();
            System.out.println("处理："+v);
        },2000,2000, TimeUnit.MILLISECONDS);


        //无数个请求，i 可以理解为请求的编号
        int i=0;
        while (true) {
            i++;
            try {
//                System.out.println("put:"+i);
                //如果是put，会一直等待桶中有空闲位置，不会丢弃
//                que.put(i);
                //等待1s如果进不了桶，就溢出丢弃
                boolean offer = que.offer(i, 1000, TimeUnit.MILLISECONDS);
                System.out.println("put "+i+": success ? "+offer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

}
