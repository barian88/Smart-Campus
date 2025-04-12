package com.ikun.backend.controller;

import com.ikun.backend.entity.Log;
import com.ikun.backend.service.LogService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.pageUtil.children.LogPageRequest;
import com.ikun.backend.utils.pageUtil.children.UserPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    @PostMapping(value = "/addLog")
    AjaxResult addLog(@RequestBody Log log){
        if(log==null){
            return AjaxResult.error("日志为空");
        }
        return AjaxResult.success(logService.addLog(log));
    }

    @PostMapping(value = "/queryLogByPageByCondition")
    AjaxResult queryLogByPageByCondition(@RequestBody LogPageRequest pageQuery){
        return AjaxResult.success(logService.queryLogByPageByCondition(pageQuery));
    }
}
