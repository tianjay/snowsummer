package com.wetalk.summersnow.summersnow.mapper;

import com.wetalk.summersnow.summersnow.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    //    @Select("SELECT * FROM CITY WHERE state = #{state}")
//    City findByState(@Param("state") String state);
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id =#{creator}")
    User findById(Integer creator);

    @Select("select * from user where account_id =#{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl}where account_id = #{accountId}")
    void update(User user);
}
