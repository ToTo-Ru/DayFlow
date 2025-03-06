package io.notecalendar.service;

import io.notecalendar.Pojo.MySQLTable.Event;
import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.development.Result;

import java.util.List;

public interface EventService {
    public <T> Result<T> addEvent(EventControllerDTO eventControllerDTO);
    public Result<List<Event>> getEventsInAWeek(String startDate, String endDate);
    public <T> Result<T> updateEvent(EventControllerDTO eventDTO);
    public <T> Result<T> deleteEvent(Integer eventId);
}
