package com.zongyuheng.service;

import com.zongyuheng.mapper.CommentMapper;
import com.zongyuheng.pojo.TComment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<TComment> listCommentByBlogId(Long blogId) {
        List<TComment> byBlogId = commentMapper.findByBlogIdAndParentCommentNot(blogId.intValue(),"createtime","desc");
        return eachComment(byBlogId);
    }

    @Override
    public TComment saveComment(TComment comment) {
        Long parentCommentId=comment.getParent().getCid();
        if (parentCommentId!=-1){
           comment.setParent(commentMapper.findByCommentId(parentCommentId.intValue()));
        }else {
            comment.setParent(null);
        }
        comment.setCreatetime(new Date());
         commentMapper.saveComment(comment);
        return comment;
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<TComment> eachComment(List<TComment> comments) {
        List<TComment> commentsView = new ArrayList<>();
        for (TComment comment : comments) {
            TComment c = new TComment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<TComment> comments) {
        for (TComment comment : comments) {
            List<TComment> replys1 = comment.getComments();


            for(TComment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中

                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<TComment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(TComment comment) {
        //顶节点添加到临时存放集合
        if (comment.getComments().size()>0) {
            List<TComment> replys = comment.getComments();
            for (TComment reply : replys) {
                tempReplys.add(reply);
                if (reply.getComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
