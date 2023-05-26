
package acme.features.lecturer.course;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
import acme.datatypes.ActivityType2;
import acme.entities.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course course;

		course = this.repository.findOneCourseById(super.getRequest().getData("id", int.class));

		status = course != null && super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = SerializationUtils.clone(this.repository.findOneCourseById(id));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "cAbstract", "draftMode", "retailPrice", "furtherInfo");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "lecturer.course.form.error.duplicated");
		}

		super.state(!(this.repository.findActivityType(object.getId()) == ActivityType.THEORETICAL), "*", "lecturer.course.error.theoretical-course");
		super.state(!this.repository.isAnyLectureInDraftModeByCourseId(object.getId()), "*", "lecturer.course.error.lecture-in-draft-mode");

		final int numLectures = this.repository.numOfLecturesOfOneTypeByCourseId(object.getId(), ActivityType2.THEORETICAL) + this.repository.numOfLecturesOfOneTypeByCourseId(object.getId(), ActivityType2.HANDS_ON);
		super.state(numLectures > 0, "*", "lecturer.course.error.course-without-lecture");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "cAbstract", "draftMode", "retailPrice", "furtherInfo");

		super.getResponse().setData(tuple);
	}

}
