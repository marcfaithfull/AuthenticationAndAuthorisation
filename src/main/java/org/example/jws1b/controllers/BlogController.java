package org.example.jws1b.controllers;

import org.example.jws1b.entities.BlogEntry;
import org.example.jws1b.services.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BlogController {
    private final BlogServiceImpl blogServiceImpl;

    @Autowired
    public BlogController(BlogServiceImpl blogServiceImpl) {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/getname")
    public String getName() {
        return "name";
    }

    @GetMapping("/anothertest")
    @ResponseBody
    public ResponseEntity<List<BlogEntry>> test() {
        blogServiceImpl.getAllBlogEntries();
        return ResponseEntity.ok(blogServiceImpl.getAllBlogEntries());
    }
}
