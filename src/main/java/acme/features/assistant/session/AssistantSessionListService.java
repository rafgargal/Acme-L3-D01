
package acme.features.assistant.session;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("tutorialId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		final Tutorial tutorial = this.repository.findTutorialById(tutorialId);

		final int assistantIdFromTutorial = tutorial.getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser);

	}

	@Override
	public void load() {
		List<Session> objects;

		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		objects = this.repository.findSessionsByTutorialId(tutorialId);

		super.getResponse().setGlobal("tutorialId", tutorialId);
		super.getResponse().setGlobal("canCreateSession", !this.repository.findTutorialById(tutorialId).isPublished());

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Session object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "sAbstract", "type");

		final int tutorialId = super.getRequest().getData("tutorialId", int.class);
		super.getResponse().setGlobal("tutorialId", tutorialId);
		super.getResponse().setGlobal("canCreateSession", !this.repository.findTutorialById(tutorialId).isPublished());

		super.getResponse().setData(tuple);
	}

}
