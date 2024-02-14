package telran.students.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.students.dto.Student;
import telran.students.model.StudentDoc;

public interface StudentRepo extends MongoRepository<StudentDoc,Long>{
@Query(value="{id:?0}", fields = "{id:1, phone:1}")
	StudentDoc findStudentNoMarks(long id);
@Query(value="{id:?0}", fields = "{id:0, marks:1}")
StudentDoc findStudentOnlyMarks(long id);
/********************************/
IdPhone findByPhone(String phone);
List<IdPhone> findByPhoneRegex(String regex);
@Query(value="{'marks.date':?0}",fields = "{'id':1, 'phone':1,'marks':1}")
List<StudentDoc> findStudentsMarksDate(LocalDate date);
@Query(value="{'marks.date.month': ?0, 'marks.date.year': ?1}", fields="{'id': 1, 'phone': 1, 'marks': 1}")
List<StudentDoc> findStudentsMarksMonthYear(int month, int year);
}