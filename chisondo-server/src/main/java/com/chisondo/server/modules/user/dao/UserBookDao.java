package com.chisondo.server.modules.user.dao;

import com.chisondo.server.modules.user.dto.UserMakeTeaReservationDTO;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@Mapper
public interface UserBookDao extends BaseDao<UserBookEntity> {

    int countUserReservation(Map<String, Object> params);

    List<UserMakeTeaReservationDTO> queryUserReservation(Map<String, Object> params);
}
