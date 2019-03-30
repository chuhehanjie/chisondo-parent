package com.chisondo.server.common.tree;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.utils.ValidateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeNodeConvertor {
    private static volatile TreeNodeConvertor instance;

    public static TreeNodeConvertor getInstance() {
        if (ValidateUtils.isEmpty(instance)) {
            synchronized (instance) {
                if (ValidateUtils.isEmpty(instance)) {
                    instance = new TreeNodeConvertor();
                }
            }
        }
        return instance;
    }

    private TreeNodeConvertor () {
    }
    public List<TreeNode> convert2TreeNodes(List<Object> data, TreeNodeKeys keys) {
       /* JSONArray jsonArray = JSONObject.parseArray(JSONObject.toJSONString(data));
        Map<Object, TreeNode> parentNodes = new HashMap<>();
        jsonArray.forEach(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            if (ValidateUtils.isNotEmpty(jsonObject.get(keys.getParentId())) &&
                    !parentNodes.containsKey(jsonObject.get(keys.getParentId()))) {
                parentNodes.put(jsonObject.get(keys.getParentId()), new TreeNode());
            }
        });*/
        return null;
    }
}
