package telran.students;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.exceptions.StudentIllegalStateException;
import telran.students.exceptions.StudentNotFoundException;
import telran.students.repo.StudentRepo;
import telran.students.service.StudentsServiceImpl;

@SpringBootTest
class StudentsMarksServiceTests {
	@Autowired
    StudentRepo studentsRepo;
	@Autowired
    StudentsServiceImpl service;
	Student student1=new Student(111L,"0544439132");
	Student student2=new Student(113L,"0544439133");
	Mark mark1=new Mark("History",98,LocalDate.now());
	Mark mark2=new Mark("Math",60,LocalDate.now());
	String newPhoneNumber="0541111111";
	Long studentNotFoundId=222L;



	@Test
	void contextLoads() {
		
	}
	
	@Test
	void addStudentTest() {
		int studentRepoSize=studentsRepo.findAll().size();
		service.addStudent(student1);
		assertEquals(studentRepoSize+1,studentsRepo.findAll().size());
		assertThrowsExactly(StudentIllegalStateException.class,()-> service.addStudent(student1));
		service.addStudent(student2);
		assertEquals(studentRepoSize+2,studentsRepo.findAll().size());
	}
	@Test
	void updatePhoneNumberTest() {
		Long studentId=student1.id();
		service.updatePhoneNumber(studentId, newPhoneNumber);
		assertEquals(newPhoneNumber,studentsRepo.findById(studentId).orElseThrow().getPhone());
		assertThrowsExactly(StudentNotFoundException.class, ()->service.updatePhoneNumber(studentNotFoundId, newPhoneNumber));
		
	}
	@Test
	void addMarkTest() {
		int student1MarksBefore=studentsRepo.findById(student1.id()).orElseThrow().getMarks().size();
		assertThrowsExactly(StudentNotFoundException.class, ()->service.addMark(studentNotFoundId,mark1));
		assertEquals(0,studentsRepo.findById(student1.id()).orElseThrow().getMarks().size());
		service.addMark(student1.id(), mark1);
		int student1MarksAfter=studentsRepo.findById(student1.id()).orElseThrow().getMarks().size();
		assertEquals(student1MarksBefore+1,student1MarksAfter);
		
		
		
		
	}
	

}
