package com.cw.web.backend.controller.notice;

import com.cw.biz.notice.app.dto.NoticeDto;
import com.cw.biz.notice.app.service.NoticeAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 公告
 * Created by dujy on 2017-07-13.
 */
@RestController
public class NoticeController extends AbstractBackendController {

    @Autowired
    private NoticeAppService noticeAppService;

    /**
     * 修改公告信息
     * @param noticeDto
     * @return
     */
    @PostMapping("/notice/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody NoticeDto noticeDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = noticeAppService.update(noticeDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setMessage("成功");
        cpViewResultInfo.setSuccess(true);

        return cpViewResultInfo;
    }

    /**
     * 公告详情
     * @param id
     * @return
     */
    @GetMapping("/notice/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        NoticeDto noticeDto = noticeAppService.findById(id);
        cpViewResultInfo.setData(noticeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询公告
     * @param noticeDto
     * @return
     */
    @PostMapping("/notice/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody NoticeDto noticeDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        noticeDto.setPageNo(noticeDto.getPageNumber());
        Page<NoticeDto> creditCardDtos = noticeAppService.findByCondition(noticeDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用公告
     * @param noticeDto
     * @return
     */
    @PostMapping("/notice/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody NoticeDto noticeDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        noticeAppService.enable(noticeDto);
        cpViewResultInfo.setMessage("成功");
        cpViewResultInfo.setSuccess(true);
        return cpViewResultInfo;
    }

}
