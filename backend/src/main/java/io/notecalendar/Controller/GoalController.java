package io.notecalendar.Controller;

import io.notecalendar.Pojo.MySQLTable.Goal;
import io.notecalendar.Pojo.MySQLTable.GoalEventTodoDTO;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.Service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    GoalService goalService;

    @GetMapping
    public Result<List<Goal>> getGoal() {
        return goalService.getAllGoal();
    }

    @GetMapping("/eventTodo")
    public Result<List<GoalEventTodoDTO>> getGoalLinkedList() {
        return goalService.getUncompletedGoalsWithTodosAndEvents();
    }

    @DeleteMapping("/{goalId}")
    public <T> Result<T>  deleteGoal(@PathVariable Integer goalId) {
        return goalService.deleteGoal(goalId);
    }

    @PatchMapping
    public <T> Result<T> updateGoalTitleOrDone(@RequestBody Goal goal) {
        return goalService.updateGoalTitleOrDone(goal);
    }
}
