package com.chisondo.server.modules.tea.dao;

import com.chisondo.server.modules.sys.dao.BaseDao;
import com.chisondo.server.modules.tea.entity.AppChapuMineEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface AppChapuMineDao extends BaseDao<AppChapuMineEntity> {

    void deleteByCondition(Map<String, Object> params);

    void updateMyTeaSpectrum(AppChapuMineEntity myTeaSpectrum);
}
