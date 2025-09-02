package org.example.jws1b.repositories;

import org.example.jws1b.entities.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntry, Long> {

}
