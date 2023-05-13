
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType;
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
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		if (super.getRequest().hasData("courseId", int.class)) {
			final Course course = this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class));
			status = super.getRequest().getPrincipal().hasRole(course.getLecturer());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int userAccountId;
		Lecturer lecturer;

		userAccountId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findOneLecturerById(userAccountId);

		object = new Lecture();
		object.setLecturer(lecturer);
		object.setDraftMode(true);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "lAbstract", "learningTime", "body", "activityType", "furtherInfo", "draftMode");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("learningTime"))
			if (object.getLearningTime() != null)
				super.state(object.getLearningTime() > 0, "learningTime", "lecturer.lecture.error.learningTime-negative");
			else
				super.state(object.getLearningTime() != null, "learningTime", "javax.validation.constraints.NotNull.message");

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
		if (super.getRequest().hasData("courseId", int.class)) {
			final LectureCourse lc = new LectureCourse();
			lc.setCourse(this.repository.findOneCourseById(super.getRequest().getData("courseId", int.class)));
			lc.setLecture(object);
			this.repository.save(lc);
		}

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "lAbstract", "learningTime", "body", "activityType", "furtherInfo", "draftMode");

		if (super.getRequest().hasData("courseId", int.class)) {
			final int courseId = super.getRequest().getData("courseId", int.class);
			final Course course = this.repository.findOneCourseById(courseId);
			tuple.put("courseId", courseId);
			tuple.put("courseCode", course.getCode());
		}

		tuple.put("activityTypes", SelectChoices.from(ActivityType.class, object.getActivityType()));

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
