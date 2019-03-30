package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;
import java.util.List;

public class TeaSortQryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int defaultSortId; // 默认茶类ID 默认选中

    private List<TeaSortRowDTO> rows;

    public int getDefaultSortId() {
        return defaultSortId;
    }

    public void setDefaultSortId(int defaultSortId) {
        this.defaultSortId = defaultSortId;
    }

    public List<TeaSortRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<TeaSortRowDTO> rows) {
        this.rows = rows;
    }
}
