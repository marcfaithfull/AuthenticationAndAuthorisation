package org.example.jws1b.services;

import org.example.jws1b.entities.BlogEntry;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;
import java.util.List;

public interface BlogService {

    List<BlogEntry> getAllBlogEntries(String userId);

    BlogEntry getBlogById(Long id, String userId);

    void addPost(BlogEntry blogEntry, String userId);

    void updatePost(BlogEntry blogEntry, String userId);

    void deletePostById(Long id, String userId, Authentication authentication);

    Long countAllBlogPosts();
}
