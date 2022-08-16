package com.example.demo.repository;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void findById(){
        assertTrue(studentRepository.findById(1L).isPresent());
    }

    @Test
    void findAll(){
        List<Student> studentList = studentRepository.findAll();

        assertFalse(studentList.isEmpty());
        assertTrue(studentList.size()>0);
    }

    @Test
    void create(){
        Student student = new Student(1L, "Gullo", "laura", LocalDate.of(1986,11,12));
        Student student1 = studentRepository.save(student);
        assertTrue(student1.getId()>0);
    }

    @Test
    public void delete(){
        Student student = studentRepository.save(new Student(1L, "Gullo", "laura", LocalDate.of(1986,11,12)));
        studentRepository.delete(student);
        assertFalse(studentRepository.findById(1L).isPresent());
    }

}