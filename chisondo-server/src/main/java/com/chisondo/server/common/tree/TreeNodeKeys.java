package com.chisondo.server.common.tree;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class TreeNodeKeys implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String parentId;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
    }
}
