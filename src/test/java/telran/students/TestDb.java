package telran.students;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.students.dto.*;
import telran.students.model.StudentDoc;
import telran.students.repo.StudentRepo;

@Component
@RequiredArgsConstructor
public class TestDb {
	final StudentRepo studentRepo;
	//ID's
	static final long ID1 = 1;
	static final long ID2 = 2;
	static final long ID3 = 3;
	static final long ID4 = 4;
	static final long ID5 = 5;
	static final long ID6 = 6;
	static final long ID7 = 7;
	static final long ID_NOT_EXIST = 1000;
	//phone numbers
	static final String PHONE1 = "051-1234567";
	static final String PHONE2 = "052-1234567";
	static final String PHONE3 = "053-1234567";
	static final String PHONE4 = "054-1234567";
	static final String PHONE5 = "055-1234567";
	static final String PHONE6 = "056-1234567";
	static final String PHONE7 = "051-1234568";
	static final String PHONE_NOT_EXIST = "051-7654321";
	//Subjects
	static final String SUBJECT1 = "Subject1";
	static final String SUBJECT2 = "Subject2";
	static final String SUBJECT3 = "Subject3";
	static final String SUBJECT4 = "Subject4";
	static final String SUBJECT_NOT_EXIST = "Java";
	//Dates
	static final LocalDate DATE1 = LocalDate.of(2024, 01, 01);
	static final LocalDate DATE2 = LocalDate.of(2024, 01, 10);
	static final LocalDate DATE3 = LocalDate.of(2024, 02, 10);
	static final LocalDate DATE4 = LocalDate.of(2024, 02, 15);
	static final LocalDate DATE_NOT_EXIST = LocalDate.of(2024, 02, 20);
	
	
	
	
	//marks
	static Mark[][] marks = {
			{new Mark(SUBJECT1,70,DATE1),
			 new Mark(SUBJECT1,80, DATE2),
			 new Mark(SUBJECT2,80, DATE3)
			 },
			{new Mark(SUBJECT2,70,DATE1),
			 new Mark(SUBJECT3,85, DATE2),
				 new Mark(SUBJECT4,80, DATE3)
			},
			{new Mark(SUBJECT3,70,DATE1),
				 new Mark(SUBJECT4,80, DATE2),
				 new Mark(SUBJECT1, 70, DATE3),
				 new Mark(SUBJECT4,80, DATE4),
				 },
			{
			new Mark(SUBJECT1, 70, DATE3),
			new Mark(SUBJECT4, 70, DATE4),
			},
			{new Mark(SUBJECT4, 95, DATE3)},
			{
				new Mark(SUBJECT1, 100, DATE1),
				new Mark(SUBJECT2, 100, DATE2),
				new Mark(SUBJECT3, 100, DATE3),
				new Mark(SUBJECT4, 100, DATE4),
			},
			{}
	};
	static Mark markNotExist = new Mark(SUBJECT_NOT_EXIST, 60, DATE_NOT_EXIST);
	//Students
	static Student[] students = {
			new Student(ID1, PHONE1),
			new Student(ID2, PHONE2),
			new Student(ID3, PHONE3),
			new Student(ID4, PHONE4),
			new Student(ID5, PHONE5),
			new Student(ID6, PHONE6),
			new Student(ID7, PHONE7),
	};
	static Student studentNotExist = new Student(ID_NOT_EXIST, PHONE_NOT_EXIST);
	static Student studentUpdated = new Student(ID1, PHONE_NOT_EXIST);
	void createDb() {
		studentRepo.deleteAll();
		List<StudentDoc> studentDocs = IntStream.range(0, students.length)
				.mapToObj(this::indexToStudent).toList();
		studentRepo.saveAll(studentDocs);
	}
	StudentDoc indexToStudent(int index) {
		StudentDoc res = new StudentDoc(students[index]);
		List<Mark> marksList = res.getMarks();
		marksList.addAll(List.of(marks[index]));
		return res;
	}
}