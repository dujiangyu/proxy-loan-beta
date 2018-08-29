package com.cw.web.backend.controller.role;

import com.alibaba.fastjson.JSONObject;
import com.cw.biz.CPContext;
import com.cw.biz.common.dto.ListParamDto;
import com.cw.biz.jdbc.JdbcPage;
import com.cw.biz.user.domain.entity.SeResource;
import com.cw.biz.user.domain.entity.SeRole;
import com.cw.biz.user.domain.service.SeResourceService;
import com.cw.biz.user.domain.service.SeRoleService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 */
@RestController
public class RoleController extends AbstractBackendController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SeRoleService seRoleService;

    @Autowired
    private SeResourceService seResourceService;
    /**
     * 角色查询
     * @param roleParamDto
     * @return
     */
    @PostMapping("/role/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody ListParamDto roleParamDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        roleParamDto.setSizePerPage(50);
        JdbcPage jdbcPage = seRoleService.findByPage(roleParamDto);
        cpViewResultInfo.setData(jdbcPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("角色查询成功");
        return cpViewResultInfo;
    }

    /**
        * 角色查询
        * @return
        */
       @GetMapping("/role/findAllRole.json")
       @ResponseBody
       public String findAllRole() {
           ListParamDto roleParamDto = new ListParamDto();
           roleParamDto.setMerchantId(CPContext.getContext().getSeUserInfo().getId());
           roleParamDto.setPageNo(1);
           roleParamDto.setSizePerPage(50);
           JdbcPage jdbcPage = seRoleService.findByPage(roleParamDto);
           return JSONObject.toJSONString(jdbcPage.getResult());
       }

    /**
     * 新增角色
     * @param seRole
     * @return
     */
    @PostMapping("/role/create.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody SeRole seRole) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeRole seRole1 = seRoleService.createRole(seRole);
        cpViewResultInfo.setData(seRole1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查询角色信息
     * @param id
     * @return
     */
    @GetMapping("/role/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SeRole seUser1 = seRoleService.findOne(id);
        cpViewResultInfo.setData(seUser1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 修改角色
     * @param seRole
     * @return
     */
    @PostMapping("/role/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody SeRole seRole) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        seRole.setMerchantId(1L);
        seRole.setType("manager");
        SeRole seUser1=null;
        String[] resources = seRole.getResources().split(",");
        List<Long> resourceIds =  new ArrayList<>();
        for(String res : resources){
            resourceIds.add(Long.parseLong(res));
        }
        seRole.setResourceIds(resourceIds);
        if(seRole.getId()==null) {
            seUser1 = seRoleService.createRole(seRole);
        }else{
            seUser1 = seRoleService.updateRole(seRole);
        }
        cpViewResultInfo.setData(seUser1.getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
        * 查看所有资源
        * @return
        */
       @PostMapping("/role/findAllResource.json")
       @ResponseBody
       public CPViewResultInfo findAllResource(Long id) {
           CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
           SeRole seRole = seRoleService.findOne(id);
           List<SeResource> seResourceList;
           if(!"admin".equals(CPContext.getContext().getSeUserInfo().getUsername())) {
               seResourceList = seResourceService.findByUserAndType(CPContext.getContext().getSeUserInfo().getId(), SeResource.ResourceType.menu);
           }else{
               seResourceList = seResourceService.findAll();
           }
           StringBuffer stringBuffer = new StringBuffer();
           int i=0;
           stringBuffer.append("[");
           for(SeResource seResource:seResourceList)
           {
               //判断是否有权限
               if(seRole!=null && seRole.getResourceIds()!=null) {
                   int idx=0;
                   for (Long resourceId : seRole.getResourceIds()) {
                       if (resourceId == seResource.getId()&&seResource.getParentId()!=0) {
                           stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\",checked:true,open:true}");
                           idx++;
                           break;
                       }
                   }
                   if(idx==0)
                   {
                       stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\", open:true}");
                   }
               }else{
                   stringBuffer.append("{id:" + seResource.getId() + ", pId:" + seResource.getParentId() + ", name:\"" + seResource.getName() + "\", open:true}");
               }
               if(i<seResourceList.size()-1)
               {
                   stringBuffer.append(",");
               }
               i++;
           }
           stringBuffer.append("]");
           cpViewResultInfo.setData(stringBuffer.toString());
           cpViewResultInfo.setSuccess(true);
           cpViewResultInfo.setMessage("查询所有资源");
           return cpViewResultInfo;
       }
}
