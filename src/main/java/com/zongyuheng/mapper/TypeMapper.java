package com.zongyuheng.mapper;

import com.zongyuheng.pojo.TType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@Mapper
@Repository
public interface TypeMapper {

    List<TType> getAllTypes();

    List<TType> findTypesByPageDesc(@Param("pageSize") int pageSize, @Param("currentPage") int currentPage, @Param("method") String method, @Param("column") String column);

    int saveType(TType tType);

    TType getTypeByName(@Param("name") String name);

    TType getTypeById(@Param("id") int id);

    int updateType(@Param("id") int id, @Param("newName") String newName);

    int deleteType(@Param("id") int id) throws Exception;

}
