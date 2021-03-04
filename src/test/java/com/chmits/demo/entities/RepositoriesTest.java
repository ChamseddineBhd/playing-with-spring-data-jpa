package com.chmits.demo.entities;

import com.chmits.demo.repositories.BookRepository;
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

    @Autowired
    BookRepository bookRepository;

    private void cleanDB() {
        studentRepository.deleteAll();
        studentIdCardRepository.deleteAll();
    }

    @Test
    public void testStudentRepository() {
        cleanDB();
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

    @Test
    public void testOneToManyRelation() {
        cleanDB();
        Student student = new Student("Jon", "Doe", "jon.doe@gmail.com", 22);
        Book book1 = new Book("Java 11");
        Book book2 = new Book("Kubernetes");
        student.addBook(book1);
        student.addBook(book2);
        Student savedStudent = studentRepository.save(student);
        assertEquals(2, savedStudent.getBooks().size());
        assertEquals("Java 11", savedStudent.getBooks().get(0).getBookName());
        savedStudent.removeBook(book1);
        Student updatedStudent = studentRepository.save(student);
        // because of orphanRemoval=true
        assertEquals(1, bookRepository.findAll().size());
        // because of CascadeType.REMOVE
        studentRepository.deleteById(updatedStudent.getId());
        assertEquals(0, bookRepository.findAll().size());

    }

}
