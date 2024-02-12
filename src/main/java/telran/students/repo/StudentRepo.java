package telran.students.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.students.model.StudentDoc;

public interface StudentRepo extends MongoRepository<StudentDoc,Long>{

}
