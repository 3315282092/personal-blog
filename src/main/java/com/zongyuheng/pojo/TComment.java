package com.zongyuheng.pojo;


import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

public class TComment {

  private long cid;
  private String nickname;
  private String headicon;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  private String commentcontent;
  private Date createtime;
  private TBlog tBlog;
  private List<TComment> comments;
  private TComment parent;
  private String email;
  private Integer admincontext;

  public Integer getAdmincontext() {
    return admincontext;
  }

  public void setAdmincontext(Integer admincontext) {
    this.admincontext = admincontext;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "TComment{" +
            ", commentcontent='" + commentcontent + '\'' +

            '}';
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public List<TComment> getComments() {
    return comments;
  }

  public void setComments(List<TComment> comments) {
    this.comments = comments;
  }

  public TComment getParent() {
    return parent;
  }

  public void setParent(TComment parent) {
    this.parent = parent;
  }

  public long getCid() {
    return cid;
  }

  public void setCid(long cid) {
    this.cid = cid;
  }



  public String getHeadicon() {
    return headicon;
  }

  public void setHeadicon(String headicon) {
    this.headicon = headicon;
  }

  public String getCommentcontent() {
    return commentcontent;
  }

  public void setCommentcontent(String commentcontent) {
    this.commentcontent = commentcontent;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatectime(Timestamp createctime) {
    this.createtime = createctime;
  }

  public TBlog gettBlog() {
    return tBlog;
  }

  public void settBlog(TBlog tBlog) {
    this.tBlog = tBlog;
  }
}
