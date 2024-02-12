package telran.students.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.exceptions.StudentIllegalStateException;
import telran.students.exceptions.StudentNotFoundException;
import telran.students.model.StudentDoc;
import telran.students.repo.StudentRepo;
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentsServiceImpl implements StudentsService {
	final StudentRepo studentRepo;
	@Override
	@Transactional
	public Student addStudent(Student student) {
		long id = student.id();
		if(studentRepo.existsById(id)) {
			log.error("student with id {} already exists", id);
			throw new StudentIllegalStateException();
		}
		StudentDoc studentDoc = new StudentDoc(student);
		studentRepo.save(studentDoc);
		log.debug("student {} has been saved", student);
		return student;
	}

	@Override
	public Mark addMark(long id, Mark mark) {
		StudentDoc studentDoc=studentRepo.findById(id).orElseThrow(()->new StudentNotFoundException());
		log.debug("student with id {}",id);
		studentDoc.getMarks().add(mark);
		studentRepo.save(studentDoc);
		log.debug("mark {} for subject{} was saved for the student with ID{}",mark.score(),mark.subject(),studentDoc.getId());
		return mark;

	}

	@Override
	@Transactional
	public Student updatePhoneNumber(long id, String phoneNumber) {
		StudentDoc studentDoc = studentRepo.findById(id)
				.orElseThrow(() -> new StudentNotFoundException());
		log.debug("student with id {}, old phone number {}, new phone number {}",
				id,studentDoc.getPhone(), phoneNumber);
		studentDoc.setPhone(phoneNumber);
		Student res = studentRepo.save(studentDoc).build();
		log.debug("Student {} has been saved ", res);
		return res;
	}

}
