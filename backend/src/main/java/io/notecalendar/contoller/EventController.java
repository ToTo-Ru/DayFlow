package io.notecalendar.contoller;

import io.notecalendar.Pojo.MySQLTable.Event;
import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/this-week")
    public Result<List<Event>> getEventsThisWeek(@RequestParam String startDate, @RequestParam String endDate) {
        return eventService.getEventsInAWeek(startDate, endDate);
    }
    @PutMapping
    public <T> Result<T> updateEvent(@RequestBody EventControllerDTO event) {
        return eventService.updateEvent(event);
    }
    @DeleteMapping("/{eventId}")
    public <T> Result<T> deleteEvent(@PathVariable Integer eventId) {
        return eventService.deleteEvent(eventId);
    }
}
