package com.chmits.demo.entities;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */

@Entity(name = "Student")
@Table(name = "student")
@Getter
@Setter()
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    //hibernate-identifiers: see https://www.baeldung.com/hibernate-identifiers
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private int age;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @Setter(AccessLevel.NONE)
    private StudentIdCard studentIdCard;

    public void addStudentCardId(StudentIdCard studentIdCard) {
        studentIdCard.setStudent(this);
        this.studentIdCard = studentIdCard;
    }

    public void removeStudentCardId(StudentIdCard studentIdCard) {
        studentIdCard.setStudent(null);
        this.studentIdCard = null;
    }

    /*
        orphanRemoval is an entirely ORM-specific thing. It marks "child" entity to be removed when it's no
        longer referenced from the "parent" entity
     */
    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.setStudent(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setStudent(null);
    }

    public Student(String firstName,
                   String lastName,
                   String email,
                   Integer age) {
       this(null, firstName, lastName, email, age, null, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", cardNumber=" + studentIdCard +
                '}';
    }
}
