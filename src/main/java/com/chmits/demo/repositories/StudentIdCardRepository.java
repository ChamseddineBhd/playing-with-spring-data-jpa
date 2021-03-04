package com.chmits.demo.repositories;

import com.chmits.demo.entities.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */

@Repository
public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {
}
