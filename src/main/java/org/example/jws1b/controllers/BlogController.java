package org.example.jws1b.controllers;

import org.example.jws1b.entities.BlogEntry;
import org.example.jws1b.exceptions.NoContentException;
import org.example.jws1b.services.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class BlogController {
    private final BlogServiceImpl blogServiceImpl;

    @Autowired
    public BlogController(BlogServiceImpl blogServiceImpl) {
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("/posts")
    @ResponseBody
    public ResponseEntity<List<BlogEntry>> getAllBlogEntries(@AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject();
        blogServiceImpl.getAllBlogEntries(userId);
        return ResponseEntity.ok(blogServiceImpl.getAllBlogEntries(userId));
    }

    @GetMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<BlogEntry> getBlogEntryById(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject();

        try {
            return blogServiceImpl.getBlogById(id, userId);
        } catch (NoContentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/newpost")
    @ResponseBody
    public ResponseEntity<String> createBlogEntry(@RequestBody BlogEntry blogEntry, @AuthenticationPrincipal Jwt principal, Authentication authentication) {
        String userId = principal.getSubject();
        blogServiceImpl.addPost(blogEntry, userId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body("new blog entry added");
    }

    @PutMapping("/updatepost")
    public ResponseEntity<String> updateBlogEntry(@RequestBody BlogEntry blogEntry, @AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject();
        blogServiceImpl.updatePost(blogEntry, userId);
        return ResponseEntity.ok("Your blog post was updated");
    }

    @PreAuthorize("hasRole('admin') or (hasRole('user'))")
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<String> deleteBlogEntry(@PathVariable Long id, @AuthenticationPrincipal Jwt principal, Authentication authentication) {
        String userId = principal.getSubject();

        try {
            blogServiceImpl.deletePostById(id, userId, authentication);
        } catch (NoContentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Your blog post was deleted");
    }

    // ADMIN CONTROLLER
    @GetMapping("/count")
    @Secured("admin")
    public ResponseEntity<Long> totalBlogs() {
        blogServiceImpl.countAllBlogPosts();
        return ResponseEntity.ok(blogServiceImpl.countAllBlogPosts());
    }
}
