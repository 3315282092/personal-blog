package com.zongyuheng.mapper;

import com.zongyuheng.pojo.TBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface BlogMapper {

    List<TBlog> listRecommendBlogTop(@Param("size") Integer size);

    List<TBlog> getBlogs();

    List<TBlog> getSomeBlogs(@Param("blog") TBlog blog);

    TBlog getBlog(@Param("bid") int bid);

    List<TBlog> listBlog(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column, @Param("blog") TBlog blog);

    int saveBlogInsert(TBlog blog);

    int saveBlogReplace(TBlog blog);

    int updateBlog(int id, TBlog blog);

    int deleteBlog(@Param("bid") int bid) throws RuntimeException;

    List<TBlog> pageListBlog(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column);

    List<TBlog> listQueryBlog(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column,@Param("query") String query);

    int updateTimes(@Param("bid") Long bid);

    List<TBlog> listByTagId(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column,@Param("tagId") Long tagId);

    List<TBlog> listByTagIdAll(@Param("tagId") int tagId);

    List<String> findGroupYear();

    List<TBlog> findByYear(String year);

    Long countBlogs();

}
