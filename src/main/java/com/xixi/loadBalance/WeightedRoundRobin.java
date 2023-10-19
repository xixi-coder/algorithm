package com.xixi.loadBalance;

import java.util.ArrayList;
import java.util.List;

/**
 * 加权轮询算法
 */
public class WeightedRoundRobin {

    private static List<Node> nodes = new ArrayList<>();
    // 权重之和
    private static Integer totalWeight = 0;

    // 准备模拟数据
    static {
        nodes.add(new Node("A", 1));
        nodes.add(new Node("B", 2));
        nodes.add(new Node("C", 3));
        nodes.forEach(node -> totalWeight += node.getWeight());
    }

    /**
     * 按照当前权重（currentWeight）最大值获取IP
     *
     * @return Node
     */
    public Node selectNode() {
        if (nodes == null || nodes.size() <= 0) return null;
        if (nodes.size() == 1) return nodes.get(0);
        Node maxNode = null;
        StringBuffer sb = new StringBuffer();
        sb.append("加权轮询--[当前权重]值的变化：").append(printCurrentWeight(nodes));
        int maxCurrent = Integer.MIN_VALUE;
        // 选出当前权重最大的节点
        for (Node node : nodes) {
            // 当前权重=当前权重+节点权重
            node.setCurrentWeight(node.getCurrentWeight() + node.getWeight());
            if (maxNode == null) {
                maxNode = node;
            }
            if (node.getCurrentWeight() > maxCurrent) {
                maxNode = node;
                maxCurrent = node.getCurrentWeight();
            }
        }
        assert maxNode != null;
        maxNode.setCurrentWeight(maxNode.getCurrentWeight() - totalWeight);
        sb.append(" -> ").append(printCurrentWeight(nodes));
        System.out.println(sb); //打印权重变化过程
        return maxNode;
    }

    public static void main(String[] args) {
        WeightedRoundRobin weightedRoundRobin1 = new WeightedRoundRobin();
        for (int i = 1; i <= 6; i++) {
            Node node = weightedRoundRobin1.selectNode();
            System.out.println("第" + i + "次轮询选中[当前权重最大]的节点：" + node + "\n");
        }
    }

    // 格式化打印信息
    private String printCurrentWeight(List<Node> nodes) {
        StringBuffer stringBuffer = new StringBuffer("[");
        nodes.forEach(node -> stringBuffer.append(node.getCurrentWeight() + ","));
        return stringBuffer.substring(0, stringBuffer.length() - 1) + "]";
    }

    // 并发测试：两个线程循环获取节点

}

