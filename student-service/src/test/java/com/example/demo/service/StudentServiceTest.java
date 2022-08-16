package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@SpringBootTest
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    Student student;

    @BeforeEach
    void setUp() {

       student = new Student(1L,"Gullo","laura", LocalDate.of(1980,01,01));
    }


    @Test
    void createStudent() {
        Student newStudent = new Student(null, "Gullo", "Maria" , LocalDate.now());
        Student createdStudent = new Student(2L, "Seri", "Martin" , LocalDate.now());
        when(studentRepository.save(newStudent)).thenReturn(createdStudent);
        assertNotNull(studentService.createStudent(newStudent));
    }

    @Test
    void findAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertFalse(studentService.findAll().isEmpty());
    }

    @Test
    void findById() {
        when(studentRepository.findById(anyLong())).thenReturn(of(student));

        assertNotNull(studentService.findById(1L));
    }

    @Test
    void deleteStudent() {
        doNothing().when(studentRepository).deleteById(student.getId());
        studentService.deleteStudent(student.getId());
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void update() {
        when(studentRepository.save(student)).thenReturn(student);
        when(studentRepository.findById(1L)).thenReturn(ofNullable(student));
        Student updateStudent = studentService.update(1L,student);
        assertNotNull(updateStudent);
        assertNotNull(studentService.update(1L, student));
    }

    @Test
    void averageAge() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(38, studentService.averageAge());
    }

    @Test
    void olderAge() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(41, studentService.olderAge());
    }

    @Test
    void minorAge() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(36, studentService.minorAge());
    }

    @Test
    void olders() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        studentList.add(new Student(3L,"Lopez","Laura", LocalDate.of(2015,04,06)));
        studentList.add(new Student(4L,"Perez","Juan", LocalDate.of(2013,06,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertFalse(studentService.olders().isEmpty());
    }

    @Test
    void minors() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        studentList.add(new Student(3L,"Lopez","Laura", LocalDate.of(2019,04,06)));
        studentList.add(new Student(4L,"Perez","Juan", LocalDate.of(2013,06,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertFalse(studentService.minors().isEmpty());
    }

    @Test
    void averageOlder() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        studentList.add(new Student(3L,"Lopez","Laura", LocalDate.of(2019,04,06)));
        studentList.add(new Student(4L,"Perez","Juan", LocalDate.of(2013,06,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(38, studentService.averageOlder());
    }

    @Test
    void averageMinor() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        studentList.add(new Student(3L,"Lopez","Laura", LocalDate.of(2019,04,06)));
        studentList.add(new Student(4L,"Perez","Juan", LocalDate.of(2013,06,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
        assertEquals(6, studentService.averageMinor());
    }

    @Test
    void getNameSurnameId() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Maria", LocalDate.of(1986,01,01)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1980,12,12)));
        when(studentRepository.findAll()).thenReturn(studentList);
            assertNotNull(studentService.getNameSurnameId());
        }

    }
