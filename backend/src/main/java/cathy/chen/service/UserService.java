package cathy.chen.service;

import cathy.chen.Pojo.development.Result;
import cathy.chen.Pojo.MySQLTable.User;

// TODO complete userService
public interface UserService {

    <T> Result<T> insertUser(User user);

    <T> Result<T> login(User user);

    <T> Result<T> getUserName();
}
