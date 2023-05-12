package com.example.campus.demo;


import java.util.Arrays;
import java.util.LinkedList;

public class ArraySwitchTree {
    String SEP = ",";
    String NULL = "#";

    /* 主函数，将二叉树序列化为字符串 */
    String serialize(TreeBuilder root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    /* 辅助函数，将二叉树存入 StringBuilder */
    void serialize(TreeBuilder root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        /****** 前序遍历位置 ******/
        sb.append(root.val).append(SEP);
        /***********************/

        serialize(root.left, sb);
        serialize(root.right, sb);
    }
    public static TreeBuilder createTree(){
        TreeBuilder tree = new TreeBuilder();
        TreeBuilder tree1 = new TreeBuilder();
        TreeBuilder tree2 = new TreeBuilder();
        TreeBuilder tree3 = new TreeBuilder();
        TreeBuilder tree4 = new TreeBuilder();
        TreeBuilder tree5 = new TreeBuilder();
        TreeBuilder tree6 = new TreeBuilder();
        tree.setVal("1-23-32-tzsd");
        tree1.setVal("2-24-33-sdf");
        tree2.setVal("3-25-34-qwe");
        tree3.setVal("4-26-35-qwsade");
        tree4.setVal("5-27-36-qwxce");
        tree5.setVal("6-28-37-qwgre");
        tree6.setVal("7-29-38-qvxbmwe");
        tree.setLeft(tree1);
        tree.setRight(tree2);
        tree1.setLeft(tree3);
        tree1.setRight(tree4);
        tree2.setLeft(tree5);
        tree2.setRight(tree6);
        return tree;
    }


    /* 主函数，将字符串反序列化为二叉树结构 */
    TreeBuilder deserialize(String data) {
        // 将字符串转化成列表
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return deserialize(nodes);
    }

    /* 辅助函数，通过 nodes 列表构造二叉树 */
    TreeBuilder deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) return null;

        /****** 前序遍历位置 ******/
        // 列表最左侧就是根节点
        String first = nodes.removeFirst();
        if (first.equals(NULL)) return null;
//         first = first.split("-")[0];
         TreeBuilder root = new TreeBuilder(first);
        /***********************/

        root.left = deserialize(nodes);
        root.right = deserialize(nodes);

        return root;
    }

    public static void main(String[] args) {
        TreeBuilder treeBuilder = createTree();
        ArraySwitchTree arraySwitchTree =new ArraySwitchTree();
        treeBuilder.pre_order(treeBuilder);

        String serialize = arraySwitchTree.serialize(treeBuilder);
//        System.out.println(serialize);
        TreeBuilder res = arraySwitchTree.deserialize(serialize);
        treeBuilder.pre_order(res);
    }

}
