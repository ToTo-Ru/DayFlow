package io.notecalendar.service.impl;

import io.notecalendar.Mapper.EventMapper;
import io.notecalendar.Pojo.MySQLTable.Event;
import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.service.EventService;
import io.notecalendar.utils.CovertPojoUtil;
import io.notecalendar.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventMapper eventMapper;

    @Override
    public <T> Result<T> addEvent(EventControllerDTO eventControllerDTO) {
        Event event = CovertPojoUtil.convertToEvent(eventControllerDTO);

        if (eventMapper.addEvent(event) == 1) {
            return Result.success();
        }else {
            return Result.error("Event adding Failed");
        }
    }

    @Override
    public Result<List<Event>> getEventsInAWeek(String startDate, String endDate) {
        ArrayList<Event> events = eventMapper.findEventsByDateRange(ThreadLocalUtil.getUserId(), startDate, endDate);
        return Result.success(events);
    }

    @Override
    public <T> Result<T> updateEvent(EventControllerDTO eventDTO) {
        Event event = CovertPojoUtil.convertToEvent(eventDTO);

        int result = eventMapper.updateEvent(event, ThreadLocalUtil.getUserId());
        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }

    @Override
    public <T> Result<T> deleteEvent(Integer eventId) {
        int result = eventMapper.deleteEvent(eventId, ThreadLocalUtil.getUserId());
        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }
}
