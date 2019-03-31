package com.chisondo.server.common.utils;

import com.chisondo.server.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

    public static final String PAGE = "page";

    public static final String SIDX = "sidx";

    public static final String ORDER = "order";

    public static final String OFFSET = "offset";

    public static final String LIMIT = "limit";
	//当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get(PAGE).toString());
        this.limit = Integer.parseInt(params.get(LIMIT).toString());
        this.put(OFFSET, (page - 1) * limit);
        this.put(PAGE, page);
        this.put(LIMIT, limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get(SIDX);
        String order = (String)params.get(ORDER);
        if(StringUtils.isNotBlank(sidx)){
            this.put(SIDX, SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put(ORDER, SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
