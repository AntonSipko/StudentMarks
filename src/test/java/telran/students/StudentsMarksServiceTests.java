package telran.students;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import static telran.students.TestDb.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.students.dto.*;
import telran.students.exceptions.*;
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
	@BeforeEach
	void setUp() {
		testDb.createDb();
	}

	@Test
	
	void addStudentTest() {
		
		assertEquals(studentNotExist, studentsService.addStudent(studentNotExist));
		assertEquals(studentNotExist, studentRepo.findById(ID_NOT_EXIST).orElseThrow().build());
		assertThrowsExactly(StudentIllegalStateException.class, ()->studentsService.addStudent(studentNotExist));
	}
	@Test

	void updatePhoneNumberTest() {
		assertEquals(studentUpdated, studentsService.updatePhoneNumber(ID1, PHONE_NOT_EXIST));
		assertEquals(PHONE_NOT_EXIST, studentRepo.findById(ID1).orElseThrow().getPhone());
		assertThrowsExactly(StudentNotFoundException.class,
				()->studentsService.updatePhoneNumber(ID1 + 1000, PHONE2));
	}
	@Test
	
	void addMarkTest() {
		
		assertFalse(studentRepo.findById(ID1).orElseThrow().getMarks().contains(markNotExist));
		assertEquals(markNotExist, studentsService.addMark(ID1, markNotExist));
		assertTrue(studentRepo.findById(ID1).orElseThrow().getMarks().contains(markNotExist));
		assertThrowsExactly(StudentNotFoundException.class,
				()->studentsService.addMark(ID1 + 1000, markNotExist));
		
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
	void getStudentsByPhonePrefixTest() {
		List<Student> expected = List.of(students[0], students[6]);
		assertIterableEquals(expected, studentsService.getStudentsByPhonePrefix("051"));
	}
	@Test
	void getStudentsMarksDateTest() {
		List<Student> expected = List.of(students[2], students[3],students[5]);
		assertIterableEquals(expected, studentsService.getStudentsMarksDate(DATE4));
		assertTrue(studentsService.getStudentsMarksDate(DATE_NOT_EXIST).isEmpty());
	}
	@Test
	void getStudentsMarksMonthYearTest() {
		List<Student> expected = List.of(students[0], students[1],students[2],students[3],students[4],students[5]);
		assertIterableEquals(expected, studentsService.getStudentsMarksMonthYear(2, 2024));
		assertTrue(studentsService.getStudentsMarksMonthYear(2, 2020).isEmpty());
	}
	@Test
	void getStudentsGoodSubjectMarkTest() {
		List<Student> expected = List.of(students[5]);
		assertIterableEquals(expected, studentsService.getStudentsGoodSubjectMark(SUBJECT1, 85));
		assertTrue(studentsService.getStudentsGoodSubjectMark(SUBJECT_NOT_EXIST, 85).isEmpty());
	}
	@Test
	void removeStudentTest() {
		assertEquals(students[0], studentsService.removeStudent(ID1));
		assertNull(studentRepo.findById(ID1).orElse(null));
		assertThrowsExactly(StudentNotFoundException.class, () -> studentsService.removeStudent(ID1));
	}
	@Test
	void getStudentsAllGoodMarksTest() {
		List<Student> expected = List.of(students[4], students[5]);
		assertIterableEquals(expected, studentsService.getStudentsAllGoodMarks(70));
		assertTrue(studentsService.getStudentsAllGoodMarks(100).isEmpty());
	}
	@Test
	void getStudentMarksSubjectTest() {
		List<Mark> expected = List.of(new Mark(SUBJECT1,70,DATE1),
			 new Mark(SUBJECT1,80, DATE2));
		assertIterableEquals(expected, studentsService.getStudentMarksSubject(ID1, SUBJECT1));
		assertTrue(studentsService.getStudentMarksSubject(ID1, SUBJECT3).isEmpty());
		assertThrowsExactly(StudentNotFoundException.class, () -> studentsService.getStudentMarksSubject(ID1 + 1000, SUBJECT3) );
	}
	@Test
	void getStudentsFewMarks() {
		List<Student> expected = List.of(students[6]);
		assertIterableEquals(expected, studentsService.getStudentsFewMarks(1));
	}
	@Test
	void getStudentsAvgScoreGreaterTest() {
		List<StudentAvgScore> expected = List.of(new StudentAvgScore(ID6, 100),
				new StudentAvgScore(ID5, 95));
		assertIterableEquals(expected, studentsService.getStudentsAvgScoreGreater(90));
		
	}
	@Test
	@Disabled
	void getStudentsGoodMarksSubjectTest() {
		List<Student>studentsExpected=List.of(new Student(students[6].id(),students[6].phone()));
		assertIterableEquals(studentsExpected, studentsService.getStudentsAllGoodMarksSubject(SUBJECT1, 95));
	}
	@Test
	void getStudentsMarksAmountBetweenTest() {
		List<Student>studentsExpected=List.of(new Student(students[4].id(),students[4].phone()));
		assertIterableEquals(studentsExpected, studentsService.getStudentsMarksAmountBetween(1, 1));

	}
	@Test
	void getStudentMarksAtDatesTest() {
		List<Mark>marksExpected=List.of(new Mark(SUBJECT1,70,DATE1));
		assertIterableEquals(marksExpected, studentsService.getStudentMarksAtDates(students[0].id(),DATE1,DATE3));
		
		
	}

}