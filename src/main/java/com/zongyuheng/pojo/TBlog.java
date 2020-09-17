package com.zongyuheng.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Transient;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class TBlog {
  private String description;
  private Long bid;
  private TType tType;
  private TUser tUser;
  private Long typeid;
  //这个是多对一


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTagIds() {
    return tagIds;
  }

  public void setTagIds(String tagIds) {
    this.tagIds = tagIds;
  }
  private String writer;

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  private String tagIds;
  private String title;
  private String content;
  private String image;
  private Long times;
  private Long pay;
  private Long copyright;
  private Long publish;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS")
  private Date createtime;
  private Date updatetime;
  private List<TTags> tTags;
  private List<TComment> comments;
  private Long recommend;
  private Long commentable;
  public String flag;

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }
  public void init(){
  this.tagIds=tagsToIds(this.gettTags());
  }
  //1,2,3
  private  String tagsToIds(List<TTags> tags){
    if (!tags.isEmpty()){
      StringBuffer ids=new StringBuffer();
      boolean flag=false;
      for (TTags tTag:tags){
        if (flag){
          ids.append(",");
        }else {
          flag=true;
        }
        ids.append(tTag.getTid());
      }
      return ids.toString();
    }else {
      return tagIds;
    }
  }




  public Long getCommentable() {
    return commentable;
  }

  public void setCommentable(Long commentable) {
    this.commentable = commentable;
  }



  public Long getBid() {
    return bid;
  }

  public void setBid(Long bid) {
    this.bid = bid;
  }

  public TType gettType() {
    return tType;
  }

  public void settType(TType tType) {
    this.tType = tType;
  }

  public TUser gettUser() {
    return tUser;
  }

  public void settUser(TUser tUser) {
    this.tUser = tUser;
  }

  public Long getTypeid() {
    return typeid;
  }

  public void setTypeid(Long typeid) {
    this.typeid = typeid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Long getTimes() {
    return times;
  }

  public void setTimes(Long times) {
    this.times = times;
  }

  public Long getPay() {
    return pay;
  }

  public void setPay(Long pay) {
    this.pay = pay;
  }

  public Long getCopyright() {
    return copyright;
  }

  public void setCopyright(Long copyright) {
    this.copyright = copyright;
  }

  public Long getPublish() {
    return publish;
  }

  public void setPublish(Long publish) {
    this.publish = publish;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Date getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(Date updatetime) {
    this.updatetime = updatetime;
  }

  public List<TTags> gettTags() {
    return tTags;
  }

  public void settTags(List<TTags> tTags) {
    this.tTags = tTags;
  }

  public List<TComment> getComments() {
    return comments;
  }

  public void setComments(List<TComment> comments) {
    this.comments = comments;
  }

  public Long getRecommend() {
    return recommend;
  }

  public void setRecommend(Long recommend) {
    this.recommend = recommend;
  }

  public TBlog() {
  }

  @Override
  public String toString() {
    return "TBlog{" +
            "description='" + description + '\'' +
            ", bid=" + bid +
            ", tType=" + tType +
            ", tUser=" + tUser +
            ", typeid=" + typeid +
            ", writer='" + writer + '\'' +
            ", tagIds='" + tagIds + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", image='" + image + '\'' +
            ", times=" + times +
            ", pay=" + pay +
            ", copyright=" + copyright +
            ", publish=" + publish +
            ", createtime=" + createtime +
            ", updatetime=" + updatetime +
            ", tTags=" + tTags +
            ", comments=" + comments +
            ", recommend=" + recommend +
            ", commentable=" + commentable +
            ", flag='" + flag + '\'' +
            '}';
  }

  public TBlog(String description, Long bid, TType tType, TUser tUser, Long typeid, String writer, String tagIds, String title, String content, String image, Long times, Long pay, Long copyright, Long publish, Date createtime, Date updatetime, List<TTags> tTags, List<TComment> comments, Long recommend, Long commentable, String flag) {
    this.description = description;
    this.bid = bid;
    this.tType = tType;
    this.tUser = tUser;
    this.typeid = typeid;
    this.writer = writer;
    this.tagIds = tagIds;
    this.title = title;
    this.content = content;
    this.image = image;
    this.times = times;
    this.pay = pay;
    this.copyright = copyright;
    this.publish = publish;
    this.createtime = createtime;
    this.updatetime = updatetime;
    this.tTags = tTags;
    this.comments = comments;
    this.recommend = recommend;
    this.commentable = commentable;
    this.flag = flag;
  }
}