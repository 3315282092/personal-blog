package com.zongyuheng.mapper;

import com.zongyuheng.pojo.TComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Mapper
@Repository
public interface CommentMapper {
    List<TComment> findByBlogIdAndParentCommentNot(@Param("blogId") int blogId,@Param("column") String column,@Param("method") String method);

    TComment findByCommentId(@Param("cid") int cid);

    int saveComment(@Param("comment") TComment comment);

    List<TComment> findByBlogId(@Param("blogId") int blogId);

    int deleteByBlogId(@Param("blogId") int blogId);

    int saveByCommentList(@Param("commentList") List<TComment> commentList);

    int updateTarget(@Param("targetid") Long targetId,@Param("oldTargetId") Long oldTargetId);
}
