package telran.students.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.students.model.StudentDoc;

public interface StudentRepo extends MongoRepository<StudentDoc,Long>{
@Query(value="{id:?0}", fields = "{id:1, phone:1}")
	StudentDoc findStudentNoMarks(long id);
@Query(value="{id:?0}", fields = "{id:0, marks:1}")
StudentDoc findStudentOnlyMarks(long id);
/********************************/
IdPhone findByPhone(String phone);
List<IdPhone> findByPhoneRegex(String regex);
List<IdPhone> findByMarksDate(LocalDate date);
List<IdPhone> findByMarksDateBetween(LocalDate firstDate, LocalDate lastDate);
List<IdPhone> findByMarksSubjectAndMarksScoreGreaterThan(String subject, int markThreshold);
/*******************/
@Query("{$and:[{marks:{$elemMatch:{score:{$gt:?0}}}},{marks:{$not:{$elemMatch:{score:{$lte:?0}}}}}]}")
List<IdPhone> findAllGoodMarks(int markThreshold);
/**********************************************************/
@Query("{$expr:{$lt:[{$size:$marks}, ?0]}}")
List<IdPhone> findFewMarks(int nMarks);
@Query("{$and:[{'marks':{$elemMatch:{$and:[{'score':{$gt:?1}},{'subject':?0}]}}},{'marks':{$not:{$elemMatch:{'score':{$lte:?1}}}}}]")
List<IdPhone>findAllGoodMarksOnSubject(String subject,int markThreshold);
@Query("{$expr:{$and:[{$gte:[{$size:'$marks'}, ?0]},{$lte:[{$size:'$marks'}, ?1]}]}}")
List<StudentDoc> findStudentsByMarksAmountBetween(int min, int max);


}