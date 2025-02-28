package cathy.chen.Mapper;

import cathy.chen.Pojo.MySQLTable.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into users values (null, #{username}, #{password}, #{email})")
    void insertUser(User user);

    @Select("select * from users where email = #{email}")
    User getUserByEmail(String email);

    @Select("select username from users where id = #{userId}")
    <T> T getUserName(Integer userId);
}
