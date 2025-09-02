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
    public List<BlogEntry> getAllBlogEntries() {
        return blogRepository.findAll();
    }
}
