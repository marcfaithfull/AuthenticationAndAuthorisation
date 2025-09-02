package org.example.jws1b.repositories;

import org.example.jws1b.entities.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntry, Long> {

    List<BlogEntry> findAllByUserId(String userId);

    BlogEntry findBlogByBlogId(Long id);

    void deleteBlogByBlogId(Long id);

}
