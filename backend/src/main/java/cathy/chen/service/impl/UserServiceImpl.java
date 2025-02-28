package cathy.chen.service.impl;

import cathy.chen.Pojo.development.Result;
import cathy.chen.Pojo.MySQLTable.User;
import cathy.chen.Mapper.UserMapper;
import cathy.chen.service.UserService;
import cathy.chen.utils.JwtUtil;
import cathy.chen.utils.Md5Util;
import cathy.chen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public <T> Result<T> insertUser(User user) {
//        check email exist or not
        User userGet = userMapper.getUserByEmail(user.getEmail());
        if (userGet != null) {
            return Result.error("user already exist");
        }

//        convert password to MD5
        String newPassword = Md5Util.getMD5String(user.getPassword());
        user.setPassword(newPassword);
        userMapper.insertUser(user);
        return Result.success();
    }

    public <T> Result<T> login(User user){
        User userGet = userMapper.getUserByEmail(user.getEmail());
//        test exist
        if (userGet.getId() == null){
            return Result.error("user does not exist");
        }

//        test password
        if (!Objects.equals(userGet.getPassword(), Md5Util.getMD5String(user.getPassword()))){
            return Result.error("user password does not match");
        }

//        set token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userGet.getId());
        claims.put("email", userGet.getEmail());
        String token = JwtUtil.genToken(claims);
        return Result.success((T) token);

    }

    @Override
    public <T> Result<T> getUserName() {
        return Result.success(userMapper.getUserName(ThreadLocalUtil.getUserId()));
    }


}
