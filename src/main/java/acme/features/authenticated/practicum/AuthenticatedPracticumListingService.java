
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListingService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


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

		Collection<Practicum> practicums;
		int courseId;

		courseId = super.getRequest().getData("id", int.class);
		practicums = this.repository.findPracticumByCourseId(courseId);

		super.getBuffer().setData(practicums);

	}

	@Override
	public void unbind(final Practicum practicum) {

		assert practicum != null;
		Tuple tuple;
		tuple = super.unbind(practicum, "code", "title", "summary");
		tuple.put("courseCode", practicum.getCourse().getCode());
		super.getResponse().setData(tuple);
	}

}
