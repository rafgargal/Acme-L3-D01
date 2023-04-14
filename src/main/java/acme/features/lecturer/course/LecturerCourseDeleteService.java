
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.auditing.Audit;
import acme.entities.course.Course;
import acme.entities.course.LectureCourse;
import acme.entities.enrolments.Enrolment;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

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
		object = this.repository.findOneCourseById(id);

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

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		List<LectureCourse> llc;
		List<Audit> la;
		List<Enrolment> le;
		List<Activity> la2;
		List<Tutorial> lt;

		llc = this.repository.findAllLectureCourseByCourseId(object.getId());
		la = this.repository.findAllAuditByCourseId(object.getId());
		le = this.repository.findAllEnrolmentByCourseId(object.getId());
		lt = this.repository.findAllTutorialByCourseId(object.getId());
		for (final Enrolment e : le) {
			la2 = this.repository.findAllActivityByEnrolmentId(e.getId());
			this.repository.deleteAll(la2);
		}
		this.repository.deleteAll(llc);
		this.repository.deleteAll(la);
		this.repository.deleteAll(le);
		this.repository.deleteAll(lt);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "cAbstract", "draftMode", "retailPrice", "furtherInfo");

		super.getResponse().setData(tuple);
	}

}
