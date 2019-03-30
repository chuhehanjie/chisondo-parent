package com.chisondo.server.datasources;

import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.user.entity.UserSchemeEntity;
import com.chisondo.server.modules.user.service.UserSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@Service
public class DataSourceTestService {
    @Autowired
    private UserSchemeService userService;

    public UserSchemeEntity queryObject(Integer id){
        return userService.queryObject(id);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public UserSchemeEntity queryObject2(Integer id){
        return userService.queryObject(id);
    }
}
