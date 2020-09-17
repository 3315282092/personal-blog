package com.zongyuheng.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;
import java.util.ArrayList;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TTags {

  private long tid;
  private String tname;
  private List<TBlog> blogs;

  public List<TBlog> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<TBlog> blogs) {
    this.blogs = blogs;
  }

  @Override
  public String toString() {
    return "TTags{" +
            "tid=" + tid +
            ", tname='" + tname + '\'' +
            ", blogs=" + blogs +
            '}';
  }

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }


  public String getTname() {
    return tname;
  }

  public void setTname(String tname) {
    this.tname = tname;
  }

}
