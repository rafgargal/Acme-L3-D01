
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository practicumRepository;


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
		Practicum practicum;
		Company company;
		int companyId;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();

		company = this.practicumRepository.findCompanyById(companyId);

		practicum = new Practicum();
		practicum.setCompany(company);

		super.getBuffer().setData(practicum);

	}

	@Override
	public void bind(final Practicum practicum) {
		Course course;
		int courseId;
		assert practicum != null;

		courseId = super.getRequest().getData("course", int.class);
		course = this.practicumRepository.findCourseById(courseId);
		super.bind(practicum, "code", "title", "summary", "goals");
		practicum.setCourse(course);

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

		Tuple tuple;
		SelectChoices choices;
		Collection<Course> courses;

		courses = this.practicumRepository.findAllCourses();
		choices = SelectChoices.from(courses, "code", practicum.getCourse());

		tuple = super.unbind(practicum, "code", "title", "summary", "goals");
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}

}
