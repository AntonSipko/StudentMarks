package telran.students;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import static telran.students.TestDb.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.students.dto.*;
import telran.students.exceptions.*;
import telran.students.model.StudentDoc;
import telran.students.repo.StudentRepo;
import telran.students.service.StudentsService;

@SpringBootTest

class StudentsMarksServiceTests {
	@Autowired
	StudentsService studentsService;
	@Autowired
	StudentRepo studentRepo;
	@Autowired
	TestDb testDb;
	Student student1=new Student(11111L,"054441789");
	Student studentUpdatedPhone=new Student(ID1,"054441789");
	Mark mark=new Mark("Math", 60, LocalDate.now());

	
	@BeforeEach
	void setUp() {
		testDb.createDb();
	}

	@Test
	
	void addStudentTest() {
	    assertEquals(student1, studentsService.addStudent(student1));
		assertEquals(student1, studentRepo.findById(11111L).orElseThrow().build());
		assertThrowsExactly(StudentIllegalStateException.class, ()->studentsService.addStudent(student1));
	}
	@Test

	void updatePhoneNumberTest() {
		
		assertEquals(studentUpdatedPhone, studentsService.updatePhoneNumber(ID1, "054441789"));
		assertEquals(studentUpdatedPhone, studentRepo.findById(ID1).orElseThrow().build());
		assertThrowsExactly(StudentNotFoundException.class,
				()->studentsService.updatePhoneNumber(ID1 + 1000, PHONE2));
	}
	@Test
	
	void addMarkTest() {
		//FIXME according to TestDb
		assertFalse(studentRepo.findById(ID1).orElseThrow().getMarks().contains(mark));
		assertEquals(mark, studentsService.addMark(ID1, mark));
		assertTrue(studentRepo.findById(ID1).orElseThrow().getMarks().contains(mark));
		assertThrowsExactly(StudentNotFoundException.class,
				()->studentsService.addMark(ID1 + 1000, mark));
		
	}
	@Test
	void getStudentTest() {
		assertEquals(students[0], studentsService.getStudent(ID1));
		assertThrowsExactly(StudentNotFoundException.class, ()->studentsService.getStudent(100000));
	}
	@Test
	void getMarksTest() {
		assertArrayEquals(marks[0], studentsService.getMarks(ID1).toArray(Mark[]::new));
		assertThrowsExactly(StudentNotFoundException.class, ()->studentsService.getMarks(100000));
	}
	@Test
	void getStudentByPhoneNumberTest() {
		assertEquals(students[0], studentsService.getStudentByPhoneNumber(PHONE1));
	}
	@Test
	void getStudentsByPhonePrefix() {
		List<Student> expected = List.of(students[0], students[6]);
		assertIterableEquals(expected, studentsService.getStudentsByPhonePrefix("051"));
	}
	@Test
	void getStudentsMarksDateTest() {
		List<Student>expectedStudentList= new ArrayList<>();
		expectedStudentList.add(students[0]);
		expectedStudentList.add(students[1]);
	    expectedStudentList.add(students[2]);
     	expectedStudentList.add(students[5]);
     	List<Student>studentsActual=studentsService.getStudentsMarksDate(DATE1);
     	assertEquals(expectedStudentList.size(),studentsActual.size());
     	List<Long>expectedIdsOfStudentsWithMarkOnDate=expectedStudentList.stream().map(s ->s.id() ).toList();
     	List<Long>actualIdsOfStudentsWithMarkOnDate=studentsActual.stream().map(s->s.id()).toList();
     	assertEquals(expectedIdsOfStudentsWithMarkOnDate, actualIdsOfStudentsWithMarkOnDate);
	}
	@Test
	@Disabled
	void getStudentsMarksMonthYear() {
		List<Student>expectedStudentList= new ArrayList<>();
		expectedStudentList.add(students[0]);
		expectedStudentList.add(students[1]);
	    expectedStudentList.add(students[2]);
     	expectedStudentList.add(students[5]);
     	List<Student>studentsActual=studentsService.getStudentsMarksMonthYear(1,2024);
     	assertEquals(expectedStudentList.size(),studentsActual.size());	
	}
	@Test
	void getStudentsGoodSubjectMarkTest() {
		assertThrowsExactly(StudentNotFoundException.class,()->studentsService.getStudentsGoodSubjectMark(100));
		assertEquals(2,studentsService.getStudentsGoodSubjectMark(99).size());
		assertEquals(6,studentsService.getStudentsGoodSubjectMark(40).size());

	}
	@Test
	void removeStudentTest() {
		studentsService.removeStudent(ID1);
		assertThrowsExactly(StudentNotFoundException.class, ()->studentsService.removeStudent(ID1));
		assertThrowsExactly(StudentNotFoundException.class, ()->studentsService.removeStudent(10101010));

		
		
	}
	
	

}