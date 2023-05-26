
package acme.features.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Session;
import acme.entities.tutorial.Tutorial;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionDeleteService extends AbstractService<Assistant, Session> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		// Cannot delete if tutorial doesn't belong to you or tutorial has been published

		final Session session = this.repository.findSessionById(super.getRequest().getData("id", int.class));

		final Tutorial tutorial = session.getTutorial();

		final int assistantIdFromTutorial = tutorial.getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromTutorial == assistantIdFromLoggedUser && !tutorial.isPublished());
	}

	@Override
	public void load() {

		Session object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionById(id);

		super.getResponse().setGlobal("canDeleteOrUpdateSession", !object.getTutorial().isPublished());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Session object) {

		assert object != null;

	}

	@Override
	public void validate(final Session object) {
		assert object != null;
	}

	@Override
	public void perform(final Session object) {
		assert object != null;

		this.repository.delete(object);
	}

}
