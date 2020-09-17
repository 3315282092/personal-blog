package com.zongyuheng.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Mapper
@Repository
public interface RelationMapper {
    int saveRelation(@Param("bid") Long bid,@Param("tids") List<Long> tids);
    int deleteRelation(@Param("bid") Long bid);
}
