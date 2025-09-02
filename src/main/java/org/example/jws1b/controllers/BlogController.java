package org.example.jws1b.controllers;

import org.example.jws1b.entities.BlogEntry;
import org.example.jws1b.services.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<BlogEntry>> test(@AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject();
        System.out.println(userId);

        blogServiceImpl.getAllBlogEntries(userId);
        return ResponseEntity.ok(blogServiceImpl.getAllBlogEntries(userId));
    }

    @PostMapping("/addpost")
    @ResponseBody
    public ResponseEntity<String> addNewPost(@RequestBody BlogEntry blogEntry) {
        blogServiceImpl.addPost(blogEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body("A post has been made");
    }
}
