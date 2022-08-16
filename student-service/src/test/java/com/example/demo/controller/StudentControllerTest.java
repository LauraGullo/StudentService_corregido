package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;


    List<Student> listStudents;
    Student student;
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createStudent() throws Exception {
        Student newStudent = new Student(null, "Gullo", "Laura", LocalDate.of(1986,11,12));
        Student createdStudent = new Student(1L, "Gullo", "Laura", LocalDate.of(1986,11,12));
        when(studentService.createStudent(any())).thenReturn(createdStudent);
        mockMvc.perform(MockMvcRequestBuilders.post("/students/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.surname").value("Gullo"));
    }

    @Test
    void findAll() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L,"Gullo","Laura", LocalDate.of(1986,11,12)));
        studentList.add(new Student(2L,"Seri","Martin", LocalDate.of(1982,10,11)));
        when(studentService.findAll()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/GetAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$[0].surname").value("Gullo"))
                .andExpect(jsonPath("$[1].surname").value("Seri"));
    }

    @Test
    void getOneById() throws Exception {
        when(studentService.findById(1L)).thenReturn(Optional.ofNullable(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/getOne/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.surname").value("Seri"));
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/delete/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Student newStudent = new Student(1L, "Gullo", "Laura", LocalDate.of(1986,11,12));
        Student updateStudent = new Student(2L, "Seri", "Martin", LocalDate.of(1982,10,12));
        when(studentService.update(1L, newStudent)).thenReturn(updateStudent);
        mockMvc.perform(MockMvcRequestBuilders.put("/students/update/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void averageAge() throws Exception {
        when(studentService.averageAge()).thenReturn(30);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/edadPromedio")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("30"));
    }

    @Test
    void olderAge() throws Exception {
        when(studentService.olderAge()).thenReturn(44);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/mayorEdad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("44"));
    }

    @Test
    void minorAge() throws Exception {
        when(studentService.minorAge()).thenReturn(14);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/menorEdad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("14"));
    }

    @Test
    void olders() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Gullo", "Laura", LocalDate.of(1986,11,12)));
        studentList.add(new Student(2L, "Seri", "Martin", LocalDate.of(1982,04,12)));
        when(studentService.olders()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/mayores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laura"))
                .andDo(print());
    }

    @Test
    void minors() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "Gullo", "Laura", LocalDate.of(2005,11,12)));
        studentList.add(new Student(2L, "Seri", "Martin", LocalDate.of(2012,04,12)));
        when(studentService.minors()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/menores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laura"))
                .andDo(print());
    }

    @Test
    void averageOlder() throws Exception {
        when(studentService.averageOlder()).thenReturn(34);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/PromedioMayores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("34"));
    }

    @Test
    void averageMinor() throws Exception {
        when(studentService.averageMinor()).thenReturn(14);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/PromedioMenores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("14"));
    }

    @Test
    void getNameSurnameId() throws Exception {
        String nameSurnameId="1 Gullo Laura - 2 Perez Juan - 3 Lopez Carlos - 4 Seri Martin - 5 Gomez Maria";
        when(studentService.getNameSurnameId()).thenReturn(nameSurnameId);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/getNombres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().string("1 Gullo Laura - 2 Perez Juan - 3 Lopez Carlos - 4 Seri Martin - 5 Gomez Maria"));
    }
}