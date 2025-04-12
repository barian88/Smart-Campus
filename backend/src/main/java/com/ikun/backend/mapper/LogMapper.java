package com.ikun.backend.mapper;

import com.ikun.backend.entity.Log;
import com.ikun.backend.utils.pageUtil.PageRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {
    /**
    * @description: 插入日志
    * @param: log
    * @return:
    * @date: 2023/5/19 15:48
    */
    int insertLog(Log log);

    /**
    * @description: 分页查询日志，按类型分类
    * @date: 2023/5/19 16:26
    */
    List<Log> selectLogByPageByCondition(PageRequest pageRequest);
}
