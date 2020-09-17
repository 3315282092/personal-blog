package com.zongyuheng;

import com.zongyuheng.Utils.BlogPageManager;
import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.mapper.*;
import com.zongyuheng.pojo.*;
import com.zongyuheng.service.BlogService;
import com.zongyuheng.service.CommentService;
import com.zongyuheng.service.TagService;
import com.zongyuheng.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BlogApplicationTests {
@Autowired
    CommentService commentService;
@Autowired
CommentMapper commentMapper;
@Autowired
BlogMapper blogMapper;

    @Test
    void contextLoads() {


TimeTest.getTime("测试", new TimeTest.Task() {
    @Override
    public void excute() {
        List<String> groupYear = blogMapper.findGroupYear();
        System.out.println(groupYear);
    }
});




    }

}
