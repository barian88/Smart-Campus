package com.ikun.backend.controller;


import com.ikun.backend.entity.Event;
import com.ikun.backend.service.EventService;
import com.ikun.backend.utils.AjaxResult;
import com.ikun.backend.utils.pageUtil.children.EventPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/event")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/getEventByCameraId")
    public AjaxResult getEventByCameraId(@RequestParam(value = "cameraId") int cameraId){
        List<Event> events = eventService.queryEventByCameraId(cameraId);
        if(events!=null){
            return AjaxResult.success(events);
        }
        else{
            return AjaxResult.error();
        }

    }
    @GetMapping(value = "/getEventByType")
    public AjaxResult getEventByType(@RequestParam(value = "eventType") String eventType){
        List<Event> events = eventService.queryEventByType(eventType);
        int len;
        len=events.size();
        if(len!=0){
            return AjaxResult.success(events);
        }else{
            return AjaxResult.error();
        }
    }

    @PostMapping(value = "/addEvent")
    public AjaxResult addEvent(
                               @RequestParam(value="cameraName") String  cameraName,
                               @RequestParam(value="cameraArea") String  cameraArea,
                               @RequestParam(value="eventImgUrl") String  eventImgUrl,
                               @RequestParam(value="eventVideoUrl") String  eventVideoUrl,
                               @RequestParam(value="cameraId") int cameraId,
                               @RequestParam(value="modelType") String modelType,
                               @RequestParam(value="eventOccurrenceTime") String eventOccurrenceTime){

        if(eventImgUrl.equals("") || eventVideoUrl.equals(""))
            return AjaxResult.error();
        Boolean result = eventService.addEvent(cameraName,cameraArea,eventImgUrl,eventVideoUrl,cameraId,modelType,eventOccurrenceTime);
        return AjaxResult.success(result);
    }

    @PostMapping(value="/getEventByPageByCondition")
    public AjaxResult getEventByPageByCondition(@RequestBody EventPageRequest pageQuery) {
        if (pageQuery == null)
            return AjaxResult.error();
        return AjaxResult.success(eventService.queryEventByPageByCondition(pageQuery));
    }

    @PostMapping("handleEvent")
    public AjaxResult handleEvent(int eventId, String eventHandlerName, String eventHandlerJobId, String eventHandlingTime, String eventHandlingComment){
        if (eventId == 0 || eventHandlerName.equals("") || eventHandlingTime.equals(""))
            return AjaxResult.error();
        return AjaxResult.success(eventService.handleEvent(eventId,eventHandlerName,eventHandlerJobId,eventHandlingTime,eventHandlingComment));
    }

    @GetMapping(value = "/getEventById")
    public AjaxResult getEventByEventId(@RequestParam(value = "eventId") int eventId){
        if(eventId != 0){
            return AjaxResult.success(eventService.queryEventById(eventId));
        }
        else{
            return AjaxResult.error();
        }

    }

    @GetMapping("getLatestEventByCamera")
    public AjaxResult getLatestEventByCamera(){
        return AjaxResult.success(eventService.queryLatestEventByCameraId());
    }

    @GetMapping("getCountByType")
    public AjaxResult getCountByType(){
        return AjaxResult.success(eventService.queryCountByType());
    }

    @GetMapping("exportEvent")
    public void exportEvent(HttpServletResponse response, @RequestParam("eventIds") List<Integer> eventIds) {
        eventService.export(response,eventIds);
    }
//    http://localhost:8003/event/exportEvent

}

