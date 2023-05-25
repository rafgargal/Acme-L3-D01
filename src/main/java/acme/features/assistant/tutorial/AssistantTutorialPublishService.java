
package acme.features.assistant.tutorial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialPublishService extends AbstractService<Assistant, Tutorial> {

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
		// Cannot publish if tutorial doesn't belong to you or tutorial has been published
		// Or it hasn't at least one session
		super.getResponse().setAuthorised(true);
		final int tutorialId = super.getRequest().getData("id", int.class);
		final Tutorial tutorial = this.repository.findTutorialById(tutorialId);

		final int assistantIdFromTutorial = tutorial.getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		final List<Session> sessions = this.repository.findSessionsByTutorialId(tutorialId);

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser && !tutorial.isPublished() && !sessions.isEmpty());
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

		super.bind(object, "code", "title", "tAbstract", "estimatedTotalTime", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		object.setPublished(true);

		this.repository.save(object);
	}

}
