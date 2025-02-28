package io.notecalendar.service;

import io.notecalendar.Pojo.development.Result;
import io.notecalendar.Pojo.MySQLTable.User;

// TODO complete userService
public interface UserService {

    <T> Result<T> insertUser(User user);

    <T> Result<T> login(User user);

    <T> Result<T> getUserName();
}
