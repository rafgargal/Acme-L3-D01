
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository practicumRepository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);

	}
	@Override
	public void authorise() {
		final boolean status;
		Practicum practicum;
		final Principal principal;
		int practicumId;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.practicumRepository.findPracticumById(practicumId);
		principal = super.getRequest().getPrincipal();

		status = practicum.getCompany().getId() == principal.getActiveRoleId() && practicum.getDraftMode();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Practicum practicum;
		int id;

		id = super.getRequest().getData("id", int.class);
		practicum = this.practicumRepository.findPracticumById(id);

		practicum.setDraftMode(false);
		super.getBuffer().setData(practicum);

	}

	@Override
	public void bind(final Practicum practicum) {
		assert practicum != null;

		int courseId;

		courseId = super.getRequest().getData("course", int.class);

		super.bind(practicum, "code", "title", "summary", "goals", "draftMode");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
	}

	@Override
	public void perform(final Practicum practicum) {
		assert practicum != null;

		this.practicumRepository.save(practicum);
	}
	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.practicumRepository.findAllCourses();
		choices = SelectChoices.from(courses, "code", practicum.getCourse());
		tuple = super.unbind(practicum, "code", "title", "summary", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);

	}

}
