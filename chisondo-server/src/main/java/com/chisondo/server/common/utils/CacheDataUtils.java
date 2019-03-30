package com.chisondo.server.common.utils;

import com.chisondo.server.modules.sys.entity.CompanyEntity;
import com.chisondo.server.modules.sys.entity.SysConfigEntity;
import com.chisondo.server.modules.sys.service.CompanyService;
import com.chisondo.server.modules.sys.service.SysConfigService;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component("cacheDataUtils")
public class CacheDataUtils {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SysConfigService sysConfigService;

    private static List<CompanyEntity> companyList;

    private static List<SysConfigEntity> configList;

    private static List<Integer> waterLevels;

    @PostConstruct
    public void init() {
        companyList = this.companyService.queryList(Collections.EMPTY_MAP);
        log.info("init query companyList size = {} ", companyList.size());
        configList = this.sysConfigService.queryAll();
        log.info("init query configList size = {} ", configList.size());
        waterLevels = ImmutableList.of(150, 200, 250, 300, 350, 400, 450, 550);
    }

    public static List<CompanyEntity> getCompanyList() {
       return companyList;
    }

    public static List<SysConfigEntity> getConfigList() {
        return configList;
    }

    public static String getConfigValueByKey(String key) {
        SysConfigEntity config = getConfigByKey(key);
        return ValidateUtils.isNotEmpty(config) ? config.getValue() : "";
    }

    public static SysConfigEntity getConfigByKey(String key) {
        if (ValidateUtils.isNotEmptyCollection(configList)) {
            SysConfigEntity config = configList.stream().filter(item -> ValidateUtils.equals(key, item.getKey())).findFirst().orElse(null);
            return config;
        }
        return null;
    }

    public static List<Integer> getWaterLevels() {
        return waterLevels;
    }
}
