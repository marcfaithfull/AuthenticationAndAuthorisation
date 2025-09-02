package org.example.jws1b.controllers;

import org.example.jws1b.services.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class AdminBlogController {
    private final BlogServiceImpl blogServiceImpl;

    @Autowired
    public AdminBlogController(BlogServiceImpl blogServiceImpl) {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/count")
    @Secured("admin")
    public ResponseEntity<Long> totalBlogs() {
        blogServiceImpl.countAllBlogPosts();
        return ResponseEntity.ok(blogServiceImpl.countAllBlogPosts());
    }
}
