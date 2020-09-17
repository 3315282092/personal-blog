package com.zongyuheng.pojo;


import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

public class TUser {
  @Override
  public String toString() {
    return "TUser{" +
            "uid=" + uid +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", type=" + type +
            ", usericon='" + usericon + '\'' +
            ", ucreatetime=" + ucreatetime +
            ", usupdatetime=" + usupdatetime +
            ", blogs=" + blogs +
            '}';
  }

  private long uid;
  private String username;
  private String password;
  private String email;
  private long type;
  private String usericon;
  private Date ucreatetime;
  private Date usupdatetime;
  private List<TBlog> blogs;

  public List<TBlog> getBlogs() {
    return blogs;
  }

  public void setBlogs(List<TBlog> blogs) {
    this.blogs = blogs;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getUsericon() {
    return usericon;
  }

  public void setUsericon(String usericon) {
    this.usericon = usericon;
  }


  public Date getUcreatetime() {
    return ucreatetime;
  }

  public void setUcreatetime(java.sql.Timestamp ucreatetime) {
    this.ucreatetime = ucreatetime;
  }


  public Date getUsupdatetime() {
    return usupdatetime;
  }

  public void setUsupdatetime(java.sql.Timestamp usupdatetime) {
    this.usupdatetime = usupdatetime;
  }

}
