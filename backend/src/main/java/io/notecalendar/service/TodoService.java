package io.notecalendar.Service;

import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.MySQLTable.Todo;
import io.notecalendar.Pojo.development.Result;

import java.util.List;

public interface TodoService {
    public <T> Result<T> addTodo(EventControllerDTO eventControllerDTO);
    public Result<List<Todo>> getUndo();
    public Result<List<Todo>> getTodoInAWeek(String startDate, String endDate);
    public <T> Result<T> updateTodoState(Integer id, int done);
    public <T> Result<T>  updateTodoInfo(Todo todo);
    public <T> Result<T> deleteTodo(Integer id);
}
