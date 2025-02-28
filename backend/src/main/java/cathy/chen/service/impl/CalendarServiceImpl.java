package cathy.chen.service.impl;

import cathy.chen.Mapper.CalendarMapper;
import cathy.chen.Pojo.MySQLTable.*;
import cathy.chen.Pojo.development.Result;
import cathy.chen.service.CalendarService;
import cathy.chen.utils.CovertPojoUtil;
import cathy.chen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    CalendarMapper calendarMapper;

//    ** Methods will be used in Controller
    @Override
    public <T> Result<T> addEvent(EventControllerDTO eventControllerDTO) {
        Event event = CovertPojoUtil.convertToEvent(eventControllerDTO);

        if (calendarMapper.addEvent(event) == 1) {
            return Result.success();
        }else {
            return Result.error("Event adding Failed");
        }
    }

    @Override
    public <T> Result<T> addTodo(EventControllerDTO eventControllerDTO) {
        Todo toDo = CovertPojoUtil.convertToToDo(eventControllerDTO);

        if (calendarMapper.addTodo(toDo) == 1) {
            return Result.success();
        }else {
            return Result.error("Todo adding Failed");
        }
    }

    @Override
    public <T> Result<T> addGoal(EventControllerDTO eventControllerDTO) {
        Goal goal = CovertPojoUtil.convertToGoal(eventControllerDTO);

        if (calendarMapper.addGoal(goal) == 1) {
            return Result.success();
        }else {
            return Result.error("Goal adding Failed");
        }
    }

    @Override
    public <T> Result<T> updateTodoState(Integer id, int done) {
        Todo todo = calendarMapper.getTodoById(id);
        if (Objects.equals(todo.getUserId(), ThreadLocalUtil.getUserId())){
            if (calendarMapper.updateTodoState(id, done) == 1){
                return Result.success();
            }else {
                return Result.error("update failed");
            }
        }else {
            return Result.error("user unmatched");
        }
    }

    @Override
    public Result<List<Todo>> getTodoInAWeek(String startDate, String endDate) {
        ArrayList<Todo> Todos = calendarMapper.findTodosByDateRange(ThreadLocalUtil.getUserId(), startDate, endDate);
        return Result.success(Todos);
    }

    @Override
    public Result<List<Event>> getEventsInAWeek(String startDate, String endDate) {
        ArrayList<Event> events = calendarMapper.findEventsByDateRange(ThreadLocalUtil.getUserId(), startDate, endDate);
        return Result.success(events);
    }

    @Override
    public Result<List<Goal>> getAllGoal() {
        ArrayList<Goal> goals = calendarMapper.getAllGoals(ThreadLocalUtil.getUserId());
        return Result.success(goals);
    }

    @Override
    public Result<List<Todo>> getUndo() {
        ArrayList<Todo> todo = calendarMapper.getAllUndo(ThreadLocalUtil.getUserId());
        return Result.success(todo);
    }

    @Override
    public <T> Result<T> deleteTodo(Integer id) {
        Todo todo = calendarMapper.getTodoById(id);
        if (!Objects.equals(todo.getUserId(), ThreadLocalUtil.getUserId())){
            return Result.error("user unmatched");
        }
        if (calendarMapper.deleteTodo(id) == 1){
            return Result.success();
        }else {
            return Result.error("delete todo failed");
        }
    }

    @Override
    public <T> Result<T>  updateTodoInfo(Todo todo) {
        Todo storedTodo = calendarMapper.getTodoById(todo.getId());
        if (!Objects.equals(storedTodo.getUserId(), ThreadLocalUtil.getUserId())){
            return Result.error("user unmatched");
        }
        if (calendarMapper.updateTodoInfo(todo) == 1){
            return Result.success();
        }else {
            return Result.error("update todo failed");
        }
    }

    @Override
    public Result<List<GoalEventTodoDTO>> getUncompletedGoalsWithTodosAndEvents() {
        List<GoalEventTodoDTO> result = calendarMapper.getUncompletedGoalsWithTodosAndEvents(ThreadLocalUtil.getUserId());
        return Result.success(result);

    }

    @Override
    @Transactional
    public <T> Result<T> deleteGoal(Integer goalId) {
        calendarMapper.unlinkRelatedTodos(goalId, ThreadLocalUtil.getUserId());
        calendarMapper.unlinkRelatedEvents(goalId, ThreadLocalUtil.getUserId());
        calendarMapper.deleteGoalById(goalId, ThreadLocalUtil.getUserId());
        return Result.success();
    }

    @Override
    public <T> Result<T> updateGoalTitleOrDone(Goal goal) {
        int result = calendarMapper.updateGoalTitleOrDone(goal, ThreadLocalUtil.getUserId());

        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }

    @Override
    public <T> Result<T> updateEvent(EventControllerDTO eventDTO) {
        Event event = CovertPojoUtil.convertToEvent(eventDTO);

        int result = calendarMapper.updateEvent(event, ThreadLocalUtil.getUserId());
        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }

    @Override
    public <T> Result<T> deleteEvent(Integer eventId) {
        int result = calendarMapper.deleteEvent(eventId, ThreadLocalUtil.getUserId());
        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }


}
