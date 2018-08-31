package com.cw.web.backend.controller.user;/*
 * 描       述:  &lt;描述&gt;
 * 修  改   人:  ${user}
 * 修  改 时 间:  ${date}
 * &lt;修改描述:&gt;
 */

import com.cw.biz.user.app.dto.YxUserInfoDto;
import com.cw.biz.user.app.service.CustomerAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController  extends AbstractBackendController {

    @Autowired
    private CustomerAppService yxUserInfoAppService;

    /**
     * 客户查询
     * @param yxUserInfoDto
     * @return
     */
    @PostMapping("/customer/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo userList(@RequestBody YxUserInfoDto yxUserInfoDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<YxUserInfoDto> userInfoDtos = yxUserInfoAppService.findByCondition(yxUserInfoDto);
        cpViewResultInfo.setData(userInfoDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

}
