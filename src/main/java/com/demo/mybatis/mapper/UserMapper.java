package com.demo.mybatis.mapper;

import com.demo.mybatis.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER ;")
    @Results(id = "user", value = {
            @Result(property = "userId", column = "USER_ID"),
            @Result(property = "userName", column = "USER_NAME"),
            @Result(property = "age", column = "AGE"),
            @Result(property = "gender", column = "GENDER")
    })
    List<User> getAll();

    @ResultMap("user")
    @Select("SELECT * FROM USER WHERE USER_ID = #{id}")
    User getByPrimaryKey(@Param("id") String id);

    @Insert("INSERT INTO USER (USER_ID, USER_NAME, AGE, GENDER) " +
            "VALUES (#{userId}, #{userName}, #{age}, #{gender})")
    int insert(User user);

    @Update("UPDATE USER SET USER_NAME= #{userName}, AGE=#{age}, GENDER = #{gender} WHERE USER_ID = #{userId}")
    int update(User user);

    @Delete("DELETE FROM USER WHERE USER_ID= #{userId}")
    int deleteUserById(String id);

    @Delete("DELETE FROM USER")
    int deleteAll();
}
