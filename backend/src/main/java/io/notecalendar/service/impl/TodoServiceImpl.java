package io.notecalendar.Service.impl;

import io.notecalendar.Mapper.TodoMapper;
import io.notecalendar.Pojo.MySQLTable.EventControllerDTO;
import io.notecalendar.Pojo.MySQLTable.Todo;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.Aspect.CheckUserOwnership;
import io.notecalendar.Service.TodoService;
import io.notecalendar.Utils.CovertPojoUtil;
import io.notecalendar.Utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl  implements TodoService {

    @Autowired
    TodoMapper todoMapper;

    @Override
    public <T> Result<T> addTodo(EventControllerDTO eventControllerDTO) {
        Todo toDo = CovertPojoUtil.convertToToDo(eventControllerDTO);

        if (todoMapper.addTodo(toDo) == 1) {
            return Result.success();
        }else {
            return Result.error("Todo adding Failed");
        }
    }

    @Override
    public Result<List<Todo>> getTodoInAWeek(String startDate, String endDate) {
        ArrayList<Todo> Todos = todoMapper.findTodosByDateRange(ThreadLocalUtil.getUserId(), startDate, endDate);
        return Result.success(Todos);
    }

    @Override
    public Result<List<Todo>> getUndo() {
        ArrayList<Todo> todo = todoMapper.getAllUndo(ThreadLocalUtil.getUserId());
        return Result.success(todo);
    }

    @Override
    @CheckUserOwnership
    public <T> Result<T> updateTodoState(Integer id, int done) {
        if (todoMapper.updateTodoState(id, done) == 1){
            return Result.success();
        }else {
            return Result.error("update failed");
        }
    }

    @Override
    @CheckUserOwnership
    public <T> Result<T>  updateTodoInfo(Todo todo) {
        if (todoMapper.updateTodoInfo(todo) == 1){
            return Result.success();
        }else {
            return Result.error("update todo failed");
        }
    }


    @Override
    @CheckUserOwnership
    public <T> Result<T> deleteTodo(Integer id) {
        if (todoMapper.deleteTodo(id) == 1){
            return Result.success();
        }else {
            return Result.error("delete todo failed");
        }
    }

}
