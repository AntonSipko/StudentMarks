package telran.students.service;

import java.time.LocalDate;
import java.util.List;

import telran.students.dto.*;

public interface StudentsService {
	Student addStudent(Student student);
	Mark addMark(long id, Mark mark);
	Student updatePhoneNumber(long id, String phoneNumber);
	Student removeStudent(long id);
	Student getStudent(long id);
	List<Mark> getMarks(long id);
	
	Student getStudentByPhoneNumber(String phoneNumber);
	List<Student> getStudentsByPhonePrefix(String prefix);
	/*********************************************/
	//The methods for the HW #71
	/**
	 * 
	 * @param date
	 * @return students having a mark of a given date
	 */
	List<Student> getStudentsMarksDate(LocalDate date);
	/********************************/
	/**
	 * 
	 * @param month
	 * @param year
	 * @return students having a mark of a given month and a given year
	 */
	List<Student> getStudentsMarksMonthYear(int month, int year);
	/****************************************************************/
	/**
	 * 
	 * @param markThreshold
	 * @return students having a mark of a given subject greater than a given markThreshold
	 */
	List<Student> getStudentsGoodSubjectMark(String subject, int markThreshold);
	//The methods for the CW #72 (next CW)
	/**
	 * 
	 * @param markThreshold
	 * @return list of students having all marks greater than markThreshold
	 */
	List<Student> getStudentsAllGoodMarks(int markThreshold);
	/****************************/
	/**
	 * 
	 * @param nMarks
	 * @return list of students having amount of marks less than nMarks
	 */
	List<Student> getStudentsFewMarks(int nMarks);
	/***************************/
	List<Mark> getStudentMarksSubject(long id, String subject);
	/*****************/
	List<StudentAvgScore> getStudentsAvgScoreGreater(int avgThreshold);
	//methods for HW #72
	List<Student> getStudentsAllGoodMarksSubject(String subject, int thresholdScore);
	List<Student> getStudentsMarksAmountBetween(int min, int max);
	List<Mark> getStudentMarksAtDates(long id, LocalDate from, LocalDate to);
	List<Long> getBestStudents(int nStudents);
	List<Long> getWorstStudents(int nStudents);
}