package com.chmits.demo.entities;

import com.chmits.demo.repositories.StudentIdCardRepository;
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

    @Autowired
    StudentIdCardRepository studentIdCardRepository;

    @Test
    public void testStudentRepository() {
        Student student = new Student("Jon", "Doe", "jon.doe@gmail.com", 22);
        StudentIdCard studentIdCard = new StudentIdCard("11009090");
        student.addStudentCardId(studentIdCard);

        Student savedStudent = studentRepository.save(student);

        assertEquals(1, studentRepository.findAll().size());
        assertEquals(22, savedStudent.getAge());
        assertNotNull(savedStudent.getStudentIdCard());
        assertTrue(studentRepository.findByEmail("jon.doe@gmail.com").isPresent());
        assertEquals("11009090", savedStudent.getStudentIdCard().getCardNumber());

        assertEquals(1, studentIdCardRepository.findAll().size());

        student.removeStudentCardId(studentIdCard);
        Student savedStudentWithoutCardId = studentRepository.save(student);
        // because of orphanRemoval=true
        assertEquals(0, studentIdCardRepository.findAll().size());
    }

}
