
package acme.features.assistant.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.ActivityType2;
import acme.entities.tutorial.Session;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionShowService extends AbstractService<Assistant, Session> {

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
		final int sessionId = super.getRequest().getData("id", int.class);

		final int assistantIdFromSession = this.repository.findSessionById(sessionId).getTutorial().getAssistant().getId();

		final int assistantIdFromLoggedUser = super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(assistantIdFromSession == assistantIdFromLoggedUser);
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
	public void unbind(final Session object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;
		choices = SelectChoices.from(ActivityType2.class, object.getType());

		tuple = super.unbind(object, "title", "sAbstract", "startDateTime", "endDateTime", "furtherInformation");
		tuple.put("type", choices.getSelected().getKey());
		tuple.put("types", choices);

		super.getResponse().setGlobal("canDeleteOrUpdateSession", !object.getTutorial().isPublished());

		super.getResponse().setData(tuple);
	}

}
