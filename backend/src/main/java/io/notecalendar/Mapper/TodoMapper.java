package io.notecalendar.Mapper;

import io.notecalendar.Pojo.MySQLTable.Todo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface TodoMapper {

    @Insert("insert into todo values (null, #{userId}, #{todoName}, #{date}, #{done}, #{goalId})")
    int addTodo(Todo todo);

    @Select("select * from todo where date between #{startDate} and #{endDate} and user_id = #{userId}")
    ArrayList<Todo> findTodosByDateRange(int userId, String startDate, String endDate);

    @Select("select * from todo where id = #{id}")
    Todo getTodoById(Integer id);

    @Select("select * from todo where user_id = #{userId} and done = 0 order by date ASC")
    ArrayList<Todo> getAllUndo(Integer userId);

    @Update("update todo set done = #{done} where id = #{id}")
    int updateTodoState(Integer id, int done);

    @Update("update todo set todo_name = #{todoName} ,date = #{date}, goal_id = #{goalId} where id = #{id}")
    int updateTodoInfo(Todo todo);

    @Update("UPDATE todo SET goal_id = NULL WHERE goal_id = #{goalId} AND user_id = #{userId}")
    void unlinkRelatedTodos(Integer goalId, Integer userId);

    @Delete("delete from todo WHERE id = #{id}")
    int deleteTodo(Integer id);


}
