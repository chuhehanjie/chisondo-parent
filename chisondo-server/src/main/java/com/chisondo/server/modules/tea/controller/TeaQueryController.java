package com.chisondo.server.modules.tea.controller;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.tea.dto.TeaSortQryDTO;
import com.chisondo.server.modules.tea.dto.TeaSortRowDTO;
import com.chisondo.server.modules.tea.service.AppTeaSortService;
import com.chisondo.server.modules.tea.service.TeamanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 茶类信息查询 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@RestController
public class TeaQueryController {
	@Autowired
	private TeamanUserService teamanUserService;

	@Autowired
	private AppTeaSortService appTeaSortService;
	/**
	 * 查询所有茶类信息
	 */
	@PostMapping("/api/rest/chapu/getsorts")
	@DataSource(name = DataSourceNames.SECOND)
	public CommonResp queryAllTeaSort(@RequestBody CommonReq req){
		List<TeaSortRowDTO> teaSorts = this.appTeaSortService.queryAllTeaSorts();
		TeaSortQryDTO teaSortQryDTO = new TeaSortQryDTO();
		if (!CollectionUtils.isEmpty(teaSorts)) {
			teaSortQryDTO.setDefaultSortId(teaSorts.get(0).getSortId()); // TODO 设置默认茶类为第1个
			teaSortQryDTO.setRows(teaSorts);
		}
		return CommonResp.ok(teaSortQryDTO);
	}

}
