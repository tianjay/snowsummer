package com.wetalk.summersnow.summersnow.mapper;

import com.wetalk.summersnow.summersnow.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag,comment_count,view_count,like_count) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag},#{commentCount},#{viewCount},#{likeCount})")
    void create(Question question);

    //开启驼峰映射
    @Select("select * from question")
    @Results(
            value = {
                    @Result(id = true, column = "id", property = "id"),
                    @Result(column = "title", property = "title"),
                    @Result(column = "description", property = "description"),
                    @Result(column = "tag", property = "tag"),
                    @Result(column = "gmt_create", property = "gmtCreate"),
                    @Result(column = "gmt_modified", property = "gmtModified"),
                    @Result(column = "creator", property = "creator"),
                    @Result(column = "comment_count", property = "commentCount"),
                    @Result(column = "view_count", property = "viewCount"),
                    @Result(column = "like_count", property = "likeCount")
            }
    )
    List<Question> list1();

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUser(Integer userId);

    @Select("select * from question where creator = #{userId} limit #{offSet},#{size}")
    List<Question> listByUser(Integer userId, Integer offSet, Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);

    @Update("update question set title =#{title},description =#{description},gmt_modified=#{gmtModified},tag=#{tag} where id = #{id}")
    void upadate(Question question);
}
