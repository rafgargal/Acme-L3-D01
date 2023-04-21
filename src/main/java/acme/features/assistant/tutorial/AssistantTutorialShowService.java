
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
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int tutorialId = super.getRequest().getData("id", int.class);

		final int assistantIdFromTutorial = this.repository.findTutorialById(tutorialId).getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {

		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "tAbstract", "estimatedTotalTime", "goals", "published");
		object.setCourse(course);

	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "estimatedTotalTime", "goals", "tAbstract", "title", "assistant.supervisor", "course", "course.title", "published");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		final boolean canPublish = this.repository.findSessionsByTutorialId(super.getRequest().getData("id", int.class)).size() > 0;

		super.getResponse().setGlobal("canPublish", canPublish);

		super.getResponse().setData(tuple);
	}

}
