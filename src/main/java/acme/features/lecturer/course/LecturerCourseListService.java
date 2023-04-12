
package acme.features.lecturer.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseListService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		List<Course> objects;
		int id;

		id = super.getRequest().getPrincipal().getAccountId();
		objects = this.repository.findAllCourseByLecturerId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "retailPrice");

		super.getResponse().setData(tuple);
	}

}
