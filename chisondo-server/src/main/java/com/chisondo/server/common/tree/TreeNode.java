package com.chisondo.server.common.tree;

import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeNode implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nodeId;
    private String nodeName;
    private String parentNodeId;
    private String extValue;
    private List<TreeNode> children = new ArrayList<>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getExtValue() {
        return extValue;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    private TreeNode() {
    }

    public static TreeNode createRoot(String rootId, String rootName) {
        TreeNode root = new TreeNode();
        root.setNodeId(rootId);
        root.setNodeName(rootName);
        root.setParentNodeId(Constant.ROOT_NODE_ID);
        return root;
    }

    public static TreeNode createNode(String nodeId, String nodeName, String parentNodeId, String extValue) {
        TreeNode node = new TreeNode();
        node.setNodeId(nodeId);
        node.setNodeName(nodeName);
        node.setParentNodeId(parentNodeId);
        node.setExtValue(extValue);
        return node;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addTreeNode(TreeNode treeNode) {
        this.children.add(treeNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(nodeId, treeNode.nodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId);
    }
}
