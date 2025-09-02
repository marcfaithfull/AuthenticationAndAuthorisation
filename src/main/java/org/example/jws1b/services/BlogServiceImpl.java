package org.example.jws1b.services;

import org.example.jws1b.entities.BlogEntry;
import org.example.jws1b.repositories.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<BlogEntry> getAllBlogEntries(String userId) {
        return blogRepository.findAllByUserId(userId);
    }

    @Override
    public BlogEntry getBlogById(Long id, String userId) {
        BlogEntry theBlog = blogRepository.findBlogByBlogId(id);
        if (theBlog.getUserId().equals(userId)) {
            return theBlog;
        }
        return null;
    }

    @Override
    public void addPost(BlogEntry blogEntry, String userId) {
        blogEntry.setUserId(userId);
        blogRepository.save(blogEntry);
    }
}
