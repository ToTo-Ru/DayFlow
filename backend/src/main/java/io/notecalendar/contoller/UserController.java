package io.notecalendar.contoller;

import io.notecalendar.Pojo.development.Result;
import io.notecalendar.Pojo.MySQLTable.User;
import io.notecalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//ConstraintViolationException was handle in GlobalExceptionHandler

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public <T> Result<T> register(@Validated(User.addUser.class) @RequestBody User user) {
        return userService.insertUser(user);
    }

//    Don't use get due to security reason
    @PostMapping("/login")
    public <T> Result<T> login(@Validated(User.loginUser.class) @RequestBody User user) {
            return userService.login(user);
    }
    @GetMapping("/name")
    public <T> Result<T> getUserName() {
        return userService.getUserName();
    }
}
