package com.example.campus.demo;

import com.sun.source.tree.Tree;

public class TreeDemo {


    public static void main(String[] args) {
        TreeBuilder treeBuilder = createTree();
        treeBuilder.pre_order(treeBuilder);
    }

    public static TreeBuilder createTree(){
        TreeBuilder tree = new TreeBuilder();
        TreeBuilder tree1 = new TreeBuilder();
        TreeBuilder tree2 = new TreeBuilder();
        TreeBuilder tree3 = new TreeBuilder();
        TreeBuilder tree4 = new TreeBuilder();
        TreeBuilder tree5 = new TreeBuilder();
        TreeBuilder tree6 = new TreeBuilder();
        tree.setVal("1,23,32,tzsd");
        tree1.setVal("2,24,33,sdf");
        tree2.setVal("3,25,34,qwe");
        tree3.setVal("4,26,35,qwsade");
        tree4.setVal("5,27,36,qwxce");
        tree5.setVal("6,28,37,qwgre");
        tree6.setVal("7,29,38,qvxbmwe");
        tree.setLeft(tree1);
        tree.setRight(tree2);
        tree1.setLeft(tree3);
        tree1.setRight(tree4);
        tree2.setLeft(tree5);
        tree2.setRight(tree6);
        return tree;
    }
}
