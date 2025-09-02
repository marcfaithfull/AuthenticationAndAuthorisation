package org.example.jws1b.services;

import org.example.jws1b.entities.BlogEntry;

import java.util.List;

public interface BlogService {

    List<BlogEntry> getAllBlogEntries();

    void addPost(BlogEntry blogEntry);
}
