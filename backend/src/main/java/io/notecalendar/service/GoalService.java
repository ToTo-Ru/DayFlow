package io.notecalendar.service;


import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.MySQLTable.Goal;
import io.notecalendar.Pojo.MySQLTable.GoalEventTodoDTO;
import io.notecalendar.Pojo.development.Result;

import java.util.List;

public interface GoalService {
    public <T> Result<T> addGoal(EventControllerDTO eventControllerDTO);
    public Result<List<Goal>> getAllGoal();
    public <T> Result<T> updateGoalTitleOrDone(Goal goal);
    public <T> Result<T> deleteGoal(Integer goalId);
    public Result<List<GoalEventTodoDTO>> getUncompletedGoalsWithTodosAndEvents();
}
