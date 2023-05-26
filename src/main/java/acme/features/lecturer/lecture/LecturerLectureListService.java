
package acme.features.lecturer.lecture;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

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
		status = course != null && super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		List<Lecture> objects;
		final int id;

		id = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findAllLectureByCourseId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "lAbstract", "learningTime", "body", "activityType", "furtherInfo", "draftMode");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Lecture> objects) {
		assert objects != null;

		final int id;

		id = super.getRequest().getData("courseId", int.class);

		final boolean courseInDraftMode = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class)).isDraftMode();

		super.getResponse().setGlobal("courseInDraftMode", courseInDraftMode);

		super.getResponse().setGlobal("courseId", id);
	}

}
