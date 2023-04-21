
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Tutorial object;
		Assistant assistant;

		assistant = this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Tutorial();
		object.setAssistant(assistant);
		object.setPublished(false);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		System.out.println(courseId);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "tAbstract", "estimatedTotalTime", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;

			existing = this.repository.findTutorialByCode(object.getCode());
			super.state(existing == null, "code", "assistant.tutorial.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("course")) {
			final Course existing = object.getCourse();
			super.state(existing != null, "course", "assistant.tutorial.form.error.null-course");
		}
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		System.out.println("ENTRA EN PERform");

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "estimatedTotalTime", "goals", "tAbstract", "title", "assistant.supervisor", "course", "course.title", "published", "id");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
