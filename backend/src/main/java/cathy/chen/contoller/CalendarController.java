package cathy.chen.contoller;

import cathy.chen.Pojo.MySQLTable.*;
import cathy.chen.Pojo.development.Result;
import cathy.chen.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @PostMapping
    public <T> Result<T> addCalendar(@RequestBody EventControllerDTO eventControllerDTO) {
        return switch (eventControllerDTO.getType()) {
            case 1 -> calendarService.addEvent(eventControllerDTO);
            case 2 -> calendarService.addTodo(eventControllerDTO);
            case 3 -> calendarService.addGoal(eventControllerDTO);
            default -> Result.error("Pojo type does not exist");
        };
    }

    @GetMapping("/eventsThisWeek")
    public Result<List<Event>> getEventsThisWeek(@RequestParam String startDate, @RequestParam String endDate) {
        return calendarService.getEventsInAWeek(startDate, endDate);
    }

    @GetMapping("/todoThisWeek")
    public Result<List<Todo>> getTodoThisWeek(@RequestParam String startDate, @RequestParam String endDate){
        return calendarService.getTodoInAWeek(startDate, endDate);
    }

    @PatchMapping("/todo/{id}")
    public <T> Result<T> updateTodoState(@PathVariable Integer id, @RequestParam int done) {
        return calendarService.updateTodoState(id, done);
    }

    @GetMapping("/goal")
    public Result<List<Goal>> getGoal() {
        return calendarService.getAllGoal();
    }

    @GetMapping("/undo")
    public Result<List<Todo>> undo() {
        return calendarService.getUndo();
    }

    @DeleteMapping("/todo/{id}")
    public <T> Result<T> deleteTodo(@PathVariable Integer id) {
        return calendarService.deleteTodo(id);
    }

    @PutMapping("/todo")
    public <T> Result<T>  updateTodoInfo(@RequestBody Todo todo) {
        return calendarService.updateTodoInfo(todo);
    }

    @GetMapping("/goal/eventTodo")
    public Result<List<GoalEventTodoDTO>> getGoalLinkedList() {
        return calendarService.getUncompletedGoalsWithTodosAndEvents();
    }
    @DeleteMapping("/goal/{goalId}")
    public <T> Result<T>  deleteGoal(@PathVariable Integer goalId) {
        return calendarService.deleteGoal(goalId);
    }
    @PatchMapping("/goal")
    public <T> Result<T> updateGoalTitleOrDone(@RequestBody Goal goal) {
        return calendarService.updateGoalTitleOrDone(goal);
    }
    @PutMapping("/event")
    public <T> Result<T> updateEvent(@RequestBody EventControllerDTO event) {
        return calendarService.updateEvent(event);
    }
    @DeleteMapping("/event/{eventId}")
    public <T> Result<T> deleteEvent(@PathVariable Integer eventId) {
        return calendarService.deleteEvent(eventId);
    }
}
