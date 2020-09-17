package com.zongyuheng.service;

import com.zongyuheng.pojo.TComment;


import java.util.*;

public interface CommentService {

    List<TComment> listCommentByBlogId(Long blogId);

    TComment saveComment(TComment comment);
}
