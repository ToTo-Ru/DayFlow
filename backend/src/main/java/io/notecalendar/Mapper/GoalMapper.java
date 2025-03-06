package io.notecalendar.Mapper;

import io.notecalendar.Pojo.MySQLTable.Goal;
import io.notecalendar.Pojo.MySQLTable.GoalEventTodoDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GoalMapper {
    @Insert("INSERT into goal(user_id, goal_name, done) values (#{userId}, #{goalName}, #{done})")
    int addGoal(Goal goal);

    @Select("select * from goal where user_id = #{userId}")
    ArrayList<Goal> getAllGoals(int userId);

    @Delete("DELETE FROM goal WHERE id = #{goalId} AND user_id = #{userId}")
    void deleteGoalById(Integer goalId, Integer userId);

    @Update("update goal set goal_name = #{goal.goalName}, done = #{goal.done} where id = #{goal.id} and user_id = #{userId}")
    int updateGoalTitleOrDone(Goal goal, Integer userId);

    List<GoalEventTodoDTO> getUncompletedGoalsWithTodosAndEvents(int userId);

}
