package com.ikun.backend.service;

import com.ikun.backend.entity.Log;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;

public interface LogService {

    /**
    * @description: 增加日志
    * @date: 2023/5/19 16:24
    */
    int addLog(Log log);

    /**
     * @description: 日志分页查询，按不同类型分类
     * @date: 2023/5/19 16:24
     */
    PageResult queryLogByPageByCondition(PageRequest pageRequest);

}
