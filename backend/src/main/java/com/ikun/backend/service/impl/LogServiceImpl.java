package com.ikun.backend.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.backend.entity.Log;
import com.ikun.backend.entity.User;
import com.ikun.backend.mapper.LogMapper;
import com.ikun.backend.service.LogService;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;
import com.ikun.backend.utils.pageUtil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    /**
     * @description: 增加日志
     * @date: 2023/5/19 16:24
     */
    @Override
    public int addLog(Log log) {
        log.setLogTime(new DateTime().toString());
        return logMapper.insertLog(log);
    }

    /**
     * @description: 日志分页查询，按不同类型分类
     * @date: 2023/5/19 16:24
     */
    @Override
    public PageResult queryLogByPageByCondition(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }
    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<Log> getPageInfo(PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Log> logs = logMapper.selectLogByPageByCondition(pageRequest);
        return new PageInfo<Log>(logs);
    }

}
