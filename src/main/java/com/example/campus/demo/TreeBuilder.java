package com.example.campus.demo;


public class TreeBuilder {
    TreeBuilder root;
    String val;
    TreeBuilder left;
    TreeBuilder right;

    public TreeBuilder(String val) {
        this.val = val;
    }


    public TreeBuilder getRoot() {
        return root;
    }

    public void setRoot(TreeBuilder root) {
        this.root = root;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public TreeBuilder getLeft() {
        return left;
    }

    public void setLeft(TreeBuilder left) {
        this.left = left;
    }

    public TreeBuilder getRight() {
        return right;
    }

    public void setRight(TreeBuilder right) {
        this.right = right;
    }

    public TreeBuilder(TreeBuilder left, TreeBuilder right, String val){
        this.left=left;
        this.right=right;
        this.val=val;
    }

    public TreeBuilder(){}


    void pre_order(TreeBuilder root)//前序遍历递归算法
    {
        if (root == null)
            return;
        System.out.println(root.val);
        pre_order(root.left);
        pre_order(root.right);
    }


}
