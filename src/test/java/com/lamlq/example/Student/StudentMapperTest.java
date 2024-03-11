package com.lamlq.example.Student;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    //Test with JUnit5
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto studentDto = new StudentDto("John",
                "Doe",
                "john@gmail.com",
                1);
        Student student = mapper.toStudent(studentDto);
        assertEquals(studentDto.firstName(), student.getFirstName());
        assertEquals(studentDto.lastName(), student.getLastName());
        assertEquals(studentDto.email(), student.getEmail());
        assertNotNull(student.getSchool());
        assertEquals(studentDto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void should_throw_null_pointer_exception_when_student_dto_is_null(){
        var exp = assertThrows(NullPointerException.class,() -> mapper.toStudent(null));
        System.out.println(exp.getMessage());
        assertEquals("The StudentDto should not be null", exp.getMessage());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        //Given
        Student student = new Student("Jane",
                "Smith",
                "jane@gmail.com",
                20);

        //When
        StudentResponseDto studentResponseDto = mapper.toStudentResponseDto(student);

        //Then
        assertEquals(studentResponseDto.firstName(), student.getFirstName());
        assertEquals(studentResponseDto.lastName(), student.getLastName());
        assertEquals(studentResponseDto.email(), student.getEmail());
    }

}