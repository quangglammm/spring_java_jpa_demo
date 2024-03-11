package com.lamlq.example.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @InjectMocks
    private StudentService service;
    @Mock
    private StudentMapper mapper;
    @Mock
    private StudentRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student() {
        //Given
        StudentDto studentDto = new StudentDto(
                "John",
                "Doe",
                "john@gmail.com",
                1);

        Student student = new Student(
                "John",
                "Doe",
                "john@gmail.com",
                20);

        Student savedStudent = new Student(
                "John",
                "Doe",
                "john@gmail.com",
                20);

        savedStudent.setId(1);
        //Mock the calls
        when(mapper.toStudent(studentDto))
                .thenReturn(student);
        when(repository.save(student))
                .thenReturn(savedStudent);
        when(mapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@gmail.com"
                ));

        //When
        StudentResponseDto studentResponseDto = service.saveStudent(studentDto);
        //Then
        assertEquals(studentDto.firstName(), studentResponseDto.firstName());
        assertEquals(studentDto.lastName(), studentResponseDto.lastName());
        assertEquals(studentDto.email(), studentResponseDto.email());

        verify(mapper, times(1))
                .toStudent(studentDto);
        verify(repository, times(1))
                .save(student);
        verify(mapper, times(1))
                .toStudentResponseDto(savedStudent);

    }

    @Test
    public void should_return_all_students() {
        //Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "John",
                "Doe",
                "john@gmail.com",
                20
        ));
        //Mock the calls
        when(repository.findAll()).thenReturn(students);
        when(mapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@gmail.com"
                ));

        //When
        List<StudentResponseDto> studentResponseDtoList = service.getAllStudent();

        //Then
        assertEquals(students.size(), studentResponseDtoList.size());
        verify(repository,times(1)).findAll();
    }

    @Test
    public void should_return_student_by_id() {
        //Given
        Integer studentId = 1;
        Student student = new Student(
                "John",
                "Doe",
                "john@gmail.com",
                20);
        //Mock the calls
        when(repository.findById(studentId))
                .thenReturn(Optional.of(student));
        when(mapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@gmail.com"
                ));
        //When
        StudentResponseDto studentResponseDto = service.getStudentById(studentId);
        //Then
        assertEquals(student.getFirstName(), studentResponseDto.firstName());
        assertEquals(student.getLastName(), studentResponseDto.lastName());
        assertEquals(student.getEmail(), studentResponseDto.email());

        verify(repository,times(1)).findById(studentId);
    }

    @Test
    public void should_return_student_by_name() {
        //Given
        String studentName = "John";
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "John",
                "Doe",
                "john@gmail.com",
                20
        ));
        //Mock the calls
        when(repository.findAllByFirstName(studentName)).thenReturn(students);
        when(mapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "john@gmail.com"
                ));

        //When
        var studentDto = service.getStudentByName(studentName);

        //Then
        assertEquals(students.size(), studentDto.size());
        verify(repository,times(1))
                .findAllByFirstName(studentName);
    }


}