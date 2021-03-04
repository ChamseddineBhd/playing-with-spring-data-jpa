package com.chmits.demo.repositories;

import com.chmits.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
