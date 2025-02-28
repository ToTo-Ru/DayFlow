package io.notecalendar.service;

import io.notecalendar.Pojo.MySQLTable.*;
import io.notecalendar.Pojo.development.Result;

import java.util.List;

public interface CalendarService {
    <T> Result<T> addEvent(EventControllerDTO eventControllerDTO);

    <T> Result<T> addTodo(EventControllerDTO eventControllerDTO);

    <T> Result<T> addGoal(EventControllerDTO eventControllerDTO);

    <T> Result<T> getEventsInAWeek(String startDate, String endDate);

    <T> Result<T> getTodoInAWeek(String startDate, String endDate);

    <T> Result<T> updateTodoState(Integer id, int done);

    Result<List<Goal>> getAllGoal();

    Result<List<Todo>> getUndo();

    <T> Result<T> deleteTodo(Integer id);

    <T> Result<T>  updateTodoInfo(Todo todo);
    Result<List<GoalEventTodoDTO>> getUncompletedGoalsWithTodosAndEvents();

    <T> Result<T> deleteGoal(Integer goalId);

    <T> Result<T> updateGoalTitleOrDone(Goal goal);

    <T> Result<T> updateEvent(EventControllerDTO event);

    <T> Result<T> deleteEvent(Integer eventId);
}
