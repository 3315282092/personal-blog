package com.zongyuheng.service;

import com.zongyuheng.Utils.BlogPageManager;
import com.zongyuheng.Utils.IndexPageManager;
import com.zongyuheng.Utils.MarkdownUtils;
import com.zongyuheng.config.NotFoundException;
import com.zongyuheng.mapper.BlogMapper;
import com.zongyuheng.mapper.CommentMapper;
import com.zongyuheng.mapper.RelationMapper;
import com.zongyuheng.pojo.TBlog;
import com.zongyuheng.pojo.TComment;
import com.zongyuheng.pojo.TType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogPageManager<TBlog> blogBlogPageManager;
    @Autowired
    private IndexPageManager<TBlog> blogIndexPageManager;

    @Override
    public IndexPageManager<TBlog> listQueryBlogs(int page, String query) {
        List<TBlog> allBlogs = blogMapper.getBlogs();
        blogIndexPageManager.setTotalElementsAndTotalPages(allBlogs.size());
        if (page == 1) {
            blogIndexPageManager.setFirst(true);
        } else {
            blogIndexPageManager.setFirst(false);
        }

        int totalPages = blogIndexPageManager.getTotalPages();
        if (page > totalPages) {
            blogIndexPageManager.setNum(page - 1);
        } else if (page == totalPages) {
            blogIndexPageManager.setNum(page);
            blogIndexPageManager.setLast(true);
        } else {
            blogIndexPageManager.setNum(page);
            blogIndexPageManager.setLast(false);
        }
        List<TBlog> blogByPage = blogMapper.listQueryBlog(blogIndexPageManager.getSize(), blogIndexPageManager.getNum(), blogIndexPageManager.getSort()[0], blogIndexPageManager.getSort()[1], query);
        blogIndexPageManager.setResults(blogByPage);
        blogIndexPageManager.setNumberOfElements(blogByPage.size());
        return blogIndexPageManager;
    }

    @Transactional
    @Override
    public TBlog getAndConvert(Long id) {
        TBlog blog = blogMapper.getBlog(id.intValue());
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        TBlog blogExtra = new TBlog();
        BeanUtils.copyProperties(blog, blogExtra);
        String content = blogExtra.getContent();
        System.out.println(content);
        String HtmlContent = MarkdownUtils.markdownToHtmlExtensions(content);
        System.out.println(HtmlContent);
        blogExtra.setContent(HtmlContent);
        blogMapper.updateTimes(id);
        return blogExtra;
    }

    @Override
    @Transactional
    public TBlog getBlog(int id) {
        TBlog blog = blogMapper.getBlog(id);
        return blog;
    }

    @Override
    @Transactional
    public BlogPageManager<TBlog> listBlog(int page, TBlog blog) {
//查询的内容一是title（非空或者空字符串），判断传递typeid的时候需要判断是不是null 不需要判断是不是空字符串
        //传递的是是否为true，如果是就查=1不是就不加这个条件查询，
        System.out.println(page);

        if (blog.getTypeid() == null && blog.getRecommend() == null && (StringUtils.isEmpty(blog.getTitle()) || blog.getTitle() == null)) {
            List<TBlog> allBlogs = blogMapper.getBlogs();
            blogBlogPageManager.setTotalElementsAndTotalPages(allBlogs.size());
        } else {
            blogBlogPageManager.setTotalElementsAndTotalPages(blogMapper.getSomeBlogs(blog).size());
        }
        if (page == 1) {
            blogBlogPageManager.setFirst(true);
        } else {
            blogBlogPageManager.setFirst(false);
        }
        int totalPages = blogBlogPageManager.getTotalPages();
        if (page > totalPages && totalPages != 0) {
            blogBlogPageManager.setNum(page - 1);
        } else if (page == totalPages || totalPages == 0) {
            blogBlogPageManager.setNum(page);
            blogBlogPageManager.setLast(true);
        } else {
            blogBlogPageManager.setNum(page);
            blogBlogPageManager.setLast(false);
        }
        List<TBlog> blogList = blogMapper.listBlog(blogBlogPageManager.getSize(), blogBlogPageManager.getNum(), blogBlogPageManager.getSort()[0], blogBlogPageManager.getSort()[1], blog);
        blogBlogPageManager.setResults(blogList);
        blogBlogPageManager.setNumberOfElements(blogList.size());
        return blogBlogPageManager;
    }

    @Override
    @Transactional
    public Boolean saveBlog(TBlog blog) {
        int changeRows1;
        int changeRows2;
        if (blog.getTagIds() != "" && blog.getTagIds() != null) {
            List<Long> list = new ArrayList<>();
            if (!"".equals(blog.getTagIds()) && blog.getTagIds() != null) {
                String[] idarray = blog.getTagIds().split(",");
                for (int i = 0; i < idarray.length; i++) {
                    list.add(new Long(idarray[i]));
                }
            }

            if (blog.getBid() == null) {
                blog.setCreatetime(new Date());
                blog.setUpdatetime(new Date());
                blog.setTimes(Long.parseLong("0"));
                changeRows1 = blogMapper.saveBlogInsert(blog);
                changeRows2 = relationMapper.saveRelation(blog.getBid(), list);
            } else {
                blog.setUpdatetime(new Date());
                Long oldBid = blog.getBid();
                relationMapper.deleteRelation(blog.getBid());
                changeRows1 = blogMapper.saveBlogReplace(blog);
                commentMapper.updateTarget(blog.getBid(), oldBid);
                changeRows2 = relationMapper.saveRelation(blog.getBid(), list);
            }
            System.out.println(changeRows1);
            System.out.println(changeRows2);
            return changeRows1 > 0 && changeRows2 > 0;
        } else {
            if (blog.getBid() == null) {
                blog.setCreatetime(new Date());
                blog.setUpdatetime(new Date());
                blog.setTimes(Long.parseLong("0"));
                changeRows1 = blogMapper.saveBlogInsert(blog);
            } else {
                blog.setUpdatetime(new Date());
                Long oldBid = blog.getBid();
                relationMapper.deleteRelation(blog.getBid());
                changeRows1 = blogMapper.saveBlogReplace(blog);
                commentMapper.updateTarget(blog.getBid(), oldBid);
            }

            return changeRows1 > 0;

        }

    }

    @Override
    @Transactional
    public Boolean updateBlog(int id, TBlog blog) {
        if (blogMapper.getBlog(id) == null) {
            throw new NotFoundException("该博客不存在");
        }
        return blogMapper.updateBlog(id, blog) == 1;
    }

    @Override
    @Transactional
    public Boolean deleteBlog(Long id) throws RuntimeException {
        int changeRows1 = relationMapper.deleteRelation(id);
        int changeRows2 = commentMapper.deleteByBlogId(id.intValue());
        int changeRows3 = blogMapper.deleteBlog(id.intValue());
        return changeRows1 > 0 && changeRows2 > 0 && changeRows3 > 0;
    }

    @Override
    public IndexPageManager<TBlog> pageListBlog(int page) {
        List<TBlog> allBlogs = blogMapper.getBlogs();
        blogIndexPageManager.setTotalElementsAndTotalPages(allBlogs.size());
        if (page == 1) {
            blogIndexPageManager.setFirst(true);
        } else {
            blogIndexPageManager.setFirst(false);
        }

        int totalPages = blogIndexPageManager.getTotalPages();
        if (page > totalPages) {
            blogIndexPageManager.setNum(page - 1);
        } else if (page == totalPages) {
            blogIndexPageManager.setNum(page);
            blogIndexPageManager.setLast(true);
        } else {
            blogIndexPageManager.setNum(page);
            blogIndexPageManager.setLast(false);
        }
        List<TBlog> blogByPage = blogMapper.pageListBlog(blogIndexPageManager.getSize(), blogIndexPageManager.getNum(), blogIndexPageManager.getSort()[0], blogIndexPageManager.getSort()[1]);
        blogIndexPageManager.setResults(blogByPage);
        blogIndexPageManager.setNumberOfElements(blogByPage.size());
        return blogIndexPageManager;
    }

    @Override
    public List<TBlog> listRecommendBlogTop(Integer size) {
        List<TBlog> blogList = blogMapper.listRecommendBlogTop(size);
        return blogList;
    }

    @Override
    public BlogPageManager<TBlog> listBlog(Long tagId, int page) {
        blogBlogPageManager.setTotalElementsAndTotalPages(blogMapper.listByTagIdAll(tagId.intValue()).size());
        if (page == 1) {
            blogBlogPageManager.setFirst(true);
        } else {
            blogBlogPageManager.setFirst(false);
        }
        int totalPages = blogBlogPageManager.getTotalPages();
        if (page > totalPages && totalPages != 0) {
            blogBlogPageManager.setNum(page - 1);
        } else if (page == totalPages || totalPages == 0) {
            blogBlogPageManager.setNum(page);
            blogBlogPageManager.setLast(true);
        } else {
            blogBlogPageManager.setNum(page);
            blogBlogPageManager.setLast(false);
        }
        List<TBlog> blogList = blogMapper.listByTagId(blogBlogPageManager.getSize(), blogBlogPageManager.getNum(), blogBlogPageManager.getSort()[0], blogBlogPageManager.getSort()[1], tagId);
        blogBlogPageManager.setResults(blogList);
        blogBlogPageManager.setNumberOfElements(blogList.size());
        return blogBlogPageManager;
    }

    @Override
    public Map<String, List<TBlog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<TBlog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlogs() {
        return blogMapper.countBlogs();
    }
}
