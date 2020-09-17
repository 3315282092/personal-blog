package com.zongyuheng.service;

import com.zongyuheng.Utils.BlogPageManager;
import com.zongyuheng.Utils.IndexPageManager;
import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.pojo.TTags;

import java.util.*;

public interface BlogService {
    IndexPageManager<TBlog> listQueryBlogs (int page,String query);

    TBlog getAndConvert(Long id);

    TBlog getBlog(int id);

    BlogPageManager<TBlog> listBlog(int page, TBlog blog);

    Boolean saveBlog(TBlog blog);

    Boolean updateBlog(int id, TBlog blog);

    Boolean deleteBlog(Long id) throws RuntimeException;

    IndexPageManager<TBlog> pageListBlog(int page);

    List<TBlog> listRecommendBlogTop(Integer size);

    BlogPageManager<TBlog> listBlog(Long tagId,int page);

    Map<String,List<TBlog>> archiveBlog();  //第一个参数是年份，第二个参数是这个年份对应的BLOG集合

    Long countBlogs();

}
