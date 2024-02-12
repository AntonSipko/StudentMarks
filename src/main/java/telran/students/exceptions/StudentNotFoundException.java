package telran.students.exceptions;

import telran.exceptions.NotFoundException;
import telran.students.service.ServiceErrorMessages;

@SuppressWarnings("serial")
public class StudentNotFoundException extends NotFoundException{

	public StudentNotFoundException() {
		super(ServiceErrorMessages.STUDENT_NOT_FOUND);
		// TODO Auto-generated constructor stub
	}

}
