package com.chisondo.server.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author ding.zhong
 * @since 17:58
 */
public class Node implements Serializable{
    private Integer nodeId;
    private String nodeName;
    private List<Node> childNodes;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<Node> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Node> childNodes) {
        this.childNodes = childNodes;
    }
}
