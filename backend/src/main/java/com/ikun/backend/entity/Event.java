package com.ikun.backend.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.data.annotation.Transient;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(120)
@HeadStyle(verticalAlignment = VerticalAlignmentEnum.CENTER)//表头样式
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER)//内容样式
public class Event {
    @ExcelProperty(value = {"事件ID"}, index = 0)
    int eventId;
    @ExcelIgnore
    String eventImgUrl;
    @ExcelIgnore
    String eventVideoUrl;
    @Transient
    @ExcelProperty(value = {"图片"}, index = 10)
    @ColumnWidth(35)
    URL eventImgExcelUrl;
    @ExcelProperty(value = {"摄像头ID"}, index = 1)
    int cameraId;
    @ExcelProperty(value = {"类型"}, index = 2)
    String eventType;
    @ExcelProperty(value = {"区域"}, index = 3)
    String eventArea;
    @ExcelProperty(value = {"发生时间"}, index = 4)
    String eventOccurrenceTime;
    @JsonProperty("eventStatus")
    @ExcelProperty(value = {"处理状态"}, index = 5, converter = com.ikun.backend.convert.StatusConvert.class)
    Boolean eventStatus;
    @ExcelProperty(value = {"处理人"}, index = 6)
    String eventHandlerName;
    @ExcelProperty(value = {"处理人工号"}, index = 7)
    String eventHandlerJobId;
    @ExcelProperty(value = {"处理时间"}, index = 8)
    String eventHandlingTime;
    @ExcelProperty(value = {"处理评论"}, index = 9)
    String eventHandlingComment;
}
