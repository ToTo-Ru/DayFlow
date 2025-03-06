package io.notecalendar.service.impl;

import io.notecalendar.Mapper.EventMapper;
import io.notecalendar.Mapper.GoalMapper;
import io.notecalendar.Mapper.TodoMapper;
import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.MySQLTable.Goal;
import io.notecalendar.Pojo.MySQLTable.GoalEventTodoDTO;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.service.GoalService;
import io.notecalendar.utils.CovertPojoUtil;
import io.notecalendar.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    @Autowired
    GoalMapper goalMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    TodoMapper todoMapper;

    @Override
    public <T> Result<T> addGoal(EventControllerDTO eventControllerDTO) {
        Goal goal = CovertPojoUtil.convertToGoal(eventControllerDTO);

        if (goalMapper.addGoal(goal) == 1) {
            return Result.success();
        }else {
            return Result.error("Goal adding Failed");
        }
    }

    @Override
    public Result<List<Goal>> getAllGoal() {
        ArrayList<Goal> goals = goalMapper.getAllGoals(ThreadLocalUtil.getUserId());
        return Result.success(goals);
    }

    @Override
    public <T> Result<T> updateGoalTitleOrDone(Goal goal) {
        int result = goalMapper.updateGoalTitleOrDone(goal, ThreadLocalUtil.getUserId());

        if(result != 1){
            return Result.error("update failed");
        }
        return Result.success();
    }

    @Override
    @Transactional
    public <T> Result<T> deleteGoal(Integer goalId) {
        todoMapper.unlinkRelatedTodos(goalId, ThreadLocalUtil.getUserId());
        eventMapper.unlinkRelatedEvents(goalId, ThreadLocalUtil.getUserId());
        goalMapper.deleteGoalById(goalId, ThreadLocalUtil.getUserId());
        return Result.success();
    }

    @Override
    public Result<List<GoalEventTodoDTO>> getUncompletedGoalsWithTodosAndEvents() {
        List<GoalEventTodoDTO> result = goalMapper.getUncompletedGoalsWithTodosAndEvents(ThreadLocalUtil.getUserId());
        return Result.success(result);
    }

}
