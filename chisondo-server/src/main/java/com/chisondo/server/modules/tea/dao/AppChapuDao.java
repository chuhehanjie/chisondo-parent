package com.chisondo.server.modules.tea.dao;

import com.chisondo.server.modules.sys.dao.BaseDao;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumDetailDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface AppChapuDao extends BaseDao<AppChapuEntity> {

    AppChapuEntity queryTeaSpectrumById(Integer id);

    QryTeaSpectrumDetailDTO queryTeaSpectrumDetailById(Integer chapuId);

    List<QryTeaSpectrumDetailDTO> queryTeaSpectrumListByCondition(Map<String, Object> params);
}
