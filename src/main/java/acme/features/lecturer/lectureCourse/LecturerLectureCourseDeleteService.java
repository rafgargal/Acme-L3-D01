
package acme.features.lecturer.lectureCourse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseDeleteService extends AbstractService<Lecturer, LectureCourse> {

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
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(course.getLecturer());

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
		int courseId;
		Lecture lecture;
		Course course;

		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		object.setLecture(lecture);

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;
	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;

		final LectureCourse lc = this.repository.findOneLectureCourseByCourseAndLecture(object.getCourse(), object.getLecture());

		this.repository.delete(lc);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;

		int courseId;
		int lecturerId;
		Course course;
		List<Lecture> lectures;
		Tuple tuple;
		SelectChoices choices;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findOneCourseById(courseId);
		lectures = this.repository.findAllLectureOfACourse(courseId);
		choices = SelectChoices.from(lectures, "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		tuple.put("courseId", courseId);
		tuple.put("courseCode", course.getCode());

		super.getResponse().setData(tuple);
	}

}
