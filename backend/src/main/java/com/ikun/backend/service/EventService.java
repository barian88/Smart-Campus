package com.ikun.backend.service;

import com.ikun.backend.entity.Event;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @date 2023/3/12
 **/
public interface EventService {

    /**
     * 根据摄像头id查询事件信息
     *
     * @param cameraId 摄像头id
     * @return 事件信息
     */
    List<Event> queryEventByCameraId(int cameraId);

    /**
     * 根据告警事件类型查询事件信息
     * @param eventType
     * @return
     */
    List<Event> queryEventByType(String eventType);

    /**
     * 插入事件信息
     * @return
     */
    Boolean addEvent(String cameraName,String cameraArea,String  eventImgUrl,String  eventVideoUrl,int cameraId,String modelType,String eventOccurrenceTime);

    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult queryEventByPageByCondition(PageRequest pageRequest);

    /**
     * @description: 更新事件，补充如参数字段
     * @param eventId,eventHandlerName,eventHandlerJobId,eventHandlingTime,eventHandlingComment
     * @return 更新记录数
     * @author: GUJianyang
     * @date: 2023/3/21 17:04
     */
    int handleEvent(int eventId, String eventHandlerName, String eventHandlerJobId, String eventHandlingTime, String eventHandlingComment);

    /**
    * @Description: 根据事件id查询事件
    * @Date:  2023/4/3 19:28
    **/
    Event queryEventById(int eventId);

    /**
    * @Description: 查询每个摄像头最新的事件（id最大则时间最新）
    * @Date:  2023/4/12 23:19
    **/
    List<Event> queryLatestEventByCameraId();

    /**
     * @Description: 根据事件类型查询事件数量
     * @Date:  2023/4/12 23:16
     **/
    List<Map<String,Integer>> queryCountByType();

    /**
     * @Description: 导出报表
     * @Date:  2023/4/12 23:16
     **/
    void export(HttpServletResponse response, List<Integer> eventIds);

    /**
     * @Description: 根据id批量查询事件
     * @Date:  2023/4/12 23:16
     **/
    List<Event> queryEventsByIds(List<Integer> eventIds);
}
