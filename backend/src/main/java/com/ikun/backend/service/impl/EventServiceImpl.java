package com.ikun.backend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ikun.backend.entity.Camera;
import com.ikun.backend.entity.Event;
import com.ikun.backend.mapper.EventMapper;
import com.ikun.backend.service.CameraService;
import com.ikun.backend.service.EventService;
import com.ikun.backend.service.ModelService;
import com.ikun.backend.service.SendMessageService;
import com.ikun.backend.utils.pageUtil.PageRequest;
import com.ikun.backend.utils.pageUtil.PageResult;
import com.ikun.backend.utils.pageUtil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Pan ZhiFu
 * @date 2023/3/12
 **/
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private CameraService cameraService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private SendMessageService sendMessageService;

    /**
     * 根据摄像头id查询事件信息
     *
     * @param cameraId 摄像头id
     * @return 事件信息
     */
    @Override
    public List<Event> queryEventByCameraId(int cameraId) {

            return eventMapper.selectEventByCameraId(cameraId);
        }


    @Override
    public List<Event> queryEventByType(String eventType) {

       return eventMapper.selectEventByType(eventType);
    }

    /**
     * 插入事件信息
     *
     * @return 结果
     */
    @Override
    public Boolean addEvent(String cameraName,String cameraArea,String
            eventImgUrl,String  eventVideoUrl,int cameraId,String modelType,String eventOccurrenceTime) {
        Event event = new Event();
        event.setCameraId(cameraId);
        event.setEventImgUrl(eventImgUrl);
        event.setEventVideoUrl(eventVideoUrl);
        event.setEventOccurrenceTime(eventOccurrenceTime);
        event.setEventStatus(false);
//      查询model表，获得eventType,对应modelName
        String modelName = modelService.queryModelByType(modelType).getModelName();
        event.setEventType(modelName);
        event.setEventArea(cameraArea);
//       事件添加到数据库
        int dbRes = eventMapper.insertEvent(event);
//       发送微信订阅消息
        Map<String,Integer> res = sendMessageService.sendWeixinMessage(event,cameraArea,cameraName);
        boolean wxRes = (res.get("success") == res.get("total"));
        return dbRes != 0 && wxRes;

//        return dbRes != 0;

    }

    @Override
    public PageResult queryEventByPageByCondition(PageRequest pageRequest) {
        return PageUtil.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<Event> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Event> events = eventMapper.selectEventByPageByCondition(pageRequest);
        return new PageInfo<Event>(events);
    }

    /**
     * @description: 更新事件，补充如参数字段
     * @param eventId,eventHandlerName,eventHandlingTime,eventHandlingComment
     * @return 更新记录数
     * @author: GUJianyang
     * @date: 2023/3/21 17:04
     */
    @Override
    public int handleEvent(int eventId, String eventHandlerName,String eventHandlerJobId, String eventHandlingTime, String eventHandlingComment) {
        return eventMapper.updateEvent(eventId,eventHandlerName,eventHandlerJobId, eventHandlingTime,eventHandlingComment);
    }

    /**
     * @description: 根据事件id查询事件
     * @param eventId
     * @return 事件对象
     */
    @Override
    public Event queryEventById(int eventId) {
        return eventMapper.selectEventById(eventId);
    }

    /**
     * @Description: 查询每个摄像头最新的事件（id最大则时间最新）
     * @Date:  2023/4/12 23:19
     **/
    @Override
    public List<Event> queryLatestEventByCameraId() {
        return eventMapper.selectLatestEventByCameraId();
    }

    /**
     * @Description: 根据事件类型查询事件数量
     * @Date:  2023/4/12 23:16
     **/
    @Override
    public List<Map<String, Integer>> queryCountByType() {
        return eventMapper.selectCountByType();
    }

    /**
     * @Description: 导出报表
     * @Date:  2023/4/12 23:16
     **/
    @Override
    public void export(HttpServletResponse response, List<Integer> eventIds) {
        List<Event> content = this.queryEventsByIds(eventIds);
        for (Event e : content){
            try {
                e.setEventImgExcelUrl(new URL(e.getEventImgUrl()));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String fileName = format + "事件.xlsx";

        try {
            response.setContentType("application/octet-stream");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(response.getOutputStream(), Event.class).
                    registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("事件").doWrite(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 根据id批量查询事件
     * @Date:  2023/4/12 23:16
     **/
    @Override
    public List<Event> queryEventsByIds(List<Integer> eventIds) {
        return eventMapper.selectEventsByIds(eventIds);
    }
}
