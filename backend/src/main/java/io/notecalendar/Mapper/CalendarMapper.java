package io.notecalendar.Mapper;

import io.notecalendar.Pojo.MySQLTable.Event;
import io.notecalendar.Pojo.MySQLTable.Goal;
import io.notecalendar.Pojo.MySQLTable.GoalEventTodoDTO;
import io.notecalendar.Pojo.MySQLTable.Todo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CalendarMapper {

    @Insert("insert into event(user_id, event_name, date_start, date_end, done, goal_id) values (#{userId}, #{eventName}, #{dateStart}, #{dateEnd}, #{done}, #{goalId})")
    int addEvent(Event event);

    @Insert("insert into todo values (null, #{userId}, #{todoName}, #{date}, #{done}, #{goalId})")
    int addTodo(Todo todo);

    @Insert("INSERT into goal(user_id, goal_name, done) values (#{userId}, #{goalName}, #{done})")
    int addGoal(Goal goal);

    @Select("select * from event where user_id = #{userId}")
    ArrayList<Event> findEventsByUserId(int userId);

    @Select("SELECT * FROM event WHERE date_start >= #{startDate} AND date_start < DATE_ADD(#{endDate}, INTERVAL 1 DAY) AND user_id = #{userId}")
    ArrayList<Event> findEventsByDateRange(int userId, String startDate, String endDate);

    @Select("select * from todo where date between #{startDate} and #{endDate} and user_id = #{userId}")
    ArrayList<Todo> findTodosByDateRange(int userId, String startDate, String endDate);

    @Select("select * from todo where id = #{id}")
    Todo getTodoById(int id);

    @Update("update todo set done = #{done} where id = #{id}")
    int updateTodoState(int id, int done);

    @Select("select * from goal where user_id = #{userId}")
    ArrayList<Goal> getAllGoals(int userId);

    @Select("select * from todo where user_id = #{userId} and done = 0 order by date ASC")
    ArrayList<Todo> getAllUndo(Integer userId);

    @Delete("delete from todo WHERE id = #{id}")
    int deleteTodo(int id);

    @Update("update todo set todo_name = #{todoName} ,date = #{date}, goal_id = #{goalId} where id = #{id}")
    int updateTodoInfo(Todo todo);

    List<GoalEventTodoDTO> getUncompletedGoalsWithTodosAndEvents(int userId);

    @Select("select * from goal where id = #{goalId}")
    Goal getGoalById(int goalId);

    @Update("UPDATE todo SET goal_id = NULL WHERE goal_id = #{goalId} AND user_id = #{userId}")
    void unlinkRelatedTodos(Integer goalId, Integer userId);

    @Update("UPDATE event SET goal_id = NULL WHERE goal_id = #{goalId} AND user_id = #{userId}")
    void unlinkRelatedEvents(Integer goalId, Integer userId);

    @Delete("DELETE FROM goal WHERE id = #{goalId} AND user_id = #{userId}")
    void deleteGoalById(Integer goalId, Integer userId);

    @Update("update goal set goal_name = #{goal.goalName}, done = #{goal.done} where id = #{goal.id} and user_id = #{userId}")
    int updateGoalTitleOrDone(Goal goal, Integer userId);

    @Update("update event set event_name = #{event.eventName}, date_start = #{event.dateStart}, date_end = #{event.dateEnd}, done = #{event.done}, goal_id = #{event.goalId} where user_id = #{userId} and id = #{event.id}")
    int updateEvent(Event event, int userId);

    @Delete("delete from event where id = #{eventId} AND user_id = #{userId}")
    int deleteEvent(Integer eventId, Integer userId);
}
