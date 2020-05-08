package com.wetalk.summersnow.summersnow.mapper;

import com.wetalk.summersnow.summersnow.model.Question;
import com.wetalk.summersnow.summersnow.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@SpringBootTest(classes = Application.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void test(){
        User user = new User();
        user.setName("tset");
        user.setToken("asdfsdf");
        user.setGmtCreate(Long.valueOf("132"));
        user.setGmtModified(user.getGmtCreate());
        user.setAccountId("1");
        userMapper.insert(user);
    }

    @Test
    public void testinsertDividedPageData(){
      //  INSERT `question`INTO question VALUE(4,"testdata","testdata",CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),4,1,1,1,"testtag");

        Question question = new Question();
        question.setTitle("testdata");
        question.setDescription("testdata");
        question.setTag("testtag");
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(9);
        question.setLikeCount(9);
        question.setViewCount(9);
        question.setCommentCount(9);
        for(Integer i=5;i<20;i++){
            question.setId(i);
            questionMapper.create(question);
        }
    }
}