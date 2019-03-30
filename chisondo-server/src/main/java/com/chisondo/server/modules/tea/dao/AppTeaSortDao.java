package com.chisondo.server.modules.tea.dao;

import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.sys.dao.BaseDao;
import com.chisondo.server.modules.tea.dto.TeaSortRowDTO;
import com.chisondo.server.modules.tea.entity.AppTeaSortEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface AppTeaSortDao extends BaseDao<AppTeaSortEntity> {

    List<TeaSortRowDTO> queryAllTeaSorts();
}
