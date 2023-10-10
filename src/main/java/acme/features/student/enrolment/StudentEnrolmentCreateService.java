
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void validate(final Enrolment object) {
		int studentId;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<String> allCodes = this.repository.findAllEnrolmentCode();
		final Collection<String> codeRegistrados = this.repository.findCourseCodeByEnrolmentByStudentId(studentId);
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(!allCodes.contains(object.getCode()), "code", "student.enrolment.error.code");
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(!codeRegistrados.contains(object.getCourse().getCode()), "course", "student.enrolment.error.courseCode");
	}

	@Override
	public void load() {
		Enrolment object;
		Student student;

		student = this.repository.findStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Enrolment();
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "motivation", "goals");
		object.setCourse(course);
		object.setDraftMode(true);
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCoursesWithoutDrafrMode();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "motivation", "goals", "course");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);

	}

}
