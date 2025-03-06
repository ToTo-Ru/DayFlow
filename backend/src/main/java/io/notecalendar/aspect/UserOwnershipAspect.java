package io.notecalendar.aspect;

import io.notecalendar.Mapper.EventMapper;
import io.notecalendar.Mapper.TodoMapper;
import io.notecalendar.Pojo.MySQLTable.Todo;
import io.notecalendar.service.EventService;
import io.notecalendar.service.TodoService;
import io.notecalendar.utils.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

//handle checking user match or not, can use AOP or MYSQL checking
@Aspect
@Component
public class UserOwnershipAspect {
    private final TodoMapper todoMapper;

    @Autowired
    public UserOwnershipAspect(TodoMapper todoMapper, EventMapper eventMapper) {
        this.todoMapper = todoMapper;
    }

    @Before("@annotation(CheckUserOwnership) && args(id,..)")
    public void checkUserOwnership(JoinPoint joinPoint, Integer id) {
        Object target = joinPoint.getTarget(); // getting the target
        Integer currentUserId = ThreadLocalUtil.getUserId();

        if (target instanceof TodoService) {
            Todo todo = todoMapper.getTodoById(id);
            if (todo == null || !Objects.equals(todo.getUserId(), currentUserId)) {
                throw new IllegalStateException("user unmatched");
            }
        } else {
            throw new IllegalStateException("Unsupported service type");
        }
    }
}