package com.chisondo.server.modules.device.dto.resp;

import java.io.Serializable;
import java.util.List;

public class MakeTeaRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO 这个 count 应该是总记录数？
    private int count;

    private List<MakeTeaRowRespDTO> rows;

    public MakeTeaRespDTO() {
    }


    public MakeTeaRespDTO(int count, List<MakeTeaRowRespDTO> rows) {
        this.rows = rows;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MakeTeaRowRespDTO> getRows() {
        return rows;
    }

    public void setRows(List<MakeTeaRowRespDTO> rows) {
        this.rows = rows;
    }
}
