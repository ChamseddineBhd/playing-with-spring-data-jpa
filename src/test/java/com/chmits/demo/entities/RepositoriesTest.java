package com.chmits.demo.entities;

import com.chmits.demo.repositories.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoriesTest {

    @Autowired
    StudentRepository studentRepository;


    @Test
    public void testStudentRepository() {
        Student student = new Student("Jon", "Doe", "jon.doe@gmail.com", 22);
        Student savedStudent = studentRepository.save(student);
        assertEquals(1, studentRepository.findAll().size());
        assertEquals(22, savedStudent.getAge());
        assertNull(savedStudent.getStudentIdCard());
        assertTrue(studentRepository.findByEmail("jon.doe@gmail.com").isPresent());

        StudentIdCard studentIdCard = new StudentIdCard("11009090");
        student.addStudentCardId(studentIdCard);
    }

}
