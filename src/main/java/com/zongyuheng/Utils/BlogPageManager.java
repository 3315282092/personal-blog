package com.zongyuheng.Utils;

import com.zongyuheng.content.BlogContent;
import com.zongyuheng.content.TagAndTypeContent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BlogPageManager<E> {
    private int size= BlogContent.PAGE_SIZE;//容量
    private int totalPages;  //总页数
    private int totalElements;//总元素
    private int num;//第几页  前端传过来的
    private boolean first; //是否是第一页
    private String[] sort={BlogContent.SORT_METHOD, BlogContent.SORT_TARGET};//按照什么元素根据什么顺序进行排序
    private int numberOfElements;//当前页数有多少个元素
    private List<E> results;  //页面的所有元素
    private boolean last;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<E> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "PageManager{" +
                "size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", num=" + num +
                ", first=" + first +
                ", sort=" + Arrays.toString(sort) +
                ", numberOfElements=" + numberOfElements +
                ", results=" + results +
                ", last=" + last +
                '}';
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    public BlogPageManager() {
    }

    public BlogPageManager(int size, int totalPages, int totalElements, int num, boolean first, String[] sort, int numberOfElements, List<E> results, boolean last) {
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.num = num;
        this.first = first;
        this.sort = sort;
        this.numberOfElements = numberOfElements;
        this.results = results;
        this.last=last;
    }



    public int getSize() {
        return size;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElementsAndTotalPages(int totalElements) {
        this.totalElements = totalElements;
        if (totalElements%size!=0){
            this.totalPages=totalElements/size+1;
        }else {
            this.totalPages=totalElements/size;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String[] getSort() {
        return sort;
    }



    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
