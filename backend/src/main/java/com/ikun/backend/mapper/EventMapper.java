package com.ikun.backend.mapper;

import com.ikun.backend.entity.Event;
import com.ikun.backend.utils.pageUtil.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.Even;

import java.util.List;
import java.util.Map;

/**
 * 事件的增删改查
 *
 * @date 2023/3/12
 **/
@Mapper
public interface EventMapper {

    /**
     * 根据摄像头id查询事件信息
     *
     * @param cameraId 摄像头id
     * @return 事件信息
     */
    List<Event> selectEventByCameraId(@Param("cameraId") int cameraId);

    /**
     * 根据告警事件类型查询事件信息
     * @param eventType
     * @return
     */
    List<Event> selectEventByType(@Param("eventType") String eventType);
    /**
     * 插入事件
     * @param event 事件对象
     * @return 影响行数
     */
    int insertEvent(Event event);

    /**
    * @description: 事件列表分类查询 + 多条件查询
    * @param
    * @return 某页列表
    * @author: GUJianyang
    * @date: 2023/3/21 8:34
    */
    List<Event> selectEventByPageByCondition(PageRequest pageRequest);

    /**
    * @description: 更新事件，补充如参数字段
    * @param eventId,eventHandlerName,eventHandlerJobId,eventHandlingTime,eventHandlingComment
    * @return 更新记录数
    * @author: GUJianyang
    * @date: 2023/3/21 17:04
    */
    int updateEvent(int eventId, String eventHandlerName,String eventHandlerJobId, String eventHandlingTime, String eventHandlingComment);

    /**
    * @Description: 根据事件id查询事件
    * @Date:  2023/4/3 19:27
    **/
    Event selectEventById(int eventId);

    /**
    * @Description: 查询每个摄像头最新的事件（id最大则时间最新）
    * @Date:  2023/4/12 23:16
    **/
    List<Event> selectLatestEventByCameraId();

    /**
     * @Description: 根据事件类型查询事件数量
     * @Date:  2023/4/12 23:16
     **/
    List<Map<String,Integer>> selectCountByType();

    /**
     * @Description: 根据事件id批量查询事件数量
     * @Date:  2023/4/12 23:16
     **/
    List<Event> selectEventsByIds(List<Integer> eventIds);
}

