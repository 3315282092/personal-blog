package com.zongyuheng.service;

import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.mapper.TypeMapper;
import com.zongyuheng.pojo.TType;
import org.apache.log4j.TTCCLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeAndTagPageManager<TType> pageManager;
    @Autowired
    TypeMapper typeMapper;

    @Override
    public List<TType> listTypeTop(Integer size) {
        List<TType> allTypes = typeMapper.getAllTypes();
       Set <Integer> set=new TreeSet<>();
        for (int i = 0; i < allTypes.size(); i++) {
            set.add(allTypes.get(i).gettBlogs().size());
        }
        ArrayList<Integer> ints=new ArrayList(set);
        Collections.reverse(ints);
        List<TType> sectionList = new ArrayList<>();
        if (allTypes.size()>=size) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                int finalI = i;
                List<TType> collect = allTypes.stream().filter(a -> a.gettBlogs().size() == ints.get(finalI)).collect(Collectors.toList());
                count += collect.size();//count=2
                if (count >size) {
                    sectionList.addAll(collect);
                    for (int j = 0; j < count - size; j++) {
                        sectionList.remove(sectionList.size() - 1);
                    }
                    break;
                } else if (count==size){
                    sectionList.addAll(collect);
                    break;
                }
                else {
                    sectionList.addAll(collect);
                }
            }
        }else {
            for (int i = 0; i < ints.size(); i++) {
                int finalI = i;
                List<TType> collect = allTypes.stream().filter(a -> a.gettBlogs().size() == ints.get(finalI)).collect(Collectors.toList());
                    sectionList.addAll(collect);
                }
            }

//这样可以得到一个以blog  size倒叙构成的，且由自己定义size的type集合
    return sectionList;
    }

    @Override
    public List<TType> getAllTypes() {
        List<TType> allTypes = typeMapper.getAllTypes();
        return allTypes;
    }

    @Override
    @Transactional
    public Boolean saveType(TType tType) {
        return typeMapper.saveType(tType) == 1;
    }

    @Override
    @Transactional
    public TType getType(int id) {
        TType typeById = typeMapper.getTypeById(id);
        return typeById;
    }

    @Override
    @Transactional
    public TypeAndTagPageManager getPageTypeList(int page) {
        List<TType> allTypes = typeMapper.getAllTypes();
        pageManager.setTotalElementsAndTotalPages(allTypes.size());
        if (page == 1) {
            pageManager.setFirst(true);
        } else {
            pageManager.setFirst(false);
        }

        int totalPages = pageManager.getTotalPages();
        if (page > totalPages) {
            pageManager.setNum(page - 1);
        } else if (page == totalPages) {
            pageManager.setNum(page);
            pageManager.setLast(true);
        } else {
            pageManager.setNum(page);
            pageManager.setLast(false);
        }
        List<TType> typesByPageDesc = typeMapper.findTypesByPageDesc(pageManager.getSize(), pageManager.getNum(), pageManager.getSort()[0], pageManager.getSort()[1]);
        pageManager.setResults(typesByPageDesc);
        pageManager.setNumberOfElements(typesByPageDesc.size());
        return pageManager;
    }

    @Override
    @Transactional
    public TType getTypeByName(String name) {
        TType typeByName = typeMapper.getTypeByName(name);
        return typeByName;
    }

    @Override
    @Transactional
    public Boolean updateType(int id, String newName) {
        return typeMapper.updateType(id, newName) == 1;
    }

    @Override
    @Transactional
    public Boolean deleteType(int id) throws Exception {
        return typeMapper.deleteType(id) == 1;
    }
}
