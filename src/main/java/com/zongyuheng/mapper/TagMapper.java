package com.zongyuheng.mapper;

import com.zongyuheng.pojo.TTags;
import com.zongyuheng.pojo.TType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    List<TTags> getAllTags();

    List<TTags> findTagsByPageDesc(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column);

    int saveTag(TTags tags);

    TTags getTagByName(@Param("name") String name);

    TTags getTagById(@Param("id") int id);

    int updateTag(@Param("id") int id, @Param("newName") String newName);

    int deleteTag(@Param("id") int id) throws Exception;

    List<TTags> getTagsByIds(@Param("ids") List<Long> ids);

   List<TTags> getSomeTags();
}
