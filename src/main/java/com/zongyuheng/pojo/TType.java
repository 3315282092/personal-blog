package com.zongyuheng.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TType {
  @Override
  public String toString() {
    return "TType{" +
            "tid=" + tid +
            ", tname='" + tname + '\'' +
            ", tBlogs=" + tBlogs +
            '}';
  }
  private long tid;

  public List<TBlog> gettBlogs() {
    return tBlogs;
  }

  public void settBlogs(List<TBlog> tBlogs) {
    this.tBlogs = tBlogs;
  }

  private String tname;
  private List<TBlog> tBlogs;

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
