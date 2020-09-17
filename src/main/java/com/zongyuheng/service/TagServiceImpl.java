package com.zongyuheng.service;

import com.zongyuheng.Utils.TypeAndTagPageManager;
import com.zongyuheng.mapper.TagMapper;
import com.zongyuheng.pojo.TTags;
import com.zongyuheng.pojo.TType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    TypeAndTagPageManager<TTags> pageManager;
    final
    TagMapper tagMapper;
@Autowired
    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
        tagMapper.getAllTags();
    }


    @Override
    public List<TTags> getAllTags() {
        List<TTags> allTags = tagMapper.getAllTags();
        return allTags;
    }

    @Override
    @Transactional
    public Boolean saveTag(TTags tags) {
        return tagMapper.saveTag(tags) == 1;
    }

    @Override
    @Transactional
    public TTags getTag(int id) {
        TTags tagsById = tagMapper.getTagById(id);
        return tagsById;
    }

    @Override
    @Transactional
    public TypeAndTagPageManager getPageTagList(int page) {
        List<TTags> allTags = tagMapper.getAllTags();
        pageManager.setTotalElementsAndTotalPages(allTags.size());
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
        List<TTags> tagsByPageDesc = tagMapper.findTagsByPageDesc(pageManager.getSize(), pageManager.getNum(), pageManager.getSort()[0], pageManager.getSort()[1]);
        pageManager.setResults(tagsByPageDesc);
        pageManager.setNumberOfElements(tagsByPageDesc.size());
        return pageManager;
    }

    @Override
    @Transactional
    public TTags getTagByName(String name) {
        TTags tTags = tagMapper.getTagByName(name);
        return tTags;
    }

    @Override
    @Transactional
    public Boolean updateTag(int id, String newName) {
        return tagMapper.updateTag(id, newName) == 1;
    }

    @Override
    public Boolean deleteTag(int id) throws Exception {
        return tagMapper.deleteTag(id) == 1;
    }

    @Override
    public List<TTags> ListTag(String ids) {
        //ids 1，2，3这种格式的字符串      将字符串拆分成集合
        List<Long> list=new ArrayList<>();
        if (!"".equals(ids) && ids!=null){
            String [] idarray=ids.split(",");
            for (int i=0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        List<TTags> tagsByIds = tagMapper.getTagsByIds(list);
        return tagsByIds;
    }

    @Override
    public List<TTags> listTagTop(Integer size) {
        List<TTags> allTags = tagMapper.getAllTags();
        Set<Integer> set=new TreeSet<>();
        List<TTags> collect;
        for (int i = 0; i < allTags.size(); i++) {
            set.add(allTags.get(i).getBlogs().size());
        }
        ArrayList<Integer> ints=new ArrayList(set);
        Collections.reverse(ints);
        List<TTags> sectionList = new ArrayList<>();
        if (allTags.size()>=size) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                int finalI = i;
               collect = allTags.stream().filter(a -> a.getBlogs().size() == ints.get(finalI)).collect(Collectors.toList());
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
                collect = allTags.stream().filter(a -> a.getBlogs().size() == ints.get(finalI)).collect(Collectors.toList());
                sectionList.addAll(collect);
            }
        }

//这样可以得到一个以blog  size倒叙构成的，且由自己定义size的type集合
        return sectionList;
    }
}
