package com.example.demo;

/**
 * @description: TreeBuilder
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-02-03 21:56
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的生成
 *
 * @author DangerShi
 */
public class TreeBuilder {

    static class TreeNode {
        Object data;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(Object data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "data=" + data +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static TreeNode buildTreeNode(Integer[] arrs) {
        if (arrs == null || arrs.length == 0) {
            return new TreeNode();
        }

        List<TreeNode> nodes = new ArrayList<>(arrs.length);
        for (Object obj : arrs) {
            TreeNode treeNode = new TreeNode();
            treeNode.data = obj;
            nodes.add(treeNode);
        }

        for (int i = 0; i < arrs.length / 2 - 1; i++) {
            TreeNode node = nodes.get(i);
            node.left = nodes.get(i * 2 + 1);
            node.right = nodes.get(i * 2 + 2);
        }
        // 只有当总节点数是奇数时，最后一个父节点才有右子节点
        int lastPNodeIndex = arrs.length / 2 - 1;
        TreeNode lastPNode = nodes.get(lastPNodeIndex);
        lastPNode.left = nodes.get(lastPNodeIndex * 2 + 1);
        if (arrs.length % 2 != 0) {
            lastPNode.right = nodes.get(lastPNodeIndex * 2 + 2);
        }

        return nodes.get(0);
        // 建立一个websocket长链接，用户访问，使用redis缓存记录用户，用来加锁，
        // 当缓存存在，抛出错误，缓存不存在，正常进行操作，
        // 用来解决浏览器非正常关闭，当客户端主动或被动断开链接时，服务端会接收断开通知，然后手动去释放锁，

    }

    public static void main(String[] args) {

        /**
         *          1
         *      2       3
         *    4   5   6   7
         */
        TreeNode treeNode = buildTreeNode(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println(treeNode);
    }

}