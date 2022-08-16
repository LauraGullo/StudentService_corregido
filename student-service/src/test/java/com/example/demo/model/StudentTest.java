package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;
    @BeforeEach
    void setUp() {
        student = new Student(1L, "Gullo", "Laura", LocalDate.of(1986,04,12));
    }


    @Test
    void getId() {
        assertTrue(student.getId()== 1);
        assertEquals(1,student.getId());
    }

    @Test
    void getSurname() {
        assertEquals("Gullo",student.getSurname());
    }

    @Test
    void getName() {
        assertEquals("Laura",student.getName());
    }

    @Test
    void getBirthday() {
        assertEquals(LocalDate.of(1986,04,12),student.getBirthday());
    }

    @Test
    void setId() {
        student.setId(Long.parseLong("6"));
        assertEquals(6,student.getId());
    }

    @Test
    void setSurname() {
        student.setSurname("Seri");
        assertEquals("Seri",student.getSurname());
    }

    @Test
    void setName() {
        student.setName("Martin");
        assertEquals("Martin",student.getName());
    }

    @Test
    void setBirthday() {
        student.setBirthday(LocalDate.now());
        assertEquals(LocalDate.now(),student.getBirthday());
    }

    @Test
    void testToString() {
            assertEquals("Student(id=1, surname=Gullo, name=Laura, birthday=1986-04-12)",student.toString());


    }
}