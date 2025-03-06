package io.notecalendar.Controller;

import io.notecalendar.Pojo.MySQLTable.*;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.Service.EventService;
import io.notecalendar.Service.GoalService;
import io.notecalendar.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    EventService eventService;

    @Autowired
    TodoService todoService;

    @Autowired
    GoalService goalService;

//    TODO last reconstruct
    @PostMapping
    public <T> Result<T> addCalendar(@RequestBody EventControllerDTO eventControllerDTO) {
        return switch (eventControllerDTO.getType()) {
            case 1 -> eventService.addEvent(eventControllerDTO);
            case 2 -> todoService.addTodo(eventControllerDTO);
            case 3 -> goalService.addGoal(eventControllerDTO);
            default -> Result.error("Pojo type does not exist");
        };
    }
}
