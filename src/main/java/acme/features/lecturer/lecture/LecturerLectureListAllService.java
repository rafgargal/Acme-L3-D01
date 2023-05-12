
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListAllService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		List<Lecture> objects;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findAllLectureByLecturerId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "learningTime", "activityType");

		super.getResponse().setData(tuple);
	}

}
