package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student estudiante = studentService.createStudent(student);
        return new ResponseEntity<Student>(estudiante, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOneById(@PathVariable("id") Long id) {
            Optional<Student> student = studentService.findById(id);
            return ResponseEntity.ok(student);
     }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.ok("student delete");
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Student student)  {

        studentService.update(id, student);
    }

    @GetMapping("/averageAge")
    public int averageAge(){
        return studentService.averageAge();
    }

    @GetMapping("/olderAge")
    public int olderAge(){
        return studentService.olderAge();
    }

    @GetMapping("/minorAge")
    public int minorAge(){
        return studentService.minorAge();
    }

    @GetMapping("/olders")
    public List<Student> olders(){
        return studentService.olders();
    }

    @GetMapping("/minors")
    public List<Student> minors(){
        return studentService.minors();
    }

    @GetMapping("/averageOlder")
    public int averageOlder(){

        return studentService.averageOlder();
    }
    @GetMapping("/averageMinor")
    public int averageMinor(){

        return studentService.averageMinor();
    }

    @GetMapping("/getNames")
    public String getNameSurnameId(){
        return studentService.getNameSurnameId();
    }



}
