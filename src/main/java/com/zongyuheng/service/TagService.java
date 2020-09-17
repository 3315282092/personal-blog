package com.zongyuheng.service;
import java.util.*;
import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.pojo.TTags;

public interface TagService {
    List<TTags> getAllTags();

    Boolean saveTag(TTags tags);

    TTags getTag(int id);

    TypeAndTagPageManager<TTags> getPageTagList(int page);

    TTags getTagByName(String name);

    Boolean updateTag(int id, String newName);

    Boolean deleteTag(int id) throws Exception;

    List<TTags> ListTag(String ids);

    List<TTags> listTagTop(Integer size);
}
