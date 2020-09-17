package com.zongyuheng.service;

import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.pojo.TType;

import java.util.List;


public interface TypeService {
    List<TType> listTypeTop(Integer size);

    List<TType> getAllTypes();

    Boolean saveType(TType tType);

    TType getType(int id);

    TypeAndTagPageManager getPageTypeList(int page);

    TType getTypeByName(String name);

    Boolean updateType(int id, String newName);

    Boolean deleteType(int id) throws Exception;
}
