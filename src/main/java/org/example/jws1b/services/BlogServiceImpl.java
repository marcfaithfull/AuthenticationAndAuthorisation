package org.example.jws1b.services;

import jakarta.transaction.Transactional;
import org.example.jws1b.entities.BlogEntry;
import org.example.jws1b.exceptions.NoContentException;
import org.example.jws1b.exceptions.UnauthorisedException;
import org.example.jws1b.repositories.BlogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<BlogEntry> getAllBlogEntries(String userId) {
        List<BlogEntry> blogEntries = blogRepository.findAllByUserId(userId);
        if (blogEntries.isEmpty()) {
            throw new NoContentException("The database is empty");
        }
        return blogRepository.findAllByUserId(userId);
    }

    @Override
    public ResponseEntity<BlogEntry> getBlogById(Long id, String userId) {
        BlogEntry theBlog = blogRepository.findBlogByBlogId(id);

        if (theBlog == null) {
            throw new NoContentException("There is no blog post with this id");
        }

        if (!theBlog.getUserId().equals(userId)) {
            throw new UnauthorisedException("This blog post belongs to another user");
        }

        return ResponseEntity.ok(theBlog);
    }

    @Override
    public void addPost(BlogEntry blogEntry, String userId, Authentication authentication) {
        blogEntry.setUserId(userId);
        blogRepository.save(blogEntry);
        Authentication keycloakUser = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(keycloakUser);
    }

    @Override
    public void updatePost(BlogEntry blogEntry, String userId) {
        BlogEntry comparisonBlog = blogRepository.findBlogByBlogId(blogEntry.getBlogId());

        if (comparisonBlog == null) {
            throw new NoContentException("There is no blog post with this id");
        }

        if (!comparisonBlog.getUserId().equals(userId)) {
            throw new UnauthorisedException("This blog post belongs to another user");
        }

        if (comparisonBlog.getUserId().equals(userId)) {
            blogEntry.setUserId(userId);
            blogRepository.save(blogEntry);
        }
    }

    @Override
    public void deletePostById(Long id, String userId, Authentication authentication) {
        BlogEntry comparisonBlog = blogRepository.findBlogByBlogId(id);

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(String::toUpperCase)
                .anyMatch(role-> role.equals("ROLE_ADMIN"));

        if (comparisonBlog == null) {
            throw new NoContentException("nothing to delete");
        }

        if (!comparisonBlog.getUserId().equals(userId) || isAdmin) {
            throw new UnauthorisedException("You do not have permission to delete this post");
        }

        if (comparisonBlog.getUserId().equals(userId) || isAdmin) {
            blogRepository.deleteBlogByBlogId(id);
        }
    }

    @Override
    public Long countAllBlogPosts() {
        int size = blogRepository.findAll().size();
        return (long) size;
    }
}
