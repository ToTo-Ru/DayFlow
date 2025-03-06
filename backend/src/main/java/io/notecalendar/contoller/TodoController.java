package io.notecalendar.contoller;

import io.notecalendar.Pojo.MySQLTable.Todo;
import io.notecalendar.Pojo.development.Result;
import io.notecalendar.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/this-week")
    public Result<List<Todo>> getTodoThisWeek(@RequestParam String startDate, @RequestParam String endDate){
        return todoService.getTodoInAWeek(startDate, endDate);
    }

    @GetMapping("/undo")
    public Result<List<Todo>> undo() {
        return todoService.getUndo();
    }

    @DeleteMapping("/{id}")
    public <T> Result<T> deleteTodo(@PathVariable Integer id) {
        return todoService.deleteTodo(id);
    }

    @PatchMapping("/{id}")
    public <T> Result<T> updateTodoState(@PathVariable Integer id, @RequestParam int done) {
        return todoService.updateTodoState(id, done);
    }

    @PutMapping
    public <T> Result<T>  updateTodoInfo(@RequestBody Todo todo) {
        return todoService.updateTodoInfo(todo);
    }
}
