package com.xixi.loadBalance;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通轮询算法
 */
public class RoundRobin {
    private static Integer index = 0;
    private static List<String> nodes = new ArrayList<>();
    // 记录轮询输出结果
    private static StringBuffer stringBuffer = new StringBuffer();
    // 准备模拟数据
    static {
        nodes.add("192.168.1.101");
        nodes.add("192.168.1.103");
        nodes.add("192.168.1.102");
        System.out.println("普通轮询算法的所有节点："+nodes);//打印所有节点
    }

    // 关键代码
    public String selectNode(){
        String ip = null;
//      之前写错的代码
//      synchronized (index){
        synchronized (RoundRobin.class){
            // 下标复位
            if(index>=nodes.size()) index = 0;
            ip = nodes.get(index);
            stringBuffer.append(Thread.currentThread().getName()+"==获取节点："+ ip +"\n");
            index++;
        }
        return ip;
    }

    // 并发测试：两个线程循环获取节点
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            RoundRobin roundRobin1 = new RoundRobin();
            for (int i=1;i<=21;i++){
                roundRobin1.selectNode();
                try {
                    // 模拟业务处理耗时
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            RoundRobin roundRobin1 = new RoundRobin();
            for (int i=1;i<=21;i++){
                roundRobin1.selectNode();
                try {
                    // 模拟业务处理耗时
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 等待轮询完成，统一输出
        Thread.sleep(3000);
        System.out.println(stringBuffer.toString());
    }
}

