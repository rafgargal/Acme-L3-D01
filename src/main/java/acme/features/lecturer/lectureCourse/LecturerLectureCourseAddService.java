
package acme.features.lecturer.lectureCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseAddService extends AbstractService<Lecturer, LectureCourse> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		final Course course = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class));
		status = super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LectureCourse object;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);

		object = new LectureCourse();
		object.setCourse(course);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LectureCourse object) {
		assert object != null;
		int lectureId;
		Lecture lecture;
		int courseId;
		Course course;

		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setLecture(lecture);
		object.setCourse(course);
	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;

	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;

		Tuple tuple;
		int lecturerId;
		int courseId;
		Course course;
		SelectChoices choices;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findOneCourseById(courseId);
		choices = SelectChoices.from(this.repository.findAllLectureForACourse(courseId, lecturerId), "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");
		tuple.put("lectures", choices);
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("courseId", courseId);
		tuple.put("courseCode", course.getCode());
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
